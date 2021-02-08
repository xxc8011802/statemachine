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
public class AuditAssignFinishHandler implements Handler{

    public void handle(TransactionContext context, StateMachine stateMachine) {
       AuditPermit auditPermit=(AuditPermit)context.getData(AuditContextConstans.LEAVE_PERMIT);
        auditPermit.setStatus("分配完成");
        log.info("[{}],permit=[{}]", this.getClass().getSimpleName(),auditPermit);
        //分配审核员后，自动触发初审事件
        stateMachine.fire(AuditEvent.AUDIT_FIRST,context);
    }
}
