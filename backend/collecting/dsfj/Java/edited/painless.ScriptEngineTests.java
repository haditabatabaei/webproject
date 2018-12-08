

package org.elasticsearch.painless;

import org.elasticsearch.script.ExecutableScript;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ScriptEngineTests extends ScriptTestCase {

    public void testSimpleEquation() {
        final Object value = exec("return 1 + 2;");
        assertEquals(3, ((Number)value).intValue());
    }

    @SuppressWarnings("unchecked")     public void testMapAccess() {
        Map<String, Object> vars = new HashMap<>();
        Map<String, Object> obj2 = new HashMap<>();
        obj2.put("prop2", "value2");
        Map<String, Object> obj1 = new HashMap<>();
        obj1.put("prop1", "value1");
        obj1.put("obj2", obj2);
        obj1.put("l", Arrays.asList("2", "1"));
        vars.put("obj1", obj1);

        Object value = exec("return params['obj1'];", vars, true);
        obj1 = (Map<String, Object>)value;
        assertEquals("value1", obj1.get("prop1"));
        assertEquals("value2", ((Map<String, Object>) obj1.get("obj2")).get("prop2"));

        value = exec("return params.obj1.l.0;", vars, true);
        assertEquals("2", value);
    }

    @SuppressWarnings("unchecked")     public void testAccessListInScript() {
        Map<String, Object> vars = new HashMap<>();
        Map<String, Object> obj2 = new HashMap<>();
        obj2.put("prop2", "value2");
        Map<String, Object> obj1 = new HashMap<>();
        obj1.put("prop1", "value1");
        obj1.put("obj2", obj2);
        vars.put("l", Arrays.asList("1", "2", "3", obj1));

        assertEquals(4, exec("return params.l.size();", vars, true));
        assertEquals("1", exec("return params.l.0;", vars, true));

        Object value = exec("return params.l.3;", vars, true);
        obj1 = (Map<String, Object>)value;
        assertEquals("value1", obj1.get("prop1"));
        assertEquals("value2", ((Map<String, Object>)obj1.get("obj2")).get("prop2"));

        assertEquals("value1", exec("return params.l.3.prop1;", vars, true));
    }

    public void testChangingVarsCrossExecution1() {
        Map<String, Object> vars = new HashMap<>();
        Map<String, Object> ctx = new HashMap<>();
        vars.put("ctx", ctx);

        ExecutableScript.Factory factory =
            scriptEngine.compile(null, "return ctx.value;", ExecutableScript.CONTEXT, Collections.emptyMap());
        ExecutableScript script = factory.newInstance(vars);

        ctx.put("value", 1);
        Object o = script.run();
        assertEquals(1, ((Number) o).intValue());

        ctx.put("value", 2);
        o = script.run();
        assertEquals(2, ((Number) o).intValue());
    }

    public void testChangingVarsCrossExecution2() {
        Map<String, Object> vars = new HashMap<>();
        ExecutableScript.Factory factory =
            scriptEngine.compile(null, "return params['value'];", ExecutableScript.CONTEXT, Collections.emptyMap());
        ExecutableScript script = factory.newInstance(vars);

        script.setNextVar("value", 1);
        Object value = script.run();
        assertEquals(1, ((Number)value).intValue());

        script.setNextVar("value", 2);
        value = script.run();
        assertEquals(2, ((Number)value).intValue());
    }
}