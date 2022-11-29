package top.aisdanny.pic.tool;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Http {
    private static String HTTP_LINK="http://aisdanny.top:8080/";
    public static void get(final String urlStr, final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(HTTP_LINK+urlStr);
                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    if(connection.getResponseCode()==200){
                        InputStream in=connection.getInputStream();
                        String str="";
                        StringBuffer sb=new StringBuffer();
                        BufferedReader br=new BufferedReader(new InputStreamReader(in,"utf-8"));
                        while ((str=br.readLine())!=null) sb.append(str);
                        Message msg=new Message();
                        msg.obj=sb.toString();
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void post(final String urlStr, final String json, final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(HTTP_LINK+urlStr);
                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type","application/json");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
                    out.append(json);
                    out.flush();
                    out.close();
                    if(connection.getResponseCode()==200){
                        InputStream in=connection.getInputStream();
                        String str="";
                        StringBuffer sb=new StringBuffer();
                        BufferedReader br=new BufferedReader(new InputStreamReader(in,"utf-8"));
                        while ((str=br.readLine())!=null) sb.append(str);
                        Message msg=new Message();
                        msg.obj=sb.toString();
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void getPic(final String urlStr, final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(HTTP_LINK+urlStr);
                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    if(connection.getResponseCode()==200){
                        InputStream in=connection.getInputStream();
                        Message msg=new Message();
                        msg.obj= BitmapFactory.decodeStream(in);
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
