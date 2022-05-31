package com.company.controller.impl;

import com.company.controller.CURDSubjectClass;
import com.company.model.SubjectClass;
import com.company.service.SubjectClassManager;
import com.company.service.ValidateDataManager;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CURDSubjectClassImpl implements CURDSubjectClass {
    private final Scanner scanner;
    private final SubjectClassManager subjectClassManager;
    private final ValidateDataManager validateDataManager;

    public CURDSubjectClassImpl(Scanner scanner, SubjectClassManager subjectClassManager, ValidateDataManager validateDataManager) {
        this.scanner = scanner;
        this.subjectClassManager = subjectClassManager;
        this.validateDataManager = validateDataManager;
    }

    private boolean isRun = false;

    @Override
    public void showMenu() {
        System.out.println(String.format("%30s","QUẢN LÝ LỚP HỌC"));
        System.out.println("---------------------------------------------");
        System.out.println("1. Hiển thị tất cả");
        System.out.println("2. Thêm mới lớp học");
        System.out.println("3. Cập nhật lớp học");
        System.out.println("4. Xóa lớp học");
        System.out.println("5. Thoát");
        System.out.print("Mời bạn nhập lựa chọn: ");

        String input = scanner.nextLine();
        int choice = 0;

        try {
            choice = Integer.parseInt(input);
        } catch (Exception e) {

        }

        switch (choice) {
            case 1:
                showAll();
                break;
            case 2:
                create();
                break;
            case 3:
                update();
                break;
            case 4:
                delete();
                break;
            case 5:
                isRun = false;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!!!");
        }

    }

    @Override
    public void run() {
        isRun = true;
        while (isRun) {
            showMenu();
        }
    }

    @Override
    public void showAll() {
        System.out.println(String.format("%20s","DANH SACH LỚP HỌC\n"));
        System.out.println(String.format("%-20s %-20s", "Mã lớp học", "Phòng học"));
        for (SubjectClass subjectClass : subjectClassManager.findAll()) {
            System.out.println(subjectClass.showDetails());
        }
    }

    @Override
    public void create() {
        System.out.print("Nhập mã lớp học: (LHxxx): ");
        String id = scanner.nextLine();
        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (subjectClassManager.findById(id) != null) {
            System.out.println("Mã lớp học đã tồn tại!");
        } else {
            SubjectClass subjectClass = enterSubjectClass();
            if (subjectClass != null) {
                subjectClass.setId(id);
                subjectClassManager.saveModel(subjectClass);
                System.out.println("Đã thêm thành công");
            }
        }
    }

    @Override
    public void delete() {
        System.out.print("Nhập mã lớp học cần xóa: ");
        String id = scanner.nextLine();

        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (subjectClassManager.deleteById(id)) {
            System.out.println("Bạn đã xóa thành công!");
        } else {
            System.out.println("Mã lớp học không tồn tại!");
        }
    }

    @Override
    public void update() {
        System.out.print("Mã lớp học cần cập nhật: ");
        String id = scanner.nextLine();

        SubjectClass subjectClass = subjectClassManager.findById(id);
        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (subjectClass != null) {
            SubjectClass updateSubjectClass = enterSubjectClass();
            if (updateSubjectClass != null) {
                updateSubjectClass.setId(subjectClass.getId());
                subjectClassManager.saveModel(updateSubjectClass);
                System.out.println("Đã cập nhật thành công!");
            }
        } else {
            System.out.println("Mã lớp học bạn nhập không tồn tại!");
        }
    }

    private SubjectClass enterSubjectClass() {
        String[] data = new String[2];

        System.out.print("Nhập tên lớp học: ");
        data[1] = scanner.nextLine();

        if (!validateDataManager.isContainSeparate(data)) {
            return null;
        }
        if(checkString(data[1])) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return null;
        }
        return new SubjectClass(data[0], data[1]);
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
