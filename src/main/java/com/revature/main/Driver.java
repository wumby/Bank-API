package com.revature.main;

import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.controller.HelloWorldController;
import com.revature.controller.ClientController;
import com.revature.utility.ConnectionUtility;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.SQLException;

public class Driver {

    public static void main(String[] args) {

        Javalin app = Javalin.create();


        mapControllers(app, new HelloWorldController(), new ClientController(), new ExceptionController());

        app.start(); // port 8080 by default
    }

    public static void mapControllers(Javalin app, Controller... controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }

}
