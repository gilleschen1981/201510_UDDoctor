package com.hp.ucmdb.uddoctor.analyzer;

import com.hp.ucmdb.uddoctor.dataModel.LogMarkerObject;
import com.hp.ucmdb.uddoctor.util.UDUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by chenyimi on 11/21/2014.
 */
public class PIDailyAverage implements ProblemIdentifier {
    private String parameter;

    public PIDailyAverage(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public ArrayList<LogMarkerObject> processLog(ArrayList<LogMarkerObject> logList) {
        // calculate the period
        Comparator<LogMarkerObject> comparator = new Comparator<LogMarkerObject>(){
            public int compare(LogMarkerObject s1, LogMarkerObject s2) {
                return (int) (s1.getTimeStamp().getTime() - s2.getTimeStamp().getTime());
            }
        };

        Collections.sort(logList, comparator);
        long period = logList.get(logList.size() - 1).getTimeStamp().getTime() - logList.get(0).getTimeStamp().getTime();
        int average = (int) (logList.size() * 3600000 * 24 / period);
        // calculate the equation
        ArrayList<String> variables = new ArrayList<String>();
        variables.add(String.valueOf(average));
        String equation = UDUtility.generateEquation(parameter, variables);

        boolean rlt = UDUtility.calculateEquation(equation);
        if (rlt)
        {
          return logList;
        }
        return null;
    }
}
