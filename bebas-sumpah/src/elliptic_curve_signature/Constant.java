package elliptic_curve_signature;


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
  public static final long p = 2000000089;
  public static final long a = 517087969;
  public static final long b = -921535869;
  public static final long n = 3081751;
  public static final int k = 649;
  public static final Random random = new Random();
  
  public static final long Pa = 410199798;
  public static final long Pb = 1120963432; 
  public static final Point P = new Point(Pa, Pb);
  
  // compute c^d mod m
  public static long power(long c, long d, long m) {
    if(d == 0) return 1 % m;
    long tmp = power(c, d / 2, m);
    tmp = tmp * tmp % m;
    if(d % 2 == 1) {
      tmp = tmp * c % m;
    }
    return tmp;
  }
  
  // public inverse of d
  public static long inv(long d) {
    return power(d, p - 2, p);
  }
  
  // get random number in range (inclusive)
  public static long getRandom(long l, long r) {
    long rand = random.nextLong();
    if(rand < 0) rand = -rand;
    return rand % (r - l + 1) + l;
  }
  
  public static Point getPublicKey(int secretKey) {
    Point base = new Point(P);
    return new Point().multiply(base, secretKey);
  }
  
  public static Pair<Point, Integer> getKey() {
    int secretKey = (int) getRandom(1, p - 1);
    return new Pair(getPublicKey(secretKey), secretKey);
  }
}
