package ru.course.springtutorial.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private String id;
    private String classNumber;
    private int teacherId;
    private String reason;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date endTime;

    public Reservation(String classNumber, int teacherId, String reason, Date startTime, Date endTime) {
        this.classNumber = classNumber;
        this.teacherId = teacherId;
        this.reason = reason;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Reservation get(String id) throws ParseException {
        // Здесь будем доставать объект из бд
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return new Reservation("108", 10, "test",
                formatter.parse("01.01.2023 10:00"),
                formatter.parse("01.01.2023 11:00")
        );
    }

    public static Reservation[] getList() throws ParseException {
        return new Reservation[] {get("10")};
    }

    public String save() {
        // Здесь будем класть объект в бд
        System.out.println("Saved " + this);
        return "-1"; // здесь будем возвращать id сохранённого объекта
    }

    @Override
    public String toString() {
        return "reserved " + classNumber + " from " + startTime + " to " + endTime + " for " + reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
