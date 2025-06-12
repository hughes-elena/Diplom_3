package ru.praktikums.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserApiSteps {

    static {
        RestAssured.baseURI = ApiEndPoints.BASE_URL;
    }
    private io.restassured.specification.RequestSpecification requestSpecification() {
        return given()
                .contentType(ContentType.JSON);
    }

    @Step("Создать нового пользователя")
    public ValidatableResponse createUser(UserCreateAndEditRequest userCreateAndEditRequest) {
        return requestSpecification()
                .body(userCreateAndEditRequest)
                .post(ApiEndPoints.USER_CREATE_POST)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(UserLoginRequest userLoginRequest) {
        return requestSpecification()
                .body(userLoginRequest)
                .post(ApiEndPoints.USER_LOGIN_POST)
                .then();
    }

    @Step("Получение accessToken пользователя")
    public String getAccessToken(UserLoginRequest userLoginRequest) {
        String token = loginUser(userLoginRequest)
                .extract()
                .path("accessToken");
        if (token == null || token.isEmpty()) {
            return null;  // или выбросить исключение, если нужно
        }
        return token;
    }


    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse userEdit(UserCreateAndEditRequest userCreateAndEditRequest) {
        return requestSpecification()
                .body(userCreateAndEditRequest)
                .patch(ApiEndPoints.USER_UPDATE_PATCH)
                .then();
    }

    @Step("Изменение данных пользователя после авторизации")
    public ValidatableResponse userEditAfterLogin(UserLoginRequest userLoginRequest, UserCreateAndEditRequest userCreateAndEditRequest) {
        String accessToken = getAccessToken(userLoginRequest);

        return requestSpecification()
                .header("Authorization", accessToken)
                .body(userCreateAndEditRequest)
                .patch(ApiEndPoints.USER_UPDATE_PATCH)
                .then();
    }

    @Step("Удаление пользователя по accessToken")
    public ValidatableResponse userDelete(String accessToken) {
        return requestSpecification()
                .header("Authorization", accessToken)
                .delete(ApiEndPoints.USER_UPDATE_PATCH) // используется PATCH-эндпоинт по договорённости
                .then();
    }

    @Step("Удаление пользователя после авторизации")
    public ValidatableResponse userDeleteAfterLogin(UserLoginRequest userLoginRequest) {
        String accessToken = getAccessToken(userLoginRequest);
        if (accessToken == null || accessToken.isEmpty()) {
            System.out.println("Access token is null or empty, user deletion skipped.");
            return null; // Пропускаем удаление, чтобы не падать
        }
        return userDelete(accessToken);
    }
}
