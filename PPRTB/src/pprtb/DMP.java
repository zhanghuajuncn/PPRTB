/**
* @author 张华君
*/
package pprtb;

import java.util.ArrayList;
import java.util.HashMap;

public class DMP {
	public static int userNum = 10;
	public HashMap<String, ArrayList<BigPair>> table = new HashMap<String, ArrayList<BigPair>>();
	public ArrayList<BigPair> getProfile(String tag) {
		String str = tag.toString();
		return table.get(str);
	}
	public void updateProfile(String tag, ArrayList<BigPair> cProfile) {
		table.put(tag, cProfile);
	}
}
