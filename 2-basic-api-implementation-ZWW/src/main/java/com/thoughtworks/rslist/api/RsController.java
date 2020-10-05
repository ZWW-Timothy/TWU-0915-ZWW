package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEventDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {

  /*
  private List<String> rsList = Arrays.asList("事件1", "事件2", "事件3");
  */

  private List<RsEventDto> rsEventList = initRsEventList();

  private List<RsEventDto> initRsEventList() {
    List<RsEventDto> rsList;
    rsList = new ArrayList<>();
    rsList.add(new RsEventDto("事件1", "无分类"));
    rsList.add(new RsEventDto("事件2", "无分类"));
    rsList.add(new RsEventDto("事件3", "无分类"));
    return rsList;
  }

  /*
  @GetMapping("/rs/list")
  public String getAllRsEvent() {
    return rsList.toString();
  }
   */

  @GetMapping(("/rs/{index}"))
  public RsEventDto getOneRsEvent(@PathVariable int index) {
    return rsEventList.get(index - 1);
  }

  @GetMapping(("/rs/list"))
  public List<RsEventDto> getRsEventInGivenRange(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start == null || end == null) {
      return rsEventList;
    }
    return rsEventList.subList(start - 1, end);
  }

  @PostMapping(("/rs/add"))
  public void addOneRsEvent(@RequestBody String rsEventContent) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    RsEventDto rsEvent = objectMapper.readValue(rsEventContent, RsEventDto.class);
    rsEventList.add(rsEvent);
  }

  @PutMapping(("/rs/edit/{index}"))
  public void editOneRsEvent(@PathVariable int index, @RequestBody RsEventDto rsEvent) {
    if (rsEvent.getRsEventName() != "") {
      rsEventList.get(index - 1).setRsEventName(rsEvent.getRsEventName());
    }
    if (rsEvent.getRsEventKeyword() != "") {
      rsEventList.get(index - 1).setRsEventKeyword(rsEvent.getRsEventKeyword());
    }
  }

  @DeleteMapping(("/rs/delete/{index}"))
  public void deleteOneRsEvent(@PathVariable int index) {
    rsEventList.remove(index - 1);
  }
}
