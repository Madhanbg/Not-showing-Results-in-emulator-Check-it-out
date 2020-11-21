package com.example.booklistapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Network {
    private static final String TAG = "Network";

    public Network() {
    }


    public static ArrayList<BookData> extractbooksfromurl(String requesturl){
        Log.d(TAG, "extractbooksfromurl: Now extracting bookdata");
        ArrayList<BookData> BookDetails;
        URL url = createUrl(requesturl);

        String jsonresponse = null;
        try {
            jsonresponse = makeHttprequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Error closing input stream", e);
        }

        BookDetails = extractInfojson(jsonresponse);
        return BookDetails;
    }

    private static URL createUrl(String requestUrl) {
        Log.d(TAG, "createUrl: now");
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "The problem with URL", e);
        }
        return url;
    }

    private static String makeHttprequest(URL url) throws IOException {
        Log.d(TAG, "makeHttprequest: Now makehttpRequest is going on");
        String jsonResponse = "";
        int responsecode;
        if (url == null) {
           return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1500);
            urlConnection.connect();
         responsecode= urlConnection.getResponseCode();
            if (responsecode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readfromstream(inputStream);
            } else {
                Log.e(TAG, "Error response code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "problem with MakeHttprequest method", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
            }
        }}
        
        return jsonResponse;
    }

    private static String readfromstream(InputStream inputStream)throws IOException {
        Log.d(TAG, "readfromstream: now streaming method");
        StringBuilder output = new StringBuilder();
        if (inputStream!= null){
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            try {
                String line = bufferedReader.readLine();
                while (line!=null ){
                    output.append(line);
                    line = bufferedReader.readLine();
                }

            } catch (IOException e) {
                Log.e(TAG,"Problem in readfromstream method",e);
            }
        } return output.toString();

    }
    public  static ArrayList<BookData> extractInfojson(String bookListData){
        Log.d(TAG, "extractInfojson: now extracting from json");
       if (TextUtils.isEmpty(bookListData)){
           return  null;
        }
        ArrayList<BookData> arrayListnew = new ArrayList<>();
        String authorsName = null;
        String title;
        String images;
        try {
            JSONObject jsonRootObject = new JSONObject(bookListData);
            JSONArray jsonArray = jsonRootObject.getJSONArray("item");
            Log.d(TAG, "extractInfojson: now it is in jsonarray");
            
                for (int i = 0; i<jsonArray.length(); ++i){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject volumeinfo = jsonObject.getJSONObject("volumeInfo");
                   JSONArray authors_list ;
                   if (volumeinfo.has("authors")){
                      authors_list = volumeinfo.getJSONArray("authors");
                      authorsName = authors_list.getString(0);
                      // for (int y=0;y<authors_list.length();y++)
                        //   authorsName = authors_list.getString(y);
                   }else{
                       authorsName ="No Author";
                   }
                    title = volumeinfo.getString("title").toString();
                 //   JSONObject image = volumeinfo.getJSONObject("imageLinks");
                          // images = image.getString("thumbnail").toString();
                    arrayListnew.add(new BookData(title,authorsName));
                }
            

        } catch (JSONException e) {
            Log.e(TAG,"problem in parsing the data",e);
        }
        Log.d(TAG, "extractInfojson: returning bookdata from json");
    return arrayListnew;
    }
}
