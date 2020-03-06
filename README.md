## 来不及解决的问题
1. utils/shiro/realm/UserRealm中, 如果一个请求没有携带认证头, 那么不管它是不是登录请求, 有没有验证用户名密码, 
都会往Redis中存储SessionID
2. 为什么@CrossOrigin不起作用, 但加上CorsConfig就没问题了.

## 踩坑记录
1. utils中的getCurUsername()方法,User类不能cast成User类.. 

项目启动时加载项目中的类使用的加载器都是 
org.springframework.boot.devtools.restart.classloader.RestartClassLoader 
而从shiro session 取出来的对象（从redis中取出经过反序列化）的类加载器都是 
sun.misc.Launcher.AppClassLoader 
很明显会导致类型转换异常，原来Spring的dev-tools为了实现重新装载class自己实现了一个类加载器，来加载项目中会改变的类，方便重启时将新改动的内容更新进来。

解决办法: 在resource/META_INF中加如spring-devtools.properties这个配置
