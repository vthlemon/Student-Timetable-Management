package com.company;

import com.company.controller.*;
import com.company.model.User;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    private static final int MIN_CHOICE = 1;

    public static void main(String[] args) throws Exception {
        Application application = new Application();
        application.run();
    }

    private final Login login;
    private final Scanner scanner;
    private final ChangePassword changePassword;

    /**
     * Data
     */
    private User user;
    private boolean isLogin;

    public Application() {
        login = DI.getLogin();
        scanner = DI.getScanner();
        changePassword = DI.getChangePassword();
    }

    private void run() throws IOException {
        String username = "";
        String password = "";

        while (true) {
            print("Nhập username: ");
            username = scanner.nextLine();
            print("Nhập password: ");
            password = scanner.nextLine();

            user = login.execute(username, password);
            isLogin = (user != null);
            while (isLogin) {
                printMenu();
            }
        }
    }

    private void print(String content) {
        System.out.print(content);
    }

    private void println(String content) {
        System.out.println(content);
    }

    private void printMenu() {
        System.out.println(String.format("%40s","PHẦN MỀM QUẢN LÝ THỜI KHÓA BIỂU"));
        println("---------------------------------------------");
        println("1. Đổi mật khẩu");
        println("2. Đăng xuất");
        println("3. Quản lý sinh viên");
        println("4. Quản lý môn học");
        println("5. Quản lý phòng học");
        println("6. Đăng ký môn học");
        println("7. Hiển thị thời khóa biểu của sinh viên");
        println("8. Thoát chương trình");
        print("Mời bạn lựa chọn: ");

        String input = scanner.nextLine();
        int choice = MIN_CHOICE - 1;

        try {
            choice = Integer.parseInt(input);
        } catch (Exception e) {
        }

        switch (choice) {
            case 1:
                processChangePassword();
                break;
            case 2:
                processLogOut();
                break;
            case 3:
                processManagerStudent();
                break;
            case 4:
                processManagerSubject();
                break;
            case 5:
                processManagerSubjectClass();
                break;
            case 6:
                processRegisterSubject();
                break;
            case 7:
                processShowSchedule();
                break;
            case 8:
                processExit();
                break;
            default:
                println("Lựa chọn không hợp lệ!");
        }
    }

    private void processShowSchedule() {
        System.out.print("Nhập mã sinh viên: ");
        String id = scanner.nextLine();
        if(checkString(id)) {
            System.out.println("Vui lòng nhập đầy đủ thông tin và không chứa ký tự đặc biệt!");
            return;
        }
            ShowSchedule showSchedule = DI.getShowSchedule();
            showSchedule.execute(id);
    }

    private void processRegisterSubject() {
        RegisterSubject registerSubject = DI.getRegisterSubject();
        registerSubject.execute();
    }

    private void processManagerSubjectClass() {
        CURDSubjectClass curdSubjectClass = DI.getCurdSubjectClass();
        curdSubjectClass.run();
    }

    private void processManagerSubject() {
        CURDSubject curdSubject = DI.getCurdSubject();
        curdSubject.run();
    }

    private void processManagerStudent() {
        CURDStudent curdStudent = DI.getCurdStudent();
        curdStudent.run();
    }

    private void processChangePassword() {
        print("Nhập mật khẩu mới: ");
        String password = scanner.nextLine();
        print("Nhập lại mật khẩu: ");
        String rePassword = scanner.nextLine();
        boolean result = false;
        if (password.equals("") || rePassword.equals("") || password.trim().isEmpty() || rePassword.trim().isEmpty()) {
            System.out.println("Vui lòng nhập đầy đủ thông tin!");
        }
        else {
            result = changePassword.execute(
                    user.getUsername(),
                    password,
                    rePassword
            );
        }

        if (result) {
            println("Đổi mật khẩu thành công!");
        } else {
            println("Đổi mật khẩu thất bại!");
        }
    }

    private void processLogOut() {
        processSaveData();
        isLogin = false;
    }


    private void processExit() {
        processSaveData();
        System.exit(0);
    }

    private void processSaveData() {
        DI.getUserManager().saveData();
        DI.getSubjectClassManager().saveData();
        DI.getSubjectManager().saveData();
        DI.getStudentManager().saveData();
        DI.getStudentSubjectManager().saveData();
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
