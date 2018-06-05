var fun1 = function(name) {
    print('Hi there from Javascript, ' + name);
    return "greetings from javascript";
};

var fun2 = function (object) {
    print("JS Class Definition: " + Object.prototype.toString.call(object));
};

var MyJavaClass = Java.type('test.Main');
var MyClass = new MyJavaClass();

var result = MyClass.fun1("John Doe");
print(result);
