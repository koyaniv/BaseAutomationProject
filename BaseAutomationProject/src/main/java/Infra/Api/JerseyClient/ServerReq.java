//package Infra.Api.JerseyClient;
//
//
//import Infra.Api.RestObject.RestObject;
//
///**
// * Todo - some description
// *
// * @author - Hanna Salameh
// * @date - 06/10/2019 - 9:44
// */
//public class ServerReq {
//    private RunQuery runQuery ;
//    private RestObject restObject ;
//    private String url ;
//    private ResponseClient responesClient ;
//    /**
//     * constructor method that recieve the RestAPiInfraStructure.RestObject that contains all the data and the url
//     * @param restObject
//     * @param url
//     */
//    public ServerReq(RestObject restObject , String url) {
//        this.restObject = restObject ;
//        this.url = url;
//    }
//
//    /**
//     * make new object of RunQuery that contains all the data of the request and all the url , and run the suitable httpmethod
//     * @return
//     */
//    public ResponseClient run() throws Exception {
//        runQuery = new RunQuery(this.restObject,this.url);
//        if(this.restObject.getOperation().equalsIgnoreCase("POST")){
//            responesClient =  runQuery.runPost();
//        }else if(this.restObject.getOperation().equalsIgnoreCase("GET")){
//            responesClient = runQuery.runGet();
//        }
//        else if(this.restObject.getOperation().equalsIgnoreCase("PUT")){
//            responesClient = runQuery.runPut();
//
//        }
//        else if(this.restObject.getOperation().equalsIgnoreCase("DELETE")){
//            responesClient = runQuery.runDelete();
//
//        }
//        else if(this.restObject.getOperation().equalsIgnoreCase("PATCH")){
//            responesClient = runQuery.runPatch();
//
//        }
//        return responesClient ;
//    }
//}
