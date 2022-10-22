# 工程简介

**freemarker入门Demo**

导入依赖
```java
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
```

编写application.yml
```yml
server:
  port: 8881 #服务端口
spring:
  application:
    name: freemarker-demo #指定服务名
  freemarker:
    cache: false  #关闭模板缓存，方便测试
    settings:
      template_update_delay: 0 #检查模板更新延迟时间，设置为0表示立即检查，如果时间大于0会有缓存不方便进行模板测试
    suffix: .ftl               #指定Freemarker模板文件的后缀名
    template-loader-path: classpath:/templates
```
# 延伸阅读

​	FreeMarker 是一款 模板引擎： 即一种基于模板和要改变的数据， 并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。 
它不是面向最终用户的，而是一个Java类库，是一款程序员可以嵌入他们所开发产品的组件。

​	模板编写为FreeMarker Template Language (FTL)。它是简单的，专用的语言， *不是* 像PHP那样成熟的编程语言。 那就意味着要准备数据在真实编程语言中来显示，比如数据库查询和业务运算， 之后模板显示已经准备好的数据。在模板中，你可以专注于如何展现数据， 而在模板之外可以专注于要展示什么数据。 


# 基础语法种类

1、注释，即<#--  -->，介于其之间的内容会被freemarker忽略

```velocity
<#--我是一个freemarker注释-->
```

2、插值（Interpolation）：即 **`${..}`** 部分,freemarker会用真实的值代替**`${..}`**

```velocity
Hello ${name}
```

3、FTL指令：和HTML标记类似，名字前加#予以区分，Freemarker会解析标签中的表达式或逻辑。

```velocity
<# >FTL指令</#> 
```

4、文本，仅文本信息，这些不是freemarker的注释、插值、FTL指令的内容会被freemarker忽略解析，直接输出内容。

```velocity
<#--freemarker中的普通文本-->
我是一个普通的文本
```

### 



# 遍历List集合

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
    <table>
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>钱包</td>
        </tr>
        <#list stus as stu>
            <tr>
                <td>${stu_index+1}</td>
                <td>${stu.name}</td>
                <td>${stu.age}</td>
                <td>${stu.money}</td>
            </tr>
        </#list>

    </table>

</body>
</html>
```

```java
@Test
    public void testList() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("01-list.ftl");
        Map params = getData();

        //封装list存放所有学生
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName("小明");
        student.setAge(18);
        student.setMoney(20.0f);
        students.add(student);

        Student student2 = new Student();
        student2.setName("小红");
        student2.setAge(18);
        student2.setMoney(30.0f);
        students.add(student2);

        params.put("stus",students);

        template.process(params, new FileWriter("d:/test.html"));
    }
```

# 获取map中的数据

实例代码：

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
<#-- Map 数据的展示 -->
<b>map数据的展示：</b>
<br/><br/>
<a href="###">方式一：通过map['keyname'].property</a><br/>
输出stu1的学生信息：<br/>
姓名：${stuMap['stu1'].name}<br/>
年龄：${stuMap['stu1'].age}<br/>
<br/>
<a href="###">方式二：通过map.keyname.property</a><br/>
输出stu2的学生信息：<br/>
姓名：${stuMap.stu2.name}<br/>
年龄：${stuMap.stu2.age}<br/>

<br/>
<a href="###">遍历map中两个学生信息：</a><br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stuMap?keys as key >
        <tr>
            <td>${key_index + 1}</td>
            <td>${stuMap[key].name}</td>
            <td>${stuMap[key].age}</td>
            <td>${stuMap[key].money}</td>
        </tr>
    </#list>
</table>
<hr>

</body>
</html>
```

👆上面代码解释：

${k_index}：
index：得到循环的下标，使用方法是在key后边加"_index"，它的值是从0开始



java代码：

```java
@Test
public void testMap() throws IOException, TemplateException {
    //freemarker的模板对象，获取模板
    Template template = configuration.getTemplate("02-map.ftl");
    Map params = new HashMap();

    //封装list存放所有学生
    Map students = new HashMap<>();
    Student student = new Student();
    student.setName("小明");
    student.setAge(18);
    student.setMoney(20.0f);
    students.put("stu1",student);

    Student student2 = new Student();
    student2.setName("小红");
    student2.setAge(18);
    student2.setMoney(30.0f);
    students.put("stu2",student2);

    params.put("stuMap",students);

    template.process(params, new FileWriter("d:/test.html"));
}
```



# if指令

​	 if 指令即判断指令，是常用的FTL指令，freemarker在解析时遇到if会进行判断，条件为真则输出if中间的内容，否则跳过内容不再输出。

- 指令格式

```html
<#if ></if>


示例：
<#if name='wangwu'>

<#elseif name='zhangsan'>

<#elseif name='lisi'>

<#else>
```

1、数据模型：

使用list指令中测试数据模型，判断名称为小红的数据字体显示为红色，小蓝则为蓝色。

```java
@Test
public void testIf() throws IOException, TemplateException {
    //freemarker的模板对象，获取模板
    Template template = configuration.getTemplate("03-if.ftl");
    Map params = new HashMap();

    //封装list存放所有学生
    List<Student> students = new ArrayList<>();
    Student student = new Student();
    student.setName("小明");
    student.setAge(18);
    student.setMoney(20.0f);
    students.add(student);

    Student student2 = new Student();
    student2.setName("小红");
    student2.setAge(18);
    student2.setMoney(30.0f);
    students.add(student2);

    Student student3 = new Student();
    student3.setName("小蓝");
    student3.setAge(18);
    student3.setMoney(30.0f);
    students.add(student3);

    params.put("stus",students);

    template.process(params, new FileWriter("d:/test.html"));
}
```

2、模板：

```velocity
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>

<#-- list 数据的展示 -->
<b>展示list中的stu数据:</b>
<br>
<br>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>

        <#list stus as stu>
            <#if stu.name=='小红'>
                <tr style="color:#ff0000">
                    <td>${stu_index + 1}</td>
                    <td>${stu.name}</td>
                    <td>${stu.age}</td>
                    <td>${stu.money}</td>
                </tr>
            <#elseif stu.name=='小蓝'>
              <tr style="color:#0000ff">
                    <td>${stu_index + 1}</td>
                    <td>${stu.name}</td>
                    <td>${stu.age}</td>
                    <td>${stu.money}</td>
                </tr>
            <#else >
                <tr>
                    <td>${stu_index + 1}</td>
                    <td>${stu.name}</td>
                    <td>${stu.age}</td>
                    <td>${stu.money}</td>
                </tr>
            </#if>
        </#list>

</table>
<hr>

</body>
</html>
```



# 运算符

**1、算数运算符**

FreeMarker表达式中完全支持算术运算,FreeMarker支持的算术运算符包括:

- 加法： `+`
- 减法： `-`
- 乘法： `*`
- 除法： `/`
- 求模 (求余)： `%`



模板代码

```html
<b>算数运算符</b>
<br/><br/>
    100+5 运算：  ${100 + 5 }<br/>
    100 - 5 * 5运算：${100 - 5 * 5}<br/>
    5 / 2运算：${5 / 2}<br/>
    12 % 10运算：${12 % 10}<br/>
<hr>
```

除了 + 运算以外，其他的运算只能和 number 数字类型的计算。



**2、比较运算符**

- **`=`**或者**`==`**:判断两个值是否相等.
- **`!=`**:判断两个值是否不等.
- **`>`**或者**`gt`**:判断左边值是否大于右边值
- **`>=`**或者**`gte`**:判断左边值是否大于等于右边值
- **`<`**或者**`lt`**:判断左边值是否小于右边值
- **`<=`**或者**`lte`**:判断左边值是否小于等于右边值
-

逻辑与：&&
逻辑或：||
逻辑非：!



= 和 == 模板代码

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>

    <b>比较运算符</b>
    <br/>
    <br/>

    <dl>
        <dt> =/== 和 != 比较：</dt>
        <dd>
            <#if "xiaoming" == "xiaoming">
                字符串的比较 "xiaoming" == "xiaoming"
            </#if>
        </dd>
        <dd>
            <#if 10 != 100>
                数值的比较 10 != 100
            </#if>
        </dd>
    </dl>



    <dl>
        <dt>其他比较</dt>
        <dd>
            <#if 10 gt 5 >
                形式一：使用特殊字符比较数值 10 gt 5
            </#if>
        </dd>
        <dd>
            <#-- 日期的比较需要通过?date将属性转为data类型才能进行比较 -->
            <#if (date1?date >= date2?date)>
                形式二：使用括号形式比较时间 date1?date >= date2?date
            </#if>
        </dd>
    </dl>

    <br/>
<hr>
</body>
</html>
```

Controller 的 数据模型代码

```java
@Test
    public void testOperator() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("04-operator.ftl");
        Map params = new HashMap();
        Date now = new Date();
        params.put("date1",now);
        params.put("date2",now);

        template.process(params, new FileWriter("d:/test.html"));
    }
```



**比较运算符注意**

- **`=`**和**`!=`**可以用于字符串、数值和日期来比较是否相等
- **`=`**和**`!=`**两边必须是相同类型的值,否则会产生错误
- 字符串 **`"x"`** 、**`"x "`** 、**`"X"`**比较是不等的.因为FreeMarker是精确比较
- 其它的运行符可以作用于数字和日期,但不能作用于字符串
- 使用**`gt`**等字母运算符代替**`>`**会有更好的效果,因为 FreeMarker会把**`>`**解释成FTL标签的结束字符
- 可以使用括号来避免这种情况,如:**`<#if (x>y)>`**



**3、逻辑运算符**

- 逻辑与:&&
- 逻辑或:||
- 逻辑非:!

逻辑运算符只能作用于布尔值,否则将产生错误 。



模板代码

```html
<b>逻辑运算符</b>
    <br/>
    <br/>
    <#if (10 lt 12 )&&( 10  gt  5 )  >
        (10 lt 12 )&&( 10  gt  5 )  显示为 true
    </#if>
    <br/>
    <br/>
    <#if !false>
        false 取反为true
    </#if>
<hr>
```





# 空值处理

**1、判断某变量是否存在使用 “??”**

用法为:variable??,如果该变量存在,返回true,否则返回false

例：为防止stus为空报错可以加上判断如下：

```velocity
    <#if stus??>
    <#list stus as stu>
    	......
    </#list>
    </#if>
  
```



**2、缺失变量默认值使用 “!”**

- 使用!要以指定一个默认值，当变量为空时显示默认值

  例：  ${name!'zhangsan'}表示如果name为空显示空字符串。

- 如果是嵌套对象则建议使用（）括起来

  例： ${(stu.bestFriend.name)!''}表示，如果stu或bestFriend或name为空默认显示空字符串。



模板：

```xml
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#if stus??>
        <#list stus as stu>
            <tr>
                <td>${stu_index+1}</td>
                <td>${(stu.name)!''}</td>
                <td>${stu.age}</td>
                <td>${stu.money}</td>
            </tr>
        </#list>
    </#if>
</table>

</body>
</html>
```



代码：

```java
@Test
public void testNull() throws IOException, TemplateException {
    //freemarker的模板对象，获取模板
    Template template = configuration.getTemplate("05-null.ftl");
    Map params = new HashMap();

    //封装list存放所有学生
    List<Student> students = new ArrayList<>();
    Student student = new Student();
    student.setName("小明");
    student.setAge(18);
    student.setMoney(20.0f);
    students.add(student);

    Student student2 = new Student();
    student2.setName("");
    student2.setAge(18);
    student2.setMoney(30.0f);
    students.add(student2);

    params.put("stus",students);

    template.process(params, new FileWriter("d:/test.html"));
}
```

# 内建函数

内建函数语法格式： **`变量+?+函数名称`**

**1、和到某个集合的大小**

**`${集合名?size}`**

**2、日期格式化**

显示年月日: **`${today?date}`**
显示时分秒：**`${today?time}`**   
显示日期+时间：**`${today?datetime}`**   
自定义格式化：  **`${today?string("yyyy年MM月")}`**



**3、内建函数`c`**

model.addAttribute("point", 102920122);

point是数字型，使用${point}会显示这个数字的值，每三位使用逗号分隔。

如果不想显示为每三位分隔的数字，可以使用c函数将数字型转成字符串输出

**`${point?c}`**



**4、将json字符串转成对象**

一个例子：

其中用到了 assign标签，assign的作用是定义一个变量。

```velocity
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank}  账号：${data.account}
```



内建函数模板页面：

```velocity
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>inner Function</title>
</head>
<body>

    <b>获得集合大小</b><br>

    集合大小：${stus?size}
    <hr>


    <b>获得日期</b><br>

    显示年月日: ${today?date}       <br>

    显示时分秒：${today?time}<br>

    显示日期+时间：${today?datetime}<br>

    自定义格式化：  ${today?string("yyyy年MM月")}<br>

    <hr>

    <b>内建函数C</b><br>
    没有C函数显示的数值：${point} <br>

    有C函数显示的数值：${point?c}

    <hr>

    <b>声明变量assign</b><br>
    <#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
    <#assign data=text?eval />
    开户行：${data.bank}  账号：${data.account}

<hr>
</body>
</html>
```

内建函数Controller数据模型：

```java
    @Test
    public void test1() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("06-inner.ftl");
        Map model = new HashMap();

        //1.1 小强对象模型数据
        Student stu1 = new Student();
        stu1.setName("小强");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        //1.2 小红对象模型数据
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
        //1.3 将两个对象模型数据存放到List集合中
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        model.put("stus", stus);
        // 2.1 添加日期
        Date date = new Date();
        model.put("today", date);
        // 3.1 添加数值
        model.put("point", 102920122);

        template.process(model, new FileWriter("d:/test.html"));
    }
```


