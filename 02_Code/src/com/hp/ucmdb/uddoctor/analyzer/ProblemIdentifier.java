package com.hp.ucmdb.uddoctor.analyzer;

import com.hp.ucmdb.uddoctor.dataModel.LogMarkerObject;

import java.util.ArrayList;

/**
 * Created by chenyimi on 10/25/2014.
 */
public interface ProblemIdentifier {
    ArrayList<LogMarkerObject> processLog(ArrayList<LogMarkerObject> logList);
}
