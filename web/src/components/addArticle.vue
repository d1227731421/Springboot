<template>
    <div style="position: relative">
        <el-button type="primary" @click="handSubmit" style="position: absolute;left: 274px">提交</el-button>
        <div style="width: 320px; position: absolute;left: 444px">
            <el-form label-width="80px" >
                <el-form-item label="文章标题">
                    <el-input v-model="title"></el-input>
                </el-form-item>
            </el-form>
        </div>
        <div style="width: 320px; position: absolute;left: 774px">
            <el-form label-width="80px" >
                <el-form-item label="文章出处">
                    <el-input v-model="from"></el-input>
                </el-form-item>
            </el-form>
        </div>

        <editor ref="editor"></editor>
    </div>
</template>

<script>
    import editor from "./editor";
    import request from "../util/request";
    import qs from "qs";

    export default {
        name: "addArticle",
        data() {

            return {
                title: "",
                content: "",
                id: "",
                createTime: null,
                clickNumber: 0,
                from:""

            }
        },
        components: {
            editor
        },
        methods: {
            handSubmit() {
                if (this.$refs.editor.content == "" || this.title == ""||this.from=="") {
                    this.$message.error('请填写内容');
                    return 0;
                }
                this.content = this.$refs.editor.content,
                    request.post("/article/save", qs.stringify({
                        title: this.title,
                        content: this.content,
                        id: this.id,
                        createTime: this.createTime,
                        clickNumber: this.clickNumber,
                        from:this.from
                    }), {headers: {"Content-Type": "application/x-www-form-urlencoded"}})
                        .then(res => {
                            if (res.data.success == true) {
                                this.$message.success('提交成功');
                                this.$router.go(-1)
                            }
                        })
            }
        },
        mounted() {
            if (this.$route.params.data != null) {
                this.title = this.$route.params.data.title
                this.$refs.editor.content = this.$route.params.data.content
                this.id = this.$route.params.data.id
                this.createTime = this.$route.params.data.createTime
                this.clickNumber = this.$route.params.data.clickNumber
               this.from=this.$route.params.data.from
            }
        }
    }
</script>

<style scoped>

</style>