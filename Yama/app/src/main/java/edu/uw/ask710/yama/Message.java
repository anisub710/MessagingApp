package edu.uw.ask710.yama;

/**
 * Created by Anirudh Subramanyam on 10/31/2017.
 */

public class Message {

    public String author = "";
    public String body = "";
    public long date = 0;


    public Message(){

    }

    public Message(String author, String body, Long date){
        this.author = author;
        this.body = body;
        this.date = date;
    }
}


