package com.mszlu.blog.rabbitmqconsumer;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.dao.pojo.ArticleMessage;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@Slf4j
@RabbitListener(queues = {"article.queue"})
public class ArticleListener {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ArticleService articleService;

    @RabbitHandler
    public void onMessage(ArticleMessage message) {
        log.info("文章的id------->{}", message.getArticleId());
        String articleId = String.valueOf(message.getArticleId());
        String md5 = DigestUtils.md5Hex(articleId);
        String redisKey = "view_article::ArticleController::findArticleById::" + md5;
        Result article = articleService.findArticleById(Long.valueOf(articleId));
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(article), Duration.ofMillis(5 * 60 * 1000));
        log.info("更新了缓存:{}", redisKey);

        Set<String> keys = redisTemplate.keys("listArticle*");
        keys.forEach(key -> {
            redisTemplate.delete(key);
            log.info("删除了缓存:{}", key);
        });

    }
}
