package com.imaginato.homeworkmvvm.exts

import android.content.Context
import android.widget.Toast
/**
 * Extension function to provide Toast on any String.
 */
fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}