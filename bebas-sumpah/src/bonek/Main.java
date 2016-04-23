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
  
  public static void main(String[] args) {
    String s = "1234567890123456";
    String key = "1234567890titid6";
    Block is = new Block(8, toByte(s));
    Block ks = new Block(8, toByte(key));
    for(int i = 0; i < is.bit.length; i++) System.out.print(is.bit[i] + " "); System.out.println("");
    is = is.e_encrypt(ks);
    for(int i = 0; i < is.bit.length; i++) System.out.print(is.bit[i] + " "); System.out.println("");
    is = is.e_decrypt(ks);
    for(int i = 0; i < is.bit.length; i++) System.out.print(is.bit[i] + " "); System.out.println("");
  }
}
