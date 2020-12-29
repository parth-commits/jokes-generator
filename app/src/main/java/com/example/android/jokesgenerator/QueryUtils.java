package com.example.android.jokesgenerator;

import android.os.Build;
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

public final class QueryUtils {

    /*private so noone calls this constructor*/
    private QueryUtils(){
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(MainActivity.LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(MainActivity.LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(MainActivity.LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static Joke fetchJokeData(String requestURL){

        URL url = createUrl(requestURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(MainActivity.LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link countries}s

        // Returns the joke
        return extractJoke(jsonResponse);

    }

    private static Joke extractJoke(String jokeJSON) {
        if (TextUtils.isEmpty(jokeJSON)) {
            return null;
        }
        try {
            //Build a joke object from json
            JSONObject root = new JSONObject(jokeJSON);

            String category = root.getString("category");
            String setup = root.getString("setup");
            String delivery = root.getString("delivery");
            boolean nsfw;
            boolean religious;
            boolean political;
            boolean racist;
            boolean sexist;
            boolean explicit;

            JSONObject flags = new JSONObject(root.getString("flags"));
            if (flags.getString("nsfw").equals("false")){
                nsfw = false;
            }
            else {
                nsfw = true;
            }
            if (flags.getString("religious").equals("false")){
                religious = false;
            }
            else {
                religious = true;
            }
            if (flags.getString("political").equals("false")){
                political = false;
            }
            else {
                political = true;
            }
            if (flags.getString("racist").equals("false")){
                racist = false;
            }
            else {
                racist = true;
            }
            if (flags.getString("sexist").equals("false")){
                sexist = false;
            }
            else {
                sexist = true;
            }
            if (flags.getString("explicit").equals("false")){
                explicit = false;
            }
            else {
                explicit = true;
            }
            return new Joke(category, setup, delivery, nsfw, religious, political, racist, sexist, explicit);
        }
        catch (Exception e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Joke JSON results", e);
        }

        String category = "NoJokeAvailable";
        String setup = "Why couldn't the skeleton go to the Christmas party?";
        String delivery = "Because he had no body to go with!";
        boolean nsfw = false;
        boolean religious = false;
        boolean political = false;
        boolean racist = false;
        boolean sexist = false;
        boolean explicit = false;
        //return a default joke
        return new Joke(category, setup, delivery, nsfw, religious, political, racist, sexist, explicit);

    }
}
