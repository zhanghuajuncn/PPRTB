/**
* @author 张华君
*/
package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import pprtb.AdExchanger;
import pprtb.BigPair;
import pprtb.DMP;
import pprtb.DSP;
import pprtb.Encryption;
import pprtb.Publisher;
import pprtb.User;

public class Benchmark {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Benchmark starts.");
		System.out.println("User Number:" + DMP.userNum);
		System.out.println("Ad Number:" + DSP.adNum);
		Encryption encTool = new Encryption("g.para");
		DMP dmp = new DMP();
		DSP dsp = new DSP(encTool);
		AdExchanger ade = new AdExchanger(encTool);
		ArrayList<User> users = new ArrayList<User>();
		System.out.println("Create users");
		//create users 
		for (int i = 0; i < DMP.userNum; i++) {
			String id = "user" + i;
			ArrayList<Integer> profile = new ArrayList<Integer>();
			Random rnd = new Random();
			for (int j = 0; j < User.n; j++) 
				profile.add(rnd.nextInt(10));
			User user = new User(id, profile, encTool);
			String tag = user.cProfile.get(0).toString();
			dmp.updateProfile(tag, user.cProfile);
			users.add(user);
			//System.out.println("User" + i +" is ready.");
		}
		
		//create triggers
		for (int k = 0; k < 100; k++) {

		Publisher pub = new Publisher(encTool);
		System.out.println(k);
		for (int i = 0; i < 50; i++) {
			Random rnd = new Random();
			int userIndex = rnd.nextInt(DMP.userNum);
			User user = users.get(userIndex);
			String tag = user.cProfile.get(0).toString();
			ArrayList<BigPair> cProfile = dmp.getProfile(tag);
			ArrayList<BigPair> bids = dsp.bid(cProfile);
			ArrayList<BigPair> saltedBids = ade.salt(bids);
			ArrayList<BigPair> reBids = user.reEnc(saltedBids, ade.pk);
			int best = ade.compare(reBids);
			//System.out.println("Round " + i);
			//System.out.println("User " + userIndex);
			//System.out.println("Bid " + best);
			pub.countFare(best, userIndex, reBids.get(best));
		}
		for (int i = 0; i < DSP.adNum; i++) {
			int charge = 0;
			for (int j = 0;j < DMP.userNum; j++) {
				int cnt = pub.rndCnt.get(i).get(j);
				if (cnt != 0) {
					BigPair c = pub.fare.get(i).get(j);
					c = users.get(j).deRndEnc(c, cnt);
					int t = ade.dec(c);
					charge += t;
				}
			}
			System.out.println("Ad " + i +" should pay: " + charge);
		}
	}}
}
