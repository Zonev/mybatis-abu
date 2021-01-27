# mybatis-abu

[![MIT](https://img.shields.io/badge/lincense-MIT-green)](https://github.com/Zonev/mybatis-abu/blob/master/LICENSE) 
[![maven central](https://img.shields.io/badge/maven-1.1-green)](https://mvnrepository.com/artifact/com.github.zonev/abu) 
[![jdk](https://img.shields.io/badge/jdk-1.8%2B-red)](https://img.shields.io/badge/jdk-1.8%2B-red)
[![mybatis](https://img.shields.io/badge/mybatis-3.0.0%2B-red)](https://mybatis.org/mybatis-3/zh/index.html)

`mybatis`扩展插件，可以在`insert`或`update`时对公共字段统一修改，减少不必要的重复代码。

- 功能纯粹，非大而全
- 零注解
- 易操作易上手，源码简单易于理解和二次改造
- 基于`mybatis 3`插件机制，可与其他插件混用，只需规划执行顺序即可

# 局限性
1. 只支持具有`BaseModel`等类似基类的实体类使用
2. 只支持单一实体类参数或者单一实现`Collection<?>`接口的集合参数

# 使用

1. 引入 Jar

   ```xml
   <!-- https://mvnrepository.com/artifact/com.github.zonev/abu -->
   <dependency>
       <groupId>com.github.zonev</groupId>
       <artifactId>abu</artifactId>
       <version>1.1</version>
   </dependency>
   ```

2. 配置需要自动绑定的字段以及实体类基类

   ```java
   FieldBindConfig.builder()
          .bind("id", CommonFieldUtils::nextId, Long.class, SqlCommandType.INSERT)
          .bind("createTime", CommonFieldUtils::time, Long.class, SqlCommandType.INSERT)
          .bind("updateTime", CommonFieldUtils::time, Long.class, SqlCommandType.INSERT, SqlCommandType.UPDATE)
          .bind("delete", false, Boolean.class, SqlCommandType.INSERT, SqlCommandType.UPDATE)
          .baseModel(BaseDTO.class);
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
   public void save() {
        // service 层在做新增或更新操作时，只需关注业务逻辑字段
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

   