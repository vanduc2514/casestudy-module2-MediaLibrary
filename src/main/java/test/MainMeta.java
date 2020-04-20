package main.java.test;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMeta {
    public static void main(String[] args) throws IOException, TikaException, SAXException {
        File file = new File("F:/Users/Duc/Downloads/Khoi Dau - Hoang Thuy Linh.mp3");
        FileInputStream fileInputStream = new FileInputStream(file);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseContext = new ParseContext();
        parser.parse(fileInputStream,handler,metadata,parseContext);
        fileInputStream.close();

        System.out.println(metadata.get("title"));
        System.out.println(metadata.get("xmpDM:artist"));
    }
}
