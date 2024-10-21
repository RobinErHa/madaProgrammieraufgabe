package org.madaRSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Rsa {

	private final PublicKey publicKey;
	private PrivateKey privateKey;

	private static final SecureRandom secureRandom = new SecureRandom();
	private final BigInteger p = BigInteger.probablePrime(1024, secureRandom);
	private final BigInteger q = BigInteger.probablePrime(1024, secureRandom);

	private final BigInteger n = p.multiply(q);

	private final BigInteger phiN;

	public BigInteger getPhiN() {
		return phiN;
	}

	/**
	 * Creates a new RSA KeyPair
	 *
	 * @private BigInteger p: a random prime number 1024bit long
	 * @private BigInteger q: a random prime number 1024bit long
	 * @private BigInteger n: product of p and q
	 * @private BigInteger phiN: phi(n) calculated from (p-1)*(q-1);
	 * @see PublicKey
	 * @see PrivateKey
	 */
	public Rsa() {

		// Calculate phi(n) = (p-1)*(q-1)
		this.phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		// Create PublicKey with random legal e
		this.publicKey = new PublicKey(n, setRandomE());

		// Create PrivateKey with publicKey
		this.privateKey = new PrivateKey(publicKey, this);
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * Find random number e: 1 < e < n, with gcd(phiN, e) = 1
	 *
	 * @return part of the publicKey: e
	 */
	private BigInteger setRandomE() {
		BigInteger b;
		do {
			b = new BigInteger(phiN.bitLength() / 2, new Random())
					.mod(phiN.subtract(BigInteger.ONE))
					.add(BigInteger.ONE);
		} while ((!b.gcd(phiN).equals(BigInteger.ONE) && !b.equals(BigInteger.ONE))
				|| b.equals(BigInteger.ZERO));

		return b;
	}
}
