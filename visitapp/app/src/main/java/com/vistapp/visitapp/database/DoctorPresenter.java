package com.vistapp.visitapp.database;

import com.orm.util.Collection;
import com.vistapp.visitapp.model.DoctorModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Santiago Cirillo on 17/05/2017.
 */

public class DoctorPresenter {
    private static DoctorPresenter mInstance;
    private List<DoctorModel> list = new ArrayList<>();

    private DoctorPresenter() {
    }

    public static DoctorPresenter getInstance() {
        if (mInstance == null) {
            mInstance = new DoctorPresenter();
        }
        return mInstance;
    }

    public void persistDoctorList(List<DoctorModel> modelList) {
        for (DoctorModel model : modelList) {
            if (!list.contains(model)) {
                DbDoctorModel dbDoctorModel = new DbDoctorModel(model.getId(), model.getName(), model.getDirection(), model.getClinic(), model.getTime(), model.getPhone(), model.getEmail(), model.isAssigned());
                dbDoctorModel.save();
                list.add(model);
            }
        }
    }

    public void removeDoctor(DoctorModel model) {
        List<DbDoctorModel> aux = DbDoctorModel.find(DbDoctorModel.class, "ID_DOC = ?", model.getId());
        if (aux.size() > 0) {
            DbDoctorModel db = aux.get(0);
            if (db.delete()) list.remove(model);
        }
    }

    public void addDoctor(DoctorModel model) {
        if(!list.contains(model)) {
            DbDoctorModel dbDoctorModel = new DbDoctorModel(model.getId(), model.getName(), model.getDirection(), model.getClinic(), model.getTime(), model.getPhone(), model.getEmail(), model.isAssigned());
            dbDoctorModel.save();
            list.add(model);
        }else{
            DbDoctorModel dbDoctorModel = new DbDoctorModel(model.getId(), model.getName(), model.getDirection(), model.getClinic(), model.getTime(), model.getPhone(), model.getEmail(), model.isAssigned());
            dbDoctorModel.update();
            list.remove(model);
            list.add(model);
        }
    }

    public void removeAllDoctor() {
        if(!getDoctorList().isEmpty()) {
            DbDoctorModel.deleteAll(DbDoctorModel.class);
            list.clear();
        }
    }

    public List<DoctorModel> getDoctorList() {
        Collections.sort(list);
        return list;
    }

    public void removeAssignedDoctors(List<DoctorModel> deleteList, int day) {
        for (DoctorModel model : deleteList) {
            if (model.getDate().equalsIgnoreCase(getParseDate(day))) {
                unassingDoctor(model);
            }
        }
    }

    private void unassingDoctor(DoctorModel model) {
        List<DbDoctorModel> aux = DbDoctorModel.find(DbDoctorModel.class, "ID_DOC = ?", model.getId());
        if (aux.size() > 0) {
            DbDoctorModel db = aux.get(0);
            db.assigned = false;
            db.update();
            list.remove(model);
            model.setAssigned(false);
            list.add(model);
        }
    }


    public void removeDoctors(List<DoctorModel> deleteList, int day) {
        for (DoctorModel model : deleteList) {
            if (model.getDate().equalsIgnoreCase(getParseDate(day))) {
                removeDoctor(model);
            }
        }
    }

    private String getParseDate(int day) {
        Calendar c = Calendar.getInstance();
        int dayWeek = 22 + day;// c.get(Calendar.DAY_OF_WEEK)+day;
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        return dayWeek + "/0" + month + "/" + year;
    }

    public List<DoctorModel> getDoctorListPerDay(int day) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        List<DoctorModel> auxList = new ArrayList<>();
        for (DoctorModel m : list) {
            if (m.getDate().equalsIgnoreCase(getParseDate(day))) {
                auxList.add(m);
            }
        }
        return auxList;
    }

    public List<DoctorModel> getDoctorAssignedPerDay(int day) {
        List<DoctorModel> auxList = new ArrayList<>();
        for (DoctorModel m : list) {
            if (m.getDate().equalsIgnoreCase(getParseDate(day))) {
                if(m.isAssigned()) auxList.add(m);
            }
        }
        return auxList;
    }
}
