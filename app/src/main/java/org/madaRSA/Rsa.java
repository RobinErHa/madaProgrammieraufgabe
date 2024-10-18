package org.madaRSA;

import java.math.BigInteger;
import java.util.Random;

public class Rsa {

	private final PublicKey publicKey;
	private PrivateKey privateKey;

	private final BigInteger p = BigInteger.probablePrime(1024, new Random());
	private final BigInteger q = BigInteger.probablePrime(1024, new Random());

	private final BigInteger phiN;

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

		// Initialize private key, which will also initialize the private key
		this.publicKey = new PublicKey();
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public class PublicKey {

		private final BigInteger n;
		private final BigInteger e;

		public BigInteger getN() {
			return n;
		}

		public BigInteger getE() {
			return e;
		}

		/**
		 * Creates a new PrivateKey. sets n = p * q calls extendedEuclydeanAlgorithm to
		 * find the inverse
		 * of e
		 *
		 * @see PublicKey(BigInteger n, BigInteger e)
		 */
		private PublicKey() {
			n = p.multiply(q);
			e = setRandomE();
			privateKey = new PrivateKey(n, e);
		}

		/**
		 * Find random number e: 1 < e < n, with gcd(phiN, e) = 1
		 *
		 * @return part of the privateKey: e
		 */
		private BigInteger setRandomE() {
			BigInteger b;
			do {
				b = new BigInteger(phiN.bitLength(), new Random())
						.mod(phiN.subtract(BigInteger.ONE))
						.add(BigInteger.ONE);
			} while ((!b.gcd(phiN).equals(BigInteger.ONE) && !b.equals(BigInteger.ONE))
					|| b.equals(BigInteger.ZERO));

			return b;
		}

		/**
		 * Sets the private key and creates corresponding publicKey
		 *
		 * @param n the product of two prim numbers
		 * @param d part of the private key (n, d)
		 * @see PublicKey(BigInteger n, BigInteger d)
		 */
		public PublicKey(BigInteger n, BigInteger e) {
			this.n = n;
			this.e = e;
			privateKey = new PrivateKey(n, e);
		}
	}

	public class PrivateKey {
		private final BigInteger n;
		private final BigInteger d;

		public PrivateKey(BigInteger n, BigInteger e) {
			this.n = n;
			d = extendedEuclydeanAlgorithm(phiN, e);
		}

		public BigInteger getN() {
			return n;
		}

		public BigInteger getD() {
			return d;
		}
	}

	/**
	 * @param phiN -> phi(n)
	 * @param d
	 * @returns the inverse of e
	 */
	public BigInteger extendedEuclydeanAlgorithm(BigInteger phiN, BigInteger d) {
		var a = phiN;
		var b = d;
		var x0 = BigInteger.ONE;
		var y0 = BigInteger.ZERO;
		var x1 = BigInteger.ZERO;
		var y1 = BigInteger.ONE;

		var temp = a.divideAndRemainder(b);
		BigInteger q = temp[0];
		BigInteger r = temp[1];

		while (!b.equals(BigInteger.ZERO)) {
			temp = a.divideAndRemainder(b);
			q = temp[0];
			r = temp[1];
			a = b;
			b = r;

			var tempX = x1;
			x1 = x0.subtract(x1.multiply(q));
			x0 = tempX;

			var tempY = y1;
			y1 = y0.subtract(y1.multiply(q));
			y0 = tempY;
		}
		if (y0.compareTo(BigInteger.ONE) < 0) {
			y0 = y0.add(phiN);
		}
		return y0;
	}

	/** Algorithm */
	// TODO still flawed check with a test
	public BigInteger fastExponent(BigInteger x, PrivateKey p) {
		var m = p.n;
		int i = m.bitLength() - 1;
		var h = BigInteger.ONE;
		var k = x;

		while (i >= 0) {
			if (m.testBit(i)) {
				h = h.multiply(k).mod(m);
			}
			k = k.pow(2).mod(m);
			i = i - 1;
		}

		return h;
	}
}
