package api

import android.util.Log
import api.responses.ApiError
import api.serializer.UserSerializer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import utils.RxBus

/**
 * Created by Santiago Cirillo on 11/3/17.
 */
object Authentication {

    fun checkUserCredentials(credentials: UserSerializer) {
        val service = ApiClient.create(ApiInterface::class.java)

        service.signIn(credentials)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            RxBus.publish(response)
                        },
                        { error ->
                            RxBus.publish(ApiError())
                            Log.e("Error", error.message)
                        })
    }
}
