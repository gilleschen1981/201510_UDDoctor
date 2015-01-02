package com.hp.ucmdb.uddoctor.logparser;

import com.hp.ucmdb.uddoctor.dataModel.LogMarkerPool;
import com.hp.ucmdb.uddoctor.util.UDUtility;
import com.hp.ucmdb.uddoctor.xml.ConfigManager;
import com.hp.ucmdb.uddoctor.xml.LogMarker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenyimi on 10/14/2014.
 */
public class LogParser {
    public void parseDir(String targetPath) {
        File dir = new File(targetPath);
        File[] fs = dir.listFiles();
        for(int i=0; i<fs.length; i++) {
            // parse file
            if (fs[i].isDirectory()) {
                continue;
            }
            parseFile(fs[i]);
        }
    }

    private void parseFile(File file) {
        // get the needed marker
        ConfigManager config = null;

        LogMarkerPool markerPool = LogMarkerPool.getInstance();

        try {
            config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<LogMarker> markers = config.getMarkersbyLogName(file.getName());
        if (markers.size() <= 0) {
            return;
        }

        // process file line by line
        BufferedReader reader = null;
        ArrayList<String> fileCache = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // prepare the pattern
            ArrayList<Pattern> patterns = new ArrayList<Pattern>();
            for (LogMarker marker : markers)
            {
                Pattern pattern = Pattern.compile(UDUtility.processXmlKeyword(marker.getKeyword()));
                patterns.add(pattern);
            }
            Date timestamp = null;

            // generate the file cache


            while ((tempString = reader.readLine()) != null) {
                fileCache.add(tempString);
                // get the time stamp.
                Date lineTime = UDUtility.getTimestampFromLog(tempString);
                if (timestamp == null) {
                    timestamp = lineTime;
                } else
                {
                    if (lineTime != null)
                    {
                        timestamp = lineTime;
                    }
                }

                // match the key word
                for (int i = 0; i < markers.size(); i ++)
                {
                    Matcher matcher = patterns.get(i).matcher(tempString);
                    if (matcher.find())
                    {
                        //add to markerPool when founded
                        ArrayList<String> logToSave = new ArrayList<String>();
                        if (markers.get(i).getLineShiftUp() == 0)
                        {
                            logToSave.add(fileCache.get(fileCache.size()-1));
                        } else {
                            for (int j = fileCache.size() - markers.get(i).getLineShiftUp(); j < fileCache.size(); j++) {
                                if(j >= 0) {
                                    logToSave.add(fileCache.get(j));
                                }
                            }
                        }

                        BufferedReader tempReader = new BufferedReader(reader);
                        for (int j = 0; j < markers.get(i).getLineShiftDown(); j++)
                        {
                            if(j < fileCache.size()) {
                                logToSave.add(fileCache.get(j));
                            }
                        }
                        markerPool.addLogMarker(markers.get(i), file.getName(), logToSave, line, timestamp);
                        fileCache.clear();
                    }
                }


                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }


    }
}
