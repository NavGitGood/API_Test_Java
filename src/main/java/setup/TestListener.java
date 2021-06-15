package setup;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends ExtentReportSetup implements ITestListener
{
    public void onTestStart(ITestResult result)
    {
        extentTest = extent.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result)
    {
        extentTest.log(Status.PASS, "Test Case Passed is ::: " +result.getMethod().getDescription());
    }

    public void onTestFailure(ITestResult result)
    {
        extentTest.log(Status.FAIL, "Test Case Failed is ::: " +result.getMethod().getDescription());
        extentTest.log(Status.FAIL, result.getThrowable());
    }

    public void onTestSkipped(ITestResult result)
    {
        extentTest.log(Status.SKIP, "Test Case Skipped is ::: " +result.getMethod().getMethodName());
    }

    public void onStart(ITestContext context)
    {
        extent = ExtentReportSetup.extentReportSetup();
    }

    public void onFinish(ITestContext context)
    {
        extent.flush();
    }
}