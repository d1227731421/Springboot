// pages/home/home.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        length: 0,
        axis: [ ],
        images:[],
        flag:false,
        cacheSetRoom:[]
    },
    chooseImage(e) {
        let that = this
        wx.chooseImage({
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success(res) {
                const tempFilePaths = res.tempFilePaths
                that.setData({
                    images: tempFilePaths,
                    imgflag: true
                })
                console.log(res)
            },
        })
    },
    handsubmit() {
        let that=this
       if(this.data.images[0]==null){
           wx.request({
               url: '',
               url: app.globalData.url + '/cof/saveNoImg',
               data:{
                   userId: that.data.id,
                   content: that.data.text
               },
                 success(res) {
                   if (res.statusCode == 200) {
                       that.query(that.data.id)
                       that.setData({
                           length: 0,
                           text: "",
                           imgflag: false
                       })
                   }
               }
           })
           return 0;
       }
        wx.uploadFile({
            url: app.globalData.url + '/cof/save',
            filePath: that.data.images[0],
            name: 'file',
            formData: {
                userId: that.data.id,
                content: that.data.text
            },
            success(res) {
                if (res.statusCode==200)
                {
                    that.query(that.data.id)
                    that.setData({
                        length : 0,
                        text:"",
                        imgflag: false, 
                        images:[]
                    })
                }  
            },
            fail(err){
                console.log(err)
            }
        })
    },
    handinput(e) {
        console.log(e.detail.value)
        this.setData({
            text: e.detail.value,
            length: e.detail.value.length
        })
    },
    query(id){
        let that=this
        wx.request({
            url: app.globalData.url+'/cof/query',
            data:{
                userId: id
            },
            success(res){
                console.log(res.data)
                that.setData({
                    axis: res.data.queryResult.list
                })
            }
        })
    },
    initSocket(id) {
        let that = this
        wx.connectSocket({
            url: 'ws://127.0.0.1:6969/ws/' + id
        })
        wx.onSocketOpen(function(res) {
            console.log("连接成功")
        })
        wx.onSocketMessage(function(e) {
            if (that.data.flag==false){
                let cache = JSON.parse(e.data)
                console.log(cache[0].roomId != null)
                if(cache[0].roomId!=null){
                    let cacheSetRoom = []
                    for (let i = 0; i < cache.length; i++) {
                        cacheSetRoom.push({
                            message: cache[i].messageList[cache[i].messageList.length - 1],
                            roomid: cache[i].roomId,
                            tag: cache[i].messageList.length
                        })
                        wx.getStorage({
                            key: cache[i].roomId,
                            success: function (res) {
                                let msglist = res.data
                                for (let j = 0; j < cache[i].messageList.length; j++) {
                                    let m = cache[i].messageList[j]
                                    msglist.push({
                                        speaker: 'server',
                                        contentType: 'text',
                                        content: m.message,
                                        avatarUrl: m.avatarUrl
                                    })
                                    wx.setStorage({
                                        key: cache[i].roomId,
                                        data: msglist,
                                    })
                                }
                            },
                            fail(err){
                                let laserrt = []
                                for (let j = 0; j < cache[i].messageList.length; j++) {
                                    let m = cache[i].messageList[j]
                                    laserrt.push({
                                        speaker: 'server',
                                        contentType: 'text',
                                        content: m.message,
                                        avatarUrl: m.avatarUrl
                                    })
                                }
                                wx.setStorage({
                                    key: cache[i].roomId,
                                    data: laserrt,
                                })
                           
                            }
                        })
                    }
                    that.setData({
                        cacheSetRoom: cacheSetRoom,
                    })
                   
                }
                that.setData({
                    flag: true
                })
                return 0;

            }
            
            if (that.data.flag){
                let msg = JSON.parse(e.data)
                let roomid = msg.roomId
                console.log(JSON.parse(e.data))
                wx.getStorage({
                    key: roomid,
                    success: function (res) {
                        let msglist = res.data
                        msglist.push({
                            speaker: 'server',
                            contentType: 'text',
                            content: msg.message,
                            avatarUrl: msg.avatarUrl
                        })
                        wx.setStorage({
                            key: roomid,
                            data: msglist,
                        })
                    },
                    fail: function (err) {
                        let laserrt = [{
                            speaker: 'server',
                            contentType: 'text',
                            content: msg.message,
                            avatarUrl: msg.avatarUrl
                        }]
                        wx.setStorage({
                            key: roomid,
                            data: laserrt,
                        })
                    }
                })
                that.setRoom(msg.message, roomid)
            }
        })
    },
    cacheSetRoom(obj){
        console.log(obj)
        wx.getStorage({
            key: 'userinfo',
            success: function (res) {
                let user = res.data
                for (let j = 0; j < obj.length; j++) {
                    for (let i = 0; i < user.chatRooms.length; i++) {
                        if (user.chatRooms[i].number == obj[j].roomid) {
                            user.chatRooms[i].last = obj[j].message.message
                            if (user.chatRooms[i].tag == null) {
                                user.chatRooms[i].tag = obj[j].tag
                            } else if (user.chatRooms[i].tag <= 99) {
                                user.chatRooms[i].tag += obj[j].tag
                            } if (user.chatRooms[i].tag > 99) {
                                user.chatRooms[i].tag = "99+"
                            }
                        }
                    }
                }
                console.log(user)
                wx.setStorage({
                    key: 'userinfo',
                    data: user,
                    fail(err){
                        console.log(err)
                    }
                })
            }
        })
    },
    setRoom(message, roomid) {
        wx.getStorage({
            key: 'userinfo',
            success: function(res) {
                let user = res.data
                for (let i = 0; i < user.chatRooms.length; i++) {
                    if (user.chatRooms[i].number == roomid) {
                        user.chatRooms[i].last = message
                        if (user.chatRooms[i].tag == null) {
                            user.chatRooms[i].tag = 1
                        } else if (user.chatRooms[i].tag <= 99) {
                            user.chatRooms[i].tag += 1
                        } else {
                            user.chatRooms[i].tag = "99+"
                        }
                    }
                }
                wx.setStorage({
                    key: 'userinfo',
                    data: user,
                })
            },
        })
    },
    initData(id) {
        let that = this
        wx.request({
            url: 'http://127.0.0.1:6969/user/findbyid',
            data: {
                id: id
            },
            success: function(res) {
                let getdata = res.data.queryResult.list[0]
                console.log(getdata.chatRooms)
                wx.getStorage({
                    key: 'userinfo',
                    success: function(res) {
                        console.log(res.data.chatRooms)
                        getdata.chatRooms = res.data.chatRooms
                        wx.setStorage({
                            key: "userinfo",
                            data: getdata
                        })
                    },
                })

            },

        })
    },
 
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        console.log(options)
        app.globalData.id=options.id
        this.setData({
            id: options.id
        })
        this.initSocket(options.id)
        this.initData(options.id)
        this.query(options.id)
       
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {
        
        this.cacheSetRoom(this.data.cacheSetRoom)
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {
       

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})