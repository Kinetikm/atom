package ru.atom;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created by vladfedorenko on 02.05.17.
 */

@SuppressWarnings("serial")
public class EventServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(EventHandler.class);
    }
}

