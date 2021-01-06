# mybatis-abu
mybatis 扩展组件，可以在 insert 和 update 时对公共字段统一修改，减少不必要的重复代码。

## Use
基于 mybatis 插件机制，所以只要了解 mybatis plugin 如何配置，就能很快上手。
> 实例代码以 SpringBoot 为例
- 注册插件并进行配置
```java
    /**
     * 注入 Mybatis 自定义插件
     *
     * @return 自定义插件实例
     */
    @Bean
    public AbuInterceptor interceptor() {
        BindDataConfig bindDataConfig = new BindDataConfig();
        bindDataConfig.add("id", UniqueIdUtil.nextId(), BindScene.INSERT);
        bindDataConfig.add("createBy", SecurityUtil.getLoginUser().getUsername(), BindScene.INSERT);
        bindDataConfig.add("createTime", System.currentTimeMillis(), BindScene.INSERT);
        bindDataConfig.add("updateBy", SecurityUtil.getLoginUser().getUsername(), BindScene.INSERT, BindScene.UPDATE);
        bindDataConfig.add("updateTime", System.currentTimeMillis(), BindScene.INSERT, BindScene.UPDATE);
        bindDataConfig.add("delete", false, BindScene.INSERT, BindScene.UPDATE);
        return new AbuInterceptor();
    }
```
## Config
- `BindDataConfig.add()`参数说明
    - `String column`期望自动绑定数据的字段，该参数为实体类中对应的属性名
    - `Supplier supplier`自动绑定字段生成逻辑，可以为具体方法或具体值
    - `BindScene[] scene`在什么场景下自动绑定
        - `BindScene.INSERT`insert 语句时绑定
        - `BindScene.UPDATE`update 语句时绑定
    
## License
MIT
