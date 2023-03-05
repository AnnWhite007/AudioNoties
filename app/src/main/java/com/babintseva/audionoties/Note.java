package com.babintseva.audionoties;

import android.net.Uri;

public class Note {
    private long id;
    private String name;
    private Uri uriAddress;
    private String isPlaying;

    public Note(long id, String name, String isPlaying, Uri uriAddress) {
        this.id = id;
        this.uriAddress = uriAddress;
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

    public String getPlaying() {
        return isPlaying;
    }

    public void setPlaying(String playing) {
        isPlaying = playing;
    }

    public Uri getUriAddress() {
        return uriAddress;
    }

    public void setUriAddress(Uri uriAddress) {
        this.uriAddress = uriAddress;
    }
}
