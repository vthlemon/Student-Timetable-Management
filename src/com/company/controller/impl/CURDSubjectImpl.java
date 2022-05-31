package com.company.controller.impl;

import com.company.DI;
import com.company.controller.CURDSubject;
import com.company.model.Subject;
import com.company.service.*;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CURDSubjectImpl implements CURDSubject {
    private final SubjectManager subjectManager;
    private final Scanner scanner;
    private final ValidateDataManager validateDataManager;

    private boolean isRun = true;

    public CURDSubjectImpl(SubjectManager subjectManager, Scanner scanner, ValidateDataManager validateDataManager) {
        this.subjectManager = subjectManager;
        this.scanner = scanner;
        this.validateDataManager = validateDataManager;
    }

    @Override
    public void showMenu() {
        System.out.println(String.format("%30s","QUẢN LÝ MÔN HỌC"));
        System.out.println("---------------------------------------------");
        System.out.println("1. Hiển thị tất cả");
        System.out.println("2. Thêm mới môn học");
        System.out.println("3. Cập nhật môn học");
        System.out.println("4. Xóa môn học");
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
                System.out.println("Lựa chọn không hợp lệ!");
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
        System.out.println(String.format("%60s","DANH SÁCH MÔN HỌC\n"));
        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %s",
                "Mã MH", "Tên MH", "Ngày bắt đầu", "Ngày kết thúc","Lớp học", "Tiết bắt đầu"));
        for (Subject subject : subjectManager.findAll()) {
            System.out.println(subject.showDetails());
        }
    }

    @Override
    public void create() {
        System.out.print("Nhập id (MHxxx): ");
        String id = scanner.nextLine();

        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (subjectManager.findById(id) != null) {
            System.out.println("Mã bạn nhập đã tồn tại!");
        } else {
            Subject subject = enterSubject();
            if (subject != null) {
                subject.setId(id);
                subjectManager.saveModel(subject);
                System.out.println("Đã thêm thành công!");
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
        if (subjectManager.deleteById(id)) {
            /**
             * Cap nhat lai thoi khoa bieu
             */
            StudentSubjectManager manager = DI.getStudentSubjectManager();
            manager.removeSubject(id);
            System.out.println("Bạn đã xóa thành công!");
        } else {
            System.out.println("Mã bạn nhập không tồn tại");
        }
    }

    @Override
    public void update() {
        System.out.print("Mã MH cần cập nhật: ");
        String id = scanner.nextLine();

        Subject subject = subjectManager.findById(id);
        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (subject == null) {
            System.out.println("Mã bạn nhập không tồn tại");
        } else {
            Subject updateSubject = enterSubject();
            if (updateSubject != null) {
                updateSubject.setId(subject.getId());
                subjectManager.saveModel(updateSubject);
                System.out.println("Đã cập nhật thành công!");
            }
        }
    }

    private Subject enterSubject() {
        String[] data = new String[6];

        System.out.print("Nhập tên môn học: ");
        data[1] = scanner.nextLine();
        System.out.print("Nhập ngày bắt đầu (yyyy-mm-dd): ");
        data[2] = scanner.nextLine();
        System.out.print("Nhập ngày kết thúc (yyyy-mm-dd): ");
        data[3] = scanner.nextLine();
        System.out.print("Nhập mã lớp học: ");
        data[4] = scanner.nextLine();
        System.out.print("Nhập tiết bắt đầu: ");
        data[5] = scanner.nextLine();

        if (!validateDataManager.isContainSeparate(data)) {
            return null;
        }

        SubjectClassManager subjectClassManager = DI.getSubjectClassManager();
        if (subjectClassManager.findById(data[4]) == null) {
            System.out.println("Mã lớp học không đúng!");
            return null;
        }

        try {
            if(checkString(data[1])) {
                System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
                return null;
            }

            if (Integer.parseInt(data[5])<0 || Integer.parseInt(data[5])>13) {
                System.out.println("Tiết bắt đầu không hợp lệ!!!");
                return null;
            }
            if (LocalDate.parse(data[3]).compareTo(LocalDate.parse(data[2]))<0) {
               System.out.println("Ngày bắt đầu phải lớn hơn ngày kết thúc");
               return null;
            }
            Subject subject = new Subject(
                    data[0],
                    data[1],
                    LocalDate.parse(data[2]),
                    LocalDate.parse(data[3]),
                    data[4],
                    Integer.parseInt(data[5])
            );

            return subject;

        } catch (Exception e) {
            System.out.println("Bạn nhập sai định dạng!");
        }

        return null;
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
