package snu.crypto.clt;

import java.math.BigInteger;
import java.util.Arrays;

import org.apache.log4j.Logger;

import snu.crypto.lll.LLL;

public class CLT13Main {

	private static Logger log = Logger.getLogger(CLT13Main.class);

	public static BigInteger TWO = BigInteger.valueOf(2);
	
	public static void main(String[] args) {
		int n = 5;
		int lambda = 10;
		int kappa = 10;
		int rho = 10;
		int alpha = 10;
		int beta = 10;
		int eta = 300;
		
		int delta = 1460;
		int dimPzt = 4;
		int dim = 10;
		
		BigInteger TWOToDELTA = TWO.pow(delta);	
		BigInteger[] zeros = new BigInteger[n];
		for (int i = 0; i < zeros.length; i++) {
			zeros[i] = BigInteger.ZERO;
		}
		
		CLT13Params params = new CLT13Params(n, lambda, kappa, eta, alpha, beta, rho);
		CLT13Key key = new CLT13Key(params);
		CLT13Scheme scheme = new CLT13Scheme(key);
		
		log.info(key);
		
		CLT13Ciphertext[] ciphers = new CLT13Ciphertext[dim];
		for (int i = 0; i < ciphers.length; i++) {
			ciphers[i] = scheme.encrypt(kappa);
			log.info(ciphers[i]);
		}
				
		BigInteger[][] matrixForLLL = new BigInteger[dim+dimPzt][dim+dimPzt];
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				matrixForLLL[i][j] = i == j ? TWOToDELTA : BigInteger.ZERO;
			}
		}
		
		BigInteger x0 = key.getX0();
		for (int i = 0; i < dimPzt; i++) {
			BigInteger pzti = key.getPzt(i);
			for (int j = 0; j < dim; j++) {
				matrixForLLL[dim + i][j] = BigInteger.ZERO;
				matrixForLLL[j][dim + i] = ciphers[j].getCval().multiply(pzti).mod(x0);				
			}
			for (int j = 0; j < dimPzt; j++) {
				matrixForLLL[dim + i][dim + j] = i == j ? x0 : BigInteger.ZERO;
			}
		}

		LLL.integral_LLL(matrixForLLL, dim + dimPzt, dim + dimPzt);
		
		for (int row = 0; row < dim + dimPzt; row++) {
			for (int col = 0; col < dimPzt; col++) {
				log.info("Step: " + row + " " + col);
				BigInteger pztcol = key.getPzt(col);
				BigInteger sumv = matrixForLLL[row][dim + col];
				
				BigInteger sumx = sumv.multiply(pztcol.modInverse(x0)).mod(x0);
				log.info("sumv:  " + sumv.bitLength() + "  " + sumv);
				log.info("X0:    " + x0.bitLength() + "  " + x0);
				log.info("sumx:  " + sumx.bitLength() + "  " + sumx);
				
				CLT13Ciphertext sumCipher = new CLT13Ciphertext(kappa, sumx);
				
				BigInteger[] sumMsg = scheme.decrypt(sumCipher);
				
				log.info("MSG: " + Arrays.toString(sumMsg));
				
				BigInteger[] gs = key.getGs();
				
				log.info("Gs:  " + Arrays.toString(gs));
			}
		}				
	}
}
