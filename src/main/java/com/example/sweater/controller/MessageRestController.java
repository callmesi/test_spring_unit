package com.example.sweater.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sweater.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/rest")
public class MessageRestController {

    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
        objectMapper = new ObjectMapper();
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString("{message: test}"));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body("Can't parse json " + e.getMessage());
        }
    }

    @GetMapping("/getAllMessages")
    public ResponseEntity<String> getAllMessages() {
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(messageService.getAllMessages()));
        } catch (JsonProcessingException e) {
            log.error("Can't parse json.", e);
            return ResponseEntity.status(500).body("Can't parse json " + e.getMessage());
        }
    }
}
