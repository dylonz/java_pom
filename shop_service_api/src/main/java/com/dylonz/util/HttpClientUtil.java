package com.dylonz.util;

import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtil {

    //httpclient工具方法 - 传递json字符串
    public static String sendJsonPost(String url,String json){
        //1.获得httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //2.post请求
        HttpPost post=new HttpPost(url);

        //3.在请求头设置content_type和响应方式
        post.setHeader(new BasicHeader("content-type","application/json"));
        post.setEntity(new StringEntity(json,"utf-8"));

        try {
            //4.执行post请求
            CloseableHttpResponse response = httpClient.execute(post);
            //5.获取响应体
            HttpEntity entity = response.getEntity();
            String responseStr = EntityUtils.toString(entity);
            return  responseStr;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //发送post请求，并且自由设置参数和请求头
    public static String sendPostParamsAndHeader(String url, Map<String,String> params,Map<String,String> header){
        //1.获得httpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try{
            //2.设置post请求
            HttpPost httpPost = new HttpPost(url);

            //设置请求头
            Set<Map.Entry<String, String>> entrySet = header.entrySet();
            for (Map.Entry<String, String> set:entrySet){
                System.out.println(set.getKey()+"=head="+set.getValue());
                httpPost.addHeader(set.getKey(),set.getValue());
            }

            //设置请求体
            List<NameValuePair> pairs=new ArrayList<>();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            if(params!=null){
                for(Map.Entry<String, String> entry:entries){
                    pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairs,"utf-8"));

            //3.发送post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            //4.获得响应体
            HttpEntity entity = response.getEntity();
            String responseStr = EntityUtils.toString(entity);
            return  responseStr;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
       //1.获得httpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //2.get
        HttpGet get=new HttpGet("http://www.baidu.com");
        //3.执行get请求
        CloseableHttpResponse response = httpClient.execute(get);
        //4.获得响应体
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        int len;
        byte[] b=new byte[1024 * 10];
        while ((len=is.read(b))!=-1){
            bos.write(b,0,len);
        }
        byte[] bytes = bos.toByteArray();
        System.out.println("响应内容为"+new String(bytes,"utf-8"));

        is.close();
        bos.close();
     }
}
