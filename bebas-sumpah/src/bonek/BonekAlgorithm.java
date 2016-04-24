/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonek;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luqman A. Siswanto
 */
public class BonekAlgorithm {
  Keygen keygen = new Keygen();
  Cbc cbc = new Cbc();
  Feistel feistel = new Feistel();
  
  Block[] encrypt(Block[] _plain, int[] K) {
    Block[] plain = new Block[_plain.length];
    for(int i = 0; i < plain.length; i++) {
      plain[i] = _plain[i];
    }
    Block key = new Block(8, K);
    Block ori_key = new Block(key);
    for(int i = 0; i < plain.length; i++) {
      plain[i] = plain[i].xor(ori_key);
    }
    for(int round = 0; round < 10; round++) {
      key.bit = keygen.nextByteKey(key.bit);
      plain = cbc.encrypt(plain, key.bit);
      for(int f = 0; f < 2; f++) {
        key.bit = keygen.nextByteKey(key.bit);
        Block keyfi = new Block(4);
        Block keyse = new Block(4);
        Pair<Block, Block> tmp = key.split();
        keyfi = tmp.first;
        keyse = tmp.second;
        for(int i = 0; i < plain.length; i++) {
          plain[i] = feistel.encrypt(plain[i], keyfi.bit);
          plain[i] = feistel.encrypt(plain[i], keyse.bit);
        }
      }
    }
    for(int i = 0; i < plain.length; i++) {
      plain[i] = plain[i].xor(ori_key);
    }
    return plain;
  }
  
  Block[] decrypt(Block[] _cipher, int[] K) {
    Block[] cipher = new Block[_cipher.length];
    for(int i = 0; i < cipher.length; i++) {
      cipher[i] = _cipher[i];
    }
    Block key = new Block(8, K);
    Block ori_key = new Block(key);
    for(int i = 0; i < 31; i++) {
      key.bit = keygen.nextByteKey(key.bit);
    }
    for(int i = 0; i < cipher.length; i++) {
      cipher[i] = cipher[i].xor(ori_key);
    }
    for(int round = 0; round < 10; round++) {
      for(int f = 0; f < 2; f++) {
        key.bit = keygen.prevByteKey(key.bit);
        Block keyfi = new Block(4);
        Block keyse = new Block(4);
        Pair<Block, Block> tmp = key.split();
        keyfi = tmp.first;
        keyse = tmp.second;
        for(int i = 0; i < cipher.length; i++) {
          cipher[i] = feistel.decrypt(cipher[i], keyse.bit);
          cipher[i] = feistel.decrypt(cipher[i], keyfi.bit);
        }
      }
      key.bit = keygen.prevByteKey(key.bit);
      cipher = cbc.decrypt(cipher, key.bit);
    }
    for(int i = 0; i < cipher.length; i++) {
      cipher[i] = cipher[i].xor(ori_key);
    }
    return cipher;
  }
  
  public int[] toArrayInt(Block[] b) {
    int[] ret = new int [b.length * Global.BLOCK_SIZE];
    for(int i = 0; i < b.length; i++) {
      for(int j = 0; j < Global.BLOCK_SIZE; j++) {
        ret[i * Global.BLOCK_SIZE + j] = b[i].bit[j];
      }
    }
    return ret;
  }
  
  public Block[] toArrayBlock(int[] a) {
    int len = a.length / Global.BLOCK_SIZE;
    len += (a.length % Global.BLOCK_SIZE != 0? 1 : 0);
    Block[] ret = new Block[len];
    for(int i = 0; i < len; i++) ret[i] = new Block(8);
    for(int i = 0; i < a.length; i++) {
      ret[i / Global.BLOCK_SIZE].bit[i % Global.BLOCK_SIZE] = a[i];
    }
    return ret;
  }
  
  /**
   * convert String to unsigned byte
   * since Java doesn't support all unsigned type, so there is no built-in function for this,
   * and we have to implement it at our own
   * @param s
   * @return 
   */
  public static int[] toByte(String s) {
    try {
      byte[] b = s.getBytes("UTF-8");
      int[] ret = new int[s.length()];
      for(int i = 0; i < ret.length; i++) {
        ret[i] = b[i] + 128;
      }
      return ret;
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
  
  /**
   * convert unsigned int to String
   * @param a
   * @return 
   */
  public static String toString(int[] a) {
    try {
      byte[] b = new byte[a.length];
      for(int i = 0; i < a.length; i++) {
        b[i] = (byte) (a[i] - 128);
      }
      return new String(b, "UTF-8");
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
  
  public static int[] toByteHex(String s) {
    int[] a = new int[s.length() / 2];
    for(int i = 0; i + i < s.length(); i++) {
      String sub = s.substring(i + i, i + i + 2);
      a[i] = Integer.parseInt(sub, 16);
    }
    return a;
  }
  
  public static String toStringHex(int[] a) {
    StringBuilder str = new StringBuilder();
    for(int i = 0; i < a.length; i++) {
      StringBuilder hex = new StringBuilder(Integer.toHexString(a[i]));
      if(hex.length() == 1) {
        hex = new StringBuilder("0" + hex.charAt(0));
      }
      str.append(hex);
    }
    return str.toString();
  }
  
  public int[] encrypt(int[] plain, int[] key) {
    Block[] b = toArrayBlock(plain);
    return toArrayInt(encrypt(b, key));
  }
  
  public int[] decrypt(int[] cipher, int[] key) {
    Block[] b = toArrayBlock(cipher);
    return toArrayInt(decrypt(b, key));
  }
  
  public String encrypt(String _plain, String _key) {
    int[] plain = toByte(_plain);
    int[] key = Keygen.normalize(toByte(_key));
    return toStringHex(encrypt(plain, key));
  }
  
  public String decrypt(String _cipher, String _key) {
    int[] cipher = toByteHex(_cipher);
    int[] key = Keygen.normalize(toByte(_key));
    return toString(decrypt(cipher, key));
  }
}
