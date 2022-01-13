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
 * Třída načte klíče s popisem položek převodů-rozpadů na jiné položky ze souboru \<codeKeysName\>
 * první položka v seznamu je ta, která se rozpadá na položky, které následují za odddělovačem
 * např. 105/RAS,67/RAS,67/RAS ... položka 105/RAS se rozpadá na (2 položky) 67/RAS a 67/RAS
 * 
 * @author sosyn
 */
class ReadKeys {
    
    int MAX_POLOZEK_ROZPADU=15;

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
                items = new String[MAX_POLOZEK_ROZPADU];
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
