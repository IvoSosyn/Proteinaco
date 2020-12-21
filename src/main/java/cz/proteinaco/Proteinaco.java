/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

/**
 * Hlavní třída pro konverzi Objednávek .XML do Skladu .XML firmy Protein&CO
 *
 * @author sosyn
 */
public class Proteinaco {

    /**
     * Implementace org.apache.logging.log4j.Logger
     */
    private static final Logger logger = LogManager.getLogger(Proteinaco.class);

    /**
     * Parametry systému
     */
    public static Properties prop = new Properties();
    public static File propFile = null;

    public static DocumentBuilderFactory factory = null;
    public static DocumentBuilder builder = null;
    public static Document doc = null;

    public static String urlOrderOriginalXml = null;
    public static File tempOrderOriginalXmlFile = null;

    public static String urlStockOriginalXml = null;
    public static File tempStockOriginalXmlFile = null;

    public static String codeKeysName = null;
    public static File codeKeysFile = null;
    public static HashMap<String, String[]> codeKeys = null;
    public static final String CODE_END = "#";

    public static String itemsXmlName = null;
    public static File itemsXmlFile = null;
    public static HashMap<String, ProteinacoItem> item = null;

    public static String orderXmlName = null;
    public static File orderXmlFile = null;

    public static String stockXmlName = null;
    public static File stockXmlFile = null;

    ReadProperties readProperties = null;
    ReadKeys readKeys = null;
    ReadXmlItems readItems = null;
    ReadXml readXml = null;
    ProcessXml processXml = null;
    WriteXml writeXml = null;

    public Proteinaco() {
        readProperties = new ReadProperties();
        readKeys = new ReadKeys();
        readItems = new ReadXmlItems();
        readXml = new ReadXml();
        processXml = new ProcessXml();
        writeXml = new WriteXml();
    }

    private void run(String[] args) {

        // Načíst parametry z parametrického souboru
        try {
            readProperties.run(args);
        } catch (Exception ex) {
            logger.error(ex);
            return;
        }

        // Naplnit základní statické proměnné
        try {
            tempOrderOriginalXmlFile = File.createTempFile("PaC", "xml");
            tempOrderOriginalXmlFile.deleteOnExit();
            System.out.println("tempXml =" + tempOrderOriginalXmlFile.getAbsolutePath());
        } catch (IOException ex) {
            logger.error(ex);
            return;
        }
        codeKeysFile = new File(codeKeysName);
        System.out.println("codeKeysFile =" + codeKeysFile.getAbsolutePath());

        itemsXmlFile = new File(itemsXmlName);
        System.out.println("itemsFile =" + itemsXmlFile.getAbsolutePath());

        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            logger.error(ex);
            return;
        }

        readKeys.run();
        readItems.run();
        readXml.run();
        processXml.run();
        writeXml.run();
    }

    public static void main(String[] args) {

        Proteinaco proteinaco = new Proteinaco();
        proteinaco.run(args);
    }

}
