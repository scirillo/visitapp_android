package ui.adapters

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
import com.charly.visitapp.R
import com.vistapp.visitapp.listeners.RecyclerOnClickListener
import com.vistapp.visitapp.listeners.UpdateFragmentListener
import model.DoctorModel
/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

class DoctorAdapter(items: ArrayList<DoctorModel>, private val showButton: Boolean, private val showCheck: Boolean) :
        RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {
    var values: ArrayList<DoctorModel> = items
    private var mListener: RecyclerOnClickListener? = null
    private var fragmentListener: UpdateFragmentListener? = null
    private var context: Context? = null


    override fun getItemCount(): Int {
        return values.size
    }

    fun setFragmentListener(fragmentListener: UpdateFragmentListener) {
        this.fragmentListener = fragmentListener
    }

    fun remove(item: DoctorModel) {
        if (values.contains(item)) {
            values.remove(item)
            this.notifyDataSetChanged()
        }
    }

    fun setOnClickListener(listener: RecyclerOnClickListener) {
        this.mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.dashbard_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = values[position]
        holder.item = mItem
        holder.mTitle.text = mItem.name
        holder.mSubTitle1.text = mItem.direction
        holder.mSubTitle2.text = mItem.clinic
        holder.mSubTitle3.text = mItem.note
        holder.mSubTitle4.text = mItem.time
        holder.mCheckbox.isChecked = false
        holder.cv.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
    }

    fun add(doctorModel: DoctorModel) {
        values.add(doctorModel)
        this.notifyDataSetChanged()
    }

    fun addAll(doctorList: ArrayList<DoctorModel>?) {
        if (doctorList != null) {
            values = doctorList
            this.notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTitle: TextView = view.findViewById(R.id.item_title)
        val mSubTitle1: TextView = view.findViewById(R.id.item_subtitle1)
        val mSubTitle2: TextView = view.findViewById(R.id.item_subtitle2)
        val mSubTitle3: TextView = view.findViewById(R.id.item_subtitle3)
        val mSubTitle4: TextView = view.findViewById(R.id.item_subtitle4)
        private val mButton: Button
        val mCheckbox: CheckBox = view.findViewById(R.id.checkBox)
        var item: DoctorModel? = null
        val cv: CardView = view.findViewById(R.id.cv)

        init {
            mSubTitle1.setOnClickListener { fragmentListener!!.navigateToMaps(item) }
            if (showCheck) {
                mCheckbox.visibility = View.VISIBLE
            } else
                mCheckbox.visibility = View.GONE
            mCheckbox.setOnClickListener {
                if (mCheckbox.isChecked) {
                    cv.setBackgroundColor(ContextCompat.getColor(context, R.color.card_selected))
                } else {
                    cv.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                }
                if (fragmentListener != null) fragmentListener!!.itemChecked(item, mCheckbox.isChecked)
            }
            mButton = view.findViewById(R.id.btn_more_info)
            if (showButton) {
                mButton.visibility = View.VISIBLE
            } else
                mButton.visibility = View.GONE
            mButton.setOnClickListener {
                if (fragmentListener != null) fragmentListener!!.updateAdapter(item!!)
            }
            view.setOnClickListener { v -> if (mListener != null) mListener!!.onClick(v, layoutPosition) }
        }
    }
}
