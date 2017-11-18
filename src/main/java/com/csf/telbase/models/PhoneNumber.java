package com.csf.telbase.models;

import javax.persistence.*;

@Entity
@Table(name="num")
public class PhoneNumber {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="country")
    private long country;

    @Column(name="network")
    private long network;

    @Column(name="local_number")
    private long localNumber;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }

    public long getNetwork() {
        return network;
    }

    public void setNetwork(long network) {
        this.network = network;
    }

    public long getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(long localNumber) {
        this.localNumber = localNumber;
    }

    public String getWholeNumber(){
        return String.format("+%d-%d-%d",country,network,localNumber);
    }

    public PhoneNumber(){

    }
}
