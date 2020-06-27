## Spring中将BeanDefinition注册到IOC容器中

- XML配置元信息 
  - <bean name=”...” ... />
- 注解: 
  - @Bean,@Component,@Import
- 命名:
  - BeanDefinitionRegistry#registerBeanDefition
- 非命名
  - BeanDefinitionReaderUtils#registerWithGeneratedName
- AnnotatedBeanDefinitionReader#register

**RumenzA**

```java
package com.rumenz;
public class RumenzA {

    private String id;
    private String name;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "RumenzA{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    public  RumenzA() {
        System.out.println("RumenzA 无参构造方法");
    }

}
```

## XMl配置元信息

```xml
<bean id="rumena" class="com.rumenz.RumenzA"/>
```

## @Bean,@Component,@Import 注解

```java
package com.rumenz;


import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Import(DemoApplication.Config.class)
public class DemoApplication {
    public static  void main(String[] args) {
       AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext();
       ac.register(DemoApplication.class);
       ac.refresh();

       System.out.println("RumenzA类"+ac.getBeansOfType(RumenzA.class));
       System.out.println("Config类"+ac.getBeansOfType(Config.class));
       System.out.println("DemoApplication类"+ac.getBeansOfType(DemoApplication.class));
       ac.close();
    }
    @Component
    public static class Config{
       @Bean
       public RumenzA rumenza(){
          RumenzA r=new RumenzA();
          r.setId("1");
          r.setName("入门小站");
          return r;
       }
    }

}

```

## BeanDefinitionRegistry#registerBeanDefinition
## BeanDefinitionReaderUtils#registerWithGeneratedName

```java
package com.rumenz;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Import(DemoApplication.Config.class) //1.注解方式
public class DemoApplication {
    public static  void main(String[] args) {

       AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext();
       //ac.register(DemoApplication.class); //1.注解方式
       // 2.BeanDefinitionReaderUtils#registerWithGeneratedName,BeanDefinitionRegistry#registerBeanDefinition
       registryBeanDefinition(ac,"rumenz");
       registryBeanDefinition(ac,null);
       ac.refresh();

       System.out.println("RumenzA类"+ac.getBeansOfType(RumenzA.class));

       ac.close();
    }

    private static void registryBeanDefinition(BeanDefinitionRegistry reg,String beanName){
        BeanDefinitionBuilder builder= BeanDefinitionBuilder.genericBeanDefinition(RumenzA.class);
        builder.addPropertyValue("id","1").addPropertyValue("name","入门小站");
        if(StringUtils.isEmpty(beanName)){ //非命名
            BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(),reg);

        }else{
            //命名
            reg.registerBeanDefinition(beanName,builder.getBeanDefinition());
        }

    }
    @Component //1.注解方式
    public static class Config{
       @Bean //1.注解方式
       public RumenzA rumenza(){
          RumenzA r=new RumenzA();
          r.setId("1");
          r.setName("入门小站");
          return r;
       }
    }

}

```
