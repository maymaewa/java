package com.oop;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "`composition`")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`personId`")
    private int id;


    @Column(name = "`names`")
    @NotEmpty(message = "Name should not be empty")
    @Size(min =2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @ManyToOne
    @JoinColumn(name = "`idgroups`")
    private Group group;


    public Person(){}

    public Person(String name) {
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
