package com.danielomeara.buildabudget.utils.keyvaluestore

interface KeyValueStore {
    fun getString(key: String, fallback: String?): String?
    fun putString(key: String, value: String?)

    fun getBoolean(key: String, fallback: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)

    fun getInt(key: String, fallback: Int): Int
    fun putInt(key: String, value: Int)

    fun getLong(key: String, fallback: Long): Long
    fun putLong(key: String, value: Long)

    fun getFloat(key: String, fallback: Float): Float
    fun putFloat(key: String, value: Float)

    fun <T: Any> getSerializable(key: String, fallback: T?, typeParameterClass: Class<T>): T?
    fun putSerializable(key: String, value: Any)

    fun contains(key: String): Boolean
    fun remove(key: String)
    fun removeAll()
}