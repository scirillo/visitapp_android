package api.serializer

import com.google.gson.annotations.SerializedName
import model.User

/**
 * Created by Santiago Cirillo on 11/3/17.
 */

open class UserSerializer {

    @SerializedName("user")
    open var mUser: User? = null
}
