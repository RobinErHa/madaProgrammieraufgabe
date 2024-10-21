package org.madaRSA;

import java.io.IOException;
import java.nio.file.Path;

public class main {
	public static void main(String[] args) throws IOException {

		// Creates a new Rsa key pair
		var rsaKeyPair = new Rsa();

		// Writes PrivateKey to sk.txt, writes PublicKey to pk.txt
		IO.writeKeys(rsaKeyPair.getPrivateKey(), rsaKeyPair.getPublicKey());

		// Reads the generated publicKey from the pk.txt file and creates a PublicKey
		Path publicKeyPath = Path.of("./src/main/java/org/madaRSA/pk.txt");
		var publicKey = IO.readPublicKey(publicKeyPath);

		// Reads a Text and encrypts it to the 'chiffre.txt' file
		Path inputTextPath = Path.of("./src/main/java/org/madaRSA/text.txt");
		IO.encryptText(inputTextPath, publicKey);

		// Reads privateKey from the sk.txt file and creates a PrivateKey
		Path privateKeyPath = Path.of("./src/main/java/org/madaRSA/sk.txt");
		var privateKey = IO.readPrivateKey(privateKeyPath);

		// decrypts the text of 'chiffre.txt' and writes it to text-d.txt
		Path outputPath1 = Path.of("./src/main/java/org/madaRSA/text-d.txt");
		Path encryptedText = Path.of("./src/main/java/org/madaRSA/chiffre.txt");
		IO.decryptText(encryptedText, outputPath1, privateKey);

		// Reads the provided PrivateKey 'originalSk'
		// decrypts the original encrypted message 'originalChiffre'
		// writes the decrypted message to 'originalText-d'
		Path originalChiffre = Path.of("./src/main/java/org/madaRSA/originalChiffre.txt");
		Path originalSK = Path.of("./src/main/java/org/madaRSA/originalSk.txt");
		Path outputPath2 = Path.of("./src/main/java/org/madaRSA/originalText-d.txt");
		var ogPrivateKey = IO.readPrivateKey(originalSK);
		IO.decryptText(originalChiffre, outputPath2, ogPrivateKey);
	}
}
