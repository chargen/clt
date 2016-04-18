package snu.crypto.clt;

import java.math.BigInteger;

public class CLT13Scheme {

	CLT13Params params;
	CLT13Key key;
	
	public CLT13Ciphertext encrypt(int level, BigInteger[] msgs) {
		return new CLT13Ciphertext(key, level, msgs);
	}
	
	public BigInteger[] decrypt(CLT13Ciphertext cipher) {
		BigInteger cval = cipher.getCval();
		BigInteger[] msgs = new BigInteger[key.getN()];
		
		for (int i = 0; i < msgs.length; i++) {
			BigInteger crtVal = cval.mod(key.getP(i));
			BigInteger crtVal0 = crtVal.multiply(key.getZpowModp(cipher.getLevel()-1, i)).mod(key.getP(i));
			msgs[i] = crtVal0.remainder(key.getG(i));
		}
		
		return msgs;
	}
		
	public boolean zeroTesting() {
		return false;
	}
	
}
