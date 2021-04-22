package com.oop;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "`groups`")
public class Group {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`idgroups`")
    private int id;


    private String name;

    @OneToMany (targetEntity=Person.class, cascade = CascadeType.ALL, mappedBy="group", fetch=FetchType.LAZY, orphanRemoval=true)
    private List<Person> composition = new ArrayList<Person>();

    private int year;

    private int rating;

    private int tickets;

    private int countOfConcerts;


    @OneToMany (targetEntity=Concert.class, cascade = CascadeType.ALL, mappedBy="group", fetch=FetchType.LAZY, orphanRemoval=true)
    private List<Concert> schedule = new ArrayList<Concert>();

    @OneToMany (targetEntity=Track.class, cascade = CascadeType.ALL, mappedBy="group", fetch=FetchType.LAZY, orphanRemoval=true)
    private List<Track> repertoire = new ArrayList<Track>();

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

    public void setCountOfConcert(int countOfConcerts) {
        this.countOfConcerts = countOfConcerts;
    }



    public void getInfo(){
        System.out.println(name + " " + year);
    }

    public void setInfo() {
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



    @Transient
    public List<Person> getComposition() {
        return composition;
    }

    public void setComposition(List<Person> composition) {
        this.composition = composition;

    }

    public void addName(Person person) {
        composition.add(person);
        person.setGroup(this);
    }

    @Column(name = "`rating`")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Transient
    public List<Concert> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Concert> schedule) {
        this.schedule = schedule;
    }

    public void addConcert(Concert concert) {
        schedule.add(concert);
        concert.setGroup(this);
    }

    public void addTrack(Track track) {
        repertoire.add(track);
        track.setGroup(this);
    }
}
