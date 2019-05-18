//package com.example.demo.service.imp;
//
//import java.util.List;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.model.Admin;
//import com.example.demo.repository.AdminDAO;
//import com.example.demo.repository.AdminRepository;
//import com.example.demo.service.AdminService;
//
//@Service
//public class AdminServiceImp implements AdminService{
//	
//	@Autowired
//	AdminRepository adminrepo;
//	
//	
//
//	@Override
//	public List<Admin> getAllAdmin() {
//		// TODO Auto-generated method stub
//		return (List<Admin>) adminrepo.findAll();
//	}
//
//
//
//	@Override
//	public Admin save(Admin admin) {
//		
//		return adminrepo.save(admin);
//	}
//
//
//
//
//	public void getAdminByUserId(String id) {
//		// TODO Auto-generated method stub
//	//	return adminrepo.findAdminByUserID(id);
//	}
//
////}
