/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonek;

/**
 *
 * All global-defined constant defined here
 * @author Luqman A. Siswanto
 */
public class Global {
  static final int BLOCK_SIZE = 16;   // how many byte/hex per block
  static final int SIDE_SIZE = 4;     // SIDE_SIZE is always sqrt(BLOCK_SIZE)
  
  static final int[] byte_table = {202,254,240,205,200,109,147,72,150,146,249,63,88,136,81,106,161,44,241,164,16,184,87,193,112,79,133,60,159,125,13,248,189,39,231,212,139,127,55,124,244,107,179,18,35,175,215,174,71,68,208,151,117,116,167,65,235,95,83,134,221,163,27,158,234,252,80,198,91,206,49,54,45,110,178,224,246,157,85,182,237,140,21,3,59,43,76,94,176,102,48,0,20,229,50,52,250,228,236,181,138,186,238,213,223,185,93,29,253,180,201,100,169,58,77,156,25,22,4,166,141,41,177,56,84,131,137,78,148,104,197,90,103,14,42,190,5,24,119,154,7,33,11,145,128,36,168,152,74,64,51,207,171,31,243,233,132,38,10,34,162,46,187,67,2,8,53,165,245,144,114,98,1,9,217,123,61,160,242,130,101,69,219,226,210,135,173,23,172,70,122,108,32,199,15,218,247,6,66,183,251,214,225,75,255,192,222,26,28,188,82,105,155,121,220,47,126,111,30,227,194,113,89,57,195,216,99,211,17,239,12,37,86,96,40,19,209,129,120,153,92,203,149,196,142,73,170,232,97,230,191,143,115,62,204,118,11,15,10,8,3,1,13,6,14,4,12,2,9,7,0,5};
  
  static final int[] hex_table = {11,15,10,8,3,1,13,6,14,4,12,2,9,7,0,5};
}
