package duotai;

public class B extends A {
    public String show(B obj) {
        return ("B and B");
    }

    public String show(A obj) {
        return ("B and A");
    }

    public void print() {
        System.out.println("B print");
    }
}