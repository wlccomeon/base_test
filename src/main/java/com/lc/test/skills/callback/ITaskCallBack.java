package com.lc.test.skills.callback;

/**
 * @desc   回调接口
 * @author wlc
 * @date 2026/3/11 星期三
 */
public interface ITaskCallBack<T> {

    /**
     * 回调方法
     * @param t
     */
    void callBack(T t);



}
