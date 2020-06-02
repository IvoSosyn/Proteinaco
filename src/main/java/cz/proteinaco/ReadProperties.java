/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import static cz.proteinaco.Proteinaco.logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author ivo
 */
public class ReadProperties {

    FileInputStream fis = null;
    FileOutputStream fos = null;
    String propFileName = System.getProperty("user.dir") + "\\proteinaco.config";

    public ReadProperties() {
    }

    void run(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            Proteinaco.propFile = new File(propFileName);

        } else {
            Proteinaco.propFile = new File(args[0]);
        }
        // V případě chyby parametrického souboru zapsat hlášení
        if (Proteinaco.propFile == null || !Proteinaco.propFile.canRead()) {
            System.out.println("Musi byt zadan nazev souboru s plnou cestou, kde jsou popsany parametry aplikace.");
            System.out.println("  např.: ");
            System.out.println("        java -jar Properties.jar \"" + propFileName + "\" ");
            System.out.println("  ");
            System.out.println("  Chybne zadano: " + (Proteinaco.propFile == null ? "<nezadano>" : Proteinaco.propFile.getAbsolutePath()));
            logger.warn("Chyba parametrickeho souboru aplikace " + (Proteinaco.propFile == null ? "<nezadano>" : Proteinaco.propFile.getAbsolutePath()) + " Nemohu načíst parametry");
        }
        // Načtení parametrů ze souboru
        try {
            fis = new FileInputStream(Proteinaco.propFile);
            Proteinaco.prop.load(fis);
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
        // Nastavení proměnných
        Proteinaco.urlOrderOriginalXml = Proteinaco.prop.getProperty("urlOrder", "https://www.proteinaco.cz/export/orders.xml?patternId=41&hash=5d0a5c7c2f1ef8713f8c8b34ef0c3ecc63ba6f1147a7ac8eb031a470a40e3520");
        Proteinaco.codeKeysName = Proteinaco.prop.getProperty("codeKeysCSV", System.getProperty("user.dir") + File.separator + "codeKeys.csv");
        Proteinaco.itemsXmlName = Proteinaco.prop.getProperty("itemsXMLName", System.getProperty("user.dir") + File.separator + "items.xml");
        Proteinaco.orderXmlName = Proteinaco.prop.getProperty("orderXmlName", System.getProperty("user.dir") + File.separator + "proteinaco.xml");

        // Uložení parametrů do souboru
        if (Proteinaco.propFile != null && !Proteinaco.propFile.exists()) {
            Proteinaco.prop.setProperty("urlOrder", Proteinaco.urlOrderOriginalXml);
            Proteinaco.prop.setProperty("codeKeysCSV", Proteinaco.codeKeysName);
            Proteinaco.prop.setProperty("itemsXMLName", Proteinaco.itemsXmlName);
            Proteinaco.prop.setProperty("orderXmlName", Proteinaco.orderXmlName);
            try {
                fos = new FileOutputStream(Proteinaco.propFile);
                Proteinaco.prop.store(fos, "Promenne programu, definujici jeho vlastnosti:");
            } catch (FileNotFoundException ex) {
                logger.error(ex);
            } catch (IOException ex) {
                logger.error(ex);
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
        }

    }
}
