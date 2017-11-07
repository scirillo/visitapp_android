package ui.views

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.*
import com.charly.visitapp.R
import com.vistapp.visitapp.fragments.DateTimePickerFragment
import com.vistapp.visitapp.fragments.DoctorDetailFragment
import com.vistapp.visitapp.listeners.DateSelectedListener
import model.DoctorModel
import org.jetbrains.anko.*
import ui.ViewBinder
import extensions.bold

/**
 * Created by Santiago Cirillo on 11/2/17.
 */
class DetailFragmentView : ViewBinder<DoctorDetailFragment, DoctorModel, Boolean>, DateSelectedListener {
    lateinit var spDoctors: Spinner
    lateinit var mName: TextView
    lateinit var mDir: TextView
    lateinit var mClinic: TextView
    lateinit var mInfo: TextView
    lateinit var mPhone: TextView
    lateinit var mEmail: TextView
    lateinit var btnAddDoctor: Button
    lateinit var mDate: Button
    lateinit var mDoctorModel: DoctorModel
    override fun unbind(t: DoctorDetailFragment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bind(t: DoctorDetailFragment, u: DoctorModel?, b: Boolean?): View {
        return t.activity.UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                scrollView {
                    linearLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.VERTICAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            spDoctors = spinner {
                                //textH = "Seleccione el medico"
                                id = R.id.sp_doctors
                                //color= 0x05ab9a.opaque
                                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                                        setDataFields(adapter.getItem(pos) as DoctorModel)
                                    }

                                    override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
                                    }

                                }
                            }.lparams(width = matchParent, height = wrapContent) {
                                margin = dip(16)
                            }
                            verticalLayout {
                                gravity = Gravity.CENTER_HORIZONTAL
                                padding = dip(16)
                                textView {
                                    textResource = R.string.add_dr_title
                                    gravity = Gravity.CENTER
                                    textSize = 24f

                                }.lparams(width = matchParent, height = wrapContent) {
                                    topMargin = dip(5)
                                    bottomMargin = dip(5)
                                }
                                view {
                                    backgroundResource = R.color.baseBlack
                                }.lparams(width = matchParent, height = 1)
                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    weightSum = 2f

                                    textView {
                                        textResource = R.string.add_dr_name
                                        textSize = 18f
                                        typeface = bold
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 0.5F
                                    }
                                    mName = editText {
                                        id = R.id.item_value_name
                                        inputType = 1
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 1.45F
                                    }
                                }.lparams(width = matchParent, height = matchParent)
                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    weightSum = 2f

                                    textView {
                                        textResource = R.string.add_dr_dir
                                        textSize = 18f
                                        typeface = bold
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 0.5F
                                    }
                                    mDir = editText {
                                        id = R.id.item_value_dir
                                        inputType = 1
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 1.45F
                                    }
                                }.lparams(width = matchParent, height = matchParent)
                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    weightSum = 2f

                                    textView {
                                        textResource = R.string.add_dr_clinic
                                        textSize = 18f
                                        typeface = bold
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 0.5F
                                    }
                                    mClinic = editText {
                                        id = R.id.item_value_clinic
                                        inputType = 1
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 1.45F
                                    }
                                }.lparams(width = matchParent, height = matchParent)
                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    weightSum = 2f

                                    textView {
                                        textResource = R.string.add_dr_info
                                        textSize = 18f
                                        typeface = bold
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 0.5F
                                    }
                                    mInfo = editText {
                                        id = R.id.item_value_info
                                        inputType = 1
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 1.45F
                                    }
                                }.lparams(width = matchParent, height = matchParent)
                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    weightSum = 2f

                                    textView {
                                        textResource = R.string.add_dr_date
                                        textSize = 18f
                                        typeface = bold
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 0.5F
                                    }
                                    mDate = button {
                                        id = R.id.item_value_date
                                        onClick {
                                            showTimePickerDialog(t)
                                        }
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 1.45F
                                    }
                                }.lparams(width = matchParent, height = matchParent)
                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    weightSum = 2f
                                    textView {
                                        textResource = R.string.phone
                                        textSize = 18f
                                        typeface = bold
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 0.5F
                                    }
                                    mPhone = editText {
                                        // autoLink = 4
                                        id = R.id.item_value_phone
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 1.45F
                                    }
                                }.lparams(width = matchParent, height = matchParent)
                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    weightSum = 2f

                                    textView{
                                        textResource = R.string.email
                                        textSize = 18f
                                        typeface = bold
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 0.5F
                                    }
                                    mEmail = editText {
                                        //   autoLink = 2
                                        id = R.id.item_value_email
                                    }.lparams(width = 0, height = wrapContent) {
                                        weight = 1.45F
                                    }
                                }.lparams(width = matchParent, height = matchParent)
                            }.lparams(width = matchParent, height = wrapContent)
                        }.lparams(width = matchParent, height = matchParent)
                        linearLayout {
                            btnAddDoctor = button {
                                textResource = R.string.accept
                                id = R.id.btn_add_doctor
                                onClick {
                                    addNewDoctor(t, mDoctorModel)
                                }
                            }.lparams(width = 0, height = wrapContent) {
                                weight = 1F
                            }
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.BOTTOM
                            weight = 1F
                            bottomMargin = dip(16)
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = matchParent)
            }
            if(!b!!) disableFields(b)
            setDataFields(u!!)
        }.view
    }

    private fun showTimePickerDialog(t: DoctorDetailFragment) {
        val newFragment = DateTimePickerFragment(this)
        newFragment.show(t.childFragmentManager, "timePicker")
    }

    private fun checkFields(): Boolean {
        return !(TextUtils.isEmpty(mDate!!.text.toString()) || TextUtils.isEmpty(mName!!.text.toString()) ||
                TextUtils.isEmpty(mDir!!.text.toString()) || TextUtils.isEmpty(mClinic!!.text.toString()) ||
                TextUtils.isEmpty(mInfo!!.text.toString()))
    }

    private fun addNewDoctor(u: DoctorDetailFragment, model: DoctorModel) {
        if (checkFields()) {
            val newModel = DoctorModel(model!!.id, mName!!.text.toString(),
                    mDir!!.text.toString(), mInfo!!.text.toString(),
                    mClinic!!.text.toString(), mDate!!.text.toString(),
                    mPhone!!.text.toString(), mEmail!!.text.toString(),
                    "", true)
            u.addDoctor(newModel)
            //  } else {
            /*/Toast.makeText(activity, "Campos incorrectos", Toast.LENGTH_LONG).show()
        }*/
        }

    }

    override fun onDateSet(hourOfDay: Int, minute: Int) {
        if (mDate != null) mDate!!.text = hourOfDay.toString() + ":" + minute
    }

    private fun setDataFields(doctorModel: DoctorModel) {
        mName!!.text = doctorModel!!.name
        mDir!!.text = doctorModel!!.direction
        mClinic!!.text = doctorModel!!.clinic
        mInfo!!.text = doctorModel!!.note
        mDate!!.text = doctorModel!!.time
        mPhone!!.text = doctorModel!!.phone
        mEmail!!.text = doctorModel!!.email
        mDoctorModel = doctorModel
    }

    private fun disableFields(editable: Boolean) {
        mName!!.isEnabled = editable
        mDir!!.isEnabled = editable
        mClinic!!.isEnabled = editable
        mInfo!!.isEnabled = editable
        mDate!!.isEnabled = editable
        mPhone!!.isEnabled = editable
        mEmail!!.isEnabled = editable
        if (editable) {
            spDoctors!!.visibility = View.VISIBLE
            btnAddDoctor!!.visibility = View.VISIBLE
        } else {
            spDoctors!!.visibility = View.GONE
            btnAddDoctor!!.visibility = View.GONE
        }
    }

}