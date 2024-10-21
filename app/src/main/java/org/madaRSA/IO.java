package org.madaRSA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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

	public static PublicKey readPublicKey(Path filePath) throws IOException {

		try (BufferedReader reader = Files.newBufferedReader(filePath)) {
			var line = reader.readLine().split(",");
			BigInteger n = new BigInteger(line[0]);
			BigInteger e = new BigInteger(line[1]);

			return new PublicKey(n, e);
		}
	}

	public static void encryptText(Path input, PublicKey publicKey) {
		Path OutputText = Path.of("./src/main/java/org/madaRSA/chiffre.txt");
		String out = "";

		try (BufferedReader reader = Files.newBufferedReader(input)) {
			var line = reader.readLine();
			while (line != null) {
				var arr = line.toCharArray();
				for (int i = 0; i < arr.length; i++) {
					BigInteger base = BigInteger.valueOf(arr[i]);
					var encryptetChar = Algorithm.fastExponent(base, publicKey.getE(), publicKey.getN());
					out += encryptetChar;
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.err.println("Error while reading file: " + e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(
				OutputText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write(out);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void decryptText(Path input, PublicKey publicKey) {
		Path OutputText = Path.of("./src/main/java/org/madaRSA/text-d.txt");
		String out = "";
		try (BufferedReader reader = Files.newBufferedReader(input)) {
			var line = reader.readLine();
			while (line != null) {
				var arr = line.toCharArray();
				for (int i = 0; i < arr.length; i++) {
					BigInteger base = BigInteger.valueOf(arr[i]);
					var encryptetChar = Algorithm.fastExponent(base, publicKey.getE(), publicKey.getN());
					out += encryptetChar;
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.err.println("Error while reading file: " + e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(
				OutputText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write(out);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
