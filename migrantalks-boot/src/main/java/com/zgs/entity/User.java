package com.zgs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zgs
 */
@Table(name = "jx_org_user")
@Data
public class User implements Serializable {

    /**
     * 账户状态
     */
    public static final String STATUS_VALID = "1";

    public static final String STATUS_LOCK = "0";

    public static final String DEFAULT_THEME = "green";

    public static final String DEFAULT_AVATAR = "default.jpg";

    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登录账号
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 密码
     */
    private String pass;

    /**
     * 部门id
     */
    @Column(name = "dept_id")
    private Long deptId;

    @Transient
    private String deptName;
    @Transient
    private Long roleId;
    @Transient
    private String roleName;

    /**
     * 用户序号
     */
    @Column(name = "user_no")
    private Long userNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 手机客户端
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 账号类型
     */
    @Column(name = "account_type")
    private String accountType;

    /**
     * 性别 0：男 1：女
     */
    @Column(name = "user_sex")
    private String userSex;

    /**
     * 主题
     */
    private String theme;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 数据状态 0：无效 1：有效 2：锁定
     */
    private String status;

    /**
     * 公司id
     */
    @Column(name = "company_id")
    private Long companyId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private Long createUser;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    /**
     * 修改人
     */
    @Column(name = "modify_user")
    private Long modifyUser;

    /**
     * shiro-redis v3.1.0 必须要有getAuthCacheKey()或者getId()方法
     * # Principal id field name. The field which you can get unique id to identify this principal.
     * # For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc.
     * # Remember to add getter to this id field. For example, getUserId(), getUserName(), getEmail(), etc.
     * # Default value is authCacheKey or id, that means your principal object has a method called "getAuthCacheKey()" or "getId()"
     * @return userId as Principal id field name
     */
    public Long getAuthCacheKey() {
        return id;
    }
}