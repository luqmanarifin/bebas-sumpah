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
    return BonekAlgorithm.toByte(s);
  }
  
  public String toString(int[] a) {
    return BonekAlgorithm.toString(a);
  }
  
  public static void main(String[] args) {
    BonekAlgorithm bonek = new BonekAlgorithm();
    String s = "inas nuha kenapa kamu kok lucu sih";
    String key = "kenapa";
    int[] is = toByte(s);
    int[] ks = Keygen.normalize(toByte(key));
    for(int i = 0; i < is.length; i++) System.out.print(is[i] + " "); System.out.println("");
    is = bonek.encrypt(is, ks);
    for(int i = 0; i < is.length; i++) System.out.print(is[i] + " "); System.out.println("");
    is = bonek.decrypt(is, ks);
    for(int i = 0; i < is.length; i++) System.out.print(is[i] + " "); System.out.println("");
  }
}
