# mybatis-abu

![MIT](https://img.shields.io/badge/lincense-MIT-green) ![maven](https://img.shields.io/badge/maven-1.0-green) ![jdk](https://img.shields.io/badge/jdk-jdk1.8%2B-red) ![mybatis](https://img.shields.io/badge/mybatis-3.0.0%2B-red)

mybatis 扩展插件，可以在 insert 或 update 时对公共字段统一修改，减少不必要的重复代码。

- 功能纯粹，非大而全
- 零注解
- 易操作易上手，源码简单易于理解和二次改造
- 基于 mybatis 3 插件机制，可与其他插件混用，只需规划执行顺序即可

# 使用

1. 引入 Jar

   ```xml
   <!-- https://mvnrepository.com/artifact/com.github.zonev/abu -->
   <dependency>
       <groupId>com.github.zonev</groupId>
       <artifactId>abu</artifactId>
       <version>1.0</version>
   </dependency>
   ```

2. 配置需要自动绑定的字段

   ```java
   // 实例化配置类
   BindDataConfig bindDataConfig = new BindDataConfig();
   // 添加配置信息
   bindDataConfig
    // 绑定属性为 "id"，生成值的方法为 "IdUtils.nextId()"，生效场景为 "insert SQL"
    // 重要：期望绑定的属性为实体类对应的属性值而不是数据库中对应的字段名
    .add("id", IdUtils.nextId(), BindScene.INSERT)
    .add("createBy", AuthUtils.getUsersname(), BindScene.INSERT, BindScene.UPDATE)
    .add("updateTime", System.currentTimeMillis(), BindScene.UPDATE)
    .add("deleteFlag", "false", BindScene.INSERT, BindScene.UPDATE);
   ```

3. 注册 mybatis-abu 插件（以 SpringBoot 为例）

   ```java
   /**
    * 注入 Mybatis 自定义插件
    *
    * @return 自定义插件实例
    */
   @Bean
   public AbuInterceptor interceptor() {
        return new AbuInterceptor();
   }
   ```

4. 业务代码示例

   ```java
   @Autowired
   private AreaDao areaDao;
   
   @Override
   ublic void save() {
        // service 层在做新增或更新操作时，只需关注业务逻辑字段，不需要 set 公共字段
        Area area = new Area();
        area.setAreaName("杭州");
        area.setAreaNum("330100");
        area.setAreaDes("浙江省会城市");
        areaDao.insert(area);
   }
   ```

   ```xml
   <insert id="insert" parameterType="Area">
       insert into t_area 
       (id, area_name, area_num, area_des, create_by, is_delete)
       values 
       (#{id}, #{areaName}, #{areaNum}, #{areaDes}, #{createBy}, #{delete})
   </insert>
   ```

   