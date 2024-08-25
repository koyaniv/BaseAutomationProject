package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    public static WebDriver webDriver;
    public static WebDriverWait wait;
    public static WebDriverWait waitShort;
    public static WebDriverWait waitLong;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        init();
    }

    public void init() {
        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(35));
    }
}
