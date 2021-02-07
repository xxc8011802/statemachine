package config.handler;

import com.github.shxz130.statemachine.core.config.Handler;
import com.github.shxz130.statemachine.core.fire.StateMachine;
import com.github.shxz130.statemachine.core.fire.TransactionContext;

import config.AuditContextConstans;
import config.AuditEvent;
import config.bean.AuditPermit;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jetty on 2019/7/31.
 */
@Slf4j
public class AuditAssignFirstHandler implements Handler{

    public void handle(TransactionContext context, StateMachine stateMachine) {
       AuditPermit auditPermit=(AuditPermit)context.getData(AuditContextConstans.LEAVE_PERMIT);
        auditPermit.setStatus("LEADER_PERMIT");
        log.info("[{}],permit=[{}]", this.getClass().getSimpleName(),auditPermit);
        //如果未分配，等待分配
        //如果当前已经分配了审核员，自动触发初审事件，走到初审状态
        stateMachine.fire(AuditEvent.AUDIT_FIRST,context);
    }
}
