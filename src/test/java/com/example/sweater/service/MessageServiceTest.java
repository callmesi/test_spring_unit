package com.example.sweater.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MessageServiceTest {

    private MessageService testedService;
    private MessageRepo messageRepo;

    private final long maxSize = 5;

    @BeforeEach
    public void initMocks() {
        messageRepo = Mockito.mock(MessageRepo.class);
        Iterable<Message> mockedAnswer = generateMockedAnswer();
        Mockito.when(messageRepo.findAll()).thenReturn(mockedAnswer);
        testedService = new MessageService(messageRepo);
    }

    @Test
    public void getAllMessages_shouldReturnAllMessages_whenMockDbRespond() {
        List<MessageDto> response = testedService.getAllMessages();
        assertEquals(maxSize, response.size());
        assertNotNull(response.get(0), "first element shouldn't be null!");
        assertEquals(response.get(0).getText(), generateUserMessage("0"));
    }

    public Iterable<Message> generateMockedAnswer() {
        return LongStream.range(0, maxSize)
                .mapToObj(this::generateUser)
                .map(this::generateMessage)
                .peek(m -> log.info("Generated message: {}", m.toString()))
                .collect(Collectors.toList());
    }

    private User generateUser(long i) {
        return User.builder()
                .id(i)
                .username(String.valueOf(i))
                .password(String.valueOf(i))
                .email(String.valueOf(i))
                .build();
    }

    private Message generateMessage(User user) {
        return Message.builder()
                .author(user)
                .id(user.getId())
                .text(generateUserMessage(user.getUsername()))
                .build();
    }

    private String generateUserMessage(String name) {
        return name + " interesting dude!";
    }
}