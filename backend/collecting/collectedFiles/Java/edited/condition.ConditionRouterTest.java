
package com.alibaba.dubbo.rpc.cluster.router.condition;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.alibaba.dubbo.rpc.cluster.router.MockInvoker;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConditionRouterTest {

    private URL SCRIPT_URL = URL.valueOf("condition:
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    private URL getRouteUrl(String rule) {
        return SCRIPT_URL.addParameterAndEncoded(Constants.RULE_KEY, rule);
    }
    @Test
    public void testRoute_matchWhen() {
        Invocation invocation = new RpcInvocation();

        Router router = new ConditionRouterFactory().getRouter(getRouteUrl(" => host = 1.2.3.4"));
        boolean matchWhen = ((ConditionRouter) router).matchWhen(URL.valueOf("consumer:                invocation);
        Assert.assertEquals(true, matchWhen);

        router = new ConditionRouterFactory()
                .getRouter(getRouteUrl("host = 2.2.2.2,1.1.1.1,3.3.3.3 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router).matchWhen(URL.valueOf("consumer:                invocation);
        Assert.assertEquals(true, matchWhen);

        router = new ConditionRouterFactory()
                .getRouter(getRouteUrl("host = 2.2.2.2,1.1.1.1,3.3.3.3 & host !=1.1.1.1 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router).matchWhen(URL.valueOf("consumer:                invocation);
        Assert.assertEquals(false, matchWhen);

        router = new ConditionRouterFactory()
                .getRouter(getRouteUrl("host !=4.4.4.4 & host = 2.2.2.2,1.1.1.1,3.3.3.3 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router).matchWhen(URL.valueOf("consumer:                invocation);
        Assert.assertEquals(true, matchWhen);

        router = new ConditionRouterFactory()
                .getRouter(getRouteUrl("host !=4.4.4.* & host = 2.2.2.2,1.1.1.1,3.3.3.3 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router).matchWhen(URL.valueOf("consumer:                invocation);
        Assert.assertEquals(true, matchWhen);

        router = new ConditionRouterFactory()
                .getRouter(getRouteUrl("host = 2.2.2.2,1.1.1.*,3.3.3.3 & host != 1.1.1.1 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router).matchWhen(URL.valueOf("consumer:                invocation);
        Assert.assertEquals(false, matchWhen);

        router = new ConditionRouterFactory()
                .getRouter(getRouteUrl("host = 2.2.2.2,1.1.1.*,3.3.3.3 & host != 1.1.1.2 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router).matchWhen(URL.valueOf("consumer:                invocation);
        Assert.assertEquals(true, matchWhen);
    }

    @Test
    public void testRoute_matchFilter() {
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf(
                "dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:                + ":20880/com.foo.BarService"));
        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:                + ":20880/com.foo.BarService"));
        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);

        Router router1 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " => " + " host = 10.20.3.3").addParameter(Constants.FORCE_KEY,
                String.valueOf(true)));
        Router router2 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " => " + " host = 10.20.3.* & host != 10.20.3.3").addParameter(
                Constants.FORCE_KEY, String.valueOf(true)));
        Router router3 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " => " + " host = 10.20.3.3  & host != 10.20.3.3").addParameter(
                Constants.FORCE_KEY, String.valueOf(true)));
        Router router4 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " => " + " host = 10.20.3.2,10.20.3.3,10.20.3.4").addParameter(
                Constants.FORCE_KEY, String.valueOf(true)));
        Router router5 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " => " + " host != 10.20.3.3").addParameter(Constants.FORCE_KEY,
                String.valueOf(true)));
        Router router6 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " => " + " serialization = fastjson").addParameter(
                Constants.FORCE_KEY, String.valueOf(true)));

        List<Invoker<String>> fileredInvokers1 = router1.route(invokers,
                URL.valueOf("consumer:        List<Invoker<String>> fileredInvokers2 = router2.route(invokers,
                URL.valueOf("consumer:        List<Invoker<String>> fileredInvokers3 = router3.route(invokers,
                URL.valueOf("consumer:        List<Invoker<String>> fileredInvokers4 = router4.route(invokers,
                URL.valueOf("consumer:        List<Invoker<String>> fileredInvokers5 = router5.route(invokers,
                URL.valueOf("consumer:        List<Invoker<String>> fileredInvokers6 = router6.route(invokers,
                URL.valueOf("consumer:        Assert.assertEquals(1, fileredInvokers1.size());
        Assert.assertEquals(0, fileredInvokers2.size());
        Assert.assertEquals(0, fileredInvokers3.size());
        Assert.assertEquals(1, fileredInvokers4.size());
        Assert.assertEquals(2, fileredInvokers5.size());
        Assert.assertEquals(1, fileredInvokers6.size());
    }

    @Test
    public void testRoute_methodRoute() {
        Invocation invocation = new RpcInvocation("getFoo", new Class<?>[0], new Object[0]);
                Router router = new ConditionRouterFactory().getRouter(getRouteUrl("methods=getFoo => host = 1.2.3.4"));
        boolean matchWhen = ((ConditionRouter) router).matchWhen(
                URL.valueOf("consumer:        Assert.assertEquals(true, matchWhen);
                matchWhen = ((ConditionRouter) router).matchWhen(
                URL.valueOf("consumer:        Assert.assertEquals(true, matchWhen);
                Router router2 = new ConditionRouterFactory()
                .getRouter(getRouteUrl("methods=getFoo & host!=1.1.1.1 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router2).matchWhen(
                URL.valueOf("consumer:        Assert.assertEquals(false, matchWhen);

        Router router3 = new ConditionRouterFactory()
                .getRouter(getRouteUrl("methods=getFoo & host=1.1.1.1 => host = 1.2.3.4"));
        matchWhen = ((ConditionRouter) router3).matchWhen(
                URL.valueOf("consumer:        Assert.assertEquals(true, matchWhen);
                List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:                + ":20880/com.foo.BarService"));
        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:                + ":20880/com.foo.BarService"));
        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);

        Router router4 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " & methods = getFoo => " + " host = 10.20.3.3").addParameter(
                Constants.FORCE_KEY, String.valueOf(true)));
        List<Invoker<String>> fileredInvokers1 = router4.route(invokers,
                URL.valueOf("consumer:        Assert.assertEquals(1, fileredInvokers1.size());

        Router router5 = new ConditionRouterFactory().getRouter(getRouteUrl(
                "host = " + NetUtils.getLocalHost() + " & methods = unvalidmethod => " + " host = 10.20.3.3")
                .addParameter(Constants.FORCE_KEY, String.valueOf(true)));
        List<Invoker<String>> fileredInvokers2 = router5.route(invokers,
                URL.valueOf("consumer:        Assert.assertEquals(3, fileredInvokers2.size());
            }

    @Test
    public void testRoute_ReturnFalse() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => false"));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        invokers.add(new MockInvoker<String>());
        invokers.add(new MockInvoker<String>());
        invokers.add(new MockInvoker<String>());
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(0, fileredInvokers.size());
    }

    @Test
    public void testRoute_ReturnEmpty() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => "));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        invokers.add(new MockInvoker<String>());
        invokers.add(new MockInvoker<String>());
        invokers.add(new MockInvoker<String>());
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(0, fileredInvokers.size());
    }

    @Test
    public void testRoute_ReturnAll() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => " + " host = " + NetUtils.getLocalHost()));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        invokers.add(new MockInvoker<String>());
        invokers.add(new MockInvoker<String>());
        invokers.add(new MockInvoker<String>());
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(invokers, fileredInvokers);
    }

    @Test
    public void testRoute_HostFilter() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => " + " host = " + NetUtils.getLocalHost()));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(2, fileredInvokers.size());
        Assert.assertEquals(invoker2, fileredInvokers.get(0));
        Assert.assertEquals(invoker3, fileredInvokers.get(1));
    }

    @Test
    public void testRoute_Empty_HostFilter() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl(" => " + " host = " + NetUtils.getLocalHost()));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(2, fileredInvokers.size());
        Assert.assertEquals(invoker2, fileredInvokers.get(0));
        Assert.assertEquals(invoker3, fileredInvokers.get(1));
    }

    @Test
    public void testRoute_False_HostFilter() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("true => " + " host = " + NetUtils.getLocalHost()));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(2, fileredInvokers.size());
        Assert.assertEquals(invoker2, fileredInvokers.get(0));
        Assert.assertEquals(invoker3, fileredInvokers.get(1));
    }

    @Test
    public void testRoute_Placeholder() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => " + " host = $host"));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(2, fileredInvokers.size());
        Assert.assertEquals(invoker2, fileredInvokers.get(0));
        Assert.assertEquals(invoker3, fileredInvokers.get(1));
    }

    @Test
    public void testRoute_NoForce() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => " + " host = 1.2.3.4"));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(invokers, fileredInvokers);
    }

    @Test
    public void testRoute_Force() {
        Router router = new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => " + " host = 1.2.3.4").addParameter(Constants.FORCE_KEY, String.valueOf(true)));
        List<Invoker<String>> invokers = new ArrayList<Invoker<String>>();
        Invoker<String> invoker1 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker2 = new MockInvoker<String>(URL.valueOf("dubbo:        Invoker<String> invoker3 = new MockInvoker<String>(URL.valueOf("dubbo:        invokers.add(invoker1);
        invokers.add(invoker2);
        invokers.add(invoker3);
        List<Invoker<String>> fileredInvokers = router.route(invokers, URL.valueOf("consumer:        Assert.assertEquals(0, fileredInvokers.size());
    }

}