import android.app.*
import android.view.*
import android.widget.*
import org.jetbrains.anko.*
import android.os.Bundle

class SomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)

        linearLayout {
            orientation = LinearLayout.VERTICAL
        
            scrollView {
                linearLayout {
                    gravity = Gravity.CENTER_HORIZONTAL
                    orientation = LinearLayout.VERTICAL
                
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                    
                        spinner {
                            hint = "Seleccione el medico"
                            id = Ids.sp_doctors
                            textColorHint = 0x05ab9a.opaque
                        }.lparams(width = matchParent, height = wrapContent) {
                            margin = dip(16)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER_HORIZONTAL
                            orientation = LinearLayout.VERTICAL
                            padding = dip(16)
                        
                            textView(R.string.add_dr_title) {
                                gravity = Gravity.CENTER
                                textSize = 24f
                                textStyle = 1
                            }.lparams(width = matchParent, height = wrapContent) {
                                topMargin = dip(5)
                                bottomMargin = dip(5)
                            }
                            view {
                                backgroundResource = R.color.baseBlack
                            }.lparams(width = matchParent, height = dp(1))
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f
                            
                                textView(R.string.add_dr_name) {
                                    textSize = 18f
                                    textStyle = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 0.5
                                }
                                textView {
                                    id = Ids.item_value_name
                                    inputType = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 1.45
                                }
                            }.lparams(width = matchParent, height = matchParent)
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f
                            
                                textView(R.string.add_dr_dir) {
                                    textSize = 18f
                                    textStyle = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 0.5
                                }
                                textView {
                                    id = Ids.item_value_dir
                                    inputType = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 1.45
                                }
                            }.lparams(width = matchParent, height = matchParent)
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f
                            
                                textView(R.string.add_dr_clinic) {
                                    textSize = 18f
                                    textStyle = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 0.5
                                }
                                textView {
                                    id = Ids.item_value_clinic
                                    inputType = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 1.45
                                }
                            }.lparams(width = matchParent, height = matchParent)
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f
                            
                                textView(R.string.add_dr_info) {
                                    textSize = 18f
                                    textStyle = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 0.5
                                }
                                textView {
                                    id = Ids.item_value_info
                                    inputType = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 1.45
                                }
                            }.lparams(width = matchParent, height = matchParent)
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f
                            
                                textView(R.string.add_dr_date) {
                                    textSize = 18f
                                    textStyle = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 0.5
                                }
                                button {
                                    id = Ids.item_value_date
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 1.45
                                }
                            }.lparams(width = matchParent, height = matchParent)
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f
                            
                                textView(R.string.phone) {
                                    textSize = 18f
                                    textStyle = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 0.5
                                }
                                textView {
                                    autoLink = 4
                                    id = Ids.item_value_phone
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 1.45
                                }
                            }.lparams(width = matchParent, height = matchParent)
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f
                            
                                textView(R.string.email) {
                                    textSize = 18f
                                    textStyle = 1
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 0.5
                                }
                                textView {
                                    autoLink = 2
                                    id = Ids.item_value_email
                                }.lparams(width = dp(0), height = wrapContent) {
                                    weight = 1.45
                                }
                            }.lparams(width = matchParent, height = matchParent)
                        }.lparams(width = matchParent, height = wrapContent)
                    }.lparams(width = matchParent, height = matchParent)
                    linearLayout {
                        button(R.string.accept) {
                            id = Ids.btn_add_doctor
                        }.lparams(width = dp(0), height = wrapContent) {
                            weight = 1
                        }
                    }.lparams(width = matchParent, height = wrapContent) {
                        gravity = Gravity.BOTTOM
                        weight = 1
                        bottomMargin = dip(16)
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = matchParent)
        }
    }

    private object Ids {
        val btn_add_doctor = 1
        val item_value_clinic = 2
        val item_value_date = 3
        val item_value_dir = 4
        val item_value_email = 5
        val item_value_info = 6
        val item_value_name = 7
        val item_value_phone = 8
        val sp_doctors = 9
    }
}