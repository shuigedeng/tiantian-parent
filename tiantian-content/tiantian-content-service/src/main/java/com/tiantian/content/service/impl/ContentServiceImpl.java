package com.tiantian.content.service.impl;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.common.utils.JsonUtils;
import com.tiantian.content.service.ContentService;
import com.tiantian.mapper.TbContentMapper;
import com.tiantian.pojo.TbContent;
import com.tiantian.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.tiantian.content.jedis.JedisClient;

import java.util.Date;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/11 15:29
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Override
    public TaotaoResult insertContent(TbContent tbContent) {
        //补全属性
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //插入数据
        tbContentMapper.insert(tbContent);

        // 做缓存同步，清除redis中内容分类id对应的缓存信息
        jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());

        return TaotaoResult.ok();

    }

    @Override
    public List<TbContent> getContentList(long cid) {
        //查询缓存
        try {
            String json = jedisClient.hget(CONTENT_KEY, cid + "");
            //判断json是否为空
            if (StringUtils.isNotBlank(json)) {
                //把json转换成list
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        return list;
    }
}
