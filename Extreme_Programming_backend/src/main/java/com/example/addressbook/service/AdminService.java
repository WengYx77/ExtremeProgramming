package com.example.addressbook.service;

import com.example.addressbook.dao.AdminDao;
import com.example.addressbook.entity.Params;
import com.example.addressbook.entity.Admin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminDao adminDao;

    public List<Admin> findAll() {
        return adminDao.selectAll();
    }

    public PageInfo<Admin> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Admin> list = adminDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public PageInfo<Admin> find(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Admin> list = adminDao.find(params);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        // 初始化一个密码
        if (admin.getPassword() == null) {
            admin.setPassword("123456");
        }
        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }
}
