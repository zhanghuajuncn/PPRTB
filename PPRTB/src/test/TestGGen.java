/**
* @author 张华君
*/
package test;

import java.io.IOException;
import java.util.Date;

import pprtb.Encryption;

public class TestGGen {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long time = (new Date()).getTime();
		Encryption test = new Encryption();
		System.out.println("p:"+test.p);
		System.out.println("g:"+test.g);
		test.storePara("g.para");
		System.out.println((new Date()).getTime()-time);
	}

}
