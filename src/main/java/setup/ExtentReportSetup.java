package setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ExtentReportSetup
{
    public static ExtentReports extent;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentTest extentTest;

    public static ExtentReports extentReportSetup() {
        String outputDirectory = System.getProperty("user.dir") + File.separator + "output";

        htmlReporter = new ExtentHtmlReporter(outputDirectory + File.separator + "ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Extent Report V4" + TimeUnit.SECONDS);
        htmlReporter.config().setTheme(Theme.DARK);

        return extent;
    }


}