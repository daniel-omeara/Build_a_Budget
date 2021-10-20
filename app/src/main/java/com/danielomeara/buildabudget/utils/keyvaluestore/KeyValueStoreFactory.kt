package com.danielomeara.buildabudget.utils.keyvaluestore

import android.content.Context

class KeyValueStoreFactory {
    companion object {
        fun create(context: Context, storeSecurely: Boolean, name: String? = null): KeyValueStore {
            return if (storeSecurely) {
                if (name.isNullOrEmpty()) {
                    SecurePrefKeyValueStore(context)
                } else {
                    SecurePrefKeyValueStore(context, name)
                }
            } else {
                if (name.isNullOrEmpty()) {
                    PrefKeyValueStore(context)
                } else {
                    PrefKeyValueStore(context, name)
                }
            }
        }
    }
}