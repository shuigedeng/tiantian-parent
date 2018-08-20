package com.tiantian.content.service.impl;

import com.tiantian.common.pojo.EasyUITreeNode;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.content.service.ContentCategoryService;
import com.tiantian.mapper.TbContentCategoryMapper;
import com.tiantian.mapper.TbContentMapper;
import com.tiantian.pojo.TbContent;
import com.tiantian.pojo.TbContentCategory;
import com.tiantian.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/11 14:58
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;


    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();

        for(TbContentCategory tbContentCategory: tbContentCategories){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
            easyUITreeNodes.add(easyUITreeNode);
        }
        return easyUITreeNodes;
    }

    @Override
    public TaotaoResult insertContentCat(long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setIsParent(false);
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategoryMapper.insert(tbContentCategory);

        TbContentCategory tbContentCategoryNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if(!tbContentCategoryNode.getIsParent()){
            tbContentCategoryNode.setIsParent(true);
            //更新父节点
            tbContentCategoryMapper.updateByPrimaryKey(tbContentCategoryNode);
        }
        return TaotaoResult.ok(tbContentCategory);
    }

}
