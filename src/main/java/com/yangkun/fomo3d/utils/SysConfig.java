package com.yangkun.fomo3d.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统的一些配置信息
 * 
 * @author tom
 *
 */
public class SysConfig {
	
//	public static String MYSQL_URL;
//	public static String MYSQL_USER;
//	public static String MYSQL_PASSWORD;

	public static String RPC_ADDRESS;

	public static String FOMO3D_CONTRACT_ADDRESS;
	public static String LAST_WINNER_CONTRACT_ADDRESS;
	public static String FOMOJP_CONTRACT_ADDRESS;
	public static String RICH3D_CONTRACT_ADDRESS;
	public static String FOMOWAR_CONTRACT_ADDRESS;
	
	public static String FOMO3D_MANAGE_CONTRACT_ADDRESS;

	public static String ADDRESS;
	public static String PASSWORD;
	public static String KEYSTORE;
	
	public static String ADDRESS1;
	public static String PASSWORD1;
	public static String KEYSTORE1;

	public static int RUSH_SECONDS;
	
	public static int GAS_PRICE;
	public static int GAS_LIMIT;
	public static int GAS_LIMIT_AIRDROP;

	static {
		init();
	}
	
	public static void init() {

		try {
			InputStream in = SysConfig.class.getClassLoader().getResourceAsStream("sysconfig.properties");
			Properties rb = new Properties();
			try {
				rb.load(in);
//				MYSQL_URL = rb.getProperty("MYSQL_URL");
//				MYSQL_USER = rb.getProperty("MYSQL_USER");
//				MYSQL_PASSWORD = rb.getProperty("MYSQL_PASSWORD");
				
				RPC_ADDRESS = rb.getProperty("RPC_ADDRESS");
				
				FOMO3D_CONTRACT_ADDRESS = rb.getProperty("FOMO3D_CONTRACT_ADDRESS");
				LAST_WINNER_CONTRACT_ADDRESS = rb.getProperty("LAST_WINNER_CONTRACT_ADDRESS");
				FOMOJP_CONTRACT_ADDRESS = rb.getProperty("FOMOJP_CONTRACT_ADDRESS");
				RICH3D_CONTRACT_ADDRESS = rb.getProperty("RICH3D_CONTRACT_ADDRESS");
				FOMOWAR_CONTRACT_ADDRESS = rb.getProperty("FOMOWAR_CONTRACT_ADDRESS");
				
				FOMO3D_MANAGE_CONTRACT_ADDRESS = rb.getProperty("FOMO3D_MANAGE_CONTRACT_ADDRESS");
				
				ADDRESS = rb.getProperty("ADDRESS");
				PASSWORD = rb.getProperty("PASSWORD");
				KEYSTORE = rb.getProperty("KEYSTORE");
				
				ADDRESS1 = rb.getProperty("ADDRESS1");
				PASSWORD1 = rb.getProperty("PASSWORD1");
				KEYSTORE1 = rb.getProperty("KEYSTORE1");
				
				RUSH_SECONDS = Integer.parseInt(rb.getProperty("RUSH_SECONDS"));

				GAS_PRICE = Integer.parseInt(rb.getProperty("GAS_PRICE"));
				GAS_LIMIT = Integer.parseInt(rb.getProperty("GAS_LIMIT"));
				GAS_LIMIT_AIRDROP = Integer.parseInt(rb.getProperty("GAS_LIMIT_AIRDROP"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Call ResourceBundle Error: " + e.getMessage());
		}
	}
}
