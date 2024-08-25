package Infra.Validation;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonArray;


import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Todo - some description
 *
 * @author - Hanna Salameh
 * @date - 06/10/2019 - 9:44
 */
public class Validation {

    public static SoftAssert softAssert = null;




    public static void validateStatusCodeWithPrint(int ActualResult, int expectedResult,boolean print) throws Exception {
        if (expectedResult == ActualResult) {
            if(print){
               System.out.println("[ Excpected status code ] = " + expectedResult + " equals to [ Actual status code ] =  " + ActualResult);
            }

        } else {
            System.out.println("[ Excpected status code ] is =  " + expectedResult + " Not equals to [ Actual status code ] = " + ActualResult);
            throw new Exception("Expected result not equals to actual result");
        }
    }



}