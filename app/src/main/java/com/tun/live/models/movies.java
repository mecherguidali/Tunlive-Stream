package com.tun.live.models;

import java.io.Serializable;

public class movies implements Serializable {
   String documentId;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public movies(String description, String source, String thmub, String title, String poster) {
        this.description = description;
        this.source = source;
        this.thmub = thmub;
        this.title = title;
        this.poster = poster;

    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    String source;
    String thmub;
    String title;
    String poster;

    public movies(String source, String thmub, String title, String poster) {
        this.source = source;
        this.thmub = thmub;
        this.title = title;
        this.poster = poster;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThmub() {
        return thmub;
    }

    public void setThmub(String thmub) {
        this.thmub = thmub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
