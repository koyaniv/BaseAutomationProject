package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GeneralMenu extends BasePage{

    @FindBy(id = "react-burger-menu-btn")
    private WebElement btnMainMenu;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement btnAllItemsSubMenu;

    @FindBy(id = "about_sidebar_link")
    private WebElement btnAboutSubManu;

    @FindBy(id = "logout_sidebar_link")
    private WebElement btnLogoutSubMenu;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement btnResetSubMenu;


    @FindBy(css = "span.title")
    private WebElement txtProductTitle;

    public GeneralMenu(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getBtnMainMenu() {
        return btnMainMenu;
    }

    public WebElement getBtnAllItemsSubMenu() {
        return btnAllItemsSubMenu;
    }

    public WebElement getBtnAboutSubManu() {
        return btnAboutSubManu;
    }

    public WebElement getBtnLogoutSubMenu() {
        return btnLogoutSubMenu;
    }

    public WebElement getBtnResetSubMenu() {
        return btnResetSubMenu;
    }

    public WebElement getTxtProductTitle() {
        return txtProductTitle;
    }

}
