package NativeTest;

public class HelloJNI {

    static {
        System.load("C:\\Program Files\\Java\\jdk-9.0.4\\bin\\hello.dll"); // Load native library at runtime
        // hello.dll (Windows) or libhello.so (Unixes)
    }

    // Declare a native method sayHello() that receives nothing and returns void
    private native void sayHello();

    // Test Driver
    public static void main(String[] args) {
        new NativeTest.HelloJNI().sayHello();  // invoke the native method
    }

}
