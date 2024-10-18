package org.madaRSA;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.madaRSA.Rsa.PrivateKey;
import org.madaRSA.Rsa.PublicKey;

public class IO {

	public static void writeKeys(PrivateKey privKey, PublicKey pubKey) throws IOException {
		Path privateKeyPath = Path.of("./src/main/java/org/madaRSA/sk.txt");
		Path publicKeyPath = Path.of("./src/main/java/org/madaRSA/pk.txt");

		try (BufferedWriter writer = Files.newBufferedWriter(
				privateKeyPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write(privKey.getN() + "," + privKey.getD());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(
				publicKeyPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write(pubKey.getN() + "," + pubKey.getE());
		}
	}

	// public static PublicKey readPublicKey(Path filePath) {
	//
	//
	// }
}
