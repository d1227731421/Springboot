const app=getApp()
Page({
    data: {
        name: '',
        password: '',
        madalflag:false,
        registerName: "", 
        registerPassword:""
    },

    bindgetuserinfo(e){
        if (e.detail.errMsg =="getUserInfo:ok"){
            console.log(e.detail.userInfo.avatarUrl)
            this.setData({
                avatarUrl: e.detail.userInfo.avatarUrl,
                madalflag: true
            })
        }
      
    
    },
    modalCancel(){
        this.setData({
            madalflag: false,
            registerName: "",
            registerPassword: "",
            registerPasswordSure: "",
            tipName:"",
            tipPasswd:""
        })
    },
    modalConfirm(){
        this.sureName()
        this.surePassword()
        let that=this
       if(this.data.tipName==""&&this.data.tipPasswd==""){
           wx.request({
               url: app.globalData.url + '/user/save', //仅为示例，非真实的接口地址
               data: {
                   userName: that.data.registerName,
                   password: that.data.registerPassword,
                   headPortrait: that.data.avatarUrl
               },
               success(res){
                   wx.showToast({
                       title: '注册成功',
                       icon: 'success',
                   })
                   setTimeout(function () {
                       that.setData({
                           madalflag: false,
                           registerName: "",
                           registerPassword: "",
                           registerPasswordSure:""
                       })
                   }, 1000)
               }
           })
       }
    },
    // 获取输入账号 
    nameInput (e) {
        this.setData({
            name: e.detail.value
        })
    },
    nameRegister(e){
        this.setData({
            registerName: e.detail.value
        })
        if (this.data.registerName.length > 6) {
            this.setData({
                tipName: "长度超过6个字符"
            })
        } else{
            this.setData({
                tipName: ""
            })
        }
      },

    // 获取输入密码 
    passwordInput (e) {
        this.setData({
            password: e.detail.value
        })
    },
    passwordRegister (e) {
        this.setData({
            registerPassword: e.detail.value
        })
        this.surePassword()
    },
    passwordRegistertSure(e) {
        this.setData({
            registerPasswordSure: e.detail.value
        })
        this.surePassword()
    },
    sureName(){
        if (this.data.registerName.length>6){
            this.setData({
                tipName:"长度超过6个字符"
            })
        } else if (this.data.registerName.length == 0)
        {
            this.setData({
                tipName: "昵称不能为空值"
            })
        }
        else {
            let that=this
            wx.request({
                url: app.globalData.url + '/user/sureName',
                data:{
                    userName:that.data.registerName
                },
                success(res){
                    let tip=""
                 if(res.data.success!=true) {
                     tip ="昵称已经被使用"
                 }
                    that.setData({
                        tipName: tip
                    })
                }
            })
            
        }
    },
    surePassword() {
        if (this.data.registerPassword.length < 6) {
            this.setData({
                tipPasswd: "长度少于6个字符"
            })
        } else if (this.data.registerPasswordSure != this.data.registerPassword) {
            this.setData({
                tipPasswd: "两次密码不一致"
            })
        } else {
            this.setData({
                tipPasswd: ""
            })
        }
    },
    // 登录 
    login() {
        console.log(this.data.name, this.data.password)
        if (this.data.name.length == 0 || this.data.password.length == 0) {
            wx.showToast({
                title: '账号或密码为空',
                icon: 'loading',
                duration: 2000
            })
        } else {
           this.handlogin(this)
        }
    },
    handlogin(that) {
        wx.request({
            url: app.globalData.url + '/user/login',
            data: {
                username: that.data.name,
                password: that.data.password
            },
            success: function (res) {
                if (res.data.success == true) {
                    let info = res.data.queryResult.list[0]
                    wx.setStorage({
                        key: "userinfo",
                        data: info,
                        success: function (res) {
                            console.log(res)
                        },
                        fail: function (err) {
                            console.log(err)
                        }
                    })
                    wx.reLaunch({
                        url: "../home/home?id="+info.id,
                        fail: function (err) {
                            console.log(err)
                        }
                    })

                }else{
                    wx.showModal({
                        title: '提示',
                        content: '昵称或密码错误',
                        showCancel:false
                    })
                }
            },
            fail: function (res) { },
        })
    }
}) 
