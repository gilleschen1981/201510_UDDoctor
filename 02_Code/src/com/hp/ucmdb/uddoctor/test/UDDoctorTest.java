package com.hp.ucmdb.uddoctor.test;


import com.hp.ucmdb.uddoctor.analyzer.PIFactory;
import com.hp.ucmdb.uddoctor.analyzer.ProblemIdentifier;
import com.hp.ucmdb.uddoctor.dataModel.LogMarkerObject;
import com.hp.ucmdb.uddoctor.dataModel.LogMarkerPool;
import com.hp.ucmdb.uddoctor.logparser.LogParser;
import com.hp.ucmdb.uddoctor.xml.ConfigManager;
import com.hp.ucmdb.uddoctor.xml.ProblemList;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by chenyimi on 11/11/2014.
 */
public class UDDoctorTest {
    @Before
    public void preParse()
    {
        // clean LogMarker pool
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.clean();

        // load config
        try {
            ConfigManager config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLog7() {
        ConfigManager config = null;
        try {
            config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // parse file
        File directory = new File("");
        String path = directory.getAbsolutePath();
        path = path + "\\TestData\\Problem\\7_ServerResultProcess\\";
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.setSourcePath(path);

        // parse the file
        LogParser parser = new LogParser();
        parser.parseDir(path);

        // compare the pool
        ArrayList<LogMarkerObject> markers = logMarkerPool.getLogmarkerListFromId("7");
        assertEquals(markers.size(), 49);
        for (LogMarkerObject marker : markers) {
            assertEquals(marker.getId(), 7);
            assertEquals(marker.getLogName(), "mam.autodiscovery.log");
            assertEquals(marker.getFileExtension(), "1");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        // analyze the file

        PIFactory piFactory = PIFactory.getInstance();
        ProblemIdentifier identifier = piFactory.createPA("Condition", "$3>1800000");
        if(identifier == null)
        {
            return;
        }
        ArrayList<LogMarkerObject> logMarkerList = logMarkerPool.getLogmarkerListFromId("7");
        if(logMarkerList != null) {
            logMarkerList = identifier.processLog(logMarkerList);
        }
        // compare the result
        assertEquals(logMarkerList.size(), 7);



        markers = logMarkerPool.getLogmarkerListFromId("8");
        assertEquals(markers.size(), 6);
        for (LogMarkerObject marker : markers) {
            assertEquals(marker.getId(), 8);
            assertEquals(marker.getLogName(), "mam.autodiscovery.log");
            assertEquals(marker.getFileExtension(), "1");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }



        markers = logMarkerPool.getLogmarkerListFromId("9");
        assertEquals(markers.size(), 49);
        for (LogMarkerObject marker : markers) {
            assertEquals(marker.getId(), 9);
            assertEquals(marker.getLogName(), "mam.autodiscovery.log");
            assertEquals(marker.getFileExtension(), "1");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        identifier = piFactory.createPA("Condition", "$4>1800000");
        if(identifier == null)
        {
            return;
        }
        logMarkerList = logMarkerPool.getLogmarkerListFromId("9");
        if(logMarkerList != null) {
            logMarkerList = identifier.processLog(logMarkerList);
        }
        // compare the result
        assertEquals(logMarkerList.size(), 48);

    }

    @Test
    public void testLog6()
    {
        ConfigManager config = null;
        try {
            config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // parse file
        File directory = new File("");
        String path = directory.getAbsolutePath();
        path = path + "\\TestData\\Problem\\6_Callhome\\";
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.setSourcePath(path);

        // parse the file
        LogParser parser = new LogParser();
        parser.parseDir(path);

        // compare the pool
        ArrayList<LogMarkerObject> markers = logMarkerPool.getLogmarkerListFromId("6");
        assertEquals(markers.size(), 66);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 6);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        // analyze the file

        PIFactory piFactory = PIFactory.getInstance();
        ProblemIdentifier identifier = piFactory.createPA("DailyAverage", "$1>4000");
        if(identifier == null)
        {
            return;
        }
        ArrayList<LogMarkerObject> logMarkerList = logMarkerPool.getLogmarkerListFromId("5");
        if(logMarkerList != null) {
            logMarkerList = identifier.processLog(logMarkerList);
        }
        // compare the result
        assertEquals(logMarkerList, null);
    }



    @Test
    public void testLog5()
    {
        ConfigManager config = null;
        try {
            config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // parse file
        File directory = new File("");
        String path = directory.getAbsolutePath();
        path = path + "\\TestData\\Problem\\5_resultProcessTimeByserver\\";
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.setSourcePath(path);

        // parse the file
        LogParser parser = new LogParser();
        parser.parseDir(path);

        // compare the pool
        ArrayList<LogMarkerObject> markers = logMarkerPool.getLogmarkerListFromId("3");
        assertEquals(markers.size(), 171);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 3);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        markers = logMarkerPool.getLogmarkerListFromId("4");
        assertEquals(markers.size(), 1);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 4);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        markers = logMarkerPool.getLogmarkerListFromId("5");
        assertEquals(markers.size(), 171);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 5);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        // analyze the file

        PIFactory piFactory = PIFactory.getInstance();
        ProblemIdentifier identifier = piFactory.createPA("Condition", "$1>1800000");
        if(identifier == null)
        {
            return;
        }
        ArrayList<LogMarkerObject> logMarkerList = logMarkerPool.getLogmarkerListFromId("5");
        if(logMarkerList != null) {
            logMarkerList = identifier.processLog(logMarkerList);
        }
        // compare the result
        assertEquals(logMarkerList.size(), 1);
    }




    @Test
    public void testLog3()
    {
        ConfigManager config = null;
        try {
            config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // parse file
        File directory = new File("");
        String path = directory.getAbsolutePath();
        path = path + "\\TestData\\Problem\\3_resultProcess\\";
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.setSourcePath(path);

        // parse the file
        LogParser parser = new LogParser();
        parser.parseDir(path);

        // compare the pool
        ArrayList<LogMarkerObject> markers = logMarkerPool.getLogmarkerListFromId("3");
        assertEquals(markers.size(), 12);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 3);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        markers = logMarkerPool.getLogmarkerListFromId("4");
        assertEquals(markers.size(), 11);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 4);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        markers = logMarkerPool.getLogmarkerListFromId("5");
        assertEquals(markers.size(), 12);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 5);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        // analyze the file

        PIFactory piFactory = PIFactory.getInstance();
        ProblemIdentifier identifier = piFactory.createPA("Condition", "$1>3600000");
        if(identifier == null)
        {
            return;
        }
        ArrayList<LogMarkerObject> logMarkerList = logMarkerPool.getLogmarkerListFromId("3");
        if(logMarkerList != null) {
            logMarkerList = identifier.processLog(logMarkerList);
        }
        // compare the result
        assertEquals(logMarkerList.size(), 2);
    }


    @Test
    public void testLog2()
    {
        ConfigManager config = null;
        try {
            config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // parse file
        File directory = new File("");
        String path = directory.getAbsolutePath();
        path = path + "\\TestData\\Problem\\2_nullPointer\\";
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.setSourcePath(path);

        // parse the file
        LogParser parser = new LogParser();
        parser.parseDir(path);

        // compare the pool
        ArrayList<LogMarkerObject> markers = logMarkerPool.getLogmarkerListFromId("2");
        assertEquals(markers.size(), 7057);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 2);
            assertEquals(marker.getLogName(), "probe-error.log");
            assertEquals(marker.getFileExtension(), "");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }
    }


    @Test
    public void testLog1()
    {
        ConfigManager config = null;
        try {
            config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // parse file
        File directory = new File("");
        String path = directory.getAbsolutePath();
        path = path + "\\TestData\\Problem\\1_getConnection\\";
        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.setSourcePath(path);

        // parse the file
        LogParser parser = new LogParser();
        parser.parseDir(path);

        // compare the pool
        ArrayList<LogMarkerObject> markers = logMarkerPool.getLogmarkerListFromId("1");
        assertEquals(markers.size(), 22);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 1);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "3");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        markers = logMarkerPool.getLogmarkerListFromId("2");
        assertEquals(markers.size(), 3);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 2);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "3");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        markers = logMarkerPool.getLogmarkerListFromId("3");
        assertEquals(markers.size(), 23);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 3);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "3");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        markers = logMarkerPool.getLogmarkerListFromId("5");
        assertEquals(markers.size(), 3);
        for (LogMarkerObject marker: markers)
        {
            assertEquals(marker.getId(), 5);
            assertEquals(marker.getLogName(), "WrapperProbeGw.log");
            assertEquals(marker.getFileExtension(), "3");
            assertTrue(marker.getLine() > 0);
            assertNotNull(marker.getLineString());
            assertNotNull(marker.getTimeStamp());
        }

        // analyze the file
        ProblemList problemList = config.getRoot().getProblemList();

        PIFactory piFactory = PIFactory.getInstance();
        ProblemIdentifier identifier = piFactory.createPA("Occurrence", null);
        if(identifier == null)
        {
            return;
        }
        ArrayList<LogMarkerObject> logMarkerList = logMarkerPool.getLogmarkerListFromId("1");
        if(logMarkerList != null) {
            logMarkerList = identifier.processLog(logMarkerList);
        }
        // compare the result
        assertEquals(logMarkerList.size(), 22);
    }

}


