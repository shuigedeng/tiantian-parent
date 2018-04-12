package com.tiantian.search.service;

import com.tiantian.common.pojo.SearchResult;

public interface SearchService{
    public SearchResult search(String queryString, int page, int rows) throws Exception;
}
