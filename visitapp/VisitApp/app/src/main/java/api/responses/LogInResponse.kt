package models.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class LogInResponse : RealmObject() {

    @SerializedName("token")
    @Expose
    open var mToken: String? = null

    @SerializedName("email")
    @Expose
    open var mEmail: String? = null

    @SerializedName("user_id")
    @Expose
    open var mUserId: String? = null

    @SerializedName("name")
    @Expose
    open var mName: String? = null
}