package com.company.model;

import com.company.constant.Constant;

import java.time.LocalDate;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Student implements ShowDetails {
    private String id;
    private String fullName;
    private LocalDate birthDate;
    private boolean gender;
    private String major;
    private String schoolYear;
    private int startYear;
    private int endYear;

    public Student(String id, String fullName, LocalDate birthDate, boolean gender, String major, String schoolYear, int startYear, int endYear) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.major = major;
        this.schoolYear = schoolYear;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isGender(boolean gender) {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(Constant.SEPARATE);
        joiner.add(id);
        joiner.add(fullName);
        joiner.add(birthDate.toString());
        joiner.add(Boolean.toString(gender));
        joiner.add(major);
        joiner.add(schoolYear);
        joiner.add(Integer.toString(startYear));
        joiner.add(Integer.toString(endYear));

        return joiner.toString();
    }

    @Override
    public String showDetails() {
        String data = String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20d %d",
                id, fullName, birthDate.toString(), ((gender) ? "Nam" : "Nữ"), major, schoolYear, startYear, endYear);
        return data;
    }
}