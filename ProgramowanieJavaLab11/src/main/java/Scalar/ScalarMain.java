package Scalar;

public class ScalarMain {

    static {
        System.load("C:\\Program Files\\Java\\jdk-9.0.4\\bin\\Scalar.dll"); // Load native library at runtime
        // hello.dll (Windows) or libhello.so (Unixes)
    }

    private double c;

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    /*
        zakładamy, że po stronie kodu natywnego wyliczony zostanie iloczyn skalarny dwóch wektorów
         */
    public native double multi01(double[] a, double[] b);

    /*
    zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej
     */
    public native double multi02(double[] a);

    /*
    zakładamy, że po stronie natywnej utworzone zostanie okienko na atrybuty,
    a po ich wczytaniu i przepisaniu do a,b obliczony zostanie wynik.
    Wynik powinna wyliczać metoda Javy multi04
    (korzystająca z parametrów a,b i wpisująca wynik do c).
     */
    public native void multi03();

    private void multi04(){
        // mnoży a i b, wynik wpisuje do c
    }

    public static void main(String args[])
    {
        double[] arr1 = {11.0, 12.5, 13.2};
        double[] arr2 = {5.2, 3.5, 2.5};

        Scalar.ScalarMain scalarMain = new Scalar.ScalarMain();
        scalarMain.setC(scalarMain.multi01(arr1, arr2));
        System.out.println("Result: " + scalarMain.getC());

        scalarMain.setC(scalarMain.multi02(arr1));
        System.out.println("Result 2: " + scalarMain.getC());
    }
}
