package com.yangkun.fomo3d.airdrop.contract;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;


public class Fomo3dContract extends Fomo3d_sol_FoMo3Dlong implements IContract{
	
	public Fomo3dContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
			BigInteger gasLimit) {
		super(contractAddress, web3j, credentials, gasPrice, gasLimit);
		// TODO Auto-generated constructor stub
	}

	public Fomo3dContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
			BigInteger gasPrice, BigInteger gasLimit) {
		super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ContractGasProvider getGasProvider() {
		// TODO Auto-generated method stub
		return this.gasProvider;
	}
	
}
