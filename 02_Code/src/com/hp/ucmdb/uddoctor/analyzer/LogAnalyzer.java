package com.hp.ucmdb.uddoctor.analyzer;

import com.hp.ucmdb.uddoctor.dataModel.LogMarkerObject;
import com.hp.ucmdb.uddoctor.dataModel.LogMarkerPool;
import com.hp.ucmdb.uddoctor.util.UDUtility;
import com.hp.ucmdb.uddoctor.xml.ConfigManager;
import com.hp.ucmdb.uddoctor.xml.Problem;
import com.hp.ucmdb.uddoctor.xml.ProblemList;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by chenyimi on 10/15/2014.
 */
public class LogAnalyzer {
    static LogAnalyzer _instance;
    public static LogAnalyzer getInstance(){
        if (_instance == null) {
            synchronized (LogAnalyzer.class) {
                if (_instance == null) {
                    _instance = new LogAnalyzer();
                }
            }
        }
        return _instance;
    }

    public void analyze() {
        ConfigManager configManager = ConfigManager.getInstance();
        ProblemList problemList = configManager.getRoot().getProblemList();

        for (Problem problem: problemList.getProblem())
        {
            analyzeProblem(problem);
        }

    }

    private void analyzeProblem(Problem problem) {
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        PIFactory piFactory = PIFactory.getInstance();
        ProblemIdentifier identifier = piFactory.createPA(problem.getIdentifier(), problem.getParameter());
        if(identifier == null)
        {
            return;
        }
        ArrayList<LogMarkerObject> logMarkerList = logMarkerPool.getLogmarkerListFromId(problem.getLogMarkerid());
        if(logMarkerList != null) {
            logMarkerList = identifier.processLog(logMarkerList);
        }

        if(logMarkerList != null && logMarkerList.size() > 0)
        {
            File reportFile = null;
            reportFile = UDUtility.createReportFile(problem);
            if(reportFile != null)
            {
                UDUtility.reportLogList(reportFile,logMarkerList);
            }
        }

    }
}
