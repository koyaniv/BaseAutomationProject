package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{


    @FindBy(css = "div.login_logo")
    private WebElement txtMainPage;

    @FindBy(id = "user-name")
    private WebElement inputUsername;

    @FindBy(id = "password")
    private WebElement inputPassword;

    @FindBy(id = "login-button")
    private WebElement btnSubmit;

    @FindBy(css = "div.error-message-container.error > h3")
    private WebElement txtErrorLogin;



    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getTxtMainPage() {
        return txtMainPage;
    }

    public WebElement getInputUsername() {
        return inputUsername;
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public WebElement getBtnSubmit() {
        return btnSubmit;
    }

    public WebElement getTxtErrorLogin() {
        return txtErrorLogin;
    }
}
