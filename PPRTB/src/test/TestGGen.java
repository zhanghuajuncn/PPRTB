/**
* @author 张华君
*/
package test;

import java.io.IOException;

import pprtb.Encryption;

public class TestGGen {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TimeCount count = new TimeCount();
		count.start();
		Encryption test = new Encryption();
		System.out.println("p:"+test.p);
		System.out.println("g:"+test.g);
		test.storePara("g.para");
		count.alive = false;
	}

}
