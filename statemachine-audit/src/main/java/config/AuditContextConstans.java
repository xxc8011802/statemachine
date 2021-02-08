package config;

import com.github.shxz130.statemachine.core.config.BaseStateMachineKey;

public class AuditContextConstans extends BaseStateMachineKey
{
    //离开作家提交环节
    public static final String LEAVE_PERMIT="LEAVE_PERMIT";

    //已分配了审核人员
    public static final String AUDIT_ASSIGN="AUDIT_ASSIGN";

    //已分配了复审审核人员
    public static final String AUDIT_ASSIGN_REVIEW="AUDIT_ASSIGN_REVIEW";

    //初审审核意见
    public static final String AUDIT_FIRST_SUGGESTION="AUDIT_FIRST_SUGGESTION";

    //复审审核意见
    public static final String AUDIT_REVIEW_SUGGESTION="AUDIT_REVIEW_SUGGESTION";

    //主站审核意见
    public static final String AUDIT_MIGU_SUGGESTION="AUDIT_MIGU_SUGGESTION";

}
