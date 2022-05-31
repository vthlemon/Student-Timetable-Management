package com.company.service.impl;

import com.company.constant.Constant;
import com.company.service.StudentSubjectManager;

import java.io.*;
import java.util.*;

public class StudentSubjectManagerImpl implements StudentSubjectManager {
    private final Map<String, Set<String>> listMap = new HashMap<>();
    private boolean initialize = false;

    private static final String FILE_NAME = "student_subject.txt";
    private static final String FILE_PATH = Constant.DATA_PATH + FILE_NAME;

    @Override
    public void initData() {
        if (!initialize) {

            initialize = true;

            try {
                Reader reader = new FileReader(FILE_PATH);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(Constant.SEPARATE);

                    String studentId = data[0];
                    Set<String> subjectsId = new HashSet<>();

                    if (data.length > 1) {
                        for (int i = 1; i < data.length; i++) {
                            subjectsId.add(data[i]);
                        }
                    }

                    listMap.put(studentId, subjectsId);
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

            for (Map.Entry<String, Set<String>> item : listMap.entrySet()) {

                StringJoiner joiner = new StringJoiner(Constant.SEPARATE);
                joiner.add(item.getKey());

                item.getValue().forEach(joiner::add);

                bufferedWriter.write(joiner.toString());
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
    public void addSubjectToStudent(String studentId, String subjectId) {
        Set<String> subjectsId = listMap.containsKey(studentId) ? listMap.get(studentId) : new HashSet<>();
        subjectsId.add(subjectId);

        listMap.put(studentId, subjectsId);
    }

    @Override
    public void removeSubject(String subjectId) {
        for (String key : listMap.keySet()) {
            Set<String> values = listMap.get(key);
            if (values.contains(subjectId)) {
                values.remove(subjectId);
                listMap.put(key, values);
            }
        }
    }

    @Override
    public Set<String> findById(String studentId) {
        return listMap.get(studentId);
    }
}
