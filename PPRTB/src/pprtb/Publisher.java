/**
* @author 张华君
*/
package pprtb;

import java.math.BigInteger;
import java.util.ArrayList;

public class Publisher {
	public ArrayList<ArrayList<BigPair>> fare;
	public ArrayList<ArrayList<Integer>> rndCnt;
	Encryption encTool;
	public Publisher(Encryption encTool) {
		this.encTool = encTool;
		fare = new ArrayList<ArrayList<BigPair>>();
		for (int i = 0; i < DSP.adNum; i++) {
			ArrayList<BigPair> user = new ArrayList<BigPair>();
			for (int j = 0; j < DMP.userNum; j++) {
				BigPair one = new BigPair(BigInteger.ONE, BigInteger.ONE);
				user.add(one);
			}
			fare.add(user);
		}
		rndCnt = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < DSP.adNum; i++) {
			ArrayList<Integer> cnt = new ArrayList<Integer>();
			for (int j = 0; j < DMP.userNum; j++) {
				cnt.add(0);
			}
			rndCnt.add(cnt);
		}
	}
	public void countFare(int ad, int user, BigPair fa) {
		BigPair newFare = fare.get(ad).get(user);
		newFare.a = newFare.a.multiply(fa.a).mod(encTool.p);
		newFare.b = newFare.b.multiply(fa.b).mod(encTool.p);
		fare.get(ad).set(user, newFare);
		int cnt = rndCnt.get(ad).get(user);
		rndCnt.get(ad).set(user, cnt + 1);
	}
}
