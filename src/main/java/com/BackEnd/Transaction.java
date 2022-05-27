package com.BackEnd;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name ="Transaction")
public class Transaction extends PanacheEntity {

    public int idDep;
    public Date borrowdate;
    public Date returndate;
    public boolean isreturned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    public Book book;






}
