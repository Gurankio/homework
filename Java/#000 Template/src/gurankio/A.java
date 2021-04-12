package gurankio;

import gurankio.util.PersistentCSV;

import java.util.Stack;

public abstract class A implements PersistentCSV {

    int a;

    public A(int a) {
        this.a = a;
    }

    public A() {
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public void serialize(Stack<String> tokens) {
        tokens.push(String.valueOf(a));
    }

    @Override
    public boolean parse(Stack<String> tokens) {
        a = Integer.parseInt(tokens.pop());
        return true;
    }

    @Override
    public String toString() {
        return "A{" +
                "a=" + a +
                '}';
    }
}
