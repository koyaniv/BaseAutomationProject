package WebTests;

import Infra.Reader.ConfigurationReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    public static WebDriverWait wait = null;
    public static WebDriver webDriver;
    private int port;

    public void invokeBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeDriverService serviceChrome = ChromeDriverService.createDefaultService();
        //port = serviceChrome.getUrl().getPort();
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);  //accept Insecure Certs
        options.addArguments("-js-flags=--expose-gc"); //for memory monitoring
        options.addArguments("--remote-allow-origins=*","ignore-certificate-errors");  //WebSocket issue with Selenium WebDriver + How to deal with certificates
        String downloadFolder = System.getProperty("user.dir") + File.separator + "FusionReports";
        Map<String, String> chromePref = new HashMap<String, String>();
        chromePref.put("download.default_directory", downloadFolder);
        options.setExperimentalOption("prefs", chromePref);
        webDriver = new ChromeDriver(serviceChrome, options);
        System.out.println("Go To URL = " + ConfigurationReader.getInstance().getValue("URL"));
        webDriver.get(ConfigurationReader.getInstance().getValue("URL"));
        webDriver.manage().window().maximize();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(45));
    }


}
