//package Infra.Api.JerseyClient;
//
//
//
//import Infra.Api.RestObject.RestObject;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.github.dockerjava.core.WebTarget;
//import com.sun.jersey.api.client.Client;
//import org.apache.hc.client5.http.classic.methods.HttpGet;
//import org.apache.hc.client5.http.classic.methods.HttpPatch;
//import org.apache.hc.client5.http.classic.methods.HttpPost;
//import org.apache.hc.client5.http.entity.mime.ContentBody;
//import org.apache.hc.client5.http.entity.mime.FileBody;
//import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
//import org.apache.hc.client5.http.entity.mime.StringBody;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
//import org.apache.hc.client5.http.impl.classic.HttpClients;
//import org.apache.hc.core5.http.ContentType;
//import org.apache.hc.core5.http.HttpEntity;
//import org.apache.hc.core5.http.HttpResponse;
//import org.apache.hc.core5.http.io.entity.StringEntity;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.*;
//import java.net.*;
//import java.nio.file.Files;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * Todo - some description
// *
// * @author - Hanna Salameh
// * @date - 06/10/2019 - 9:44
// */
//public class RunQuery {
//
//    private RestObject restObj;
//    private String url;
//    private String all_url;
//    private String allBodyJson;
//    private ResponseClient responesClient;
//    private Response response;
//
//    /**
//     * constructor method that contains all the data and the url
//     *
//     * @param restObj
//     * @param url
//     */
//    public RunQuery(RestObject restObj, String url) {
//        this.restObj = restObj;
//        this.url = url;
//    }
//
//    /**
//     * send Post request to the server and return response
//     *
//     * @return
//     */
//    public ResponseClient runPost() throws Exception {
//        buildUrl();
//        buildAllBody();
//        responesClient = new ResponseClient();
//
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustManager, null);
//        Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
//
//        WebTarget webTarget
//                = client.target(this.all_url);
//        Invocation.Builder invocationBuilder
//                = webTarget.request(MediaType.APPLICATION_JSON);
//        if (restObj.isSend_with_token()) {
//            invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + restObj.getToken_value());
//        }
//        response
//                = invocationBuilder
//                .post(Entity.entity(this.allBodyJson, MediaType.APPLICATION_JSON));
//        responesClient = initResposeObject(response, false);
//
//
//        return responesClient;
//    }
//
//    public void printReport() {
////        //ExtentReport.test.log(LogStatus.PASS, "-------------------------------------------------------------------------------------------------------");
////        //ExtentReport.test.log(LogStatus.PASS, "Operation = [ " + this.restObj.getOperation() + " ] , URL = " + this.all_url);
////        //ExtentReport.test.log(LogStatus.PASS, "BODY = " + this.allBodyJson);
////        //ExtentReport.test.log(LogStatus.PASS, "Server response status code = " + String.valueOf(responesClient.getSatusCode()));
////        //ExtentReport.test.log(LogStatus.PASS, "-------------------------------------------------------------------------------------------------------");
//    }
//
//    /**
//     * send get request to the server and return the response
//     *
//     * @return
//     */
//    public ResponseClient runGet() throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        buildUrl();
//        buildAllBody();
//        responesClient = new ResponseClient();
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustManager, null);
//
//        Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
//
//        // Client client = ClientBuilder.newClient();
//        WebTarget webTarget
//                = client.target(this.all_url);
//        Invocation.Builder invocationBuilder
//                = webTarget.request(MediaType.APPLICATION_JSON);
//        if (restObj.isSend_with_token()) {
//            invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + restObj.getToken_value());
//        }
//        response = invocationBuilder.get();
//        responesClient = initResposeObject(response, false);
//        return responesClient;
//    }
//
//    /**
//     * run Patch request to the server and return thre response
//     *
//     * @return
//     */
//    public ResponseClient runPatch() throws Exception {
//        buildUrl();
//        buildAllBody();
//        responesClient = new ResponseClient();
//        // Using Apache HttpClient
////        SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();
////        sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());
////        SSLContext sslContext = sslContextBuilder.build();
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustManager, null);
//        CloseableHttpClient httpClient  = HttpClients.custom().setSSLContext(sslContext).build();
//
//        // Create an HttpPatch request using Apache HttpClient
//        HttpPatch httpPatch = new HttpPatch(this.all_url);
//
//        // Set the JSON payload for the PATCH request
//        String jsonPayload = this.allBodyJson;
//        HttpEntity entity = new StringEntity(jsonPayload);
//        httpPatch.setEntity(entity);
//        httpPatch.setHeader("Content-Type", "application/json");
//
//        // Execute the PATCH request
//        response  = convertToJakartaResponse(httpClient.execute(httpPatch));
//        responesClient = initResposeObject(response, true);
//        return responesClient;
//
//    }
//
//    /**
//     * run Put request to the server and return the response
//     *
//     * @return
//     */
//    public ResponseClient runPut() throws IOException, KeyManagementException, NoSuchAlgorithmException {
//        buildUrl();
//        buildAllBody();
//
//        responesClient = new ResponseClient();
//
//        //Client client = ClientBuilder.newClient();
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustManager, null);
//
//        Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
//
//        WebTarget webTarget
//                = client.target(this.all_url);
//        Invocation.Builder invocationBuilder
//                = webTarget.request(MediaType.APPLICATION_JSON);
//        if (restObj.isSend_with_token()) {
//            invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + restObj.getToken_value());
//        }
//        response
//                = invocationBuilder
//                .put(Entity.entity(this.allBodyJson, MediaType.APPLICATION_JSON));
//        responesClient = initResposeObject(response, false );
//        return responesClient;
//
//    }
//
//    /**
//     * run Delete request to the server and return the response
//     *
//     * @return
//     */
//    public ResponseClient runDelete() throws IOException, KeyManagementException, NoSuchAlgorithmException {
//        buildUrl();
//        buildAllBody();
//        responesClient = new ResponseClient();
//        //Client client = ClientBuilder.newClient();
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustManager, null);
//
//        Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
//
//        WebTarget webTarget
//                = client.target(this.all_url);
//        Invocation.Builder invocationBuilder
//                = webTarget.request(MediaType.APPLICATION_JSON);
//        if (restObj.isSend_with_token()) {
//            invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + restObj.getToken_value());
//        }
//        if(restObj.isUserDelete()){
//            invocationBuilder.header("user-info","{\"userID\":\""+restObj.getUserDeleteValue()+"\"}");
//
//        }
//        response = invocationBuilder.delete();
//        responesClient = initResposeObject(response, false);
//
//        return responesClient;
//    }
//
//    /**
//     * check if the response from the server is Valid Json
//     *
//     * @param test
//     * @return
//     */
//    public boolean isJSONValid(String test) {
//        try {
//            new JSONObject(test);
//        } catch (JSONException ex) {
//
//            try {
//                new JSONArray(test);
//            } catch (JSONException ex1) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * init the response and put all the info in the response Client object
//     *
//     * @param response
//     * @return
//     */
//    public ResponseClient initResposeObject(Response response, boolean isPatch) throws IOException {
//        int statusCode = response.getStatus();
//        String message;
//
//        if(!isPatch)
//            message = response.readEntity(String.class);
//        else
//            message = response.getEntity().toString();
//
//        if (isJSONValid(message)) {
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode actualObj = mapper.readTree(message);
//            responesClient.setMessageResponseJsonNode(actualObj);
//
//        }
//        responesClient.setMessage(message);
//        responesClient.setSatusCode(statusCode);
//        printReport();
//        return responesClient;
//    }
//
//    /**
//     * build all the url
//     *
//     * @return
//     */
//    public String buildUrl() {
//        if (this.restObj.getMicroservice()==null)
//        {
//
//            this.all_url = this.url + this.restObj.getMethod().replace("/","") + this.restObj.getParams().replaceAll(" ", "%20");
//        }else
//        {
//            this.all_url = this.url + this.restObj.getMicroservice() + this.restObj.getMethod() + this.restObj.getParams().replaceAll(" ", "%20");
//        }
//        return this.all_url;
//    }
//
//    /**
//     * build all the body for PUT POST Patch method
//     *
//     * @return
//     */
//    public String buildAllBody() {
//        //TODO: Need to check why all our post body requeres adding "\n}" in the end and fix the code
//        if(this.restObj.getBody().startsWith("[")) { //in case  the body starts with '[' we shouldn't add "\n}"
//            this.allBodyJson = this.restObj.getBody();
//        } else this.allBodyJson = this.restObj.getBody() + "\n}";
//        return this.allBodyJson;
//    }
//
//    /**
//     * after the run ends send the report.html to the server and save it
//     *
//     * @param success
//     * @param start_time
//     */
//    public static void uploadReportToServer(String success, String start_time, String alltests, String testPass, String testFailure, String coverage, String run) throws Exception {
//        try {
//            long time = System.currentTimeMillis();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//            SimpleDateFormat data_And_time = new SimpleDateFormat("HH:mm:ss");
//            Date date = new Date(System.currentTimeMillis());
//            System.out.println(formatter.format(date));
//            String date_format = formatter.format(date);
//
//            String end_time = data_And_time.format(date);
//
//            String time_string = String.valueOf(time);
//            String date_time = date_format + "_" + time_string;
//            String filePath;
//            String report_name;
//            if (run.equals("WEBUI")) {
//                report_name = date_time + "_ReportWeb.html";
//                filePath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "ReportWeb.html";
//
//            } else if (run.equals("RESTAPI")) {
//                report_name = date_time + "_ReportAPI.html";
//                filePath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "ReportAPI.html";
//
//            } else {
//                report_name = date_time + "_ReportMobile.html";
//                filePath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "ReportMobile.html";
//
//            }
//
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
//            HttpPost httppost = new HttpPost("http://www.onenglishstyles.com/index.html/report_save.php");
//            File file = new File(filePath);
//            MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
//
//            ContentBody contentFile = new FileBody(file);
//            mpEntity.addPart("userfile", contentFile);
//            mpEntity.addPart("key", new StringBody(date_time, ContentType.TEXT_PLAIN));
//            mpEntity.setContentType(ContentType.MULTIPART_FORM_DATA);
//            mpEntity.setBoundary(boundary);
//            HttpEntity entity = mpEntity.build();
//
//            httppost.setEntity(entity);
//            System.out.println("executing request " + httppost.getRequestLine());
//            HttpResponse response = httpClient.execute(httppost);
//            HttpEntity resEntity = response.getEntity();
//
//            if (!(response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")) {
//                // Successfully Uploaded
//                //   main.test.log(LogStatus.INFO, "Report did not upload to server");
//            } else {
//                //    main.test.log(LogStatus.INFO, "Report successfully upload to server");
//            }
//            System.out.println(response.getStatusLine());
//            if (resEntity != null) {
//                System.out.println(EntityUtils.toString(resEntity));
//            }
//            if (resEntity != null) {
//                EntityUtils.consume(resEntity);
//            }
//            httpClient.close();
//            String currentVersion = null;
//
//            try {
//                RestApiFunction.getParam("serviceinfo", "NoData", "NoData", 200, false);
//                currentVersion = RestApiFunction.getSpecificValueFromKeyValueResponse("version");
//
//            } catch (Exception e) {
//                currentVersion = "NULL";
//            }
//            currentVersion = currentVersion.replaceAll("\"", "");
//            insert_data_to_db(report_name, date_format, time_string, "IOS", currentVersion, success, end_time, start_time, alltests, testPass, testFailure, coverage, run);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static String getDataFromOIDOffendersTxt(){
//        String allData = "" ;
//
//        try {
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpGet httpGet = new HttpGet("http://www.onenglishstyles.com/OIDOffenders.txt");
//            MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
//            String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
//
//            mpEntity.setContentType(ContentType.MULTIPART_FORM_DATA);
//            mpEntity.setBoundary(boundary);
//
//            HttpEntity entity = mpEntity.build();
//            HttpResponse response = httpClient.execute(httpGet);
//            HttpEntity resEntity = response.getEntity();
//
//            if (!(response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")) {
//            } else {
//            }
//            System.out.println(response.getStatusLine());
//            if (resEntity != null) {
//                System.out.println(EntityUtils.toString(resEntity));
//                allData += EntityUtils.toString(resEntity) ;
//            }
//            if (resEntity != null) {
//                EntityUtils.consume(resEntity);
//            }
//            httpClient.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return allData ;
//    }
//    public static String sendGETOffenderOID() throws IOException {
//        StringBuffer response = new StringBuffer();
//        URL obj = new URL("http://www.onenglishstyles.com/OIDOffenders.txt");
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("GET");
////        con.setRequestProperty("User-Agent", USER_AGENT);
//        int responseCode = con.getResponseCode();
//        System.out.println("GET Response Code :: " + responseCode);
//        if (responseCode == HttpURLConnection.HTTP_OK) { // success
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    con.getInputStream()));
//            String inputLine;
//
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // print result
//            System.out.println(response.toString());
//        } else {
//            System.out.println("GET request not worked");
//        }
//        return response.toString() ;
//
//    }
//
//    public static void uploadOffenderOIDTxtToServer(String path) throws IOException {
//        try {
//
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost httppost = new HttpPost("http://www.onenglishstyles.com/offenderOid.php");
//            File file = new File(path);
//            MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
//            String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
//            ContentBody contentFile = new FileBody(file);
//
//            mpEntity.addPart("userfile", contentFile);
//            mpEntity.setContentType(ContentType.MULTIPART_FORM_DATA);
//            mpEntity.setBoundary(boundary);
//
//            HttpEntity entity = mpEntity.build();
//            httppost.setEntity(entity);
//            System.out.println("executing request " + httppost.getRequestLine());
//            HttpResponse response = httpClient.execute(httppost);
//            HttpEntity resEntity = response.getEntity();
//
//            if (!(response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")) {
//            } else {
//            }
//            System.out.println(response.getStatusLine());
//            if (resEntity != null) {
//                System.out.println(EntityUtils.toString(resEntity));
//            }
//            if (resEntity != null) {
//                EntityUtils.consume(resEntity);
//            }
//            httpClient.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void uploadScreenShotImageToServer(String path, String date) throws IOException {
//        try {
//
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost httppost = new HttpPost("http://www.onenglishstyles.com/index.html/screen_shot.php");
//            File file = new File(path);
//            MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
//            String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
//            ContentBody contentFile = new FileBody(file);
//
//            mpEntity.addPart("userfile", contentFile);
//            mpEntity.setContentType(ContentType.MULTIPART_FORM_DATA);
//            mpEntity.setBoundary(boundary);
//            mpEntity.addPart("key", new StringBody(date, ContentType.TEXT_PLAIN));
//
//            HttpEntity entity = mpEntity.build();
//            httppost.setEntity(entity);
//            System.out.println("executing request " + httppost.getRequestLine());
//            HttpResponse response = httpClient.execute(httppost);
//            HttpEntity resEntity = response.getEntity();
//
//            if (!(response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")) {
//            } else {
//            }
//            System.out.println(response.getStatusLine());
//            if (resEntity != null) {
//                System.out.println(EntityUtils.toString(resEntity));
//            }
//            if (resEntity != null) {
//                EntityUtils.consume(resEntity);
//            }
//            httpClient.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void upload(String path, String date) throws IOException {
//        String url = "http://www.onenglishstyles.com/index.html/report_save.php";
//        String charset = "UTF-8";
//        String param = "value";
//        File textFile = new File("/path/to/file.txt");
//        File binaryFile = new File(path);
//        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
//        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
//
//        URLConnection connection = new URL(url).openConnection();
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//
//        try (
//                OutputStream output = connection.getOutputStream();
//                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
//        ) {
////            // Send normal param.
////            writer.append("--" + boundary).append(CRLF);
////            writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
////            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
////            writer.append(CRLF).append(param).append(CRLF).flush();
////
////            // Send text file.
////            writer.append("--" + boundary).append(CRLF);
////            writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
////            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
////            writer.append(CRLF).flush();
////            Files.copy(textFile.toPath(), output);
////            output.flush(); // Important before continuing with writer!
////            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
//
//            // Send binary file.
//            writer.append("--" + boundary).append(CRLF);
//            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
//            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
//            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
//            writer.append("key = " + date);
//            writer.append(CRLF).flush();
//            Files.copy(binaryFile.toPath(), output);
//            output.flush(); // Important before continuing with writer!
//            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
//
//            // End of multipart/form-data.
//            writer.append("--" + boundary + "--").append(CRLF).flush();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//// Request is lazily fired whenever you need to obtain information about response.
//        int responseCode = ((HttpURLConnection) connection).getResponseCode();
//        String message = ((HttpURLConnection) connection).getResponseMessage();
//        System.out.println(message);
//        System.out.println(responseCode); // Should be 200
//    }
//
//    /**
//     * insert all the data of the report html to the DataBase
//     *
//     * @param report_html
//     * @param date
//     * @param time_mili
//     * @param platform
//     * @param version
//     * @param success
//     * @param end_time
//     * @param start_time
//     */
//    public static void insert_data_to_db(String report_html, String date, String time_mili, String platform, String version, String success, String end_time, String start_time, String alltests, String testPass, String testFailure, String coverage, String run) {
//        //String Run = ConfigurationReader.getInstance().getValue("RUN");
//
//        String json_body = "{"
//                + "\"report_name\": \"" + report_html + "\","
//                + "\"date\": \"" + date + "\","
//                + "\"time_mili\" : \"" + time_mili + "\","
//                + "\"platform\" : \"" + platform + "\","
//                + "\"run\" : \"" + run + "\","
//                + "\"tests\" : \"" + success + "\","
//                + "\"end_time\" : \"" + end_time + "\","
//                + "\"start_time\" : \"" + start_time + "\","
//                + "\"all_tests\" : \"" + alltests + "\","
//                + "\"tests_pass\" : \"" + testPass + "\","
//                + "\"tests_failure\" : \"" + testFailure + "\","
//                + "\"coverage\" : \"" + coverage + "\","
//                + "\"version\" : \"" + version + "\""
//                + "}";
//        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
//
//        System.out.println(json_body);
//        Client client = ClientBuilder.newClient();
//        WebTarget webTarget
//                = client.target("http://www.onenglishstyles.com/index.html/index.php?act=automation&do=insert_report");
//        Invocation.Builder invocationBuilder
//                = webTarget.request(MediaType.APPLICATION_JSON);
//        Response response
//                = invocationBuilder
//                .post(Entity.entity(json_body, MediaType.APPLICATION_JSON));
//
//        //    main.test.log(LogStatus.INFO, "successfully insert data to DB");
//
//
//    }
//
//    TrustManager[] trustManager = new X509TrustManager[]{new X509TrustManager() {
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            return null;
//        }
//
//        @Override
//        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//
//        }
//    }};
//    private static Response convertToJakartaResponse(HttpResponse apacheResponse) throws Exception {
//        // Get the response status code and entity content
//        int statusCode = apacheResponse.getStatusLine().getStatusCode();
//
//        // Read the response entity content
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(apacheResponse.getEntity().getContent()))) {
//            StringBuilder responseBody = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                responseBody.append(line);
//            }
//
//            // Create a Jakarta Response with status code, entity, and optional headers
//            return Response.status(statusCode)
//                    .entity(responseBody.toString())
//                    .header("Content-Type", "application/json") // You may add more headers as needed
//                    .build();
//        }
//    }
//
//}