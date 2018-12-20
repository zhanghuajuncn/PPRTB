/**
* @author 张华君
*/
package test;

import java.util.Date;

public class TimeCount extends Thread {
	long startTime = 0;
	boolean alive = true;
	public void run() {
		startTime = (new Date()).getTime();
		System.out.println("time: 0");
		while (alive) {
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("time: "+((new Date()).getTime()-startTime)/1000);
		}
    }
}
