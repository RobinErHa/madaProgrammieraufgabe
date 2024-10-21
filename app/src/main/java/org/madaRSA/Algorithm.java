package org.madaRSA;

import java.math.BigInteger;

public class Algorithm {

	/**
	 * Implementation of the extended euclydean algorithm
	 *
	 * @param phiN: phi(n)
	 * @param e:    Part of the Public Key (or the Private Key)
	 * @returns d: d modulo phi(n) is the inverse of e modulo phi(n)
	 */
	public static BigInteger extendedEuclideanAlgorithm(BigInteger phiN, BigInteger e) {
		var a = phiN;
		var b = e;
		var x0 = BigInteger.ONE;
		var y0 = BigInteger.ZERO;
		var x1 = BigInteger.ZERO;
		var y1 = BigInteger.ONE;

		var temp = a.divideAndRemainder(b);
		BigInteger q = temp[0];
		BigInteger r = temp[1];

		while (!b.equals(BigInteger.ZERO)) {
			temp = a.divideAndRemainder(b);
			q = temp[0]; // quotient
			r = temp[1]; // remainder
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
		return y0; // d
	}

	/** Implementation of the fast exponent algorithm */
	public static BigInteger fastExponent(BigInteger base, BigInteger exponent, BigInteger m) {
		int i = 0;
		var h = BigInteger.ONE;
		var k = base;

		while (i < exponent.bitLength()) {
			if (exponent.testBit(i)) {
				h = h.multiply(k).mod(m);
			}
			k = k.multiply(k).mod(m);
			i++;
		}

		return h;
	}
}
