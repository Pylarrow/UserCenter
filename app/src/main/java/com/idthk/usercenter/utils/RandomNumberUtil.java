package com.idthk.usercenter.utils;

import java.util.Random;

public class RandomNumberUtil {

	private final static String NUM_CHAR = "0123456789";
	private static int charLen = NUM_CHAR.length();

	/**
	 * 根据系统时间获得指定位数的随机数
	 * 
	 * @param randomNumberDigit
	 *            随机数的位数
	 * @return 获得的随机数
	 */
	public static String getRandomNumber(int randomNumberDigit) {
		long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子
		StringBuffer sb = new StringBuffer();// 装载生成的随机数
		Random random = new Random(seed);// 调用种子生成随机数
		for (int i = 0; i < randomNumberDigit; i++) {
			sb.append(NUM_CHAR.charAt(random.nextInt(charLen)));
		}

		return sb.toString();
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			System.out.println(getRandomNumber(4));
			Thread.sleep(50);
		}
	}

}
