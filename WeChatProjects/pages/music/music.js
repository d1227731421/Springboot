// pages/music/music.js
const app=getApp()
Page({

  data: {
      madalflag:false,
      key:"",
      flag:true,
      index:0
  },
    onInput: function (e) {
        this.setData({
            key: e.detail.value
        })
    },
    handsearch: function () {
        let that = this
        console.log(this.data.key)
        let key = this.data.key
        if (key == "") {
            this.getMusicTable()
            return 0
        }
        wx.request({
            url: app.globalData.url + '/tableMusic/find/key',
            data: {
                key: key
            },
            success(res) {
                that.setData({
                    musicTableList: res.data.queryResult.list
                })
            }
        })


    },
getMusicTable(){
    let that=this
    wx.request({
        url: app.globalData.url + '/tableMusic/find',
        success(res) {
            console.log(res.data.queryResult.list)
            that.setData({
                musicTableList: res.data.queryResult.list
            })
        }
    })
},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      let that=this
     this.getMusicTable()
      let InnerAudioContext=wx.createInnerAudioContext()
      this.setData({
          InnerAudioContext: InnerAudioContext
      })
      InnerAudioContext.onEnded(function (){
         that. next()
      })
  },
    pause(){
        this.data.InnerAudioContext.pause()
        this.setData({
            flag:true
        })
    },
    iconPlay(){
        if (this.data.InnerAudioContext.src != "") {
            this.data.InnerAudioContext.play()
            this.setData({
                flag: false,
                index: 0,
            })
        } else {
            let music = this.data.musicList[0]
            this.data.InnerAudioContext.src = music.url
            this.data.InnerAudioContext.play()
            this.setData({
                flag: false,
                index: 0,
            })
        }
    },
    back(){
        if(this.data.index==0){
            let music = this.data.musicList[0]
            this.data.InnerAudioContext.src = music.url
            this.data.InnerAudioContext.play()
            this.setData({
                flag: false,
                index: 0,
            })
        }else{
            let music = this.data.musicList[this.data.index-1]
            this.data.InnerAudioContext.src = music.url
            this.data.InnerAudioContext.play()
            this.setData({
                flag: false,
                index: this.data.index - 1,
            })
        }
    },
    next(){
        if (this.data.index + 1 < this.data.musicList.length) {
            let music = this.data.musicList[this.data.index + 1]
            this.data.InnerAudioContext.src = music.url
            this.data.InnerAudioContext.play()
            this.setData({
                flag: false,
                index: this.data.index + 1,
            })
        } 
        else {
            let music = this.data.musicList[0]
            this.data.InnerAudioContext.src = music.url
            this.data.InnerAudioContext.play()
            this.setData({
                flag: false,
                index: 0,
            })
        }
    },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
    play(e){
        let index = e.currentTarget.dataset.index
        let music = this.data.musicList[index]
        console.log(music)
        this.data.InnerAudioContext.src=music.url
        this.data.InnerAudioContext.play()
        this.setData({
            flag:false,
            index:index,
        })
    },
    modalConfirm(){
        this.setData({
            madalflag:false,
            index:0
        })
    },
    showMusic(e){
       let index=e.currentTarget.dataset.index
       this.setData({
           musicList: this.data.musicTableList[index].musicList,
           madalflag:true
       })
        console.log(this.data.musicList)
    },
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    //   wx.closeSocket()

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
      this.getMusicTable()
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