package com.hcq.hcqmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.hcq.hcqmall.common.to.es.SkuEsModel;
import com.hcq.hcqmall.search.config.hcqmallElasticSearchConfig;
import com.hcq.hcqmall.search.constant.EsConstant;
import com.hcq.hcqmall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author huchangqi
 */
@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws Exception {
        // 保存到es


        // 1 给es中建立索引，product
        BulkRequest bulkRequest = new BulkRequest();
        // 1 构造保存请求
        for (SkuEsModel model : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, hcqmallElasticSearchConfig.COMMON_OPTIONS);
        // TODO 如果批量错误
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架完成,{},返回数据,{}",collect,bulk.toString());

        return  b;
    }
}
