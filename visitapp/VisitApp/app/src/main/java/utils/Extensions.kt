package utils

/**
 * Created by Santiago Cirillo on 11/2/17.
 */

import android.content.Context
import android.content.Intent
import android.graphics.Point
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
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.wrapContent

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

fun Intent.defaultCategory(): Intent = apply { addCategory(Intent.CATEGORY_DEFAULT) }

fun View.actionBarSize(): Int {
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        return TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
    return 0
}

fun ViewGroup.setSelectableItemBackground() {
    val typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackground))
    setBackgroundResource(typedArray.getResourceId(0, 0))
    typedArray.recycle()
    isClickable = true
}

fun ViewGroup.setMinimumListHeight() {
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.listPreferredItemHeight, tv, true)) {
        minimumHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
}

fun <T : View> T.widthProcent(procent: Int): Int =
        getAppUseableScreenSize().x.toFloat()
                .times(procent.toFloat() / 100).toInt()

fun <T : View> T.heightProcent(procent: Int): Int = getAppUseableScreenSize().y.toFloat().times(procent.toFloat() / 100).toInt()

fun <T : View> T.getAppUseableScreenSize(): Point {
    val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

fun <T : android.view.View> T.collapseLayoutParams(
        width: kotlin.Int = wrapContent, height: kotlin.Int = wrapContent,
        init: android.support.design.widget.CollapsingToolbarLayout.LayoutParams.() -> kotlin.Unit = { }): T {
    val layoutParams = android.support.design.widget.CollapsingToolbarLayout.LayoutParams(width, height)
    layoutParams.init()
    this@collapseLayoutParams.layoutParams = layoutParams
    return this
}


