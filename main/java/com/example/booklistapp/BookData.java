package com.example.booklistapp;

import org.json.JSONArray;

public class BookData {


   private String image;
    private String titleName;
   private  String authorName;



    public BookData(String image, String titleName, String authorName) {
        this.image = image;
        this.titleName = titleName;
        this.authorName = authorName;

    }
    public BookData(String titleName) {
      this.titleName = titleName;
    }

    public BookData(String title, String authorsName) {
        titleName = title;
        authorName = authorsName;
    }

    public String getImage() {
        return image;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getAuthorName() {
        return authorName;
    }


}
