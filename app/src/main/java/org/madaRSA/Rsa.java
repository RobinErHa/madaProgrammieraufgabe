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
	 * @private BigInteger p - a random prime number 1024bit long
	 * @private BigInteger q - a random prime number 1024bit long
	 * @private BigInteger phiN - phi(n) calculated from (p-1)*(q-1);
	 * @see PublicKey creates a new public key - which then generates the
	 *      corresponding private key
	 */
	public Rsa() {

		// Calculate phi(n) = (p-1)*(q-1)
		this.phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		// Initialize public key, which will then initialize the private key
		this.publicKey = new PublicKey(n, setRandomE());

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
	 * @return part of the privateKey: e
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

	/// **
	// * @param phiN -> phi(n)
	// * @param d
	// * @returns e -> e modulo phi(n) is the inverse of d modulo phi(n)
	// */
	// public BigInteger extendedEuclydeanAlgorithm(BigInteger phiN, BigInteger d) {
	// var a = phiN;
	// var b = d;
	// var x0 = BigInteger.ONE;
	// var y0 = BigInteger.ZERO;
	// var x1 = BigInteger.ZERO;
	// var y1 = BigInteger.ONE;
	//
	// var temp = a.divideAndRemainder(b);
	// BigInteger q = temp[0];
	// BigInteger r = temp[1];
	//
	// while (!b.equals(BigInteger.ZERO)) {
	// temp = a.divideAndRemainder(b);
	// q = temp[0]; // quotient
	// r = temp[1]; // remainder
	// a = b;
	// b = r;
	//
	// var tempX = x1;
	// x1 = x0.subtract(x1.multiply(q));
	// x0 = tempX;
	//
	// var tempY = y1;
	// y1 = y0.subtract(y1.multiply(q));
	// y0 = tempY;
	// }
	// if (y0.compareTo(BigInteger.ONE) < 0) {
	// y0 = y0.add(phiN);
	// }
	// return y0;
	// }
	//
	/// ** Algorithm */
	// public BigInteger fastExponent(BigInteger base, BigInteger exponent,
	/// BigInteger m) {
	// int i = m.bitLength() - 1;
	// var h = BigInteger.ONE;
	// var k = base;
	//
	// while (i >= 0) {
	// if (m.testBit(i)) {
	// h = h.multiply(k).mod(m);
	// }
	// k = k.pow(2).mod(m);
	// i = i - 1;
	// }
	//
	// return h;
	// }
}
