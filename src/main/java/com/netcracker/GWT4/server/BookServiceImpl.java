package com.netcracker.GWT4.server;


import com.google.gwt.thirdparty.guava.common.collect.Ordering;
import com.netcracker.GWT4.shared.Book;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("books")
public class BookServiceImpl {

    @Path("loadBooks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Book> loadBooks(){
        JSONService reader = new JSONService();
        return reader.readingFromJSON();
    }

    @Path("saveBooks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveBooks(List<Book> books){
        JSONService writer = new JSONService();
        writer.writingToJSON(books);
    }

    @Path("sortByID")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Book> sortBooksByID(){
        JSONService rw = new JSONService();
        List<Book> books = rw.readingFromJSON();
        if(Ordering.natural().isOrdered(books)) Collections.sort(books,Collections.reverseOrder());
        else Collections.sort(books);
        rw.writingToJSON(books);
        return books;
    }
}
