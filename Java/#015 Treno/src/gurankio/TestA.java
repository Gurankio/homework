package gurankio;

import gurankio.io.data.Persistent;

public class TestA implements Persistent {

    private int a;
    private int b;
    private double c;

    public TestA() {
    }

    public TestA(int a, int b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "TestA{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
