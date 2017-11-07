package ui.activities

import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.charly.visitapp.R

/**
 * Created by Santiago Cirillo on 11/3/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main2, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId === android.R.id.home) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else
                finish() // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item)
    }
}