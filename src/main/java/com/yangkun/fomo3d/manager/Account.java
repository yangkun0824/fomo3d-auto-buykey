package com.yangkun.fomo3d.manager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionUtils;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.tx.ChainId;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import com.yangkun.fomo3d.airdrop.contract.Fomo3dAirdropContract;
import com.yangkun.fomo3d.airdrop.contract.Fomo3dContract;

public class Account {

	private Web3j web3j;

	private String password;

	private String keystore;

	private Credentials credentials;

	private TransactionManager transactionManager;

	private Fomo3dAirdropContract airDropContract;

	private Fomo3dContract fomo3dContract;

	public Account(Web3j web3j, String password, String keystore) throws IOException, CipherException {
		super();
		this.web3j = web3j;
		this.password = password;
		this.keystore = keystore;

		this.credentials = WalletUtils.loadCredentials(password, keystore);
		this.transactionManager = new RawTransactionManager(web3j, credentials, ChainId.MAINNET);
	}

	public Account(Web3j web3j, Credentials credentials) {
		super();
		this.web3j = web3j;
		this.credentials = credentials;
		this.transactionManager = new RawTransactionManager(web3j, credentials, ChainId.MAINNET);
	}

	public Web3j getWeb3j() {
		return web3j;
	}

	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public Fomo3dAirdropContract getAirDropContract() {
		return airDropContract;
	}

	public void setAirDropContract(Fomo3dAirdropContract airDropContract) {
		this.airDropContract = airDropContract;
	}

	public Fomo3dContract getFomo3dContract() {
		return fomo3dContract;
	}

	public void setFomo3dContract(Fomo3dContract fomo3dContract) {
		this.fomo3dContract = fomo3dContract;
	}

	public BigInteger getNonce() throws IOException {
		EthGetTransactionCount ethGetTransactionCount = web3j
				.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

		return ethGetTransactionCount.getTransactionCount();
	}

	public String getCheckAndAttackTransactionHash(String target, BigInteger weiValue) throws IOException {
		final Function function = new Function(airDropContract.FUNC_CHECKANDATTACK,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(target)),
				Collections.<TypeReference<?>>emptyList());

		String contractAddress = airDropContract.getContractAddress();
		String data = FunctionEncoder.encode(function);

		BigInteger gasLimit = airDropContract.getGasProvider().getGasLimit(function.getName());
		BigInteger gasPrice = airDropContract.getGasProvider().getGasPrice(function.getName());
		BigInteger nonce = this.getNonce();

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
				weiValue, data);

		return TransactionUtils.generateTransactionHashHexEncoded(rawTransaction, ChainId.MAINNET, credentials);
	}
	
	public String getWithdrawTransactionHash() throws IOException {
		final Function function = new Function(
				fomo3dContract.FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
		
		String contractAddress = fomo3dContract.getContractAddress();
		String data = FunctionEncoder.encode(function);

		BigInteger gasLimit = fomo3dContract.getGasProvider().getGasLimit(function.getName());
		BigInteger gasPrice = fomo3dContract.getGasProvider().getGasPrice(function.getName());
		BigInteger nonce = this.getNonce();

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, data);

		return TransactionUtils.generateTransactionHashHexEncoded(rawTransaction, ChainId.MAINNET, credentials);
	}

	public String getBuyXAddrTransactionHash(String _affCode, BigInteger _team, BigInteger weiValue)
			throws IOException {

		final Function function = new Function(fomo3dContract.FUNC_BUYXADDR,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_affCode),
						new org.web3j.abi.datatypes.generated.Uint256(_team)),
				Collections.<TypeReference<?>>emptyList());

		String contractAddress = fomo3dContract.getContractAddress();
		String data = FunctionEncoder.encode(function);

		BigInteger gasLimit = fomo3dContract.getGasProvider().getGasLimit(function.getName());
		BigInteger gasPrice = fomo3dContract.getGasProvider().getGasPrice(function.getName());
		BigInteger nonce = this.getNonce();

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress,
				weiValue, data);

		return TransactionUtils.generateTransactionHashHexEncoded(rawTransaction, ChainId.MAINNET, credentials);
	}
}
