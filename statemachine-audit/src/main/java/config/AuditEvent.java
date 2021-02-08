package config;

import lombok.Data;

/**
 * Created by jetty on 2019/7/31.
 */
public enum AuditEvent
{
    SUBMIT_AUDIT,//提交审核
    AUDIT_ASSIGN,//等待审核分配
    AUDIT_ASSIGN_FINISH,//审核人员分配完成
    AUDIT_FIRST,//等待初审审核
    AUDIT_FIRST_REJECT, //初审驳回
    AUDIT_FIRST_AGREE, //初审通过
    AUDIT_REVIEW,//等待复审审核
    AUDIT_REVIEW_REJECT,//复审驳回
    AUDIT_REVIEW_AGREE,//复审通过
    AUDIT_MIGU,//等待主站审核
    AUDIT_MIGU_AGREE,//主站审核通过
    AUDIT_MIGU_REJECT,//主站审核驳回
}
