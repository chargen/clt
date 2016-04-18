package snu.crypto.clt;

import java.math.BigInteger;

import snu.crypto.lll.LLL;

public class CLT13Main {

	
	public static void main(String[] args) {
		int n = 100;
		int lambda = 26;
		int kappa = 3;
		int rho = 26;
		int alpha = 26;
		int beta = 26;
		int eta = 450;
		
		int delta = 40;
		
		BigInteger TWO = BigInteger.valueOf(2);
		
		BigInteger TWOToDELTA = TWO.pow(delta);

		BigInteger[] zeros = new BigInteger[n];
		for (int i = 0; i < zeros.length; i++) {
			zeros[i] = BigInteger.ZERO;
		}
		
		
		
		CLT13Params params = new CLT13Params(n, lambda, kappa, eta, alpha, beta, rho);
		CLT13Key key = new CLT13Key(params);
		CLT13Scheme scheme = new CLT13Scheme(key);
		
		CLT13Ciphertext[] ciphers = new CLT13Ciphertext[n];
		for (int i = 0; i < ciphers.length; i++) {
			ciphers[i] = scheme.encrypt(kappa-1);
		}
		
		BigInteger[][] matrixForLLL = new BigInteger[n][n+1];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrixForLLL[i][j] = i == j ? TWOToDELTA : BigInteger.ZERO;
			}
		}
		
		for (int i = 0; i < n; i++) {
			matrixForLLL[i][n] = ciphers[i].getCval();
		}
		
		LLL.integral_LLL(matrixForLLL, n, n+1);
		
		System.out.println(matrixForLLL[n-1][n]);
	}
}
