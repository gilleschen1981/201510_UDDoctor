package com.hp.ucmdb.uddoctor;

import com.hp.ucmdb.uddoctor.analyzer.LogAnalyzer;
import com.hp.ucmdb.uddoctor.dataModel.LogMarkerPool;
import com.hp.ucmdb.uddoctor.logparser.LogParser;
import com.hp.ucmdb.uddoctor.xml.ConfigGenerator;
import com.hp.ucmdb.uddoctor.xml.ConfigManager;

import java.io.File;

public class UDDoctor {

    public static void main(String[] args) {
        if(args.length == 0)
        {
            runUDDoctor(args);
        } else if (args.length == 1 || args.length == 2)
        {
            if("-c".equals(args[0]))
            {
                runUDDoctor(args);
            } else if("-g".equals(args[0]) )
            {
                generateConfigFile(args[1]);
            }else
            {
                printUsage();
                System.exit(0);
            }
        } else
        {
            printUsage();
            System.exit(0);
        }





    }

    private static void generateConfigFile(String xmlFile) {
        File directory = new File("");
        ConfigGenerator configGenerator = new ConfigGenerator();
        configGenerator.generateXMLFromExcel(xmlFile);
    }

    private static void runUDDoctor(String[] args) {
        // calculate the target folder
        String targetPath = null;
        if(args.length > 1)
        {
            targetPath = args[1];
        } else
        {
            File directory = new File("");
            try{
                String path = directory.getAbsolutePath();
                targetPath = path.substring(0, path.lastIndexOf("\\")) + "\\..\\runtime\\log\\";
            }catch(Exception e){}
        }

        // confirm target path

        // load config file.
        try {
            ConfigManager config = ConfigManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogMarkerPool logMarkerPool = LogMarkerPool.getInstance();
        logMarkerPool.setSourcePath(targetPath);

        // call log parser
        LogParser parser = new LogParser();
        parser.parseDir(targetPath);


        logMarkerPool.report();


        // call problem analyzer
        LogAnalyzer analyzer = LogAnalyzer.getInstance();
        analyzer.analyze();

    }

    private static void printUsage() {
        System.out.println("Please use the following parameter ");
        System.out.println("To run the log check: -c [log folder]");
        System.out.println("To generate the config file: -g [Xml design file]");
    }
}
