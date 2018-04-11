package com.tiantian.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiantian.common.pojo.EasyUIDataGridResult;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.common.utils.IDUtils;
import com.tiantian.mapper.TbItemDescMapper;
import com.tiantian.mapper.TbItemMapper;
import com.tiantian.pojo.TbItem;
import com.tiantian.pojo.TbItemDesc;
import com.tiantian.pojo.TbItemExample;
import com.tiantian.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/10 13:57
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page, rows);
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(tbItems);

        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItems);
        result.setTotal(tbItemPageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);

        tbItem.setStatus((byte)1);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);

        tbItemMapper.insert(tbItem);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);

        tbItemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }
}
