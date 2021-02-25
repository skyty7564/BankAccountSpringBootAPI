package com.bank.account.BankAccount;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	

	
	@GetMapping ("/Test")
	public ArrayList<String> Hello()
	{
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("Hello");
		list.add("Hello");
		return list;
	}
	
	@GetMapping("/Get")
	public ArrayList<BankAccount> updateUser()
	{
		 return userList.Test2();
	} 
	@GetMapping("/Get2")
	public ArrayList<BankAccount> updateUser2()
	{
		 return userList.Test1();
	} 
	
	@PostMapping("/Update")
	public void update(BankAccount user1)
	{
		userList.Test2();
	}
	@PutMapping("/Put")
	public void updateUser2(BankAccount user1)
	{
		userList.Test2();
	}
	//-------------------------------------------------------------------------
	@Autowired
	BankUserDao userList = new BankUserDao();
	
	@GetMapping("/displayAllCustomers")
	public ArrayList<BankAccount> displayAllUser()
	{
			return userList.getAllUsers();
	}
	
	@PutMapping("/AddUser")
	public void addUser(BankAccount user)
	{
		userList.addUser(user);
	}
	
	@PostMapping("/UpdateUser")
	public void updateUser(BankAccount user)
	{
		userList.updateDatabase();
	}

	@GetMapping("/displayUser")
	public BankAccount displayUser(int AccNum)
	{
		return BankUserDao.retrieveUser(AccNum);
	}
	
	
}
