package snu.crypto.clt;

public class CLT13Main {

	
	public static void main(String[] args) {
		int n = 100;
		int lambda = 26;
		int kappa = 3;
		int rho = 26;
		int alpha = 26;
		int beta = 26;
		int eta = 450;
		
		CLT13Params params = new CLT13Params(n, lambda, kappa, eta, alpha, beta, rho);
		CLT13Key key = new CLT13Key(params);
		System.out.println(key);
	}
}
