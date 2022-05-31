package com.company.controller.impl;

import com.company.controller.ShowSchedule;
import com.company.model.Subject;
import com.company.service.StudentSubjectManager;
import com.company.service.SubjectManager;

import java.util.Set;

public class ShowScheduleImpl implements ShowSchedule {
    private final StudentSubjectManager studentSubjectManager;
    private final SubjectManager subjectManager;

    public ShowScheduleImpl(StudentSubjectManager studentSubjectManager, SubjectManager subjectManager) {
        this.studentSubjectManager = studentSubjectManager;
        this.subjectManager = subjectManager;
    }

    @Override
    public void execute(String studentId) {
        Set<String> subjectsId = studentSubjectManager.findById(studentId);

        if (subjectsId == null) {
            System.out.println("Sinh viên chưa đăng ký môn học!");
            return;
        }

        System.out.println(String.format("%80s %s", "THỜI KHÓA BIỂU CỦA SINH VIÊN", studentId));
        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %s",
                "Mã môn học",
                "Tên môn học",
                "Mã lớp",
                "Tiết bắt đầu",
                "Ngày bắt đầu",
                "Ngày kết thúc")
        );

        for (String subjectId : subjectsId) {
            Subject subject = subjectManager.findById(subjectId);

            if (subject != null) {
                String data = String.format("%-20s %-20s %-20s %-20d %-20s %s",
                        subject.getId(),
                        subject.getName(),
                        subject.getClassId(),
                        subject.getLesson(),
                        subject.getStartDate(),
                        subject.getEndDate());

                System.out.println(data);
            }
        }
    }
}
