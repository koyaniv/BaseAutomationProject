//package Infra.Api.JerseyClient;
//
//
//import Infra.Api.RestObject.RestObject;
//import Infra.Reader.ConfigurationReader;
//import com.fasterxml.jackson.databind.JsonNode;
//
//
///**
// * Todo - some description
// *
// * @author - Hanna Salameh
// * @date - 06/10/2019 - 9:44
// */
//public class GeneralRest {
//    private String port ;
//    private String ip ;
//    private String protocol ;
//    private RestObject restObject;
//    private ResponseClient responesClient ;
//    private String url ;
//
//    /**
//     * Constructor function recieve port , protocol , ip of the device.
//     * @param port
//     * @param protocol
//     * @param Ip
//     */
//    public GeneralRest(String port,String protocol ,  String Ip){
//        this.port = port ;
//        this.ip = Ip ;
//        this.protocol = protocol ;
//    }
//
//    /**
//     * setter method that recieve the RestAPiInfraStructure.RestObject that contains all the data about the Rest Api request
//     * @param restObj
//     */
//    public void setRestObj(RestObject restObj){
//        this.restObject = restObj ;
//    }
//
//    /**
//     * make new object from ServerReq that to send the request to the server
//     */
//    public void sendReq() throws Exception {
//        String urlConnected = connectUrlParam();
//        ServerReq Sr = new ServerReq(this.restObject , urlConnected);
//        responesClient =  Sr.run();
//    }
//
//    /**
//     * get the message that the server send it back to the client
//     * @return
//     */
//    public String getResponse(){
//        return this.responesClient.getMessage() ;
//    }
//
//    /**
//     * get the status code that the server send it back to the client
//     * @return
//     */
//    public int getStatusCode(){ return this.responesClient.getSatusCode() ; }
//
//    /**
//     * get all the response by json format from the server
//     * @return
//     */
//    public JsonNode getJsonNodeResponse(){
//        return this.responesClient.getMessageResponseJsonNode() ;
//    }
//
//    /**
//     * build the url that contains the protocol=Http , port , ip and apigateway
//     * @return
//     */
//    public String connectUrlParam(){
//        String apiGateway = ConfigurationReader.getInstance().getValue("APIGATEWAY");
//        if(apiGateway.isEmpty()){
//            if(this.port.isEmpty()) {
//                this.url = this.protocol +"://"+this.ip+"/";
//
//            }else{
//                this.url = this.protocol +"://"+this.ip+":"+this.port+"/";
//
//            }
//
//        }else{
//            if(this.port.isEmpty()) {
//                this.url = this.protocol +"://"+this.ip+"/"+ apiGateway +"/";
//
//            }else{
//                this.url = this.protocol +"://"+this.ip+":"+this.port+"/"+ apiGateway +"/";
//
//            }
//
//        }
//        return this.url ;
//    }
//
//    /**
//     * return all the url ;
//     * @return
//     */
//    public  String getUrl(){
//        String all_url = null ;
//        String apiGateway =ConfigurationReader.getInstance().getValue("APIGATEWAY");
//        if(this.port.isEmpty()){
//            if (this.restObject.getMicroservice()==null)
//            {
//                all_url = this.protocol + "://" + this.ip + this.restObject.getMethod() + this.restObject.getParams();
//
//            }else
//            {
//                all_url = this.protocol + "://" + this.ip + "/" + this.restObject.getMicroservice() + this.restObject.getMethod() + this.restObject.getParams();
//                ;
//            }
//        }else{
//            if (this.restObject.getMicroservice()==null)
//            {
//                all_url = this.protocol +"://"+this.ip+":"+this.port+"/"+ apiGateway + this.restObject.getMethod() + this.restObject.getParams();;
//
//            }else{
//
//                all_url = this.protocol +"://"+this.ip+":"+this.port+"/"+ apiGateway +"/"+this.restObject.getMicroservice() + this.restObject.getMethod() + this.restObject.getParams();;
//                }
//        }
//        return all_url;
//    }
//
//
//}
