package org.example.app;

import org.example.service.FoodService;

import java.sql.SQLException;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        FoodService foodService = new FoodService();

        port(8080);

        staticFiles.location("/public");

        post("/food/insert", foodService::insert);

        get("/food/:id", foodService::get);

        get("/food/list/:orderby", foodService::getAll);

        get("/food/update/:id", foodService::getToUpdate);

        post("/food/update/:id", foodService::update);

        get("/food/delete/:id", foodService::delete);
    }
}