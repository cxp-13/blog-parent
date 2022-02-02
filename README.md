# 我的博客
#### 介绍
B站UP主-码神之路:博客项目  
时间从2021年10月到2022年1月
#### 软件架构
前端vue  
后端spring—boot  
数据库mybatis,mybatis-plus
#### 学习到的知识
* threadlocal 
> ThreadLocal叫做线程变量，意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的，也就是说该变量是当前线程独有的变量。ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
  ```java
  public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
  }
  ```
###应用
1. 在LoginInterceptor中存入sysUser对象。
2. 在ArticleServiceImpl中的publish方法中拿对象。

* jwt
>JWT(JSON Web Token)是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准。
```java
public class JWTUtils {

    private static final String jwtToken = "123456Mszlu!@#$$";

    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间（其实应该是60天）
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
```
###应用
1. 在LoginServiceImpl中的登录和注册生成token,注销删除token
2. LoginInterceptor中`String token = request.getHeader("Authorization"); `
调用checkToken验证token的有效性
* 自定义注解 + AOP切面

```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 60 * 1000;

    String name() default "";

}
```
####AOP使用
@Aspect来注明切面类
`@Pointcut("@annotation(com.mszlu.blog.common.cache.Cache)")
public void pt() {
}`  
定义切点,指定Cache注解来标记需要切面的地方。  
`@Around("pt()")`  
around环绕处理,ProceedingJoinPoint类可以通过反射拿到切入点的信息。
将信息作为键,返回的结果。例如文章信息json化作为值,存入redis。
###应用
1. ArticleController中的listArticle和hotArticle.将方法返回的结果存入redis缓存  
2. 问题-->会出现浏览量和编辑(未实现)不能及时更新
#### 安装教程
1. maven指定prod配置,package成jar包
2. Linux安装docker
3. 拉取jdk,redis,mysql,nginx
4. 基于jdk编写dockerfile构建镜像app
5. 再编写docker-compose将镜像app和nginx联合启动
#### 使用说明
1.  redis和mysql的连接端口和ip地址以Linux虚拟机上的为准
2.  如果使用阿里云,请提前开放安全组端口和Linux主机防火墙端口
3.  .sql文件如果是用navicat生成的，创表时是使用utf8mb4或者3编码。会造成数据库数据是正常显示，但是在网页上是乱码。应该使用qq群百度网盘up主提供的.sql文件它是使用utf8编码来创表的。
#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
