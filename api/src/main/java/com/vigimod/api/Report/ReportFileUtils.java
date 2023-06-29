package com.vigimod.api.Report;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ReportFileUtils {
    public static void writeFile(String fileName, String content) {

        try {
           

            FileUtils.writeStringToFile(
                    new File((fileName != null ? "api/logs/" + fileName + ".txt" : "api/logs/report.txt")), content,
                    "UTF-8");
            System.out.println("File scritto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName) {
        try {
            return FileUtils.readFileToString(
                    new File((fileName != null ? "api/logs/" + fileName + ".txt" : "api/logs/report.txt")), "UTF-8");
        
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
