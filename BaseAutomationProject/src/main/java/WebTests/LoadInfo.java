package WebTests;

import Infra.config_files.Const;
import org.testng.ITest;
import org.testng.annotations.DataProvider;

public class LoadInfo extends WebTest implements ITest {
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @DataProvider(name = "userList")
    public static Object[][] userList() {
        return new Object[][]{
                {
                        Const.STANDARD_USER, Const.SECRET_SAUCE
                },
                {
                        Const.LOCKED_OUT_USER, Const.SECRET_SAUCE
                },
                {
                        Const.PROBLEM_USER, Const.SECRET_SAUCE
                },
                {
                        Const.PERFORMANCE_GLITCH_USER, Const.SECRET_SAUCE
                },
                {
                        Const.ERROR_USER, Const.SECRET_SAUCE
                },
                {
                        Const.VISUAL_USER, Const.SECRET_SAUCE
                }
        };
    }



    @Override
    public String getTestName() {
        return testName.get();
    }
}
