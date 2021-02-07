package config.main;

import com.github.shxz130.statemachine.core.fire.StateMachineFactory;
import com.github.shxz130.statemachine.core.fire.TransactionContext;

import config.AuditContextConstans;
import config.AuditEvent;
import config.AuditState;
import config.StatemachineInit;
import config.bean.AuditPermit;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jetty on 2019/7/31.
 */
@Slf4j
public class Main
{

    public static void main(String[] args){
        StatemachineInit.init();

        log.info("作家创建审核");
        TransactionContext transactionContext=new TransactionContext();
        transactionContext.setData(AuditContextConstans.CURRENT_STATE, AuditState.SUBMIT_AUDIT);
        StateMachineFactory.getStateMachine("LEAVE_PERMIT").fire(AuditEvent.SUBMIT_AUDIT, transactionContext);


        log.info("编辑分配审核人员");
        AuditPermit auditPermit=(AuditPermit)transactionContext.getData(AuditContextConstans.LEAVE_PERMIT);
        TransactionContext transactionContext2=new TransactionContext();
        //已分配审核人员，可以查询库判断是否已分配
        if(false){
            transactionContext2.setData(AuditContextConstans.LEAVE_PERMIT,auditPermit);
            transactionContext2.setData(AuditContextConstans.CURRENT_STATE, AuditState.AUDIT_ASSIGN);
        }else{
            //未分配审核人员，走分配流程
            transactionContext2.setData(AuditContextConstans.LEAVE_PERMIT,auditPermit);
            transactionContext2.setData(AuditContextConstans.CURRENT_STATE, AuditState.AUDIT_ASSIGN);
            transactionContext.setData(AuditContextConstans.CURRENT_STATE, AuditState.SUBMIT_AUDIT);
        }
        //实际执行初审通过
        StateMachineFactory.getStateMachine("LEAVE_PERMIT").fire(AuditEvent.AUDIT_FIRST_AGREE, transactionContext2);

    }
}
