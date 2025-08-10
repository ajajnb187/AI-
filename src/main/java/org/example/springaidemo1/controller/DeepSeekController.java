package org.example.springaidemo1.controller;

import org.example.springaidemo1.service.DeepSeekService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deepseek")
public class DeepSeekController {

    private final DeepSeekService deepSeekService;

    public DeepSeekController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    // 通用调用接口
    @PostMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestParam(defaultValue = "deepseek-chat") String model,
            @RequestParam(defaultValue = "false") boolean stream,
            @RequestBody List<Map<String, String>> messages) {
        String response = deepSeekService.chat(model, messages, stream);
        return ResponseEntity.ok(response);
    }

    // 简单对话接口
    @GetMapping("/chat/simple")
    public ResponseEntity<String> simpleChat(@RequestParam String message) {
        String response = deepSeekService.chatWithDefaultModel(message);
        return ResponseEntity.ok(response);
    }
}
