package com.example.tinkoffwatcher.network

import android.annotation.SuppressLint
import android.util.Log
import com.example.tinkoffwatcher.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException

fun provideOkHttpClient(authHeaderInterceptor: AuthorizationHeaderInterceptor) =
    if (BuildConfig.BUILD_TYPE == "debug") {
        Log.d("WARNING", "The app will use unsafe OkHttpClient")
        getUnsafeOkHttpClient()?.addInterceptor(authHeaderInterceptor)
            ?.addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            ?.build()
    } else {
        OkHttpClient.Builder().addInterceptor(authHeaderInterceptor)
            .build()
    }

fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
    return try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(

            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            }
        )

        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }
        builder
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}