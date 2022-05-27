package com.BackEnd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name ="Book")
public class Book extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int bookId;
    @Column(length = 100)
    public String title;
    public double price;
    public int nbPages;
    @Column(length = 200)
    public String Description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    public List<Transaction> transactions = new ArrayList<>();


}


