
import bonek.BonekAlgorithm;
import bonek.Keygen;
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
   * convert String to unsigned byte
   * since Java doesn't support all unsigned type, so there is no built-in function for this,
   * and we have to implement it at our own
   * @param s
   * @return 
   */
  public static int[] toByte(String s) {
    byte[] b = s.getBytes();
    int[] ret = new int[b.length];
    for(int i = 0; i < ret.length; i++) {
      ret[i] = b[i] + 128;
    }
    return ret;
  }
  
  /**
   * convert unsigned int to String
   * @param a
   * @return 
   */
  public static String toString(int[] a) {
    byte[] b = new byte[a.length];
    for(int i = 0; i < a.length; i++) {
      b[i] = (byte) (a[i] - 128);
    }
    return new String(b);
  }
  
  public static void print(int[] a) {
    for(int i : a) System.out.print(i + " "); System.out.println("");
  }
  
  public static void main(String[] args) {
    String message = "ciee yang udah dapet macbook trus gak ikutan codejam m(_ _)m";
    int[] a = toByte(message);
    
    System.out.println("before encrypted");
    print(a);
    
    // get generated key pair (public - private)
    Pair<Point, BigInteger> key = Constant.getKey();
    
    // normalizing the symmetric encryption key, because all Block Cipher stuff must be normal
    String encryptKey = "kentang goreng";
    int[] simKey = Keygen.normalize(toByte(encryptKey));
    
    a = new BonekAlgorithm().encrypt(a, simKey);
    
    System.out.println("Encrypted");
    print(a);
    
    // signing the message, param : int[] message, BigInteger secretKey
    Ecdsa dsa = new Ecdsa();
    Pair<BigInteger, BigInteger> signature = dsa.sign(a, key.second);
    
    System.out.println("\ndigital signature");
    System.out.println(signature.first.toString(16));
    System.out.println(signature.second.toString(16));
    
    // verify the digital signature, param : int[] message, Point publicKey, Pair<Bigint, Bigint> signature
    System.out.println(dsa.verify(a, key.first, signature)? "VERIFIED" : "NOT VERIFIED");
    
    a = new BonekAlgorithm().decrypt(a, simKey);
    System.out.println("Decrypted");
    print(a);
    
    System.out.println("original message");
    System.out.println(toString(a));
  }
  
}
