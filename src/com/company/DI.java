package com.company;

import com.company.controller.*;
import com.company.controller.impl.*;
import com.company.service.*;
import com.company.service.impl.*;

import java.util.Scanner;

public class DI {
    private DI() {
    }

    private static final UserManager userManager = new UserManagerImpl();

    private static final StudentManager studentManager = new StudentManagerImpl();

    private static final SubjectManager subjectManager = new SubjectManagerImpl();

    private static final SubjectClassManager subjectClassManager = new SubjectClassManagerImpl();

    private static final StudentSubjectManager studentSubjectManager = new StudentSubjectManagerImpl();

    /**
     * Init data
     */
    static {
        userManager.initData();
        studentManager.initData();
        subjectManager.initData();
        subjectClassManager.initData();
        studentSubjectManager.initData();
    }

    private static final Scanner scanner = new Scanner(System.in);

    private static final Login login = new LoginImpl(userManager);

    private static final ChangePassword changePassword = new ChangePasswordImpl(userManager);

    private static final ValidateDataManager validateDataManager = new ValidateDataMangerImpl();

    private static final CURDStudent curdStudent = new CURDStudentImpl(studentManager, scanner, validateDataManager);

    private static final CURDSubject curdSubject = new CURDSubjectImpl(subjectManager, scanner, validateDataManager);

    private static final CURDSubjectClass curdSubjectClass = new CURDSubjectClassImpl(scanner, subjectClassManager, validateDataManager);

    private static final RegisterSubject registerSubject = new RegisterSubjectImpl(studentManager, subjectManager, scanner);

    private static final ShowSchedule showSchedule = new ShowScheduleImpl(studentSubjectManager, subjectManager);

    /**
     * Getter
     */
    public static ChangePassword getChangePassword() {
        return changePassword;
    }

    public static Login getLogin() {
        return login;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static StudentManager getStudentManager() {
        return studentManager;
    }

    public static ValidateDataManager getValidateDataManager() {
        return validateDataManager;
    }

    public static CURDStudent getCurdStudent() {
        return curdStudent;
    }

    public static SubjectManager getSubjectManager() {
        return subjectManager;
    }

    public static SubjectClassManager getSubjectClassManager() {
        return subjectClassManager;
    }

    public static CURDSubject getCurdSubject() {
        return curdSubject;
    }

    public static CURDSubjectClass getCurdSubjectClass() {
        return curdSubjectClass;
    }

    public static StudentSubjectManager getStudentSubjectManager() {
        return studentSubjectManager;
    }

    public static RegisterSubject getRegisterSubject() {
        return registerSubject;
    }

    public static ShowSchedule getShowSchedule() {
        return showSchedule;
    }
}
