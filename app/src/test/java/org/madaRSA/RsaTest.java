package org.madaRSA;

import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.Test;

public class RsaTest {

	@Test
	public void testExtendedEuclydeanAlgorithm() {

		// Example values for e and phi(n)
		BigInteger phiN = new BigInteger("3233");
		BigInteger e = new BigInteger("17");

		BigInteger d = Algorithm.extendedEuclideanAlgorithm(phiN, e);

		// Validate that (e * d) % phi(n) == 1
		BigInteger result = e.multiply(d).mod(phiN);

		// Assert that the result is 1, which confirms d is the modular inverse of e mod
		// phi(n)
		assertEquals(
				"The result should be 1, indicating e * d ≡ 1 (mod phi(n))", BigInteger.ONE, result);
	}

	@Test
	public void testFastExponentAlgorithm() {
		var result = Algorithm.fastExponent(
				BigInteger.valueOf(9), BigInteger.valueOf(25), BigInteger.valueOf(11));
		assertEquals(1, result.intValue());

		var result2 = Algorithm.fastExponent(
				BigInteger.valueOf(5), BigInteger.valueOf(36), BigInteger.valueOf(11));
		assertEquals(5, result2.intValue());
	}
}
