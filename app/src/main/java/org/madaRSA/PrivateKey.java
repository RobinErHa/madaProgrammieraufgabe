package org.madaRSA;

import java.math.BigInteger;

public class PrivateKey {

	private final BigInteger n;
	private final BigInteger d;

	public PrivateKey(BigInteger n, BigInteger d) {
		this.n = n;
		this.d = d;
	}

	public PrivateKey(PublicKey publicKey, Rsa rsa) {
		this.n = publicKey.getN();
		d = Algorithm.extendedEuclydeanAlgorithm(rsa.getPhiN(), publicKey.getE());
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getD() {
		return d;
	}
}
