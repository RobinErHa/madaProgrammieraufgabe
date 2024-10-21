package org.madaRSA;

import java.math.BigInteger;

public class PublicKey {
	private final BigInteger n;

	public BigInteger getN() {
		return n;
	}

	private final BigInteger e;

	public BigInteger getE() {
		return e;
	}

	/**
	 * Create new PublicKey
	 *
	 * @param n: sets n
	 * @param e: sets e
	 */
	public PublicKey(BigInteger n, BigInteger e) {
		this.n = n;
		this.e = e;
	}
}
