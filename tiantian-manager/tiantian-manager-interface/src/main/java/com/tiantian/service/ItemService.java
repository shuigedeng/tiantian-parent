package com.tiantian.service;

import com.tiantian.common.pojo.EasyUIDataGridResult;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.pojo.TbItem;
import com.tiantian.pojo.TbItemDesc;

public interface ItemService {
    EasyUIDataGridResult getItemList(int page, int rows);

    TaotaoResult addItem(TbItem tbItem, String desc);

    TbItem getItemById(long itemId);

    TbItemDesc getItemDesc(long itemId);
}
