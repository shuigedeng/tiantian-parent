import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author 戴礼明
 * @create 2018/4/11 17:18
 */
public class TestSolrJ {
    @Test
    public void testSolrJAddDocument() throws Exception {
        // 创建一个SolrServer对象，即HttpSolrServer对象，需要指定solr服务的url
        // 如果有多个collection，则需要指定要操作哪个collection，如果只有一个，可以不指定
        SolrServer solrServer = new HttpSolrServer("http://116.62.71.60:8089/solr");
        // 创建一个文档对象，即SolrInputDocument对象
        SolrInputDocument document = new SolrInputDocument();
        // 向文档中添加域，添加域这里面有一个要求，必须有一个id域，域必须在schema.xml中定义
        document.addField("id", "test001");
        document.addField("item_title", "海尔空调");
        document.addField("item_sell_point", "送电暖宝一个哟！");
        document.addField("item_price", 10000);
        document.addField("item_image", "http://www.123.ipg");
        document.addField("item_category_name", "电器");
        document.addField("item_desc", "这是一款最新的空调，质量好，值得您信赖！！");
        // 把文档写入索引库
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }

    @Test
    public void testSolrJDeleteDocument() throws Exception {
        // 创建一个SolrServer对象，即HttpSolrServer对象，需要指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://116.62.71.60:8089/solr");
        // 通过id来删除文档
        solrServer.deleteById("test001");
        // 提交
        solrServer.commit();
    }
}
