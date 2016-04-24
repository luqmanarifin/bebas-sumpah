/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elliptic_curve_signature;

import bonek.BonekAlgorithm;
import java.math.BigInteger;
import sha_1.SHA1;

/**
 *
 * @author Luqman A. Siswanto
 */
public class Ecdsa {
  SHA1 sha = new SHA1();
  
  public Ecdsa() {
    
  }
  
  public static int[] toByte(String s) {
    return BonekAlgorithm.toByte(s);
  }
  
  public static String toString(int[] a) {
    return BonekAlgorithm.toString(a);
  }
  
  /**
   * 
   * @param a
   * @param privateKey
   * @return signature as pair of BigInteger
   */
  public Pair<BigInteger, BigInteger> sign(int[] a, BigInteger privateKey) {
    while(true) {
      BigInteger k = Constant.getRandom(BigInteger.valueOf(1), Constant.n.subtract(BigInteger.valueOf(1)));
      Point temp = Point.multiply(Constant.P, k);
      BigInteger r = temp.x.mod(Constant.n);
      if(r.compareTo(BigInteger.valueOf(0)) == 0) continue;
      BigInteger k_inv = Constant.power(k, Constant.n.subtract(BigInteger.valueOf(2)), Constant.n);
      BigInteger h = sha.getDigest(a);
      BigInteger s = k_inv.multiply(h.add(privateKey.multiply(r))).mod(Constant.n);
      if(s.compareTo(BigInteger.valueOf(0)) == 0) continue;
      return new Pair(r, s);
    }
  }
  
  public String sign(String a, BigInteger privateKey) {
    Pair<BigInteger, BigInteger> ret = sign(toByte(a), privateKey);
    return ret.first.toString(16) + "\n" + ret.second.toString(16);
  }
  
  public boolean verify(int[] a, Point publicKey, Pair<BigInteger, BigInteger> signature) {
    BigInteger r = signature.first;
    BigInteger s = signature.second;
    BigInteger low = BigInteger.valueOf(1);
    if(r.compareTo(low) == -1 || s.compareTo(low) == -1) return false;
    BigInteger high = Constant.n.subtract(BigInteger.valueOf(1));
    if(r.compareTo(high) == 1 || s.compareTo(high) == 1) return false;
    BigInteger w = Constant.power(s, Constant.n.subtract(BigInteger.valueOf(2)), Constant.n);
    BigInteger h = sha.getDigest(a);
    BigInteger u1 = h.multiply(w).mod(Constant.n);
    BigInteger u2 = r.multiply(w).mod(Constant.n);
    Point temp = Point.multiply(Constant.P, u1).add(Point.multiply(publicKey, u2));
    BigInteger v = temp.x.mod(Constant.n);
    return r.compareTo(v) == 0;
  }

  public boolean verify(String a, Point publicKey, String signature) {
    String[] parts = signature.split("\n");
    Pair<BigInteger, BigInteger> sign = new Pair(new BigInteger(parts[0], 16), new BigInteger(parts[1], 16));
    return verify(toByte(a), publicKey, sign);
  }
  
}
