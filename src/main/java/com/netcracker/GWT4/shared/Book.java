package com.netcracker.GWT4.shared;

import com.google.gwt.i18n.shared.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

import java.util.Objects;


public class Book implements Comparable<Book> {

    @NotNull
    private Integer id;
    @NotNull
    private String author;
    @NotNull
    private String bookName;
    @NotNull
    private Integer pageCount;
    @NotNull
    private Integer publishingYear;
    @NotNull
    private String addingDate;

    public Book() {
    }

    public Book(Integer id, String author, String bookName, Integer pageCount, Integer publishingYear, Date addingDate) {
        this.id = id;
        this.author = author;
        this.bookName = bookName;
        this.pageCount = pageCount;
        this.publishingYear = publishingYear;
        this.addingDate = DateTimeFormat.getFormat("dd MMM yyyy").format(addingDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(String addingDate) {
        this.addingDate = addingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                pageCount == book.pageCount &&
                publishingYear == book.publishingYear &&
                Objects.equals(author, book.author) &&
                Objects.equals(bookName, book.bookName) &&
                Objects.equals(addingDate, book.addingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, bookName, pageCount, publishingYear, addingDate);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", bookName='" + bookName + '\'' +
                ", pageCount=" + pageCount +
                ", publishingYear=" + publishingYear +
                ", addingDate=" + addingDate +
                '}';
    }


    @Override
    public int compareTo(Book o) {
        return id.compareTo(o.getId());
    }
}
