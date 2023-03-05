package com.babintseva.audionoties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private View audioButton;
    private RecordController recordController = new RecordController(this);
    private CountDownTimer countDownTimer = null;
    private int MAX_RECORD_AMPLITUDE = 32768;
    private Boolean stopTimer = false;

    private ListView listView;
    private ArrayList<Note> listOfNoteObjects;
    private NoteAdapter adapter;
    private MediaPlayer mediaPlayer;

    private String workItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.noteList);
        getListOfObjects();
        adapter = new NoteAdapter(this, R.layout.music_item, listOfNoteObjects);
        listView.setAdapter(adapter);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO}, 00);

        audioButton = findViewById(R.id.start_button);
        audioButton.setOnClickListener(view -> {
            mediaPlayer.stop();
            mediaPlayer.reset();
            if (recordController.isAudioRecording()) {
                audioButton.setBackgroundResource(R.drawable.mic);
                recordController.stop();
                stopTimer = true;
                countDownTimer = null;
                getListOfObjects();
                updateAdapter();
            } else {
                audioButton.setBackgroundResource(R.drawable.stop);
                recordController.start();

                CountDownTimer countDownTimer = new CountDownTimer(60_000, 100) {
                    @Override
                    public void onTick(long l) {
                        int volume = recordController.getVolume();
                        Log.d("!!!", "Volume = " + volume);
                        handleVolume(volume);
                        if (stopTimer) {
                            cancel();
                            stopTimer = false;
                        }
                    }

                    @Override
                    public void onFinish() {
                    }
                }.start();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteFile(listOfNoteObjects.get(i).getName());
                getListOfObjects();
                updateAdapter();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = listOfNoteObjects.get(i);
                Log.d("!!!", note.isPlaying());
                if (note.getName().equals(workItem)) {
                    if (note.isPlaying().equals("play")) {
                        Log.d("!!!", "уже играет");
                        note.setPlaying("pause");
                        updateAdapter();
                        Log.d("!!!", note.isPlaying());
                        mediaPlayer.pause();
                    } else {
                        Log.d("!!!", "пауза или конец");
                        note.setPlaying("play");
                        updateAdapter();
                        Log.d("!!!", note.isPlaying());
                        mediaPlayer.start();
                    }
                } else {
                    if (mediaPlayer != null) {
                        Log.d("!!!", "не играет");
                        updateAdapter();
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    try {
                        Log.d("!!!", "все заново");
                        note.setPlaying("play");
                        workItem = note.getName();
                        updateAdapter();
                        mediaPlayer = MediaPlayer.create(MainActivity.this, note.getUriAdress());
                        mediaPlayer.start();

                    } catch (Exception e) {
                        Log.e("Exception", "че то не так");

                    }
                }
            }
        });
    }

    private void updateAdapter() {
//        if (adapter == null) {
        getListOfObjects();
        adapter = new NoteAdapter(this, R.layout.music_item, listOfNoteObjects);
        listView.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }
    }

    private void handleVolume(int volume) {
        double scale = volume / (float) MAX_RECORD_AMPLITUDE + 1.0;
        scale = Math.min(scale, 4.0);
        Log.d("!!!", "Scale = " + scale);
        audioButton.animate()
                .scaleX((float) scale)
                .scaleY((float) scale)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(100);
    }

    private void getListOfObjects() {
        ArrayList<String> nameNotesList = new ArrayList<>();
        listOfNoteObjects = new ArrayList<>();
        String[] list = this.fileList();
        for (int i = list.length - 1; i >= 0; i--) {
            String nameNote = list[i];
            nameNotesList.add(nameNote);
        }
        Collections.sort(nameNotesList, Collections.reverseOrder());
        for (String i : nameNotesList) {
            Uri noteUri = Uri.parse(this.getFilesDir().getAbsolutePath() + "/" + i);
            Note note = new Note(Long.valueOf(i.replace(".wav", "")),
                    i, "stop", noteUri);
            listOfNoteObjects.add(note);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("!!!", "destroy");
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }
    }
}