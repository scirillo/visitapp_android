import android.app.*
import android.view.*
import android.widget.*
import org.jetbrains.anko.*
import android.os.Bundle

class SomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)

        linearLayout {
            orientation = LinearLayout.HORIZONTAL
        
            textView {
                id = Ids.id
            }.lparams(width = wrapContent, height = wrapContent)
            textView {
                id = Ids.content
            }.lparams(width = wrapContent, height = wrapContent)
        }
    }

    private object Ids {
        val content = 1
        val id = 2
    }
}