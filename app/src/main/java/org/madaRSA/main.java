package org.madaRSA;

public class main {

	public static void main(String[] args) {
		var e = new Rsa();
		System.out.println(e.getPublickey().getN());
		System.out.println(e.getPublickey().getD());
	}
}
