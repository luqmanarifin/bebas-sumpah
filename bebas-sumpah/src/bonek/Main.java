/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonek;

/**
 *
 * @author Luqman A. Siswanto
 */
public class Main {
  public static int[] toByte(String s) {
    byte[] b = s.getBytes();
    int[] ret = new int[b.length];
    for(int i = 0; i < ret.length; i++) {
      ret[i] = b[i] + 128;
    }
    return ret;
  }
  
  /* force key to be 128-bit long */ 
  public static int[] normalize(int[] key) {
    int[] ret = new int[16];
    for(int i = 0; i < 16; i++) {
      ret[i] = key[i % key.length];
    }
    return ret;
  }
  
  public static void main(String[] args) {
    BonekAlgorithm bonek = new BonekAlgorithm();
    String s = "inas nuha kenapa kamu kok lucu sih";
    String key = "kenapa";
    int[] is = toByte(s);
    int[] ks = normalize(toByte(key));
    for(int i = 0; i < is.length; i++) System.out.print(is[i] + " "); System.out.println("");
    is = bonek.encrypt(is, ks);
    for(int i = 0; i < is.length; i++) System.out.print(is[i] + " "); System.out.println("");
    is = bonek.decrypt(is, ks);
    for(int i = 0; i < is.length; i++) System.out.print(is[i] + " "); System.out.println("");
  }
}
