<template>
    <div style="position: relative">
        <div class="add-div">
            <el-button type="primary" @click="drawer=true">添加</el-button>
        </div>
        <el-table :data="tableData"  height="750" style="width: 100%" @row-dblclick="handClick">
            <el-table-column label="编号" prop="id"></el-table-column>
            <el-table-column label="歌单封面">
                <template slot-scope="scope">
                    <img class="head-image" :src="scope.row.img" height="70" width="70"/>
                </template>
            </el-table-column>
            <el-table-column label="歌单名字" prop="name"></el-table-column>
            <el-table-column align="right">
                <template slot="header" slot-scope="scope">
                    <el-input v-model="search" size="mini" style="width: 80%" placeholder="输入关键字搜索"/>
                    <el-button size="mini" type="warning" @click="searchKey" > 搜索</el-button>
                </template>
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit
                    </el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div>
            <el-dialog title="添加歌单" :visible.sync="drawer" :before-close="handleClose">
                <el-form :model="form">
                    <el-form-item label="封面" label-width="120px">
                        <el-upload class="avatar-uploader" action="http://localhost:6969/file/img"
                                   :show-file-list="false" :on-success="handleAvatarSuccess"
                                   :before-upload="beforeAvatarUpload">
                            <img v-if="imageUrl" :src="imageUrl" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="名称" label-width="120px">
                        <el-input v-model="form.name" autocomplete="off"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="handCencle">取 消</el-button>
                    <el-button type="primary" @click="createMusicTalbe">确 定</el-button>
                </div>
            </el-dialog>
        </div>
        <div>
            <el-drawer :with-header="false" :visible.sync="dialogTableVisible" direction="rtl" size="35%">
                <el-upload class="upload-demo" :show-file-list="false" :on-success="musicSuccess" :before-upload="musicUpload"
                           action="http://localhost:6969/file/mp3">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
                <el-table :data="musics">
                    <el-table-column property="name" label="名字" ></el-table-column>
                    <el-table-column align="right">
                        <template slot-scope="scope">
                            <el-button size="mini" @click="play(scope.$index, scope.row)">Play
                            </el-button>
                            <el-button size="mini" type="danger" @click="Delete(scope.$index, scope.row)">Delete
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-drawer>
        </div>
    </div>

</template>

<script>
    import request from "../util/request";

    export default {
        name: "article",
        data() {
            return {
                tableData: [],
                tableId: "",
                dialogTableVisible: false,
                search: '',
                drawer: false,
                imageUrl: '',
                img: "",
                id: "",
                form: {
                    name: ''
                },
                musics: [{

                }],
                audio:"",
            }

        },
        methods: {
            searchKey(){
                if(this.search=="")
                {
                    this.getMusicTable();
                    return 0;
                }
                request.get("/tableMusic/find/key",{
                    params:{
                        key:this.search
                    }
                }). then(res => {
                    this.tableData = res.data.queryResult.list
                })
            },
            handClick(obj) {
                console.log(obj)
                this.getMusic(obj.id)
                this.dialogTableVisible = true
                this.tableId=obj.id
            },
            handCencle() {
                this.handleClose()
            },
            handleClose() {
                this.imageUrl = ""
                this.form.name = ""
                this.drawer = false
                this.id = ""
            },
            handleAvatarSuccess(res, file) {
                this.img = res
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            musicSuccess(res, file) {
                request.get("/music/save", {
                    params: {
                        tableId:this.tableId,
                        name: file.name,
                        url:res
                    }
                })
                    .then(res => {
                        this.getMusic(this.tableId)
                    })
            },
            beforeAvatarUpload(file) {
                console.log(file)
            },
            musicUpload(file) {
                console.log(file)
            },
            handleEdit(index, row) {

                this.id = row.id
                this.imageUrl = row.img
                this.img = row.img
                this.form.name = row.name
                this.drawer = true
            },
            play(index,row){
                console.log(index, row);
                this.audio.src=row.url
                this.audio.play()
                console.log(this.audio)
            },
            handleDelete(index, row) {
                console.log(index, row);
                this.deleteMusicTable(row.id)
                this.tableData.splice(index, 1)
            },
            Delete(index, row) {
                console.log(index, row);
                this.musics.splice(index, 1)
                request.get("/music/delete",{
                    params:{
                        id:row.id
                    }
                })
            },
            getMusicTable() {
                request.get("/tableMusic/find")
                    .then(res => {
                        this.tableData = res.data.queryResult.list
                    })
            },
            getMusic(id) {
                request.get("/tableMusic/findById", {
                    params: {
                        id: id
                    }
                }).then(res => {
                        console.log(res.data.queryResult.list[0].musicList)
                        this.musics=res.data.queryResult.list[0].musicList
                    })
            },
            deleteMusicTable(id) {
                request.get("/tableMusic/delete", {
                    params: {
                        id: id
                    }
                }).then(res => {
                    console.log(res)
                })
            },
            createMusicTalbe() {
                request.get("/tableMusic/save", {
                    params: {
                        name: this.form.name,
                        img: this.img,
                        id: this.id
                    }
                }).then(res => {
                    console.log(res)
                    this.handleClose()
                    this.getMusicTable()
                })
            }

        },
        mounted() {
            this.getMusicTable()
            this.audio=document.createElement("AUDIO")
        }
    }
</script>


<style scoped>
    .add-div {
        position: absolute;
        z-index: 12;
        top: -43px;
        left: 24px;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
        border: #D3DCE6 solid 1px;
    }

    .head-image {
        border-radius: 50%;
    }
</style>