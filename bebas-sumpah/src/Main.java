
import elliptic_curve_signature.Constant;
import elliptic_curve_signature.Ecdsa;
import elliptic_curve_signature.Pair;
import elliptic_curve_signature.Point;
import java.math.BigInteger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luqman A. Siswanto
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    
    /* convert string to byte[] and vice versa */
    String aa = "asu";
    byte[] bb = aa.getBytes();
    for(byte i : bb) System.out.print(i + " "); System.out.println("");
    aa = new String(bb);
    System.out.println(aa);
    
    Kelas k = new Kelas();
    k.a().b();
    BigInteger big = new BigInteger("10", 16);
    System.out.println(big);
    
    BigInteger num = BigInteger.valueOf(4);
    BigInteger numb = num;
    num = num.add(BigInteger.valueOf(5));
    System.out.println(num + " " + numb);
    
    int[] a = {1, 2, 3, 4, 5};
    System.out.println(a);
    Pair<Point, BigInteger> key = Constant.getKey();
    Ecdsa dsa = new Ecdsa();
    Pair<BigInteger, BigInteger> signature = dsa.sign(a, key.second);
    System.out.println(signature);
    System.out.println(dsa.verify(a, key.first, signature));
  }
  
  public static class Kelas {
    public Kelas() {}
    public Kelas a() {
      System.out.println("a");
      return this;
    }
    public Kelas b() {
      System.out.println("b");
      return this;
    }
  }
}
