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
public class SubmitAuditHandler implements Handler{

    public void handle(TransactionContext context, StateMachine stateMachine) {
        AuditPermit auditPermit=new AuditPermit();
        auditPermit.setPermitNo("PERMITN");
        auditPermit.setStatus("初始提交审核");//设置为初始状态
        log.info("[{}],permit=[{}]", this.getClass().getSimpleName(), auditPermit);
        context.setData(AuditContextConstans.LEAVE_PERMIT, auditPermit); //context 存入一些下一个步骤用于判断的数据，比如审核内容，下一个环节需要看到
        //提交完后是自动去触发分配事件
        stateMachine.fire(AuditEvent.AUDIT_ASSIGN,context);
    }
}
