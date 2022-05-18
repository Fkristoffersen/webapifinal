package com.webapifinal.webapifinal;

import DataModel.Person;
import DataService.PersonDataService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;


@Path("/person")
public class HelloResource {
    private PersonDataService dataService = new PersonDataService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Person> getAllPersons() {

        return dataService.getAllPersons();
    }


    @POST
    @Path("/{name}")
    @Produces("text/plain")
    public String customWelcome(@PathParam("name") String name) {
        return "welcome " + name + "!";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{persid}")
    public Person getPersonbyid(@PathParam("persid") int persId) {
        return dataService.getonePerson(persId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{persid}")
    public Person updatePerson(@PathParam("persid") int persid,Person person){
        return dataService.updatePerson(person,persid);

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Person AddPerson(Person person){
        return dataService.addPerson(person);

    }

    @DELETE
    @Produces("text/plain")
    @Path("/{persid}")
    public String deletePerson(@PathParam("persid") int persid){
        return dataService.deletedPerson(persid);

    }

}