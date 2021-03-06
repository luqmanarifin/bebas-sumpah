
import bonek.BonekAlgorithm;
import bonek.Keygen;
import elliptic_curve_signature.Constant;
import elliptic_curve_signature.Ecdsa;
import elliptic_curve_signature.Pair;
import elliptic_curve_signature.Point;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    return BonekAlgorithm.toByte(s);
  }
  
  /**
   * convert unsigned int to String
   * @param a
   * @return 
   */
  public static String toString(int[] a) {
    return BonekAlgorithm.toString(a);
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
    
    
    BonekAlgorithm bonek = new BonekAlgorithm();
    String s = "asu kon emang jancok";
    String sim = "cok";
    String enc = bonek.encrypt(s, sim);
    System.out.println(s);
    //System.out.println(BonekAlgorithm.toStringHex(BonekAlgorithm.toByteHex(s)));
    System.out.println(enc);
    System.out.println(bonek.decrypt(enc, sim));
    
    Pair<Point, BigInteger> one = Constant.getKey();
    Pair<Point, BigInteger> two = Constant.getKey();
    System.out.println(one.first + "\n\n" + one.second.toString(16));
    System.out.println(two.first + "\n\n" + two.second.toString(16));
  }
  
}
