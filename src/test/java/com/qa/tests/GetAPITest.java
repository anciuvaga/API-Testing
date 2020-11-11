package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.utils.TestUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPITest extends TestBase {

    TestBase testBase;
    String serviceURL;
    String apiURL;
    String url;
    RestClient restClient;
    CloseableHttpResponse httpResponse;

    @BeforeMethod
    public void setUp() {

        testBase = new TestBase();
        serviceURL = properties.getProperty("URL");
        apiURL = properties.getProperty("apiURL");
        //https://reqres.in/api/users
        url = serviceURL + apiURL;
    }

    @Test(priority = 1)
    public void getApiTestWithoutHeader() throws IOException {
        restClient = new RestClient();
        httpResponse = restClient.get(url);

        // Get HTTP status:
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //JSON string
        String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        JSONObject jsonObject = new JSONObject(responseString);
        System.out.println("JSON response: " + jsonObject);

        //Get headers
        Header [] headers = httpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<>();

        for (Header header: headers){
            allHeaders.put(header.getName(), header.getValue());
        }

        System.out.println("Get response headers: " + allHeaders);


    }
    @Test(priority = 2)
    public void getApiTestWithHeaders() throws IOException {
        restClient = new RestClient();
        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type" , "application/json");

        httpResponse = restClient.get(url, headerMap);

        //Get and Assert statusCode is : 200
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not: \"200\"");

        String responseJson = EntityUtils.toString(httpResponse.getEntity());

        JSONObject jsonObject = new JSONObject(responseJson);

        //Single value assertion - "per page", "total"
        String perPageValue  = TestUtils.getValueByJPath(jsonObject,"/per_page");
        System.out.println("Value of per page is: " + perPageValue);
        Assert.assertEquals(perPageValue, "6", "per_page value is not: \"6\"");
        String totalValue = TestUtils.getValueByJPath(jsonObject,"/total");
        Assert.assertEquals(Integer.parseInt(totalValue),12, "total value is not \"12\"");

       // Assert objects from json array
        String id = TestUtils.getValueByJPath(jsonObject, "/data[0]/id");
        String avatar = TestUtils.getValueByJPath(jsonObject, "/data[0]/avatar");
        String firstName = TestUtils.getValueByJPath(jsonObject, "/data[0]/first_name");
        Assert.assertEquals(Integer.parseInt(id), 1, "id is not \"1\"");
        Assert.assertEquals(avatar, "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
        Assert.assertEquals(firstName, "George", "first_name is not \"George\"");
    }
}
