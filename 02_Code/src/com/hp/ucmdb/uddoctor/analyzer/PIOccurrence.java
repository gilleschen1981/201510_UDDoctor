package com.hp.ucmdb.uddoctor.analyzer;

import com.hp.ucmdb.uddoctor.dataModel.LogMarkerObject;

import java.util.ArrayList;

/**
 * Created by chenyimi on 10/25/2014.
 */
public class PIOccurrence implements ProblemIdentifier {
    @Override
    public ArrayList<LogMarkerObject> processLog(ArrayList<LogMarkerObject> logList) {
        return logList;
    }
}
