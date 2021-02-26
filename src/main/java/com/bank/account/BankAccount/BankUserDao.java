package com.bank.account.BankAccount;

import java.util.ArrayList;

import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Component
public class BankUserDao {
	
	private final static String JDBC_DRIVER =  "com.mysql.cj.jdbc.Driver";
	private final static String DB_URL ="jdbc:mysql://bankapp.cmzwk8z05jtw.us-east-2.rds.amazonaws.com/BankDemo";
	private final static String USER = "Admin";
	private final static String PASSWORD = "Password";
	private static Connection conn = null;
	private static PreparedStatement preStmt = null;
	private ArrayList<BankAccount> listUser = new ArrayList<BankAccount>();
	private static Statement stmt = null;
	
	//open the DB connection
	public static void DBOpen()
	{
		System.out.println("Connecting to Database");	
		try
		{
			Class.forName(JDBC_DRIVER);	
			conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
			
		}

		catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection Close");
			e.printStackTrace();
		}
	
	}
	
	
	
	public ArrayList<BankAccount> getAllUsers()
	{
		DBOpen();
		
		try {
		//	System.out.println("Get in here");
			stmt = conn.createStatement();
			
			String sql = "Select * from usersdb";
			ResultSet result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				BankAccount user = new BankAccount();
				user.setfName(result.getString("FirstName"));
				user.setlName(result.getString("LastName"));
				user.setAccNum(result.getInt("AccNum"));
				user.setPhoneNumber(result.getLong("PhoneNumber"));
				user.setEmail(result.getString("email"));
				user.setBalance(result.getDouble("Balance"));
				listUser.add(user);
				
			}
			
		
			conn.close();
			return listUser;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("My Error");
		}
		return listUser;
		
	
	}
	//check to see account exist
	public static BankAccount retrieveUser(int Acc)
	{
		DBOpen();
		try {
			preStmt = conn.prepareStatement("Select * from usersdb Where AccNum = ?");
			preStmt.setInt(1, Acc);
			ResultSet result = preStmt.executeQuery();
			BankAccount user = new BankAccount();
			while(result.next())
			{
				user.setfName(result.getString("FirstName"));
				user.setlName(result.getString("LastName"));
				user.setAccNum(result.getInt("AccNum"));
				user.setPhoneNumber(result.getLong("PhoneNumber"));
				user.setEmail(result.getString("email"));
				user.setBalance(result.getDouble("Balance"));
				
			}
			
			
			conn.close();
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//check to see if the user exist before adding current new user
	public static boolean retrieveUser(BankAccount Acc)
	{
		DBOpen();
		try {
			preStmt = conn.prepareStatement("Select 1 from usersdb Where AccNum = ?");
			preStmt.setInt(1, Acc.getAccNum());
			ResultSet result = preStmt.executeQuery();
			
			if(result.next())
			{
		       return true;
				
			}
			
			
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//add user to list before adding the database
	public void addUser(BankAccount user)
	{
	
		System.out.println(user.getfName());
		listUser.add(user);
		
	}
	
	//add user to list before adding the database
	public void addUserData(BankAccount user)
	{
		DBOpen();
		try
		{
			boolean userExist = retrieveUser(user);
			
			if(!userExist)
			{
				preStmt = conn.prepareStatement("INSERT INTO usersdb (AccNum,FirstName,LastName,Balance,email,PhoneNumber) Values (?,?,?,?,?,?)");
				
				preStmt.setInt(1, user.getAccNum());
				preStmt.setString(2, user.getfName());
				preStmt.setString(3, user.getlName());
				preStmt.setDouble(4, user.getBalance());
				preStmt.setString(5, user.getEmail());
				preStmt.setLong(6, user.getPhoneNumber());
				
				preStmt.executeUpdate();
				System.out.println("Entered into database:"+user.getfName());
				
			}
			else
			{
				updateUser(user);
			}
			
			conn.close();
		}
		catch (Exception e)
		{
			
		}	
		
	}
	
	//update the database;
	public void updateDatabase()
	{
		DBOpen();
		if(listUser == null)
		{
			return;
		}
		for(BankAccount user : listUser )
		{
		
			try {
				boolean userExist = retrieveUser(user);
				
				if(!userExist)
				{
					preStmt = conn.prepareStatement("INSERT INTO usersdb (AccNum,FirstName,LastName,Balance,email,PhoneNumber) Values (?,?,?,?,?,?)");
					
					preStmt.setInt(1, user.getAccNum());
					preStmt.setString(2, user.getfName());
					preStmt.setString(3, user.getlName());
					preStmt.setDouble(4, user.getBalance());
					preStmt.setString(5, user.getEmail());
					preStmt.setLong(6, user.getPhoneNumber());
					
					preStmt.executeUpdate();
					System.out.println("Entered into database:"+user.getfName());
					
				}
				else
				{
					updateUser(user);
				}
			
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				
			}
		}
		

	}
	
	//display the current user info
	public void depositFunds(BankAccount user)
	{
	
	
		double balance = user.getBalance();
		double amount = user.getAmount();
		user.setBalance(balance + amount);
		
		System.out.println(user.getAccNum() + ":" + user.getAmount() + ":"+ user.getBalance());
		
		DBOpen();
		
		try {
			preStmt = conn.prepareStatement("Update usersdb Set Balance = ? Where AccNum = ?");
			preStmt.setDouble(1,user.getBalance());
			preStmt.setInt(2, user.getAccNum());
			displayFunds(user,"Fund deposited!");
			preStmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Deposit");
			e.printStackTrace();
		}
		
		
	}
	
	public boolean checkUser(int Acc)
	{
		DBOpen();
		try {
			preStmt = conn.prepareStatement("Select 1 from usersDB Where AccNum = ?");
			preStmt.setInt(1, Acc);
			ResultSet result = preStmt.executeQuery();
			if (result != null)
			{
				return true;
			}
			else
				
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	public static void updateUser(BankAccount User)
	{
		DBOpen();
		try
		{
		
			

				preStmt = conn.prepareStatement("UPDATE usersDB Set FirstName =?, LastName = ?, Balance = ? ,email = ?, PhoneNumber = ? Where AccNum = ?");
				

				preStmt.setString(1, User.getfName());
				preStmt.setString(2, User.getlName());
				preStmt.setDouble(3, User.getBalance());
				preStmt.setString(4, User.getEmail());
				preStmt.setLong(5, User.getPhoneNumber());
				preStmt.setInt(6, User.getAccNum());
				
				preStmt.executeUpdate();
				System.out.println("Entered into database:"+User.getfName());
				
		
			
			conn.close();
		}
		catch (Exception e)
		{
			
		}	
		
	}
	//withdraw money
	public void withdrawFunds(BankAccount user)
	{

		double amount = user.getAmount();
		double balance = user.getBalance();
		double tempValue = balance - amount;
		
		
		
		if (tempValue < 0)
		{
			displayFunds(user,"Insufficent fund!");
		}
		else
		{
			user.setBalance(tempValue);
			System.out.println(user.getAccNum() + ":" + user.getAmount());
			displayFunds(user,"Fund Successfully Withdrawed!");
			
			try {
				preStmt = conn.prepareStatement("Update usersdb Set Balance = ? Where AccNum =  ?");
				preStmt.setDouble(1,user.getBalance());
				preStmt.setInt(2, user.getAccNum());
				preStmt.executeUpdate();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

	}
	
	public void displayFunds(BankAccount user,String Message)
	{
		
		System.out.println("Bank Account User:" +user.getfName()+ " " + user.getlName());
		System.out.println(Message);
		System.out.println("Bank Balance:$" + String.format("%,.2f",user.getBalance()));
		
	}
	
	public void displayInfo(BankAccount user)
	{
		System.out.println("Bank Account Number:"+user.getAccNum());
		System.out.println("Customer Name:" + user.getfName() + " " + user.getlName());
		System.out.println("Bank Account Fund:$" + user.getBalance());
		System.out.println("Customer Email:" + user.getEmail());
		System.out.println("Customer Phone Number:" + user.getPhoneNumber());
		
		}
	
	public void deleteUser(BankAccount user)
	{
		DBOpen();
		try
		{
			boolean userExist = retrieveUser(user);
			
			if(!userExist)
			{
				preStmt = conn.prepareStatement("Delete From usersdb Where AccNum  = ?");
				
				preStmt.setInt(1, user.getAccNum());
				
				preStmt.executeUpdate();
				System.out.println("Deleted user from database:"+user.getfName());
				
			}
			
			conn.close();
		}
		catch (Exception e)
		{
			
		}	
	}
	
	
	//----------------------------------------------------------------
	
	public ArrayList<BankAccount> Test1()
	{
		DBOpen();
		
		try {
		//	System.out.println("Get in here");
			stmt = conn.createStatement();
			
			String sql = "Select * from usersdb";
			ResultSet result = stmt.executeQuery(sql);
			
			while(result.next())
			{
				BankAccount user = new BankAccount();
				user.setfName(result.getString("FirstName"));
				user.setlName(result.getString("LastName"));
				user.setAccNum(result.getInt("AccNum"));
				user.setPhoneNumber(result.getLong("PhoneNumber"));
				user.setEmail(result.getString("email"));
				user.setBalance(result.getDouble("Balance"));
				listUser.add(user);
				
			}
			
		
			conn.close();
			return listUser;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("My Error");
		}
		return listUser;
		
	
	}
	
	public ArrayList<BankAccount> Test2()
	{
		
		BankAccount user = new BankAccount (3,1000,"john","william","mail.com",9999999);
		
		listUser.add(user);
		
		return listUser;
	}
	
	
	public void Test3(BankAccount user)
	{
		listUser.add(user);
		System.out.println(user.getfName());
		
		
	
	}
}
