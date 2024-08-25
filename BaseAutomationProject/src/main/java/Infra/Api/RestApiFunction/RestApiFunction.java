//package Infra.Api.RestApiFunction;
//
//
//import Infra.Api.ConnectJsonBody.ConnectJsonBody;
//import Infra.Api.JerseyClient.GeneralRest;
//import Infra.Api.RestObject.RestObject;
//import Infra.Reader.ConfigurationReader;
//import Infra.Validation.Validation;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.testng.Assert;
//import org.testng.asserts.SoftAssert;
//
//import java.util.ArrayList;
//
///**
// * Todo - some description
// *
// * @author - Hanna Salameh
// * @date - 06/10/2019 - 9:44
// */
//public class RestApiFunction {
//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";
//
//    public static GeneralRest gr;
//    public static int i = 1;
//    private static final int MAX_RETRIES = 10;
//    public static JsonNode latestResponse = null;
//    public static Integer latestResponseStatus = null;
//    public static String tokenAuth;
//    public static String intentValue = "validate";
//    public static String microServiceId;
//    public static String agencyId;
//    public static String device = "";
//    public static String type = "";
//    public static String transmitter = "";
//    public static String beacon = "";
//    public static String subType = "";
//    public static String offenderId = "";
//    public static String ofenderStatusFromAPI = "";
//    public static String subjectIDFromAPI = "";
//    public static String sybaseIDFromAPI = "";
//    public static String environment = "";
//
//
//    public static void InitGeneralRest() {
//        String ip = ConfigurationReader.getInstance().getValue("IP");
//        String port = ConfigurationReader.getInstance().getValue("PORT");
//        String protocol = ConfigurationReader.getInstance().getValue("PROTOCOL");
//
//        if (protocol != null) {
//            if (protocol.equalsIgnoreCase("http")) {
//                protocol = String.valueOf(Protocol.Http);
//            } else {
//                protocol = String.valueOf(Protocol.Https);
//            }
//        } else {
//            protocol = String.valueOf(Protocol.Https);
//        }
//        gr = new GeneralRest(port, protocol, ip);
//    }
//
//    public static void InitGeneralRest(String MSName) {
//        InitGeneralRest(MSName,false);
//    }
//
//    public static void InitGeneralRest(String MSName,boolean isObjectGeneratorFlow) {
//        String ip = null;
//        if(isObjectGeneratorFlow)
//             ip = ConfigurationReader.getInstance().getValue("IP_FOR_OFFENDER_GENERATOR");
//        else
//             ip = ConfigurationReader.getInstance().getValue("IP");
//        String port = ConfigurationReader.getInstance().getValue("PORT");
//        String protocol = ConfigurationReader.getInstance().getValue("PROTOCOL");
//        ip = MSName + ip;
//        if (protocol != null) {
//            if (protocol.equalsIgnoreCase("http")) {
//                protocol = String.valueOf(Protocol.Http);
//            } else {
//                protocol = String.valueOf(Protocol.Https);
//            }
//        } else {
//            protocol = String.valueOf(Protocol.Https);
//        }
//        gr = new GeneralRest(port, protocol, ip);
//    }
//
//
//    public static String generateIdStringRandom(int length, boolean useLetters, boolean useNumbers) {
//        String id_random;
//        id_random = RandomStringUtils.random(length, useLetters, useNumbers);
//        id_random = "\"" + id_random + "\"";
//        return id_random;
//    }
//
//    public static String generateIdStringRandomWithoutQoutes(int length, boolean useLetters, boolean useNumbers) {
//        String id_random;
//        id_random = RandomStringUtils.random(length, useLetters, useNumbers);
//        return id_random;
//    }
//
//    /**
//     * get the data from the test and set it in the restObj and send PUT request
//     *
//     * @param microService
//     * @param methods
//     * @param data
//     * @param statusCode
//     * @param token
//     */
//    public static void putParam(String microService, String methods, String data, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPutData(microService, methods, data, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            // printReport(restObj);
//            ConnectJsonBody.clearAllObjects();
//           // Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//            if (latestResponse != null) {
//            }
//        } catch (Exception e) {
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static void putParamWithMicroserviceNameWithPrint(String microServiceName, String microService, String methods, String data, int statusCode, boolean token, boolean print) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPutDataPreparedJsonWithPrint(microServiceName, microService, methods, statusCode, restObj, print);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            restObj.setBody(data);
//            gr.setRestObj(restObj);
//            if (print) {
//                printReport(restObj);
//            }
//            printReportConsole(restObj);
//            gr.sendReq();
//            Thread.sleep(1000);
//            Validation.validateStatusCodeWithPrint(gr.getStatusCode(), restObj.getStatusCode(), false);
//            latestResponse = gr.getJsonNodeResponse();
//            latestResponseStatus = gr.getStatusCode();
//        } catch (Exception e) {
//            //  printReport(restObj);
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static void checkIfMicroserviceExitInMongoAndDeleteIt() {
//        try {
//            if (latestResponse.get("data").size() > 0) {
//                String microservice = latestResponse.get("data").get(0).get("id").toString();
//                microservice = microservice.replace("\"", "");
//                deleteParamWithMicroserviceName("proximityservice", "Relations", microservice, 204, false, true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * @param microService
//     * @param methods
//     * @param statusCode
//     * @param token
//     */
//
//    public static void deleteParamWithMicroserviceName(String microserviceName, String microService, String methods, int statusCode, boolean token, boolean saveResponseFromServer) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairDeleteDataWithMicroseriveName(microserviceName, microService, methods, statusCode, restObj);
//
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            if (saveResponseFromServer) {
//                latestResponse = gr.getJsonNodeResponse();
//            }
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static void deleteParamWithUser(String microserviceName, String microService, String methods, String data, int statusCode, boolean token, boolean saveResponseFromServer, boolean userInfo, String userInfoValue) throws Exception {
//
//        RestObject restObj = new RestObject();
//        prepairDeleteDataWithMicroseriveName(microserviceName, microService, methods, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        if (userInfo) {
//            restObj.setUserDelete(true);
//            restObj.setUserDeleteValue(userInfoValue);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            if (saveResponseFromServer) {
//                latestResponse = gr.getJsonNodeResponse();
//            }
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static void deleteParam(String microService, String methods, String data, int statusCode, boolean token, boolean saveResponseFromServer) throws Exception {
//
//        RestObject restObj = new RestObject();
//        prepairDeleteData(microService, methods, data, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            if (saveResponseFromServer) {
//                latestResponse = gr.getJsonNodeResponse();
//            }
//        } catch (Exception e) {
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    /**
//     * get the data from the test and set it in the restObj and send Patch request
//     *
//     * @param microService
//     * @param methods
//     * @param data
//     * @param statusCode
//     * @param token
//     */
//
//    //Origin
//    public static void patchParam(String microService, String methods, String data, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepaiPatchData(microService, methods, data, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//        } catch (Exception e) {
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    //Stas: Adapt to send objects
//    public static void patchParam(String microServiceName, String microService, String methods, String data, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        preparePatchData(microServiceName, microService, methods, data, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            restObj.setBody(data);
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//        } catch (Exception e) {
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static void getIdOfTheMicroService() {
//        if (latestResponseStatus.equals(201)) {
//            if (latestResponse.has("id")) {
//                microServiceId = latestResponse.get("id").toString();
//                microServiceId = microServiceId.replaceAll("\"", "");
//              //  //ExtentReport.test.log(LogStatus.PASS, "microService id is " + microServiceId);
//            }
//        } else if (latestResponseStatus.equals(200)) {
//            if (latestResponse.has("id")) {
//                microServiceId = latestResponse.get("id").toString();
//                microServiceId = microServiceId.replaceAll("\"", "");
//              //  //ExtentReport.test.log(LogStatus.PASS, "microService id is " + microServiceId);
//            }
//        }
//    }
//
//    public static void getIdOfTheMicroService(boolean print) {
//        if (latestResponseStatus.equals(201)) {
//            if (latestResponse.has("id")) {
//                microServiceId = latestResponse.get("id").toString();
//                microServiceId = microServiceId.replaceAll("\"", "");
//                if (print) {
//                   // //ExtentReport.test.log(LogStatus.PASS, "microService id is " + microServiceId);
//                }
//            }
//        } else if (latestResponseStatus.equals(200)) {
//            if (latestResponse.has("id")) {
//                microServiceId = latestResponse.get("id").toString();
//                microServiceId = microServiceId.replaceAll("\"", "");
//                if (print) {
//                  //  //ExtentReport.test.log(LogStatus.PASS, "microService id is " + microServiceId);
//                }
//            }
//        }
//    }
//
//    public static void setDeviceId() {
//        microServiceId = latestResponse.get("data").get(0).get("id").toString();
//    }
//
//    public static void getAgencyId() {
//        agencyId = latestResponse.get("data").get(0).get("agencyID").toString();
//    }
//
//    public static void getIdOfTheMicroServiceByRow(int row) {
//        if (latestResponseStatus.equals(201)) {
//            if (latestResponse.get("data").get(row).has("id")) {
//                microServiceId = latestResponse.get("data").get(row).get("id").toString();
//                microServiceId = microServiceId.replaceAll("\"", "");
//                ////ExtentReport.test.log(LogStatus.PASS, "microService id is " + microServiceId);
//            }
//        } else if (latestResponseStatus.equals(200)) {
//            if (latestResponse.get("data").get(row).has("id")) {
//                microServiceId = latestResponse.get("data").get(row).get("id").toString();
//                microServiceId = microServiceId.replaceAll("\"", "");
//               // //ExtentReport.test.log(LogStatus.PASS, "microService id is " + microServiceId);
//            }
//        }
//    }
//
//    public static String removeQuetsFromString(String value) {
//        String value_updated = "";
//        value_updated = value.replace("\"", "");
//        return value_updated;
//    }
//
//    /**
//     * get the data from the test and set it in the restObj and send Post request to get the token
//     *
//     * @param microService
//     * @param methods
//     * @param data
//     * @param statusCode
//     * @param token
//     */
//    public static void postGetToken(String microService, String methods, String data, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPostData(microService, methods, data, statusCode, restObj);
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//            if (latestResponse != null) {
//                tokenAuth = latestResponse.get("token").toString();
//                tokenAuth = tokenAuth.replace("\"", "");
//               // //ExtentReport.test.log(LogStatus.PASS, "Token is " + tokenAuth);
//
//                restObj.setToken_value(tokenAuth);
//            }
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    /**
//     * @param microService
//     * @param methods
//     * @param data
//     * @param statusCode
//     * @param token
//     */
//    public static void postParam(String microService, String methods, String data, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPostData(microService, methods, data, statusCode, restObj);
//        if (token) {
//            ////ExtentReport.printInfoToReportAndToConsole("@@@@  Required Token = " + tokenAuth);
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//            latestResponseStatus = gr.getStatusCode();
//
//            getIdOfTheMicroService();
//            //for users microservice save the id
////            if(microService.equalsIgnoreCase("users")){
////                if(latestResponse.has("id")){
////                    microService_id = latestResponse.get("id").toString();
////                    microService_id = microService_id.replace("\"", "");
////                    RestAPiInfraStructure.//ExtentReport.test.log(LogStatus.PASS, "User id is "+microService_id);
////                }
////            }
//        } catch (Exception e) {
//          //  //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static JsonNode postParamPreparedJson(String microServiceName, String microservice, String methods, String data, int statusCode, boolean token) throws Exception {
//        postParamPreparedJson( microServiceName, microservice, methods, data, statusCode, token, false);
//        return latestResponse;
//    }
//
//    //Stas: Post prepared Json body
//    public static JsonNode postParamPreparedJson(String microServiceName, String microservice, String methods, String data, int statusCode, boolean token, boolean  isObjectGeneratorFlow) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPostDataPreparedJson(microServiceName, microservice, methods, statusCode, restObj,isObjectGeneratorFlow);
//        if (token) {
//           // //ExtentReport.printInfoToReportAndToConsole("@@@@  Required Token = " + tokenAuth);
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth.replaceAll("\"", ""));
//        }
//        try {
//            restObj.setBody(data);
//            //restObj.setParams(restObj.getParams().replaceAll(" ","%20"));
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            Thread.sleep(1000);
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//            latestResponseStatus = gr.getStatusCode();
//        } catch (Exception e) {
//            //  printReport(restObj);
//          //  //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//        return latestResponse;
//    }
//
//
//    public static void postParamPreparedJsonWithPrint(String microServiceName, String microservice, String methods, String data, int statusCode, boolean token, boolean print) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPostDataPreparedJsonWithPrint(microServiceName, microservice, methods, statusCode, restObj, print);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            restObj.setBody(data);
//            //restObj.setParams(restObj.getParams().replaceAll(" ","%20"));
//            gr.setRestObj(restObj);
//            if (print) {
//                printReport(restObj);
//            }
//            printReportConsole(restObj);
//
//            gr.sendReq();
//            Validation.validateStatusCodeWithPrint(gr.getStatusCode(), restObj.getStatusCode(), false);
//            latestResponse = gr.getJsonNodeResponse();
//            latestResponseStatus = gr.getStatusCode();
//        } catch (Exception e) {
//            //  printReport(restObj);
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static void postParam(String microservice_name, String microService, String methods, String data, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPostData(microservice_name, microService, methods, data, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//            latestResponseStatus = gr.getStatusCode();
//
//            getIdOfTheMicroService();
//
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static void postEquipmentParam(String microService, String methods, String data, int statusCode, boolean token, boolean intent) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPostData(microService, methods, data, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        if (intent) {
//            restObj.setSend_with_intent(true);
//            restObj.setIntent_value(intentValue);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    /**
//     * get the data from the test and set it in the restObj and send Post request with 2 splitted characters
//     *
//     * @param microService
//     * @param methods
//     * @param data
//     * @param statusCode
//     * @param token
//     * @param first
//     * @param last
//     */
//    public static void postParamWithSplittedChars(String microService, String methods, String data, int statusCode, boolean token, String first, String last) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairPostDataWithSplittedChars(microService, methods, data, statusCode, restObj, first, last);
//
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            ConnectJsonBody.clearAllObjects();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static String getSpecificValueFromObjectResponse(String objectName, String fieldName) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(objectName).get("_id").toString();
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//    }
//
//    public static String getValueFromObjectResponse(String objectName, String fieldName) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(objectName).get(fieldName).toString();
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//    }
//
//    public static String getValueOfKeyInsideArrayThatExistInObject(String objectName, String arrayName, int row, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(objectName).get(arrayName).get(row).get(key).toString();
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//    }
//
//    public static String getValueFromFieldInObjectResponse(String objectName, String fieldName, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(objectName).get(fieldName).get(key).toString();
//        } catch (Exception e) {
//          //  //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//    }
//
//    public static String getSpecificValueFromKeyValueResponse(String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(key).toString();
//
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//    }
//
//    public static String getSpecificValueFromKeyValueResponseInsideArrayName(String arrayName, int row, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(arrayName).get(row).get(key).toString();
//
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//    }
//
//    public static String getSpecificValueFromKeyValueResponseInsideArrayName(JsonNode response, String arrayName, int row, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = response.get(arrayName).get(row).get(key).toString();
//
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//    }
//
//    public static String getLastIndexSpecificValueFromKeyValueResponseInsideArrayName(String arrayName, String key) throws Exception {
//        String actual_value = "";
//        try {
//            int size = latestResponse.get(arrayName).size();
//            actual_value = latestResponse.get(arrayName).get(size - 1).get(key).toString();
//        } catch (Exception e) {
//          //  //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return actual_value;
//    }
//
//    public static boolean checkIfkeyExistInObjectInArray(String arrayName, int row, String objectName, String key) throws Exception {
//        try {
//            return latestResponse.get(arrayName).get(row).get(objectName).has(key);
//        } catch (Exception e) {
//          //  //ExtentReport.test.log(LogStatus.FAIL, "The [ Key ] " + key + " does not exist in the response ");
//          //  //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public static String getSpecificValueFromObjectInsideArray(String arrayName, int row, String objectName, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(arrayName).get(row).get(objectName).get(key).toString();
//        } catch (Exception e) {
//          //  //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return actual_value;
//    }
//
//    public static String getSpecificValueFromArrayObjectInsideArray(int indexBigArray, String arrayName, int row, String objectName, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(indexBigArray).get(arrayName).get(row).get(objectName).get(key).toString();
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return actual_value;
//    }
//
//    public static String getSpecificValueFromArrayObjectInsideArrayObject(int indexBigArray, String arrayName, int row, String arrayNameInside, int rowArray, String objectName, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(indexBigArray).get(arrayName).get(row).get(arrayNameInside).get(rowArray).get(objectName).get(key).toString();
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return actual_value;
//    }
//
//    public static String getSpecificValueFromArrayKeyValueInsideArray(int indexBigArray, String arrayName, int row, String key) throws Exception {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(indexBigArray).get(arrayName).get(row).get(key).toString();
//        } catch (Exception e) {
//          //  //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return actual_value;
//    }
//
//    public static String get_specific_value_from_array_inside_object_inside_array(String arrayName, int row, String objectName, String arrayInside, int rowInside) {
//        String actual_value = "";
//        try {
//            actual_value = latestResponse.get(arrayName).get(row).get(objectName).get(arrayInside).get(rowInside).toString();
//        } catch (Exception e) {
//          //  //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//        return actual_value;
//
//    }
//
//    public static JsonNode getLastSpecificValueFromArrayInsideArray(String arrayName, String insideArrayName, int insideRow, String key) throws Exception {
//        JsonNode actual_value = null;
//        try {
//            int size = latestResponse.get(arrayName).size();
//            actual_value = latestResponse.get(arrayName).get(size - 1).get(insideArrayName).get(insideRow).get(key);
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return actual_value;
//    }
//
//    public static JsonNode getLastArrayValueFromArrayInsideArray(String arrayName, String insideArrayName, int insideRow, String insideSecondArrayName, int insideSecondRow) throws Exception {
//
//        JsonNode actual_value = null;
//        try {
//            int size = latestResponse.get(arrayName).size();
//            actual_value = latestResponse.get(arrayName).get(size - 1).get(insideArrayName).get(insideRow).get(insideSecondArrayName);
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, e.getMessage());
//            e.printStackTrace();
//        }
//
//        return actual_value;
//    }
//
//    /**
//     * @param microService
//     * @param methods
//     * @param params
//     * @param statusCode
//     * @param token
//     */
//
//    public static void getParam(String microService, String methods, String params, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepareGetData(microService, methods, params, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//            //  printReport(restObj);
//          //  //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//
//        }
//    }
//
//    //Yaniv - validate subject generator
//    public static boolean getSGAPIParam(String microService, String methods, String params, int statusCode, boolean token,boolean isObjectGeneratorFlow) throws Exception {
//        RestObject restObj = new RestObject();
//        prepareGetData(microService, "", methods, params, statusCode, restObj,isObjectGeneratorFlow);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//            boolean isAlive = Validation.validateStatusCodeForMicroServices(gr.getStatusCode(), restObj.getStatusCode());
//            if(isAlive)
//                return true;
//            else
//                return false;
//        } catch (Exception e) {
////            //  printReport(restObj);
////            //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
////            e.printStackTrace();
////            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//            return false;
//        }
//    }
//
//    //Yaniv
//    public static void getAPIParamWithSpecialCharacters(String microservice_name, String microService, String methods, String params, String first_split, String second_split, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairGetDataWithSpecialCharacters(microservice_name, microService, methods, params, statusCode, restObj, first_split, second_split);
//
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            restObj.setParams(restObj.getParams().replaceAll(" ", "%20"));
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//        } catch (Exception e) {
//            ////ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    public static int getAPIParamWithSpecialCharactersWithoutCheckingStatusCode(String microservice_name, String microService, String methods, String params, String first_split, String second_split, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairGetDataWithSpecialCharacters(microservice_name, microService, methods, params, statusCode, restObj, first_split, second_split);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            restObj.setParams(restObj.getParams().replaceAll(" ", "%20"));
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//        }
//        return gr.getStatusCode();
//    }
//
//    public static GeneralRest getAPIParamWithSpecialCharactersWithoutCheckingStatusCodeReturnArraylist(String microservice_name, String microService, String methods, String params, String first_split, String second_split, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairGetDataWithSpecialCharacters(microservice_name, microService, methods, params, statusCode, restObj, first_split, second_split);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            restObj.setParams(restObj.getParams().replaceAll(" ", "%20"));
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//           // //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//        }
//        return gr;
//    }
//
//    //Oren
//    public static JsonNode getParam(String microserviceName, String microService, String methods, String params, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepareGetData(microserviceName, microService, methods, params, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth.replaceAll("\"", ""));
//        }
//        try {
//            //restObj.setParams(restObj.getParams().replaceAll(" ","%20"));
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//        } catch (Exception e) {
//            //  printReport(restObj);
//          //  //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request = " + gr.getUrl() + " Or the expected status code not equals to actual status code");
//        }
//        return latestResponse;
//    }
//
//    //Yaniv - Verify microserviecs for 'Is Alive' Check for GET API
//    public static JsonNode getParamIsAlive(String microserviceName, String microService, String methods, String params, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepareGetData(microserviceName, microService, methods, params, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth.replaceAll("\"", ""));
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            latestResponse = gr.getJsonNodeResponse();
//            boolean isAlive = Validation.validateStatusCodeForMicroServices(gr.getStatusCode(), restObj.getStatusCode());
//            if(!isAlive){
//                ((ObjectNode) latestResponse).put("status", "DOWN");
//             }
//        } catch (Exception e) {
//        }
//        return latestResponse;
//    }
//
//    public static GeneralRest getRequestRestApiWithoutLogCheckMoreThanStatusCode(String microserviceName, String microService, String methods, String params, int statusCode, String moreStatusCode, boolean token) {
//        RestObject restObj = new RestObject();
//        prepareGetData(microserviceName, microService, methods, params, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReportConsole(restObj);
//            gr.sendReq();
//
//            boolean check = Validation.validateStatusCodeWithoutLogMoreThanStatusCode(microserviceName, gr.getStatusCode(), restObj.getStatusCode(), moreStatusCode, gr.getResponse());
//            if (check == false) {
//              //  //ExtentReport.printInfoToReportAndToConsole("wait 30 sec and send the request again to check the status code");
//                Thread.sleep(30000);
//                gr.sendReq();
//                check = Validation.validateStatusCodeWithoutLogMoreThanStatusCode(microserviceName, gr.getStatusCode(), restObj.getStatusCode(), moreStatusCode, gr.getResponse());
//                if (check == false) {
//                  //  //ExtentReport.printFailToReportAndToConsole("[microservice] -> " + microserviceName + " [none of the all expected status code ] are =  " + moreStatusCode + " Not equals to [ Actual status code ] = " + gr.getStatusCode());
//                    if (gr.getResponse() != null) {
//                  //      //ExtentReport.printFailToReportAndToConsole("Actual Response is " + gr.getResponse());
//                    }
//                }
//            }
//
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//            printReport(restObj);
//          //  //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//          //  //ExtentReport.printFailToReportAndToConsole("Fail in sending the request Or the expected status code not equals to actual status code");
//            e.printStackTrace();
////            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//        return gr;
//    }
//
//    public static GeneralRest getRequestRestApiWithoutLog(String microserviceName, String microService, String methods, String params, int statusCode, boolean token) {
//        RestObject restObj = new RestObject();
//        prepareGetData(microserviceName, microService, methods, params, statusCode, restObj);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReportConsole(restObj);
//            gr.sendReq();
//
//            Validation.validateStatusCodeWthoutLog(microserviceName, gr.getStatusCode(), restObj.getStatusCode(), gr.getResponse());
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//            printReport(restObj);
//         //   //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//         //   //ExtentReport.printFailToReportAndToConsole("Fail in sending the request Or the expected status code not equals to actual status code");
//            e.printStackTrace();
////            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//        return gr;
//    }
//
//    public static GeneralRest getRequestRestApiWithoutLogWithSplitCharacter(String microserviceName, String microService, String methods, String params, String first_split, String second_split, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairGetDataWithSpecialCharacters(microserviceName, microService, methods, params, statusCode, restObj, first_split, second_split);
//
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            gr.setRestObj(restObj);
//            printReportConsole(restObj);
//            gr.sendReq();
//
//            Validation.validateStatusCodeWthoutLog(microserviceName, gr.getStatusCode(), restObj.getStatusCode(), gr.getResponse());
//
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//            printReport(restObj);
//         //   //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//         //   //ExtentReport.printFailToReportAndToConsole("Fail in sending the request Or the expected status code not equals to actual status code");
//            e.printStackTrace();
////            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//        return gr;
//    }
//
//    //Hanna
//    public static void getParamWithSpecialChar(String microService, String methods, String params, String firstSplit, String secondSplit, int statusCode, boolean token) throws Exception {
//        RestObject restObj = new RestObject();
//        prepairGetDataWithSpecialChar(microService, methods, params, statusCode, restObj, firstSplit, secondSplit);
//        if (token) {
//            restObj.setSend_with_token(true);
//            restObj.setToken_value(tokenAuth);
//        }
//        try {
//            //restObj.setParams(restObj.getParams().replaceAll(" ","%20"));
//            gr.setRestObj(restObj);
//            printReport(restObj);
//            gr.sendReq();
//            Validation.validateStatusCode(gr.getStatusCode(), restObj.getStatusCode());
//            latestResponse = gr.getJsonNodeResponse();
//
//        } catch (Exception e) {
//            //ExtentReport.test.log(LogStatus.FAIL, gr.getResponse());
//            e.printStackTrace();
//            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
//        }
//    }
//
//    /**
//     * add all the data to the body and insert it to the restObj
//     *
//     * @param data
//     * @param restObj
//     */
//    public static void setBodyValues(String data, RestObject restObj) {
//        String[] bodySplit = data.split(",");
//        if (!data.equalsIgnoreCase("Data")) {
//            for (int i = 0; i < bodySplit.length; i++) {
//                String[] key_value = bodySplit[i].split(":");
//                String key = key_value[0].trim();
//                String value = key_value[1].trim();
//                restObj.addToBody(key.trim(), value.trim());
//            }
//        } else {
//            restObj.addToBody("Data", "Data");
//        }
//    }
//
//    public static void setBodyValuesWithSplittedChars(String data, RestObject restObj, String first, String last) {
//        String[] bodySplit = data.split(first);
//        if (!data.equalsIgnoreCase("Data")) {
//            for (int i = 0; i < bodySplit.length; i++) {
//                String[] key_value = bodySplit[i].split(last);
//                String key = key_value[0].trim();
//                String value = key_value[1].trim();
//                restObj.addToBody(key.trim(), value.trim());
//            }
//        } else {
//            restObj.addToBody("Data", "Data");
//        }
//    }
//
//    /**
//     * add the index data to the url and set it in the restObj
//     *
//     * @param method
//     * @param restObj
//     */
//    public static void setIndexValues(String method, RestObject restObj) {
//        String[] index_values = method.split(",");
//        for (int i = 0; i < index_values.length; i++) {
//            restObj.addToMethod(index_values[i]);
//        }
//    }
//
//    /**
//     * add the params that exist in the url to the restObj
//     *
//     * @param params
//     * @param restObj
//     */
//    public static void setParamsValues(String params, RestObject restObj) {
//        if (params.contains(":"))//params contain key:value format
//        {
//            String[] paramsSplit = params.split(",");
//            for (int i = 0; i < paramsSplit.length; i++) {
//                String[] key_value = paramsSplit[i].split(":");
//                String key = key_value[0].trim();
//                String value = key_value[1].trim();
//                restObj.addToParams(key, value);
//            }
//        } else {
//            restObj.setParams(params);//params format as postman
//        }
//    }
//
//    public static void setParamsValuesWithSpecialChar(String params, RestObject restObj, String firstSplit, String secondSplit) {
//        String[] paramsSplit = params.split(firstSplit);
//
//        for (int i = 0; i < paramsSplit.length; i++) {
//            String[] key_value = paramsSplit[i].split(secondSplit);
//            String key = key_value[0];
//            String value = key_value[1];
//            restObj.addToParams(key, value);
//        }
//    }
//
//    /**
//     * put all the neccessary Data in RestAPiInfraStructure.RestObject and create new object from the GeneralRest with PUT HTTPMETHOD
//     *
//     * @param microService
//     * @param index
//     * @param data
//     * @param statusCode
//     * @param restObj
//     */
//    public static void prepairPutData(String microService, String index, String data, int statusCode, RestObject restObj) {
//       // //ExtentReport.test.log(LogStatus.PASS, "Send PUT Request to MicroService " + microService);
//
//        InitGeneralRest();
//        restObj.setOperation(String.valueOf(Operation.PUT));
//        restObj.setMicroservice(microService.trim());
//        restObj.setStatusCode(statusCode);
//        if (data.equals("NoData")) {
//            restObj.setBody("{}");
//        } else {
//            setBodyValues(data, restObj);
//            if (ConnectJsonBody.all_body != "") {
//                restObj.addToBodyWithObjectsAndArrays(ConnectJsonBody.all_body);
//                restObj.checkBody();
//            }
//        }
//        if (index.equals("NoData")) {
//            restObj.setMethod("");
//
//        } else {
//            setIndexValues(index, restObj);
//        }
//    }
//
//    /**
//     * put all the neccessary Data in RestAPiInfraStructure.RestObject and create new object from the GeneralRest with PATCH HTTPMETHOD
//     *
//     * @param tableName
//     * @param index
//     * @param data
//     * @param statusCode
//     * @param restObj
//     */
//    public static void prepaiPatchData(String tableName, String index, String data, int statusCode, RestObject restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send PATCH Request Value to MicroService " + tableName);
//
//
//        InitGeneralRest();
//        restObj.setOperation(String.valueOf(Operation.PATCH));
//        restObj.setMicroservice(tableName.trim());
//        restObj.setStatusCode(statusCode);
//        if (data.equals("NoData")) {
//            restObj.setBody("{}");
//        } else {
//            setBodyValues(data, restObj);
//            if (ConnectJsonBody.all_body != "") {
//                restObj.addToBodyWithObjectsAndArrays(ConnectJsonBody.all_body);
//                restObj.checkBody();
//            }
//        }
//        if (index.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(index, restObj);
//        }
//    }
//
//    //Stas: Adapt to send objects
//    public static void preparePatchData(String microserviceName, String tableName, String index, String data,
//                                        int statusCode, RestObject restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send PATCH Request Value to MicroService " + tableName);
//
//        InitGeneralRest(microserviceName);
//        restObj.setOperation(String.valueOf(Operation.PATCH));
//        restObj.setMicroservice(tableName.trim());
//        restObj.setStatusCode(statusCode);
//        if (data.equals("NoData")) {
//            restObj.setBody("{}");
//        }/*else{
//            setBodyValues(data , restObj);
//            if(ConnectJsonBody.all_body !=""){
//                restObj.addToBodyWithObjectsAndArrays(ConnectJsonBody.all_body);
//                restObj.checkBody();
//            }
//        }*/
//        if (index.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(index, restObj);
//        }
//    }
//
//    /**
//     * put all the neccessary Data in RestAPiInfraStructure.RestObject and create new object from the GeneralRest with POST HTTP METHOD
//     *
//     * @param tableName
//     * @param index
//     * @param data
//     * @param statusCode
//     * @param restObj
//     */
//    public static void prepairPostData(String tableName, String index, String data, int statusCode, RestObject
//            restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send POST Request to MicroService " + tableName);
//
//        InitGeneralRest();
//        restObj.setOperation(String.valueOf(Operation.POST));
//        restObj.setMicroservice(tableName.trim());
//        restObj.setStatusCode(statusCode);
//        if (data.equals("NoData")) {
//            restObj.setBody("{}");
//        } else {
//            setBodyValues(data, restObj);
//            if (ConnectJsonBody.all_body != "") {
//                restObj.addToBodyWithObjectsAndArrays(ConnectJsonBody.all_body);
//                restObj.checkBody();
//            }
//        }
//        if (index.equals("NoData")) {
//            restObj.setMethod("");
//
//        } else {
//            setIndexValues(index, restObj);
//        }
//    }
//
//    public static void prepairPutDataPreparedJsonWithPrint(String microServiceName, String microservice, String method,
//                                                           int statusCode, RestObject restObj, boolean print) {
//        if (print) {
//            //ExtentReport.test.log(LogStatus.PASS, "Send PUT Request to MicroService " + microservice);
//        }
//
//        InitGeneralRest(microServiceName);
//        restObj.setOperation(String.valueOf(Operation.PUT));
//        restObj.setMicroservice(microservice.trim());
//        restObj.setStatusCode(statusCode);
//
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(method, restObj);
//        }
//    }
//
//
//    public static void prepairPostDataPreparedJson(String microServiceName, String microservice, String method, int statusCode, RestObject restObj, boolean isObjectGeneratorFlow) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send POST Request to MicroService " + microservice);
//
//        InitGeneralRest(microServiceName,isObjectGeneratorFlow);
//        restObj.setOperation(String.valueOf(Operation.POST));
//        restObj.setMicroservice(microservice.trim());
//        restObj.setStatusCode(statusCode);
//
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(method, restObj);
//        }
//    }
//
//
//    public static void prepairPostDataPreparedJsonWithPrint(String microServiceName, String microservice, String method,
//                                                            int statusCode, RestObject restObj, boolean print) {
//        if (print) {
//            //ExtentReport.test.log(LogStatus.PASS, "Send POST Request to MicroService " + microservice);
//
//        }
//        InitGeneralRest(microServiceName);
//        restObj.setOperation(String.valueOf(Operation.POST));
//        restObj.setMicroservice(microservice.trim());
//        restObj.setStatusCode(statusCode);
//
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(method, restObj);
//        }
//    }
//
//    public static void prepairPostData(String microserviceName, String tableName, String index, String data,
//                                       int statusCode, RestObject restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send POST Request to MicroService " + tableName);
//
//        InitGeneralRest(microserviceName);
//        restObj.setOperation(String.valueOf(Operation.POST));
//        restObj.setMicroservice(tableName.trim());
//        restObj.setStatusCode(statusCode);
//        if (data.equals("NoData")) {
//            restObj.setBody("{}");
//        } else {
//            setBodyValues(data, restObj);
//            if (ConnectJsonBody.all_body != "") {
//                restObj.addToBodyWithObjectsAndArrays(ConnectJsonBody.all_body);
//                restObj.checkBody();
//            }
//        }
//        if (index.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(index, restObj);
//        }
//    }
//
//    public static void prepairPostDataWithSplittedChars(String tableName, String index, String data,
//                                                        int statusCode, RestObject restObj, String first, String last) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send POST Request to MicroService " + tableName);
//
//        InitGeneralRest();
//        restObj.setOperation(String.valueOf(Operation.POST));
//        restObj.setMicroservice(tableName.trim());
//        restObj.setStatusCode(statusCode);
//        if (data.equals("NoData")) {
//            restObj.setBody("{}");
//        } else {
//            setBodyValuesWithSplittedChars(data, restObj, first, last);
//            if (ConnectJsonBody.all_body != "") {
//                restObj.addToBodyWithObjectsAndArrays(ConnectJsonBody.all_body);
//                restObj.checkBody();
//            }
//        }
//        if (index.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(index, restObj);
//        }
//    }
//
//    public static void prepairDeleteData(String tableName, String index, String params, int statusCode, RestObject
//            restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send DELETE Request to MicroService " + tableName);
//        InitGeneralRest();
//        restObj.setOperation(String.valueOf(Operation.DELETE));
//        restObj.setMicroservice(tableName.trim());
//        restObj.setStatusCode(statusCode);
//        restObj.setBody("{");
//        if (index.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(index, restObj);
//        }
//
//        if (params.equals("NoData")) {
//            restObj.setParams("");
//        } else {
//            setParamsValues(params, restObj);
//        }
//    }
//
//    /**
//     * put all the neccessary Data in RestAPiInfraStructure.RestObject and create new object from the GeneralRest with GET HTTPMETHOD
//     *
//     * @param microService
//     * @param method
//     * @param params
//     * @param statusCode
//     * @param restObj
//     */
//    public static void prepareGetData(String microService, String method, String params, int statusCode, RestObject
//            restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send Get Request to MicroService " + microService);
//        InitGeneralRest();
//        restObj.setOperation(String.valueOf(Operation.GET));
//        restObj.setMicroservice(microService.trim());
//        restObj.setStatusCode(statusCode);
//        restObj.setBody("{");
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(method, restObj);
//        }
//
//        if (params.equals("NoData")) {
//            restObj.setParams("");
//        } else {
//            setParamsValues(params, restObj);
//        }
//    }
//
//    //Yaniv
//    public static void prepairGetDataWithSpecialCharacters(String microservice_name, String microService, String
//            method, String params, int statusCode, RestObject restObj, String first_split, String second_split) {
////        //ExtentReport.test.log(LogStatus.PASS, "Send Get Request to MicroService " + microService);
//        InitGeneralRest(microservice_name);
//        restObj.setOperation(String.valueOf(Operation.GET));
//        restObj.setMicroservice(microService.trim());
//        restObj.setStatusCode(statusCode);
//        restObj.setBody("{");
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//
//        } else {
//            setIndexValues(method, restObj);
//        }
//
//        if (params.equals("NoData")) {
//            restObj.setParams("");
//        } else {
//            setParamsValuesWithSpecialChar(params, restObj, first_split, second_split);
//        }
//    }
//
//    public static void prepairDeleteDataWithMicroseriveName(String microservice_name, String microService, String
//            method, int statusCode, RestObject restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send DELETE Request to MicroService " + microService);
//        InitGeneralRest(microservice_name);
//        restObj.setOperation(String.valueOf(Operation.DELETE));
//        restObj.setMicroservice(microService.trim());
//        restObj.setStatusCode(statusCode);
//        restObj.setBody("{");
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//
//        } else {
//            setIndexValues(method, restObj);
//        }
//    }
//
//    public static void prepareGetData(String microserviceName, String microService, String method, String params,
//                                      int statusCode, RestObject restObj) {
//        prepareGetData(microserviceName, microService, method, params,statusCode, restObj, false);
//    }
//
//    //Oren
//    public static void prepareGetData(String microserviceName, String microService, String method, String params,
//                                      int statusCode, RestObject restObj,boolean isObjectGeneratorFlow) {
//        System.out.println("Send Get Request to MicroService " + microserviceName);
//        InitGeneralRest(microserviceName,isObjectGeneratorFlow);
//        restObj.setOperation(String.valueOf(Operation.GET));
//        if ((microService != null)&&!(microService.isEmpty()))
//            restObj.setMicroservice(microService.trim());
//        restObj.setStatusCode(statusCode);
//        restObj.setBody("{");
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(method, restObj);
//        }
//
//        if (params.equals("NoData")) {
//            restObj.setParams("");
//        } else {
//            setParamsValues(params, restObj);
//        }
//    }
//
//    //Hanna
//    private static void prepairGetDataWithSpecialChar(String microService, String method, String params,
//                                                      int statusCode, RestObject restObj, String firstSplit, String secondSplit) {
//        //ExtentReport.test.log(LogStatus.PASS, "Send Get Request to MicroService " + microService);
//        InitGeneralRest();
//        restObj.setOperation(String.valueOf(Operation.GET));
//        restObj.setMicroservice(microService.trim());
//        restObj.setStatusCode(statusCode);
//        restObj.setBody("{");
//        if (method.equals("NoData")) {
//            restObj.setMethod("");
//        } else {
//            setIndexValues(method, restObj);
//        }
//
//        if (params.equals("NoData")) {
//            restObj.setParams("");
//        } else {
//            //used when param contain dates
//            setParamsValuesWithSpecialChar(params, restObj, firstSplit, secondSplit);
//        }
//    }
//
//    /**
//     * but the neccessary data to the Report
//     *
//     * @param restObj
//     */
//    public static void printReport(RestObject restObj) {
//        //ExtentReport.test.log(LogStatus.PASS, "-------------------------------------------------------------------------------------------------------");
//        //ExtentReport.test.log(LogStatus.PASS, "HTTP_MEDTHOD = [ " + restObj.getOperation() + " ] , URL = " + gr.getUrl());
//        //ExtentReport.test.log(LogStatus.PASS, "BODY = " + restObj.getBody() + " }");
//        System.out.println("----------------------------------    REQUEST    ----------------------------------------------");
//        System.out.println("#HTTP_MEDTHOD = [ " + restObj.getOperation() + " ] , URL = " + gr.getUrl());
//        System.out.println("#BODY = " + restObj.getBody() + " }");
//        System.out.println("-------------------------------------------------------------------------------------------------------");
//    }
//
//    public static void printReportConsole(RestObject restObj) {
//        System.out.println("----------------------------------    REQUEST    ----------------------------------------------");
//        System.out.println("HTTP_MEDTHOD = [ " + restObj.getOperation() + " ] , URL = " + gr.getUrl());
//        System.out.println("BODY = " + restObj.getBody() + " }");
//        System.out.println("-------------------------------------------------------------------------------------------------------");
//    }
//
////    public static void printSubjectGeneratorResponseList(ArrayList<OffenderGeneratorResponse> subjectGeneratorResponseList){
////        if (subjectGeneratorResponseList.size() > 0) {
////            printInfoToReportAndToConsole(ANSI_YELLOW + "Subject Generator Response List contain the following details:" + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- OffenderID: " + subjectGeneratorResponseList.get(0).getOffender() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- Type: " + subjectGeneratorResponseList.get(0).getType() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- SubType: " + subjectGeneratorResponseList.get(0).getSubType() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- DeviceSN: " + subjectGeneratorResponseList.get(0).getDevice() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- Transmitter: " + subjectGeneratorResponseList.get(0).getTransmitter() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- Beacon: " + subjectGeneratorResponseList.get(0).getBeacon() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- SubjectID: " + subjectGeneratorResponseList.get(0).getSubjectID() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- SybaseID: " + subjectGeneratorResponseList.get(0).getSybaseID() + ANSI_RESET);
////            printInfoToReportAndToConsole(ANSI_YELLOW + "- Status: " + subjectGeneratorResponseList.get(0).getStatus()+ ANSI_RESET);
////        } else printInfoToReportAndToConsole(ANSI_RED + "Subject Generator Response List is empty." + ANSI_RESET);
////    }
//
//    /* Example for Tracker 100 offender generation using RestAPI POST to Subject Generator:
//    URL:
//        Https://subject-generatordevopsdevopstools.dev.attentigroup.com/automation
//    BODY:
//        {
//            "numOfSubjects": 1,
//            "doActivation": true,
//            "doDownload": false,
//            "shouldAttachVictim": false,
//            "programType": 2200,
//            "programSubType": 0,
//            "address": "10.107.48.147",
//            "offenderRefIDPrefix": "devA",
//            "addZone": false,
//            "selectedZone": [],
//            "numOfVictimsToAttach": 1,
//            "startProgramTime": "2024-12-22T09:43:55.901Z", - Optional
//            "endProgramTime": "2025-01-22T09:43:55.901Z" - Optional
//        } */
////    public static ArrayList<OffenderGeneratorResponse> addNewOffenderViaSubjectGenerator(String program, boolean withReceiver, boolean withTransmitter, CellularDataObject cellDataObject, boolean doActivation, boolean shouldAttachToVictim) throws Exception{
////
////        return addNewOffenderViaSubjectGenerator(program, withReceiver, withTransmitter,false, cellDataObject, doActivation, shouldAttachToVictim, 0, null, null, false, null);
////    }
////    public static ArrayList<OffenderGeneratorResponse> addNewOffenderViaSubjectGenerator(String program, boolean withReceiver, boolean withTransmitter,boolean withBeacon, CellularDataObject cellDataObject, boolean doActivation, boolean shouldAttachToVictim, Integer numOfVictimsToAttach, String startProgramTime, String endProgramTime, boolean addZone, Const.ZoneTypes zoneType) throws Exception {
////        OffenderGeneratorResponse offenderGeneratorResponse = new OffenderGeneratorResponse();
////        ArrayList<OffenderGeneratorResponse> responseDvList = new ArrayList<>();
////        ArrayList<String> selectedZoneList = new ArrayList<String>();
////
////        //Add up to TWO zone types as String to SG's POST: "Inclusion" or "Inclusion", "Exclusion"
////        if(addZone){
////                if (zoneType.equals(Const.ZoneTypes.INCLUSION_AND_EXCLUSION)){
////                    selectedZoneList.add(zoneType.toString().split(",")[0].trim());
////                    selectedZoneList.add(zoneType.toString().split(",")[1].trim());
////                }
////                else
////                    selectedZoneList.add(zoneType.toString());
////            }
////
////        int runProgram = -1;
////        int runProgramsSubType= -1;
////        String programSubType = null;
////
////        String offenderPrefix = ConfigurationReader.env == ConfigurationReader.RUNNING_ENV.LOCAL ? Const.DEV_AUTO : Const.AUTO;
////        environment = ConfigurationReader.getInstance().getValue("SERVER_WEB_IP");
////
////        switch (program) {
////            //Regular Offender
////            case Const.ONE_PIECE_V10_PROGRAM:
////                if ((withTransmitter)||(withBeacon)) {
////                    runProgram =  Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.ONE_PIECE_WITH_BEACON.toString());
////                } else {
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.ONE_PIECE.toString());
////                }
////                shouldAttachToVictim = false;
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.REGULAR.toString());
////                programSubType = "REGULAR";
////                break;
////
////            case Const.TWO_PIECE_V10_PROGRAM:
////                if (withTransmitter) {//with BCL
////                    if (withBeacon) {//with BCL and BCN
////                        runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.TWO_PIECE_WITH_BCL_AND_BCN.toString());
////                    } else {//with BCL but without BCN
////                        runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.TWO_PIECE_WITH_BCL.toString());
////                    }
////                }else {//without BCL
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.TWO_PIECE.toString());
////                }
////                shouldAttachToVictim = false;
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.REGULAR.toString());
////                programSubType = "REGULAR";
////                break;
////
////            case Const.HUB500_PHB_PROGRAM:
////                if (withTransmitter) {
////                    runProgram =  Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.PHB_WITH_BCL.toString());
////                } else {
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.PHB.toString());
////                }
////                shouldAttachToVictim = false;
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.REGULAR.toString());
////                programSubType = "REGULAR";
////                break;
////
////            case Const.HUB300_PHB_PROGRAM:
////                if (withTransmitter) {
////                    runProgram =  Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.PHB_HUB300_WITH_BCL.toString());
////                } else {
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.PHB_HUB300.toString());
////                }
////                shouldAttachToVictim = false;
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.REGULAR.toString());
////                programSubType = "REGULAR";
////                break;
////
////            //For DV (Aggressor/Victim)
////            case Const.TWO_PIECE_PROGRAM_VICTIM_NG_FOR_DV:
////                runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.TWO_PIECE.toString());
////                shouldAttachToVictim = false;
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.VICTIM.toString());
////                programSubType = "VICTIM";
////                break;
////
////            case Const.ONE_PIECE_PROGRAM_AGGRESSOR_NG_FOR_DV:
////                if (withReceiver)
////                {
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.ONE_PIECE_WITH_BEACON.toString());
////                }else{
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.ONE_PIECE.toString());
////                }
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.AGGRESSOR.toString());
////                programSubType = "AGGRESSOR";
////                break;
////            case Const.TWO_PIECE_PROGRAM_AGGRESSOR_NG_FOR_DV:
////                if (withBeacon)//Tracker 200 Aggressor with BCL and BCN
////                {
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.TWO_PIECE_WITH_BCL_AND_BCN.toString());
////
////                }else//Tracker 200 Aggressor with BCL without BCN
////                {
////                    runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.TWO_PIECE_WITH_BCL.toString());
////                }
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.AGGRESSOR.toString());
////                programSubType = "AGGRESSOR";
////                break;
////            default:
////                runProgram = Integer.parseInt(Const.SubjectGeneratorProgramsTypeEnum.TWO_PIECE.toString());
////                runProgramsSubType = Integer.parseInt(Const.SubjectGeneratorProgramsSubTypeEnum.REGULAR.toString());
////                programSubType = "REGULAR";
////        }
//
//        //populate the request via request object
////        OffenderGeneratorRequest offenderGeneratorRequest = new OffenderGeneratorRequest(false,1,doActivation,false,shouldAttachToVictim,runProgram,runProgramsSubType,environment,offenderPrefix, addZone,selectedZoneList, numOfVictimsToAttach, startProgramTime, endProgramTime);
////        //Build Json str from request object
////        String bodyJsonStr = ApiHelper.convertToJson(offenderGeneratorRequest, false).substring(0, ApiHelper.convertToJson(offenderGeneratorRequest, false).length() - 1);
////        //Run Post
////
////        JsonNode response = null;
////        try {
////            response = postParamPreparedJson(ConfigurationReader.getInstance().getValue("SUBJECT_GENERATOR_URL"), "automation", "NoData", bodyJsonStr, 200, false, true);
////        } catch (Exception e) {
////            //ExtentReport.printFailToReportAndToConsole(ANSI_RED + "Fail in sending the request to Subject Generator" + ANSI_RESET);
////
////        };
//
//        //Print beautified Json response...
//        //ExtentReport.printInfoToReportAndToConsole(ApiHelper.beautifyJsonResponse(response));
//
//        //Perform Subject Generator POST retries in case of Offender creation or activation failure or any Exception(504, 502...)
//       // performSubjectGeneratorPOSTRetries(response, doActivation, MAX_RETRIES, offenderGeneratorRequest);
//
//        //Add response to response object
////        if (response.size() > 0) {
////            for (int i = 0; i < response.size(); i++) {
////                offenderGeneratorResponse.setOffender(response.get(i).get("offender").asText());
////                offenderGeneratorResponse.setSubType(programSubType); //Subject Generator response doesn't include sub-type
////                offenderGeneratorResponse.setDevice(response.get(i).get("devices").get(0).get("serialNumber").asText());
////                if (response.get(i).get("devices").size() > 1) { //Checking if the 2nd device is Bracelet / Beacon
////                    if(response.get(i).get("devices").get(1).get("type").asText().equals("Beacon")){
////                        offenderGeneratorResponse.setBeacon(response.get(i).get("devices").get(1).get("serialNumber").asText());
////                    } else
////                        offenderGeneratorResponse.setTransmitter(response.get(i).get("devices").get(1).get("serialNumber").asText());
////                }
////                if(response.get(i).get("devices").size() > 2){
////                    if (response.get(i).get("devices").get(2).get("type").asText().equals("Beacon")) {
////                        offenderGeneratorResponse.setBeacon(response.get(i).get("devices").get(2).get("serialNumber").asText());
////                    }
////                }
////                offenderGeneratorResponse.setStatus(response.get(i).get("status").asText());
////                offenderGeneratorResponse.setType(response.get(i).get("devices").get(0).get("type").asText());
////                if(doActivation)
////                    offenderGeneratorResponse.setSubjectID(response.get(i).get("subjectID").asText());
////                if (response.get(i).has("sybaseID")) {
////                    offenderGeneratorResponse.setSybaseID(response.get(i).get("sybaseID").asText());
////                }else{
////                    //ExtentReport.printFailToReportAndToConsole("No 'sybaseID' found on the Subject generator response");
////                    Assert.fail("No 'sybaseID' found on the Subject generator response");
////                }
////                int size  = responseDvList.size();
////                responseDvList.add(offenderGeneratorResponse);
////                if (responseDvList.size()==size)
////                    responseDvList.add(offenderGeneratorResponse);
////                offenderGeneratorResponse = new OffenderGeneratorResponse();
////            }
////        } else {
////            throw new Exception("#########     API response for Offender/DV is empty !!!     ########");
////        }
////        return  responseDvList;
////    }
//
//    //This methode is being used to generate BODY for the SubjectGeneratorTriggerRule POST API
//    //When you wish to use internal defaults for the values section, the values' argument value should be null.
//    //When you wish to inject different values than the defaults, you must set them in values argument.
////    public static String buildBodyForTriggerRuleWithSubjectGeneratorAPI(String serialNumber, int ruleID, String ruleGroup, String ruleName, String utcTimeJsonFormat, boolean attachLocation, String values) throws Exception {
////
////        //initialize TriggerRule Object
////        SubjectGeneratorTriggerRuleValues ruleValues = new SubjectGeneratorTriggerRuleValues("$$$");
////        SubjectGeneratorTriggerRule subjectGeneratorTriggerRule = new SubjectGeneratorTriggerRule(ruleValues, ruleID, serialNumber, ruleGroup, ruleName, utcTimeJsonFormat, attachLocation);
////
////        String bodyJsonStr = ApiHelper.convertToJson(subjectGeneratorTriggerRule,false);
////
////        //TODO: Need to add all relevant vales per rule we wish to implement (Each rule has different numbers/types of values)
////        if (values == null) { //This is used if we want to override the defaults.
////            values = "";
////            String stringKey1;
////            String stringKey2;
////            int intKey1;
////            int intKey2;
////            switch (ruleID) {
////                case 422: //BatteryStatus | SM400 - BatteryFull
////                    stringKey1 = "\"SOC\": 99";
////                    stringKey2 = "\"batteryFullThreshold\": 99";
////                    values = stringKey1 + "," + stringKey2;
////                    break;
////                case 424: //BatteryStatus | SM400 - BatteryLow
////                    stringKey1 = "\"SOC\": 12";
////                    stringKey2 = "\"batteryLowThreshold\": 24";
////                    values = stringKey1 + "," + stringKey2;
////                    break;
////                case 1713: //BeaconCurfew | SM1700 - Linked Partner returned, Violation
////                case 1763: //BeaconCurfew | SM1700 - Linked Partner in range, Violation Restored
////                case 2222: //StrapTamper | SM2200 - StrapTamper
////                case 2231: //StrapTamper | SM2200 - StrapTamperRestored
////                case 2233: //StrapTamper | SM2200 - Strap Still in Tamper
////                case 9022: //RestMode | SM9000 - DeviceAtRest
////                case 9031: //RestMode | SM9000 - MovementDetected
////                    break;
////                default:
////                    System.out.println("Unsupported RuleID = [" + ruleID + "],  was requested.");
////                    System.out.println("Please add the RuleID to the triggerRuleWithSubjectGeneratorAPI method.");
////            }
////        }
////        return bodyJsonStr.replace("\"values\":\"$$$\"", values);
////    }
////
////    public static boolean triggerRuleWithSubjectGeneratorAPI(String bodyJsonStr) throws Exception {
////        try{
////            postParamPreparedJson(ConfigurationReader.getInstance().getValue("SUBJECT_GENERATOR_URL"), "triggerRule", "NoData", bodyJsonStr.substring(0, bodyJsonStr.length() - 1), 200, false, true);
////            if (!latestResponseStatus.equals(200)) {
////                return false;
////            }
////
////        }catch (Exception e){
////            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
////        }
////        return true;
////    }
////
////    public static void createLocationWithSubjectGeneratorAPI(String utcTimeJsonFormat, String timeZone, double accuracy, int speedKMH, int heading, String locationMethod, double lat, double lon, String serialNumber) throws Exception
////    {
////        createLocationWithSubjectGeneratorAPI(utcTimeJsonFormat, timeZone, accuracy,  speedKMH,  heading,  locationMethod,  lat,  lon, serialNumber, false, null, null);
////    }
////
////    public static void createLocationWithSubjectGeneratorAPI(String utcTimeJsonFormat, String timeZone, double accuracy, int speedKMH, int heading, String locationMethod, double lat, double lon, String serialNumber, boolean shouldAddRuleID, String ruleID, String rulesValues) throws Exception
////    {
////
////        //initialize subject Generator Time Object
////        SubjectGeneratorTime subjectGeneratorTime = new SubjectGeneratorTime(utcTimeJsonFormat, timeZone);
////        SubjectGeneratorProperties subjectGeneratorProperties = new SubjectGeneratorProperties(subjectGeneratorTime, accuracy,speedKMH, heading, locationMethod, false);
////        SubjectGeneratorTriggerRuleValues subjectGeneratorTriggerRuleValues = new SubjectGeneratorTriggerRuleValues("$$$");
////        SubjectGeneratorTriggerRule subjectGeneratorTriggerRule = new SubjectGeneratorTriggerRule();
////
////        if(shouldAddRuleID){
////            subjectGeneratorTriggerRule.setRuleID(Integer.parseInt(ruleID));
////            subjectGeneratorTriggerRule.setTime(utcTimeJsonFormat);
////            if(rulesValues == null)
////                subjectGeneratorTriggerRule.setValues(null);
////            else
////                subjectGeneratorTriggerRule.setValues(subjectGeneratorTriggerRuleValues);
////            subjectGeneratorTriggerRule.setAttachLocation(null);
////            subjectGeneratorProperties.setRuleDetails(subjectGeneratorTriggerRule);
////        }
////
////        //initialize coordinates Object
////        ArrayList<Double> coordinates = new ArrayList<>();
////        coordinates.add(lat);
////        coordinates.add(lon);
////
////        //initialize geometry Object
////        Geometry geometry = new Geometry("Point", coordinates);
////
////        //initialize subject Generator Features Object
////        SubjectGeneratorFeatures subjectGeneratorFeatures = new SubjectGeneratorFeatures(0, 153, geometry, subjectGeneratorProperties);
////
////        //initialize Subject Generator Object
////        SubjectGeneratorLocation subjectGeneratorLocation = new SubjectGeneratorLocation(subjectGeneratorFeatures, serialNumber, 1);
////
////        //Post API Subject Generator Location
////        //TODO: Stas: Temporary solution to fix subjectGenerator requests...
////
////        try{
////            if(rulesValues != null)
////                postParamPreparedJson(ConfigurationReader.getInstance().getValue("SUBJECT_GENERATOR_URL"), "location", "NoData", ApiHelper.convertToJson(subjectGeneratorLocation, false).substring(0, ApiHelper.convertToJson(subjectGeneratorLocation, false).length() - 1).replace("\"values\":\"$$$\"", rulesValues), 200, false, true);
////            else
////                postParamPreparedJson(ConfigurationReader.getInstance().getValue("SUBJECT_GENERATOR_URL"), "location", "NoData", ApiHelper.convertToJson(subjectGeneratorLocation, false).substring(0, ApiHelper.convertToJson(subjectGeneratorLocation, false).length() - 1), 200, false, true);
////
////            if (!latestResponseStatus.equals(200)) {
////            //ExtentReport.printInfoToReportAndToConsole("API: subject-generator POST location FAILED. Another try will be performed");
////            if(rulesValues != null)
////                postParamPreparedJson(ConfigurationReader.getInstance().getValue("SUBJECT_GENERATOR_URL"), "location", "NoData", ApiHelper.convertToJson(subjectGeneratorLocation, false).substring(0, ApiHelper.convertToJson(subjectGeneratorLocation, false).length() - 1).replace("\"values\":\"$$$\"", rulesValues), 200, false, true);
////            else
////                postParamPreparedJson(ConfigurationReader.getInstance().getValue("SUBJECT_GENERATOR_URL"), "location", "NoData", ApiHelper.convertToJson(subjectGeneratorLocation, false).substring(0, ApiHelper.convertToJson(subjectGeneratorLocation, false).length() - 1), 200, false, true);
////            if (!latestResponseStatus.equals(200)) {
////                //ExtentReport.printInfoToReportAndToConsole("API: subject-generator POST location failed");
////            } else //ExtentReport.printInfoToReportAndToConsole("API: subject-generator POST location SUCCESS");
////        } else //ExtentReport.printInfoToReportAndToConsole("API: subject-generator POST location SUCCESS");
////        }catch (Exception e){
////            throw new Exception("Fail in sending the request Or the expected status code not equals to actual status code");
////        }
////    }
////
////    public static void createNGTrailWithSG(ArrayList<Location> aggressorCoordinates, String deviceSerialNumber) throws Exception {
////        int createdPoints = 0;
////        //ExtentReport.printInfoToReportAndToConsole("SG: Create NG trail - Start");
////        for(int i =0; i <=aggressorCoordinates.size()-1; i++ ){
////            createLocationWithSubjectGeneratorAPI(DateCalculation.getCurrentUtcDateJsonFormatAddDays(0, 0, 0, 0, 0, 0), "Asia/Jerusalem", 13, 5, 0, "GNSS",
////                    Double.parseDouble(aggressorCoordinates.get(i).getLatitue()), Double.parseDouble(aggressorCoordinates.get(i).getLongitute()), deviceSerialNumber,false, null, null);
////            createdPoints++;
////        }
////        //ExtentReport.printInfoToReportAndToConsole("SG: Create NG trail - Finish. Created Points: " + createdPoints);
////    }
////
////    public static OffenderGeneratorResponse getOffenderGeneratorOResponse() {
////        OffenderGeneratorResponse offenderGeneratorResponse;
////        //Fill Offender response From API
////        offenderGeneratorResponse = new OffenderGeneratorResponse(offenderId, subType, device, transmitter, beacon, subjectIDFromAPI, sybaseIDFromAPI, ofenderStatusFromAPI);
////        return offenderGeneratorResponse;
////    }
////
////    public static void sendRequestForLogin(String user, String pass) throws Exception {
////        String bodyJsonStr = buildRequestTechSubject(user.replaceAll("\"", ""), pass, "111");
////        RestApiFunction.postParamPreparedJson("ambassador", "APIGateway/auth/login", "NoData", bodyJsonStr, 200, false);
////        tokenAuth = latestResponse.get("token").toString();
////    }
////
////    public static void sendCreateTechnician(String name) throws Exception {
////        String bodyJsonStr = buildRequestTechnicianDetails(name);
////        RestApiFunction.postParamPreparedJson("ambassador", "users", "NoData", bodyJsonStr, 200, false);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "########      New Technician Was Created  ######## " + ANSI_RESET);
////    }
////
////    public static void sendCreateTechnicianPassword(String technicianUsername, String password) throws Exception {
////        String bodyJsonStr = buildRequestTechnicianPassword(technicianUsername, password);
////        RestApiFunction.postParamPreparedJson("ambassador", "users", "NoData", bodyJsonStr, 200, false);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "########      New Technician Was Created  ######## " + ANSI_RESET);
////    }
////
////
////    public static void sendRequestForChangeLogLevel(String encodedSerial, String level) throws Exception {
////        String bodyJsonStr = buildRequestSubjectLogLevel(encodedSerial, level);
////        RestApiFunction.postParamPreparedJson("ambassador", "APIGateway/deviceLog/configurationRequest", "NoData", bodyJsonStr, 201, true);
////    }
////
////    public static void getStatusDeviceLog(SoftAssert softAssert, String requestid) throws Exception {
////        String status = null;
////        Thread.sleep(5000);
////        RestApiFunction.getParam("ambassador", "APIGateway/deviceRequests/" + requestid, "NoData", "", 200, true);
////        status = latestResponse.get("state").toString();
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  Status = " + status + ANSI_RESET);
////        softAssert.assertEquals(status.replaceAll("\"", ""), "Succeeded", "Failed :: Change Level Status is not \"Succeeded\"");
////    }
////
////    public static void getDeviceLogFiles(SoftAssert softAssert, String encodedSerial) throws Exception {
////        String requestState = null;
////        String bodyJsonStr = buildRequestDeviceLog(encodedSerial);
////        RestApiFunction.postParamPreparedJson("ambassador", "APIGateway/deviceLog/createDeviceLogFilesMetadataRequest", "NoData", bodyJsonStr, 201, true);
////        requestState = latestResponse.get("requestState").toString();
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  requestState = " + requestState + ANSI_RESET);
////        softAssert.assertEquals(requestState.replaceAll("\"", ""), "Sent", "Failed :: Change Level Status is not \"Succeeded\"");
////    }
////
////    public static void getDeviceLogFileList(SoftAssert softAssert, String encodedSerial) throws Exception {
////        RestApiFunction.getParam("ambassador", "APIGateway/deviceLog/deviceLogFilesMetadata?encodedSerial=" + encodedSerial, "NoData", "", 200, true);
////        String fileIndex = latestResponse.get("data").get(0).get("logsFilesToDownload").get(0).get("fileIndex").toString();
////        String start = latestResponse.get("data").get(0).get("logsFilesToDownload").get(0).get("start").toString();
////        String end = latestResponse.get("data").get(0).get("logsFilesToDownload").get(0).get("end").toString();
////        String size = latestResponse.get("data").get(0).get("logsFilesToDownload").get(0).get("size").toString();
////        String fileName = latestResponse.get("data").get(0).get("logsFilesToDownload").get(0).get("fileName").toString();
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  fileIndex = " + fileIndex.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  start = " + start.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  end = " + end.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  size = " + size.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  fileName = " + fileName.replaceAll("\"", "") + ANSI_RESET);
////
////        softAssert.assertEquals(fileIndex.replaceAll("\"", ""), "0");
////        softAssert.assertTrue(fileName != null && !fileName.equals(""));
////        softAssert.assertTrue(start != null && !start.equals(""));
////        softAssert.assertTrue(end != null && !end.equals(""));
////        softAssert.assertTrue(Integer.parseInt(size.replaceAll("\"", "")) > 0);
////    }
////
////    public static void getDeviceLogContentReq(SoftAssert softAssert, String encodedSerial) throws Exception {
////        String requestState = null;
////        String bodyJsonStr = buildLogContentRequest(encodedSerial);
////        RestApiFunction.postParamPreparedJson("ambassador", "APIGateway/deviceLog/createDeviceLogFileContentRequest", "NoData", bodyJsonStr, 201, true);
////
////        String fileIndex = latestResponse.get("fileIndex").toString();
////        String start = latestResponse.get("start").toString();
////        String end = latestResponse.get("end").toString();
////        String size = latestResponse.get("size").toString();
////        String fileName = latestResponse.get("fileName").toString();
////
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  fileIndex = " + fileIndex.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  start = " + start.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  end = " + end.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  size = " + size.replaceAll("\"", "") + ANSI_RESET);
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  fileName = " + fileName.replaceAll("\"", "") + ANSI_RESET);
////
////        softAssert.assertEquals(fileIndex.replaceAll("\"", ""), "0");
////        softAssert.assertTrue(fileName != null && !fileName.equals(""));
////        softAssert.assertTrue(start != null && !start.equals(""));
////        softAssert.assertTrue(end != null && !end.equals(""));
////        softAssert.assertTrue(Integer.parseInt(size.replaceAll("\"", "")) > 0);
////    }
////
////    public static String buildRequestTechSubject(String user, String password, String uuid) {
////
////        String response = "{\"username\":\"" + user + "\",\"password\": \"" + password + "\",\"UUID\": \"" + uuid + "\"";
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  Build Json for log level = \n" + response + ANSI_RESET);
////        return response.toString();
////    }
////
////
////    public static String buildRequestTechnicianDetails(String name) {
////        String response = "{\"username\": \"" + name + "\",\"firstName\":\"" + name + "\",\"lastName\":\"" + name + "\",\"type\": \"Technician\",\"phoneNumber\": {\"countryCode\": \"IL\",\"lineNumber\": \"779114359\",\"prefix\": \"05742\",\"phone\": \"057778114359\"},\"language\": \"en-US\",\"middleName\": \"55555\",\"hintText\": \"55555\",\"contract\": \"555555\",\"gender\": \"male\"";
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  Build Json for log level = \n" + response + ANSI_RESET);
////        return response.toString();
////    }
////
////
////    public static String buildRequestTechnicianPassword(String technicianUsername, String password) {
////        String response = "{\"username\": \"" + technicianUsername + "\",\"password\": \"" + password + "\" ,\"UUID\": \"111\",\"hintText\": \"ido test\"}";
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  Build Json for log level = \n" + response + ANSI_RESET);
////        return response.toString();
////    }
////
////    public static String buildRequestSubjectLogLevel(String encodedSerial, String ModeLevel) {
////        String body = "{\"encodedSerial\":\"" + encodedSerial + "\",\"requestData\":{\"defaultLocalLogLevel\": \"INFO\",\"module\": [{\"moduleName\": \"_easy_log\",\"localLevel\": \"" + ModeLevel + "\"}]}";
////        //ExtentReport.printInfoToReportAndToConsole(ANSI_GREEN + "####  Build Json for log level = \n" + ModeLevel + ANSI_RESET);
////        return body.toString();
////    }
////
////    public static String buildRequestDeviceLog(String encodedSerial) {
////        String body = "{\"encodedSerial\":\"" + encodedSerial + "\"";
////        return body.toString();
////    }
////
////    public static String buildLogContentRequest(String encodedSerial) {
////        String body = "{\"encodedSerial\":\"" + encodedSerial + "\",\"fileIndex\":\"0\"";
////        return body.toString();
////    }
////
////    //Perform Subject Generator POST retries in case of Offender creation or activation failure or any Exception(504, 502...)
////    public static void
////    performSubjectGeneratorPOSTRetries(JsonNode response, boolean doActivation, int tries, OffenderGeneratorRequest offenderGeneratorRequest ) throws Exception
////    {
////        int countTries=0;
////        int buffer=1;
////        boolean shouldRetry = false;
////        for (int i = 0; i < response.size(); i++){
////            //ExtentReport.printInfoToReportAndToConsole("Start verifying POST response for the " + i + " offender");
////            if(!doActivation){
////                if(response.get(i).get("devices").size()<0 || !response.get(i).get("state").textValue().equals("SubjectCreated"))
////                    shouldRetry = true;
////            }else{
////                if(response.get(i).get("devices").size()<0 || !response.get(i).get("status").textValue().equals("Active")){
////                    shouldRetry = true;
////                }
////            }
////            while(shouldRetry){
////                try
////                {
////                    offenderGeneratorRequest.setAddDelay(true);
////                    offenderGeneratorRequest.setDelayInSec(4+buffer);
////                    //ExtentReport.printInfoToReportAndToConsole("Failed Creating Offenders by Subject Generator. Another try will performed. Try number :" + countTries);
////                    response = postParamPreparedJson(ConfigurationReader.getInstance().getValue("SUBJECT_GENERATOR_URL"), "automation", "NoData", ApiHelper.convertToJson(offenderGeneratorRequest, false).substring(0, ApiHelper.convertToJson(offenderGeneratorRequest, false).length() - 1), 200, false, true);
////
////                    //ExtentReport.printInfoToReportAndToConsole(ApiHelper.beautifyJsonResponse(response));
////
////                    shouldRetry = false;
////                    //buffer++;
////                }catch (Exception exception){
////                    //ExtentReport.printInfoToReportAndToConsole(ANSI_RED + "Fail in sending the request to Subject Generator" + ANSI_RESET);
////                    //ExtentReport.printInfoToReportAndToConsole(String.valueOf(exception));
////                }
////                tries--;
////                countTries++;
////                buffer++;
////                i = -1;
////                if (tries - 1 == 0)
////                {
////                    //ExtentReport.printInfoToReportAndToConsole("End verifying POST response");
////                    throw new Exception("Failed Creating Offender using Subject Generator. Tried: " + MAX_RETRIES + " times");
////                }
////                //Another wait to reduce the load on SG
////                Thread.sleep(10000);
////            }
////        }
////        //ExtentReport.printInfoToReportAndToConsole("End verifying POST response");
////        //ExtentReport.printInfoToReportAndToConsole("Success Creating Offender using Subject Generator.Tried: " + countTries + " times");
////    }
////
////    public static ArrayList<Location> getAllSubjectLocations(String deviceMongoID, int hoursBack, int minutesBack) throws Exception {
////
////        //ExtentReport.printInfoToReportAndToConsole("Get All Subject Locations for the device serial: " + deviceMongoID);
////
////        RestApiFunction.getParam("locationservice", "locations", "?deviceIDs[]=" + deviceMongoID
////                + "&from=" + DateCalculation.getCurrentUtcDateJsonFormatAddDays(0, 0, 0, hoursBack, minutesBack, 0) + "&to=" + DateCalculation.getCurrentUtcDateJsonFormatAddDays(0, 0, 0, 0, 0, 0),"NoData", 200, false).get("metadata").get("count").asInt();
////
////        ArrayList<Location> all_aggressor_location = new ArrayList<>();
////        for(int i=0; i < latestResponse.get("data").size(); i++){
////            all_aggressor_location.add(new Location(latestResponse.get("data").get(i).get("point").get("coordinates").get(1).asText(), latestResponse.get("data").get(i).get("point").get("coordinates").get(0).asText()));
////        }
////        return all_aggressor_location;
////    }
//}
