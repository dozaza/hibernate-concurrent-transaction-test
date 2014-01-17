package com.datayes.pms.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * System: Ubuntu
 * User: baoan @datayes
 * Date: 1/14/14
 * Time: 2:33 PM
 */

@Entity
@Table(name="BOOK")
@Proxy(lazy=false)
public class Book implements Serializable {

    private Long id;

    private Long readerId;

    private String name;

    public Book() {

    }

    public Book(Long readerId, String name) {
        this.readerId = readerId;
        this.name = name;
    }

    @Id
    @Column(name="BOOK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="READER_ID")
    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
