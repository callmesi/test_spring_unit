package com.example.sweater.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.util.Utils;
import com.example.sweater.utils.MessageServiceUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MessageService.class)
class MessageServiceTest {

    private final long maxSize = 5;

    @Autowired
    private MessageService testedService;
    @MockBean
    private MessageRepo messageRepo;
    @MockBean
    private Utils utils;

    @BeforeEach
    public void initMocks() {
        when(utils.getApiText()).thenReturn("response");
        Iterable<Message> mockedAnswer = MessageServiceUtils.generateMockedAnswer(maxSize);
        when(messageRepo.findAll()).thenReturn(mockedAnswer);
    }

    @Test
    public void getAllMessages_shouldReturnAllMessages_whenMockDbRespond() {
        List<MessageDto> response = testedService.getAllMessages();
        assertEquals(maxSize, response.size());
        assertNotNull(response.get(0), "first element shouldn't be null!");
        assertEquals(response.get(0).getText(), MessageServiceUtils.generateUserMessage("0"));
    }
}