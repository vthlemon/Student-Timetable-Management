package com.company.service.impl;

import com.company.constant.Constant;
import com.company.model.SubjectClass;
import com.company.service.SubjectClassManager;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SubjectClassManagerImpl implements SubjectClassManager {
    private final Map<String, SubjectClass> subjectClassMap = new HashMap<>();
    private boolean initialize = false;

    private static final String FILE_NAME = "subject_classes.txt";
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

                    if (data.length == 2) {
                        SubjectClass subjectClass = new SubjectClass(data[0], data[1]);
                        subjectClassMap.put(subjectClass.getId(), subjectClass);
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

            for (SubjectClass subjectClass : subjectClassMap.values()) {
                bufferedWriter.write(subjectClass.toString());
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
    public void saveModel(SubjectClass subjectClass) {
        subjectClassMap.put(subjectClass.getId(), subjectClass);
    }

    @Override
    public SubjectClass findById(String s) {
        return subjectClassMap.get(s);
    }

    @Override
    public boolean deleteById(String s) {
        if (subjectClassMap.containsKey(s)) {
            subjectClassMap.remove(s);
            return true;
        }

        return false;
    }

    @Override
    public Collection<SubjectClass> findAll() {
        return subjectClassMap.values();
    }
}
