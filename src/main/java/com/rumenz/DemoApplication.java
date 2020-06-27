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
        if(StringUtils.isEmpty(beanName)){ //创建匿名BeanDefinition
            BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(),reg);

        }else{
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
