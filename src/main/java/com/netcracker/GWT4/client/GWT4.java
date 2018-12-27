package com.netcracker.GWT4.client;



import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;
import com.netcracker.GWT4.shared.Book;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;


import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Date;
import java.util.List;


public class GWT4 implements EntryPoint {

    private static BookService service = GWT.create(BookService.class);

    private static ListDataProvider<Book> data = new ListDataProvider<>();

    private static Integer bookIDToDelete;

    public void onModuleLoad() {

        String root= Defaults.getServiceRoot();
        root=root.replace("GWT4/","");
        Defaults.setServiceRoot(root);


        CellTable<Book> table = new CellTable<>();

        SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER,false,true);
        pager.setDisplay(table);
        pager.setPageSize(8);


        final SingleSelectionModel<Book> selectionModel = new SingleSelectionModel<>();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                Integer selectedID = selectionModel.getSelectedObject().getId();
                if (selectedID != null) {
                    bookIDToDelete = selectedID;
                }
            }
        });


        getBooks(data);
        data.addDataDisplay(table);



        TextColumn<Book> idColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getId().toString();
            }
        };

        TextColumn<Book> authorColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getAuthor();
            }
        };

        TextColumn<Book> bookNameColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getBookName();
            }
        };

        TextColumn<Book> pageCountColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getPageCount().toString();
            }
        };

        TextColumn<Book> publishingYearColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                final String s = object.getPublishingYear().toString();
                return s;
            }
        };

        TextColumn<Book> dateColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getAddingDate();
            }
        };




        final DialogBox dialogBox = new DialogBox();
        dialogBox.setAnimationEnabled(true);
        dialogBox.setWidget(createAddDialogWindow(dialogBox, pager));


        table.addColumn(idColumn, "ID");
        table.addColumn(bookNameColumn, "Book Name");
        table.addColumn(authorColumn, "Author");
        table.addColumn(pageCountColumn, "Pages");
        table.addColumn(publishingYearColumn, "Year of publishing");
        table.addColumn(dateColumn, "Date of adding");


        RootPanel.get("pager").add(pager);
        RootPanel.get("mainTable").add(table);

        final Button addBtn = new Button("Add");
        final Button deleteBtn = new Button("Delete");
        final Button sortByIDBtn = new Button("Sort by ID");

        addBtn.addStyleName("mainBtn");
        deleteBtn.addStyleName("mainBtn");
        sortByIDBtn.addStyleName("mainBtn");

        addBtn.addClickHandler(clickEvent -> dialogBox.center());

        deleteBtn.addClickHandler(clickEvent -> deleteBook(bookIDToDelete));

        sortByIDBtn.addClickHandler(clickEvent -> serverSorting());

        RootPanel.get("addBtn").add(addBtn);
        RootPanel.get("deleteBtn").add(deleteBtn);
        RootPanel.get("sortByIDBtn").add(sortByIDBtn);

    }

    private static void getBooks(ListDataProvider data){
        final List[] retBooks = new List[]{};
        service.loadBooks(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                throwable.fillInStackTrace();
                Window.alert("Can not connect to the server");
            }
            @Override
            public void onSuccess(Method method, List<Book> books) {
                retBooks[0] = books;
                data.setList(retBooks[0]);
                System.out.println("Books table initialized successfully");
            }
        });
    }

    private static void saveBooks(ListDataProvider<Book> data){
        service.saveBooks(data.getList(), new MethodCallback<Book>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                throwable.fillInStackTrace();
                Window.alert("Can not connect to the server");
            }

            @Override
            public void onSuccess(Method method, Book book) {
                System.out.println("Saved");
            }
        });
    }

    private static void serverSorting(){
        final List<Book>[] retBooks = new List[]{};
        service.sortBooksByID(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                throwable.fillInStackTrace();
                Window.alert("Can not connect to the server");
            }

            @Override
            public void onSuccess(Method method, List<Book> books) {
                retBooks[0] = books;
                data.setList(retBooks[0]);
                System.out.println("Books have sorted successfully");
            }
        });
    }



    private void deleteBook(Integer ID){
        for (Book book : data.getList()){
            if (book.getId().equals(ID)) data.getList().remove(book);
        }
        saveBooks(data);
    }

    private DecoratorPanel createAddDialogWindow(DialogBox dialogBox, SimplePager pager){
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(0, 0, "Adding Book");
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(
                0, 0, HasHorizontalAlignment.ALIGN_CENTER);


        layout.setHTML(1, 0, "Book ID");
        TextBox bookIDText = new TextBox();
        layout.setWidget(1, 1,bookIDText);

        layout.setHTML(2, 0, "Book name");
        TextBox bookNameText = new TextBox();
        layout.setWidget(2, 1,bookNameText);

        layout.setHTML(3, 0, "Author");
        TextBox authorText = new TextBox();
        layout.setWidget(3, 1,authorText);

        layout.setHTML(4, 0, "Pages amount");
        TextBox pagesText = new TextBox();
        layout.setWidget(4, 1,pagesText);

        layout.setHTML(5, 0, "Year of Publishing");
        TextBox yearText = new TextBox();
        layout.setWidget(5, 1,yearText);

        Button createBtn = new Button("Add book");
        layout.setWidget(6, 0, createBtn);
        cellFormatter.setHorizontalAlignment(
                6, 0, HasHorizontalAlignment.ALIGN_CENTER);

        Button backBtn = new Button("Back");
        layout.setWidget(6, 1, backBtn);
        cellFormatter.setHorizontalAlignment(
                6, 1, HasHorizontalAlignment.ALIGN_CENTER);


        createBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                StringBuilder errorMessage = new StringBuilder();
                try{
                    String id = bookIDText.getText();
                    String author = authorText.getText();
                    String bookName = bookNameText.getText();
                    String pages = pagesText.getText();
                    String year = yearText.getText();


                    // Validation check
                    if (!isInteger(id) && !id.isEmpty()){
                        errorMessage.append("ID value should be integer\n");
                    }
                    if (!isInteger(pages) && !pages.isEmpty()){
                        errorMessage.append("Pages field value should be integer\n");
                    }
                    if (!isInteger(year) && !year.isEmpty()){
                        errorMessage.append("Publishing year value should be integer\n");
                    }

                    Book newBook = new Book(Integer.parseInt(id),author,bookName,Integer.parseInt(pages),
                            Integer.parseInt(year),new Date());

                    bookIDText.setText("");
                    bookNameText.setText("");
                    authorText.setText("");
                    pagesText.setText("");
                    yearText.setText("");
                    pager.lastPage();
                    data.getList().add(newBook);
                    saveBooks(data);
                    dialogBox.hide();
                }
                catch(IllegalArgumentException ex){
                    Window.alert("Some fields are wrong or empty\n" + errorMessage);
                }
            }
        });

        backBtn.addClickHandler(clickEvent -> dialogBox.hide());

        createBtn.addStyleName("subBtn");
        backBtn.addStyleName("subBtn");


        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setWidget(layout);
        return decPanel;

    }

    private boolean isInteger( String input ) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }


}
