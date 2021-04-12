package gurankio;

import java.util.Stack;

public class C extends A {

    String c;

    public C(int a, String c) {
        super(a);
        this.c = c;
    }

    public C() {
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Override
    public void serialize(Stack<String> tokens) {
        super.serialize(tokens);
        tokens.add(c);
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        c = tokens.pop();
        return super.parse(tokens);
    }

    @Override
    public String toString() {
        return "C{" +
                "a=" + a +
                ", c='" + c + '\'' +
                "} ";
    }
}
