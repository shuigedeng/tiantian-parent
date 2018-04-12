package com.tiantian.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiantian.common.pojo.EasyUIDataGridResult;
import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.common.utils.IDUtils;
import com.tiantian.common.utils.JsonUtils;
import com.tiantian.jedis.JedisClient;
import com.tiantian.mapper.TbItemDescMapper;
import com.tiantian.mapper.TbItemMapper;
import com.tiantian.pojo.TbItem;
import com.tiantian.pojo.TbItemDesc;
import com.tiantian.pojo.TbItemExample;
import com.tiantian.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
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

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name="topicDestination")
    private Topic topicDestination;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

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
        final long itemId = IDUtils.genItemId();
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

        // 商品添加完成后发送一个MQ消息
        jmsTemplate.send(topicDestination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                // 创建一个消息对象
                // 要在匿名内部类访问局部变量itemId，itemId需要用final修饰
                TextMessage message = session.createTextMessage(itemId + "");
                return message;
            }
        });
        return TaotaoResult.ok();
    }

    @Override
    public TbItem getItemById(long itemId) {
        // 先查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        // 添加到缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItem));
            // 设置过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":BASE", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbItem;
    }

    @Override
    public TbItemDesc getItemDesc(long itemId) {
        // 先查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        // 添加到缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
            // 设置过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":DESC", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }
}
