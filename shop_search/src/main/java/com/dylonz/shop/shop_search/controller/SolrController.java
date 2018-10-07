package com.dylonz.shop.shop_search.controller;

import com.dylonz.entity.Goods;
import com.dylonz.entity.SolrPage;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/solr")
public class SolrController {

    @Autowired
    private SolrClient solrClient;

    @Value("${image.path}")
    private String path;

    //在添加商品时添加索引库
    @RequestMapping("/add")
    @ResponseBody
    public boolean solrAdd(@RequestBody Goods goods){

        SolrInputDocument solrDocument=new SolrInputDocument();
        solrDocument.addField("id",goods.getId());
        solrDocument.addField("gtitle",goods.getTitle());
        solrDocument.addField("ginfo",goods.getGinfo());
        solrDocument.addField("gprice",goods.getPrice());
        solrDocument.addField("gimage",goods.getGimage());

        try {
            solrClient.add(solrDocument);
            solrClient.commit();
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //查询索引库
    @RequestMapping("/query")
    public String query(String keyword, Model model, SolrPage<Goods> solrPage){
        // System.out.println("关键字"+keyword);
        SolrQuery solrQuery=new SolrQuery();

        if(keyword==null || keyword.trim().equals("")){
            solrQuery.setQuery("*:*");
        }else {
            solrQuery.setQuery("goods_info:" + keyword);
        }

        //设置搜索的高亮
        solrQuery.setHighlight(true);

        //设置高亮的前缀和后缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //设置需要高亮的字段
        solrQuery.addHighlightField("gtitle");

        //设置高亮的折叠
        solrQuery.setHighlightSnippets(4); //摘要分成几部分
        solrQuery.setHighlightFragsize(7); //每部分的长度

        //设置分页 limit ? ,?
        solrQuery.setStart((solrPage.getCurrentPage()-1)*(solrPage.getPageSize()));
        solrQuery.setRows(solrPage.getPageSize());

       List<Goods> list=new ArrayList<Goods>();
        try {
            QueryResponse query = solrClient.query(solrQuery);

            //获得高亮的结果
            Map<String, Map<String, List<String>>> map = query.getHighlighting();
            for(Map.Entry<String,Map<String,List<String>>> map1:map.entrySet()){
                System.out.println("key"+ map1.getKey());
                System.out.println("value"+map1.getValue());
                System.out.println("---------");
            }

            //获得普通搜索结果
            SolrDocumentList results = query.getResults();

            //获取总条数，设置总条数和总页码
            long totalCount = results.getNumFound();
            solrPage.setTotalCount((int) totalCount);
            solrPage.setTotalPage(solrPage.getTotalCount()%solrPage.getPageSize()==0
            ?solrPage.getTotalCount()/solrPage.getPageSize():
            solrPage.getTotalCount()/solrPage.getPageSize()+1);

            for (SolrDocument solrDocument : results) {
                Goods goods=new Goods();
                goods.setId(Integer.parseInt(solrDocument.getFieldValue("id")+""));
                goods.setTitle(solrDocument.getFieldValue("gtitle")+"");
                goods.setPrice(Float.parseFloat(solrDocument.getFieldValue("gprice")+""));
                goods.setGinfo(solrDocument.getFieldValue("ginfo")+"");
                goods.setGimage(solrDocument.getFieldValue("gimage")+"");

                //处理高亮的内容
                if(map.containsKey(goods.getId()+"")){
                    //说明当前商品有高亮的信息
                    List<String> gtitle = map.get(goods.getId() + "").get("gtitle");
                    if(gtitle!=null){
                        String strs="";
                        for (String str : gtitle) {
                            strs+=str+"...";
                            goods.setTitle(strs);
                        }
                    }
                }

                list.add(goods);
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查询结果放到分页对象中
        solrPage.setDatas(list);
        model.addAttribute("page",solrPage);
        model.addAttribute("keyword",keyword);
        model.addAttribute("path",path);
        System.out.println("商品详情"+list);
        return "searchlist";
    }
}
