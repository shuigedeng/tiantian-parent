package com.tiantian.content.service;

import com.tiantian.common.pojo.EasyUITreeNode;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.pojo.TbContent;

import java.util.List;

public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(long parentId);

    TaotaoResult insertContentCat(long parentId, String name);

}
