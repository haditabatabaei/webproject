
package com.alibaba.json.bvt.bug;

import java.text.DateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import junit.framework.TestCase;

public class Bug_376_for_iso8601 extends TestCase {

    public void test_fix() {

        String s = "{date: \"2015-07-22T19:13:42Z\"}";
        String s2 = "{date: \"2015-07-22T19:13:42.000Z\"}";

        MyObj o = JSON.parseObject(s, MyObj.class, Feature.AllowISO8601DateFormat);
        MyObj o2 = JSON.parseObject(s2, MyObj.class, Feature.AllowISO8601DateFormat);

        System.out.println(DateFormat.getDateTimeInstance().format(o.getDate()));
        System.out.println(DateFormat.getDateTimeInstance().format(o2.getDate()));

                        
                        
    }

    static class MyObj {

        private Date date;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

}
