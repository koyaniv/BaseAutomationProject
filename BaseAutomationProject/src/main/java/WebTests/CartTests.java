package WebTests;

import Infra.Reader.ConfigurationReader;
import Infra.Tools.WebActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartTests  extends LoadInfo {
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
    public void validateCart() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(generalMenu.getTxtProductTitle()));
        Assert.assertTrue(generalMenu.getTxtProductTitle().getText().equals("Products"));
        WebActions.clickElement( productsPage.getBackupItemAddToCart());
        wait.until(ExpectedConditions.visibilityOf(productsPage.getBackupItemRemoveFromCart()));
        softAssert.assertEquals(productsPage.getBackupItemRemoveFromCart().getText(), "Remove","Remove button is not displayed");
        WebActions.clickElement(productsPage.getBtnCart());
        //Cart Page
        wait.until(ExpectedConditions.visibilityOf(productsPage.getCartTitle()));
        softAssert.assertEquals(productsPage.getCartTitle().getText(), "Your Cart","Cart title is not displayed");
        //validate item exist
        softAssert.assertTrue(productsPage.getBackupItemInCartPage().getText().contains("Sauce Labs Backpack"));
        softAssert.assertEquals(productsPage.getBackupItemQTYInCartPage().getText(), "1","Quantity is not correct");
        softAssert.assertAll();
    }

    @Test
    public void validateRemoveFromCart() throws InterruptedException {
    }

    @Test
    public void validateAddToCart_ALlItems() throws InterruptedException {
    }


}
