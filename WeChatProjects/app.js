//app.js
App({
    onLaunch: function () {
        let that = this
        wx.getStorage({
            key: 'userinfo',
            success(res) {
                // that.initSocket(res.data.id)
                // that.initData(res.data.id)
                if (res.data != null) {
                    that.globalData.id = res.data.id
                    let id = res.data.id
                    wx.request({
                        url: 'http://127.0.0.1:6969/user/findbyid',
                        data:{
                            id:id
                        },
                        success(res){
                            if (res.data.success==true){
                                let userinfo = res.data.queryResult.list[0]
                                wx.setStorage({
                                    key: 'userinfo',
                                    data: userinfo,
                                    success(res){
                                        wx.reLaunch({
                                            url: "/pages/home/home?id=" + id,
                                            fail: function (err) {
                                                console.log(err)
                                            }
                                        })
                                    }
                                }) 
                            }
                        }
                    })
                   
                   
                }
            }
        })
        
    },
    globalData: {
        url: "http://127.0.0.1:6969",
        id:""
    }
})