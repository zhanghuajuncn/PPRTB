/**
* @author 张华君
*/
package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import pprtb.AdExchanger;
import pprtb.BigPair;
import pprtb.DMP;
import pprtb.DSP;
import pprtb.Encryption;
import pprtb.User;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		System.out.println("TestUser starts.");
		
		//Create two users. Push their encrypted profiles into DMP.
		Encryption encTool = new Encryption("g.para");
		
		String id = "user1";
		ArrayList<Integer> profile = new ArrayList<Integer>();
		Random rnd = new Random();
		for (int i = 0; i < User.n; i++) 
			profile.add(rnd.nextInt(10));
		User user1 = new User(id, profile, encTool);
		
		System.out.println("User1 Ready.");

		DMP dmp = new DMP();
		String tag = user1.cProfile.get(0).toString();
		dmp.updateProfile(tag, user1.cProfile);
		
		id = "user2";
		profile = new ArrayList<Integer>();
		rnd = new Random();
		for (int i = 0; i < User.n; i++) 
			profile.add(rnd.nextInt(10));
		User user2 = new User(id, profile, encTool);
		tag = user2.cProfile.get(0).toString();
		dmp.updateProfile(tag, user2.cProfile);
		
		System.out.println("User2 Ready.");
		//Simulate user1 triggering SSP.
		System.out.println("User1 sends tag to SSP.");
		String sspTag = user1.cProfile.get(0).toString();
		System.out.println("SSP gets cProfile from DMP.");
		ArrayList<BigPair> cProfile = dmp.getProfile(sspTag);
		
		System.out.println("DMP sends cProfile to AdExchanger.");
		AdExchanger ade = new AdExchanger(encTool);
		System.out.println("AdExchanger sends cProfile to DSP.");
		System.out.println("DSP caculates the bids.");
		DSP dsp = new DSP(encTool);
		ArrayList<BigPair> bids = dsp.bid(cProfile);
		
		for (int i = 0; i < DSP.adNum; i++) {
			ArrayList<Integer> bidFun = dsp.bidFuns.get(i);
			int bid = user1.computeBid(bidFun);
			//System.out.println("Bid" + i + " caculated by User1:" + bid);
		}
		int bid0D = encTool.dec(user1.sk, bids.get(0));
		System.out.println("Decrypted Bid0:" + bid0D);
		System.out.println("AdExchanger adds salt to bids.");
		ArrayList<BigPair> saltedBids = ade.salt(bids);
		System.out.println("User1 re-encrypts the bids.");
		ArrayList<BigPair> reBids = user1.reEnc(saltedBids, ade.pk);
		System.out.println("AdExchanger caculates the best bid.");
		int best = ade.compare(reBids);
		System.out.println("AdExchanger says the best bid is bid" + best);
	}

}
