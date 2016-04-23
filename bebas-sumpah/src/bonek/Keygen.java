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
public class Keygen {
  public static boolean created = false;
  public static int[] rev_byte_table = null;
  public static int[] rev_hex_table = null;
  
  public Keygen() {
    if(!created) {
      init();
      created = true;
    }
  }
  
  public void init() {
    rev_byte_table = new int[256];
    rev_hex_table = new int[16];
    for(int i = 0; i < 256; i++) rev_byte_table[Global.byte_table[i]] = i;
    for(int i = 0; i < 16; i++) rev_hex_table[Global.hex_table[i]] = i;
  }
  
  public int[] nextHexKey(int[] key) {
    int[] p = new int[Global.BLOCK_SIZE];
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      p[i] = Global.hex_table[key[i]];
    }
    return p;
  }
  
  public int[] nextByteKey(int[] key) {
    int[] p = new int[Global.BLOCK_SIZE];
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      p[i] = Global.byte_table[key[i]];
    }
    return p;
  }
  
  public int[] nextKey(int[] key, int bit_size) {
    if(bit_size == 4) {
      return nextHexKey(key);
    }
    if(bit_size == 8) {
      return nextByteKey(key);
    }
    // should do assertion here
    return null;
  }
  
  public int[] prevHexKey(int[] key) {
    int[] p = new int[Global.BLOCK_SIZE];
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      p[i] = rev_hex_table[key[i]];
    }
    return p;
  }
  
  public int[] prevByteKey(int[] key) {
    int[] p = new int[Global.BLOCK_SIZE];
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      p[i] = rev_byte_table[key[i]];
    }
    return p;
  }
  
  public int[] prevKey(int[] key, int bit_size) {
    if(bit_size == 4) {
      return prevHexKey(key);
    }
    if(bit_size == 8) {
      return prevByteKey(key);
    }
    return null;
  } 
}
