package com.springmvc.lxy.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述: es 练习类：从QueryBuilder开始
 * <p>
 *
 * @author: harry
 * @date: 2018-10-23
 **/
public class EsClient2 extends Client{

    private static final Logger log = LoggerFactory.getLogger(EsClient2.class);

    public static void main(String[] args) {
//        matchQueryTest();

        BoolQueryTest1();
        close();
    }

    /**
     * 查出三条来：只要带有单独一个test的就行
     * {"user":"kiharry4","postDate":"2018-10-21T13:54:42.346Z","message":"tryirch4","age":"15","multi":"test"}
     * {"user":"kiharry4","postDate":"2018-10-21T13:51:36.736Z","message":"tryirch4","age":"11","multi":"test is 4"}
     * {"user":"kiharry11","postDate":"2018-10-22T16:00:20.632Z","message":"tryirch11","age":"15","multi":"这次可以把test空出来了 test query","price1":11}
     *
     * 用"轻易"查：
     * {"name":"刘旭阳","gender":"sdfadfsadfasdf","message":"长春市长春药店可能是真的假的","age":"45","multi":"这些都是宝贵的test数据，不可以轻易的query","price1":22}
     *
     */
    private static void matchQueryTest() {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("multi", "轻易");

        SearchResponse response = client.prepareSearch("twitter", "index")
            .setTypes("tweet", "type")
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
            .setQuery(queryBuilder)
            .setFrom(0).setSize(60).setExplain(true)
            .get();

        log.info("<search> hits: num:{}", response.getHits().totalHits);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            log.info("<search> hits {}", hit.getSourceAsString());
        }
    }


    /**
     * eg1：user字段必须有长安街，有刘旭阳更好。
     * BoolQueryBuilder qb = QueryBuilders.boolQuery()
     *             .must(QueryBuilders.matchQuery("user", "长安街"))
     *             .should(QueryBuilders.matchQuery("user", "刘旭阳"));
     * 结果：{"user":"刘旭阳 武首昭 住宿绝不仅限于住宿 旅馆 特殊的享受 安静 文艺卫生 高级商务","gender":"sdfadfsadfasdf","message":"长春市长春药店可能是真的假的","age":"45","multi":"这些都是宝贵的test数据，不可以轻易的query","price1":56}
     * 大概率他会取一个最相关的。
     *
     * eg2：
     * BoolQueryBuilder qb = QueryBuilders.boolQuery()
     *             .must(QueryBuilders.matchQuery("user", "拉倒"))
     *             .should(QueryBuilders.matchQuery("user", "沙滩"));
     * 结果：空的
     *
     * boolquery 也不是会非要取一个默认的给你返回，但是他会想办法取一个相关的
     */
    private static void BoolQueryTest1() {
        BoolQueryBuilder qb = QueryBuilders.boolQuery()
            .must(QueryBuilders.matchQuery("user", "长安街"))
            .should(QueryBuilders.matchQuery("user", "没毛病"));

        log.info("<BoolQueryTest1> qb：{}",qb.toString());
        SearchResponse response = client.prepareSearch("twitter")
            .setTypes("tweet")
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
            .setQuery(qb)
//            .setFrom(0).setSize(60).setExplain(true)
            .get();

        log.info("<BoolQueryTest1> hits: num:{}", response.getHits().totalHits);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            log.info("<BoolQueryTest1> hits {}", hit.getSourceAsString());
        }
    }
}
