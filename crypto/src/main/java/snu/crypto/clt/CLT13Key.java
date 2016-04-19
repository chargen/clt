package snu.crypto.clt;

import java.math.BigInteger;
import java.util.Arrays;

import snu.crypto.utils.RandomGenerator;

public class CLT13Key {

	private CLT13Params params;

	private BigInteger x0;
	private BigInteger z;

	private BigInteger[] ps;
	private BigInteger[] gs;

	private BigInteger[][] zpowsModp;
	private BigInteger[][] zpowsModpInv;

	private BigInteger[] pzts;
	private BigInteger[] crtSummands;

	public CLT13Key(CLT13Params params) {
		this.params = params;
		int n = params.getN();
		int kappa = params.getKappa();

		ps = RandomGenerator.rndPrimes(params.getEta(), n);
		gs = RandomGenerator.rndPrimes(params.getAlpha(), n);

		x0 = BigInteger.ONE;
		for (int i = 0; i < n; i++) {
			x0 = x0.multiply(ps[i]);
		}

		z = RandomGenerator.rndPos(x0);
		BigInteger zpow = BigInteger.ONE;

		zpowsModp = new BigInteger[kappa + 1][n];
		zpowsModpInv = new BigInteger[kappa + 1][n];
		for (int i = 0; i < kappa; i++) {
			zpow = zpow.multiply(z).mod(x0);
			for (int j = 0; j < n; j++) {
				zpowsModp[i + 1][j] = zpow.mod(ps[j]);
				zpowsModpInv[i + 1][j] = zpowsModp[i + 1][j].modInverse(ps[j]);
			}
		}
		for (int i = 0; i < n; i++) {
			zpowsModp[0][i] = BigInteger.ONE;
			zpowsModpInv[0][i] = BigInteger.ONE;
		}

		BigInteger H[] = RandomGenerator.rnds(params.getBeta(), n * n);
		pzts = new BigInteger[n];

		crtSummands = new BigInteger[n];
		BigInteger pztSummands[] = new BigInteger[n];
		for (int i = 0; i < n; i++) {
			BigInteger x0Overp = x0.divide(ps[i]);
			crtSummands[i] = x0Overp.multiply(x0Overp.modInverse(ps[i]));
			pztSummands[i] = zpow.mod(ps[i]);
			pztSummands[i] = pztSummands[i].multiply(gs[i].modInverse(ps[i]))
					.mod(ps[i]);
			pztSummands[i] = pztSummands[i].multiply(x0Overp);
		}

		int idx = 0;
		for (int i = 0; i < n; i++) {
			pzts[i] = BigInteger.ZERO;
			for (int j = 0; j < n; j++) {
				pzts[i] = pzts[i]
						.add(H[idx++].multiply(pztSummands[j]).mod(x0)).mod(x0);
			}
		}
	}

	public BigInteger getPzt(int i) {
		return pzts[i];
	}

	public BigInteger getP(int i) {
		return ps[i];
	}

	public BigInteger getG(int i) {
		return gs[i];
	}

	public BigInteger[] getGs() {
		return gs;
	}

	public BigInteger getX0() {
		return x0;
	}

	public BigInteger getZpowModp(int level, int i) {
		return zpowsModp[level][i];
	}

	public BigInteger getZpowModpInv(int level, int i) {
		return zpowsModpInv[level][i];
	}

	public BigInteger getCrtSummand(int i) {
		return crtSummands[i];
	}

	public int getN() {
		return params.getN();
	}

	public int getLambda() {
		return params.getLambda();
	}

	public int getKappa() {
		return params.getKappa();
	}

	public int getEta() {
		return params.getEta();
	}

	public int getAlpha() {
		return params.getAlpha();
	}

	public int getBeta() {
		return params.getBeta();
	}

	public int getRho() {
		return params.getRho();
	}

	@Override
	public String toString() {
		return "\n CLT13Key: \n\n" + "[params=" + params + ", \n\n" + "x0="
				+ x0 + ", \n\n" + "z=" + z + ", \n\n" + "ps="
				+ Arrays.toString(ps) + ", \n\n" + "gs=" + Arrays.toString(gs)
				+ "] \n";
	}

}