package com.sparta.morningworkout.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.morningworkout.websocket.dto.WebSocketChatRoom;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebSocketChatService {

  private final ObjectMapper objectMapper;
  private Map<String, WebSocketChatRoom> chatRooms;

  @PostConstruct
  private void init() {
    chatRooms = new LinkedHashMap<>();
  }

  public List<WebSocketChatRoom> findAllRoom() {
    return new ArrayList<>(chatRooms.values());
  }

  public WebSocketChatRoom findRoomById(String roomId) {
    return chatRooms.get(roomId);
  }

  public WebSocketChatRoom createRoom(String name) {
    String randomId = UUID.randomUUID().toString();
    WebSocketChatRoom websocketChatRoom = WebSocketChatRoom.builder()
        .roomId(randomId)
        .name(name)
        .build();
    chatRooms.put(randomId, websocketChatRoom);
    return websocketChatRoom;
  }

  public <T> void sendMessage(WebSocketSession session, T message) {
    try {
      session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }
}
