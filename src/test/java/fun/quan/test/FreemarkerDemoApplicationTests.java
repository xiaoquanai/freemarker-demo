package fun.quan.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import fun.quan.test.pojo.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootTest(classes = FreemarkerDemoApplication.class)
@RunWith(SpringRunner.class)
class FreemarkerDemoApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void test() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        String name = "/index.ftl";
        Template template = configuration.getTemplate(name);
        Map params = getData();
        params.put("name","张三");
        Student student = new Student();
        student.setName("小明");
        student.setAge(18);
        params.put("stu",student);
        template.process(params, new FileWriter("d:/test.html"));

    }

    private Map getData() {
        Map<String, Object> map = new HashMap<>();

        //小强对象模型数据
        Student stu1 = new Student();
        stu1.setName("小强");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());

        //小红对象模型数据
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);

        //将两个对象模型数据存放到List集合中
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);

        //向map中存放List集合数据
        map.put("stus", stus);


        //创建Map数据
        HashMap<String, Student> stuMap = new HashMap<>();
        stuMap.put("stu1", stu1);
        stuMap.put("stu2", stu2);
        //向map中存放Map数据
        map.put("stuMap", stuMap);

        //返回Map
        return map;
    }


    @Test
    public void testList() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("list.ftl");
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

        template.process(params, new FileWriter("d:/list.html"));
    }
    @Test
    public void testMap() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("map.ftl");
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

        template.process(params, new FileWriter("d:/map.html"));
    }

    @Test
    public void testIf() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("if.ftl");
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

        template.process(params, new FileWriter("d:/if.html"));
    }

    @Test
    public void testOperator() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("operator.ftl");
        Map params = new HashMap();
        Date now = new Date();
        params.put("date1",now);
        params.put("date2",now);

        template.process(params, new FileWriter("d:/operator.html"));
    }
    @Test
    public void testNull() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("nulls.ftl");
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

        template.process(params, new FileWriter("d:/null.html"));
    }

    @Test
    public void test1() throws IOException, TemplateException {
        //freemarker的模板对象，获取模板
        //freemarker的模板对象，获取模板
        Template template = configuration.getTemplate("inner.ftl");
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

        template.process(model, new FileWriter("d:/inner.html"));
    }
}
