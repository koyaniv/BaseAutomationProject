package Infra.Tools;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static WebTests.BaseTest.wait;


public class WebActions {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";


    public static void wait_until_element_is_up(WebElement element) throws Exception
    {
        try
        {
            wait.until(ExpectedConditions.visibilityOf(element));
            ////ExtentReport.printInfoToReportAndToConsole("Web: Wait for the element visibility :" + element);
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("### ERROR :: Element not found (" + element.toString() + ")");
        }
    }

    public static void clickElement(WebElement elementToClick){
        wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
        if (elementToClick.isEnabled() && elementToClick.isDisplayed()) {
                    elementToClick.click();
            }
        }

    public static void selectWebElementFromList(List<WebElement> elements, String strToSelect)
    {
        boolean existElement = false;
        try
        {
            for (WebElement webElement : elements){
                if (webElement.getText().equals(strToSelect))
                {
                    WebActions.wait_until_element_is_up(webElement);
                    ////ExtentReport.printInfoToReportAndToConsole(ANSI_YELLOW + "Going to click on [" + strToSelect + "] from dropdown list" + ANSI_RESET);
                    clickElement(webElement);
                    existElement = true;
                    break;
                }
            }
            if(!existElement){
                throw new Exception();
            }
        } catch (Exception e)
        {
            ////ExtentReport.printInfoToReportAndToConsole(ANSI_RED + "### ERROR  ::  Element (" + strToSelect + ") not found in the list !!!    "  + e.toString() + ANSI_RESET);
            e.printStackTrace();
        }
       // //ExtentReport.printInfoToReportAndToConsole(ANSI_PURPLE + "selectWebElementFromList method - end" + ANSI_RESET);
    }



}
