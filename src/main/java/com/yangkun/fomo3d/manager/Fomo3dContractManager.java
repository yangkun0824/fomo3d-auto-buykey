package com.yangkun.fomo3d.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple13;
import org.web3j.tuples.generated.Tuple14;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.yangkun.fomo3d.airdrop.App.CONTRACT_TARGET;
import com.yangkun.fomo3d.airdrop.contract.Fomo3dAirdropContract;
import com.yangkun.fomo3d.airdrop.contract.Fomo3dContract;
import com.yangkun.fomo3d.utils.SysConfig;
import com.yangkun.fomo3d.utils.TUtil;

public class Fomo3dContractManager {
	private static Logger log = LoggerFactory.getLogger("aLog");

	public static Fomo3dContractManager managerInstance;

	private Web3j web3j;

	private ArrayList<Account> accounts;

	// 普通retryer
	private Retryer<Object> retryer;

	public synchronized static Fomo3dContractManager getInstance() throws IOException, CipherException {
		if (managerInstance == null) {
			managerInstance = new Fomo3dContractManager();
		}
		return managerInstance;
	}

	private Fomo3dContractManager() throws IOException, CipherException {

		// 创建web3j
		web3j = Web3j.build(new HttpService(SysConfig.RPC_ADDRESS));

		// 创建账户
		accounts = new ArrayList<>();

		// account 监控fomo3d最后一个key
		Account account = new Account(web3j, SysConfig.PASSWORD, SysConfig.KEYSTORE);
		accounts.add(account);

		// account1 监控last winner最后一个key
		Account account1 = new Account(web3j, SysConfig.PASSWORD1, SysConfig.KEYSTORE1);
		accounts.add(account1);

		// 两个账号都监控airdrop
		for (Account acc : accounts) {
			acc.setAirDropContract(new Fomo3dAirdropContract(SysConfig.FOMO3D_MANAGE_CONTRACT_ADDRESS, web3j,
					acc.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT));
		}

		// 初始化重试器
		retryer = RetryerBuilder.<Object>newBuilder().retryIfResult(r -> r == null).retryIfException()
				.withStopStrategy(StopStrategies.stopAfterAttempt(10))
				.withWaitStrategy(WaitStrategies.fixedWait(1000, TimeUnit.MILLISECONDS))
				.withRetryListener(new RetryListener() {
					public <V> void onRetry(Attempt<V> attempt) {
						if (attempt.hasException()) {
							log.error("retry exception {}", attempt.getAttemptNumber());
							attempt.getExceptionCause().printStackTrace();
						}

						if (attempt.hasResult()) {
							log.info("retry result: {} number: {}", attempt.getResult(), attempt.getAttemptNumber());
						}
					}
				}).build();
	}

	private void setFomo3dGasProvider(BigInteger gas, int retryCount) {

		for (Account account : accounts) {
			if (account.getFomo3dContract() != null) {
				account.getFomo3dContract().setGasProvider(new ContractGasProvider() {

					@Override
					public BigInteger getGasPrice(String contractFunc) {

						if (contractFunc.compareTo("buyXaddr") == 0 || contractFunc.compareTo("buyXid") == 0
								|| contractFunc.compareTo("buyXname") == 0) {

							// 增加交易手续费失败了增加交易
							float multiple = (float) (retryCount + 1.5);
							BigInteger gasReal = gas.multiply(BigDecimal.valueOf(multiple).toBigInteger());
							BigInteger gasMax = Convert.toWei("50", Unit.GWEI).toBigInteger();

							// 防止手续费太多
							if (gasReal.compareTo(gasMax) >= 0) {
								gasReal = gasMax;
							}
							log.info("retry count: {} multiple: {} gas: {} gas real: {}", retryCount, multiple, gas,
									gasReal);
							return gasReal;
						}

						return gas;
					}

					@Override
					public BigInteger getGasLimit(String contractFunc) {
						// TODO Auto-generated method stub
						return new BigInteger(Integer.toString(SysConfig.GAS_LIMIT));
					}

					@Deprecated
					public BigInteger getGasPrice() {
						return gas;
					}

					@Deprecated
					public BigInteger getGasLimit() {
						return new BigInteger(Integer.toString(SysConfig.GAS_LIMIT));
					}
				});
			}
		}
	}

	private void setAirDropGasProvider(BigInteger gas, int retryCount) {

		for (Account account : accounts) {
			account.getAirDropContract().setGasProvider(new ContractGasProvider() {

				@Override
				public BigInteger getGasPrice(String contractFunc) {
					BigInteger gasReal = gas.multiply(BigInteger.valueOf(2));
					return gas;
				}

				@Override
				public BigInteger getGasLimit(String contractFunc) {
					// TODO Auto-generated method stub
					if (contractFunc.compareTo("checkAndAttack") == 0) {
						return new BigInteger(Integer.toString(SysConfig.GAS_LIMIT_AIRDROP));
					}
					return new BigInteger(Integer.toString(SysConfig.GAS_LIMIT));
				}

				@Deprecated
				public BigInteger getGasPrice() {
					return gas;
				}

				@Deprecated
				public BigInteger getGasLimit() {
					return new BigInteger(Integer.toString(SysConfig.GAS_LIMIT_AIRDROP));
				}
			});
		}
	}

	public BigInteger retryCurrentGasPrice() {

		Callable<Object> task = new Callable<Object>() {
			public BigInteger call() throws Exception {
				return getCurrentGasPrice();
			}
		};

		try {
			return (BigInteger) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private BigInteger getCurrentGasPrice() {
		BigInteger minWei = Convert.toWei("1", Unit.GWEI).toBigInteger();

		if (web3j != null) {
			try {
				String hex16 = web3j.ethGasPrice().send().getResult();
				if (!TUtil.isNullOrNot(hex16)) {
					hex16 = hex16.substring(2);
					BigInteger gas = new BigInteger(hex16, 16);

					log.info("Online Line GAS PRICE : " + gas.toString(10));
					if (gas.compareTo(minWei) >= 0) {
						return gas;
					} else {
						return minWei;
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}

		return minWei;
	}

	// 攻击合约
	// target 攻击对象
	// 返回txhash集合
	public ArrayList<String> retryAttack(String target, int mutiple) {

		Callable<Object> task = new Callable<Object>() {
			public ArrayList<String> call() throws Exception {
				return attack(target, mutiple);
			}
		};

		try {
			return (ArrayList<String>) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> attack(String target, int mutiple) throws Exception {
		// 设置gasprice
		setAirDropGasProvider(retryCurrentGasPrice(), 0);

		ArrayList<String> result = new ArrayList<>();
		// 开始攻击
		Iterator<Account> iterator = accounts.iterator();
		while (iterator.hasNext()) {
			Account account = iterator.next();

			Fomo3dAirdropContract contract = (Fomo3dAirdropContract) account.getAirDropContract();

			// 每次花费0.1eth
			BigInteger weiValue = Convert.toWei("0.1", Unit.ETHER).toBigInteger();

			String txhash = account.getCheckAndAttackTransactionHash(target, weiValue);
			log.info("send the checkandattack transaction:" + txhash);

			// send the transaction
			contract.checkAndAttack(target, BigInteger.valueOf(mutiple), weiValue).sendAsync();

			result.add(txhash);
		}

		log.info("<<<<<<<<<< Step 3 攻击完毕");

		return result;
	}

	public BigInteger retryBuyPrice(String target) {

		Callable<Object> task = new Callable<Object>() {
			public BigInteger call() throws Exception {
				return getBuyPrice(target);
			}
		};

		try {
			return (BigInteger) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 得到key的价格
	private BigInteger getBuyPrice(String target) throws Exception {
		Account account = accounts.get(0);

		Fomo3dContract fomo3dLongContract = new Fomo3dContract(target, web3j,
				account.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT);
		account.setFomo3dContract(fomo3dLongContract);

		BigInteger result = fomo3dLongContract.getBuyPrice().send();
		BigDecimal eth = Convert.fromWei(result.toString(), Unit.ETHER).setScale(8, RoundingMode.DOWN);

		log.info("target {} Current Buy Price: {} ETH", target, eth);

		return result;
	}

	public BigInteger retryTimeLeft(String target) {

		Callable<Object> task = new Callable<Object>() {
			public BigInteger call() throws Exception {
				return getTimeLeft(target);
			}
		};

		try {
			return (BigInteger) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 得到剩余时间
	private BigInteger getTimeLeft(String target) throws Exception {
		Account account = accounts.get(0);

		Fomo3dContract fomo3dLongContract = new Fomo3dContract(target, web3j,
				account.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT);
		account.setFomo3dContract(fomo3dLongContract);

		BigInteger result = fomo3dLongContract.getTimeLeft().send();

		log.info("target {} Current Round Time Left: {}", target, result);

		return result;
	}

	public BigInteger retryAirDropPot(String target) {

		Callable<Object> task = new Callable<Object>() {
			public BigInteger call() throws Exception {
				return getAirDropPot(target);
			}
		};

		try {
			return (BigInteger) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public BigInteger getAirDropPot(String target) throws Exception {
		Account account = accounts.get(0);

		Fomo3dContract fomo3dLongContract = new Fomo3dContract(target, web3j,
				account.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT);
		account.setFomo3dContract(fomo3dLongContract);

		BigInteger airDropPot = fomo3dLongContract.airDropPot_().send();

		return airDropPot;
	}

	public BigInteger retryAirDropTracker(String target) {

		Callable<Object> task = new Callable<Object>() {
			public BigInteger call() throws Exception {
				return getAirDropTracker(target);
			}
		};

		try {
			return (BigInteger) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public BigInteger getAirDropTracker(String target) throws Exception {
		Account account = accounts.get(0);

		Fomo3dContract fomo3dLongContract = new Fomo3dContract(target, web3j,
				account.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT);
		account.setFomo3dContract(fomo3dLongContract);

		BigInteger airDropTracker = fomo3dLongContract.airDropTracker_().send();
		return airDropTracker;
	}

	public Tuple2<String, BigInteger> retryCurrentLeaderAndEndTime(String target, CONTRACT_TARGET t) {

		Callable<Object> task = new Callable<Object>() {
			public Tuple2<String, BigInteger> call() throws Exception {
				return getCurrentLeaderAndEndTime(target, t);
			}
		};

		try {
			return (Tuple2<String, BigInteger>) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Tuple2<String, BigInteger> getCurrentLeaderAndEndTime(String target, CONTRACT_TARGET t) throws Exception {

		Account account = accounts.get(0);

		Fomo3dContract fomo3dLongContract = new Fomo3dContract(target, web3j,
				account.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT);
		account.setFomo3dContract(fomo3dLongContract);

		String leadingAddr;
		BigInteger endTime;

		if (t == CONTRACT_TARGET.FOMO3D || t == CONTRACT_TARGET.FOMOJP) {
			Tuple14<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, byte[], BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> result = fomo3dLongContract
					.getFomo3dCurrentRoundInfo().send();

			leadingAddr = result.getValue8();
			endTime = result.getValue4();

		} else {
			Tuple13<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, byte[], BigInteger, BigInteger, BigInteger, BigInteger> result = fomo3dLongContract
					.getLastWinnerCurrentRoundInfo().send();

			leadingAddr = result.getValue8();
			endTime = result.getValue4();

		}

		Tuple2<String, BigInteger> result = new Tuple2<String, BigInteger>(leadingAddr, endTime);

		log.info("target {} Current Leading Address:{} End Time:{}", target, leadingAddr, endTime);

		return result;
	}

	public String retryWithdraw(String target, int accountNumber) {

		Callable<Object> task = new Callable<Object>() {
			public String call() throws Exception {
				return withdraw(target, accountNumber);
			}
		};

		try {
			return (String) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String withdraw(String target, int accountNumber) throws IOException {
		Account account = accounts.get(accountNumber);

		Fomo3dContract fomo3dLongContract = new Fomo3dContract(target, web3j,
				account.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT);
		account.setFomo3dContract(fomo3dLongContract);

		setFomo3dGasProvider(retryCurrentGasPrice(), 0);

		String txhash = account.getWithdrawTransactionHash();
		log.info("target {} send the withdraw transaction:{}", target, txhash);

		// send the transaction
		account.getFomo3dContract().withdraw().sendAsync();

		return txhash;
	}

	// buy a key
	// 根据重试次数，增加交易手续费，每次都是上一次的两倍
	// 异步调用，并监控交易状态
	public String retryBuyKey(String target, int accountNumber, int retryCount) {

		Callable<Object> task = new Callable<Object>() {
			public String call() throws Exception {
				return buyKey(target, accountNumber, retryCount);
			}
		};

		try {
			return (String) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String buyKey(String target, int accountNumber, int retryCount) throws Exception {
		Account account = accounts.get(accountNumber);

		Fomo3dContract fomo3dLongContract = new Fomo3dContract(target, web3j,
				account.getTransactionManager(), Contract.GAS_PRICE, Contract.GAS_LIMIT);
		account.setFomo3dContract(fomo3dLongContract);

		setFomo3dGasProvider(retryCurrentGasPrice(), retryCount);

		// get the key price
		BigInteger keyPrice = getBuyPrice(target);

		// to ensure buy successfully, multiply price by 1.1
		BigDecimal buyPrice = Convert.fromWei(new BigDecimal(keyPrice), Unit.ETHER).multiply(new BigDecimal(1.01));
		BigInteger buyPriceWei = Convert.toWei(buyPrice, Unit.ETHER).toBigInteger();

		String affCode = SysConfig.ADDRESS;
		BigInteger team = BigInteger.valueOf(0);
		BigInteger weiValue = buyPriceWei;

		String txhash = account.getBuyXAddrTransactionHash(affCode, team, weiValue);
		log.info("target {} send the buyXAddr transaction:{}", target, txhash);

		// send the transaction
		account.getFomo3dContract().buyXaddr(affCode, team, buyPriceWei).sendAsync();

		return txhash;
	}

	public Boolean retryBuyKeyResult(List<String> hashes) {
		Callable<Object> task = new Callable<Object>() {
			public Boolean call() throws Exception {
				return getBuyKeyResult(hashes);
			}
		};

		try {
			return (Boolean) retryer.call(task);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RetryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 监控tx的状态
	private Boolean getBuyKeyResult(List<String> hashes) throws Exception {
		long start = System.currentTimeMillis();

		// 循环检查所有交易hash
		while (true) {
			// 时间太久，增加交易手续费，重发
			long curr = System.currentTimeMillis();
			long timeUsed = (curr - start) / 1000;
			log.info("time used: {} seconds", timeUsed);

			if (timeUsed >= SysConfig.RUSH_SECONDS / 3) {
				log.info("pending too long time {} seconds", timeUsed);
				return false;
			}

			// 遍历所有交易，查看是否有交易已经完成
			// 需要重新获取迭代器
			Iterator<String> iterator = hashes.iterator();
			while (iterator.hasNext()) {

				String txhash = iterator.next();

				// 交易hash出错，检查下一个
				if (txhash.length() != 66) {
					log.info("txhash format error");
					continue;
				}

				// 开始检查交易回执
				log.info("==========start check the transaction status {}==========", txhash);

				TransactionReceipt txReceipt = web3j.ethGetTransactionReceipt(txhash).send().getResult();
				if (txReceipt == null) {
					log.info("still wait for tx {} to complete", txhash);
				} else {
					// 得到
					BigInteger gasUsed = txReceipt.getGasUsed();
					String status = txReceipt.getStatus();

					long end = System.currentTimeMillis();
					long seconds = (end - start) / 1000;

					log.info("get the transaction receipt for: {} time used: {} seconds", txhash, seconds);
					if (status.compareToIgnoreCase("0x0") == 0) {
						log.info("buy key fail {}", txhash);
					} else {
						log.info("buy key success {}", txhash);
						log.info("gas used: {}", gasUsed.toString());
						log.info("time used: {} seconds", seconds);

						return true;
					}
				}
			}

			Thread.sleep(2000);
		}
	}
}
