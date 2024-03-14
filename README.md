#### 1.概述
目前线上运行版本的是带IP认证，没有集成Redis以及Security，在git中加入两个后端版本，其中一个为带IP认证以及已经完成缓存工作，配置Redis服务器即可使用，另一版本加入了权限认证，
由于SpringBoot的IP认证服务与Security不兼容，暂未解决，可以使用Security的IP授权代替该功能，并且该版本暂未在数据库中配置所有接口及用户权限，需完成该部分才可使用。另外已对之
前返回值进行了修改，对错误的边界值进行处理，使用时需注意对前端进行部分修改。（使用oauth2来取代Security进行认证）

#### 2. 项目结构
项目使用框架包括：Springboot、MyBatis、SpringMVC、SpringSecurity
缓存采用Redis

![Image text](../img/temp1.jpg)  
1. Config：SpringSecurity配置类
2. Controller：接口
3. Domain：实体类
4. Exception: 捕获全局异常并抛出
5. Mapper: 数据访问层
6. Redis：缓存
7. Result：定义返回值
8. Security：定义权限认证成功或失败返回类型
9. Service：服务层
10.Utils：工具类
11.Vo：请求类

#### 3. config
主要完成以下工作：
1.	当用户匿名访问时，除登录登出接口可访问外，访问其他接口时抛出403没有权限错误。
2.	登出成功处理逻辑
3.	登录成功处理逻辑
4.	登录失败处理逻辑
5.	访问决策管理器：当用户每次请求接口数据时，获取当前用户权限并与数据库中权限进行对比，若没有权限则抛出403没有权限错误。
![Image text](../img/temp2.jpg) 

#### 4. security
Handle包：定义上述几种处理逻辑，重写UserDetailService。

![Image text](../img/temp3.jpg)

目前未完成部分：仅对DeviceController中device/list及device/findFailure中两个接口做了权限管理，暂未对其他接口做管理，若不走网关，只使用后端集成Security，还需在数据库中配置其他接口权限。

##### 4.1 security数据库配置
1. 将文件夹下权限认证.sql导入数据库
2. 根据需要添加角色，可分为管理员，普通用户，游客等角色

![Image text](../imges/角色.jpg)

3. 将用户与角色关系关联起来，用户与角色关系为多对多，一种用户可以包括多种角色，一个角色可以对应多个用户

![Image text](../imges/用户-角色.jpg)

4. 设置权限，例如查看设备，修改设备信息，下发命令等权限

![Image text](../imges/权限.jpg)

5. 将角色与权限关联起来，即允许每个角色拥有哪些权限。

![Image text](../imges/角色权限.jpg)

6. 将请求路径加入数据库中

![Image text](../imges/请求路径.jpg)

7. 将请求路径url与权限关联起来，给url户分配权限。

![Image text](../imges/用户-权限.jpg)

8. 权限认证主要逻辑

    1.首先，判断用户是否登录，若未登录，提醒用户登录；
    
    2.获取当前请求需要的权限；
    
    3.获取当前用户具备的权限；
    
    4.将当前用户具备权限与当前请求需要权限进行匹配；
    
    5.若匹配成功，则放行，否则提示用户不具备该权限。

#### 5. controller
定义接口，详情见后端接口文档

#### 6. domain
定义实体类，与数据库中表字段对应

#### 7. exception
GlobleException：定义全局异常
GlobleException：捕获全局异常，并做返回值返回给前端。

![Image text](../img/temp4.jpg)

#### 8. mapper
数据访问层，访问数据库

#### 9. redis
缓存，***Key结尾类为定义存入redis中的key值，使其保证key的唯一性。对User、DeviceList、DeviceEvent、DeviceFailure、DeviceHistory、DeviceWarning、Command、Response进行缓存，当前缓存时间为一小时。
注意：若需更改缓存时间，修改expireSeconds值即可，运行时需更换Redis服务器IP及密码。

![Image text](../img/temp6.jpg)

![Image text](../img/temp7.jpg)

![Image text](../img/temp8.jpg)

#### 10. utils
1.	ExcelUtils：导出表格的工具类
2.	HexToBinaryStr：二进制、十六进制转换工具类
3.	HttpClientUtils：下发命令时工具类
4.	IPUtils：获取用户当前真实IP工具类
5.	PageBean：分页工具类
6.	ThingBoardJWTTokenTelemetryValue：获取TB JWT，以及使用JWT获取遥测数据工具类
7.	TimeCovert：时间转换工具类
8.	UUIDUtils：获取随机数

![Image text](../img/temp9.jpg)

#### 11. 项目运行
导入Jar包，运行主函数即可

#### 12. 项目部署
方法一、打包jar包，通过终端运行命令 nohup java –jar xxx.jar &即可，日志输出再nohup.out文件中。。
方法二、docker部署：
1.	设置DockerFile文件

![Image text](../img/temp12.jpg)

2.	配置docker

![Image text](../img/temp13.jpg)

![Image text](../img/temp14.jpg)

3.	连接docker

![Image text](../img/temp15.jpg)

4.	运行

![Image text](../img/temp16.jpg)

5.	通过接口测试或查看服务器即可测试是否部署成功。

#### 13. 项目部署服务器IP
项目部署服务器IP：47.103.121.83