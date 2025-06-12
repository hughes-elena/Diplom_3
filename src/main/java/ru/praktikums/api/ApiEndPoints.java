package ru.praktikums.api;

public class ApiEndPoints {
    //Создаю константы с URL-эндпоинтами для запросов к серверу
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site"; //основной сайт
    public static final String USER_CREATE_POST = "/api/auth/register"; //ручка для создания пользователя
    public static final String USER_LOGIN_POST = "/api/auth/login"; //ручка для логина пользователя
    public static final String USER_UPDATE_PATCH = "/api/auth/user"; //ручка для обновления данных пользователя

}
