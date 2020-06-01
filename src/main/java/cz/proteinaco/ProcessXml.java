/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import static cz.proteinaco.Proteinaco.logger;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.SAXException;

/**
 * Třída provede zpracování .XML souboru a naplní HashMap novými údaji ke
 * zpracování
 *
 * @author sosyn
 */
public class ProcessXml {

    ArrayList<Node> ordersNew = null;
    ArrayList<Node> itemsNew = null;

    public ProcessXml() {
    }

    void run() {
        try {
            Proteinaco.doc = Proteinaco.builder.parse(Proteinaco.tempOrderOriginalXmlFile);
        } catch (SAXException | IOException ex) {
            logger.error(ex);
            return;
        }
        Proteinaco.doc.getDocumentElement().normalize();

        System.out.println("Root element: " + Proteinaco.doc.getDocumentElement().getNodeName());
        // Načíst objednávky 
        ordersNew = new ArrayList<>();
        NodeList orders = Proteinaco.doc.getElementsByTagName("ORDER");
        for (int i = 0; i < orders.getLength(); i++) {
            // Zpracuj objednávku
            Node order = orders.item(i);
//            for (int j = 0; j < order.getChildNodes().getLength(); j++) {
//                System.out.println(" name="+order.getChildNodes().item(j).getNodeName()+" type="+order.getChildNodes().item(j).getNodeType()+" value="+order.getChildNodes().item(j).getNodeValue());
//
//            }                        
//            ordersNew.add(order.cloneNode(true));
            NodeList order_itemses = ((Element) order).getElementsByTagName("ORDER_ITEMS");
            Node order_items = null;
            if (order_itemses.getLength() > 0) {
                order_items = order_itemses.item(0);
                for (int j = 0; j < order_items.getChildNodes().getLength(); j++) {
                    System.out.println(" name=" + order_items.getChildNodes().item(j).getNodeName() + " type=" + order_items.getChildNodes().item(j).getNodeType() + " value=" + order_items.getChildNodes().item(j).getNodeValue());
                }
            }

            // Načíst položky objednávky
            itemsNew = new ArrayList<>();
            NodeList items;
            while (((items = ((Element) order).getElementsByTagName("ITEM")).getLength()) > 0) {
                // Zpracuj polozku skladu
                Node item = items.item(0);

                // Načíst kód položky objednávky
                String codeItem = getTag(item, "CODE");
                String amountItem = getTag(item, "AMOUNT");
                HashMap<String, Integer> orderNewItem = new HashMap();
                if (Proteinaco.codeKeys.containsKey(codeItem)) {
                    // Kódy položek rozpadu
                    String[] itemsCodeVariant = Proteinaco.codeKeys.get(codeItem);
                    for (int k = 0; k < itemsCodeVariant.length; k++) {
                        String itemCodeVariant = itemsCodeVariant[k];
                        if (itemCodeVariant == null) {
                            continue;
                        }
                        int amount = 0;
                        try {
                            amount = Integer.parseInt(amountItem);
                        } catch (NumberFormatException ex) {
                            logger.error(ex);
                        }
                        // Do zasobniku
                        if (orderNewItem.containsKey(itemCodeVariant)) {
                            amount += orderNewItem.get(itemCodeVariant);
                        }
                        // Uložit mmnožství
                        orderNewItem.put(itemCodeVariant, amount);
                    }
                    // Uložit položky ze zássobníku do objednávky
                    for (String key: orderNewItem.keySet()) {
                        Node itemCodeNew = item.cloneNode(true);
                        setTag(itemCodeNew, "CODE", key );                        
                        setTag(itemCodeNew, "AMOUNT", String.format("%1$d",orderNewItem.get(key) ) );
                        setTag(itemCodeNew, "VARIANT_NAME", Proteinaco.item.get(key).getVariant() );                                                
                        itemsNew.add(itemCodeNew);
                    }

                } else {
                    itemsNew.add(item.cloneNode(true));
                }
                order_items.removeChild(item);
            }

            // Přidat nové položky skladu
            for (Node itemNew : itemsNew) {
                order_items.appendChild(itemNew);
            }
        }
    }

    private void setTag(Node nodeParent, String tagName, String value) {
        NodeList nodeList = ((Element) nodeParent).getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            String oldValue = node.getTextContent();
            node.setTextContent(value);
            System.out.println(" tagName=" + tagName + " OLD value=" + oldValue + " NEW value=" + value + " AKTUAL node.getTextContent()=" + node.getTextContent());
        }
    }

    private String getTag(Node nodeParent, String tagName) {
        String value = "";
        NodeList nodeList = ((Element) nodeParent).getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            value = node.getTextContent();
            // System.out.println(" tagName=" + tagName + " node.getTextContent()=" + node.getTextContent());
        }
        return value;
    }
}
