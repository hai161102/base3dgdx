package com.haiprj.gamebase.utils.server;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketConnectionUtil {

    public static String URL_SOCKET = "http://localhost:8080/";
    private static SocketConnectionUtil instance;
    public Socket socket;
    public Emitter emitter;

    private SocketConnectionUtil() {
        try {
            this.socket = IO.socket(URL_SOCKET);
            this.emitter = this.socket.connect();
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }

    public static SocketConnectionUtil getInstance() {
        if (SocketConnectionUtil.instance == null) SocketConnectionUtil.instance = new SocketConnectionUtil();
        return SocketConnectionUtil.instance;
    }

    public static SocketConnectionUtil getInstance(String link) {
        SocketConnectionUtil.URL_SOCKET = link;
        return new SocketConnectionUtil();
    }

    @SuppressWarnings("VulnerableCodeUsages")
    public static JSONObject parseData(String key, Object... data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(key, data);
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
