/**
* @author 张华君
*/
package pprtb;

import java.io.IOException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class User {
	public static int n = 10;
	public String id;
	ArrayList<Integer> profile;
	public ArrayList<BigPair> cProfile;
	public Encryption encTool;
	private BigInteger sk;
	public BigInteger pk;
	private BigInteger s; 
	

	public User(String id, ArrayList<Integer> profile, Encryption encTool) throws IOException, ClassNotFoundException {
		this.id = id;
		this.profile = profile;
		this.encTool = encTool;
		sk = encTool.genSK();
		pk = encTool.g.modPow(sk, encTool.p);
		s = new BigInteger(Encryption.keyLength, new Random());
		cProfile = new ArrayList<BigPair>();
		for (int i = 0; i < n; i++) {
			cProfile.add(encTool.enc(pk, profile.get(i)));
		}
	}
	
	public void updateProfile(ArrayList<Integer> profile) throws IOException {
		this.profile = profile;
		cProfile = new ArrayList<BigPair>();
		for (int i = 0; i < n; i++) {
			cProfile.add(encTool.enc(pk, profile.get(i)));
		}
	}
	
	public int computeBid (ArrayList<Integer> para) {
		int result = 0;
		for (int i = 0; i < n; i++) {
			result += para.get(i) * profile.get(i);
		}
		return result;
	}
	
	public ArrayList<BigPair> reEnc(ArrayList<BigPair> saltedBids, BigInteger pk2) {
		ArrayList<BigPair> results = new ArrayList<BigPair>();
		//BigInteger r = new BigInteger(Encryption.keyLength, new Random());
		//s = s.multiply(r).mod(encTool.p);
		for (int i = 0; i < DSP.adNum; i++) {
			BigPair saltedBid = saltedBids.get(i);
			BigPair re = encTool.reEnc(sk, pk2, saltedBid, s);
			results.add(re);
		}
		return results;
	}
	public BigPair deRndEnc(BigPair c, int cnt) {
		BigInteger sc = s.modPow(BigInteger.valueOf(cnt), encTool.p);
		BigInteger scn = sc.modInverse(encTool.p);
		c.a = c.a.multiply(scn).mod(encTool.p);
		return c;
		
	}
	
}
