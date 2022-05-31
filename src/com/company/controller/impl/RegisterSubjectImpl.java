package com.company.controller.impl;

import com.company.DI;
import com.company.controller.RegisterSubject;
import com.company.service.StudentManager;
import com.company.service.StudentSubjectManager;
import com.company.service.SubjectManager;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterSubjectImpl implements RegisterSubject {
    private final StudentManager studentManager;
    private final SubjectManager subjectManager;;
    private final Scanner scanner;

    public RegisterSubjectImpl(StudentManager studentManager, SubjectManager subjectManager, Scanner scanner) {
        this.studentManager = studentManager;
        this.subjectManager = subjectManager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Nhập mã số sinh viên: ");
        String studentId = scanner.nextLine();
        if(checkString(studentId)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (studentManager.findById(studentId) == null) {
            System.out.println("Mã sinh viên bạn nhập không tồn tại!");
            return;
        }

        System.out.print("Nhập mã môn học: ");
        String subjectId = scanner.nextLine();
        if(checkString(subjectId)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (subjectManager.findById(subjectId) == null) {
            System.out.println("Mã môn học bạn nhập không tồn tại!");
            return;
        }

        StudentSubjectManager manager = DI.getStudentSubjectManager();
        manager.addSubjectToStudent(studentId, subjectId);
        System.out.println("Bạn đã thêm thành công!");
    }
    public boolean checkString(String s) {
        if (s == null || s.trim().isEmpty()) {
            return true;
        }
        Pattern p = Pattern.compile("[A-Za-z0-9]");
        Matcher m = p.matcher(s);
        boolean b = m.find();
        if (b == true)
            return false;
        else
            return true;
    }
}
