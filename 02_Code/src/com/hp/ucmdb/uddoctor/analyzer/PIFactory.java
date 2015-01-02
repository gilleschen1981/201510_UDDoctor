package com.hp.ucmdb.uddoctor.analyzer;

/**
 * Created by chenyimi on 10/25/2014.
 */
public class PIFactory {
    final static private String PA_OCCURRENCE = "Occurrence";
    final static private String PA_CONDITION = "Condition";
    final static private String PA_DAILYAVERAGE = "DailyAverage";

    static PIFactory _instance;
    public static PIFactory getInstance(){
        if (_instance == null) {
            synchronized (PIFactory.class) {
                if (_instance == null) {
                    _instance = new PIFactory();
                }
            }
        }
        return _instance;
    }

    public ProblemIdentifier createPA(String paName, String parameter)
    {
        if (PA_OCCURRENCE.equals(paName))
        {
            return new PIOccurrence();
        }
        else if(PA_CONDITION.equals(paName))
        {
            return new PICondition(parameter);
        }
        else if(PA_DAILYAVERAGE.equals(paName))
        {
            return new PIDailyAverage(parameter);
        }
        else
        {
            return null;
        }
    }
}
