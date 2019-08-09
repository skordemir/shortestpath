package com.havelsan.visgraph.sampler;

import java.util.Random;

/**
 * 
 * @author skordemir
 *
 */
public class RandomUtil {

	
	/**
	 * generates random Integer between the interval
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomInterval(int min, int max) {
		Random r = new Random();
		int randomInt = r.nextInt(max - min + 1) + min;
		return randomInt;

	}
}
