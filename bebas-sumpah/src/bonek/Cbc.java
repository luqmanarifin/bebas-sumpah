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
public class Cbc {
  Keygen keygen = new Keygen();
  
  public Cbc() {
    
  }
  
  Block[] encrypt(Block[] plain, int[] k) {
    Block K = new Block(8, k);
    int[] key = K.bit;
    Block[] cip = new Block[plain.length];
    int[] pre = key;
    for(int i = 0; i < plain.length; i++) {
      key = keygen.nextByteKey(key);
      plain[i] = plain[i].xor(new Block(8, pre));
      cip[i] = plain[i].e_encrypt(new Block(8, key));
      pre = cip[i].bit;
    }
    return cip;
  }
  
  Block[] decrypt(Block[] cipher, int[] k) {
    Block K = new Block(8, k);
    int[] key = K.bit;
    Block[] pres = new Block[cipher.length];
    Block[] keys = new Block[cipher.length];
    Block[] plain = new Block[cipher.length];
    pres[0] = new Block(8, key);
    for(int i = 1; i < cipher.length; i++) {
      pres[i] = new Block(cipher[i - 1]);
    }
    for(int i = 0; i < cipher.length; i++) {
      key = keygen.nextByteKey(key);
      keys[i] = new Block(8, key);
    }
    for(int i = 0; i < cipher.length; i++) {
      Block tmp = cipher[i].e_decrypt(keys[i]);
      plain[i] = tmp.xor(pres[i]);
    }
    return plain;
  }
}
