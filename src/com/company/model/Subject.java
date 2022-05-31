package com.company.model;

import com.company.constant.Constant;

import java.time.LocalDate;
import java.util.StringJoiner;

public class Subject implements ShowDetails {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String classId;
    private int lesson;

    public Subject(String id, String name, LocalDate startDate, LocalDate endDate, String classId, int lesson) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.classId = classId;
        this.lesson = lesson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(Constant.SEPARATE);
        joiner.add(id);
        joiner.add(name);
        joiner.add(startDate.toString());
        joiner.add(endDate.toString());
        joiner.add(classId);
        joiner.add(Integer.toString(lesson));

        return joiner.toString();
    }

    @Override
    public String showDetails() {
        String data = String.format("%-20s %-20s %-20s %-20s %-20s %d",
                id,name,startDate.toString(),endDate.toString(),classId,lesson);
        return data;
    }
}
