package com.oop;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "`groups`")
public class Group {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`idgroups`")
    private int id;


    @NotEmpty(message = "Name should not be empty")
    @Size(min =2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    /*@OneToMany (targetEntity=Person.class, cascade = CascadeType.ALL, mappedBy="group", fetch=FetchType.LAZY, orphanRemoval=true)
    private List<Person> composition = new ArrayList<Person>();*/

    @Min(value = 1900, message = "Year should be greater than 1900")
    @Max( value = 2021, message = "Year should be smaller than 2021")
    private int year;

    @Min(value = 1, message = "Rating should be greater than 1")
    private int rating;

    @Min(value = 1, message = "Count of tickets should be greater than 1")
    private int tickets;

    @Min(value = 1, message = "Count of concerts should be greater than 1")
    private int countOfConcerts;

    public Group(){
    }

    public Group(String name, int year, int rating, int tickets,
                 int countOfConcerts) {
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.tickets = tickets;
        this.countOfConcerts = countOfConcerts;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }


    @Column(name = "`count_of_concerts`")
    public int getCountOfConcerts() {
        return countOfConcerts;
    }

    public void setCountOfConcerts(int countOfConcerts) {
        this.countOfConcerts = countOfConcerts;
    }


    @Column(name = "`name`")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "`year`")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @Column(name = "`rating`")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
