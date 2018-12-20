/**
* @author 张华君
*/
package test;



import java.io.IOException;
import java.math.BigInteger;

import pprtb.BigPair;
import pprtb.Encryption;

public class TestEnc {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Encryption test = new Encryption("g.para");
		System.out.println("p:"+test.p);
		System.out.println("g:"+test.g);
		System.out.println("decMap:"+test.decMap.size());
		BigInteger sk = test.genSK();
		System.out.println("sk:"+sk);
		BigInteger pk = test.g.modPow(sk, test.p);
		System.out.println("pk:"+pk);
		int m1 = 1000;
		int m2 = -402;
		BigPair c1= test.enc(pk, m1);
		int m11 = test.dec(sk, c1);
		System.out.println("m11:"+m11);
		BigPair c2 = test.enc(pk, m2);
		int m22 = test.dec(sk, c2);
		System.out.println("m22:"+m22);
		BigPair c3 = new BigPair(c1.a.multiply(c2.a).mod(test.p), c1.b.multiply(c2.b).mod(test.p));
		int m33 = test.dec(sk, c3);
		System.out.println("m33:"+m33);
	}

}
