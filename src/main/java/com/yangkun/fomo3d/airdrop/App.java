package com.yangkun.fomo3d.airdrop;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.yangkun.fomo3d.manager.Fomo3dContractManager;
import com.yangkun.fomo3d.utils.SysConfig;

/**
 * Hello world!
 *
 */
public class App {
	private static Logger log = LoggerFactory.getLogger("aLog");

	public static enum CONTRACT_TARGET {
		FOMO3D, LASTWINNER, FOMOJP, RICH3D, FOMOWAR
	}

	public static String getContractAddressForTarget(CONTRACT_TARGET t) {
		switch (t) {
		case FOMO3D:
			return SysConfig.FOMO3D_CONTRACT_ADDRESS;
		case LASTWINNER:
			return SysConfig.LAST_WINNER_CONTRACT_ADDRESS;
		case FOMOJP:
			return SysConfig.FOMOJP_CONTRACT_ADDRESS;
		case RICH3D:
			return SysConfig.RICH3D_CONTRACT_ADDRESS;
		case FOMOWAR:
			return SysConfig.FOMOWAR_CONTRACT_ADDRESS;

		default:
			break;
		}
		return null;
	}

	public static int getMultipleForTarget(CONTRACT_TARGET t) {
		switch (t) {
		case FOMO3D:
		case FOMOJP:
		case RICH3D:
		case FOMOWAR:
			return 4;
		case LASTWINNER:
			return 10;
		default:
			break;
		}
		return 0;
	}

	public static int getBuyKeyAccountNumber(CONTRACT_TARGET t) {
		switch (t) {
		case FOMO3D:
		case FOMOJP:
		case RICH3D:
		case FOMOWAR:
			return 0;
		case LASTWINNER:
			return 1;
		default:
			break;
		}
		return 0;
	}

	public static ArrayList<String> buyKeyTxHashes = new ArrayList<String>();

	public static void main(String[] args) throws IOException, CipherException {
		String targetString = args[0];
		startAttack(targetString);
	}

	public static void startAttack(String targetString) {
		try {
			if (targetString.equalsIgnoreCase("fomo3d")) {

				////////////////////////////////////////////////////////////////
				// FOMO3D
				////////////////////////////////////////////////////////////////

				// 空投攻击fomo3d
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub
				// try {
				// startAirDropAttack(CONTRACT_TARGET.FOMO3D);
				// } catch (IOException | CipherException | InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// }).start();

				// 抢key
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							startBuyKeyLastKey(CONTRACT_TARGET.FOMO3D);
						} catch (IOException | CipherException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}

			else if (targetString.equalsIgnoreCase("lastwinner")) {

				////////////////////////////////////////////////////////////////
				// LAST WINNER
				////////////////////////////////////////////////////////////////

				// 空投攻击lastwinner
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub
				// try {
				// startAirDropAttack(CONTRACT_TARGET.LASTWINNER);
				// } catch (IOException | CipherException | InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// }).start();

				// 抢key
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							startBuyKeyLastKey(CONTRACT_TARGET.LASTWINNER);
						} catch (IOException | CipherException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}

			else if (targetString.equalsIgnoreCase("fomojp")) {

				////////////////////////////////////////////////////////////////
				// FOMOJP
				////////////////////////////////////////////////////////////////

				// 空投攻击FOMOJP
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub
				// try {
				// startAirDropAttack(CONTRACT_TARGET.FOMOJP);
				// } catch (IOException | CipherException | InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// }).start();

				// 抢key
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							startBuyKeyLastKey(CONTRACT_TARGET.FOMOJP);
						} catch (IOException | CipherException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}

			else if (targetString.equalsIgnoreCase("rich3d")) {

				////////////////////////////////////////////////////////////////
				// RICH3D
				////////////////////////////////////////////////////////////////

				// 空投攻击RICH3D
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub
				// try {
				// startAirDropAttack(CONTRACT_TARGET.RICH3D);
				// } catch (IOException | CipherException | InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// }).start();

				// 抢key
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							startBuyKeyLastKey(CONTRACT_TARGET.RICH3D);
						} catch (IOException | CipherException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
			
			else if (targetString.equalsIgnoreCase("fomowar")) {

				////////////////////////////////////////////////////////////////
				// FOMOWAR
				////////////////////////////////////////////////////////////////

				// 空投攻击FOMOWAR
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub
				// try {
				// startAirDropAttack(CONTRACT_TARGET.FOMOWAR);
				// } catch (IOException | CipherException | InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// }).start();

				// 抢key
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							startBuyKeyLastKey(CONTRACT_TARGET.FOMOWAR);
						} catch (IOException | CipherException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void withdraw(CONTRACT_TARGET t) throws IOException, CipherException {
		if (t == CONTRACT_TARGET.FOMO3D) {
			log.info("========== 提现Fomo3d ==========");
		} else if (t == CONTRACT_TARGET.LASTWINNER) {
			log.info("========== 提现LastWinner ==========");
		} else if (t == CONTRACT_TARGET.FOMOJP) {
			log.info("========== 提现FOMOJP ==========");
		} else if (t == CONTRACT_TARGET.RICH3D) {
			log.info("========== 提现RICH3D ==========");
		} else if (t == CONTRACT_TARGET.FOMOWAR) {
			log.info("========== 提现FOMOWAR ==========");
		}

		String target = getContractAddressForTarget(t);
		int accountNumber = getBuyKeyAccountNumber(t);

		Fomo3dContractManager.getInstance().retryWithdraw(target, accountNumber);
	}

	public static void startAirDropAttack(CONTRACT_TARGET t) throws IOException, CipherException, InterruptedException {
		if (t == CONTRACT_TARGET.FOMO3D) {
			log.info("========== 开始空投攻击Fomo3d ==========");
		} else if (t == CONTRACT_TARGET.LASTWINNER) {
			log.info("========== 开始空投攻击LastWinner ==========");
		} else if (t == CONTRACT_TARGET.FOMOJP) {
			log.info("========== 开始空投攻击FOMOJP ==========");
		}
		String target = getContractAddressForTarget(t);
		int multiple = getMultipleForTarget(t);

		int count = 0;
		while (true) {
			count++;

			log.info("=============开始第 {} 次检查================", count);

			log.info("<<<<<<<<<< Step 1 检查合约空投余额");
			// 检查奖池
			BigInteger airDropPot = Fomo3dContractManager.getInstance().retryAirDropPot(target);
			BigDecimal weiDecimal = Convert.toWei("0.101", Unit.ETHER);
			BigDecimal minPotDecimal = weiDecimal.multiply(BigDecimal.valueOf(multiple));

			if (airDropPot.compareTo(minPotDecimal.toBigInteger()) < 0) {
				log.info("合约{} 奖池金额不足: 当前:{}  最低:{}", target, airDropPot, minPotDecimal.toBigInteger());

				Thread.sleep(10000);
				continue;
			}

			log.info("<<<<<<<<<< Step 2 检查合约空投概率");
			// 检查概率，概率太小放弃
			BigInteger airDropTracker = Fomo3dContractManager.getInstance().retryAirDropTracker(target);
			if (airDropTracker.compareTo(BigInteger.valueOf(10)) < 0) {
				log.info("合约{} 中奖概率太低:{}", target, airDropTracker);

				Thread.sleep(10000);
				continue;
			}

			log.info("<<<<<<<<<< Step 3 满足条件，开始合约攻击");

			Fomo3dContractManager.getInstance().retryAttack(target, multiple);

			log.info("<<<<<<<<<< Step 4 攻击完毕");

			Thread.sleep(10000);
		}
	}

	public static void startBuyKeyLastKey(CONTRACT_TARGET t) throws IOException, CipherException, InterruptedException {
		if (t == CONTRACT_TARGET.FOMO3D) {
			log.info("========== 开始抢Key Fomo3d ==========");
		} else if (t == CONTRACT_TARGET.LASTWINNER) {
			log.info("========== 开始抢Key LastWinner ==========");
		} else if (t == CONTRACT_TARGET.FOMOJP) {
			log.info("========== 开始抢Key FOMOJP ==========");
		} else if (t == CONTRACT_TARGET.RICH3D) {
			log.info("========== 开始抢Key RICH3D ==========");
		} else if (t == CONTRACT_TARGET.FOMOWAR) {
			log.info("========== 开始抢Key FOMOWAR ==========");
		}

		int count = 0;
		int retryCount = 0;
		
		while (true) {
			count++;
			log.info("=============开始第 {} 次检查================", count);

			String target = getContractAddressForTarget(t);
			log.info("合约: {}", target);

			log.info("<<<<<<<<<< Step 1 查看剩余时间和当前KEY持有者");
			Tuple2<String, BigInteger> res = Fomo3dContractManager.getInstance().retryCurrentLeaderAndEndTime(target,
					t);
			String leadingAddr = res.getValue1();
			BigInteger timeEnd = res.getValue2();
			long timeLeft = timeEnd.longValue() - (System.currentTimeMillis() / 1000);
			String myAddr = t == CONTRACT_TARGET.LASTWINNER ? SysConfig.ADDRESS1 : SysConfig.ADDRESS;

			log.info("离结束还有 {} 秒", timeLeft);
			log.info("持有地址 {}", leadingAddr);
			log.info("我的地址 {}", myAddr);
			
			// 限制重试次数
			if( retryCount >= 3 ) {
				retryCount = 0;
			}
			
			if (timeLeft > SysConfig.RUSH_SECONDS) {
				log.info("<<<<<<<<<< 时间足够，离结束还有 {} 秒", timeLeft);
				retryCount=0;
			} else if (leadingAddr.compareToIgnoreCase(myAddr) == 0) {
				log.info("<<<<<<<<<< 恭喜你持有最后一个key,持有地址 {} ", myAddr);
				retryCount=0;
			} else {

				log.info("<<<<<<<<<< Step 2 开始抢KEY");
				
				log.info("buy the {} time", retryCount);
				boolean txResult = buyKey(t, retryCount);

				if (txResult) {
					// 购买成功，立即开始下一轮检测，并开始合约攻击
					buyKeyTxHashes.clear();
					retryCount=0;
				} else {
					retryCount++;
				}
			}
			
			// 重试为0 暂停5秒
			if( retryCount == 0 ) {
				Thread.sleep(5000);
			}
		}
	}

	public static boolean buyKey(CONTRACT_TARGET t, int retryCount) throws IOException, CipherException {
		String target = getContractAddressForTarget(t);
		int accountNumber = getBuyKeyAccountNumber(t);

		String txhash = Fomo3dContractManager.getInstance().retryBuyKey(target, accountNumber, retryCount);
		buyKeyTxHashes.add(txhash);
		boolean txResult = Fomo3dContractManager.getInstance().retryBuyKeyResult(buyKeyTxHashes);

		return txResult;
	}
}
