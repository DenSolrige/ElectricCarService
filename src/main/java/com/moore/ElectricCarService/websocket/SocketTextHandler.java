package com.moore.ElectricCarService.websocket;

import java.io.IOException;

import com.moore.ElectricCarService.controllers.ChargingStationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(ChargingStationController.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String payload = message.getPayload();
        String identifier = (String)session.getAttributes().get("identifier");
        log.atInfo().log("Websocket received for "+identifier+": "+payload);
    }
}
