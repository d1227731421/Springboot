// pages/chat/chat.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        key: "",
        madalflag: false
    },
    chooseImage: function(e) {
        let that = this
        wx.chooseImage({
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success: function(res) {
                const tempFilePaths = res.tempFilePaths
                that.setData({
                    images: tempFilePaths,
                    imgflag: true
                })
                console.log(res)
            },
        })
    },

    onInput: function(e) {
        this.setData({
            key: e.detail.value
        })

    },
    handroom: function(e) {
        this.myNavigate(e.currentTarget.dataset.roomid)
    },
    myNavigate:function(roomid){
        wx.navigateTo({
            url: "/pages/chat/chatroom/chatroom?roomId=" +roomid,
            success: function (res) { },
            fail: function (res) { },
            complete: function (res) { },
        })
    },
    modalConfirm: function() {
        let that = this
        wx.uploadFile({
            url: app.globalData.url + '/chat/create',
            filePath: that.data.images[0],
            name: 'file',
            formData: {
                userid: app.globalData.id,
                name: that.data.name,
                number: that.data.key
            },
            success(res) {
                let dataR = JSON.parse(res.data)
                let data = dataR.queryResult.list[0]
                console.log(data)
                that.data.roomlist.push({
                    id: data.id,
                    number: data.number,
                    name: data.name,
                    img: data.img,
                    last: "",
                    tag: null
                })
                that.setRoom()
                that.setData({
                    madalflag: false,
                    images: "",
                    imgflag: false,
                    name: "",
                    roomlist: that.data.roomlist
                })
                that.socketReload()
            }
        })


    },
    modalCancel: function() {
        this.setData({
            madalflag: false,
            images: "",
            imgflag: false,
            key: "",
            name: ""
        })

    },
    handinput: function(e) {
        this.setData({
            name: e.detail.value
        })
    },
    handsearch: function() {
        let that = this
        console.log(this.data.key)
        let number = this.data.key
        wx, wx.request({
            url: app.globalData.url + '/chat/query',
            data: {
                number: number
            },
            success: function(res) {
                let result = res.data
                if (result.success == true) {
                    let room = result.queryResult.list[0]
                    let flag = that.data.roomlist.filter(item => item.number == room.number)
                    if (flag.length > 0) {
                        wx.showModal({
                            title: '提示',
                            content: '你已经加入群聊' + "   '" + room.name + "'",
                            showCancel: false,
                        })
                    } else {
                        wx.showModal({
                            title: '提示',
                            content: '找到群聊' + "   '" + room.name + "'" + "," + "是否加入?",
                            success(res) {
                                if (res.confirm) {
                                    console.log(room) 
                                    wx.request({
                                        url: app.globalData.url + '/chat/join',
                                        data: {
                                            userid: app.globalData.id,
                                            img: room.img,
                                            name: room.name,
                                            number: room.number,
                                        },
                                        success:function(res){
                                            let data = res.data.queryResult.list[0]
                                            console.log(data)
                                            that.data.roomlist.push({
                                                id: data.id,
                                                number: data.number,
                                                name: data.name,
                                                img: data.img,
                                                last: "",
                                                tag: null
                                            })
                                            that.setRoom()
                                            that.setData({
                                                roomlist: that.data.roomlist,
                                                key:""
                                            })
                                            that.socketReload()
                                        }
                                    })
                                } else if (res.cancel) {
                                    console.log('用户点击取消')
                                }
                            }
                        })
                    }

                } else {
                    wx.showModal({
                        title: '提示',
                        content: '没有找到相关群聊,是否创建?',
                        success(res) {
                            if (res.confirm) {
                                that.setData({
                                    madalflag: true
                                })
                            } else if (res.cancel) {
                                console.log('用户点击取消')
                            }
                        }
                    })
                }
            },
        })

    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },
    socket: function() {
        let that = this
        wx.onSocketMessage(function(e) {
            let msg = JSON.parse(e.data)
            let roomid = msg.roomId
            console.log(JSON.parse(e.data))
            let last = {
                speaker: 'server',
                contentType: 'text',
                content: msg.message,
                avatarUrl: msg.avatarUrl
            }
            wx.getStorage({
                key: roomid,
                success: function(res) {
                    let msglist = res.data
                    msglist.push(last)
                    wx.setStorage({
                        key: roomid,
                        data: msglist,
                    })
                },
                fail: function(err) {
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
            for (let i = 0; i < that.data.roomlist.length; i++) {
                if (that.data.roomlist[i].number == roomid) {

                    that.data.roomlist[i].last = msg.message
                    if (that.data.roomlist[i].tag == null) {
                        that.data.roomlist[i].tag = 1
                    } else if (that.data.roomlist[i].tag <= 99) {
                        that.data.roomlist[i].tag += 1
                    } else {
                        that.data.roomlist[i].tag = "99+"
                    }
                }
            }
            that.setData({
                roomlist: that.data.roomlist
            })
            that.setRoom()
        })
    },
    socketReload:function(){
        wx.closeSocket()
        wx.connectSocket({
            url: 'ws://127.0.0.1:6969/ws/' + app.globalData.id
        })
        wx.onSocketOpen(function (res) {
            console.log("连接成功")
        })
    },
    setRoom: function() {
        let that = this
        wx.getStorage({
            key: 'userinfo',
            success: function(res) {
                res.data.chatRooms = that.data.roomlist
                wx.setStorage({
                    key: 'userinfo',
                    data: res.data
                })
            },
        })


    },
    onShow: function() {
        let that = this
        wx.getStorage({
            key: 'userinfo',
            success: function(res) {
                console.log(res.data.chatRooms)
                that.setData({
                    roomlist: res.data.chatRooms
                })
                that.socket()

            },
        })

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