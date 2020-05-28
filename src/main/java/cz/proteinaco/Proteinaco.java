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
     * Parametry systému
     */
    public static Properties prop = new Properties();
    public static File propFile = null;
    /**
     * Implementace org.apache.logging.log4j.Logger
     */
    public static Logger logger = LogManager.getLogger(Proteinaco.class);

    public static DocumentBuilderFactory factory = null;
    public static DocumentBuilder builder = null;
    public static Document doc = null;

    public static String urlXml = null;
    public static File tempXmlFile = null;

    public static String codeKeysName = null;
    public static File codeKeysFile = null;
    public static HashMap<String, String[]> codeKeys = null;

    public static String productsName = null;
    public static File productsFile = null;
    public static HashMap<String, String[]> products = null;

    public static String orderXmlName = null;
    public static File orderXmlFile = null;

    ReadProperties readProperties = null;
    ReadKeys readKeys = null;
    ReadXml readXml = null;
    ProcessXml processXml = null;
    WriteXml writeXml = null;

    public Proteinaco() {
        readProperties = new ReadProperties();
        readKeys = new ReadKeys();
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
            tempXmlFile = File.createTempFile("PaC", "xml");
            tempXmlFile.deleteOnExit();
            System.out.println("tempXml =" + tempXmlFile.getAbsolutePath());
        } catch (IOException ex) {
            logger.error(ex);
            return;
        }
        codeKeysFile = new File(codeKeysName);
        System.out.println("codeKeysFile =" + codeKeysFile.getAbsolutePath());

        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            logger.error(ex);
            return;
        }

        readKeys.run();
        readXml.run();
        processXml.run();
        writeXml.run();
    }

    public static void main(String[] args) {

        Proteinaco proteinaco = new Proteinaco();
        proteinaco.run(args);
    }

}
