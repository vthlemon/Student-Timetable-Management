package com.company.service.impl;

import com.company.constant.Constant;
import com.company.model.Subject;
import com.company.service.SubjectManager;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SubjectManagerImpl implements SubjectManager {
    private final Map<String, Subject> subjectMap = new HashMap<>();
    private boolean initialize = false;

    private static final String FILE_NAME = "subjects.txt";
    private static final String FILE_PATH = Constant.DATA_PATH + FILE_NAME;

    @Override
    public void initData() {
        if (!initialize) {
            initialize = true;

            try {
                Reader reader = new FileReader(FILE_PATH);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line = "";
                //MH001#Toan#2020-08-08#2020-12-08#LH001#5
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(Constant.SEPARATE);

                    if (data.length == 6) {
                        try {
                            Subject subject = new Subject(
                                    data[0],
                                    data[1],
                                    LocalDate.parse(data[2]),
                                    LocalDate.parse(data[3]),
                                    data[4],
                                    Integer.parseInt(data[5])
                            );

                            subjectMap.put(subject.getId(), subject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                bufferedReader.close();
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveData() {
        try {
            Writer writer = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (Subject subject : subjectMap.values()) {
                bufferedWriter.write(subject.toString());
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
    public void saveModel(Subject subject) {
        subjectMap.put(subject.getId(), subject);
    }

    @Override
    public Subject findById(String s) {
        return subjectMap.get(s);
    }

    @Override
    public boolean deleteById(String s) {
        if (subjectMap.containsKey(s)) {
            subjectMap.remove(s);
            return true;
        }

        return false;
    }

    @Override
    public Collection<Subject> findAll() {
        return subjectMap.values();
    }
}
