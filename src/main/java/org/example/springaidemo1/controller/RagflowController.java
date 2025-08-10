package org.example.springaidemo1.controller;

import org.example.springaidemo1.dto.RagflowDtos;
import org.example.springaidemo1.service.RagflowService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ragflow")
public class RagflowController {

    private final RagflowService ragflowService;

    public RagflowController(RagflowService ragflowService) {
        this.ragflowService = ragflowService;
    }

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> chat(@RequestBody RagflowDtos.RagflowChatRequest request) {
        String result = ragflowService.chat(request);
        return ResponseEntity.ok(result);
    }
}