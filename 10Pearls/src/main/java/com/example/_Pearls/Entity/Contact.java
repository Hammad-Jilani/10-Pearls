package com.example._Pearls.Entity;

import jakarta.persistence.*;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;

    @Column(unique = true)
    private String name;
    private String number;
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact() {
    }

    public Contact(Long contactId, String name, String number, String email, User user) {
        this.contactId = contactId;
        this.name = name;
        this.number = number;
        this.email = email;
        this.user = user;
    }
}
