package com.oop;

import javax.persistence.*;

@Entity
@Table(name = "`schedule`")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`concertId`")
    private int id;

    private String time;
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
