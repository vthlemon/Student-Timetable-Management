package com.company.service.impl;

import com.company.constant.Constant;
import com.company.model.Student;
import com.company.service.StudentManager;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StudentManagerImpl implements StudentManager {
    private final Map<String, Student> studentMap = new HashMap<>();
    private static final String FILE_NAME = "students.txt";
    private static final String FILE_PATH = Constant.DATA_PATH + FILE_NAME;

    private boolean initialize = false;

    @Override
    public void initData() {
        if (!initialize) {

            initialize = true;

            try {
                Reader reader = new FileReader(FILE_PATH);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";

                //SV001#Nguyen Van A#1999-06-09#Nam#CNTT#K17#2017#2020
                while ((line = bufferedReader.readLine()) != null) {
                    String[] items = line.split(Constant.SEPARATE);
                    if (items.length == 8) {
                        try {
                            Student student = new Student(
                                    items[0],
                                    items[1],
                                    LocalDate.parse(items[2]),
                                    Boolean.parseBoolean(items[3]),
                                    items[4],
                                    items[5],
                                    Integer.parseInt(items[6]),
                                    Integer.parseInt(items[7])
                            );

                            studentMap.put(student.getId(), student);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                bufferedReader.close();
                reader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveData() {
        try {
            Writer writer = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (Student student : studentMap.values()) {
                bufferedWriter.write(student.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveModel(Student student) {
        studentMap.put(student.getId(), student);
    }

    @Override
    public Student findById(String id) {
        return studentMap.get(id);
    }

    @Override
    public boolean deleteById(String id) {
        if (studentMap.containsKey(id)) {
            studentMap.remove(id);
            return true;
        }

        return false;
    }

    @Override
    public Collection<Student> findAll() {
        return studentMap.values();
    }
}
