import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Santiago Cirillo on 11/3/17.
 */
open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val realmConfig = RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}