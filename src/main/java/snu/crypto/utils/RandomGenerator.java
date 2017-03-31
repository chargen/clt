package snu.crypto.utils;

import java.math.BigInteger;
import java.util.Random;

public class RandomGenerator {

	public static final Random rnd = new Random();
	
	public static BigInteger rnd(int bits) {
		BigInteger res = new BigInteger(bits, rnd);
		return rnd.nextBoolean() ? res : res.negate();
	}
	
	public static BigInteger[] rnds(int bits, int size) {
		BigInteger res[] = new BigInteger[size];
		for (int i = 0; i < size; i++) {
			res[i] = rnd(bits);
		}
		return res;
	}
	
	public static BigInteger rndPos(BigInteger bnd) {
		BigInteger res;
		do {
			res = new BigInteger(bnd.bitLength(), rnd);			
		} while (res.compareTo(bnd) < 0);
		return res;
	}
	
	public static BigInteger rndPrime(int bits) {
		return BigInteger.probablePrime(bits, rnd);
	}
	
	public static BigInteger[] rndPrimes(int bits, int size) {
		BigInteger res[] = new BigInteger[size];
		for (int i = 0; i < size; i++) {
			res[i] = BigInteger.probablePrime(bits, rnd);
		}
		return res;
	}
}