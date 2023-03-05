package com.babintseva.audionoties;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class RecordController {

    private final Context context;
    private MediaRecorder mediaRecorder = null;
    public RecordController(Context context) {
        this.context = context;
    }

    public void start() {
        Log.d("!!!", "Start");
        MediaRecorder mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(getAudioPath());

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mediaRecorder.start();
        this.mediaRecorder = mediaRecorder;
    }

    public Boolean isAudioRecording() {
        return mediaRecorder != null;
    }

    private String getAudioPath() {
        String way = context.getFilesDir().getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".wav";
        Log.d("!!!", way);
        return way;
    }

    public Integer getVolume() {
        if (mediaRecorder != null) {
            return mediaRecorder.getMaxAmplitude();
        }
        return 0;
    }

    public void stop() {
        if (mediaRecorder != null) {
            try {
                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                }
            } catch (Exception e) {
            }
            mediaRecorder = null;
        }
    }
}
