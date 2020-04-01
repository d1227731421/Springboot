<template>
    <div>


        <el-table :data="tableData" height="750"  style="width: 100%">
            <el-table-column label="编号" prop="id"></el-table-column>
            <el-table-column label="昵称" prop="userName"></el-table-column>
            <el-table-column label="头像" prop="headPortrait">
                <template slot-scope="scope">
                    <img class="head-image" :src="scope.row.headPortrait" height="70" width="70" />
                </template>
            </el-table-column>
            <el-table-column align="right">
                <template slot="header" slot-scope="scope">
                    <el-input v-model="search" size="mini" style="width: 80%" placeholder="输入关键字搜索"/>
                    <el-button size="mini" type="warning" @click="searchKey" > 搜索</el-button>
                </template>
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    import request from "../util/request";

    export default {
        name: "user",
        data() {
            return {
                tableData: [],
                search: '',
            }
        },
        methods: {
            searchKey(){
                if(this.search=="")
                {
                    this.getUser();
                    return 0;
                }
                request.get("/user/find/key",{
                    params:{
                        key:this.search
                    }
                }).then(res => {
                    console.log(res.data.queryResult.list)
                    this.tableData = res.data.queryResult.list
                })
            },
            handleDelete(index, row) {
                console.log(index, row.id);
                this.tableData.splice(index,1)
                request.get("user/delete",{
                    params:{
                        id:row.id
                    }
                })
            },
            getUser() {
                request.get("/user/find")
                    .then(res => {
                        console.log(res.data.queryResult.list)
                        this.tableData = res.data.queryResult.list
                    })
            },
        },
        mounted() {
            this.getUser()
        }


    }
</script>


<style scoped>
    .head-image {
        border-radius: 50%;
    }
</style>