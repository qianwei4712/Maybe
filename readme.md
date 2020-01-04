# 路的尽头在哪
  
  > 业务时间做的玩具，面向学习型基础框架
  >
  > 作者：shiva                           
  >
  > 项目名：maybe


**从零开始搭建，代码尚未经受项目考验，估计漏洞百出，极力完善中ing**
 
  ### 开始使用
  1. 创建数据库，sql位于doc文件下，更改 maybe-web/application.yml 数据库名
  2. 后台访问链接：localhost/manage

  
  ### 项目简介：
  - 基于SpringBoot 2进行开发
  - mybatis，基础CRUD方法，PageHelper分页插件
  - shiro权限系统，照例使用用户，角色，权限这一套
  - 后台管理界面使用`layui`
  - 涉及到的插件和服务包括：`javamail`，`ztree`
  
  
  ### 使用简介
  
- maybe
  - base-common 工具包模块
  - base-core 核心模块，用户、角色、部门、菜单都在这里
    - java 核心代码
      - core 基础类，配置类，常量
        - base 基础类（BaseEntity、BaseService等）
        - basic 基础类（基础返回格式，Page、Resp封装）
        - config 项目配置类（例如：分页，mvc时间格式转化，shiro配置）
        - constant 项目中使用的配置常量（不包含业务常量，业务常量一般都在各自对应的包）
        - interceptor 拦截器（日志拦截器配置）
        - utils 项目工具类（缓存工具，默认用户工具，spring ApplicationContext工具等）
      - system 核心模块代码
    - resources 配置和页面模板
      - config 缓存，mybatis分页、springboot配置
      - static 静态资源
      - templates 页面模板
  - base-generate 基于freemarker开发的代码基础代码生成器，个人习惯
    - java
      - core 核心配置
      - Generate 代码生成器启动类
    - resources
      - freemakerTpl 代码生成器模板
  - doc sql和一些文档，作者自己查询用的，后续完善
  - maybe-web 业务模块，随意添加
   
  