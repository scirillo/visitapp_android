package api

import api.serializer.UserSerializer
import models.responses.LogInResponse
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by Santiago Cirillo on 11/3/17.
 */

interface ApiInterface {
    @POST("users")
    fun signUp(@Body user: UserSerializer): Observable<LogInResponse>

    @POST("users/sign_in")
    fun signIn(@Body user: UserSerializer): Observable<LogInResponse>
}
