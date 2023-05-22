package api;

import helpers.RestAssuredHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static helpers.RestAssuredHelper.TypesOfRequest.*;


public class RequestToApi {

    @Step("Метод GET")
    public static Response methodGET(String basePath) {
        return new RestAssuredHelper(GET)
                .setSpecification()
                .makeRequest(basePath);
    }

    @Step("Метод POST")
    public static Response methodPOST(Object jsonBody, String endpoint) {
        return new RestAssuredHelper(POST)
                .setBody(jsonBody)
                .makeRequest(endpoint);
    }

    @Step("Метод DELETE")
    public static Response methodDELETE(String pathParam, String value, String endpoint) {
        return new RestAssuredHelper(DELETE)
                .setPathParam(pathParam, value)
                .makeRequest(endpoint);
    }

    @Step("Извлечение Response в BookStoreModel")
    public static BookStoreModel extractResponse(Response response) {
        return response.as(BookStoreModel.class);
    }

}
