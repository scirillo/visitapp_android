package model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

public class DoctorModel implements Serializable, Comparable<DoctorModel>{
    private String id;
    private String name;
    private String direction;
    private String date;
    private String time;
    private String note;
    private String clinic;
    private String phone;
    private String email;
    private boolean assigned;
    private Location location;
    private String title;


    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorModel model = (DoctorModel) o;

        return id.equals(model.id);

    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public DoctorModel(String id, String name, String direction, String note, String clinic, String time, String phone, String email, String date, boolean assigned) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.note = note;
        this.clinic = clinic;
        this.time = "Fecha de visita: " +time;
        this.phone = phone;
        this.email = email;
        this.date = date;
        this.assigned = assigned;
    }

    public DoctorModel(String id, String name, String direction, String note, String clinic, String time) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.note = note;
        this.clinic = clinic;
        this.time = "Fecha de visita: " +time;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getId() {
        return id;

    }

    public boolean isAssigned() {
        return assigned;
    }

    public String getClinic() {
        return clinic;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getTime() {
        return time;
    }
    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public String getNote() {
        return note;
    }

    public DoctorModel() {
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }


    @Override
    public int compareTo(@NonNull DoctorModel o) {
        return this.getName().compareTo(o.getName());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
}
