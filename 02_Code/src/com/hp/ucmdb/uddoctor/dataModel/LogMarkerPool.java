package com.hp.ucmdb.uddoctor.dataModel;

import com.hp.ucmdb.uddoctor.util.UDUtility;
import com.hp.ucmdb.uddoctor.xml.LogMarker;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by chenyimi on 10/14/2014.
 */
public class LogMarkerPool {
    HashMap<Integer, ArrayList<LogMarkerObject>> logMarkers; // logfile name  -> logmarkerObject

    public HashMap<Integer, ArrayList<LogMarkerObject>> getLogMarkers() {
        return logMarkers;
    }

    private String sourcePath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    static LogMarkerPool _instance;
    public static LogMarkerPool getInstance(){
        if (_instance == null) {
            synchronized (LogMarkerPool.class) {
                if (_instance == null) {
                    _instance = new LogMarkerPool();
                }
            }
        }
        return _instance;
    }

    public LogMarkerPool() {
        logMarkers = new HashMap<Integer, ArrayList<LogMarkerObject>>();
    }

    public void addLogMarker(LogMarker marker, String fileName, ArrayList<String> line, int lineNumber, Date timestamp) {
        // validate the data
        if (fileName == null || line == null || line.size() == 0 || timestamp == null)
            return;

        Integer key = marker.getId();
        ArrayList<LogMarkerObject> value = logMarkers.get(key);
        if (value == null) {
            value = new ArrayList<LogMarkerObject>();
            logMarkers.put(key, value);
        }

        String mainFileName;
        String extentionName;
        extentionName = fileName.substring(fileName.lastIndexOf('.') + 1);

        if("log".equals(extentionName))
        {
            mainFileName = fileName;
            extentionName = "";
        } else
        {
            mainFileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }

        LogMarkerObject object = new LogMarkerObject(marker.getId(), mainFileName, extentionName, lineNumber, line, marker.getLineShiftUp(), marker.getLineShiftDown(), timestamp);
        value.add(object);
    }

    public void report(){
        for (Object object : logMarkers.keySet()) {
            Integer key = (Integer)object;
            ArrayList<LogMarkerObject> objects = logMarkers.get(key);
            if (objects == null) {
                continue;
            }
            File reportFile = new File("logReport_" + key + ".txt");
            UDUtility.reportLogList(reportFile, objects);
        }

    }

    public ArrayList<LogMarkerObject> getLogmarkerListFromId(String id) {
        int key = 0;
        if (id !=null)
        {
            try{
                key = Integer.valueOf(id);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return logMarkers.get( new Integer(key));
    }

    public void clean() {
        logMarkers = new HashMap<Integer, ArrayList<LogMarkerObject>>();
    }
}
