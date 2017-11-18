package com.csf.telbase.models;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="subscriber")
public class Subscriber {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PhoneNumber> phones;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<PhoneNumber> getPhones(){
        return phones;
    }

    public void setPhones(List<PhoneNumber> phones) {
        this.phones=phones;
    }

    public Subscriber() {

    }
}
