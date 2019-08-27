package com.example.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller

public class UserSelectPostController {
    @RequestMapping("/testDemo")

    public static String sendPost(String url, String jsonParams, String encoding) throws ClientProtocolException, IOException, JSONException {
        int statusCode = 0;
        String body = "";
        String resultCode = "";

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        // 创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        StringEntity uefEntity = new StringEntity(jsonParams, encoding);
        uefEntity.setContentType("application/json");

        // 设置参数到请求对象中
        httpPost.setEntity(uefEntity);

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);

        statusCode = response.getStatusLine().getStatusCode();

        if (200 != statusCode)
        {
            resultCode = "-1";
            return resultCode;
        }

        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
            resultCode = getJson(body);
        }
        EntityUtils.consume(entity);

        // 释放链接
        response.close();
        return resultCode;
    }

    //解析json串,获取resultCode值
    private static String getJson(String body) throws JSONException {
        JSONObject obj = new JSONObject(body);

        String resultCode = obj.getString("resultCode");
        return resultCode;
    }

    //post数据访问接口

    @RequestMapping(value = "/userInfo",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public static String testDemo(String usercode) throws JSONException {
        System.out.println(usercode);
        String json=null;
        if(usercode.equals("YN20190360")){
            Map map = new HashMap();
            map.put("username","张天旭");
            map.put("usercode","YN20190360");
            map.put("department","武汉技术开发部");

            //json字符串格式转化
            Gson gson = new Gson();
            json = gson.toJson(map);
        }
        return json;
    }

    //页面加载展示

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
