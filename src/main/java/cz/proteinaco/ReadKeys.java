/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import static cz.proteinaco.Proteinaco.logger;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author sosyn
 */
class ReadKeys {

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
            String[] items = new String[5];
            while ((line = br.readLine()) != null) {
                stringTokenizer = new StringTokenizer(line, ",;|:");
                key = null;
                int i = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken(",;|:");
                    if (key == null) {
                        key = token;
                    } else {
                        items[i++] = token;
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
