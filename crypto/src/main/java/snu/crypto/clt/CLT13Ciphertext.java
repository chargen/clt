package snu.crypto.clt;

import java.math.BigInteger;

import snu.crypto.utils.RandomGenerator;

public class CLT13Ciphertext {

	private int level;
	private BigInteger cval;	
	
	public CLT13Ciphertext(int level, BigInteger cval) {
		this.level = level;
		this.cval = cval;
	}

	public CLT13Ciphertext(CLT13Key key, int level) {
		this.level = level;
		cval = BigInteger.ZERO;
		for (int i = 0; i < key.getN(); i++) {
			BigInteger msg = RandomGenerator.rndPos(key.getG(i));
			BigInteger error = RandomGenerator.rnd(key.getRho());
			BigInteger crtVal0 = msg.add(key.getG(i).multiply(error));
			BigInteger crtVal = crtVal0.multiply(key.getZpowModpInv(level, i)).mod(key.getP(i));
			cval = cval.add(crtVal.multiply(key.getCrtSummand(i)).mod(key.getX0()));
		}
	}
	
	public CLT13Ciphertext(CLT13Key key, int level, BigInteger[] msgs) {
		this.level = level;
		cval = BigInteger.ZERO;
		for (int i = 0; i < key.getN(); i++) {
			BigInteger msg = msgs[i];
			BigInteger error = RandomGenerator.rnd(key.getRho());
			BigInteger crtVal0 = msg.add(key.getG(i).multiply(error));
			BigInteger crtVal = crtVal0.multiply(key.getZpowModpInv(level, i)).mod(key.getP(i));
			cval = cval.add(crtVal.multiply(key.getCrtSummand(i)).mod(key.getX0()));
		}
	}
	
	public CLT13Ciphertext add(CLT13Ciphertext other, CLT13Key key) {
		int level = this.level;
		BigInteger cval = this.cval.add(other.cval).mod(key.getX0());	
		return new CLT13Ciphertext(level, cval);
	}
	
	public CLT13Ciphertext sub(CLT13Ciphertext other, CLT13Key key) {
		int level = this.level;
		BigInteger cval = this.cval.subtract(other.cval).mod(key.getX0());	
		return new CLT13Ciphertext(level, cval);
	}

	public CLT13Ciphertext mult(CLT13Ciphertext other, CLT13Key key) {
		int level = this.level + other.level;
		BigInteger cval = this.cval.multiply(other.cval).mod(key.getX0());	
		return new CLT13Ciphertext(level, cval);
	}

	public int getLevel() {
		return level;
	}

	public BigInteger getCval() {
		return cval;
	}
	
	
}