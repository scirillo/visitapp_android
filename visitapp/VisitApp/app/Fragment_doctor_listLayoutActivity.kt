import android.app.*
import android.view.*
import android.widget.*
import org.jetbrains.anko.*
import android.os.Bundle

class SomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)

        relativeLayout {
            backgroundResource = R.color.background_list
        
            android.support.v7.widget.RecyclerView {
                id = Ids.list
                app:layoutManager = LinearLayoutManager
            }.lparams(width = matchParent, height = matchParent) {
                leftMargin = dip(16)
                topMargin = dip(16)
                rightMargin = dip(16)
            }
            include<View>(R.layout.spinner_layout).lparams(width = wrapContent, height = wrapContent)
            android.support.design.widget.FloatingActionButton {
                id = Ids.fab
                tint = android.R.color.white
                app:srcCompat = @drawable/ic_person_add_black_24dp
            }.lparams(width = wrapContent, height = wrapContent) {
                alignParentRight()
                alignParentBottom()
                alignParentEnd()
            }
        }
    }

    private object Ids {
        val fab = 1
        val list = 2
    }
}