package gurankio;

import java.util.Stack;

public class B extends A {

    double b;

    public B(int a, double b) {
        super(a);
        this.b = b;
    }

    public B() {
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    @Override
    public void serialize(Stack<String> tokens) {
        super.serialize(tokens);
        tokens.add(String.valueOf(b));
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        b = Double.parseDouble(tokens.pop());
        return super.parse(tokens);
    }

    @Override
    public String toString() {
        return "B{" +
                "a=" + a +
                ", b=" + b +
                "} ";
    }
}
