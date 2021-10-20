package com.danielomeara.buildabudget.utils.keyvaluestore

import android.annotation.SuppressLint
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
fun SharedPreferences.editAndApply(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    this
        .edit()
        .block()
        .apply()
}