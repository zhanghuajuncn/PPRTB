/**
* @author 张华君
*/
package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import pprtb.BigPair;
import pprtb.Encryption;
import pprtb.User;

public class TestUser {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("TestUser starts.");
		String id = "test1";
		ArrayList<Integer> profile = new ArrayList<Integer>();
		Random rnd = new Random();
		for (int i = 0; i < User.n; i++) 
			profile.add(rnd.nextInt(10));

		Encryption encTool = new Encryption("g.para");
		
		User user = new User(id, profile, encTool);
		
		System.out.println("User is ready.");
		
		System.out.println("cProfile.siez:" + user.cProfile.size());
		System.out.println("cProfile[0].b:" + user.cProfile.get(0).b);
		
		System.out.println("Update profile");
		
		user.updateProfile(profile);
		System.out.println("cProfile.size:" + user.cProfile.size());
		System.out.println("cProfile[0].b:" + user.cProfile.get(0).b);
		
		System.out.println("TestUser Finished");
	}
}
