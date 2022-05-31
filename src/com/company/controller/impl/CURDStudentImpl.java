package com.company.controller.impl;

import com.company.controller.CURDStudent;
import com.company.model.Student;
import com.company.service.StudentManager;
import com.company.service.ValidateDataManager;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CURDStudentImpl implements CURDStudent {
    private final StudentManager studentManager;
    private final Scanner scanner;
    private final ValidateDataManager validateDataManager;

    private boolean isRun = true;

    public CURDStudentImpl(StudentManager studentManager, Scanner scanner, ValidateDataManager validateDataManager) {
        this.studentManager = studentManager;
        this.scanner = scanner;
        this.validateDataManager = validateDataManager;
    }

    @Override
    public void showMenu() {
        System.out.println(String.format("%30s","QUẢN LÝ SINH VIÊN"));
        System.out.println("---------------------------------------------");
        System.out.println("1. Hiển thị tất cả");
        System.out.println("2. Thêm mới sinh viên");
        System.out.println("3. Cập nhật sinh viên");
        System.out.println("4. Xóa sinh viên");
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
        System.out.println(String.format("%80s","DANH SÁCH SINH VIÊN\n"));
        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %s",
                "Mã số SV", "Họ và tên", "Ngày sinh", "Giới tính","Ngành", "Khóa học", "Năm bắt đầu", "Năm kết thúc"));
        for (Student student : studentManager.findAll()) {
            System.out.println(student.showDetails());
        }
    }

    @Override
    public void create() {
        System.out.print("Nhập mã sinh viên (SVxxx): ");
        String id = scanner.nextLine();

        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (studentManager.findById(id) != null) {
            System.out.println("Mã sinh viên đã tồn tại!");
        } else {
            Student student = enterStudent();
            if (student != null) {
                student.setId(id);
                studentManager.saveModel(student);
                System.out.println("Đã thêm thành công!");
            }
        }
    }

    @Override
    public void delete() {
        System.out.print("Nhập mã cần xóa: ");
        String id = scanner.nextLine();

        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (studentManager.deleteById(id)) {
            System.out.println("Bạn đã xóa thành công!");
        } else {
            System.out.println("Mã bạn nhập không tồn tại!");
        }
    }

    @Override
    public void update() {
        System.out.print("Mã SV cần cập nhật: ");
        String id = scanner.nextLine();

        Student student = studentManager.findById(id);
        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
        if (student == null) {
            System.out.println("Mã bạn nhập không hợp lệ!");
        } else {
            Student updateStudent = enterStudent();
            if (updateStudent != null) {
                updateStudent.setId(student.getId());
                studentManager.saveModel(updateStudent);
                System.out.println("Đã cập nhật thành công!");
            }
        }
    }


    private Student enterStudent() {
        String[] data = new String[8];

        System.out.print("Nhập họ tên: ");
        data[1] = scanner.nextLine();
        System.out.print("Nhập ngày sinh (yyyy-mm-dd): ");
        data[2] = scanner.nextLine();
        System.out.print("Nhập giới tính (true: Nam | Nhập bất kỳ: Nữ): ");
        data[3] = scanner.nextLine();
        System.out.print("Nhập ngành: ");
        data[4] = scanner.nextLine();
        System.out.print("Nhập khóa: ");
        data[5] = scanner.nextLine();
        System.out.print("Nhập năm bắt đầu: ");
        data[6] = scanner.nextLine();
        System.out.print("Nhập năm kết thúc: ");
        data[7] = scanner.nextLine();

        if (!validateDataManager.isContainSeparate(data)) {
            return null;
        }

        try {
            if(checkString(data[1]) || checkString(data[4]) || checkString(data[5])) {
                System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
                return null;
            }
            if (Integer.parseInt(data[7])<1800 || Integer.parseInt(data[6]) <1800){
                System.out.println("Năm phải lớn hơn 1800 và nhỏ hơn 2100!");
                return null;
            }
            if (Integer.parseInt(data[7]) <  Integer.parseInt(data[6])){
                System.out.println("Năm kết thúc phải lớn hơn năm bắt đầu!");
                return null;
            }
            Student student = new Student(
                    data[0],
                    data[1],
                    LocalDate.parse(data[2]),
                    Boolean.parseBoolean(data[3]),
                    data[4],
                    data[5],
                    Integer.parseInt(data[6]),
                    Integer.parseInt(data[7])
            );

            return student;

        } catch (Exception e) {
            System.out.println("Bạn nhập không đúng định dạng!");
        }

        return null;
    }

    public boolean checkString(String s) {
        if (s.equals("") || s.trim().isEmpty()) {
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
