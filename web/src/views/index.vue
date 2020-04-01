<template>
    <el-container>
        <el-header><span v-text="title"></span></el-header>
        <el-container>
            <el-aside width="300px">
                <el-row class="tac">
                    <el-col :span="12">
                        <el-menu
                                default-active="/index/user"
                                class="el-menu-vertical-demo"
                                background-color="#545c64"
                                text-color="#fff"
                                :router="flag"
                                :unique-opened="flag"
                                active-text-color="#ffd04b" @select="handleSelect">
                            <el-menu-item index="/index/user">
                                <i class="el-icon-user"></i>
                                <span slot="title">用户管理</span>
                            </el-menu-item>


                            <el-menu-item index="/index/music">
                                <i class="el-icon-menu"></i>
                                <span slot="title">歌单管理</span>
                            </el-menu-item>
                            <el-menu-item index="/index/article">
                                <i class="el-icon-menu"></i>
                                <span slot="title">文章管理</span>
                            </el-menu-item>
                            <el-menu-item index="/index/cartoon">
                                <i class="el-icon-menu"></i>
                                <span slot="title">漫画管理</span>
                            </el-menu-item>
                        </el-menu>
                    </el-col>
                </el-row>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                title: "用户管理",
                flag:true
            }
        },
        methods: {
            handleSelect(key,keyPath) {
                switch (keyPath[0]) {
                    case "/index/user":this.title="用户管理" ;break;
                    case "/index/article":this.title="文章管理" ;break;
                    case "/index/music":this.title="音乐管理" ;break;
                    case "/index/cartoon":this.title="漫画管理" ;break;
                 }
            }
        }
    }
</script>

<style scoped>
    .el-menu-vertical-demo {
        width: 298px;
        height: 820px;
    }

    .el-header, .el-footer {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }
</style>