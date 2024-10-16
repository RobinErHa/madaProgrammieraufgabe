package org.madaRSA;

import java.math.BigInteger;
import java.util.Random;

public class Rsa {

	private final BigInteger p = BigInteger.probablePrime(1024, new Random());
	private final BigInteger q = BigInteger.probablePrime(1024, new Random());

	private final BigInteger n = p.multiply(q);

	public final BigInteger phiVonN() {
		return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	}
}
