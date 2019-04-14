// pages/login/login.js
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
  /**
   * 普通登录
   */
  toLogin: function(e){
    var param = e.detail.value;
    var username = param.username.trim();
    var password = param.password.trim();

    if (!username.length) {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '请输入账号'
      });
      return false;
    }

    if (!password.length) {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '请输入密码'
      });
      return false;
    }

    app.request.req('/api/login', param, function(res){
      if (res.code != 200) {
        wx.showModal({
          title: '提示',
          showCancel: false,
          content: res.message
        });
        return false;
      }

      wx.redirectTo({
        url: '../main/main?username=' + username + '&token=' + res.data.token
      })
    })
  },
  /**
   * 微信登录
   */
  wxLogin: function(){
    wx.login({
      success(res) {
        if (res.code) {
          var param = {
            code: res.code
          };
          app.request.req('/api/wxLogin', param, function (res) {
            if (res.code != 200) {
              wx.showModal({
                title: '提示',
                showCancel: false,
                content: res.message
              });
              return false;
            }
          })
        } else {
          wx.showModal({
            title: '登录失败',
            showCancel: false,
            content: res.errMsg
          });
        }
      }
    })
  }
})