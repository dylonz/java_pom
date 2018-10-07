package com.dylonz.shop.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

//	@Autowired
//	private SolrClient solrClient;
//
//	//添加索引库
//	@Test
//	public void solrAdd(){
//		for(int i=0;i<100;i++){
//			SolrInputDocument solrInputDocument=new SolrInputDocument();
//			solrInputDocument.addField("id",i);
//			solrInputDocument.addField("gtitle","小米手机，燃烧中的小米手机，小米便宜又耐摔，小米冬天可以取暖"+i);
//			solrInputDocument.addField("ginfo","为发烧而生"+i);
//			solrInputDocument.addField("gprice","999"+i);
//			try {
//				solrClient.add(solrInputDocument);
//			} catch (SolrServerException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//
//		try {
//			solrClient.commit();
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	//删除索引库
//	@Test
//	public void solrDel() throws IOException, SolrServerException {
//		//根据id删除索引库
//		solrClient.deleteById("2");
//
//		solrClient.commit();
//	}
//
//	//搜索索引库
//	@Test
//	public void solrQuery() throws IOException, SolrServerException {
//		SolrQuery solrQuery=new SolrQuery();
//		solrQuery.setQuery("gtitle:小米");
//
//		QueryResponse response = solrClient.query(solrQuery);
//		SolrDocumentList results = response.getResults();
//		for(SolrDocument solrDocument:results){
//			String id = (String) solrDocument.getFieldValue("id");
//			String gtitle = (String) solrDocument.getFieldValue("gtitle");
//			Float gprice = (Float) solrDocument.getFieldValue("gprice");
//			System.out.println(id);
//			System.out.println(gtitle);
//			System.out.println(gprice);
//			System.out.println("----------");
//		}
//	}

}
