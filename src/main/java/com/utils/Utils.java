package com.utils;

import java.util.List;

public class Utils {

    public static void addToList(List<Byte> bytesList, byte[] bytes, int num) {

        for(int i = 0; i < num ; i++) {
            bytesList.add(bytes[i]);
        }
    }
}
