package org.example;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReportBase {

    public static ExtentReports reports;
    public static ExtentTest test;

    public static void createReport() {
        String basePath = System.getProperty("user.dir");
        reports = new ExtentReports(basePath + "/data_output/test_report.html");
    }
}
