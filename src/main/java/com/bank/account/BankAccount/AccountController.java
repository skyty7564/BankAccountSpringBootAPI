package com.bank.account.BankAccount;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	

	@Autowired
	BankUserDao userList = new BankUserDao();
	
	@GetMapping("/displayAllCustomers")
	public ArrayList<BankAccount> displayAllUser()
	{
			return userList.getAllUsers();
	}
	
	@PutMapping("/AddUser")
	public void addUser(@RequestBody BankAccount user)
	{
		userList.addUser(user);
	}
	
	@PostMapping("/UpdateDatabase")
	public void updateDatabase()
	{
		userList.updateDatabase();
	}

	@GetMapping("/displayUser/{AccNum}")
	public BankAccount displayUser(@PathVariable("AccNum") int AccNum )
	{
		return BankUserDao.retrieveUser(AccNum);
	}
	//-------------------------------------------------------------------------
	@GetMapping("/Get2")
	public ArrayList<BankAccount> updateUser2()
	{
		 return userList.Test2();
	} 
	@PutMapping("/Get3")
	public void updateUser3(@RequestBody BankAccount user)
	{
		BankAccount users = new BankAccount (2,1000,"john","william","mail.com",9999999);
		  userList.Test3(user);
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
	
	
}
