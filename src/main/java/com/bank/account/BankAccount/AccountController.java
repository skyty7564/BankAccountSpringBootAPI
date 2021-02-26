package com.bank.account.BankAccount;

import java.awt.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	

	
	@GetMapping ("/Test")
	public int Hello()
	{
		int list = 1;
		return list;
	}
	
	
//	@Autowired
//	GetDataService fetchDataService;
	
	@Autowired
	BankUserDao userList = new BankUserDao();
	
	@GetMapping("/displayAllCustomers")
	public ArrayList<BankAccount> displayAllUser()
	{
			return userList.getAllUsers();
	}
	
//	@GetMapping(path = "GetUser")
//	public ArrayList<BankAccount> getAllUser()
	//{
	//	return fetchDataService.findAll();
		
	//}
	
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

	@PostMapping ("/DeleteUser")
	public void deleteUser(@RequestBody BankAccount user)
	{
		userList.deleteUser(user);
	}
	
	@PostMapping ("/Deposit")
	public void deposit(@RequestBody BankAccount user)
	{
		BankAccount userEdit = BankUserDao.retrieveUser(user.getAccNum());
		
		userEdit.setAmount(user.getAmount());
		
		userList.depositFunds(userEdit);
	}
	
	@PostMapping ("/Withdraw")
	public void withdraw(@RequestBody BankAccount user)
	{
		userList.deleteUser(user);
	}

	@PostMapping("/UpdateUser")
	public void updateUser(@RequestBody BankAccount user)
	{
		BankUserDao.updateUser(user);
	}
	@GetMapping("/displayUser/{AccNum}")
	public BankAccount displayUser(@PathVariable("AccNum") int AccNum )
	{
		return BankUserDao.retrieveUser(AccNum);
	}
	//-------------------------------------------------------------------------

	
	
}
