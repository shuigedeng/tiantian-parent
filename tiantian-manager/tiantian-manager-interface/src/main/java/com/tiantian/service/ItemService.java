package com.tiantian.service;

import com.tiantian.common.pojo.EasyUIDataGridResult;

public interface ItemService {
    public EasyUIDataGridResult getItemList(int page, int rows);
}
