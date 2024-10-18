package org.madaRSA;

import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.Test;

public class RsaTest {

	private final Rsa rsa = new Rsa();

	@Test
	public void testExtendedEuclydeanAlgorithm() {

		// Example values for e and phi(n)
		BigInteger phiN = new BigInteger("3233"); // Example phi(n) value
		BigInteger e = new BigInteger("17"); // Example e value

		// Call the extendedEuclydeanAlgorithm method to get the result
		BigInteger d = rsa.extendedEuclydeanAlgorithm(phiN, e);

		// Validate that (e * d) % phi(n) == 1
		BigInteger result = e.multiply(d).mod(phiN);

		// Assert that the result is 1, which confirms d is the modular inverse of e mod
		// phi(n)
		assertEquals(
				"The result should be 1, indicating e * d ≡ 1 (mod phi(n))", BigInteger.ONE, result);
	}
}
