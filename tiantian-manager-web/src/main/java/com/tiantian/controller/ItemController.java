package com.tiantian.controller;

import com.tiantian.common.pojo.EasyUIDataGridResult;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.pojo.TbItem;
import com.tiantian.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 戴礼明
 * @create 2018/4/10 14:02
 */
@Controller
public class ItemController {
    @Autowired
    public ItemService itemService;

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult itemList(int page, int rows){
        EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
        return itemList;
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String desc){
        TaotaoResult taotaoResult = itemService.addItem(tbItem, desc);
        return taotaoResult;
    }
}
