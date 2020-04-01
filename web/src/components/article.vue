<template>
    <div style="position: relative">
        <div class="add-div">
            <el-button type="primary" @click="bindAddArticle">添加</el-button>
        </div>

        <el-table
                :data="tableData" height="750"
                style="width: 100%">
            <el-table-column
                    label="编号"
                    prop="id">
            </el-table-column>
            <el-table-column
                    label="标题"
                    prop="title">
            </el-table-column>
            <el-table-column
                    label="点击数"
                    prop="clickNumber">
            </el-table-column>
            <el-table-column
                    label="添加时间"
                    prop="createTime">
            </el-table-column>
            <el-table-column align="right">
                <template slot="header" slot-scope="scope">
                    <el-input v-model="search" size="mini" style="width: 80%" placeholder="输入关键字搜索"/>
                    <el-button size="mini" type="warning" @click="searchKey" > 搜索</el-button>
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>

</template>

<script>
    import request from "../util/request";

    export default {
        name: "music",
        data() {
            return {
                tableData: [],
                search: ''
            }
        },
        methods: {
            searchKey(){
                if(this.search=="")
                {
                    this.getArticle()
                    return 0;
                }
                request.get("/article/findAllArticle/key",{
                    params:{
                        key:this.search
                    }
                }) .then(res=>{
                    this.tableData=res.data.queryResult.list
                })
            },
            handleEdit(index, row) {
                this.$router.push({name:"addArticle",params:{data:row }})
            },

            handleDelete(index, row) {
                request.get("http://localhost:6969//article/delete",{
                    params:{
                        id:row.id
                    }
                }).then(res=>{
                    this.tableData.splice(index,1)
                })
            },
            bindAddArticle(){
                this.$router.push("/index/addArticle")
            },
            getArticle(){
                request.get("/article/findAllArticle")
                    .then(res=>{
                        this.tableData=res.data.queryResult.list
                    })
            }
        },

        mounted() {
            this.getArticle()
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
</style>