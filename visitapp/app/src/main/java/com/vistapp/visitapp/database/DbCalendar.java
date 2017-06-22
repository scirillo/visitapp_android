package com.vistapp.visitapp.database;

import com.orm.SugarRecord;

/**
 * Created by Santiago Cirillo on 17/05/2017.
 */

public class DbCalendar extends SugarRecord{
    String idVisit;
    String idDoctor;
    String time;
    String place;

    public DbCalendar(String idVisit, String idDoctor, String time, String place) {
        this.idVisit = idVisit;
        this.idDoctor = idDoctor;
        this.time = time;
        this.place = place;
    }

    public DbCalendar() {
    }
}
