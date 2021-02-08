package config;

import com.github.shxz130.statemachine.core.config.Handler;
import com.github.shxz130.statemachine.core.fire.StateMachine;
import com.github.shxz130.statemachine.core.fire.StateMachineConfig;
import com.github.shxz130.statemachine.core.fire.StateMachineFactory;
import config.handler.*;

/**
 * Created by jetty on 2019/7/31.
 *
 *
 *
 */
public class StatemachineInit
{

    //初始化状态机
    public static  void init(){
        //支持多状态机 这里以请假为例，可以支持多种
        StateMachineFactory.register("LEAVE_PERMIT",buildLeavePermitStateMachine());

    }

    private static StateMachine buildLeavePermitStateMachine() {
        StateMachineConfig<AuditState,AuditEvent,Handler> stateMachineConfig=new StateMachineConfig();

        stateMachineConfig.from(AuditState.SUBMIT_AUDIT)//初始状态，提交审核
                .permit(AuditEvent.SUBMIT_AUDIT) //作家提交审核事件
                .handle(new SubmitAuditHandler())//提交审核操作
                .to(AuditState.AUDIT_ASSIGN) //提交后，请求审核分配初审
                 .build();

        //该状态转换是提交时自动触发的
        stateMachineConfig.from(AuditState.AUDIT_ASSIGN) //分配审核人员状态
                .permit(AuditEvent.AUDIT_ASSIGN)  //审核分配事件
                .handle(new AuditAssignWaitHandler())     //等待分配审核人员
                .to(AuditState.AUDIT_ASSIGN)    //下一状态，还在审核分配状态
                .build();

        //分配者分配审核人员
        stateMachineConfig.from(AuditState.AUDIT_ASSIGN) //编辑分配初审审核人员状态
            .permit(AuditEvent.AUDIT_ASSIGN_FINISH)  //审核分配完成事件
            .handle(new AuditAssignFinishHandler())     //审核分配后等待初审状态
            .to(AuditState.AUDIT_FIRST)    //下一状态，初审状态
            .build();

        //等待初审审核人员审核
        stateMachineConfig.from(AuditState.AUDIT_FIRST) //初审审核状态
            .permit(AuditEvent.AUDIT_FIRST)  //等待初审审核
            .handle(new AuditWaitHandler())     //等待审核
            .to(AuditState.AUDIT_FIRST)    //下一状态，初审状态
            .build();

        //初审审核通过分配，下一状态的跳转在
        stateMachineConfig.from(AuditState.AUDIT_FIRST) //初审状态
            .permit(AuditEvent.AUDIT_FIRST_AGREE)  //初审审核通过
            .handle(new AuditFirstSuccessHandler())     //初审通过，根据是否分配复审人员判断是走到复审状态还是主站审核状态
            .build();

        //等待复审
        stateMachineConfig.from(AuditState.AUDIT_REVIEW) //复审状态
            .permit(AuditEvent.AUDIT_REVIEW)  //等待复审审核
            .handle(new AuditWaitHandler())     //
            .to(AuditState.AUDIT_REVIEW)    //下一状态，复审状态
            .build();

        //复审通过自动触发走到等待主站审核流程
        stateMachineConfig.from(AuditState.AUDIT_REVIEW) //复审状态
            .permit(AuditEvent.AUDIT_REVIEW_AGREE)  //等待复审审核
            .handle(new AuditReviewSuccessHandler())     //
            .to(AuditState.AUDIT_MIGU)    //下一状态，复审状态
            .build();

        //等待主站审核
        stateMachineConfig.from(AuditState.AUDIT_MIGU) //主站审核状态
            .permit(AuditEvent.AUDIT_MIGU)  //等待主站审核审核
            .handle(new AuditWaitHandler())     //
            .to(AuditState.AUDIT_MIGU)    //下一状态，主站审核状态
            .build();

        //主站审核通过结束
        stateMachineConfig.from(AuditState.AUDIT_MIGU)   //主站审核
            .permit(AuditEvent.AUDIT_MIGU_AGREE)         //主站审核通过
            .handle(new AuditMiguSuccessHandler())       //结束
            .build();
        return new StateMachine(stateMachineConfig);
    }

}
