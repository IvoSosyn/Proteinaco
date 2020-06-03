/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Instance třídy zapíše XML soubor na disk, do místa určeného parametrem
 * "Proteinaco.orderXmlName"
 *
 * @author sosyn
 */
public class WriteXml {

    /**
     * Implementace org.apache.logging.log4j.Logger
     */
    private static final Logger logger = LogManager.getLogger(WriteXml.class);

    TransformerFactory transformerFactory = null;
    Transformer transformer = null;
    DOMSource domSource = null;
    StreamResult console = null;
    StreamResult file = null;

    public WriteXml() {
    }

    /**
     * Metoda zapíše data z DOM modelu do místa určeného parametrem
     * "Proteinaco.orderXmlName"
     */
    void run() {
        try {
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            domSource = new DOMSource(Proteinaco.doc);

            Proteinaco.orderXmlFile = new File(Proteinaco.orderXmlName);

//            console = new StreamResult(System.out);
            file = new StreamResult(Proteinaco.orderXmlFile);

//            transformer.transform(domSource, console);
            transformer.transform(domSource, file);
        } catch (TransformerConfigurationException ex) {
            logger.error(ex);
        } catch (TransformerException ex) {
            logger.error(ex);
        }
        if (file instanceof StreamResult && file.getOutputStream() instanceof OutputStream) {
            try {
                file.getOutputStream().close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
    }
}
