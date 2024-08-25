package WebTests;

import Infra.Reader.ConfigurationReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductsTests  extends LoadInfo {
    SoftAssert softAssert = new SoftAssert();



    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() throws Exception {
        System.out.println("LoginTests");
        shouldLoginToURL(ConfigurationReader.getInstance().getValue("USER_NAME"),ConfigurationReader.getInstance().getValue("PASSWORD"));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
         logOutWebsite();
    }

    @Test
    public void validateProducts() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(generalMenu.getTxtProductTitle()));
        Assert.assertTrue(generalMenu.getTxtProductTitle().getText().equals("Products"));
        softAssert.assertEquals(productsPage.getProductsList().size(),6);
        softAssert.assertTrue(productsPage.getProductsList().get(0).getText().contains("Sauce Labs Backpack"));
        softAssert.assertTrue(productsPage.getProductsList().get(1).getText().contains("Sauce Labs Bike Light"));
        softAssert.assertTrue(productsPage.getProductsList().get(2).getText().contains("Sauce Labs Bolt T-Shirt"));
        softAssert.assertTrue(productsPage.getProductsList().get(3).getText().contains("Sauce Labs Fleece Jacket"));
        softAssert.assertTrue(productsPage.getProductsList().get(4).getText().contains("Sauce Labs Onesie"));
        softAssert.assertTrue(productsPage.getProductsList().get(5).getText().contains("Test.allTheThings() T-Shirt (Red)"));
        softAssert.assertAll();

    }


}
