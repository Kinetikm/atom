package ru.atom.lecture08.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.masks.ZeroMasker;
import ru.atom.lecture08.websocket.message.Message;
import ru.atom.lecture08.websocket.message.Topic;

import java.net.URI;
import java.util.concurrent.Future;

import static ru.atom.lecture08.websocket.util.JsonHelper.toJson;

public class EventClient {
    public static void main(String[] args) {
        URI uri = URI.create("ws://wtfis.ru:8090/events/");//CHANGE TO wtfis.ru for task

        WebSocketClient client = new WebSocketClient();
        //client.setMasker(new ZeroMasker());
        try {
            try {
                client.start();
                // The socket that receives events
                EventHandler socket = new EventHandler();
                // Attempt Connect
                Future<Session> fut = client.connect(socket, uri);
                // Wait for Connect
                Session session = fut.get();
                // Send a message
                //TODO TASK: implement sending Message with type HELLO and your name as data
                Message message = new Message(Topic.HELLO, "Baranov Michael");

                session.getRemote().sendString(toJson(message));
                // Close session
                session.close();
            } finally {
                client.stop();
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
