package pokerServer.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import pokerServer.webSocketHandler.SimpleServerWebSocketHandler;

@Configuration
@EnableWebSocket
public class ApplicationConfigurer implements WebSocketConfigurer {

    @Autowired
    SimpleServerWebSocketHandler simpleServerWebSocketHandler;

    @Override public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(simpleServerWebSocketHandler, "/poker");
    }
}
