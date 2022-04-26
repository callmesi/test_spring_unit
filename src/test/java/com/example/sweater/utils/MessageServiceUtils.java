package com.example.sweater.utils;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageServiceUtils {

    public static Iterable<Message> generateMockedAnswer(long maxSize) {
        return LongStream.range(0, maxSize)
                .mapToObj(MessageServiceUtils::generateUser)
                .map(MessageServiceUtils::generateMessage)
                .peek(m -> log.info("Generated message: {}", m.toString()))
                .collect(Collectors.toList());
    }

    private static User generateUser(long i) {
        return User.builder()
                .id(i)
                .username(String.valueOf(i))
                .password(String.valueOf(i))
                .email(String.valueOf(i))
                .build();
    }

    private static Message generateMessage(User user) {
        return Message.builder()
                .author(user)
                .id(user.getId())
                .text(generateUserMessage(user.getUsername()))
                .build();
    }

    public static String generateUserMessage(String name) {
        return name + " interesting dude!";
    }
}
