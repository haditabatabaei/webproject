package com.alibaba.json.bvt.serializer;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;

import java.lang.reflect.Proxy;


public class ProxyTest2 extends TestCase {
    public void test_0() throws Exception {
        Model model = JSON.parseObject("{\"id\":1001}", Model.class);
        Model model2 = JSON.parseObject("{\"id\":1001}", Model.class);
        System.out.println(model.getId());


        assertEquals("{\"id\":1001}", JSON.toJSONString(model));
        assertEquals("{\"id\":1001}", JSON.toJSONString(model));

    }

    public static interface Model {
        int getId();
        void setId(int val);
    }
}
