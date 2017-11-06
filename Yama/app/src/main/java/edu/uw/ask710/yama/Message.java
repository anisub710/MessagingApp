package edu.uw.ask710.yama;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anirudh Subramanyam on 10/31/2017.
 */

//custom class to store messages with author, body and date.
public class Message {

    public String author = "";
    public String body = "";
    public String date = "";


    public Message(){

    }

    public Message(String author, String body, String date){
        this.author = author;
        this.body = body;
        this.date = date;
    }

    public String getDate(){
        long computed = Long.parseLong(this.date);
        Date df = new Date(computed);
        String correctDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(df);
        return correctDate;
    }
}


