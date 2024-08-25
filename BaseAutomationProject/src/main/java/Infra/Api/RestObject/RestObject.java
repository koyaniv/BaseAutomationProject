package Infra.Api.RestObject;

/**
 * Todo - some description
 *
 * @author - Hanna Salameh
 * @date - 06/10/2019 - 9:44
 */
public class RestObject {
    private String operation ;
    private String method = "" ;
    private String body ;
    private String microservice ;
    private String params = "" ;
    private String token_value = null;
    private boolean send_with_token = false;
    private String intent_value = null;
    private boolean send_with_intent = false;
    private boolean userDelete = false ;
    private String userDeleteValue ;

    public String getUserDeleteValue() {
        return userDeleteValue;
    }

    public void setUserDeleteValue(String userDeleteValue) {
        this.userDeleteValue = userDeleteValue;
    }

    private int statusCode ;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMicroservice() {
        return microservice;
    }

    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getToken_value() {
        return token_value;
    }

    public void setToken_value(String token_value) {
        this.token_value = token_value;
    }

    public boolean isSend_with_token() {
        return send_with_token;
    }

    public boolean isUserDelete() {
        return userDelete;
    }

    public void setUserDelete(boolean userDelete) {
        this.userDelete = userDelete;
    }

    public void setSend_with_token(boolean send_with_token) { this.send_with_token = send_with_token; }

    public String getIntent_value() { return intent_value; }

    public void setIntent_value(String intent_value) { this.intent_value = intent_value; }

    public boolean isSend_with_intent() { return send_with_intent; }

    public void setSend_with_intent(boolean send_with_intent) { this.send_with_intent = send_with_intent; }

    /**
     * add key value to the body
     * @param key
     * @param value
     */
    public void addToBodyWithObjectGgson(String key , String value){
        if(!key.equalsIgnoreCase("Data")) {
            if (body == null) {
                body = "{\n"+""+key+":"+value+"";

                // body = "{\n" + "\"" + key + "\":\"" + value +  "\"" ;
            } else {
                // body = body.substring(0,body.length() - 1);
                body += ",\n";
                body += "\""+key+"\":"+value+"";
                // body+= "\""+key + "\":\"" + value +"\"";
            }
        }

    }
    public void addToBody(String key , String value){
        if(!key.equalsIgnoreCase("Data")) {
            if (body == null) {
                body = "{\n"+"\""+key+"\":"+value+"";

                // body = "{\n" + "\"" + key + "\":\"" + value +  "\"" ;
            } else {
                // body = body.substring(0,body.length() - 1);
                body += ",\n";
                body += "\""+key+"\":"+value+"";
                // body+= "\""+key + "\":\"" + value +"\"";
            }
        }

    }
    public void addObjectToBody(String key , String value){
        if(body == null) {
            body = "{\n"+"\""+key+"\":"+value+"" ;

            // body = "{\n" + "\"" + key + "\":\"" + value +  "\"" ;
        }else{
            // body = body.substring(0,body.length() - 1);
            body+= ",\n";
            body+= "\""+key+"\":"+value+"";
            // body+= "\""+key + "\":\"" + value +"\"";
        }
    }

    /**
     * add the body (with object and lists) to the body
     * @param arraysObjects
     */
    public void addToBodyWithObjectsAndArrays(String arraysObjects){
        if(body == null) {
            body = "{\n"+""+arraysObjects ;
        }else{
            // body = body.substring(0,body.length() - 1);
            body+= ",\n";
            body+= arraysObjects;
        }
    }

    /**
     * if the body not Json Valid end with , delete it ","
     */
    public void checkBody(){
        if(body.charAt(body.length()-1) == ','){
            body = body.substring(0,body.length()-1)+' '+body.substring(body.length());
        }
    }

    /**
     * add the index to the url
     * @param method
     */
    public void addToMethod(String method){
        this.method += "/"+ method ;
    }

    /**
     * and the params to the url
     * @param param
     * @param value
     */
    public void addToParams(String param , String value){
        if(this.params == "" ){
            this.params = "?"+param+"="+value;
        }else{
          //  this.params+=",";
            this.params+="&"+param+"="+value;
        }
    }

}




