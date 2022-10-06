package com.solera.forum.models;

import javax.persistence.*;

@Entity
@Table(name="SUB_THREADS")
public class SubThreads {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long mainThreadId; //clave foránea

    @Column(length = 120)
    private String title;

    @Column(length = 50)
    private String author;

    @Column(length = 10)
    private String date;

    @Column(length = 2000)
    private String content;

    public SubThreads() {
    }

    public Long getId() {
        return id;
    }

    public Long getMainThreadId() {
        return mainThreadId;
    }

    public void setMainThreadId(Long mainThreadId) {
        this.mainThreadId = mainThreadId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
