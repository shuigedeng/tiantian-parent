package com.tiantian.controller;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.content.service.ContentService;
import com.tiantian.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 戴礼明
 * @create 2018/4/11 15:30
 */
@Controller
public class ContentServiceController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent content) {
        TaotaoResult result = contentService.insertContent(content);
        return result;
    }

}
