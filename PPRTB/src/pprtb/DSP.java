/**
* @author 张华君
*/
package pprtb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class DSP {
	public static int adNum = 10; 
	public static int bidFunBound = 100; 
	public ArrayList<ArrayList<Integer>> bidFuns;
	Encryption enctool;
	public DSP(Encryption encTool) throws FileNotFoundException, ClassNotFoundException, IOException{
		bidFuns = new ArrayList<ArrayList<Integer>>();
		this.enctool =encTool;
		Random rnd = new Random();
		for (int i = 0; i < adNum; i++) {
			ArrayList<Integer> bidFun = new ArrayList<Integer>();
			for (int j = 0; j < User.n; j++) {
				bidFun.add(rnd.nextInt(bidFunBound));
			}
			bidFuns.add(bidFun);
		}
	}
	public ArrayList<BigPair> bid(ArrayList<BigPair> cProfile) {
		ArrayList<BigPair> results = new ArrayList<BigPair>();
		for (int i = 0; i < adNum; i++) {
			BigPair bid = new BigPair(BigInteger.ONE, BigInteger.ONE);
			for (int j = 0; j < cProfile.size(); j++) {
				BigInteger a = cProfile.get(j).a;
				BigInteger b = cProfile.get(j).b;
				a = a.modPow(BigInteger.valueOf(bidFuns.get(i).get(j)), enctool.p);
				b = b.modPow(BigInteger.valueOf(bidFuns.get(i).get(j)), enctool.p);
				bid.a = bid.a.multiply(a).mod(enctool.p);
				bid.b = bid.b.multiply(b).mod(enctool.p);
			}
			results.add(bid);
		}
		return results;
	}
}
