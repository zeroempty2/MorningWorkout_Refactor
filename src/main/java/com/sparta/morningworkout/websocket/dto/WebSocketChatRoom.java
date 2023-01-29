package com.sparta.morningworkout.websocket.dto;

import com.sparta.morningworkout.websocket.service.WebSocketChatService;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class WebSocketChatRoom {
  private String roomId;
  private String name;
  private Set<WebSocketSession> sessions = new HashSet<>();

  @Builder
  public WebSocketChatRoom(String roomId, String name) {
    this.roomId = roomId;
    this.name = name;
  }

  public void handleActions(WebSocketSession session, WebSocketChatMessage websocketChatMessage, WebSocketChatService webSocketChatService) {
    if (websocketChatMessage.getType().equals(WebSocketChatMessage.MessageType.ENTER)) {
      sessions.add(session);
      WebSocketChatMessage message = WebSocketChatMessage.builder().type(websocketChatMessage.getType()).sender(
          websocketChatMessage.getSender()).message(websocketChatMessage.getSender() + "님이 입장했습니다.").roomId(
          websocketChatMessage.getRoomId()).build();
//      chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
      sendMessage(message, webSocketChatService);
    }
    else {
      sendMessage(websocketChatMessage, webSocketChatService);
    }
  }

  public <T> void sendMessage(T message, WebSocketChatService webSocketChatService) {
    sessions.parallelStream().forEach(session -> webSocketChatService.sendMessage(session, message));
  }
}