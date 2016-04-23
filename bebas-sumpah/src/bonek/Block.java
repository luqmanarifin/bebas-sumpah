/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonek;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * Block is 128-bit, all constant defined in Global.java
 * @author Luqman A. Siswanto
 */
public class Block {
  int[] bit = null;       // a container array, consist of byte/hex of Block
  int BIT_SIZE;           // 8 for byte, 4 for hex 
  
  public Block() {
    bit = new int[Global.BLOCK_SIZE];
    BIT_SIZE = 8;
    Arrays.fill(bit, 0);
  }
  
  public Block(int n) {
    BIT_SIZE = n;
    bit = new int[Global.BLOCK_SIZE];
    Random rand = new Random();
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      bit[i] = 0; //rand.nextInt(1 << BIT_SIZE);
    }
  }
  
  public Block(int n, int[] b) {
    BIT_SIZE = n;
    bit = new int[Global.BLOCK_SIZE];
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      bit[i] = b[i];
    }
  }
  
  public Block(Block b) {
    BIT_SIZE = b.BIT_SIZE;
    bit = new int[Global.BLOCK_SIZE];
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      bit[i] = b.bit[i];
    }
  }
  
  public int get(int i) {
    return bit[i];
  }
  
  public void set(int i, int b) {
    bit[i] = b;
  }
  
  public Block add(Block b) {
    Block ret = new Block(BIT_SIZE);
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      ret.bit[i] = (this.bit[i] + b.bit[i]) % (1 << BIT_SIZE);
    }
    return ret;
  }
  
  public Block subtract(Block b) {
    Block ret = new Block(BIT_SIZE);
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      ret.bit[i] = (this.bit[i] - b.bit[i] + (1 << BIT_SIZE)) % (1 << BIT_SIZE);
    }
    return ret;
  } 
  
  public Block xor(Block b) {
    Block ret = new Block(BIT_SIZE);
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      ret.bit[i] = this.bit[i] ^ b.bit[i];
    }
    return ret;
  }
  
  public Block shiftLeft(int num) {
    Block ret = new Block(BIT_SIZE);
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      ret.bit[i] = this.bit[(i + num) % Global.BLOCK_SIZE];
    }
    return ret;
  } 
  
  public Block shiftRight(int num) {
    Block ret = new Block(BIT_SIZE);
    for(int i = 0; i < Global.BLOCK_SIZE; i++) {
      ret.bit[i] = this.bit[(i - num + Global.BLOCK_SIZE) % Global.BLOCK_SIZE];
    }
    return ret;
  }
  
  /* rotate the byte/hex matrix clockwise 90 degree */
  public Block rotate() {
    Block ret = new Block(BIT_SIZE);
    for(int t = 0; t < Global.BLOCK_SIZE; t++) {
      int i = t / Global.SIDE_SIZE;
      int j = t % Global.SIDE_SIZE;
      int newi = j;
      int newj = Global.SIDE_SIZE - 1 - i;
      ret.bit[newi * Global.SIDE_SIZE + newj] = this.bit[t];
    }
    return ret;
  }
  
  public Block transpose() {
    Block ret = new Block(BIT_SIZE);
    for(int t = 0; t < Global.BLOCK_SIZE; t++) {
      int i = t / Global.SIDE_SIZE;
      int j = t % Global.SIDE_SIZE;
      ret.bit[j * Global.SIDE_SIZE + i] = this.bit[t];
    }
    return ret;
  }
  
  public Block e_encrypt(Block key) {
    Block ret = new Block(BIT_SIZE);
    Keygen keygen = new Keygen();
    ret = this.shiftRight(6).xor(key.shiftLeft(9));
    ret = ret.rotate();
    ret = ret.add(key);
    ret = ret.rotate().rotate().rotate();
    ret = new Block(BIT_SIZE, keygen.nextKey(ret.bit, BIT_SIZE));    ret = ret.transpose();
    ret = ret.xor(key);
    ret = ret.rotate().rotate();
    return ret;
  }
  
  public Block e_decrypt(Block key) {
    Block ret = new Block(BIT_SIZE);
    Keygen keygen = new Keygen();
    ret = this.rotate().rotate();
    ret = ret.xor(key);
    ret = ret.transpose();
    ret = new Block(BIT_SIZE, keygen.prevKey(ret.bit, BIT_SIZE));
    ret = ret.rotate();
    ret = ret.subtract(key);
    ret = ret.rotate().rotate().rotate();
    ret = ret.xor(key.shiftLeft(9)).shiftLeft(6);
    return ret;
  }
  
  public Block f_encrypt(Block key) {
    return e_encrypt(key);
  }
  
  public Block f_decrypt(Block key) {
    return e_decrypt(key);
  }
  
  public Pair<Block, Block> split() {
    Block fi = new Block(4);
    Block se = new Block(4);
    for(int i = 0; i < Global.BLOCK_SIZE / 2; i++) {
      fi.bit[i * 2] = bit[i] % (1 << 4);
      fi.bit[i * 2 + 1] = (bit[i] / (1 << 4)) % (1 << 4);
    }
    for(int i = 0; i < Global.BLOCK_SIZE / 2; i++) {
      se.bit[i * 2] = bit[i + Global.BLOCK_SIZE/2] % (1 << 4);
      se.bit[i * 2 + 1] = (bit[i + Global.BLOCK_SIZE/2] >> 4) % (1 << 4);
    }
    return new Pair(fi, se);
  }
  
  public void combine(Block fi, Block se) {
    this.BIT_SIZE = 8;
    for(int i = 0; i < Global.BLOCK_SIZE; i += 2) {
      this.bit[i/2] = fi.bit[i] | (fi.bit[i + 1] << 4);
      this.bit[i/2 + Global.BLOCK_SIZE/2] = se.bit[i] | (se.bit[i + 1] << 4);
    }
  }
}
