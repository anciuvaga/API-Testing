package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    public int  RESPONSE_STATUS_CODE_200 = 200;
    public int  RESPONSE_STATUS_CODE_201 = 201;

    public Properties properties;
    public String propertyFilePath = "src/main/resources/configs/config.properties";

    public TestBase() {
        try{
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(propertyFilePath);
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

