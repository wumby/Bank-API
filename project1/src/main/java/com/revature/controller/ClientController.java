package com.revature.controller;

import com.revature.model.Client;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class ClientController implements Controller {

    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    // This lambda will implicitly have "throws Exception" based on the functional interface
    // This is something to be aware of, because you might actually want to handle some exceptions
    private Handler getAllClients = (ctx) -> {
        List<Client> clients = clientService.getAllClients();

        ctx.json(clients);
    };

    private Handler getClientById = (ctx) -> {
        String id = ctx.pathParam("clientId");

        Client client = clientService.getClientById(id);

        ctx.json(client);
    };

    private Handler deleteClientByID = (ctx) -> {
        String id = ctx.pathParam("clientId");

        boolean client = clientService.deleteClientById(id);

        ctx.json(client);
    };

    private Handler addClient = (ctx) -> {
        Client clientToAdd = ctx.bodyAsClass(Client.class);
        Client client = clientService.addClient(clientToAdd);
        ctx.json(client);
    };

    private Handler editClient = (ctx) -> {

        Client clientToEdit = ctx.bodyAsClass(Client.class);

        Client editedClient = clientService.editClient((ctx.pathParam("clientId")),clientToEdit);
        ctx.status(200);
        ctx.json(editedClient);
    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/clients", addClient);
        app.get("/clients", getAllClients);
        app.get("/clients/{clientId}", getClientById);
        app.put("/clients/{clientId}", editClient);
        app.delete("/clients/{clientId}", deleteClientByID);


    }
}
