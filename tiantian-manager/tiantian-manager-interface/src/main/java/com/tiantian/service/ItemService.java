package com.tiantian.service;

import com.tiantian.common.pojo.EasyUIDataGridResult;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.pojo.TbItem;

public interface ItemService {
    public EasyUIDataGridResult getItemList(int page, int rows);

    TaotaoResult addItem(TbItem tbItem, String desc);
}
