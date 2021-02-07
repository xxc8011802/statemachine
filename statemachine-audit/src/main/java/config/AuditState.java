package config;

/**
 * Created by jetty on 2019/7/31.
 */
public enum AuditState
{
    SUBMIT_AUDIT,//提交审核
    AUDIT_ASSIGN_FIRST,//审核分配初审
    AUDIT_ASSIGN_REVIEW,//审核分配复审
    AUDIT_FIRST, //初审审核
    AUDIT_REVIEW,//复审审核
    AUDIT_MIGU,//主站审核
}
