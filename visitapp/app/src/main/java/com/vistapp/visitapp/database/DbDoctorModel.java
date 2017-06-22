package com.vistapp.visitapp.database;

import com.orm.SugarRecord;


/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

public class DbDoctorModel extends SugarRecord {
    String idDoc;
    String name;
    String direction;
    String time;
    String clinic;
    String phone;
    String email;
    boolean assigned;

    public DbDoctorModel(String idDoc, String name, String direction, String clinic, String time, String phone, String email, boolean assigned) {
        this.idDoc = idDoc;
        this.name = name;
        this.direction = direction;
        this.clinic = clinic;
        this.time = time;
        this.phone = phone;
        this.email = email;
        this.assigned = assigned;
    }

    public DbDoctorModel() {
    }
}
