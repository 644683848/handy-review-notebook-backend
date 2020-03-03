## 踩坑记录
1. utils中的getCurUsername()方法,User类不能cast成User类.. 

项目启动时加载项目中的类使用的加载器都是 
org.springframework.boot.devtools.restart.classloader.RestartClassLoader 
而从shiro session 取出来的对象（从redis中取出经过反序列化）的类加载器都是 
sun.misc.Launcher.AppClassLoader 
很明显会导致类型转换异常，原来Spring的dev-tools为了实现重新装载class自己实现了一个类加载器，来加载项目中会改变的类，方便重启时将新改动的内容更新进来。

解决办法: 在resource/META_INF中加如spring-devtools.properties这个配置
