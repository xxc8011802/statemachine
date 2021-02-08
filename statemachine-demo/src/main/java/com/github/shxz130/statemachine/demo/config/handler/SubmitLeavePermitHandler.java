package com.github.shxz130.statemachine.demo.config.handler;

import com.github.shxz130.statemachine.core.config.BaseStateMachineKey;
import com.github.shxz130.statemachine.core.config.Handler;
import com.github.shxz130.statemachine.core.fire.StateMachine;
import com.github.shxz130.statemachine.core.fire.TransactionContext;
import com.github.shxz130.statemachine.demo.config.LeavePermitContextConstants;
import com.github.shxz130.statemachine.demo.config.LeavePermitEvent;
import com.github.shxz130.statemachine.demo.config.bean.LeavePermit;
import lombok.extern.slf4j.Slf4j;

import static com.github.shxz130.statemachine.demo.config.LeavePermitState.CEO_PERMIT;

/**
 * Created by jetty on 2019/7/31.
 * 提交
 */
@Slf4j
public class SubmitLeavePermitHandler implements Handler{

    public void handle(TransactionContext context, StateMachine stateMachine) {
        LeavePermit leavePermit=new LeavePermit();
        leavePermit.setPermitNo("PERMITN");
        leavePermit.setStatus("INIT");//设置为初始状态
        log.info("[{}],permit=[{}]", this.getClass().getSimpleName(), leavePermit);
        context.setData(LeavePermitContextConstants.LEAVE_PERMIT, leavePermit);
        //context.setData(BaseStateMachineKey.CURRENT_STATE,CEO_PERMIT);
        //提交完后是自动去触发领导审核事件
        stateMachine.fire(LeavePermitEvent.LEADER_PERMIT,context);
    }
}
