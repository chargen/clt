package snu.crypto.clt;

public class CLT13Params {

	private int n;
	private int lambda;
	private int kappa;
	private int eta;
	private int alpha;
	private int beta;
	private int rho;
		
	public CLT13Params(int n, int lambda, int kappa, int eta, int alpha, int beta, int rho) {
		this.n = n;
		this.lambda = lambda;
		this.kappa = kappa;
		this.eta = eta;
		this.alpha = alpha;
		this.beta = beta;
		this.rho = rho;
	}
	
	public int getN() {
		return n;
	}
	public int getLambda() {
		return lambda;
	}
	public int getKappa() {
		return kappa;
	}
	public int getEta() {
		return eta;
	}
	public int getAlpha() {
		return alpha;
	}
	public int getBeta() {
		return beta;
	}
	public int getRho() {
		return rho;
	}

	@Override
	public String toString() {
		return "CLT13Params [n=" + n + ", lambda=" + lambda + ", kappa="
				+ kappa + ", eta=" + eta + ", alpha=" + alpha + ", beta="
				+ beta + ", rho=" + rho + "]";
	}
	
}
