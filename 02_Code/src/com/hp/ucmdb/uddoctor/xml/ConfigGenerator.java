package com.hp.ucmdb.uddoctor.xml;

import com.hp.ucmdb.uddoctor.util.UDUtility;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenyimi on 10/27/2014.
 */
public class ConfigGenerator {
    private final String logMarkerSheetName = "LogMarker";
    private final String problemSheetName = "Problem";
    private final String endMark = "end";

    private final int logMarkerRow = 1;
    private final int problemRow = 1;

    private final int logMarkerId = 0;
    private final int logMarkerDesc = 1;
    private final int logMarkerFileName = 2;
    private final int logMarkerKeyword = 3;
    private final int logMarkerLineShiftUp = 4;
    private final int logMarkerLineShiftDown = 5;

    private final int problemId = 0;
    private final int problemDesc = 1;
    private final int problemIdentifier = 2;
    private final int problemParameter = 3;
    private final int problemClass = 4;
    private final int problemLogMarkerId = 5;
    private final int problemSolution = 6;



    public void generateXMLFromExcel(String xmlFile) {
        Workbook wb = null;
        File file = new File(xmlFile);
        try {
            // get wk from file
            wb=Workbook.getWorkbook(file);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(wb==null)
            return ;

        Sheet logSheet = wb.getSheet(logMarkerSheetName);
        Log log = generateLog(logSheet);

        Sheet problemSheet = wb.getSheet(problemSheetName);
        ProblemList problemList = generateProblemList(problemSheet);

        Root root = new Root();
        root.setLog(log);
        root.setProblemList(problemList);

        ConfigManager configManager = ConfigManager.getInstance();
        try {
            configManager.save(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Log generateLog(Sheet logSheet) {
        Log log = new Log();

        int row = logMarkerRow;
        // for the number
        for(;;)
        {
            String number = logSheet.getCell(logMarkerId, row).getContents();
            if(endMark.equals(number))
                break;

            if(!number.isEmpty())
            {
                LogMarker logMarker = generateLogMarker(logSheet, row);
                log.getLogMarker().add(logMarker);
            }

            row++;
        }

        return log;
    }

    private LogMarker generateLogMarker(Sheet logSheet, int row) {
        LogMarker logMarker = new LogMarker();

        try {


            logMarker.setId(UDUtility.convertCellToInt(logSheet.getCell(logMarkerId, row).getContents()));
            logMarker.setDesc(logSheet.getCell(logMarkerDesc, row).getContents());
            logMarker.setFileName(logSheet.getCell(logMarkerFileName, row).getContents());
            logMarker.setKeyword(UDUtility.processExcelKeyword(logSheet.getCell(logMarkerKeyword, row).getContents()));
            logMarker.setLineShiftUp(UDUtility.convertCellToInt(logSheet.getCell(logMarkerLineShiftUp, row).getContents()));
            logMarker.setLineShiftDown(UDUtility.convertCellToInt(logSheet.getCell(logMarkerLineShiftDown, row).getContents()));
        }catch(Exception e) {
            e.printStackTrace();
        }

        return logMarker;
    }


    private ProblemList generateProblemList(Sheet problemSheet) {
        ProblemList problemList = new ProblemList();

        int row = problemRow;
        // for the number
        for(;;)
        {
            String number = problemSheet.getCell(problemId, row).getContents();
            if(endMark.equals(number))
                break;

            if(!number.isEmpty())
            {
                Problem problem= generateProblem(problemSheet, row);
                problemList.getProblem().add(problem);
            }

            row++;
        }

        return problemList;
    }

    private Problem generateProblem(Sheet problemSheet, int row) {
        Problem problem = new Problem();

        try {
            problem.setId(UDUtility.convertCellToInt(problemSheet.getCell(problemId, row).getContents()));
            problem.setDesc(problemSheet.getCell(problemDesc, row).getContents());
            problem.setIdentifier(problemSheet.getCell(problemIdentifier, row).getContents());
            problem.setParameter(problemSheet.getCell(problemParameter, row).getContents());
            problem.setClazz(problemSheet.getCell(problemClass, row).getContents());
            problem.setLogMarkerid(problemSheet.getCell(problemLogMarkerId, row).getContents());
            problem.setSolution(problemSheet.getCell(problemSolution, row).getContents());
        }catch(Exception e) {
            e.printStackTrace();
        }

        return problem;
    }

}
