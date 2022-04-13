package com.lc.test.designpattern.observer.apply;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wlc
 * @desc: 监听器，它就是观察者的桥梁
 * @datetime: 2022/4/13 18:08
 */
public class EventListener {
    /**jdk底层的listener通常也是这样来设计的*/
    protected Map<String,Event> events = new HashMap<>();
    /**通过事件名称和一个目标对象来触发事件*/
    public void addListener(String eventType,Object target){
        try {
            this.addListener(eventType,target,
                    //这里是固定规则的方法名称
                    target.getClass().getMethod("on"+toUpperFirstCase(eventType),Event.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 注册事件
     * @param eventType
     * @param target
     * @param callback
     */
    public void addListener(String eventType, Object target, Method callback){
        events.put(eventType,new Event(target,callback));
    }

    /**
     * 触发，只要有动作就触发
     * @param event
     */
    private void trigger(Event event){
        event.setSource(this);
        event.setTime(System.currentTimeMillis());
        //利用反射发起回调
        try {
            if (Objects.nonNull(event.getCallback())){
                event.getCallback().invoke(event.getTarget(),event);
            }
        }catch (Exception e){

        }
    }
    protected void trigger(String triggerName){
        if (!this.events.containsKey(triggerName)){
            return;
        }
        trigger(this.events.get(triggerName).setTrigger(triggerName));
    }

    /**
     * 字符串首字母大写
     * @param str
     * @return
     */
    private String toUpperFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }
}

