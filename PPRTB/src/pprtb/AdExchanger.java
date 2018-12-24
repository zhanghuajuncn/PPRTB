/**
* @author 张华君
*/
package pprtb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class AdExchanger {
	private ArrayList<BigInteger> rnd;
	Encryption encTool;
	private BigInteger sk;
	public BigInteger pk;
	//public ArrayList<ArrayList<BigPair>> fare;
	//public ArrayList<ArrayList<Integer>> rndCnt;
	
	public AdExchanger(Encryption encTool) throws FileNotFoundException, ClassNotFoundException, IOException {
		this.encTool = encTool;
		rnd = new ArrayList<BigInteger>();
		sk = encTool.genSK();
		pk = encTool.g.modPow(sk, encTool.p);
		for (int i = 0; i < DSP.adNum; i++) {
			BigInteger r = new BigInteger(Encryption.keyLength, new Random());
			r = r.mod(encTool.p);
			rnd.add(r);
		}
	}
	
	public ArrayList<BigPair> salt(ArrayList<BigPair> bids) {
		ArrayList<BigPair> results = new ArrayList<BigPair>();
		for (int i = 0; i < DSP.adNum; i++) {
			BigPair salted = bids.get(i);
			salted.b = salted.b.multiply(rnd.get(i)).mod(encTool.p);
			results.add(salted);
		}
		return results;
	}

	public int compare(ArrayList<BigPair> saltedBids) {
		int result = 0;
		BigPair max = saltedBids.get(0);
		BigInteger r = rnd.get(0);
		r = r.modInverse(encTool.p);
		max.b = max.b.multiply(r).mod(encTool.p);
		for (int i = 1; i < DSP.adNum; i++) {
			BigPair bid = saltedBids.get(i);
			r = rnd.get(i);
			r = r.modInverse(encTool.p);
			bid.b = bid.b.multiply(r).mod(encTool.p);
			BigPair t = new BigPair(bid.a.modInverse(encTool.p), bid.b.modInverse(encTool.p));
			t.a = t.a.multiply(max.a).mod(encTool.p);
			t.b = t.b.multiply(max.b).mod(encTool.p);
			int c = encTool.dec(sk, t);
			if (c < 0) {
				result = i;
				max = bid;
			}
		}
		return result;
	}

	public int dec(BigPair c) {
		int result = encTool.dec(sk, c);
		return result;
	}
}
