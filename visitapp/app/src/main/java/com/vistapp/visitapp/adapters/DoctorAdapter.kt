package com.vistapp.visitapp.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

import com.vistapp.visitapp.R
import com.vistapp.visitapp.listeners.RecyclerOnClickListener
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import com.vistapp.visitapp.model.DoctorModel

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

class DoctorAdapter(items: List<DoctorModel>, private val showButton: Boolean, private val showCheck: Boolean) : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {
    var values: List<DoctorModel>? = null
        private set
    private var mListener: RecyclerOnClickListener? = null
    private var fragmentListener: UpdateFragmentListener? = null
    private var context: Context? = null

    val itemCount: Int
        @Override
        get() = values!!.size()

    init {
        values = items
    }

    fun setFragmentListener(fragmentListener: UpdateFragmentListener) {
        this.fragmentListener = fragmentListener
    }

    fun remove(item: DoctorModel) {
        if (values!!.contains(item)) {
            values!!.remove(item)
            this.notifyDataSetChanged()
        }
    }

    fun setOnClickListener(listener: RecyclerOnClickListener) {
        this.mListener = listener
    }

    @Override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        val view = LayoutInflater.from(context).inflate(R.layout.dashbard_item, parent, false)
        return ViewHolder(view)
    }

    @Override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = values!![position]
        holder.item = mItem
        holder.mTitle.setText(mItem.getName())
        holder.mSubTitle1.setText(mItem.getDirection())
        holder.mSubTitle2.setText(mItem.getClinic())
        holder.mSubTitle3.setText(mItem.getNote())
        holder.mSubTitle4.setText(mItem.getTime())
        holder.mCheckbox.setChecked(false)
        holder.cv.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
    }

    fun add(doctorModel: DoctorModel) {
        values!!.add(doctorModel)
        this.notifyDataSetChanged()
    }

    fun addAll(doctorList: List<DoctorModel>?) {
        if (doctorList != null) {
            values = doctorList
            this.notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mTitle: TextView
        private val mSubTitle1: TextView
        private val mSubTitle2: TextView
        private val mSubTitle3: TextView
        private val mSubTitle4: TextView
        private val mButton: Button
        private val mCheckbox: CheckBox
        private val item: DoctorModel? = null
        private val cv: CardView

        init {
            mTitle = view.findViewById(R.id.item_title) as TextView
            mSubTitle1 = view.findViewById(R.id.item_subtitle1) as TextView
            mSubTitle2 = view.findViewById(R.id.item_subtitle2) as TextView
            mSubTitle3 = view.findViewById(R.id.item_subtitle3) as TextView
            mSubTitle4 = view.findViewById(R.id.item_subtitle4) as TextView
            mCheckbox = view.findViewById(R.id.checkBox) as CheckBox
            cv = view.findViewById(R.id.cv) as CardView
            mSubTitle1.setOnClickListener(object : View.OnClickListener() {
                @Override
                fun onClick(v: View) {
                    fragmentListener!!.navigateToMaps(item)
                }
            })
            if (showCheck) {
                mCheckbox.setVisibility(View.VISIBLE)
            } else
                mCheckbox.setVisibility(View.GONE)
            mCheckbox.setOnClickListener(object : View.OnClickListener() {
                @Override
                fun onClick(v: View) {
                    if (mCheckbox.isChecked()) {
                        cv.setBackgroundColor(ContextCompat.getColor(context, R.color.card_selected))
                    } else {
                        cv.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    }
                    if (fragmentListener != null) fragmentListener!!.itemChecked(item, mCheckbox.isChecked())
                }
            })
            mButton = view.findViewById(R.id.btn_more_info) as Button
            if (showButton) {
                mButton.setVisibility(View.VISIBLE)
            } else
                mButton.setVisibility(View.GONE)
            mButton.setOnClickListener(object : View.OnClickListener() {
                @Override
                fun onClick(v: View) {
                    if (fragmentListener != null) fragmentListener!!.updateAdapter(item)
                }
            })
            view.setOnClickListener(object : View.OnClickListener() {
                @Override
                fun onClick(v: View) {
                    if (mListener != null) mListener!!.onClick(v, getLayoutPosition())
                }
            })
        }
    }
}
