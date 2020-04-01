const app = getApp();
var inputVal = '';
var msgList = [];
var windowWidth = wx.getSystemInfoSync().windowWidth;
var windowHeight = wx.getSystemInfoSync().windowHeight;
var keyHeight = 0;


function initData(that, roomId) {
    inputVal = '';
    wx.getStorage({
        key: roomId,
        success: function(res) {
            console.log(res.data)
            let msgList = res.data
            that.setData({
                msgList,
                inputVal
            })
        },
        fail: function(err) {
            let msgList = [{
                speaker: "",
                contentType: "",
                content: "",
                avatarUrl: ""
            }]
            that.setData({
                msgList: msgList,
                inputVal: ""
            })
        }
    })

}

/**
 * 计算msg总高度
 */
// function calScrollHeight(that, keyHeight) {
//   var query = wx.createSelectorQuery();
//   query.select('.scrollMsg').boundingClientRect(function(rect) {
//   }).exec();
// }

Page({

    data: {
        scrollHeight: '100vh',
        inputBottom: 0
    },
    handinput(e){
            this.setData({
                e:e
            })
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
                fail(err){
                    let msglist=[]
                    msglist.push(last)
                    wx.setStorage({
                        key: roomid,
                        data: msglist,
                    })
                }
            })
            let msd = that.data.msgList
            msd.push(last)
            that.setData({
                msgList: msd,
                toView: 'last'
            })
            that.setRoom(msg.message, roomid,false)

        })
    },
    setRoom: function (message,roomid,flag){
        wx.getStorage({
            key: 'userinfo',
            success: function (res) {
                let user = res.data
                    for (let i = 0; i < user.chatRooms.length; i++) {
                        if (user.chatRooms[i].number == roomid) {
                            user.chatRooms[i].last = message
                            if(false==false)
                            {
                                user.chatRooms[i].tag = null

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
    onLoad: function(options) {

        console.log(options.roomId)
        initData(this, options.roomId);
        this.setRoom("", options.roomId,false)
        this.setData({
            roomid: options.roomId,
        });
        console.log(this.data.msgList)
        let that = this
        wx.getStorage({
            key: 'userinfo',
            success: function(res) {
                that.setData({
                    avatarUrl: res.data.headPortrait,
                });
            },
        })
        this.socket()
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {

    },


    /**
     * 获取聚焦
     */
    focus: function(e) {
        this.setData({
            toView: 'last',
        })
        //    // 计算msg高度
        //     calScrollHeight(this, keyHeight);

    },

    //失去聚焦(软键盘消失)
    // blur: function(e) {
    //     // this.setData({
    //     //     scrollHeight: '100vh',
    //     //     inputBottom: 0
    //     // })


    // },

    /**
     * 发送点击监听
     */
    handsendClick(){
        this.sendClick(this.data.e)
    },
    sendClick: function(e) {
        this.setData({
            toView: 'last',
        })
        let that = this
        let msgcash = this.data.msgList
        msgcash.push({
            speaker: 'customer',
            contentType: 'text',
            content: e.detail.value,
            avatarUrl: this.data.avatarUrl
        })
        inputVal = '';
        this.setData({
            msgList: msgcash,
            inputVal: ""
        });
        wx.setStorage({
            key: that.data.roomid,
            data: that.data.msgList,
        })
        let msg = {
            back: true,
            message: e.detail.value,
            roomId: this.data.roomid,
            avatarUrl: this.data.avatarUrl
        }
        wx.sendSocketMessage({
            data: JSON.stringify(msg)
        })
        this.setRoom(e.detail.value, this.data.roomid,true)

    },

    /**
     * 退回上一页
     */
    toBackClick: function() {
        wx.navigateBack({})
    }

})