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
public class AuditReviewSuccessHandler implements Handler
{
    public void handle(TransactionContext context, StateMachine stateMachine) {
        AuditPermit auditPermit=(AuditPermit)context.getData(AuditContextConstans.LEAVE_PERMIT);
        auditPermit.setStatus("复审审核成功");
        log.info("[{}],permit=[{}],审批意见:[{}]", this.getClass().getSimpleName(), auditPermit);
        //复审通过走到初审审核流程
        stateMachine.fire(AuditEvent.AUDIT_MIGU,context);
    }
}
