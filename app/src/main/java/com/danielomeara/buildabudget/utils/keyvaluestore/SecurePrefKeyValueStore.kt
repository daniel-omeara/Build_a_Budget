package com.danielomeara.buildabudget.utils.keyvaluestore

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson

class SecurePrefKeyValueStore(context: Context, fileName: String = PREF_FILE) : KeyValueStore {

    private val preferences: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        preferences = EncryptedSharedPreferences.create(
            fileName,
            masterKeyAlias,
            context.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun getString(key: String, fallback: String?): String? {
        return preferences.getString(key, fallback)
    }

    override fun putString(key: String, value: String?) {
        preferences.editAndApply {
            putString(key, value)
        }
    }

    override fun getBoolean(key: String, fallback: Boolean): Boolean {
        return preferences.getBoolean(key, fallback)
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferences.editAndApply {
            putBoolean(key, value)
        }
    }

    override fun getInt(key: String, fallback: Int): Int {
        return preferences.getInt(key, fallback)
    }

    override fun putInt(key: String, value: Int) {
        preferences.editAndApply {
            putInt(key, value)
        }
    }

    override fun getLong(key: String, fallback: Long): Long {
        return preferences.getLong(key, fallback)
    }

    override fun putLong(key: String, value: Long) {
        preferences.editAndApply {
            putLong(key, value)
        }
    }

    override fun getFloat(key: String, fallback: Float): Float {
        return preferences.getFloat(key, fallback)
    }

    override fun putFloat(key: String, value: Float) {
        preferences.editAndApply {
            putFloat(key, value)
        }
    }

    override fun <T : Any> getSerializable(key: String, fallback: T?, typeParameterClass: Class<T>): T? {
        return try {
            val gson = Gson()
            val json: String? = getString(key, null)
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
        private const val PREF_FILE = "com.danielomeara.features.keyvaluestore.securepreferences"
    }
}