#include <jni.h>
#include <stdio.h>
#include "Scalar_ScalarMain.h"

JNIEXPORT jdouble JNICALL Java_Scalar_ScalarMain_multi01
          (JNIEnv *env, jobject thisObj, jdoubleArray doubleJNIArray1, jdoubleArray doubleJNIArray2) {
   // Step 1: Convert the incoming JNI jintarray to C's jint[]
   jdouble *inCArray1 = (*env)->GetDoubleArrayElements(env, doubleJNIArray1, 0);
//    if (NULL == inCArray1) return NULL;
   jsize length = (*env)->GetArrayLength(env, doubleJNIArray1);


   jdouble *inCArray2 = (*env)->GetDoubleArrayElements(env, doubleJNIArray2, 0);
//    if (NULL == inCArray2) return NULL;

   // Step 2: Perform its intended operations
   jint sum = 0;
   int i;
   for (i = 0; i < length; i++) {

      sum += inCArray1[i] * inCArray2[i];
   }
   (*env)->ReleaseDoubleArrayElements(env, doubleJNIArray1, inCArray1, 0); // release resources
    (*env)->ReleaseDoubleArrayElements(env, doubleJNIArray2, inCArray2, 0); // release resources

   // Step 3: Convert the C's Native jdouble[] to JNI jdoublearray, and return
   jdouble jresult = (jdouble) sum;
   return jresult;
}

 JNIEXPORT jdouble JNICALL Java_Scalar_ScalarMain_multi02
           (JNIEnv *env, jobject thisObj, jdoubleArray doubleJNIArray1) {
    // Step 1: Convert the incoming JNI jintarray to C's jint[]
    jdouble *inCArray1 = (*env)->GetDoubleArrayElements(env, doubleJNIArray1, 0);
//     if (NULL == inCArray1) return NULL;
    jsize length = (*env)->GetArrayLength(env, doubleJNIArray1);

    jdouble * inCArray2;
    inCArray2 =(jdouble[3]){5.2,3.0,2.5};

    // Step 2: Perform its intended operations
    jint sum = 0;
    int i;
    for (i = 0; i < length; i++) {

       sum += inCArray1[i] * inCArray2[i];
    }
    (*env)->ReleaseDoubleArrayElements(env, doubleJNIArray1, inCArray1, 0); // release resources
    // delete inCArray2;
    // Step 3: Convert the C's Native jdouble[] to JNI jdoublearray, and return
    jdouble jresult = (jdouble) sum;
    return jresult;
 }