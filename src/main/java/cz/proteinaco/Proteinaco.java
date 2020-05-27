/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hlavní třída pro konverzi Objednávek .XML do Skladu .XML firmy Protein&CO
 *
 * @author sosyn
 */
public class Proteinaco {

    /**
     *
     */
    public static Logger logger = LogManager.getLogger(Proteinaco.class);

    public static String urlXml = "https://www.proteinaco.cz/export/orders.xml?patternId=41&hash=5d0a5c7c2f1ef8713f8c8b34ef0c3ecc63ba6f1147a7ac8eb031a470a40e3520";
    public static File tempXml = null;
    public static String codeKeysURI = System.getProperty("user.dir")+"\\codeKeys.csv";
    public static File codeKeysFile = null;
    public static HashMap<String, String[]> codeKeys = null;

    ReadKeys readKeys = null;
    ReadXml readXml = null;
    ProcessXml processXml = null;
    WriteXml writeXml = null;

    public Proteinaco() {
        readKeys = new ReadKeys();
        readXml = new ReadXml();
        processXml = new ProcessXml();
        writeXml = new WriteXml();
    }

    private void run() {
        try {
            tempXml = File.createTempFile("PaC", "xml");
            tempXml.deleteOnExit();
            System.out.println("tempXml =" + tempXml.getAbsolutePath());
        } catch (IOException ex) {
            logger.error(ex);
            return;
        }
        codeKeysFile = new File(codeKeysURI);
        System.out.println("codeKeysFile =" + codeKeysFile.getAbsolutePath());

        readKeys.run();
        readXml.run();
        processXml.run();
        writeXml.run();
    }

    public static void main(String[] args) {
        Proteinaco proteinaco = new Proteinaco();
        proteinaco.run();
    }

}
