package com.trevorjdobson.taskmaster.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.Date;

@DynamoDBDocument
public class HistoryItem {

    private String timestamp;

    private String action;


    public HistoryItem(){}
    public HistoryItem(String action){
        this.timestamp = new Date().toString();
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


}
