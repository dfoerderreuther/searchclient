package com.decisionem.google.model;

import java.util.List;

import com.google.api.client.util.Key;

public class ResponseData {

    @Key("results")
    private List<Result> results;

    public List<Result> getResults() {
      return results;
    }

}
