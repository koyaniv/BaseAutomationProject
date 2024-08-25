package WebTests;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITest    {

@Test
public static void testAPI() throws JSONException {
    Client _client = Client.create();
    WebResource wr = _client.resource("http://restcountries.eu/rest/v1/name/norway");
    WebResource.Builder builder = wr.getRequestBuilder();
    //Adding headers to the request
    builder = builder.header("Accept", "application/json");
    //Fetching Response from API
    ClientResponse cr = builder.method("GET", ClientResponse.class, null);
    String responseBodyString = cr.getEntity(String.class).trim();
    System.out.println("This is the response: " + responseBodyString);
    System.out.println(cr.getStatus());
    //Fetching response in JSON
    JSONArray jsonResponse = new JSONArray(responseBodyString);
    System.out.println(jsonResponse);
    //Fetching value of capital parameter
    String capital = jsonResponse.getJSONObject(0).getString("capital");
    //Asserting that capital of Norway is Oslo
    Assert.assertEquals(capital, "Oslo");
}
}
