package com.project.elearningwebapp.models;

import java.util.List;

public class FormBean {
    private List<String>responses;

    public FormBean() {
    }

    public FormBean(List<String> responses) {
        this.responses = responses;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "FormBean{" +
                "responses=" + responses +
                '}';
    }
}
