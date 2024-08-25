package Infra.Api.JerseyClient;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Todo - some description
 *
 * @author - Hanna Salameh
 * @date - 06/10/2019 - 9:44
 */
public class ResponseClient {
    private int satusCode ;
    private String message ;
    private JsonNode messageResponseJsonNode ;

    public int getSatusCode() {
        return satusCode;
    }

    public void setSatusCode(int satusCode) {
        this.satusCode = satusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public JsonNode getMessageResponseJsonNode() {
        return messageResponseJsonNode;
    }

    public void setMessageResponseJsonNode(JsonNode messageResponseJsonNode) {
        this.messageResponseJsonNode = messageResponseJsonNode;
    }
}
