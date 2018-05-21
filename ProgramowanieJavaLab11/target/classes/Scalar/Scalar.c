#include <jni.h>
#include <stdio.h>
#include "Scalar_ScalarMain.h"

JNIEXPORT jobject JNICALL Java_Scalar_ScalarMain_multi01
          (JNIEnv *env, jobject thisObj, jobjectArray doubleJNIArray1, jobjectArray doubleJNIArray2) {
   // Step 1: Convert the incoming JNI jintarray to C's jint[]

   jclass classDouble = (*env)->FindClass(env, "java/lang/Double");

   jmethodID midDouble = (*env)->GetMethodID(env, classDouble, "doubleValue", "()D");

   jsize length = (*env)->GetArrayLength(env, doubleJNIArray1);

   jdouble sum = 0;
   int i;
   for (i = 0; i < length; i++) {
     jobject objDouble1 = (*env)->GetObjectArrayElement(env, doubleJNIArray1, i);
     jdouble value1 = (*env)->CallDoubleMethod(env, objDouble1, midDouble);
     jobject objDouble2 = (*env)->GetObjectArrayElement(env, doubleJNIArray2, i);
     jdouble value2 = (*env)->CallDoubleMethod(env, objDouble2, midDouble);
     sum += value1 * value2;
   }

  jmethodID midInit = (*env)->GetMethodID(env, classDouble, "<init>", "(D)V");
   jobject objSum = (*env)->NewObject(env, classDouble, midInit, sum);
   return objSum;
}

 JNIEXPORT jobject JNICALL Java_Scalar_ScalarMain_multi02
           (JNIEnv *env, jobject thisObj, jobjectArray doubleJNIArray1) {

  jclass classDouble = (*env)->FindClass(env, "java/lang/Double");

   jmethodID midDouble = (*env)->GetMethodID(env, classDouble, "doubleValue", "()D");
   jmethodID midInit = (*env)->GetMethodID(env, classDouble, "<init>", "(D)V");

   jsize length = (*env)->GetArrayLength(env, doubleJNIArray1);

    jdouble * inCArray2;
    inCArray2 =(jdouble[3]){5.2,3.0,2.5};

    jdouble sum = 0;
    int i;
    for (i = 0; i < length; i++) {
      jobject objDouble1 = (*env)->GetObjectArrayElement(env, doubleJNIArray1, i);
      jdouble value1 = (*env)->CallDoubleMethod(env, objDouble1, midDouble);
      sum += value1 * inCArray2[i];
    }

  
   jobject objSum = (*env)->NewObject(env, classDouble, midInit, sum);
   return objSum;
 }

  JNIEXPORT void JNICALL Java_Scalar_ScalarMain_multi03
            (JNIEnv *env, jobject thisObj) {

    jclass scalarClass = (*env)->GetObjectClass(env, thisObj);
    jclass classDouble = (*env)->FindClass(env, "java/lang/Double");

    jmethodID midInit = (*env)->GetMethodID(env, classDouble, "<init>", "(D)V");

    jfieldID fidNumber1 = (*env)->GetFieldID(env, scalarClass, "a", "[Ljava/lang/Double;");
    jfieldID fidNumber2 = (*env)->GetFieldID(env, scalarClass, "b", "[Ljava/lang/Double;");

    char str[128];
    int size;
    int i;
    double d;

    printf("Liczba elementów wektor 1:\n");
    fflush(stdout);
    scanf("%s", str);
    size = atoi(str);
    jobjectArray outObjDoubleArrayA = (*env)->NewObjectArray(env,size, classDouble, NULL);
    fflush(stdout);

    for(i = 0; i<size;i++){
    printf("Wartość elementu %d \n",i);
    fflush(stdout);
    scanf("%s", str);
    sscanf(str, "%lf", &d);
    jobject newDoubleObject1 = (*env)->NewObject(env, classDouble, midInit, d);
    (*env)->SetObjectArrayElement(env, outObjDoubleArrayA, i, newDoubleObject1);
    }
 
    printf("Wektor 2:\n");
    jobjectArray outObjDoubleArrayB = (*env)->NewObjectArray(env, size, classDouble, NULL);
    fflush(stdout);
 
    for(i = 0; i<size;i++){
    printf("Wartość elementu %d \n",i);
    fflush(stdout);
    scanf("%s", str);
    sscanf(str, "%lf", &d);
    jobject newDoubleObject2 = (*env)->NewObject(env, classDouble, midInit, d);
    (*env)->SetObjectArrayElement(env, outObjDoubleArrayB, i, newDoubleObject2);
    }

    (*env)->SetObjectField(env,thisObj, fidNumber1, outObjDoubleArrayA);
    (*env)->SetObjectField(env,thisObj, fidNumber2, outObjDoubleArrayB);
 
     jmethodID midMulti04 = (*env)->GetMethodID(env, scalarClass, "multi04", "()V");
     (*env)->CallVoidMethod(env,thisObj, midMulti04);
 
     return;

  }