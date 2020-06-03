/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import java.io.IOException;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Třída načte ceníkové(skladové) položky z .XML souboru
 *
 * @author ivo
 */
class ReadXmlItems {

    /**
     * Implementace org.apache.logging.log4j.Logger
     */
    private static final Logger logger = LogManager.getLogger(ReadXmlItems.class);

    public ReadXmlItems() {
    }

    void run() {
        try {
            Proteinaco.doc = Proteinaco.builder.parse(Proteinaco.itemsXmlFile);
        } catch (SAXException | IOException ex) {
            logger.error(ex);
            return;
        }
        Proteinaco.doc.getDocumentElement().normalize();
        // Načíst položky ceníku z XML souboru
//        System.out.println("ItemsXmlFile> Root element: " + Proteinaco.doc.getDocumentElement().getNodeName());

// Uložit položky do HashMap k dalšímu použití
        Proteinaco.item = new HashMap<>();
        NodeList items = Proteinaco.doc.getElementsByTagName("ITEM");
        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);
            String code = getTag(item, "CODE").trim() + Proteinaco.CODE_END;
            Proteinaco.item.put(code, new ProteinacoItem(getTag(item, "CODE"), getTag(item, "NAME"), getTag(item, "VARIANT")));
        }
    }

    private String getTag(Node nodeParent, String tagName) {
        String value = "";
        NodeList nodeList = ((Element) nodeParent).getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            value = node.getTextContent();
//            System.out.println(" tagName=" + tagName + " node.getTextContent()=" + node.getTextContent());
        }
        return value;
    }
}
