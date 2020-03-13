package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class Notification implements Serializable {
    private String id, userId, doctorId, userMessage, doctorMessage, appointmentId;

    public Notification() {
    }

    public Notification(String id, String userId, String doctorId, String userMessage, String doctorMessage, String appointmentId) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
        this.userMessage = userMessage;
        this.doctorMessage = doctorMessage;
        this.appointmentId = appointmentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDoctorMessage() {
        return doctorMessage;
    }

    public void setDoctorMessage(String doctorMessage) {
        this.doctorMessage = doctorMessage;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
