package com.oop;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;



@Entity
@Table(name = "`schedule`")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`concertId`")
    private int id;


    @DateTimeFormat(pattern="dd-MMM-yyyy")
    @NotEmpty(message = "Time should not be empty")
    private String time;

    @NotEmpty(message = "City should not be empty")
    @Size(min =2, max = 30, message = "City should be between 2 and 30 characters")
    private String city;


    @ManyToOne
    @JoinColumn(name = "`idgroups`")
    private Group group;


    public Concert(){

    }

    public Concert(String time, String city) {
        this.time = time;
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

/*
@startuml
-id
@enduml
 */