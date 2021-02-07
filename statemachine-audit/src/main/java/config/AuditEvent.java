package config;

import lombok.Data;

/**
 * Created by jetty on 2019/7/31.
 */
public enum AuditEvent
{
    SUBMIT_AUDIT,//提交审核
    AUDIT_ASSIGN,//审核分配
    AUDIT_ASSIGN_FIRST_TRUE,//初审已分配
    AUDIT_ASSIGN_REVIEW_TRUE,//复审已分配
    AUDIT_FIRST,//初审审核
    AUDIT_FIRST_REJECT, //初审驳回
    AUDIT_FIRST_AGREE, //初审通过
    AUDIT_FIRST_AGREE_WITHOUT_REVIEW,//初审通过并且未安排复审
    AUDIT_REVIEW,//复审审核
    AUDIT_REVIEW_REJECT,//复审驳回
    AUDIT_REVIEW_AGREE,//复审通过
    AUDIT_MIGU_AGREE,//主站审核通过
    AUDIT_MIGU_REJECT,//主站审核驳回
}
