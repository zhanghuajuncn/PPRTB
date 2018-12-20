/**
* @author 张华君
*/
package test;

import java.math.BigInteger;
import java.util.Random;

public class TestBigInteger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimeCount count = new TimeCount();
		count.start();
		BigInteger p = new BigInteger(2048, 50, new Random());
		System.out.println("Test randomGen");
		for (int i = 0; i < 100; i++) {
			BigInteger b = new BigInteger(2048, new Random());
		}
		System.out.println("Test modPow");
		BigInteger a = new BigInteger(2048, new Random());
		for (int i = 0; i < 500; i++) {
			a = a.modPow(a, p);
		}
		System.out.println("Test modInverse");
		for (int i = 0; i < 500; i++) {
			a = a.modInverse(p);
		}
		System.out.println("Test modInverse");
		count.alive = false;
	}

}
