package com.scoreboard.app.util;

import org.springframework.data.domain.Page;

import java.util.List;

public class PagedResponse<T> {

    private Page<T> page;

    private PagedResponse(Page<T> page) {
        this.page = page;
    }

    public static <T> PagedResponse<T> of(Page<T> page) {
        return new PagedResponse(page);
    }

    public List<T> getContent() {
        return page.getContent();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public int getCurrentPage() {
        return page.getNumber();
    }

    public int getPageSize() {
        return page.getSize();
    }

    public Boolean getLast() {
        return page.isLast();
    }

    public Boolean getFirst() {
        return page.isFirst();
    }
}
