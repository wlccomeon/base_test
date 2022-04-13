package com.lc.test.designpattern.observer.apply;

import org.junit.platform.commons.function.Try;

/**
 * @author wlc
 * @desc: MouseEventTest
 * @datetime: 2022/4/13 21:29
 */
public class MouseEventTest {
    public static void main(String[] args) {
        try {
            MouseEventCallback callback = new MouseEventCallback();

            //注册事件
            Mouse mouse = new Mouse();
            mouse.addListener(MouseEventType.ON_CLICK,callback);
            mouse.addListener(MouseEventType.ON_MOVE,callback);
            mouse.addListener(MouseEventType.ON_WHEEL,callback);
            mouse.addListener(MouseEventType.ON_OVER,callback);

            //调用方法
            mouse.click();
            mouse.blur();
            mouse.over();

        }catch (Exception e){

        }
    }
}

