package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONObject;


class TestBean {
    private JSONObject data;
    private String name;

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
