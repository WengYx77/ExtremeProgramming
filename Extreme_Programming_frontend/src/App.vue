<template>
  <div id="app">
    <el-container>
      <el-header style="background-color: #4c535a">
        <img src="@/assets/logo1.png" alt="" style="width: 50px; position: relative; top: 10px;">
          <span style="font-size: 40px; margin-left: 15px; color: white">书签联系人（极限编程）</span>
      </el-header>
    </el-container>
    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh; background-color: #545c64; width: 250px">
        <el-menu :default-active="$route.path" router background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i><span>信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin">联系人信息</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<style>
.el-menu{
    border-right: none !important;
}
</style>