package com.gollersoft.ld23;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Pixel {
    final int r;
    final int g;
    final int b;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pixel pixel = (Pixel) o;

        if (b != pixel.b) return false;
        if (g != pixel.g) return false;
        if (r != pixel.r) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }
}
