package com.datayes.pms.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * System: Ubuntu
 * User: baoan @datayes
 * Date: 1/14/14
 * Time: 2:38 PM
 */

@Entity
@Table(name="READER")
@Proxy(lazy=false)
public class Reader implements Serializable {

    private Long id;

    private String name;

    public Reader() {

    }

    public Reader(String name) {
        this.name = name;
    }

    @Id
    @Column(name="READER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
