package WebTests;


import Infra.Tools.WebActions;
import Pages.BasePage;
import Pages.ProductsPage;
import Pages.GeneralMenu;
import Pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class WebTest extends BaseTest{

    public static BasePage basePage;
    public static LoginPage loginPage;
    public static GeneralMenu generalMenu;;
    public static ProductsPage productsPage;

    public void shouldLoginToURL(String user, String password) {
        try {
            Assert.assertEquals(loginPage.getTxtMainPage().getText(),"Swag Labs" , "Failed to login to URL");
            loginPage.getInputUsername().clear();
            loginPage.getInputUsername().sendKeys(user);
            loginPage.getInputPassword().clear();
            loginPage.getInputPassword().sendKeys(password);
            WebActions.clickElement(loginPage.getBtnSubmit());
        } catch (Exception e) {
            System.out.println("Failed to login to URL");
            e.printStackTrace();
        }
    }

    public void logOutWebsite( ) {
        try {
            wait.until(ExpectedConditions.visibilityOf(generalMenu.getBtnMainMenu()));
            WebActions.clickElement(generalMenu.getBtnMainMenu());
            WebActions.clickElement(generalMenu.getBtnLogoutSubMenu());
            Assert.assertTrue(loginPage.getBtnSubmit().isDisplayed() , "Failed to logout from URL");
        } catch (Exception e) {
            System.out.println("Failed to logout from URL");
            e.printStackTrace();
        }
    }

    @BeforeClass(alwaysRun = true)
    public void beforeSuite() {
        try {
            invokeBrowser();
            initAllPages();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterClass(alwaysRun = true)
    public void aftetrClass() {
        try {
            teardown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initAllPages() throws Exception {
        try {
            basePage = new BasePage(webDriver);
            loginPage = new LoginPage(webDriver);
            generalMenu = new GeneralMenu(webDriver);
            productsPage = new ProductsPage(webDriver);
        }catch (Exception ex){
            System.out.println("ERROR:  initAllPages init failed");
            ex.getStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public void teardown() {
        try {
            System.out.println("tearDown");
            if (webDriver != null) {
                webDriver.quit();
                System.out.println("tearDown: close webDriver");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
