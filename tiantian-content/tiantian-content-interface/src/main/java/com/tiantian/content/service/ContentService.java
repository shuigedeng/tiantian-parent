package com.tiantian.content.service;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.pojo.TbContent;

import java.util.List;

public interface ContentService {
    TaotaoResult insertContent(TbContent tbContent);

    List<TbContent> getContentList(long cid);
}
