/**
* @author 张华君
*/
package pprtb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class Encryption {
	static int keyLength = 1024;
	static int messageRange = 100000;
	public BigInteger p;
	public BigInteger g;
	public HashMap<BigInteger, Integer> decMap = new HashMap<BigInteger, Integer>();
	
	public Encryption() {
		p = new BigInteger(keyLength, 50, new Random());
		g = new BigInteger(keyLength, new Random());
		g = g.mod(p);
		for (int i = -messageRange; i <= messageRange; i++) {
			BigInteger bi = new BigInteger(Integer.toString(i));
			BigInteger gi = g.modPow(bi, p);
			decMap.put(gi, i);
		}
	}
	
	public void storePara(String filename) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(p);
		out.writeObject(g);
		out.writeObject(decMap);
		out.close();
	}
	
	public Encryption(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
		p = (BigInteger) in.readObject();
		g = (BigInteger) in.readObject();
		decMap = (HashMap<BigInteger, Integer>) in.readObject();
		in.close();
	}
	
	public BigInteger genSK() {
		BigInteger sk = new BigInteger(keyLength, new Random());
		sk = sk.mod(p);
		return sk;
	}
	
	public BigPair enc(BigInteger pk, int m) {
		BigInteger bm = BigInteger.valueOf(m);
		BigInteger r = new BigInteger(keyLength, new Random());
		BigInteger gr = g.modPow(r, p);
		BigInteger pkr = pk.modPow(r, p);
		BigInteger gm = g.modPow(bm, p);
		BigPair pair = new BigPair(gr, pkr.multiply(gm).mod(p));
		return pair;
	}
	
	public BigPair reEnc(BigInteger sk1, BigInteger pk2, BigPair c, BigInteger r0) {
		BigInteger pkr1 = c.a.modPow(sk1, p);
		BigInteger pkr1n = pkr1.modInverse(p);
		BigInteger gm = c.b.multiply(pkr1n).mod(p);
		BigInteger r2 = new BigInteger(keyLength, new Random());
		BigInteger gr2 = g.modPow(r2, p);
		BigInteger pkr2 = pk2.modPow(r2, p);
		BigPair pair = new BigPair(gr2, pkr2.multiply(gm).mod(p));
		pair.a = pair.a.multiply(r0).mod(p);
		return pair;
	}
	
	public int dec (BigInteger sk, BigPair c) {
		BigInteger pkr = c.a.modPow(sk, p);
		BigInteger pkrn = pkr.modInverse(p);
		BigInteger gm = c.b.multiply(pkrn).mod(p);
		Integer m = decMap.get(gm);
		if (m == null) 
			return -999999;
		return m;
	}
	
}
