package com.trevorjdobson.taskmaster.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//public class HistoryItemConverter<T extends Object>
//        implements DynamoDBTypeConverter<String, T> {
//
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convert(T object) {
//        try {
//            return objectMapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        throw new IllegalArgumentException("Unable to parse JSON");
//    }
//
//    @Override
//    public T unconvert(String object) {
//        try {
//            T unconvertedObject = objectMapper.readValue(object,
//                    new TypeReference<T>() {
//                    });
//            return unconvertedObject;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}


public class HistoryItemConverter implements DynamoDBTypeConverter<String, List<HistoryItem>> {
    @Override
    public String convert(List<HistoryItem> object) {
        List<HistoryItem> historyItemList = (List<HistoryItem>) object;
        String convertedList = "";

        try {
            if (historyItemList != null) {
                for(HistoryItem historyItem : historyItemList){
                    String item = "";

                    item += String.format("%s;%s", historyItem.getTimestamp(), historyItem.getAction());
                    item += ",";
                    convertedList += item;
                }
                convertedList = convertedList.substring(0,convertedList.length()-1);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return convertedList;
    }



    @Override
    public List<HistoryItem> unconvert(String s) {
        System.out.println("in unconvert");
        System.out.println(s);
        ArrayList<HistoryItem> historyItemList = new ArrayList<HistoryItem>();
        try {
            if (s != null && s.length() != 0) {
                String[] data = s.split(",");
                System.out.println(data);
                for(String stringItem : data){
                    System.out.println(stringItem);
                    HistoryItem tempHistory = new HistoryItem();
                    String[] items = stringItem.split(";");
                    tempHistory.setTimestamp(items[0]);
                    tempHistory.setAction(items[1]);
                    historyItemList.add(tempHistory);
                }


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return historyItemList;
    }

}
