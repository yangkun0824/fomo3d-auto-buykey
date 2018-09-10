package com.yangkun.fomo3d.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

public class TUtil {
	
	/**
	 * 除法获取count位小数
	 */
	public static BigDecimal fixedCount(double num, int count) {
		BigDecimal total = new BigDecimal(0);
		if (num==0) {
			return total;
		}else {
			BigDecimal decimal = new BigDecimal(num);
	        total = decimal.setScale(count,BigDecimal.ROUND_HALF_UP);
			return total;
		}
	}
	
	/**
	 * 判断字符串中是否包含数字
	 */
	public static boolean isContainNum(String content) {
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		return m.matches();
	}
	
	/**
	 * 取出含有数字的字符串中的数字
	 */
	public static String getNum(String content) {
		String regEx="[^0-9]";  
		Pattern p = Pattern.compile(regEx);  
		Matcher m = p.matcher(content);  
		return m.replaceAll("").trim();
	}
	
	/**
	 * 得到日期的字符串
	 */
	public static String toDateStr(long time){
		Date date =new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
	public static String toDateStr2(long time){
		Date date =new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd#HH:mm:ss");
		String s=formatter.format(date);
		s=s.replace("#", "<br/>");
		return s;
	}
	/**
	 * 精确到分
	 */
	public static String toDateStr3(long time){
		if (time == 0) {
			return "";
		}
		Date date =new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(date);
	}
	/**
	 * 精确到月
	 */
	public static String toDateStr4(long time){
		if (time == 0) {
			return "";
		}
		Date date =new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		return formatter.format(date);
	}
	/**
	 * 精确到天
	 */
	public static String toDateStr5(long time){
		if (time == 0) {
			return "";
		}
		Date date =new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	/**
	 * 精确到年
	 */
	public static String toDateStr6(long time){
		if (time == 0) {
			return "";
		}
		Date date =new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(date);
	}

	/**
	 * 小数点后两位
	 */
	public static String pointTwo(long num) {
		return new BigDecimal(num).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toString();
	}
	public static String pointTwo(int num) {
		return new BigDecimal(num).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 将日期字符系统时间的毫秒
	 * yyyy-MM-dd or
	 * yyyy-MM-dd-HH
	 */
	public static long str2LongTime(String str){
		SimpleDateFormat formatter;
		if(str==null)
			return Long.MIN_VALUE;
		if(str.length()>"yyyy-MM-dd".length())
			formatter= new SimpleDateFormat("yyyy-MM-dd-HH");
		else
			formatter= new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(str).getTime();
		} catch (ParseException e) {
			return Long.MIN_VALUE;
		}
	}
	
	/**
	 * 截取掉时间戳后三位
	 */
	public static long interceptLongTime(long time) {
		if (time == 0) {
			return 0;
		}
		return time/1000l;
	}
	
	/**
	 * 今天凌晨的成绩
	 * @return
	 */
	public static String todayTime(){
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date(System.currentTimeMillis());
		return formatter.format(d);
	}
	public static int Long2Int(long l){
		if(l<Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
		if(l>Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		return (int)l;
	}
	/***
	 * 将字符串<b>解码</b>成指定格式
	 * 
	 * @param str
	 *            编码成指定格式的字符串
	 * @param coder
	 * @return
	 */
	public static String decodeUTF8(String str) {
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将字符串<b>编码</b>成指定格式编码
	 * 
	 * @param str
	 * @param coder
	 *            null表示默认utf-8
	 * @return
	 */
	public static String encoderUTF8(String str) {
		if(str==null)
			return "";
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	


	/***
	 * 将字符串<b>解码</b>成指定格式
	 * 
	 * @param str
	 *            编码成指定格式的字符串
	 * @param coder
	 * @return
	 */
	public static String decode(String str, String coder) {
		try {
			return URLDecoder.decode(str, coder);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将字符串<b>编码</b>成指定格式编码
	 * 
	 * @param str
	 * @param coder
	 *            null表示默认utf-8
	 * @return
	 */
	public static String encoder(String str, String coder) {
		try {
			return URLEncoder.encode(str, coder);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}


	public static long toLong(String value) {
		if (value == null || value.length() < 1)
			return -1;
		long t = -1;
		try {
			t = Long.parseLong(value);
		} catch (Exception e) {
			t = -1;
		}
		return t;
	}

	public static int toInt(String value) {
		if (value == null || value.length() < 1)
			return -1;
		int t = -1;
		try {
			t = Integer.parseInt(value);
		} catch (Exception e) {
			t = -1;
		}
		return t;
	}

	private static Random random = new Random();

	/**
	 * 得到对应的字数组
	 */
	public static int[] getSubArray(int[] arr, int start, int size) {
		int[] r = new int[size];
		System.arraycopy(arr, start, r, 0, size);
		return r;
	}

	/**
	 * 得到当前的小时数
	 */
	public static int getCurrentHour() {
		long d = System.currentTimeMillis();
		return (int) (d / 3600000 + 8) % 24;
	}
	/**
	 * 得到当前的天数
	 */
	public static int getCurrentDay() {
		long d = System.currentTimeMillis();
		return  (int)((d / 3600000 + 8) /24);
	}
	public static int getTheDay(long time) {
		return  (int)((time /( 3600000) + 8) /24);
	}
/**
 * 获得一天的毫秒数
 * @return
 */
	public static long getOneDayTime(){
		return 1000L*3600*24;
	}
	
	/**
	 * 得到一个随机数
	 */
	public static int randInt(int max) {
		return random.nextInt(max);
	}

	/**
	 * 得到一定范围内的随机数 取值为[start,end]的随机数
	 */
	public static int randInt(int start, int end) {
		
		return random.nextInt(end - start + 1) + start;

	}

	/**
	 * 求正太分布
	 * 
	 * @param avg
	 *            均值
	 * @param sgma
	 *            方差
	 * @return
	 */
	public static long getGaussian(double avg, double sgma, long max, long min) {
		long value = 0;
		double r = Math.random() * 9;
		switch ((int) r / 3) {
		case 0:
			value = normalRandom1(avg, sgma);
			break;
		case 1:
			value = normalRandom2(avg, sgma);
			break;
		case 2:
			value = normalRandom3(avg, sgma);
			break;
		}

		if (value <= min || value >= max) {
			value = TUtil.randInt((int) min, (int) max);
		}

		return value;
	}

	private static long normalRandom1(double avg, double sgma) {
		int temp = 12;
		double x = 0;
		for (int i = 0; i < temp; i++) {
			x = x + Math.random();
		}
		x = (x - temp / 2) / Math.sqrt(temp / 12);
		x = avg + x * Math.sqrt(sgma);
		return (long) x;
	}

	private static long normalRandom2(double avg, double sgma) {
		double pi = 3.1415926535;
		double r1 = Math.random();
		Math.random();
		Math.random();
		Math.random();
		Math.random();
		Math.random();
		Math.random();
		Math.random();
		double r2 = Math.random();
		double u = Math.sqrt((-2) * Math.log(r1)) * Math.cos(2 * pi * r2);
		double z = avg + u * Math.sqrt(sgma);
		return (long) z;
	}

	private static long normalRandom3(double avg, double sgma) {
		double f = 0;
		double c0 = 2.515517, c1 = 0.802853, c2 = 0.010328;
		double d1 = 1.432788, d2 = 0.189269, d3 = 0.001308;
		double w;
		double r = Math.random();
		if (r <= 0.5)
			w = r;
		else
			w = 1 - r;
		if ((r - 0.5) > 0)
			f = 1;
		else if ((r - 0.5) < 0)
			f = -1;
		double y = Math.sqrt((-2) * Math.log(w));
		double x = f
				* (y - (c0 + c1 * y + c2 * y * y)
						/ (1 + d1 * y + d2 * y * y + d3 * y * y * y));
		double z = avg + x * Math.sqrt(sgma);
		return (long) z;
	}

	public static int daysBetween(long t1, long t2) {
		t1 = (t1 / (3600 * 1000) + 8) / 24;
		t2 = (t2 / (3600 * 1000) + 8) / 24;
		return (int) Math.abs(t1 - t2);
	}

	/**
	 * 强制转换成long
	 * 
	 * @param param
	 * @return
	 */
	public static long getLongValue(String param) {
		if (param == null || param.length() < 1)
			return Long.MIN_VALUE;
		try {
			return Long.parseLong(param);
		} catch (Exception e) {
			return Long.MIN_VALUE;
		}
	}

	/**
	 * 强制转换成int
	 * 
	 * @param param
	 * @return
	 */
	public static int getIntValue(String param) {
		if (param == null || param.length() < 1)
			return Integer.MIN_VALUE;
		try {
			return Integer.parseInt(param);
		} catch (Exception e) {
			return Integer.MIN_VALUE;
		}
	}
	
	/**
	 * 字符串是否为空或者为null则返回TRUE 否则返回FALSE
	 * @param reString
	 * @return
	 */
	public static boolean isNullOrNot(String reString){
		if (reString==null||(reString!=null&&reString.length()<1)) 
			return true;
		return false;
	}
	
	public static boolean ContainNullOrNot(String...reString ){
		for (String string : reString) {
			if (isNullOrNot(string)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 从给定的JSon中获取指定整数参数
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static Integer getJsonIntegerValue(String key,JSONObject jsonObject){
		if (jsonObject==null) 
			return null;
		if (jsonObject.containsKey(key)) 
			return jsonObject.getInteger(key);
		return null;
	}
	/**
	 * 从给定的JSon中获取指定长整形参数
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static Long getJsonLongValue(String key,JSONObject jsonObject){
		if (jsonObject==null) 
			return null;
		if (jsonObject.containsKey(key)) 
			return jsonObject.getLong(key);
		return null;
	}
	/**
	 * 从给定的JSon中获取指定字符串参数
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static String getJsonStringValue(String key,JSONObject jsonObject){
		if (jsonObject==null) 
			return null;
		if (jsonObject.containsKey(key)) 
			return jsonObject.getString(key);
		return null;
	}
	
    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
	public static String CreateNoncestr(int length) {
		char[] arr=new char[length];
		for (int i = 0; i < length; i++) {
			arr[i]=(char)('a'+randInt(25));
		}
		return new String(arr);
	}
	/**
	 * 一个简单的方式获取xml的信息
	 * 只会返回找到的第一个标签
	 * @param xmlstr xml字符串
	 * @param node 需要查找的标签，需要包含<>
	 * @return
	 */
	public static String getXMLValue(String xmlstr,String node){
		if(xmlstr==null)
			return null;
		int index=xmlstr.indexOf(node);
		if(index<0)
			return null;
		index+=node.length();
		int end=xmlstr.indexOf(node.substring(1), index);
		if(end<index)
			return null;
		return xmlstr.substring(index, end-2);
	}
    
    public static String ifNullGetEmpty(String meString){
    	if (isNullOrNot(meString)) {
			return "";
		}
    	return meString;
    }
	
    /**
     * 计算年复合增长率
     * @param beginFunds  起始资金
     * @param finalFunds  最终资金
     * @param years  年数
     * @return
     */
    public static String getCAGR(double beginFunds, double finalFunds, int years) {
    	if(beginFunds != 0 && finalFunds != 0 && years != 0 && finalFunds > beginFunds) {
    		double res = (Math.pow(finalFunds/beginFunds, 1.0/years)-1)*100;
    		BigDecimal result = fixedCount(res,2);
    		return result + "%";
    	}else {
    		return "0%";
		}
    }
}
