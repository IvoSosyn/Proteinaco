/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sosyn
 */
public class ProteinacoItem {

    /**
     * Implementace org.apache.logging.log4j.Logger
     */
    private static final Logger logger = LogManager.getLogger(ProteinacoItem.class);

    private String code = "nezname";
    private String name = "nezname";
    private String variant = "nezname";

    /**
     * Ceníková položka
     *
     * @param code - kód výrobku
     * @param name - jméno výrobku
     *
     */
    public ProteinacoItem(String code, String name, String variant) {
        if (code instanceof String) {
            this.code = code;
        }
        if (name instanceof String) {
            this.name = name;
        }
        if (variant instanceof String) {
            this.variant = variant;
        }
    }

    /**
     * Ceníková položka
     *
     * @param code - kód výrobku
     * @param name - jméno výrobku
     *
     */
    public ProteinacoItem(String code, String name) {
        this(code, name, null);
    }

    /**
     * Ceníková položka bez jména (default jméno je 6 mezer)
     *
     * @param code - kód výrobku
     *
     */
    public ProteinacoItem(String code) {
        this(code, null, null);
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the variant
     */
    public String getVariant() {
        return variant;
    }

    /**
     * @param variant the variant to set
     */
    public void setVariant(String variant) {
        this.variant = variant;
    }

}
