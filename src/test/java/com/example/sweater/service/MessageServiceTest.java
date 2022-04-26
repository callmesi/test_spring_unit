package com.example.sweater.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.utils.MessageServiceUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MessageServiceTest {

    private final long maxSize = 5;
    private MessageService testedService;
    private MessageRepo messageRepo;

    @BeforeEach
    public void initMocks() {
        messageRepo = Mockito.mock(MessageRepo.class);
        Iterable<Message> mockedAnswer = MessageServiceUtils.generateMockedAnswer(maxSize);
        Mockito.when(messageRepo.findAll()).thenReturn(mockedAnswer);
        testedService = new MessageService(messageRepo);
    }

    @Test
    public void getAllMessages_shouldReturnAllMessages_whenMockDbRespond() {
        List<MessageDto> response = testedService.getAllMessages();
        assertEquals(maxSize, response.size());
        assertNotNull(response.get(0), "first element shouldn't be null!");
        assertEquals(response.get(0).getText(), MessageServiceUtils.generateUserMessage("0"));
    }
}