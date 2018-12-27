package com.netcracker.GWT4.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.GWT4.shared.Book;



import java.io.*;
import java.net.URL;
import java.util.List;

public class JSONService {

    public JSONService() {

    }

    public List readingFromJSON(){
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = null;
        try {
            URL is = this.getClass().getClassLoader().getResource("/bookStore.json");// for Apache Tomcat
            books = mapper.readValue(is, new TypeReference<List<Book>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void writingToJSON(List<Book> books){
        ObjectMapper mapper = new ObjectMapper();
        //ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        try {
            URL out = this.getClass().getClassLoader().getResource("/bookStore.json");   // for Apache Tomcat
            //writer.writeValue(new File(out.getFile()), books);
            mapper.writeValue(new File(out.getFile()),books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
