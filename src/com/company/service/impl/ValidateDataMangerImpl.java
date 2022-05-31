package com.company.service.impl;

import com.company.constant.Constant;
import com.company.service.ValidateDataManager;

public class ValidateDataMangerImpl implements ValidateDataManager {

    @Override
    public boolean isContainSeparate(String[] items) {
        for (String s : items) {
            if (s != null) {
                if (s.contains(Constant.SEPARATE)){
                    System.out.println("Khong duoc chua ki tu dac biet(#)");
                    return false;
                }
            }
        }

        return true;
    }
}
