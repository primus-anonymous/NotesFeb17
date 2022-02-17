package com.neocaptainnemo.notesfeb17.domain;

import java.util.Date;

public class Note {
    private final String id;
    private final String title;
    private final String content;
    private final Date createdAt;

    public Note(String id, String title, String content, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
