package com.danielomeara.buildabudget.utils.keyvaluestore

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson




class PrefKeyValueStore(context: Context, fileName: String = PREF_FILE) : KeyValueStore {

    private val preferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    override fun getString(key: String, fallback: String?): String? {
        return try {
            preferences.getString(key, fallback)
        } catch (e: ClassCastException) {
            fallback
        }
    }

    override fun putString(key: String, value: String?) {
        preferences.editAndApply {
            putString(key, value)
        }
    }

    override fun getBoolean(key: String, fallback: Boolean): Boolean {
        return try {
            preferences.getBoolean(key, fallback)
        } catch (e: ClassCastException) {
            fallback
        }
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferences.editAndApply {
            putBoolean(key, value)
        }
    }

    override fun getInt(key: String, fallback: Int): Int {
        return try {
            preferences.getInt(key, fallback)
        } catch (e: ClassCastException) {
            fallback
        }
    }

    override fun putInt(key: String, value: Int) {
        preferences.editAndApply {
            putInt(key, value)
        }
    }

    override fun getLong(key: String, fallback: Long): Long {
        return try {
            preferences.getLong(key, fallback)
        } catch (e: ClassCastException) {
            fallback
        }
    }

    override fun putLong(key: String, value: Long) {
        preferences.editAndApply {
            putLong(key, value)
        }
    }

    override fun getFloat(key: String, fallback: Float): Float {
        return try {
            preferences.getFloat(key, fallback)
        } catch (e: ClassCastException) {
            fallback
        }
    }

    override fun putFloat(key: String, value: Float) {
        preferences.editAndApply {
            putFloat(key, value)
        }
    }

    override fun <T : Any> getSerializable(key: String, fallback: T?, typeParameterClass: Class<T>): T? {
        return try {
            val gson = Gson()
            val json: String? = getString(key, gson.toJson(fallback))
            gson.fromJson(json, typeParameterClass)
        } catch(e: ClassCastException) {
            fallback
        }
    }

    override fun putSerializable(key: String, value: Any) {
        val gson = Gson()
        val json: String = gson.toJson(value)
        putString(key, json)
    }

    override fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    override fun remove(key: String) {
        preferences.editAndApply {
            remove(key)
        }
    }

    override fun removeAll() {
        preferences.editAndApply {
            clear()
        }
    }

    companion object {
        private const val PREF_FILE = "com.danielomeara.buildabudget.utils.keyvaluestore.preferences"
    }
}