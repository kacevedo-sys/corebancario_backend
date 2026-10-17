package com.umg.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Serdeable
@Getter
@Setter
public class PageResponse<T> {
    private List<T> content;
    private int totalSize;
    private PageableInfo pageable;

    public PageResponse(List<T> content, int totalSize, int size, int number) {
        this.content = content;
        this.totalSize = totalSize;
        this.pageable = new PageableInfo(size, number);
    }

    @Serdeable
    @Getter
    @Setter
    public static class PageableInfo {
        private int size;
        private int number;

        public PageableInfo(int size, int number) {
            this.size = size;
            this.number = number;
        }
    }
}