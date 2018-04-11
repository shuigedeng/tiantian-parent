package com.tiantian.service;

import com.tiantian.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
