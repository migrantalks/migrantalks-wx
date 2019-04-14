package com.zgs.web;

import com.zgs.domain.Result;
import com.zgs.entity.User;
import com.zgs.service.IUserService;
import com.zgs.utils.JwtUtil;
import com.zgs.utils.MD5Util;
import com.zgs.utils.NetUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zgs
 * 登录接口
 */
@RestController
public class LoginController {

    private static final String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String WX_APPID = "wx916083cf08aa8e90";
    private static final String WX_SECRET = "3817182b16cd8b2db91deb04f681c97d";

    @Autowired
    IUserService userService;

    @ApiOperation(value = "登录" , notes="用户登录接口")
    @PostMapping(value="/api/login")
    public Result login(String username, String password){

        if (!StringUtils.isNotBlank(username)) {
            return Result.fail("登录账号不能为空");
        }
        if (!StringUtils.isNotBlank(password)) {
            return Result.fail("密码不能为空");
        }

        Condition condition = new Condition(User.class);
        condition.createCriteria().andEqualTo("loginName", username)
                .andEqualTo("status", "1");

        List<User> userList = userService.selectByCondition(condition);
        if (userList.size() < 1) {
            return Result.fail("用户不存在");
        }

        User user = userList.get(0);
        if (null == user) {
            return Result.fail("用户不存在");
        }

        try {
            String encryptPwd = MD5Util.encrypt(password);
            if (!encryptPwd.equals(user.getPass())) {
                return Result.fail("密码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!User.STATUS_LOCK.equals(user.getStatus())) {
            return Result.fail("账户已锁定");
        }

        String token = JwtUtil.createJWT(username);

        Map<String, Object> ret = new HashMap<>();
        ret.put("token", token);

        return Result.success(ret);
    }

    @ApiOperation(value = "微信登录" , notes="用户微信登录接口")
    @PostMapping(value="/api/wxLogin")
    public Result wxLogin(String code) {

        if (!StringUtils.isNotBlank(code)) {
            return Result.fail("用户登录凭证不能为空");
        }

        Map<String, String> param = new HashMap<>();
        param.put("appid", WX_APPID);
        param.put("secret", WX_SECRET);
        param.put("js_code", code);
        param.put("grant_type", "wx_login");

        String wxResult = NetUtil.doGet(WX_URL, param);
        System.out.println(wxResult);

        return Result.success(wxResult);
    }
}
