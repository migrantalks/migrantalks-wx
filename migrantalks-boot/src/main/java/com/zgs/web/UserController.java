package com.zgs.web;

import com.zgs.domain.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @ApiOperation(value = "新增用户" , notes="新增用户接口")
    @PostMapping(value="/add")
    public Result createUser(){
        return Result.success("");
    }
}
