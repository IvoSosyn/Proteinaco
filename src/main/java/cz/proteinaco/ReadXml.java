/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.proteinaco;

import static cz.proteinaco.Proteinaco.logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author sosyn
 */
public class ReadXml {

    URL urlOrdersXml = null;
    HttpsURLConnection conn = null;
    BufferedReader br = null;
    BufferedWriter bw = null;

    public ReadXml() {
    }

    void run() {
        try {
            urlOrdersXml = new URL(Proteinaco.urlOrderOriginalXml);
            conn = (HttpsURLConnection) urlOrdersXml.openConnection();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Proteinaco.tempOrderOriginalXmlFile), "UTF-8"));
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine);
                bw.newLine();
            }
        } catch (MalformedURLException ex) {
            logger.error(ex);
            return;
        } catch (IOException ex) {
            logger.error(ex);
        }
        if (br instanceof BufferedReader) {
            try {
                br.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
        if (bw instanceof BufferedWriter) {
            try {
                bw.flush();
                bw.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }

    }

}
