package api

import com.charly.visitapp.BuildConfig
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Santiago Cirillo on 11/3/17.
 */
object ApiClient {
    private val URL_API = BuildConfig.API_URL
    private val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes): Boolean {
            return f.declaringClass == RealmObject::class.java
        }

        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }
    }).create()

    fun build(): Retrofit {
        val client = OkHttpClient().newBuilder().build()
 //               .addInterceptor(HeadersInterceptor())
//                .addInterceptor(AuthenticationInterceptor())
//                .addInterceptor(ResponseInterceptor())
 //               .build()

        return Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
    }

    fun <T> create(klass: Class<T>): T {
        return build().create(klass)
    }
}