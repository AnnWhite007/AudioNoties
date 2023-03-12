package com.babintseva.audionoties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context context;
    private int layout;
    private ArrayList<Note> listOfNote;
    private LayoutInflater inflater;

//    private double startTime = 0;
//    private double finalTime = 0;

    NoteAdapter(Context context, int resource, ArrayList<Note> listOfNote) {
        super(context, resource, listOfNote);
        this.listOfNote = listOfNote;
        this.context = context;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.playOrPause);
            holder.nameView = convertView.findViewById(R.id.title);
            holder.dateCreation = convertView.findViewById(R.id.dateCreation);
            holder.noteLength = convertView.findViewById(R.id.noteLengthView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Note note = listOfNote.get(position);

        holder.nameView.setText(note.getName());
        holder.dateCreation.setText(readableDate(note.getId()));
        holder.noteLength.setText(note.getNoteDuration());

        if (note.getPlaying()) {
            holder.imageView.setBackgroundResource(R.drawable.pause);
        } else {
            holder.imageView.setBackgroundResource(R.drawable.play);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return listOfNote.size();
    }

    @Nullable
    @Override
    public Note getItem(int position) {
        return listOfNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private String readableDate(long d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy в HH:mm");
        Date date = new Date(d);
        return sdf.format(date);
    }

    static class ViewHolder {
        ImageView imageView;
        TextView nameView, dateCreation, noteLength;

    }
}

