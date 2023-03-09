package com.babintseva.audionoties;

import android.net.Uri;

public class Note {
    private long id;
    private String name;
    private Uri uriAddress;
    private boolean isPlaying;

    private String noteDuration;


    public Note(long id, String name, Uri uriAddress, boolean isPlaying, String noteDuration) {
        this.id = id;
        this.name = name;
        this.uriAddress = uriAddress;
        this.isPlaying = isPlaying;
        this.noteDuration = noteDuration;
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

    public boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Uri getUriAddress() {
        return uriAddress;
    }

    public void setUriAddress(Uri uriAddress) {
        this.uriAddress = uriAddress;
    }

    public String getNoteDuration() {
        return noteDuration;
    }

    public void setNoteDuration(String noteDuration) {
        this.noteDuration = noteDuration;
    }
}
