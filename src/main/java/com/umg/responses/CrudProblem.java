package com.umg.responses;

import io.micronaut.http.HttpStatus;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CrudProblem {
    private final String type;
    private final String title;
    private final int status;
    private final String detail;

    public CrudProblem(String type, String title, Integer status, String detail) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
    }

    public CrudProblem(String type, String title, HttpStatus status, String detail) {
        this.type = type;
        this.title = title;
        this.status = status.getCode();
        this.detail = detail;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDetail() {
        return this.detail;
    }
}
