package com.qa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class PostAPITest extends TestBase {

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

      url = serviceURL + apiURL;
    }

    @Test
    public void postAPITest() throws IOException {

        restClient = new RestClient();

        HashMap<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        // jackson API:
        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("andrew", "boss"); // expected users object

        // object to json file
        mapper.writeValue(new File("src/main/java/com/qa/data/users.json"), users);

        // object to json in String
        String userJsonString = mapper.writeValueAsString(users);
        System.out.println(userJsonString);

        httpResponse = restClient.post(url, userJsonString, headerMap);// call the POST API

        // Validate the response from API
        // 1. status code
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);

        // 2. JSON String
        String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        JSONObject responseJSON = new JSONObject(responseString);
        System.out.println("The response from API is: " + responseJSON);

        // json to java
        Users usersResponseObj = mapper.readValue(responseString, Users.class); // actual users object
        Assert.assertTrue(users.getName().equals(usersResponseObj.getName()));

        Assert.assertTrue(users.getJob().equals(usersResponseObj.getJob()));

        System.out.println(usersResponseObj.getCreatedAt());
        System.out.println(usersResponseObj.getId());

    }
}

