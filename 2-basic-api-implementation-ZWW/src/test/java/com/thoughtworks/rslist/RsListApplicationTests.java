package com.thoughtworks.rslist;

import com.thoughtworks.rslist.dto.RsEventDto;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    /*
    @Test
    void should_get_rs_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(content().string("[事件1, 事件2, 事件3]"));
    }
     */

    @Test
    void should_get_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsEventName",is("事件1")))
                .andExpect(jsonPath("$.rsEventKeyword",is("无分类")));

        mockMvc.perform(get("/rs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsEventName",is("事件2")))
                .andExpect(jsonPath("$.rsEventKeyword",is("无分类")));

        mockMvc.perform(get("/rs/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rsEventName",is("事件3")))
                .andExpect(jsonPath("$.rsEventKeyword",is("无分类")));
    }

    @Test
    void should_get_rs_event_in_given_range() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")));
    }

    @Test
    void should_add_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto oneRsEvent = new RsEventDto("事件4", "无分类");
        ObjectMapper objectMapper = new ObjectMapper();
        final String addOneRsEventJson = objectMapper.writeValueAsString(oneRsEvent);

        mockMvc.perform(post("/rs/add")
                .content(addOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[3].rsEventName", is("事件4")))
                .andExpect(jsonPath("$[3].rsEventKeyword", is("无分类")));
    }

    @Test
    void should_edit_one_rs_event_keyword_stay() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto oneRsEvent = new RsEventDto("事件1-修改1", "");
        ObjectMapper objectMapper = new ObjectMapper();
        final String editOneRsEventJson = objectMapper.writeValueAsString(oneRsEvent);

        mockMvc.perform(put("/rs/edit/1")
                .content(editOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1-修改1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));
    }

    @Test
    void should_edit_one_rs_event_name_stay() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto oneRsEvent = new RsEventDto("", "无分类-修改1");
        ObjectMapper objectMapper = new ObjectMapper();
        final String editOneRsEventJson = objectMapper.writeValueAsString(oneRsEvent);

        mockMvc.perform(put("/rs/edit/2")
                .content(editOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类-修改1")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));
    }

    @Test
    void should_edit_one_rs_event_both_change() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        RsEventDto oneRsEvent = new RsEventDto("事件3-修改1", "无分类-修改1");
        ObjectMapper objectMapper = new ObjectMapper();
        final String editOneRsEventJson = objectMapper.writeValueAsString(oneRsEvent);

        mockMvc.perform(put("/rs/edit/3")
                .content(editOneRsEventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3-修改1")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类-修改1")));
    }

    @Test
    void should_delete_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件1")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[2].rsEventKeyword", is("无分类")));

        mockMvc.perform(delete("/rs/delete/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rsEventName", is("事件2")))
                .andExpect(jsonPath("$[0].rsEventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].rsEventName", is("事件3")))
                .andExpect(jsonPath("$[1].rsEventKeyword", is("无分类")));
    }
}
