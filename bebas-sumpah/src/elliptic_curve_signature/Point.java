package elliptic_curve_signature;

import java.math.BigInteger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luqman A. Siswanto
 */
public class Point {
  public BigInteger x;
  public BigInteger y;
  
  public Point() {
    x = BigInteger.valueOf(0);
    y = BigInteger.valueOf(0);
  }
  public Point(boolean isZero) {
    x = BigInteger.valueOf(-1);
    y = BigInteger.valueOf(-1);
  }
  public Point(BigInteger x, BigInteger y) {
    this.x = x;
    this.y = y;
  }
  public Point(Point p) {
    this.x = p.x;
    this.y = p.y;
  }
  public boolean isZero() {
    return this.x.compareTo(BigInteger.valueOf(-1)) == 0 && this.y.compareTo(BigInteger.valueOf(-1)) == 0;
  }
  public boolean equals(Point p) {
    return this.x.equals(p.x) && this.y.equals(p.y);
  }
  @Override
  public String toString() {
    return "{" + this.x.toString(16) + "\n" + this.y.toString(16) + ")\n";
  }
  
  // this + p
  public Point add(Point p) {
    if(this.isZero()) return new Point(p);
    if(p.isZero()) return new Point(this);
    if(this.equals(p.negate())) return new Point(true);
    if(this.equals(p)) {      // find gradien using tangent, different method than standard adding
      if(this.y.compareTo(BigInteger.valueOf(0)) == 0) return new Point(true);
      BigInteger d = BigInteger.valueOf(3).multiply(this.x).mod(Constant.p).multiply(this.x).mod(Constant.p).add(Constant.a).multiply(Constant.inv(BigInteger.valueOf(2).multiply(this.y))).mod(Constant.p);
      BigInteger ra = d.multiply(d).subtract(BigInteger.valueOf(2).multiply(this.x)).mod(Constant.p);
      if(ra.compareTo(BigInteger.valueOf(0)) == -1) ra = ra.add(Constant.p);
      BigInteger rb = d.multiply(this.x.subtract(ra)).subtract(this.y).mod(Constant.p);
      if(rb.compareTo(BigInteger.valueOf(0)) == -1) rb = rb.add(Constant.p);
      return new Point(ra, rb);
    }
    BigInteger d = this.y.subtract(p.y).multiply(Constant.inv(this.x.subtract(p.x))).mod(Constant.p);
    BigInteger ra = d.multiply(d).subtract(this.x).subtract(p.x).mod(Constant.p);
    if(ra.compareTo(BigInteger.valueOf(0)) == -1) ra = ra.add(Constant.p);
    BigInteger rb = (d.multiply(this.x.subtract(ra)).subtract(this.y)).mod(Constant.p);
    if(rb.compareTo(BigInteger.valueOf(0)) == -1) rb = rb.add(Constant.p);
    return new Point(ra, rb);
  }
  
  // this - p
  public Point subtract(Point p) {
    return add(p.negate());
  }
  
  // -this
  public Point negate() {
    if(isZero()) return new Point(this);
    return new Point(this.x, this.y.negate().add(Constant.p).mod(Constant.p));
  }
  
  // p * k
  public static Point multiply(Point p, BigInteger k) {
    if(k.compareTo(BigInteger.valueOf(0)) == 0) return new Point(true);
    Point temp = multiply(p, k.divide(BigInteger.valueOf(2)));
    temp = temp.add(temp);
    if(k.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(1)) == 0) {
      temp = temp.add(p);
    }
    return temp;
  }
}
