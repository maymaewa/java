package com.oop;

import javax.persistence.*;

@Entity
@Table(name = "`repertoire`")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`trackId`")
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "`idgroups`")
    private Group group;

    public Track() {
    }

    public Track(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
