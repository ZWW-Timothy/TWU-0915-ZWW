package com.thoughtworks.rslist.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RsEventDto {

  private String rsEventName;
  private String rsEventKeyword;

  public RsEventDto(String rsEventName, String rsEventKeyword) {
    this.rsEventName = rsEventName;
    this.rsEventKeyword = rsEventKeyword;
  }

  public String getRsEventName() {
    return rsEventName;
  }

  public String getRsEventKeyword() {
    return rsEventKeyword;
  }

  public void setRsEventName(String rsEventName) {
    this.rsEventName = rsEventName;
  }

  public void setRsEventKeyword(String rsEventKeyword) {
    this.rsEventKeyword = rsEventKeyword;
  }
}
