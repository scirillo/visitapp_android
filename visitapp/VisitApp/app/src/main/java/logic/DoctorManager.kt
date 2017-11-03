package logic


import android.content.Context
import model.DoctorModel
import utils.SingletonHolder
import extensions.addDay
import kotlin.collections.ArrayList

/**
 * Created by Santiago Cirillo on 17/05/2017.
 */

class DoctorManager private constructor(context: Context) {
    private val list = ArrayList<DoctorModel>()
    init {}
        companion object : SingletonHolder<DoctorManager, Context>(::DoctorManager)


        fun persistDoctorList(modelList: List<DoctorModel>) {
        modelList.filterNot { list.contains(it) }
                .forEach { list.add(it) }
    }

    fun removeDoctor(model: DoctorModel) {
        list.remove(model)
    }

    fun addDoctor(model: DoctorModel) {
        if (!list.contains(model)) {
            list.add(model)
        } else {
            list.remove(model)
            list.add(model)
        }
    }

    fun removeAllDoctor() {
        if (!list.isEmpty()) {
            list.clear()
        }
    }

    fun removeAssignedDoctors(deleteList: ArrayList<DoctorModel>, day: Int) {
        for (model in deleteList) {
            if (model.date.equals(addDay(day))) {
                unassingDoctor(model)
            }
        }
    }

    private fun unassingDoctor(model: DoctorModel) {
        list.remove(model)
        model.isAssigned = false
        list.add(model)
    }


    fun removeDoctors(deleteList: List<DoctorModel>, day: Int) {
        deleteList
                .filter { it.date.equals(addDay(day), ignoreCase = true) }
                .forEach { removeDoctor(it) }
    }

    fun getDoctorListPerDay(day: Int): List<DoctorModel> {
        val auxList = list.filterTo(ArrayList<DoctorModel>()) { it.date.equals(addDay(day), ignoreCase = true) }
        return auxList
    }

    fun getDoctorAssignedPerDay(day: Int): ArrayList<DoctorModel> {
        val auxList = ArrayList<DoctorModel>()
        for (m in list) {
            if (m.date.equals(addDay(day))) {
                if(m.isAssigned)auxList.add(m)
            }
        }
        return auxList
    }

    fun getDoctorList(): ArrayList<DoctorModel> {
        return list
    }
}
