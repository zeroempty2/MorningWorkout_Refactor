package com.sparta.morningworkout.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.morningworkout.websocket.dto.WebSocketChatMessage;
import com.sparta.morningworkout.websocket.dto.WebSocketChatRoom;
import com.sparta.morningworkout.websocket.service.WebSocketChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
  private final ObjectMapper objectMapper;
  private final WebSocketChatService webSocketChatService;

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("payload {}", payload);
    WebSocketChatMessage websocketChatMessage = objectMapper.readValue(payload, WebSocketChatMessage.class);
    WebSocketChatRoom room = webSocketChatService.findRoomById(websocketChatMessage.getRoomId());
    room.handleActions(session, websocketChatMessage, webSocketChatService);
  }
}