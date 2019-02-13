package com.springmvc.lxy.elasticsearch;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 描述: es的练习类
 * <p>
 *
 * @author: harry
 * @date: 2018-10-21
 **/
public class Client {

    protected static TransportClient client = null;

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static final String INDEX = "fendo";

    public static final String TYPE = "fendodate";

    static {
        log.info("start init client...");
        Settings settings = Settings.builder()
            .put("cluster.name", "lxy").build();
        try {
            client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            log.info("init client successfully...");
        } catch (UnknownHostException e) {
            log.error("初始化client 报错了：{}" ,e);
        }
    }

    public static void close() {
        if (client != null) {
            client.close();
        }
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        Client client = new Client();

        client.index();

//        client.close();

//        client.get();
//        client.delete();

//        client.update();

//        client.upsert();
//        client.multiGet();

//        client.search();

//        client.searchTermQuery();

//        client.searchMatchQuery();

//        client.agg1();

//        client.topHitAgg();

        close();
    }


    /**
     * set操作
     * 1.   如果Index，type都没有，会自动创建
     * 2.   如果index-type-id 对应的都有，那么会覆盖
     *
     * @throws IOException
     */
    public void index() throws IOException {
        IndexResponse response = client.prepareIndex("twitter", "tweet", "20")
            .setSource(jsonBuilder()
                .startObject()
                .field("user", "这个就是我拿来垫底的，就是最后一个怎么了")
                .field("postDate", new Date())
                .field("message", "我也不知道该说点什么好了，马上就去上班了")
                .field("age", "20")
                .field("multi", "querying是不会被分词的test虽然夹在中文中但是，会分出来的")
                .field("price1", 100)
                .endObject()
            )
            .get();


        IndexResponse response2 = client.prepareIndex("twitter", "tweet", "17")
            .setSource(jsonBuilder()
                .startObject()
                .field("user", "刘旭阳 徐雪琴 旅游 有线电视 早餐吃得饱 接送汽车飞机 地铁方便离市中心近 平民价格 大多数人的选择 棒棒的")
                .field("postDate", new Date())
                .field("message", "tryin不行的瞎写的ch2225")
                .field("age", "34")
                .field("multi", "这个只有query啊！")
                .field("price1", 100000)
                .endObject()
            )
            .get();

        IndexResponse response3 = client.prepareIndex("twitter", "tweet", "18")
            .setSource(jsonBuilder()
                .startObject()
                .field("user", "武首昭 雷晓芳 酒店 wifi 泳池可以有用 宾馆非常好 服务 热闹人多可以的 很棒")
                .field("postDate", new Date())
                .field("message", "我想验证一下有没有装ik")
                .field("age", "199")
                .field("multi", "有test也有query，还有两个query呢")
                .field("price1", 24)
                .endObject()
            )
            .get();

        IndexResponse response4 = client.prepareIndex("twitter", "tweet", "19")
            .setSource(jsonBuilder()
                .startObject()
                .field("user", "刘旭阳 武首昭 住宿绝不仅限于住宿 旅馆 特殊的享受 安静 文艺卫生 高级商务")
                .field("gender", "sdfadfsadfasdf")
                .field("message", "长春市长春药店可能是真的假的")
                .field("age", "45")
                .field("multi", "这些都是宝贵的test数据，不可以轻易的query")
                .field("price1", 56)
                .endObject()
            )
            .get();

        log.info("<index>,response:{}"
            , response.toString());

    }

    public void delete() {
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();

        log.info("<delete>,response:{}"
            , response.toString());
    }

    public void upsert() throws IOException, ExecutionException, InterruptedException {
        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
            .source(jsonBuilder()
                .startObject()
                .field("name", "Joe Smith")
                .field("gender", "male")
                .endObject());
        UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
            .doc(jsonBuilder()
                .startObject()
                .field("gender", "male")
                .endObject())
            .upsert(indexRequest);
        UpdateResponse updateResponse = client.update(updateRequest).get();

    }

    /**
     * 如果要更新的index，type，id不存在，就会报错。：DocumentMissingException[[type_not_exist][1]: document missing]
     *
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void update() throws IOException, ExecutionException, InterruptedException {
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.index("index");
//        updateRequest.type("type");
//        updateRequest.id("1");
//        updateRequest.doc(jsonBuilder()
//            .startObject()
//            .field("gender", "male")
//            .endObject());
//
//        UpdateResponse updateResponse = client.update(updateRequest).get();

        XContentBuilder xContentBuilder = jsonBuilder()
            .startObject()
            .field("gender", "male11")
            .endObject();

        log.info("<update>,req:{}", xContentBuilder.toString());

        UpdateResponse updateResponse = client.prepareUpdate("index", "type", "2")
            .setDoc(xContentBuilder)
            .get();

        log.info("<update>,req:{},response:{}", xContentBuilder.toString()
            , updateResponse.toString());
    }

    public void get() {
        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();

        Map<String, Object> source = response.getSource();
        log.info("get : index:{},type:{},id:{},source：{}，version:{}"
            , response.getIndex(), response.getType(), response.getId(), response.getSourceAsString(), response.getVersion());


        log.info("source: --");
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            log.info("key:{},value:{}", entry.getKey(), entry.getValue());
        }

        log.info("field: --");
        for (Map.Entry<String, GetField> entry : response.getFields().entrySet()) {
            log.info("key:{},value-name{},value-value", entry.getKey(), entry.getValue().getName(), entry.getValue().getValue());

        }
    }

    public void multiGet() {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
            .add("twitter", "tweet", "1")
            .add("twitter", "tweet", "2", "3", "4")
            .add("index", "type", "1")
            .get();

        log.info("multiGet:--");
        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                log.info("json:{}", json);
            }
        }
    }

    public void bulkApi() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

// either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "7")
            .setSource(jsonBuilder()
                .startObject()
                .field("user", "no7")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch no7")
                .endObject()
            )
        );

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "8")
            .setSource(jsonBuilder()
                .startObject()
                .field("user", "kimchyno8")
                .field("postDate", new Date())
                .field("message", "another post no8")
                .endObject()
            )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            log.error("bulk req faile : {}", bulkResponse.buildFailureMessage());
        } else {
            BulkItemResponse[] items = bulkResponse.getItems();
            for (BulkItemResponse item : items) {
                log.error("bulk req success : itemId:{},id:{}", item.getItemId(), item.getId());
            }
        }
    }

    public void search() {
        SearchResponse response = client.prepareSearch("twitter", "index")
            .setTypes("tweet", "type")
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
            .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
            .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
            .setFrom(0).setSize(60).setExplain(true)
            .get();

        log.info("<search> hits: num:{}", response.getHits().totalHits);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            log.info("<search> hits {}", hit.getSourceAsString());
        }
    }


    /**
     * 用了 queryBuilder.filter()  方法去构造请求。
     * term：term是代表完全匹配，即不进行分词器分析，文档中必须包含整个搜索的词汇
     * <p>
     * toString() 的方法构造的请求：
     * {
     * "bool": {
     * "filter": [  //两个filter
     * {
     * "term": {
     * "multi": {
     * "value": "test",
     * "boost": 1.0
     * }
     * }
     * },
     * {
     * "range": {
     * "age": {
     * "from": 12,
     * "to": 18,
     * "include_lower": true,
     * "include_upper": true,
     * "boost": 1.0
     * }
     * }
     * }
     * ],
     * "disable_coord": false,
     * "adjust_pure_negative": true,
     * "boost": 1.0
     * }
     * }
     * <p>
     * 返回的结果：（age在12-18之间，而且查询的只有严格的multi字段是 test 才可以）
     * {"user":"kiharry4","postDate":"2018-10-21T13:54:42.346Z","message":"tryirch4","age":"15","multi":"test"}
     */
    public void searchTermQuery() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.termQuery("multi", "test"));
        queryBuilder.filter(QueryBuilders.rangeQuery("age").from(12).to(18));

        log.info("search2 : queryBuilder:{} ", queryBuilder.toString());
        SearchResponse response = client.prepareSearch("twitter", "index")
            .setTypes("tweet", "type")
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
            .setPostFilter(queryBuilder)
            .setFrom(0).setSize(60).setExplain(true)
            .get();

        log.info("<search> hits: num:{}", response.getHits().totalHits);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            log.info("<search> hits {}", hit.getSourceAsString());
        }
    }

    /**
     * {
     * "bool": {
     * "filter": [
     * {
     * "range": {
     * "age": {
     * "from": 12,
     * "to": 18,
     * "include_lower": true,
     * "include_upper": true,
     * "boost": 1.0
     * }
     * }
     * },
     * {
     * "match": {
     * "multi": {
     * "query": "test",
     * "operator": "OR",
     * "prefix_length": 0,
     * "max_expansions": 50,
     * "fuzzy_transpositions": true,
     * "lenient": false,
     * "zero_terms_query": "NONE",
     * "boost": 1.0
     * }
     * }
     * }
     * ],
     * "disable_coord": false,
     * "adjust_pure_negative": true,
     * "boost": 1.0
     * }
     * }
     * <p>
     * 返回的结果：（只能是把test完全漏出来才行 testing,atest,均不行，必须要 is a test sd）
     * 1.   {"user":"kiharry4","postDate":"2018-10-21T13:54:42.346Z","message":"tryirch4","age":"15","multi":"test"}
     * 2.   {"user":"kiharry11","postDate":"2018-10-21T14:09:47.270Z","message":"tryirch11","age":"15","multi":"这次可以把test空出来了 test query"}
     * <p>
     * 注意：
     * 对于match搜索，可以按照分词后的分词集合的or或者and进行匹配，默认为or，
     * 这也是为什么我们看到前面的搜索都是只要有一个分词出现在文档中就会被搜索出来，同样的，如果我们希望是所有分词都要出现，那只要把匹配模式改成and就行了
     */
    public void searchMatchQuery() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.rangeQuery("age").from(12).to(18));
        queryBuilder.filter(QueryBuilders.matchQuery("multi", "test"));

        log.info("searchMatchQuery : queryBuilder:{} ", queryBuilder.toString());
        SearchResponse response = client.prepareSearch("twitter", "index")
            .setTypes("tweet", "type")
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
            .setPostFilter(queryBuilder)
            .setFrom(0).setSize(60).setExplain(true)
            .get();

        log.info("<searchMatchQuery> hits: num:{}", response.getHits().totalHits);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            log.info("<searchMatchQuery> hits {}", hit.getSourceAsString());
        }
    }

    /**
     * scroll查询
     * https://blog.csdn.net/SunnyYoona/article/details/52810397?utm_source=blogxgwz1
     * https://blog.csdn.net/feifantiyan/article/details/54096138?utm_source=blogxgwz2
     * http://lxwei.github.io/posts/%E4%BD%BF%E7%94%A8scroll%E5%AE%9E%E7%8E%B0Elasticsearch%E6%95%B0%E6%8D%AE%E9%81%8D%E5%8E%86%E5%92%8C%E6%B7%B1%E5%BA%A6%E5%88%86%E9%A1%B5.html
     * es官方文档：https://www.elastic.co/guide/en/elasticsearch/reference/5.5/search-request-scroll.html
     */
    public void scrollQuery() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.rangeQuery("age").from(12).to(18));
        queryBuilder.filter(QueryBuilders.matchQuery("multi", "test"));

        SearchResponse scrollResp = client.prepareSearch("twitter", "index")
            .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
            .setScroll(new TimeValue(60000))
            .setQuery(queryBuilder)
            .setSize(100).get(); //max of 100 hits will be returned for each scroll

        //Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...
            }

            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        }
        while (scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
    }

    /**
     * IllegalArgumentException[Fielddata is disabled on text fields by default.
     * Set fielddata=true on [age] in order to load fielddata in memory by uninverting the inverted index.
     * Note that this can however use significant memory. Alternatively use a keyword field instead.
     * <p>
     * 1.   用：age.keyword 就可以了
     * 2.   执行如下：
     * PUT megacorp/_mapping/employee/
     * {
     * "properties": {
     * "interests": {
     * "type":     "text",
     * "fielddata": true
     * }
     * }
     * }
     */
    public void agg1() {
        SearchResponse sr = client.prepareSearch("twitter")
            .setTypes("tweet")
            .setQuery(QueryBuilders.matchAllQuery())
            .addAggregation(
                AggregationBuilders.terms("agg1").field("age.keyword")
                    .subAggregation(
                        AggregationBuilders.topHits("top_hit").size(3)
                    )
            ).addAggregation(
                AggregationBuilders.terms("agg2").field("user.keyword")
            )
            .get();

        // Get your facet results
        Terms agg1 = sr.getAggregations().get("agg1");
        List<? extends Terms.Bucket> buckets = agg1.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            log.info("<agg1>:{},count:{} ", bucket.getKeyAsString(), bucket.getDocCount());
        }
//        log.info("<agg1> count:{},max:{},min:{}",agg1.get,agg1.getMax(),agg1.getMin());

        Terms agg2 = sr.getAggregations().get("agg2");
        List<? extends Terms.Bucket> buckets2 = agg2.getBuckets();
        for (Terms.Bucket bucket : buckets2) {
            log.info("<agg2>:{},count:{} ", bucket.getKeyAsString(), bucket.getDocCount());
        }

    }

    /**
     * size 表示的是 返回的数量。
     * 刚开始没有用terms，size是5，返回了5个，按照price1排序（没有的就排在最后）
     * SearchRequestBuilder search = client.prepareSearch("mytest_1").setTypes("test");
     *
     *         TopHitsAggregationBuilder addtion = AggregationBuilders.topHits("top_price_hits").sort("price", SortOrder.DESC).fieldDataField("price")
     *                 .size(5);
     *
     *         SearchResponse sr =search.addAggregation(addtion).execute().actionGet();
     *        TopHits topHits = sr.getAggregations().get("top_price_hits");
     *        System.out.println();
     *         SearchHit[] hits = topHits.getHits().internalHits();
     *         for(SearchHit searchHit : hits) {
     *             System.out.println(searchHit.getSourceAsString());
     *
     * }
     *
     * 后来用了嵌套的模式：https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.5/_metrics_aggregations.html#java-aggs-metrics-tophits，在官网上都有例子
     */
    public void topHitAgg() {
        SearchRequestBuilder search = client.prepareSearch("twitter").setTypes("tweet");
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("price").field("price1")
            .subAggregation(AggregationBuilders.topHits("top_price_hits")
                .sort("price1", SortOrder.DESC).fieldDataField("price")
                .size(2));

        SearchResponse sr = search.addAggregation(termsAggregationBuilder).execute().actionGet();

        Terms price1 = sr.getAggregations().get("price");
        for (Terms.Bucket bucket : price1.getBuckets()) {
            Long key = (Long)bucket.getKey();
            long docCount = bucket.getDocCount();
            log.info("key [{}], doc_count [{}]", key, docCount);

            TopHits top_price_hits = bucket.getAggregations().get("top_price_hits");
            SearchHit[] hits = top_price_hits.getHits().internalHits();
            for (SearchHit searchHit : hits) {
                log.info("<agg2>:{} ", searchHit.getSourceAsString());
            }
        }
    }
}
