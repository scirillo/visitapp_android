package ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import api.Authentication
import com.charly.visitapp.R
import io.realm.Realm
import api.responses.ApiError
import model.User
import api.serializer.UserSerializer
import io.realm.RealmConfiguration
import io.realm.RealmQuery
import models.responses.LogInResponse
import org.jetbrains.anko.toast
import utils.RxBus
import ui.views.LogInView

/**
 * Created by Santiago Cirillo on 11/3/17.
 */

class LogInActivity : BaseActivity() {
    private lateinit var mView: LogInView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val realm = Realm.getDefaultInstance()
        val savedUser: User? = RealmQuery.createQuery(realm, User::class.java).findFirst()
        if(savedUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {
            mView = LogInView()
            setContentView(mView.bind(this, User(), false))
            setListeners()
        }
    }

    fun authorizeUser(user: User) {
        val userSerializer = UserSerializer()
        userSerializer.mUser = user
        Authentication.checkUserCredentials(userSerializer)
    }

    private fun setListeners() {
        RxBus.listen(LogInResponse::class.java).subscribe(
                { response ->
                    mView.spinner.visibility = View.INVISIBLE
                    saveUser(response)
                })
        RxBus.listen(ApiError::class.java).subscribe(
                { _ ->
                    mView.spinner.visibility = View.GONE
                    toast(R.string.signInError)
                    saveUser(null)
                })
    }

    private fun saveUser(user: LogInResponse?) {
        val realm = Realm.getDefaultInstance()
        if(user == null){
            var user1 = User()
            user1.mEmail = "santiago@hotmail.com"
            user1.mPassword = "password1"
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(user1)
            realm.commitTransaction()
        }
        startActivity(Intent(this, MainActivity::class.java))
    }
}

