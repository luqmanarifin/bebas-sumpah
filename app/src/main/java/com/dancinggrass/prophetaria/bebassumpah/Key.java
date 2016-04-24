package com.dancinggrass.prophetaria.bebassumpah;

/**
 * Created by dancinggrass on 4/24/16.
 */
public class Key {
    String x;
    String y;
    String start;

    String address;

    public Key() {

    }

    public Key(String address, String x, String y, String start) {
        this.x = x;
        this.y = y;
        this.start = start;
        this.address = address;
    }

    public String getStart() {
        return start;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }
}
