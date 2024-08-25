package Infra.Api.ConnectJsonBody;

/**
 * Todo - some description
 *
 * @author - Hanna Salameh
 * @date - 06/10/2019 - 9:44
 */
public class ConnectJsonBody {

    public static String objects = "" ;
    public static String keys_values = "" ;
    public static String object_with_name = "" ;
    public static String list_with_name = "" ;
    public static String arrays = "" ;
    public static String all_body = "" ;
    public static String array_with_name = "" ;
    public static String lists = "" ;


    /**
     * build object according to the split character
     * @param dataObject
     * @param firstSplit
     * @param secondSplit
     */
    public static void buildObjectSplitParam(String dataObject , String firstSplit , String secondSplit){
        String[] data_split = dataObject.split(firstSplit);
        String object = null;
        for (int i = 0; i < data_split.length; i++) {
            String[] data_key_value = data_split[i].split(secondSplit);
            String key = data_key_value[0];
            String value = data_key_value[1];
            if (object == null) {
                object = "{\n"+"\""+key.trim()+"\":"+value.trim()+"";
            } else {
                object += ",\n";
                object += "\""+key.trim()+"\":"+value.trim()+"";
            }
        }
        if(objects==""){
            objects += object+"\n}";
        }else{
            objects = objects +","+object+"\n}";

        }
    }
    /**
     *      {
     *        "userID": "5c7bc13bb3ded8439e002864",
     *        "UUID": "1212"
     *      },
     * @param dataObject
     */
    public static void buildObject(String dataObject){
        String[] data_split = dataObject.split(",");
        String object = null;
        for (int i = 0; i < data_split.length; i++) {
            String[] data_key_value = data_split[i].split(":");
            String key = data_key_value[0];
            String value = data_key_value[1];
            if (object == null) {
                object = "{\n"+"\""+key.trim()+"\":"+value.trim()+"";
            } else {
                object += ",\n";
                object += "\""+key.trim()+"\":"+value.trim()+"";
            }
        }
        if(objects==""){
            objects += object+"\n}";
            //    objects += ",";
        }else{
            objects = objects +","+object+"\n}";
            //    objects += ",";

        }

    }

    /**
     *              [
     *                 "Sunday"
     *             ]
     * @param data
     */
    public static void buildList(String data){
        String[] data_split = data.split(",");
        String list = null ;
        for (int  i = 0 ; i < data_split.length ; i ++){

            if(list == null ) {
                list = "["+data_split[i].trim()  ;
            }else{
                list += "," ;
                list += ""+data_split[i].trim() ;
            }
        }
        if (lists == "") {
            lists+=list+"\n]" ;
            //  lists += "," ;

        }else{
            lists = lists+","+list+"\n]";
            //  lists += "," ;
        }

    }

    /**
     *         "summary": "checkin regular",
     *         "start": "2019-07-28T10:13:48.025Z",
     * @param data
     */
    public static void buildKeyValue(String data){
        String[] data_split = data.split(",");
        String key_value = null;
        for (int i = 0; i < data_split.length; i++) {
            String[] data_key_value = data_split[i].split(":");
            String key = data_key_value[0];
            String value = data_key_value[1];
            if (key_value == null) {
                key_value = "\n"+"\""+key.trim()+"\":"+value.trim()+"";
            } else {
                key_value += ",\n";
                key_value += "\""+key.trim()+"\":"+value.trim()+"";
            }
        }
        if(keys_values==""){
            keys_values += key_value+"\n";
        }else{
            keys_values += key_value ;
            keys_values += ", \n" ;
            // keys_values = keys_values +","+key_value+"\n";
        }
    }

    /**
     * build key value by split character
     * @param data
     * @param firstSplit
     * @param secondSplit
     */
    public static void buildKeyValueBySplitCharachter(String data , String firstSplit , String secondSplit){
        String[] data_split = data.split(firstSplit);
        String key_value = null;
        for (int i = 0; i < data_split.length; i++) {
            String[] data_key_value = data_split[i].split(secondSplit);
            String key = data_key_value[0];
            String value = data_key_value[1];
            if (key_value == null) {
                key_value = "\n"+"\""+key.trim()+"\":"+value.trim()+"";
            } else {
                key_value += ",\n";
                key_value += "\""+key.trim()+"\":"+value.trim()+"";
            }
        }
        if(keys_values==""){
            keys_values += key_value+"\n";
        }else{
            keys_values += key_value ;
            keys_values += ", \n" ;
            // keys_values = keys_values +","+key_value+"\n";
        }
    }

    public static void convertAllDataToObject(){
        String object = "";
        object += array_with_name ;
        if(array_with_name != ""){
            object+=",";
        }
        object+=list_with_name;
        if(list_with_name != ""){
            object+=",";
        }
        object+=object_with_name;
        if(object_with_name != ""){
            object+=",";
        }
        object+=keys_values ;

        if(object.charAt(object.length() - 2)  == ','){
            object = object.substring(0,object.length() - 2) + ' ' + object.substring(object.length());
        }

        objects += "{ \n " ;
        objects += object ;
        objects += "}" ;
        //   objects+= "," ;

        object_with_name = "" ;
        keys_values = "" ;
        list_with_name = "" ;
        array_with_name="";


    }


    /**    [
     *        {
     *
     *           "question": "How are you?",
     *           "order": 1,
     *           "type": "YesNo",
     *           "extraInfo": null
     *        }
     *    ]
     *
     *
     */
    public static void connectObjectToArray(){
        if(arrays == ""){
            arrays += "[" ;
            arrays += objects ;
            arrays += "]" ;
        }else{
            arrays += "," ;
            arrays += "[" ;
            arrays += objects ;
            arrays += "]" ;
        }

        objects = "" ;

    }

    /**
     * "questions":[
     *       {
     *
     *           "question": "How are you?",
     *           "order": 1,
     *           "type": "YesNo",
     *           "extraInfo": null
     *       },
     *  ]
     * @param name
     */
    public static void connectArrayToName(String name){

        if(array_with_name == ""){
            array_with_name += "\"" + name + "\" :  \n";
            array_with_name += arrays +"" ;
        }else{
            array_with_name += ",\n";
            array_with_name += "\"" + name + "\" :  \n";
            array_with_name += arrays +"" ;
        }




        arrays = "" ;

    }

    /**
     *    "range": {
     *                 "type": "EndDate",
     *                 "endDate": "2019-09-04T062730.347Z"
     *             },
     * @param name
     */
    public static void connectObjectToName(String name){
        if(object_with_name == ""){
            object_with_name += "\"" + name + "\" :  \n";
            object_with_name+=objects;
        }else{
            object_with_name+=",";
            object_with_name += "\"" + name + "\" :  \n";
            object_with_name+= objects;
        }

//        if(objects.charAt(objects.length() -1 ) != ','){
//            object_with_name += ",";
//
//        }
        objects = "" ;
    }

    /**
     *     "daysOfWeek": [
     *                 "Sunday"
     *             ],
     * @param name
     */
    public static void connectListToName(String name){
        list_with_name += "\"" + name + "\" :  \n";
        list_with_name+=lists;
        //  list_with_name += ",";
        lists = "" ;
    }

    public static void connectArrayWithNameToBody(){
        if(all_body == ""){
            all_body += array_with_name ;

        }else{
            all_body += ","+array_with_name ;

        }
        array_with_name = "" ;
    }
    public static void connectKeyValueToobject_with_name(){
        if(all_body == ""){
            all_body += object_with_name+","+keys_values  ;

        }else{
            all_body += ","+object_with_name+","+keys_values ;
        }

    }
    public static void connectObjectWithNameToBody(){
        if(all_body == ""){
            all_body += object_with_name ;

        }else{
            all_body += ","+object_with_name ;

        }
        object_with_name = "" ;
    }

    /**
     * DELETE ALL DATA FROM THE ALL OBJECTS IN THIS CLASS
     */
    public static void clearAllObjects(){
        objects = "" ;
        keys_values = "" ;
        object_with_name = "" ;
        list_with_name = "" ;
        arrays = "" ;
        all_body = "" ;
        array_with_name = "" ;
        lists = "" ;
    }
}
