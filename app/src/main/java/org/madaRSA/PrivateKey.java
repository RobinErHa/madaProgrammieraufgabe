package org.madaRSA;

import java.math.BigInteger;

public class PrivateKey {

	private final BigInteger n;
	private final BigInteger d;

	/**
	 * Create a new PrivateKey
	 *
	 * @param n: sets n
	 * @param d: sets d
	 */
	public PrivateKey(BigInteger n, BigInteger d) {
		this.n = n;
		this.d = d;
	}

	/**
	 * Create new PrivateKey for given PublicKey
	 *
	 * @see Algorithm.extendedEuclydeanAlgorithm
	 */
	public PrivateKey(PublicKey publicKey, Rsa rsa) {
		this.n = publicKey.getN();
		d = Algorithm.extendedEuclideanAlgorithm(rsa.getPhiN(), publicKey.getE());
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getD() {
		return d;
	}
}
