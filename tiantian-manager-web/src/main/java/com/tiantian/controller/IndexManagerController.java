package com.tiantian.controller;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 戴礼明
 * @create 2018/4/11 17:32
 */
@Controller
public class IndexManagerController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult indexImport() throws Exception {
        TaotaoResult taotaoResult = searchItemService.importAllItemToIndex();
        return taotaoResult;
    }
}
