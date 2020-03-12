package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class Appointment implements Serializable {

    private String id, time, date, address, category, patientId, doctorId, status;

    public Appointment() {
    }

    public Appointment(String id, String time, String date, String address, String category, String patientId, String doctorId, String status) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.address = address;
        this.category = category;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
