
package com.alibaba.dubbo.config.spring.beans.factory.annotation;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.api.DemoService;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

import static com.alibaba.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor.BEAN_NAME;


public class ReferenceAnnotationBeanPostProcessorTest {

    private static final String PROVIDER_LOCATION = "META-INF/spring/dubbo-provider.xml";

    @Before
    public void before() {
                new ClassPathXmlApplicationContext(PROVIDER_LOCATION);
    }

    @Test
    public void test() throws Exception {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestBean.class);

        TestBean testBean = context.getBean(TestBean.class);

        Assert.assertNotNull(testBean.getDemoServiceFromAncestor());
        Assert.assertNotNull(testBean.getDemoServiceFromParent());
        Assert.assertNotNull(testBean.getDemoService());

        Assert.assertEquals(testBean.getDemoServiceFromAncestor(), testBean.getDemoServiceFromParent());
        Assert.assertEquals(testBean.getDemoService(), testBean.getDemoServiceFromParent());

        DemoService demoService = testBean.getDemoService();

        Assert.assertEquals("annotation:Mercy", demoService.sayName("Mercy"));

        context.close();

    }

    
    @Test
    public void testGetReferenceBeans() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestBean.class);

        ReferenceAnnotationBeanPostProcessor beanPostProcessor = context.getBean(BEAN_NAME,
                ReferenceAnnotationBeanPostProcessor.class);

        Collection<ReferenceBean<?>> referenceBeans = beanPostProcessor.getReferenceBeans();

        Assert.assertEquals(1, referenceBeans.size());

        ReferenceBean<?> referenceBean = referenceBeans.iterator().next();

        TestBean testBean = context.getBean(TestBean.class);

        Assert.assertEquals(referenceBean.get(), testBean.getDemoServiceFromAncestor());
        Assert.assertEquals(referenceBean.get(), testBean.getDemoServiceFromParent());
        Assert.assertEquals(referenceBean.get(), testBean.getDemoService());

    }

    private static class AncestorBean {


        private DemoService demoServiceFromAncestor;

        @Autowired
        private ApplicationContext applicationContext;

        public DemoService getDemoServiceFromAncestor() {
            return demoServiceFromAncestor;
        }

        @Reference(version = "1.2", url = "dubbo:        public void setDemoServiceFromAncestor(DemoService demoServiceFromAncestor) {
            this.demoServiceFromAncestor = demoServiceFromAncestor;
        }

        public ApplicationContext getApplicationContext() {
            return applicationContext;
        }

    }


    private static class ParentBean extends AncestorBean {

        @Reference(version = "1.2", url = "dubbo:        private DemoService demoServiceFromParent;

        public DemoService getDemoServiceFromParent() {
            return demoServiceFromParent;
        }


    }

    @ImportResource("META-INF/spring/dubbo-annotation-consumer.xml")
    @DubboComponentScan(basePackageClasses = ReferenceAnnotationBeanPostProcessorTest.class)
    private static class TestBean extends ParentBean {

        private DemoService demoService;

        @Autowired
        private ApplicationContext applicationContext;

        public DemoService getDemoService() {
            return demoService;
        }

        @Reference(version = "1.2", url = "dubbo:        public void setDemoService(DemoService demoService) {
            this.demoService = demoService;
        }
    }

}
