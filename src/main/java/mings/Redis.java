package mings;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;



public class Redis {
    public static void main(String[] args) {
            Jedis jedis=new Jedis("localhost");
            Article article=new Article();
            article.setAuthor("fs");
            article.setContent("bb");
            article.setTitle("my bb");

            Long articleId=saveArticle(article,jedis);
            System.out.println("保存成功");

            getArticle(jedis,articleId);

            System.out.println("获取成功");


        }

     static Article getArticle(Jedis jedis, Long articleId) {
       String article=jedis.get("post:" +articleId+ "data");
       Article article1= JSON.parseObject(article,Article.class);

        return article1;
    }

      static Long saveArticle(Article article,Jedis jedis) {
        Long articles=jedis.incr("articles");
       String articleStr = JSON.toJSONString(article);
       jedis.set("article:"+articles+ "data",articleStr);
       return articles;
    }
     static void addArticle(Article article,Jedis jedis){
        Long articleid =jedis.incr("article");
        String Article=JSON.toJSONString(article);
        String articles=article.getAuthor()+articleid+article.getContent();
        jedis.set(articles, Article);
     }
     static void delArticle(String articleTitle,Jedis jedis){
        jedis.del(articleTitle);
     }
     static  void uddateAuthor(Article article,String articleTitle,Jedis jedis){
        article.setAuthor("fs");
        String article1=JSON.toJSONString(article);
        jedis.set(articleTitle,article1);
     }

}
