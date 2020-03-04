package com.dmtroncoso.satapp.retrofit.model;

import java.util.ArrayList;
import java.util.List;

public class PagedList<T> {
    private int page;
    private List<T> results;
    private int totalResults;
    private int totalPages;

    public PagedList() {
    }

    public PagedList(int page, List<T> results, int totalResults, int totalPages) {
        this.page = page;
        this.results = new ArrayList<>();
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
