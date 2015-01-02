package com.hp.ucmdb.uddoctor.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyimi on 10/14/2014.
 */
public class ConfigManager {
    static ConfigManager _instance;
    public static ConfigManager getInstance(){
        if (_instance == null) {
            synchronized (ConfigManager.class) {
                if (_instance == null) {
                    _instance = new ConfigManager();
                }
            }
        }
        return _instance;
    }
    private String filePath;
    private Root root;
    /**
     * constructor
     */
    public ConfigManager()
    {
        super();
        File directory = new File("");
        try{
            String path = directory.getAbsolutePath();
            filePath = path + "\\Config.xml";
            load();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public Root getRoot() {
        return root;
    }

    /**
     * @return
     */
    private void load() throws Exception
    {
        JAXBContext jaxbContext = null;
        Unmarshaller unmarshaller = null;

        // get jaxb context
        jaxbContext = JAXBContext.newInstance("com.hp.ucmdb.uddoctor.xml");



        // get jaxb unmarshaller
        unmarshaller = jaxbContext.createUnmarshaller();



        // unmarshal the element
        root = (Root) unmarshaller.unmarshal(new File(filePath));


        return;
    }

    public ArrayList<LogMarker> getMarkersbyLogName(String fileName) {
        ArrayList<LogMarker> markers = new ArrayList<LogMarker>();
        for(LogMarker marker : root.getLog().getLogMarker())
        {
            if (fileName.contains(marker.getFileName()) || "*".equals(marker.getFileName()))
            {
                markers.add(marker);
            }
        }
        return markers;
    }


    public void save(Root root) throws Exception
    {
        JAXBContext jaxbContext = null;
        Marshaller marshaller = null;


        // get jaxb context
        try {
            jaxbContext = JAXBContext.newInstance("com.hp.ucmdb.uddoctor.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        // get jaxb marshaller
        try {
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        // marshal the element
        try {
            marshaller.marshal(root, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public LogMarker getLogMarkerById(int id) {
        List<LogMarker> markers = root.getLog().getLogMarker();
        for(LogMarker log : markers)
        {
            if (log.getId() == id)
            {
                return log;
            }
        }
        return null;
    }
}
