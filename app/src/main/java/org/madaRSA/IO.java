package org.madaRSA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class IO {

	/**
	 * Writes PrivateKey into the sk.txt file Writes PublicKey into the pk.txt file
	 *
	 * @param privKey PrivateKey to be written
	 * @param pubKey  PublicKey to be written
	 */
	public static void writeKeys(PrivateKey privKey, PublicKey pubKey) {
		Path privateKeyPath = Path.of("./src/main/java/org/madaRSA/sk.txt");
		Path publicKeyPath = Path.of("./src/main/java/org/madaRSA/pk.txt");

		try (BufferedWriter writer = Files.newBufferedWriter(
				privateKeyPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write("(" + privKey.getN() + "," + privKey.getD() + ")");
		} catch (IOException e) {
			System.err.println("Error while trying to write PrivateKey: " + e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(
				publicKeyPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write("(" + pubKey.getN() + "," + pubKey.getE() + ")");
		} catch (IOException e) {
			System.err.println("Error while trying to write PublicKey: " + e.getMessage());
		}
	}

	/**
	 * Reads PublicKey from a file
	 *
	 * @param filePath Path to the file containing PublicKey
	 * @return returns PublicKey
	 */
	public static PublicKey readPublicKey(Path filePath) {

		try (BufferedReader reader = Files.newBufferedReader(filePath)) {
			var line = reader.readLine();
			line = line.replaceAll("^\\(|\\)$", ""); // removes the '(' and ')'
			var arr = line.split(",");
			BigInteger n = new BigInteger(arr[0]);
			BigInteger e = new BigInteger(arr[1]);

			return new PublicKey(n, e);
		} catch (IOException e) {
			System.err.println("Error while trying to read PublicKey from file: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Reads PrivateKey from a file
	 *
	 * @param filePath Path to the file containing PrivateKey
	 * @return returns PrivateKey
	 */
	public static PrivateKey readPrivateKey(Path filePath) {

		try (BufferedReader reader = Files.newBufferedReader(filePath)) {
			var line = reader.readLine();
			line = line.replaceAll("^\\(|\\)$", ""); // removes the '(' and ')'
			var arr = line.split(",");
			BigInteger n = new BigInteger(arr[0]);
			BigInteger d = new BigInteger(arr[1]);

			return new PrivateKey(n, d);
		} catch (IOException e) {
			System.err.println("Error while trying to read PrivateKey from file: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Encrypts given text with the given PublicKey Writes result of encryption to
	 * 'chiffre.txt'
	 *
	 * @param input     - Path to text
	 * @param publicKey - Key to encrypt text with
	 */
	public static void encryptText(Path input, PublicKey publicKey) {
		Path OutputText = Path.of("./src/main/java/org/madaRSA/chiffre.txt");
		StringBuilder out = new StringBuilder();

		try (BufferedReader reader = Files.newBufferedReader(input)) {
			var line = reader.readLine();
			while (line != null) {
				var arr = line.toCharArray();
				for (int i = 0; i < arr.length; i++) {
					BigInteger base = BigInteger.valueOf(arr[i]);
					System.out.println("char:" + base);
					var encryptedChar = Algorithm.fastExponent(base, publicKey.getE(), publicKey.getN());
					out.append(encryptedChar);
					out.append(",");
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.err.println("Error while reading file " + input + "\nError: " + e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(
				OutputText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write(out.toString());
		} catch (IOException e) {
			System.err.println(
					"Error while trying to write to " + OutputText + "\nError: " + e.getMessage());
		}
	}

	/**
	 * Decrypts given text with the given PrivateKey. Writes result of decryption to
	 * 'text-d.txt'
	 *
	 * @param input      - Path to text
	 * @param privateKey - Key to decrypt text with
	 */
	public static void decryptText(Path input, PrivateKey privateKey) {
		Path OutputText = Path.of("./src/main/java/org/madaRSA/text-d.txt");
		StringBuilder out = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(input)) {
			var line = reader.readLine();
			while (line != null) {
				var arr = line.split(",");
				for (int i = 0; i < arr.length; i++) {
					BigInteger base = new BigInteger(arr[i]);
					var decryptedChar = Algorithm.fastExponent(base, privateKey.getD(), privateKey.getN());
					out.append((char) decryptedChar.intValue());
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.err.println("Error while reading file: " + input + "\nError: " + e.getMessage());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(
				OutputText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write(out.toString());
		} catch (IOException e) {
			System.err.println("Error while writing file: " + OutputText + "\nError: " + e.getMessage());
		}
	}
}
