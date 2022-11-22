package com.codeyourwork.jsonreader.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@RestController
public class JsonReaderController {

    @Value("classpath:read.json")
    Resource jsonFile;


    @GetMapping("/test")
    public void extractJson() throws IOException {
        File file =jsonFile.getFile();

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode node=objectMapper.readTree(file);
        ArrayList<ArrayList<String>> s1=new ArrayList<>();
        Iterator<String> itr=node.fieldNames();
        while(itr.hasNext()){

            if(itr.next()=="locationAddress"){
                Iterator<String> mainheading=node.get("locationAddress").fieldNames();
                while(mainheading.hasNext()){
                    ArrayList<String> heading=new ArrayList<>();
                    ArrayList<String> topic=new ArrayList<>();
                    ArrayList<String> value=new ArrayList<>();
                    String tempHeading=mainheading.next();
                    heading.add(tempHeading);
                    s1.add(heading);
                    //code for topic
                    Iterator<String> mainTopic=node.get("locationAddress").get(tempHeading).fieldNames();
                    while(mainTopic.hasNext()){
                        String tempTopic=mainTopic.next();
                        String tempValue= String.valueOf(node.get("locationAddress").get(tempHeading).get(tempTopic).get("value"));
                        topic.add(tempTopic);
                        value.add(tempValue);

                    }
                    //end of topic code
                    s1.add(topic);
                    s1.add(value);


                }
            }
        }
        //while loop ends

    for(int i=0;i<s1.size();i++){
        for(int m=0;m<s1.get(i).size();m++){
            System.out.println(s1.get(i).get(m));
        }
    }

    }
}
