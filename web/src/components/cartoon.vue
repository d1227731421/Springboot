<template>
    <div style="position: relative">
        <div class="add-div">
            <el-button type="primary" @click="drawer = true">添加</el-button>
        </div>
        <div class="content">
            <el-row :gutter="12">
                <el-col :span="4" v-for="item in cartoonLsit">
                    <el-card style="height: 300px;margin-top: 60px" shadow="hover">
                        <div style="position: relative">
                            <img :src="item.img"
                                 class="image">
                            <span class="card-title" v-text="item.title"></span>
                            <span class="card-time" v-text="item.createTime">2017-09-16 12:34:45</span>
                            <el-button class="card-more" type="text" @click="handMore(item.id)">更多</el-button>
                            <el-button size="mini" class="card-del" @click="handleDelete(item.id)" type="danger" icon="el-icon-delete" circle></el-button>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>

        <el-drawer

                :with-header="false"
                :visible.sync="drawer"
                :direction="direction"
                :file-list="fileList"
                :before-close="handleClose"

                style="text-align: left;height: 900px">
            <div style="width: 320px; position: absolute;left: 100px;top: 8px">
                <el-form label-width="80px">
                    <el-form-item label="漫画标题">
                        <el-input v-model="title"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div style="position: absolute;right: 100px;top: -52px">
                <el-button type="primary" @click="bindAddCartoon">保存</el-button>
            </div>
            <el-upload
                    action="http://localhost:6969/file/img"
                    list-type="picture-card"
                    :on-preview="handlePictureCardPreview"
                    :on-success="handleSuccess"
                    :file-list="fileList"
                    :on-remove="handleRemove" style="margin-top:90px;margin-left: 100px">
                <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible" z-index="10">
                <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
        </el-drawer>
    </div>
</template>

<script>
    import request from "../util/request";
    import qs from "qs"

    export default {
        name: "cartoon",
        data() {
            return {
                drawer: false,
                direction: 'ttb',
                dialogImageUrl: '',
                dialogVisible: false,
                title: "",
                paths: [],
                createTime:"",
                id:"",
                cartoonLsit: [],
                fileList: []
            };
        },
        methods: {
            handleRemove(file, fileList) {
                console.log(file)
                let index = -1
                for (let i = 0; i < this.paths.length; i++)
                    if (file.response == this.paths[i])
                        index = i
                this.paths.splice(index, 1)
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            handleSuccess(res, file, fileList) {
                this.fileList = fileList
                console.log(fileList)
                this.paths.push(res)
            },
            bindAddCartoon() {

                if (this.paths.length == 0 || this.title == "") {
                    this.$message.error("内容填写不完整")
                    return 0
                }
                request.post("/article/saveCartoon", qs.stringify({
                    img: this.paths.join(","),
                    title: this.title,
                    createTime:this.createTime,
                    id:this.id
                }), {headers: {"Content-Type": "application/x-www-form-urlencoded"}})
                    .then(res => {
                        if (res.data.success == true) {
                            this.$message.success("保存成功")
                            this.drawer = false
                            this.title = ""
                            this.id=""
                            this.createTime=""
                            this.paths=[]
                            this.fileList=[]
                            this.handgetData()

                        }
                    })
            },
            handleClose(done){
                this.title = ""
                this.id=""
                this.createTime=""
                this.paths=[]
                this.fileList=[]
                done()
            },
            handMore(id) {
                request.get("/article/findCartoonById", {
                    params: {
                        id: id
                    }
                }).then(res => {
                    let imgList = []
                    this.title=res.data.title
                    this.id=res.data.id
                    this.createTime=res.data.createTime
                    imgList = res.data.imgList;
                    imgList.forEach(e => {
                        this.paths.push(e.substring(e.indexOf("group1")))
                        this.fileList.push({
                            url:e,
                            response:e.substring(e.indexOf("group1"))
                            })
                    })
                    this.drawer=true
                })
            },
            handgetData(){
                request.get("/article/findAllCartoon")
                    .then(res => {
                        this.cartoonLsit = res.data.queryResult.list
                    })
            },
            handleDelete(id){
               request.get("article/delete",{
                   params:{
                       id:id
                   }
               }).then(res=>{
                   this.handgetData()
               })
            }
        },
        mounted() {
           this.handgetData()
        }

    }
</script>

<style scoped>
    .image {
        max-width: 200px;
        height: 200px;
        display: block;
    }
    .content{
        height: 780px;
        white-space: nowrap;
        overflow:scroll;
        overflow-y: auto;
        overflow-x: hidden;
    }
    .card-title {
        position: absolute;
        bottom: 55px;
        height: 12px;
        left: 0px
    }

    .card-time {
        position: absolute;
        bottom: 11px;
        height: 12px;
        left: 0px;
        font-size: 13px;
        color: #8c939d;
    }

    .card-more {
        position: absolute;
        bottom: -62px;
        height: 12px;
        right: 0px;
    }

    .add-div {
        position: absolute;
        z-index: 12;
        top: -65px;
        left: 24px;
    }
    .card-del{
        position: absolute;
        top: -18px;
        right: -12px;
    }

</style>



