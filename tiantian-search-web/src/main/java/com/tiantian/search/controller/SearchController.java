package com.tiantian.search.controller;

import com.tiantian.common.pojo.SearchResult;
import com.tiantian.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 戴礼明
 * @create 2018/4/12 8:43
 */
@Controller
public class SearchController {
    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q")String queryString,
                         @RequestParam(defaultValue="1")Integer page, Model model) throws Exception {
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        SearchResult result = searchService.search(queryString, page, ITEM_ROWS);
        //传递给页面
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("itemList", result.getItemList());
        model.addAttribute("page", page);

        //返回逻辑视图
        return "search";

    }

}
