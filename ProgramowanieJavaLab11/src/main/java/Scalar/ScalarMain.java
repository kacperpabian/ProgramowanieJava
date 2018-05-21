package Scalar;

public class ScalarMain {

    static {
        System.load("C:\\Program Files\\Java\\jdk-9.0.4\\bin\\Scalar.dll"); // Load native library at runtime
    }

    private Double[] a;
    private Double[] b;
    private Double c;

    public Double[] getA() {
        return a;
    }

    public void setA(Double[] a) {
        this.a = a;
    }

    public Double[] getB() {
        return b;
    }

    public void setB(Double[] b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public native Double multi01(Double[] a, Double[] b);

    public native Double multi02(Double[] a);

    public native void multi03();

    private void multi04(){
        double sum = 0;
        for (int i = 0; i < a.length; i++)
        {
            sum += a[i] * b[i];
        }
        setC(sum);

    }

    public static void main(String args[])
    {
        Double[] arr1 = {11.0, 12.5, 13.2};
        Double[] arr2 = {5.2, 3.5, 2.5};

        Scalar.ScalarMain scalarMain = new Scalar.ScalarMain();
        scalarMain.setC(scalarMain.multi01(arr1, arr2));
        System.out.println("Result: " + scalarMain.getC());

        scalarMain.setC(scalarMain.multi02(arr1));
        System.out.println("Result 2: " + scalarMain.getC());
//
       scalarMain.multi03();
        System.out.println("Result3: " + scalarMain.getC());
    }
}
