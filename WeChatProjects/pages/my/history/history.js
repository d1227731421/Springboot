// pages/my/history/history.js
const app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        show:true,
        li: ["文章", "漫画"],
        id: 0,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    select: function (e) {
        var id = e.currentTarget.dataset.id
        if (this.data.id == id)
            return 0
        this.setData({
            id: id,
            show: !this.data.show
        })
    },
    hindinfor(e) {
        wx.navigateTo({
            url: "/pages/look/infor/infor?url=show&id=" + e.currentTarget.dataset.id,
        })
    },
    handcartoon(e) {
        wx.navigateTo({
            url: "/pages/look/infor/infor?url=cartoon&id=" + e.currentTarget.dataset.id,
        })

    },
    getData(){
        let that = this
        wx.request({
            url: app.globalData.url + '/history/find',
            data: {
                userId: that.data.userId
            },
            success(res) {
                let article = []
                let cartoon = []
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].img != null) {
                        cartoon.push(res.data[i])
                    } else {
                        article.push(res.data[i])
                    }
                }
                that.setData({
                    article: article,
                    cartoon: cartoon
                })
            }
        })
    },
    onLoad: function (options) {
        this.setData({
            userId: options.id
        })
       this.getData()
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        this.getData()
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})