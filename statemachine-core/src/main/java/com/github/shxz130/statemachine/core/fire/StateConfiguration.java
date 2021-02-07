package com.github.shxz130.statemachine.core.fire;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jetty on 2019/7/31.
 * 状态配置
 */

public class StateConfiguration<S, E,H> {

    @Getter
    private S currentState;//当前状态初审审核

    //绑定当前状态下 事件和处理方法的映射 初审审核通过事件和审核通过操作
    private Map<E,H> eventHandleMap;

    //绑定当前状态下 事件和下一状态的映射  初审审核通过事件和复审审核
    private Map<E, S> nextStateMap;

    public StateConfiguration(S state) {
        this.currentState = state;
        eventHandleMap = new HashMap<E, H>(8);
        nextStateMap = new HashMap<E, S>(8);
    }

    public void configEventHandle(E e,H h){
        eventHandleMap.put(e,h);
    }

    public void configEventNextState(E e,S s){
        nextStateMap.put(e,s);
    }

    public H getHandle(E e){
        return eventHandleMap.get(e);
    }

    public S getNextState(E e){
        return nextStateMap.get(e);
    }
}
