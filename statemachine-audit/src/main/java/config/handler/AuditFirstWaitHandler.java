package config.handler;

import com.github.shxz130.statemachine.core.config.Handler;
import com.github.shxz130.statemachine.core.fire.StateMachine;
import com.github.shxz130.statemachine.core.fire.TransactionContext;
import config.AuditContextConstans;
import config.bean.AuditPermit;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jetty on 2019/7/31.
 */
@Slf4j
public class AuditFirstWaitHandler implements Handler{

    public void handle(TransactionContext context, StateMachine stateMachine) {
       AuditPermit auditPermit=(AuditPermit)context.getData(AuditContextConstans.LEAVE_PERMIT);
        auditPermit.setStatus("LEADER_PERMIT");
        log.info("[{}],permit=[{}]", this.getClass().getSimpleName(),auditPermit);
        String auditFirstSuggestion=(String)context.getData(AuditContextConstans.AUDIT_FIRST_SUGGESTION);
        //如果未审核，等待初审
        if(auditFirstSuggestion==null){
            log.info("等待初审");
            return;
        }
    }
}
