package com.mycompany.instafitgram;

import java.sql.Date;

/**
 *
 * @author Manu
 */
public class Attempt {

    private int id;
    private int idUser;
    private int idExercise;
    private Date timeStampStart;
    private Date timeStampEnd;
    private String videoFile;
    private String exercise;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public Date getTimeStampStart() {
        return timeStampStart;
    }

    public void setTimeStampStart(Date timeStampStart) {
        this.timeStampStart = timeStampStart;
    }

    public Date getTimeStampEnd() {
        return timeStampEnd;
    }

    public void setTimeStampEnd(Date timeStampEnd) {
        this.timeStampEnd = timeStampEnd;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    @Override
    public String toString() {
        return ""+id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
