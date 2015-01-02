package com.hp.ucmdb.uddoctor.dataModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chenyimi on 10/14/2014.
 */
public class LogMarkerObject {
    int id;
    String logName;
    String fileExtension;  // for example, error.log.1 would have 1 as extention
    int line;
    ArrayList<String> lineString;
    int lineShiftUp;
    int lineShiftDown;
    Date timeStamp;

    public LogMarkerObject(int id, String logName, String fileExtension, int line, ArrayList<String> lineString, int lineShiftUp, int lineShiftDown, Date timeStamp) {
        this.id = id;
        this.logName = logName;
        this.fileExtension = fileExtension;
        this.line = line;
        this.lineString = lineString;
        this.lineShiftUp = lineShiftUp;
        this.lineShiftDown = lineShiftDown;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getLineString() {
        return lineString;
    }

    public String getLogName() {
        return logName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public int getLine() {
        return line;
    }

    public int getLineShiftUp() {
        return lineShiftUp;
    }

    public int getLineShiftDown() {
        return lineShiftDown;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
