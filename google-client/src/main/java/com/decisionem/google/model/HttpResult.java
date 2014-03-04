package com.decisionem.google.model;

import com.google.api.client.util.Key;

public class HttpResult {

    @Key("responseData")
    private ResponseData responseData;

    public ResponseData getResponseData() {
      return responseData;
    }

}
