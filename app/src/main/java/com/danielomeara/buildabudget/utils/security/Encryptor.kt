package com.danielomeara.buildabudget.utils.security

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@Throws(NoSuchAlgorithmException::class)
fun encryptString(input: String): String {
    val md = MessageDigest.getInstance("SHA-256")

    val messageDigest = md.digest(input.toByteArray())

    val bigInt = BigInteger(1, messageDigest)

    return bigInt.toString(16)
}