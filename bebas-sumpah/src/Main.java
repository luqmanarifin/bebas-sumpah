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
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    
    /* convert string to byte[] and vice versa */
    String a = "asu";
    byte[] b = a.getBytes();
    for(byte i : b) System.out.print(i + " "); System.out.println("");
    a = new String(b);
    System.out.println(a);
    
    Kelas k = new Kelas();
    k.a().b();
  }
  
  public static class Kelas {
    public Kelas() {}
    public Kelas a() {
      System.out.println("a");
      return this;
    }
    public Kelas b() {
      System.out.println("b");
      return this;
    }
  }
}
