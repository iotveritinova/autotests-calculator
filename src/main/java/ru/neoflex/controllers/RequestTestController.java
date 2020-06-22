package ru.neoflex.controllers;

import io.restassured.http.ContentType;
import ru.neoflex.model.RequestSaveTestimony;
import ru.neoflex.model.RequestSetPrice;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;

public class RequestTestController {

    //Метод для отправки Post запроса на микросервис.

    public static int getRequestCodeSaveTestimony(String uRL, RequestSaveTestimony requestSaveTestimony) {

        return given().
                contentType(ContentType.JSON).
                body(requestSaveTestimony).
                when().
                post(uRL).
                then().
                extract().
                response().
                getStatusCode();
    }

    public static int getRequestCodeGetOldTestimony(String uRL) {

        return get(uRL).
                then().
                extract().
                response().
                getStatusCode();
    }

    public static int getRequestCodeSetPrice(String uRL, RequestSetPrice requestSetPrice) {

        return given().
                contentType(ContentType.JSON).
                body(requestSetPrice).
                when().
                post(uRL).
                then().
                extract().
                response().
                getStatusCode();
    }

}