package org.madaRSA;

import java.io.IOException;
import java.nio.file.Path;

public class main {

	public static void main(String[] args) throws IOException {
		var e = new Rsa();
		System.out.println("PublicKey: N -> " + e.getPublicKey().getN() + "\n");
		System.out.println("PublicKey: E -> " + e.getPublicKey().getE() + "\n");

		System.out.println("\n");
		System.out.println("PrivateKey: N ->" + e.getPrivateKey().getN() + "\n");
		System.out.println("PrivateKey: D ->" + e.getPrivateKey().getD() + "\n");
		IO.writeKeys(e.getPrivateKey(), e.getPublicKey());

		System.out.println("\n");

		Path publicKeyPath = Path.of("./src/main/java/org/madaRSA/pk.txt");
		var publicKey = IO.readPublicKey(publicKeyPath);

		System.out.println("PublicKey of pk:N -> " + publicKey.getN() + "\n");
		System.out.println("PublicKey of pk:E ->" + publicKey.getE() + "\n");
		System.out.println("\n");

		Path inputTextPath = Path.of("./src/main/java/org/madaRSA/text.txt");
		IO.encryptText(inputTextPath, publicKey);

		Path privateKeyPath = Path.of("./src/main/java/org/madaRSA/sk.txt");
		var privateKey = IO.readPrivateKey(privateKeyPath);
		System.out.println("Privatekey of sk: N ->" + privateKey.getN() + "\n");
		System.out.println("Privatekey of sk: d ->" + privateKey.getD() + "\n");

		Path encryptedText = Path.of("./src/main/java/org/madaRSA/chiffre.txt");
		IO.decryptText(encryptedText, privateKey);

		Path originalChiffre = Path.of("./src/main/java/org/madaRSA/originalChiffre.txt");
		Path originalSK = Path.of("./src/main/java/org/madaRSA/originalSk.txt");
		var ogPrivateKey = IO.readPrivateKey(originalSK);
		IO.decryptText(originalChiffre, ogPrivateKey);
	}
}
