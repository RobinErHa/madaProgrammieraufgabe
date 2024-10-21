package org.madaRSA;

import java.io.IOException;
import java.nio.file.Path;

public class main {

	public static void main(String[] args) throws IOException {
		var e = new Rsa();
		System.out.println(e.getPublicKey().getN() + "\n");
		System.out.println(e.getPublicKey().getE());
		IO.writeKeys(e.getPrivateKey(), e.getPublicKey());

		System.out.println("\n \n \n");

		Path publicKeyPath = Path.of("./src/main/java/org/madaRSA/pk.txt");

		var publicKey = IO.readPublicKey(publicKeyPath);
		System.out.println("\n \n \n");
		System.out.println(publicKey.getN() + "\n");
		System.out.println(publicKey.getE());

		Path inputTextPath = Path.of("./src/main/java/org/madaRSA/text.txt");
		IO.encryptText(inputTextPath, publicKey);
	}
}
