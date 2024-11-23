<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input v-model="params.name" style="width: 200px" placeholder="根据姓名查找"></el-input>
      <el-input v-model="params.phone" style="width: 200px; margin-left: 5px" placeholder="根据电话查找"></el-input>
      <el-button round type="warning" style="margin-left: 10px" @click="findBySearch()">查找联系人</el-button>
      <el-button round type="warning" style="margin-left: 10px" @click="reset()">清空查询栏</el-button>
      <el-button round type="primary" style="margin-left: 10px" @click="add()" icon="el-icon-plus">新增联系人</el-button>
      <el-button round type="primary" style="margin-left: 10px" @click="find()" icon="el-icon-search">查找收藏</el-button>
      <el-button round type="success" style="margin-left: 10px" @click="exp()">导出表格</el-button>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="name" label="姓名" width="70"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
        <el-table-column prop="social_media" label="社交媒体" width="100"></el-table-column>
        <el-table-column prop="phone" label="电话" width="125"></el-table-column>
        <el-table-column prop="phone2" label="电话2" width="125"></el-table-column>
        <el-table-column prop="address" label="地址" width="120"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)" icon="el-icon-edit">修改联系人信息</el-button>
            <el-popconfirm title="确定删除该联系人吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" type="danger" style="margin-left: 5px" icon="el-icon-delete">删除该联系人</el-button>
            </el-popconfirm>
            <el-button type="warning" @click="collect(scope.row)" style="margin-left: 5px" :icon="scope.row.collect ? 'el-icon-star-on' : 'el-icon-star-off'">收藏</el-button>
            <el-button type="primary" @click="plus(scope.row)" icon="el-icon-plus">添加联系方式</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="margin-top: 10px">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[5, 10]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
    </div>
    <div>
      <el-dialog title="请填写联系人信息" :visible.sync="dialogFormVisible" width="30%">
          <el-form :model="form">
            <el-form-item label="姓名" label-width="20%">
              <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" label-width="20%">
              <el-input v-model="form.email" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="社交媒体" label-width="20%">
              <el-input v-model="form.social_media" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="电话" label-width="20%">
              <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="地址" label-width="20%">
              <el-input v-model="form.address" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submit()">确 定</el-button>
          </div>
      </el-dialog>
      <el-dialog title="是否添加联系方式" :visible.sync="PlusDialogFormVisible" width="20%">
        <el-form :model="form">
         <el-form-item label="电话" label-width="20%">
            <el-input v-model="form.phone2" autocomplete="off" style="width: 100%"></el-input>
         </el-form-item>
        </el-form>
         <div slot="footer" class="dialog-footer">
           <el-button @click="PlusDialogFormVisible = false">取 消</el-button>
           <el-button type="primary" @click="submit()">确 定</el-button>
         </div>
      </el-dialog>
    </div>
    <el-upload
      class="upload-demo"
      drag
      action=""
      :show-file-list="false"
      :on-change="handleFileUpload"
      :before-upload="beforeFileUpload">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text"><em>点击上传</em></div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </el-upload>
  </div>
</template>

<script>
import request from "@/utils/request";
  export default {
    name: "AdminView",
    data() {
      return {
        params: {
          name: '',
          phone: '',
          phone2: '',
          pageNum: 1,
          pageSize: 5,
          collect: 1
        },
        tableData: [],
        total: 0,
        dialogFormVisible: false,
        PlusDialogFormVisible: false,
        form: {}
      }
    },
    created() {
      this.load();
      this.findBySearch();
    },
    methods: {
      load() {
        request.get("/admin").then(res => {
          if (res.code === '0') {
            this.tableData = res.data;
          }
        })
      },
      findBySearch() {
        request.get("/admin/search", {
          params: this.params
        }).then(res => {
          if (res.code === '0') {
            this.tableData = res.data.list;
            this.total = res.data.total;
          } else {
            this.$message({
              message: res.msg,
              type: 'success'
          });
          }
        })
      },
      reset(){
        this.params = {
            pageNum: 1,
            pageSize: 5,
            name: '',
            phone: '',
            phone2: ''
        }
        this.findBySearch();
      },
      find(){
          this.params = {
            pageNum: 1,
            pageSize: 5,
            name: '',
            collect: 1
          }
        request.get("/admin/collect", {
          params: this.params
        }).then(res => {
          if (res.code === '0') {
            this.tableData = res.data.list;
            this.total = res.data.total;
          } else {
            this.$message({
                message: res.msg,
                type: 'success'
            });
          }
        })
      },
      handleSizeChange(pageSize) {
        this.params.pageSize = pageSize;
        this.findBySearch();
      },
      handleCurrentChange(pageNum) {
        this.params.pageNum = pageNum;
        this.findBySearch();
      },
      add(){
        this.form = {};
        this.dialogFormVisible = true;
      },
      edit(obj){
        this.form = obj;
        this.dialogFormVisible = true;
      },
      plus(obj){
        this.form = obj;
        this.PlusDialogFormVisible = true;
      },
      collect(obj){
        this.form = obj;
        if(obj.collect == 1){
            this.form.collect = 0;
        }
        else{
            this.form.collect = 1;
        }
        request.post("/admin", this.form).then(res => {
          if(res.code === '0'){
            this.$message({
              message: '操作成功!',
              type: 'success'
            });
          this.dialogFormVisible = false;
          this.PlusDialogFormVisible = false;
          this.findBySearch();
          }else{
            this.$message({
              message: res.msg,
              type: 'success'
            });
          }
        })
      },
      submit(){
        request.post("/admin", this.form).then(res => {
          if(res.code === '0'){
            this.$message({
              message: '操作成功!',
              type: 'success'
            });
            this.dialogFormVisible = false;
            this.PlusDialogFormVisible = false;
            this.findBySearch();
          }else{
            this.$message({
              message: res.msg,
              type: 'success'
            });
          }
        })
      },
      del(id) {
        request.delete("/admin/" + id).then(res => {
          if (res.code === '0') {
            this.$message({
              message: '删除成功!',
              type: 'success'
            });
          this.findBySearch();
          } else {
          this.$message({
            message: res.msg,
            type: 'success'
            });
          }
        })
      },
      exp() {
        const data = this.tableData.map(row => ({
          '姓名': row.name,
          '电话': row.phone,
          '邮箱': row.email,
          '社交媒体': row.social_media,
          '地址': row.address
        }));
        const ws = XLSX.utils.aoa_to_sheet(data);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
        XLSX.writeFile(wb, '联系人列表.xlsx');
      },
      beforeFileUpload(file) {
        const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || file.type === 'application/vnd.ms-excel';
        if (!isExcel) {
          this.errorMessage = '请上传Excel文件';
          return false;
        }
        this.errorMessage = '';
        return true;
      },
      handleFileUpload(file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const data = new Uint8Array(e.target.result);
          const workbook = XLSX.read(data, { type: 'array' });
          const firstSheetName = workbook.SheetNames[0];
          const worksheet = workbook.Sheets[firstSheetName];
          const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });
          console.log(jsonData);
        };
        reader.readAsArrayBuffer(file.raw);
      }
    }
  }
</script>