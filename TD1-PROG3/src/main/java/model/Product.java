package model;

import java.time.Instant;
public class Product {
    private int id;
    private String name;
    private Instant creationDatetime;

    public Product() {}

    public Product(int id, String name, Instant creationDatetime) {
        this.id = id;
        this.name = name;
        this.creationDatetime = creationDatetime;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Instant getCreationDatetime() { return creationDatetime; }
    public void setCreationDatetime(Instant creationDatetime) {
        this.creationDatetime = creationDatetime;
    }
}