package elliptic_curve_signature;


import java.math.BigInteger;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luqman A. Siswanto
 */
public class Constant {
  public static final BigInteger p = new BigInteger("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);
  public static final BigInteger a = new BigInteger("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC", 16);
  public static final BigInteger b = new BigInteger("0051953EB9618E1C9A1F929A21A0B68540EEA2DA725B99B315F3B8B489918EF109E156193951EC7E937B1652C0BD3BB1BF073573DF883D2C34F1EF451FD46B503F00", 16);
  public static final BigInteger n = new BigInteger("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFA51868783BF2F966B7FCC0148F709A5D03BB5C9B8899C47AEBB6FB71E91386409", 16);
  
  public static final Random random = new Random();
  
  public static final BigInteger Pa = new BigInteger("00C6858E06B70404E9CD9E3ECB662395B4429C648139053FB521F828AF606B4D3DBAA14B5E77EFE75928FE1DC127A2FFA8DE3348B3C1856A429BF97E7E31C2E5BD66", 16);
  public static final BigInteger Pb = new BigInteger("011839296A789A3BC0045C8A5FB42C7D1BD998F54449579B446817AFBD17273E662C97EE72995EF42640C550B9013FAD0761353C7086A272C24088BE94769FD16650", 16);
  public static final Point P = new Point(Pa, Pb);
  
  // compute c^d mod m
  public static BigInteger power(BigInteger c, BigInteger d, BigInteger m) {
    return c.modPow(d, m);
  }
  
  public static BigInteger inv(BigInteger c) {
    return power(c, p.subtract(BigInteger.valueOf(2)), p);
  }
  
  // get random number in range (inclusive)
  public static BigInteger getRandom(BigInteger l, BigInteger r) {
    BigInteger rand = (new BigInteger(521, random)).abs();
    return rand.mod(r.subtract(l).add(BigInteger.valueOf(1))).add(l);
  }
  
  public static Point getPublicKey(BigInteger secretKey) {
    Point base = new Point(P);
    return new Point().multiply(base, secretKey);
  }
  
  public static Pair<Point, Integer> getKey() {
    BigInteger secretKey = getRandom(BigInteger.valueOf(1), n.subtract(BigInteger.valueOf(1)));
    return new Pair(getPublicKey(secretKey), secretKey);
  }
}
