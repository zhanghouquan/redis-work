package mings;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class Hset {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Article article=new Article();
        article.setAuthor("fs");
        article.setContent("bb");
        article.setTitle("my bb");

        Long articleId=saveArticle(article,jedis);
        System.out.println("保存成功");

        Article myArticle=getArticle(articleId,jedis);
        System.out.println(myArticle);

        System.out.println("获取成功");

       UpTitle(jedis,articleId);


    }

     static void UpTitle(Jedis jedis, Long articleId) {
         Map<String,String> getMap = jedis.hgetAll("articles:"+articleId+":data");
         getMap.put("Title","新标题");
         jedis.hmset("articles:"+articleId+":data",getMap);
         System.out.println(jedis.hgetAll("article"+articleId+":date"));
         System.out.println("修改完成");
    }



    static Article getArticle(Long articleId,Jedis jedis) {
         Map<String,String> myBlog=jedis.hgetAll("article"+articleId+":date");
        System.out.println(myBlog);
         Article article1=new Article();
             article1.setTitle(myBlog.get("title"));
             article1.setAuthor(myBlog.get("author"));
             article1.setContent(myBlog.get("content"));
        System.out.println(article1);
         return article1;
    }

     static Long saveArticle(Article article, Jedis jedis) {
         Long articleId =jedis.incr("articles");
         Map<String,String> blog=new HashMap<String, String>();
         blog.put("title",article.getTitle());
         blog.put("author",article.getAuthor());
         blog.put("content",article.getContent());
         jedis.hmset("article"+articleId+":date",blog);
         System.out.println(jedis.hgetAll("article"+articleId+":date"));
         return articleId;
    }

    static void delArticle(long articleid,Jedis jedis){
        jedis.hdel("Article:"+articleid,"author");
        jedis.lrem("list:article",1,"Article:"+articleid);
    }

}
