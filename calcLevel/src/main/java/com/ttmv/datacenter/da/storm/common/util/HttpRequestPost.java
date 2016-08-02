package com.ttmv.datacenter.da.storm.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by zbs on 15/10/20.
 */
public class HttpRequestPost {

	private static Logger logger = LogManager.getLogger(HttpRequestPost.class);

    private String url;
    
    public HttpRequestPost(String url){
    	this.url = url;
    }

    private URLConnection initConfig(){
    	URLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //超时为1秒
            conn.setConnectTimeout(1000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
        }catch (Exception e){
            logger.error("初始化HttpResponse对象失败。");
        }
        return conn;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String param) throws Exception {
    	URLConnection conn = initConfig();
        if(conn == null)
            throw new Exception("HTTP配置错误，未能成功建立HTTP连接。");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try{
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            logger.info("[HTTP请求] ==> URL="+url+" 发送数据="+param);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw new Exception("发送 POST 请求出现异常！"+e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        logger.info("[HTTP请求] ==> 收到数据"+result);
        return result;
    }

}
