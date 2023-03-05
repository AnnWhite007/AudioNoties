package com.babintseva.audionoties;

import android.net.Uri;

public class Note {
    private long id;

    private String name;
    private Uri uriAdress;
    private String isPlaying;

    public Note(long id, String name, String isPlaying, Uri uriAdress) {
        this.id = id;
        this.uriAdress = uriAdress;
        this.name = name;
        this.isPlaying = isPlaying;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String isPlaying() {
        return isPlaying;
    }

    public void setPlaying(String playing) {
        isPlaying = playing;
    }

    public Uri getUriAdress() {
        return uriAdress;
    }

    public void setUriAdress(Uri uriAdress) {
        this.uriAdress = uriAdress;
    }
}
