package extensions

/**
 * Created by Santiago Cirillo on 11/2/17.
 */


import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.util.TypedValue
import android.view.*
import android.widget.Button
import com.charly.visitapp.R
import logic.DoctorManager
import model.DoctorModel
import model.Location
import org.jetbrains.anko.custom.ankoView

enum class LoginError {
    EMAIL_ERROR(),
    PASSWORD_ERROR(),
    RE_PASSWORD_ERROR(),
    NAME_ERROR(),
    GENDER_ERROR(),
    VALID_USER()
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

inline fun ViewManager.appCompatEditText(theme: Int =
                                         R.style.TextStyle, init: AppCompatEditText.() -> Unit) = ankoView(::AppCompatEditText, theme, init)

inline fun ViewManager.textInputLayout(theme: Int = R.style.AppTextInputLayout, init: TextInputLayout.() -> Unit) = ankoView(::TextInputLayout, theme, init)

inline fun ViewManager.styledButton (theme: Int = R.style.AppButton, init: Button.() -> Unit) = ankoView(::Button, theme, init)

//inline fun ViewManager.styledDialog (theme: Int = R.style.AppButton, init: AlertDialogBuilder.() -> Unit) = ankoView(::AlertDialogBuilder, theme, init)

//inline fun ViewManager.indeterminateProgressDialogStyled (theme: Int = R.style.AppSpinnerLayout, init: it.() -> Unit) = ankoView(::ProgressDialog, theme, init)

val View.bold: Typeface
    get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-Bold.ttf")
val View.boldItalic: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-BoldItalic.ttf")
val View.extraBold: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-ExtraBold.ttf")
val View.extraBoldItalic: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-ExtraBoldItalic.ttf")
val View.italic: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-Italic.ttf")
val View.light: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-Light.ttf")
val View.lightItalic: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-LightItalic.ttf")
val View.regular: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-Regular.ttf")
val View.semibold: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-Semibold.ttf")
val View.semiboldItalic: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/OpenSans-SemiboldItalic.ttf")

fun View.drawable(@DrawableRes resource: Int): Drawable = ContextCompat.getDrawable(context, resource)

fun View.color(@ColorRes resource: Int): Int = ContextCompat.getColor(context, resource)

fun View.actionBarSize(): Int {
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        return TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
    return 0
}

fun checkUserFields(email: String, password: String): LoginError? {
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).find()) {
        return LoginError.EMAIL_ERROR
    } else if (password.length < 8) {
        return LoginError.PASSWORD_ERROR
    }
    return null
}

fun hardcodedList(context: Context): ArrayList<DoctorModel> {
    DoctorManager.getInstance(context).removeAllDoctor()
    val list = ArrayList<DoctorModel>()
    val m = DoctorModel("1", "Juan Luis Terra", "Av.Gral Flores 2212", "Insisitr con esto",
            "Dr.Selby", "12:05", "098421726", "jlterra@gmail.com", addDay(0), true)
    val m2 = DoctorModel("2", "Felipe Carlos", "Av.Gral Flores 2212", "Insisitr con esto",
            "Particular", "13:25", "+59898421726", "fpcelestr@gmail.com", addDay(0), false)
    val m3 = DoctorModel("3", "Maria Victoria Ruiz", "Av.Gral Flores 2212", "Insisitr con esto",
            "CASMU", "14:00", "094500412", "mvruizsa@hotmail.com", addDay(1), true)
    val m4 = DoctorModel("4", "Oscar Dualde Jimenez", "Av.Gral Flores 2212", "Insisitr con esto",
            "Hospital XZZ", "15:15", "094500412", "mvruizsa@hotmail.com", addDay(1), false)
    val m5 = DoctorModel("5", "Federica Cardozo Locomona", "Av.Gral Flores 2212", "Insisitr con esto",
            "Centro Evangelico", "17:30", "094500412", "mvruizsa@hotmail.com", addDay(1), true)
    val m6 = DoctorModel("6", "Jimena Ruiz", "Av.Gral Flores 2212", "Insisitr con esto",
            "Particular", "13:25", "098421726", "jlterra@gmail.com", addDay(2), true)
    val m7 = DoctorModel("7", "Pedro Alonso", "Av.Gral Flores 2212", "Insisitr con esto",
            "CASMU", "14:00", "098421726", "jlterra@gmail.com", addDay(2), false)
    val m8 = DoctorModel("8", "Marcos Jimenez", "Av.Gral Flores 2212", "Insisitr con esto",
            "Hospital XZZ", "15:15", "098421726", "jlterra@gmail.com", addDay(3), true)
    val m9 = DoctorModel("9", "Cecilia Cardozo", "Av.Gral Flores 2212", "Insisitr con esto",
            "Centro Evangelico", "17:30", "098421726", "jlterra@gmail.com", addDay(4), false)
    val l = Location("Dr.Selby", -34.8173171, -56.158871)
    val l2 = Location("Dr.Selby", -34.902545, -56.164862)
    val l3 = Location("Dr.Selby", -34.903460, -56.170355)
    val l4 = Location("Dr.Selby", -34.892197, -56.148917)
    val l5 = Location("Dr.Selby", -34.889662, -56.175353)
    val l6 = Location("Dr.Selby", -34.904235, -56.180245)
    val l7 = Location("Dr.Selby", -34.909514, -56.149260)
    val l8 = Location("Dr.Selby", -34.885368, -56.141106)
    val l9 = Location("Dr.Selby", -34.885227, -56.188768)
    m.location = l
    m2.location = l2
    m3.location = l3
    m4.location = l4
    m5.location = l5
    m6.location = l6
    m7.location = l7
    m8.location = l8
    m9.location = l9
    list.add(m)
    list.add(m2)
    list.add(m3)
    list.add(m4)
    list.add(m5)
    list.add(m6)
    list.add(m7)
    list.add(m8)
    list.add(m9)
    return list
}



