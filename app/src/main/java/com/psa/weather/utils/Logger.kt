package com.psa.weather.utils

import android.util.Log
import com.psa.weather.Constants

fun <T> i(tag: String, msg: T) {
    Log.i(Constants.LOG_TAG + tag, msg.toString() + "")
}

fun <T> d(tag: String, msg: T) {
    Log.d(Constants.LOG_TAG + tag, msg.toString() + "")
}

fun <T> v(tag: String, msg: T) {
    Log.v(Constants.LOG_TAG + tag, msg.toString() + "")
}

fun <T> e(tag: String, msg: T) {
    Log.e(Constants.LOG_TAG + tag, msg.toString() + "")
}

fun <T> w(tag: String, msg: T) {
    Log.w(Constants.LOG_TAG + tag, msg.toString() + "")
}

fun <T> wtf(tag: String, msg: T) {
    Log.wtf(Constants.LOG_TAG + tag, msg.toString() + "")
}

fun <T> i(msg: T) {
    Log.i(Constants.APP_NAME, msg.toString() + "")
}

fun <T> d(msg: T) {
    Log.d(Constants.APP_NAME, msg.toString() + "")
}

fun <T> v(msg: T) {
    Log.v(Constants.APP_NAME, msg.toString() + "")
}

fun <T> e(msg: T) {
    Log.e(Constants.APP_NAME, msg.toString() + "")
}

fun <T> w(msg: T) {
    Log.w(Constants.APP_NAME, msg.toString() + "")
}

fun <T> wtf(msg: T) {
    Log.wtf(Constants.APP_NAME, msg.toString() + "")
}
