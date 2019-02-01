package com.brainacad;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestTest{

    private static final String URL="https://reqres.in/";

    @Test//GET метод StatusCode endpoint="/api/users" "page=2"
    public void checkGetResponseStatusCode() throws IOException {
        String endpoint="/api/users";

        //TODO: Избавится он хедеров в тесте добавив методы с хедерами по умолчанию в класс HttpClientHelper
        /**
        //Создаём переменую headers типа Map
        Map<String, String> headers=new HashMap<>();
        //Добавляем в headers наш заголовок
        headers.put("User-Agent", "My-Test-User-Agent");
        */
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        // HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2", headers);
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }

    @Test//GET метод BodyNotNull endpoint="/api/users" "page=2"
    public void checkGetResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";

        //TODO: Избавится он хедеров в тесте добавив методы с хедерами по умолчанию в класс HttpClientHelper
        /**
        //Создаём переменую headers типа Map
        Map<String, String> headers=new HashMap<>();
        //Добавляем в headers наш заголовок
        headers.put("User-Agent", "My-Test-User-Agent");
        */

        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        // HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2", headers);
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    @Test//POST метод StatusCode endpoint="/api/users" requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}"
    public void checkPostResponseStatusCode() throws IOException {
        String endpoint="/api/users";

        //TODO: Избавится он хедеров в тесте добавив методы с хедерами по умолчанию в класс HttpClientHelper
        /**
        //Создаём переменую headers типа Map
        Map<String, String> headers=new HashMap<>();
        //Добавляем в headers наш заголовок
        headers.put("User-Agent", "My-Test-User-Agent");
        */

        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        // HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody, headers);
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 201", 201, statusCode);
    }

    @Test//POST метод BodyNotNull endpoint="/api/users" requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}"
    public void checkPostResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";

        //TODO: Избавится он хедеров в тесте добавив методы с хедерами по умолчанию в класс HttpClientHelper
        /**
        //Создаём переменую headers типа Map
        Map<String, String> headers=new HashMap<>();
        //Добавляем в headers наш заголовок
        headers.put("User-Agent", "My-Test-User-Agent");
        */

        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        // HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody, headers);
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    //TODO: напишите по тесткейсу на каждый вариант запроса на сайте https://reqres.in
    //TODO: в тескейсах проверьте Result Code и несколько параметров из JSON ответа (если он есть)

    @Test//GET метод SingleUser endpoint="/api/users/2" jsonPath="$.data.first_name"
    public void checkGetResponseSingleUser() throws IOException {
        String endpoint="/api/users/2";

        HttpResponse response = HttpClientHelper.get(URL+endpoint,null);

        //Конвертируем входящий поток тела ответа в строку
        String body = HttpClientHelper.getBodyFromResponse(response);
        String dataByJSONPath = JsonUtils.stringFromJSONByPath(body, "$.data.first_name"); // must return "Janet"
        System.out.println("body = " + body);
        System.out.println("dataByJSONPath = " + dataByJSONPath);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);

        Assert.assertEquals("Must be \"Janet\"", "Janet", dataByJSONPath);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }

    @Test//GET метод SingleUserNotFound endpoint="/api/users/23"
    public void checkGetResponseSingleUserNotFound() throws IOException {
        String endpoint="/api/users/23";

        HttpResponse response = HttpClientHelper.get(URL+endpoint,null);

        //Конвертируем входящий поток тела ответа в строку
        String body = HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);

        Assert.assertEquals("Must be string \"{}\"", "{}", body);
        Assert.assertEquals("Response status code should be 404", 404, statusCode);
    }

    @Test//GET метод ListResource endpoint="/api/unknown" jsonPath="$.data[*].color"
    public void checkGetResponseListResource() throws IOException {
        String endpoint="/api/unknown";

        HttpResponse response = HttpClientHelper.get(URL+endpoint,null);

        //Конвертируем входящий поток тела ответа в строку
        String body = HttpClientHelper.getBodyFromResponse(response);
        List <String> listByJSONPath = JsonUtils.listFromJSONByPath(body, "$.data[*].color");
        String dataByJSONPath = listByJSONPath.toString();

        System.out.println("body = " + body);
        System.out.println("dataByJSONPath = " + dataByJSONPath);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        List<String> expList= new ArrayList<>();
        expList.add("#98B2D1");
        expList.add("#C74375");
        expList.add("#BF1932");

        Assert.assertEquals("Must be List of color strings [" + " \"#98B2D1\"," +" \"#C74375\"," +" \"#BF1932\"" +"]", expList, listByJSONPath);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }

    @Test//GET метод SingleResourse endpoint="/api/unknown/2" jsonPath="$.data.color"
    public void checkGetResponseSingleResourse() throws IOException {
        String endpoint="/api/unknown/2";

        HttpResponse response = HttpClientHelper.get(URL+endpoint,null);

        //Конвертируем входящий поток тела ответа в строку
        String body = HttpClientHelper.getBodyFromResponse(response);
        String dataByJSONPath = JsonUtils.stringFromJSONByPath(body, "$.data.color");

        System.out.println("body = " + body);
        System.out.println("dataByJSONPath = " + dataByJSONPath);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);

        Assert.assertEquals("Must be color strings #C74375", "#C74375", dataByJSONPath);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }
}
