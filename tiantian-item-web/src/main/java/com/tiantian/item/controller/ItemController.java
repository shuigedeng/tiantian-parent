package com.tiantian.item.controller;

import com.tiantian.item.pojo.Item;
import com.tiantian.pojo.TbItem;
import com.tiantian.pojo.TbItemDesc;
import com.tiantian.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:54
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        // 根据商品id查询商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        // 使用TbItem来初始化Item对象
        Item item = new Item(tbItem);
        // 根据商品id查询商品描述
        TbItemDesc tbItemDesc = itemService.getItemDesc(itemId);
        // 传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        // 返回逻辑视图
        return "item";
    }

}
