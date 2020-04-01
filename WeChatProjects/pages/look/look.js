// pages/look/look.js
const app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
      li: ["文章", "漫画"],
      id: 0,
      show:true,
      key:""
  },
    onInput: function (e) {
        this.setData({
            key: e.detail.value
        })},
    handsearch: function () {
        let that = this
        console.log(this.data.key)
        let key = this.data.key
        if(key=="")
        {
            this.handgetData()
            return 0
        }
        wx.request({
            url: app.globalData.url + '/article/findAllArticle/key',
            data:{
                key:key
            },
            success(res) {
                that.setData({
                    articleList: res.data.queryResult.list
                })
            }
        })
        wx.request({
            url: app.globalData.url + '/article/findAllCartoon/key',
            data: {
                key: key
            },
            success(res) {
                that.setData({
                    cartoonList: res.data.queryResult.list
                })
            }
        })

    },
    select: function (e) {
        var id = e.currentTarget.dataset.id
        if (this.data.id == id)
            return 0
        this.setData({
            id: id,
            show:!this.data.show
        })
    },
    hindinfor(e){
        wx.navigateTo({
            url: "/pages/look/infor/infor?url=show&id=" + e.currentTarget.dataset.id,
        })
    }, 
    handcartoon(e){
        wx.navigateTo({
            url: "/pages/look/infor/infor?url=cartoon&id=" + e.currentTarget.dataset.id,
        })

    },
    handgetData(){
        let that = this
        wx.request({
            url: app.globalData.url + '/article/findAllArticle',
            success(res) {
                that.setData({
                    articleList: res.data.queryResult.list
                })
            }
        })
        wx.request({
            url: app.globalData.url + '/article/findAllCartoon',
            success(res) {
                that.setData({
                    cartoonList: res.data.queryResult.list
                })
            }
        })
    },
  onLoad: function (options) {
      this.handgetData()
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
      this.handgetData()

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
      this.handgetData()
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