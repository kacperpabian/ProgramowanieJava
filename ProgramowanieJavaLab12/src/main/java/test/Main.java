package test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

public class Main {

    static String fun1(String name) {
        System.out.format("Hi there from Java, %s", name);
        return "greetings from java";
    }

    static void fun2(Object object) {
        System.out.println(object.getClass());
    }

    public static void main (String args []) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader("scripts/test0.js"));

        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        System.out.println(result);
        System.out.println(result.getClass());

        invocable.invokeFunction("fun2", new Date());
// [object java.util.Date]

        invocable.invokeFunction("fun2", LocalDateTime.now());
// [object java.time.LocalDateTime]

//        test.Main.fun2(123);
//// class java.lang.Integer
//
//        test.Main.fun2(49.99);
//// class java.lang.Double
//
//        test.Main.fun2(true);
//// class java.lang.Boolean
//
//        test.Main.fun2("hi there");
//// class java.lang.String
//
//        test.Main.fun2(new Number(23));
//// class jdk.nashorn.internal.objects.NativeNumber
//
//        test.Main.fun2(new Date());
//// class jdk.nashorn.internal.objects.NativeDate
//
//        test.Main.fun2(new RegExp());
//// class jdk.nashorn.internal.objects.NativeRegExp
//
//        test.Main.fun2({foo: 'bar'});
// class jdk.nashorn.internal.scripts.JO4
//        invocable.invokeFunction("fun2", new Person());
// [object com.winterbe.java8.Person]
    }
}
