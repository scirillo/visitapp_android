package ui.viewlisteners

import android.view.View

/**
 * Created by Santiago Cirillo on 11/2/17.
 */
interface ViewBinder<in T, in U, in B> {
    fun bind(t: T, u: U,b: B): View
    fun unbind(t: T)
}