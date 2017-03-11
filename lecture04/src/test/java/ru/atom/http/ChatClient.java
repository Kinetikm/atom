package ru.atom.http;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;


public class ChatClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "wtfis.ru";
    private static final String PORT = ":8080";

    //GET host:port/chat/online
    public static Response viewOnline() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/chat/online")
                .addHeader("host", HOST + PORT)
                .build();

        return client.newCall(request).execute();
    }

    //GET host:port/chat/login?name=my_name
    public static Response login(String name) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/chat/login?name=" + name)
                .build();

        return client.newCall(request).execute();
    }

    //GET host:port/chat/say?name=my_name
    //Body: "my_message"
    public static Response say(String name, String msg) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "msg='"+msg+"'"))
                .url(PROTOCOL + HOST + PORT + "/chat/say?name=" + name)
                .build();
        return client.newCall(request).execute();
    }

    //GET host:port/chat/chat
    public static Response viewChat() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/chat/chat")
                .build();
        return client.newCall(request).execute();
    }
}