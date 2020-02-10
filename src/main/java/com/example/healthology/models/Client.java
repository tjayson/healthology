package com.example.healthology.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 11, columnDefinition = "int(11) unsigned")
    private Long id;
    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private boolean agreed_to_terms;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Journal> journals;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;

    @OneToOne(mappedBy = "client_id", cascade = CascadeType.ALL)
    private Client_history client_history;

    @OneToOne(mappedBy = "client_id", cascade = CascadeType.ALL)
    private Client_contact client_contact;


    public Client() {}

    public Client(boolean agreed_to_terms, List<Journal> journals, User user) {
        this.agreed_to_terms = agreed_to_terms;
        this.journals = journals;
        this.user_id = user;
    }

    public Client(boolean agreed_to_terms) {
        this.agreed_to_terms = agreed_to_terms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getAgreed_to_terms() {
        return agreed_to_terms;
    }

    public void setAgreed_to_terms(boolean agreed_to_terms) {
        this.agreed_to_terms = agreed_to_terms;
    }

    public User getUser() {
        return user_id;
    }

    public void setUser(User user) {
        this.user_id = user;
    }
}