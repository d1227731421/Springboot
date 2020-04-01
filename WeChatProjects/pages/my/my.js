// pages/my/my.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        madalflag: false,
        registerName: "",
        registerPassword: "",
        registerPasswordSure:""
    },
    gohistory(){
        wx.navigateTo({
            url: "/pages/my/history/history?id="+app.globalData.id,
        })
    },
    handupdate(){
        this.setData({
            madalflag: true,
            registerName: this.data.userName,
            registerPassword: this.data.password,
            registerPasswordSure: this.data.password
        })
    },
    chooseImage: function (e) {
        var that = this
        wx.chooseImage({
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success: function (res) {
                const tempFilePaths = res.tempFilePaths
                that.upfile(tempFilePaths)
            },
        })
    },
    upfile(tempFilePaths) {
        let that = this
        wx.uploadFile({
            url: 'http://localhost:6969/file/litImg',
            filePath: tempFilePaths[0],
            name: 'file',
            success(res) {
                console.log(res.data)
                wx.request({
                    url: app.globalData.url + '/user/save',
                    data: {
                        id: that.data.id,
                        headPortrait: res.data,
                        password: that.data.password,
                        userName: that.data.userName

                    },
                    success(res) {
                        that.setUseinfor()
                    }
                })
            }
        })
    },
    setUseinfor() {
        let that = this
        wx.request({
            url: 'http://127.0.0.1:6969/user/findbyid',
            data: {
                id: that.data.id
            },
            success(res) {
                if (res.data.success == true) {
                    let userinfo = res.data.queryResult.list[0]
                    that.setData({
                        userName: userinfo.userName,
                        headPortrait: userinfo.headPortrait,
                        id: userinfo.id,
                        password: userinfo.password
                    })
                    wx.setStorage({
                        key: 'userinfo',
                        data: userinfo,
                    })
                }
            }
        })
    },
    onLoad: function (options) {
        let that = this
        wx.getStorage({
            key: 'userinfo',
            success: function (res) {
                that.setData({
                    userName: res.data.userName,
                    headPortrait: res.data.headPortrait,
                    id: res.data.id,
                    password: res.data.password
                })
            },
        })
    },

    modalCancel() {
        this.setData({
            madalflag: false,
            registerName: "",
            registerPassword: "",
            registerPasswordSure: "",
            tipName: "",
            tipPasswd: ""
        })
    },
    modalConfirm() {
        this.sureName()
        this.surePassword()
        let that = this
        console.log("qqqqqqqq")
        if (this.data.tipName == "" && this.data.tipPasswd == "") {
            wx.request({
                url: app.globalData.url + '/user/save', //仅为示例，非真实的接口地址
                data: {
                    id:that.data.id,
                    userName: that.data.registerName,
                    password: that.data.registerPassword,
                    headPortrait: that.data.headPortrait
                },
                success(res) {
                    wx.showToast({
                        title: '修改成功',
                        icon: 'success',
                    })
                    setTimeout(function () {
                        that.setUseinfor()
                        that.setData({
                            madalflag: false,
                            registerName: "",
                            registerPassword: "",
                            registerPasswordSure: ""
                        })
                    }, 1000)
                }
            })
        }
    },

    nameRegister(e) {
        this.setData({
            registerName: e.detail.value
        })
        if (this.data.registerName.length > 6) {
            this.setData({
                tipName: "长度超过6个字符"
            })
        } else {
            this.setData({
                tipName: ""
            })
        }
    },


    passwordRegister(e) {
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
    sureName() {
        if (this.data.registerName.length > 6) {
            this.setData({
                tipName: "长度超过6个字符"
            })
        } else if (this.data.registerName.length == 0) {
            this.setData({
                tipName: "昵称不能为空值"
            })
        }
        else {
            let that = this
            wx.request({
                url: app.globalData.url + '/user/sureNameById',
                data: {
                    userName: that.data.registerName,
                    id: that.data.id
                },
                success(res) {
                    let tip = ""
                    if (res.data.success != true) {
                        tip = "昵称已经被使用"
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

})