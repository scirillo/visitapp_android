package ui.views

import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.charly.visitapp.R
import ui.adapters.DoctorAdapter
import com.vistapp.visitapp.fragments.DoctorListFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ui.ViewBinder

/**
 * Created by Santiago Cirillo on 11/6/17.
 */

class DoctorListView : ViewBinder<DoctorListFragment, DoctorAdapter, Boolean> {
    lateinit var list: RecyclerView
    private lateinit var emptyView: TextView
    lateinit var spinner: ProgressBar

    override fun bind(ui: DoctorListFragment, adapterList: DoctorAdapter?, b: Boolean?): View {
        return ui.activity.UI {
            relativeLayout {
                emptyView = textView("Say something outrageous.") {
                    textSize = 16f
                    typeface = Typeface.MONOSPACE
                }.lparams {
                    centerInParent()
                }
                spinner = progressBar {
                    visibility = View.GONE
                }.lparams(width = wrapContent, height = wrapContent){
                    centerInParent()
                }
                backgroundResource = R.color.background_list
                 list = recyclerView {
                    val orientation = LinearLayoutManager.VERTICAL
                    layoutManager = LinearLayoutManager(context, orientation, true)
                    overScrollMode = View.OVER_SCROLL_NEVER
                    adapter = adapterList
                    adapter.registerAdapterDataObserver(
                            object : RecyclerView.AdapterDataObserver() {
                                override fun onItemRangeInserted(start: Int, count: Int) {
                                    updateEmptyViewVisibility(this@recyclerView)
                                }

                                override fun onItemRangeRemoved(start: Int, count: Int) {
                                    updateEmptyViewVisibility(this@recyclerView)
                                }
                            })
                    onClick {

                    }
                    updateEmptyViewVisibility(this)
                }.lparams(width = matchParent, height = wrapContent) {
                   // above(inputEditText)
                }
                floatingActionButton {
                    id = R.id.fab
                    //= color(android.R.color.white)
                    visibility = View.GONE
                    imageResource = R.drawable.ic_person_add_black_24dp
                    onClick {
                        handleAddButtonClicked(ui)
                    }
                                  // tint = color(android.R.color.white)
                   // srcCompat = drawable(R.drawable.ic_person_add_black_24dp)
                }.lparams(width = wrapContent, height = wrapContent) {
                    alignParentRight()
                    alignParentBottom()
                    alignParentEnd()
                    verticalMargin = dip(16)
                    margin = dip(16)
                }
            }
        }.view
    }

    private fun handleAddButtonClicked(ui: DoctorListFragment) {
        ui.navigateToAddDoctor()
    }

    fun updateEmptyViewVisibility(recyclerView: RecyclerView) {
        if (doesListHaveItem(recyclerView)) {
            emptyView.visibility = View.GONE
        } else {
            emptyView.visibility = View.VISIBLE
        }
    }

    private fun doesListHaveItem(list: RecyclerView?) = getListItemCount(list) > 0

    private fun getListItemCount(list: RecyclerView?) = list?.adapter?.itemCount ?: 0

    override fun unbind(t: DoctorListFragment) {
    }
}
