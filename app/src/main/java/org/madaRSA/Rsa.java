package org.madaRSA;

import java.math.BigInteger;
import java.util.Random;

public class Rsa {

	private PublicKey publickey;

	public PublicKey getPublickey() {
		return publickey;
	}

	private final PrivateKey privateKey = new PrivateKey();

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	// Erstelle zwei Primzahlen p und q
	private final BigInteger p = BigInteger.probablePrime(1024, new Random());
	private final BigInteger q = BigInteger.probablePrime(1024, new Random());

	// Rechne phi(n)
	private final BigInteger phiN = phiVonN();

	// rechnet phi(n) = (p-1) * (q-1)
	private BigInteger phiVonN() {
		return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
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
				b = new BigInteger(n.bitLength(), new Random()).mod(n);
			} while ((!b.gcd(n).equals(BigInteger.ONE) && !b.equals(BigInteger.ONE))
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
		var a1 = phiN;
		var b1 = e;
		var x0 = BigInteger.ONE;
		var y0 = BigInteger.ZERO;
		var x1 = BigInteger.ZERO;
		var y1 = BigInteger.ONE;
		BigInteger q, r;

		while (b1 != BigInteger.ZERO) {
			var temp = a1.divideAndRemainder(b1);
			q = temp[0];
			r = temp[1];
			a1 = b1;
			b1 = r;
			x0 = x1;
			y0 = y1;
			x1 = x0.subtract(x1.multiply(q));
			y1 = y0.subtract(y1.multiply(q));
		}
		if (y0.compareTo(BigInteger.ONE) < 1) {
			y0.add(q);
		}
		return y0;
	}
}
