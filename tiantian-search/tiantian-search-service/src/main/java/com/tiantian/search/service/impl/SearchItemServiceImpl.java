package com.tiantian.search.service.impl;

import com.tiantian.common.pojo.SearchItem;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import com.tiantian.search.mapper.TtemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/11 17:22
 */
@Service
public class SearchItemServiceImpl implements SearchItemService{

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private TtemMapper itemMapper;

    @Override
    public TaotaoResult importAllItemToIndex() throws Exception {
        // 1、查询所有商品数据。
        List<SearchItem> itemList = itemMapper.getItemList();
        // 2、创建一个SolrServer对象。
        // 3、为每个商品创建一个SolrInputDocument对象。
        for (SearchItem searchItem : itemList) {
            SolrInputDocument document = new SolrInputDocument();
            // 4、为文档添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            // 5、向索引库中添加文档。
            solrServer.add(document);
        }
        //提交修改
        solrServer.commit();
        // 6、返回TaotaoResult。
        return TaotaoResult.ok();

    }
}
