package com.yae.evaluation.utils;

import java.io.IOException;

public class FileUtils {
    private static String USER= System.getProperty("user.name");
    private static final String  FILE_BASE_PATH="/home/"+USER+"/yae/";

    public static String getSubmissionFilePath(Long userId, Long assignmnentId, String fileName){
        String filePath = FILE_BASE_PATH;
        filePath += (userId + "/" + assignmnentId + "/" + fileName);
        return filePath;
    }

    public static String getTempFilePath(Long userId, String fileName) throws IOException{

        String tempDir = System.getProperty("java.io.tmpdir");
        String tempFilePath = tempDir + "/" + fileName;
        return tempFilePath;
}
}
