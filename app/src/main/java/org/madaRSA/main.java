package org.madaRSA;

import java.io.IOException;

public class main {

	public static void main(String[] args) throws IOException {
		var e = new Rsa();
		System.out.println(e.getPublicKey().getN());
		System.out.println(e.getPublicKey().getE());
		IO.writeKeys(e.getPrivateKey(), e.getPublicKey());
	}
}
