package com.company.service;

import java.util.Set;

public interface StudentSubjectManager {
    void initData();
    void saveData();

    void addSubjectToStudent(String studentId, String  subjectId);
    void removeSubject(String subjectId);

    Set<String> findById(String studentId);
}
