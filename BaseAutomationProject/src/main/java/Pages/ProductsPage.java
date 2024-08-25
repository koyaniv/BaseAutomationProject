package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductsPage extends BasePage{


    @FindBy(css = "div.inventory_item")
    private List<WebElement> productsList;

    //First Item
    @FindBy(id = "item_4_title_link")
    private WebElement backupItem;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement backupItemAddToCart;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement backupItemRemoveFromCart;

    @FindBy(id = "item_4_title_link")
    private WebElement backupItemInCartPage;


    @FindBy(css = "div.cart_quantity")
    private WebElement backupItemQTYInCartPage;

    //Second Item
    @FindBy(id = "item_0_title_link")
    private WebElement bikeLightItem;

    @FindBy(id = "item_1_title_link")
    private WebElement tShirtItem;

    @FindBy(id = "inventory_item_name")
    private WebElement jacketItem;

    @FindBy(id = "item_2_title_link")
    private WebElement ohesieItem;

    @FindBy(id = "item_3_title_link")
    private WebElement redTShirtItem;



    @FindBy(css = "div#shopping_cart_container.shopping_cart_container")
    private WebElement CartTitle;

    @FindBy(id = "shopping_cart_container")
    private WebElement btnCart;


    public ProductsPage(WebDriver webDriver) {
        super(webDriver);
    }


    public List<WebElement> getProductsList() {
        return productsList;
    }

    public WebElement getBackupItem() {
        return backupItem;
    }

    public WebElement getBikeLightItem() {
        return bikeLightItem;
    }

    public WebElement gettShirtItem() {
        return tShirtItem;
    }

    public WebElement getJacketItem() {
        return jacketItem;
    }

    public WebElement getOhesieItem() {
        return ohesieItem;
    }

    public WebElement getRedTShirtItem() {
        return redTShirtItem;
    }

    public WebElement getBackupItemAddToCart() {
        return backupItemAddToCart;
    }

    public WebElement getBackupItemRemoveFromCart() {
        return backupItemRemoveFromCart;
    }
    public WebElement getBtnCart() {
        return btnCart;
    }

    public WebElement getCartTitle() {
        return CartTitle;
    }

    public WebElement getBackupItemInCartPage() {
        return backupItemInCartPage;
    }

    public WebElement getBackupItemQTYInCartPage() {
        return backupItemQTYInCartPage;
    }
}
