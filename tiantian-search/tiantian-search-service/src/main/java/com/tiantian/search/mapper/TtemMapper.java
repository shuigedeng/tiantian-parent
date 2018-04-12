package com.tiantian.search.mapper;

import com.tiantian.common.pojo.SearchItem;
import com.tiantian.common.pojo.SearchResult;

import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/11 17:14
 */
public interface TtemMapper {
    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
