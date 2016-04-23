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
public class Feistel {
  Keygen keygen = new Keygen();
  
  public Feistel() {
    
  }
  
  public Block encrypt(Block plain, int[] key) {
    Block fi = new Block(4);
    Block se = new Block(4);
    Block newfi = new Block(4);
    Block newse = new Block(4);
    Pair<Block, Block> tmp = plain.split();
    fi = tmp.first;
    se = tmp.second;
    newfi = se;
    newse = se.f_encrypt(new Block(4, key)).xor(fi);
    Block ret = new Block(8);
    ret.combine(newfi, newse);
    return ret;
  }
  
  public Block decrypt(Block cipher, int[] key) {
    Block fi = new Block(4);
    Block se = new Block(4);
    Block newfi = new Block(4);
    Block newse = new Block(4);
    Pair<Block, Block> tmp = cipher.split();
    fi = tmp.first;
    se = tmp.second;
    newse = fi;
    newfi = fi.f_encrypt(new Block(4, key)).xor(se);
    Block ret = new Block(8);
    ret.combine(newfi, newse);
    return ret;
  }
}
