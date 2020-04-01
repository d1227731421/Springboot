import Vue from 'vue'
import VueRouter from 'vue-router'
import index from "../views/index";
import user from "../components/user";
import article from "../components/article";
import music from "../components/music";
import addArticle from "../components/addArticle";
import cartoon from "../components/cartoon";
Vue.use(VueRouter)

const routes = [
  { path: "/", redirect:"/index/user"},
  {path: '/index', name: 'index', component: index,
    children: [
      {path:"user", component:user},
      {path:"article", component:article},
      {path:"music", component:music},
      {path:"addArticle", name:"addArticle",component:addArticle},
      {path:"cartoon",component:cartoon}
    ]
  },

]

const router = new VueRouter({
  routes
})

export default router
