package com.hp.ucmdb.uddoctor.analyzer;

import com.hp.ucmdb.uddoctor.dataModel.LogMarkerObject;
import com.hp.ucmdb.uddoctor.util.UDUtility;

import java.util.ArrayList;

/**
 * Created by chenyimi on 10/30/2014.
 */
public class PICondition implements ProblemIdentifier {
    private String parameter;

    public PICondition(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public ArrayList<LogMarkerObject> processLog(ArrayList<LogMarkerObject> logList) {
        ArrayList<LogMarkerObject> result = new ArrayList<LogMarkerObject>();
        for(LogMarkerObject log : logList)
        {
            ArrayList<String> variables = UDUtility.analyzeVariableList(log);
            String equation = UDUtility.generateEquation(parameter, variables);

            boolean rlt = UDUtility.calculateEquation(equation);

            if (rlt)
            {
                result.add(log);
            }
        }

        return result;
    }
}
