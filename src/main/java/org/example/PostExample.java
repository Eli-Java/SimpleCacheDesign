package org.example;

import java.io.*;
import java.net.*;

public class PostExample {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.cafeaulait.org");
            // open the connection and prepare it to POST
            URLConnection uc = u.openConnection();
            uc.setDoOutput(true);
            OutputStream raw = uc.getOutputStream();
            OutputStream buffered = new BufferedOutputStream(raw);
            OutputStreamWriter out = new OutputStreamWriter(buffered, "8859_1");
            out.write("first=Julie&middle=&last=Harting&work=String+Quartet\r\n");
            out.flush();
            out.close();

            // Read the response if needed
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}