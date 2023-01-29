package com.sparta.morningworkout.websocket.controller;

import com.sparta.morningworkout.websocket.dto.WebSocketChatRoom;
import com.sparta.morningworkout.websocket.service.WebSocketChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class WebSocketChatController {

  private final WebSocketChatService webSocketChatService;

  @PostMapping
  public WebSocketChatRoom createRoom(@RequestParam String name) {
    return webSocketChatService.createRoom(name);
  }

  @GetMapping
  public List<WebSocketChatRoom> findAllRoom() {
    return webSocketChatService.findAllRoom();
  }
}