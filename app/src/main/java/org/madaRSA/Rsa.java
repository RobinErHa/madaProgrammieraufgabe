package org.madaRSA;

import java.math.BigInteger;
import java.util.Random;

public class Rsa {

	private PublicKey publickey;
	private final PrivateKey privateKey;

	// Erstelle zwei Primzahlen p und q
	private final BigInteger p = BigInteger.probablePrime(1024, new Random());
	private final BigInteger q = BigInteger.probablePrime(1024, new Random());

	// Rechne phi(n)
	private final BigInteger phiN;

	public Rsa() {
		// Calculate phi(n) = (p-1)*(q-1)
		this.phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		// Initialize private key, which will also initialize the public key
		this.privateKey = new PrivateKey();
	}

	public PublicKey getPublickey() {
		return publickey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	private class PrivateKey {

		private final BigInteger n;
		private final BigInteger e;

		private PrivateKey() {
			n = p.multiply(q);
			e = setE();
			publickey = new PublicKey(n, e);
		}

		// Finde eine zufaellige Zahl e: 1 < e < n mit ggT(N, e) = 1
		private BigInteger setE() {
			BigInteger b;
			do {
				b = new BigInteger(phiN.bitLength(), new Random())
						.mod(phiN.subtract(BigInteger.ONE))
						.add(BigInteger.ONE);
			} while ((!b.gcd(phiN).equals(BigInteger.ONE) && !b.equals(BigInteger.ONE))
					|| b.equals(BigInteger.ZERO));

			return b;
		}
	}

	public class PublicKey {
		private final BigInteger n;
		private final BigInteger d;

		public PublicKey(BigInteger n, BigInteger e) {
			this.n = n;
			d = erweiterterEuklydischerAlgorithmus(phiN, e);
		}

		public BigInteger getN() {
			return n;
		}

		public BigInteger getD() {
			return d;
		}
	}

	public BigInteger erweiterterEuklydischerAlgorithmus(BigInteger phiN, BigInteger e) {
		var a = phiN;
		var b = e;
		var x0 = BigInteger.ONE;
		var y0 = BigInteger.ZERO;
		var x1 = BigInteger.ZERO;
		var y1 = BigInteger.ONE;

		var temp = a.divideAndRemainder(b);
		BigInteger q = temp[0];
		BigInteger r = temp[1];

		while (b != BigInteger.ZERO) {
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
}
