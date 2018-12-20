/**
* @author 张华君
*/
package pprtb;

import java.io.Serializable;
import java.math.BigInteger;

public class BigPair implements Serializable {
	private static final long serialVersionUID = 22L;
	public BigInteger a;
	public BigInteger b;

	public BigPair (BigInteger a, BigInteger b) {
		this.a = a;
		this.b = b;
	}
	public String toString() {
		String str = a.toString() + b.toString();
		return str;
	}
}
