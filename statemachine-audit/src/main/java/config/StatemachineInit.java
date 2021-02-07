package config;

import com.github.shxz130.statemachine.core.config.Handler;
import com.github.shxz130.statemachine.core.fire.StateMachine;
import com.github.shxz130.statemachine.core.fire.StateMachineConfig;
import com.github.shxz130.statemachine.core.fire.StateMachineFactory;
import config.handler.*;

/**
 * Created by jetty on 2019/7/31.
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
                .to(AuditState.AUDIT_ASSIGN_FIRST) //提交后，请求审核分配初审
                 .build();

        stateMachineConfig.from(AuditState.AUDIT_ASSIGN_FIRST) //编辑 分配审核人员状态
                .permit(AuditEvent.AUDIT_ASSIGN)  //审核分配事件
                .handle(new AuditAssignWaitHandler())     //等待审核人员分配处理
                .to(AuditState.AUDIT_ASSIGN_FIRST)    //下一状态，初审状态
                .build();

        stateMachineConfig.from(AuditState.AUDIT_ASSIGN_FIRST) //编辑 分配审核人员状态
            .permit(AuditEvent.AUDIT_ASSIGN)  //审核分配事件
            .handle(new AuditAssignWaitHandler())     //等待审核人员分配处理
            .to(AuditState.AUDIT_ASSIGN_REVIEW)    //下一状态，初审状态
            .build();

        stateMachineConfig.from(AuditState.AUDIT_ASSIGN) //编辑 分配审核人员状态
            .permit(AuditEvent.AUDIT_ASSIGN_FIRST_TRUE)  //审核分配事件
            .handle(new AuditAssignFirstHandler())     //审核人员分配处理
            .to(AuditState.AUDIT_FIRST)    //下一状态，初审状态
            .build();

        stateMachineConfig.from(AuditState.AUDIT_FIRST) //初审状态
            .permit(AuditEvent.AUDIT_FIRST)  //等待初审
            .handle(new AuditAssignFirstHandler())     //审核人员分配处理
            .to(AuditState.AUDIT_FIRST)    //下一状态，初审状态
            .build();

        stateMachineConfig.from(AuditState.AUDIT_FIRST) //初审状态
            .permit(AuditEvent.AUDIT_FIRST_AGREE)  //初审审核通过
            .handle(new AuditReviewWaitHandler())     //等待复审
            .to(AuditState.AUDIT_REVIEW)    //下一状态，复审状态
            .build();

        stateMachineConfig.from(AuditState.AUDIT_FIRST) //初审状态
            .permit(AuditEvent.AUDIT_FIRST_AGREE_WITHOUT_REVIEW)  //初审审核通过且未分配复审
            .handle(new AuditReviewWaitHandler())     //等待主站审核
            .to(AuditState.AUDIT_REVIEW)    //下一状态，主站审核状态
            .build();

        stateMachineConfig.from(AuditState.AUDIT_FIRST) //初审状态
            .permit(AuditEvent.AUDIT_FIRST_REJECT)  //初审驳回
            .handle(new AuditFailHandler())     //审核人员分配处理
            .build();//驳回后则结束





        /*stateMachineConfig.from(LeavePermitState.LEADER_PERMIT) //领导审批
                .permit(LeavePermitEvent.LEADER_PERMIT_AGREE)   //领导同意
                .handle(new CeoPermitHandler())                 //领导同意之后CEO审批
                .to(LeavePermitState.CEO_PERMIT)                //ceo审批
                .build();

        stateMachineConfig.from(LeavePermitState.LEADER_PERMIT)  //领导审批
                .permit(LeavePermitEvent.LEADER_PERMIT_DISAGREE) //领导不同意
                .handle(new PermitFailHandler())                //假条失败
                .build();

        stateMachineConfig.from(LeavePermitState.CEO_PERMIT)   //CEO审批
                .permit(LeavePermitEvent.CEO_PERMIT_AGREE)      //ceo审批同意
                .handle(new PermitSuccessHandler())             //假条成功
                .build();


        stateMachineConfig.from(LeavePermitState.CEO_PERMIT)       //ceo审批
                .handle(new PermitFailHandler())                //ceo审批不通过
                .permit(LeavePermitEvent.CEO_PERMIT_DISAGREE)      //假条失败
                .build();*/

        return new StateMachine(stateMachineConfig);
    }

}
