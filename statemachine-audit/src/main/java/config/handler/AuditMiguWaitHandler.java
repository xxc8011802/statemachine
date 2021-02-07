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
public class AuditMiguWaitHandler implements Handler{

    public void handle(TransactionContext context, StateMachine stateMachine) {
       AuditPermit auditPermit=(AuditPermit)context.getData(AuditContextConstans.LEAVE_PERMIT);
        auditPermit.setStatus("LEADER_PERMIT");
        log.info("[{}],permit=[{}]", this.getClass().getSimpleName(),auditPermit);
        String auditMiguSuggestion=(String)context.getData(AuditContextConstans.AUDIT_MIGU_SUGGESTION);
        //如果未审核，等待主站审核
        if(auditMiguSuggestion==null){
            log.info("等待主站审核");
            return;
        }
    }
}
