#include <jni.h>
#include <iostream>
#include "NativeTest_HelloJNI.h"
using namespace std;

// Implementation of native method sayHello() of NativeTest.HelloJNI class
JNIEXPORT void JNICALL Java_NativeTest_HelloJNI_sayHello(JNIEnv *env, jobject thisObj) {
	cout << "Hello World from C++!" << endl;
   return;
}