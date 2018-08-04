package com.tiantian.controller;

import com.tiantian.common.pojo.EasyUITreeNode;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/11 15:06
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(defaultValue="0") Long id) {
        return contentCategoryService.getContentCatList(id);
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult createCategory(Long parentId, String name) {
        TaotaoResult result = contentCategoryService.insertContentCat(parentId, name);
        return result;
    }

}
