package WebTests;

import Infra.config_files.Const;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LoginTests extends LoadInfo {
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, Object[] testArgs) throws Exception {
        testName.set(method.getName() + "_Login_" + testArgs[0].toString().toUpperCase());
        System.out.println("LoginTests");
        shouldLoginToURL(testArgs[0].toString(),testArgs[1].toString());
  }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
       // logOutWebsite();
    }

    @Test(dataProvider = "userList")
    public void LoginValidationTestAllUsers(String user, String password) throws InterruptedException {
        //Handling the different cases of users
        userHandlingCases(user);

    }

    private void userHandlingCases(String user) {
        switch(user) {
            case Const.STANDARD_USER:
            case Const.PROBLEM_USER:
            case Const.ERROR_USER:
            case Const.VISUAL_USER:
            case Const.PERFORMANCE_GLITCH_USER:
                System.out.println("Login with " + Const.STANDARD_USER + " user");
                wait.until(ExpectedConditions.visibilityOf(generalMenu.getTxtProductTitle()));
                logOutWebsite();
                break;
            case Const.LOCKED_OUT_USER:
                System.out.println("Login with " + Const.LOCKED_OUT_USER + " user");
                wait.until(ExpectedConditions.visibilityOf(loginPage.getTxtErrorLogin()));
                Assert.assertEquals(loginPage.getTxtErrorLogin().getText(),Const.ERROR_LOCKED_OUT_USER);
                break;
            default:
                // code block
        }
    }


}
