package com.netcracker.GWT4.client;



import com.netcracker.GWT4.shared.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface BookService extends RestService {

    @POST
    @Path("api/books/loadBooks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void loadBooks(MethodCallback<List<Book>> callback);

    @POST
    @Path("api/books/saveBooks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void saveBooks(List<Book> books, MethodCallback<Book> callback);

    @POST
    @Path("api/books/sortByID")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void sortBooksByID(MethodCallback<List<Book>> callback);
}
