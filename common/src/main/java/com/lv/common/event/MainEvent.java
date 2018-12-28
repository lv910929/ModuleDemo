package com.lv.common.event;

import java.io.Serializable;

/**
 * Created by Lv on 2016/6/30.
 */
public class MainEvent implements Serializable {

    private int what;
    private Object obj;

    public MainEvent(int what, Object obj) {
        this.what = what;
        this.obj = obj;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
