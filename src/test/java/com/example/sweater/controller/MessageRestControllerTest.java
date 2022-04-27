package com.example.sweater.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.service.MessageService;
import com.example.sweater.testutils.ResourceAccessor;
import com.example.sweater.util.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(properties = "/application-test.properties")
@AutoConfigureMockMvc(secure = false)
@ExtendWith(SpringExtension.class)
class MessageRestControllerTest {

    @SpyBean
    private MessageService messageService;

    @MockBean
    private Utils utils;
    @SpyBean
    private MessageRepo messageRepo;

    @Autowired
    private MockMvc mockMvc;

    private ResourceAccessor resourceAccessor;

    @BeforeEach
    public void init() {
        resourceAccessor = new ResourceAccessor(MessageRestControllerTest.class);
    }

    @Test
    void testEndpoint() throws Exception {
        mockMvc.perform(get("/rest/test"))
                .andExpect(content().string(containsString("test")));
    }

    @Test
    void getAllMessages_shouldOk_whenEmptyResponse() throws Exception {
        String expected = "";
        mockMvc.perform(get("/rest/getAllMessages"))
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    @Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllMessages_shouldOk_whenNonEmptyResponse() throws Exception {
        String expected = Files.lines(resourceAccessor.getFile("expected.json").toPath())
                .collect(Collectors.joining());
        mockMvc.perform(get("/rest/getAllMessages"))
                .andExpect(content().string(containsString(expected)));
    }
}