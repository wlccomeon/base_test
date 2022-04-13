package com.lc.test.designpattern.observer.apply;

/**
 * @author wlc
 * @desc: 鼠标事件类型常量
 * @datetime: 2022/4/13 20:59
 */
public interface MouseEventType {
    /**单击*/
    String ON_CLICK = "click";
    /**双击*/
    String ON_DOUBLE_CLICK = "doubleClick";
    /**弹起*/
    String ON_UP = "up";
    /**按下*/
    String ON_DOWN = "down";
    /**移动*/
    String ON_MOVE = "move";
    /**滚动*/
    String ON_WHEEL = "wheel";
    /**悬停*/
    String ON_BLUR = "over";
    /**失焦*/
    String ON_OVER = "blur";
    /**获焦*/
    String ON_FOCUS = "focus";

}
