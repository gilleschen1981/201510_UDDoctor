package com.hp.ucmdb.uddoctor.util;

import com.hp.ucmdb.uddoctor.dataModel.LogMarkerObject;
import com.hp.ucmdb.uddoctor.dataModel.LogMarkerPool;
import com.hp.ucmdb.uddoctor.xml.ConfigManager;
import com.hp.ucmdb.uddoctor.xml.LogMarker;
import com.hp.ucmdb.uddoctor.xml.Problem;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenyimi on 10/15/2014.
 */
public class UDUtility {
    final static char PARENTHESE_CLOSE = '>';
    final static char PARENTHESE_OPEN = '<';

    public static Date StringToDate(String time) {
        Date date = null;
        Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d");
        Matcher matcher = pattern.matcher(time);

        if (matcher.find())
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public static File createReportFile(Problem problem) {
        String fileName = problem.getId() + "_" + problem.getDesc() + ".txt";
        File file = new File(fileName);
        return file;
    }

    public static void reportLogList(File reportFile, ArrayList<LogMarkerObject> logMarkerList){
        FileWriter fw = null;
        try {
            fw = new FileWriter(reportFile);
            fw.write("********************" + "\n");
            fw.write("* " + reportFile.getName() + "\n");
            fw.write("********************" + "\n");

            for (LogMarkerObject logMarker : logMarkerList) {
                fw.write("Line : " + logMarker.getLine() + "  |  " + "Time : " + logMarker.getTimeStamp().toString() + "\n");
                for(int i = 0; i < logMarker.getLineString().size(); i ++)
                {
                    fw.write("\t" + logMarker.getLineString().get(i) + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static int convertCellToInt(String contents) {
        int iCell = 0;
        if (contents !=null)
        {
            try{
                iCell = Integer.valueOf(contents);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return iCell;
    }

    // process the keyword in xml file and change it to java string.
    // For example
    // "(ConnectionPoolManager.java:507)"  to "\(ConnectionPoolManager\.java:507\)"
    public static String processExcelKeyword(String keyword) {
        StringBuilder builder = new StringBuilder ();
        for(int i = 0; i < keyword.length(); i ++)
        {
            if (isRegexKeyword(keyword.charAt(i))) {
                builder.append("\\");
            }
            builder.append(keyword.charAt(i));
        }

        return builder.toString();
    }

    private static boolean isRegexKeyword(char c) {
        if(c == '(' || c == ')' || c == '.' )
        {
            return true;
        }
        return false;
    }

    public static String processXmlKeyword(String keyword) {
        StringBuilder builder = new StringBuilder ();
        int count = 0;
        boolean skip = false;
        while(count < keyword.length())
        {
            if (keyword.charAt(count) == '$' )
            {
                skip = true;
                count +=2;
                continue;
            } else if(keyword.charAt(count) == PARENTHESE_OPEN )
            {
                if(skip == true)
                {
                    count ++;
                    continue;
                }
            } else if(keyword.charAt(count) == PARENTHESE_CLOSE )
            {
                if(skip == true)
                {
                    if(count < keyword.length()) {
                        count++;
                        skip = false;
                    }
                }
            }

            if(count < keyword.length()) {
                builder.append(keyword.charAt(count));
            }

            count ++;
        }
        return builder.toString();
    }

    public static ArrayList<String> analyzeVariableList(LogMarkerObject log) {
        ArrayList<String> rlt = new ArrayList<String>();
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        ConfigManager config = ConfigManager.getInstance();
        LogMarker logMarker = config.getLogMarkerById(log.getId());

        // get the match string
        Pattern pattern = Pattern.compile(UDUtility.processXmlKeyword(logMarker.getKeyword()));
        String line = log.getLineString().get(log.getLineShiftUp());
        String word = null;
        Matcher matcher = pattern.matcher(line);
        MatchResult result = matcher.toMatchResult();

        if (matcher.find()) {
            word = line.substring(matcher.start(), matcher.end());
        }

        ArrayList<String> keywords = UDUtility.splitXMLKeyword(logMarker.getKeyword());
        for(int i = 0; i < keywords.size(); i++)
        {
            word = word.substring(keywords.get(i).length());
            if (i == keywords.size() - 1)
            {
                rlt.add(word);
            } else {
                String value = word.substring(0, word.indexOf(keywords.get(i + 1)));
                rlt.add(value);
                word = word.substring(word.indexOf(keywords.get(i + 1)));
            }
        }


        return rlt;
    }

    public static String generateEquation(String parameter, ArrayList<String> variables) {
        for(int i = 1; i <= variables.size(); i++)
        {
            String key = "\\$" + String.valueOf(i);
            parameter = parameter.replaceAll(key, variables.get(i - 1));
        }
        return parameter;
    }

    public static Boolean calculateEquation(String equation) {
        Boolean rlt = null;
        ScriptEngineManager manager=new ScriptEngineManager();  //javax.script.ScriptEngine
        ScriptEngine engine=manager.getEngineByName("JavaScript");  //javax.script.ScriptEngineManager
        Object i = null;
        try {
            i = engine.eval(equation);
        } catch (ScriptException e) {  //javax.script.ScriptException
            e.printStackTrace();
            return rlt;
        }
        rlt = (Boolean)(i);
        return rlt;
    }

    public static ArrayList<String> splitXMLKeyword(String strpattern) {
        ArrayList<String> rlt = new ArrayList<String>();
        int mark = 0;
        boolean parsingVariable = false;
        for(int i = 0; i < strpattern.length(); i ++)
        {
            if (strpattern.charAt(i) == '$')
            {
                String key = strpattern.substring(mark, i);
                key = key.replaceAll("\\\\","");
                rlt.add(key);
                parsingVariable = true;
                continue;
            } else if (strpattern.charAt(i) == PARENTHESE_CLOSE && parsingVariable == true)
            {
                parsingVariable = false;
                mark = i + 1;
                continue;
            }

            if (i == strpattern.length() - 1)
            {
                String key = strpattern.substring(mark, i + 1);
                key = key.replaceAll("\\\\","");
                rlt.add(key);
            }
        }
        return rlt;
    }

    public static Double convertStringToDouble(String value) {
        Double rlt = null;
        if (value !=null)
        {
            try{
                rlt = Double.valueOf(value);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return rlt;
    }

    public static Date getTimestampFromLog(String line) {
        Date rlt = null;
        int start = line.indexOf('<');
        int end = line.indexOf('>');
        String time = null;
        if (start >= 0 && end > 0 && end > start)
        {
            time = line.substring(start + 1, end);
        }
        if(time != null) {
            rlt = StringToDate(time);
        }
        return rlt;
    }
}
