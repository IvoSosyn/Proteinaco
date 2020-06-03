/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sosyn
 */
class ReadKeys {

    /**
     * Implementace org.apache.logging.log4j.Logger
     */
    private static final Logger logger = LogManager.getLogger(ReadKeys.class);

    BufferedReader br = null;

    public ReadKeys() {
    }

    void run() {
        try {
            Proteinaco.codeKeys = new HashMap<>();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(Proteinaco.codeKeysFile), "UTF-8"));
            String line;
            StringTokenizer stringTokenizer;
            String key;
            String[] items;
            while ((line = br.readLine()) != null) {
                stringTokenizer = new StringTokenizer(line, ",;|:");
                key = null;
                int i = 0;
                items = new String[5];
                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken(",;|:");
                    if (key == null) {
                        key = token.trim() + Proteinaco.CODE_END;
                    } else {
                        items[i++] = token.trim() + Proteinaco.CODE_END;
                    }
                }
                /**
                 * Proteinaco.codeKeys.put("105/CHO", new
                 * String[]{"67/VAN","67/CHO"})
                 */
                if (key != null) {
                    Proteinaco.codeKeys.put(key, items);
                }
            }
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            logger.error(ex);
            return;

        } catch (IOException ex) {
            logger.error(ex);
            return;
        }
        // Uzavřít soubor s klíči
        if (br instanceof BufferedReader) {
            try {
                br.close();
            } catch (IOException ex) {
                logger.error(ex);
                return;
            }
        }
    }

}
