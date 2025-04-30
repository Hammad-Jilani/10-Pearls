package com.example._Pearls.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @Column(unique = true)
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    @ElementCollection
//    @CollectionTable(name = "user_contacts",joinColumns = @JoinColumn(name = "user_id"))
//    private List<Contact> contactPerson;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contactPerson;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @NotBlank(message = "Email cannot be empty") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be empty") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Contact> getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(List<Contact> contactPerson) {
        this.contactPerson = contactPerson;
    }

    public User(Long id, String username, String email, String password, List<Contact> contactPerson) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.contactPerson = contactPerson;
    }
}
