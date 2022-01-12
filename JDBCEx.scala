import java.sql.DriverManager
import java.sql.Connection
import java.util.Scanner
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date

/**
 * A Scala JDBC connection example by Alvin Alexander,
 * https://alvinalexander.com
 */
/*
Cash-Flow App
		-Main goal: 
			Helps user manage cash-in and cash-out by planning out monthly
			expenses vs income 
		- Main Functions
			- Create
				- Enter Income Source, Amount, Date
				- Enter Expense, Amount, Date
			- Retrieve
				- List Income Source, Amount, Date
				- List Expense, Amount, Date
		  - Update
				- Select Income Source to update Amount & Date
				- Select Expense to update Amount & Date
			- Delete
				- Select Income Source to delete
				- Select Expense to delete 
1. Scala  App
	a. UI
		- Application main menue
			- Enter data 
			- Show/List Data
			- Select data record to update 
			- Select data record to delete
		- Quit
	b. Create - Done-50%
	c. Retrieve -Done-50%
	d. Update - Done-50%
	e. Delete - Done-50%
	f. log 
2. MySql database
	a. DB design
      -Income table
        -Source of Income 
        -Amount
        -Date 
      -Expenses table
        -Expense
        -Amount
        -Date 
	b. DB connection- Done 100% 



*/


object JDBCEx {
val log = new PrintWriter(new File("Ins&Outs.log"))

  def main(args: Array[String]):Unit = {
      createIncome()
      createExpense()
      retrieveIncome()
      retrieveExpense()
      updateIncome()
      updateExpenses()
      deleteIncome()
      deleteExpenses()
    

   //Buffer-reader 
  }

  def createIncome():Unit = {
     var scanner = new Scanner(System.in)
     var sc = new Scanner(System.in)
    

    println("Please Enter Your Income Source: ")
     var incomeSrc = sc.nextLine()
    println("Please Enter Your Income Amount: ")
     var incomeAmt = sc.nextInt()
     scanner.nextLine()
    println("Please Enter Your Income Date: ")
     var incomedt = sc.nextLine()
   

    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null
    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT MAX(IncomeID) FROM Income")
      log.write("Executing 'SELECT MAX(IncomeID) FROM Income'")
      resultSet.next()
      var incomeId = resultSet.getInt(1) + 1
      


      statement.executeUpdate("INSERT INTO Income (IncomeID, IncomeSource, IncomeAmount, IncomeDate) VALUES ("+ incomeId + " ,'" + incomeSrc + "', "+ incomeAmt + ",'" + incomedt + "')") // Change query to your table
      log.write("Executing 'INSERT INTO Income (IncomeID, IncomeSource, IncomeAmount, IncomeDate) VALUES ("+ incomeId + " ,'" + incomeSrc + "', "+ incomeAmt + ",'" + incomedt + "'))")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }  

    def createExpense():Unit = {
     var sc = new Scanner(System.in)
     var scanner = new Scanner(System.in)


    println("Please Enter Your Expense: ")
     var expense = sc.nextLine()
      scanner.nextLine()    
    println("Please Enter Your Expense Amount: ")
     var expenseAmt = sc.nextInt()
     scanner.nextLine()
    println("Please Enter Your Expense Date: ")
    var expensedt = sc.nextLine()
     

    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null
    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT MAX(expenseId) FROM Expenses") // Change query to your table
     log.write("Executing 'SELECT MAX(expenseId) FROM Expenses'")
      resultSet.next()
      var expenseId = resultSet.getInt(1) + 1
      statement.executeUpdate("INSERT INTO Expenses (expenseId, expense, expenseAmount, ExpenseDate) VALUES ("+ expenseId + " ,'" + expense + "', "+ expenseAmt + ",'" + expensedt + "')") // Change query to your table
      log.write("Executing 'INSERT INTO Expenses (expenseId, expense, expenseAmount, ExpenseDate) VALUES ("+ expenseId + " ,'" + expense + "', "+ expenseAmt + ",'" + expensedt + "'))\n")   
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }  
  
  def retrieveIncome():Unit = {
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM Income") // Change query to your table
      log.write("Executing 'SELECT * FROM Income'")
      println("Income Sources: ")
      while ( resultSet.next() ) {
        print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " " + resultSet.getString(4))
        println()
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }
  def retrieveExpense():Unit = {
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
     
      val resultSet1 = statement.executeQuery("SELECT * FROM Expenses") // Change query to your table
       log.write("Executing 'SELECT * FROM Expenses'")
      println("Expenses: ")
     while ( resultSet1.next() ) {
        print(resultSet1.getInt(1) + " " + resultSet1.getString(2) + " " + resultSet1.getInt(3) + " " + resultSet1.getString(4))
        println()
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }
   def updateIncome():Unit = {
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null




    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM Income") // Change query to your table
       log.write("Executing 'SELECT * FROM Income'")
      println("Income Sources: ")
      while ( resultSet.next() ) {
        print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " " + resultSet.getString(4))
        println()
      }
      
     var scanner = new Scanner(System.in)
     var sc = new Scanner(System.in)
     println("Select Income Id to Update: ")
     scanner.nextLine()
     var incId = sc.nextInt()
     scanner.nextLine()
        

    println("Please Enter Your Income Source: ")
     var incomeSrc = sc.nextLine()
    println("Please Enter Your Income Amount: ")
     scanner.nextLine()
     var incomeAmt = sc.nextInt()
     scanner.nextLine()
    println("Please Enter Your Income Date: ")
     var incomedt = sc.nextLine()
      
   


    statement.executeUpdate("UPDATE Income SET IncomeSource = '"+ incomeSrc +"', IncomeAmount = " + incomeAmt + ", IncomeDate = '" + incomedt + "' WHERE incomeId = " + incId) // Change query to your table
    retrieveIncome()

     

    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }
  

  
  def updateExpenses():Unit = {
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null




    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM Expenses") // Change query to your table
      println("Expenses: ")
     log.write("Executing 'SELECT * FROM Expenses'")
      while ( resultSet.next() ) {
        print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " " + resultSet.getString(4))
        println()
      }
      
     var scanner = new Scanner(System.in)
     var sc = new Scanner(System.in)
     println("Select Expense Id to Update: ")
      scanner.nextLine() 
       var exId = sc.nextInt()
      scanner.nextLine()

   println("Please Enter Your Expense: ")
     var expense = sc.nextLine()
      scanner.nextLine()    
    println("Please Enter Your Expense Amount: ")
     var expenseAmt = sc.nextInt()
     scanner.nextLine()
    println("Please Enter Your Expense Date: ")
    var expensedt = sc.nextLine()
      
   


    statement.executeUpdate("UPDATE Expenses SET Expense = '"+ expense +"', ExpenseAmount = " + expenseAmt + ", ExpenseDate = '" + expensedt + "' WHERE ExpenseId = " + exId) // Change query to your table
    retrieveExpense()

     

    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }
  def deleteIncome():Unit = {
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null




    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM Income") // Change query to your table
      log.write("Executing 'SELECT * FROM Income'") 
      println("Income Sources: ")
      while ( resultSet.next() ) {
        print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " " + resultSet.getString(4))
        println()
      }
      
     var scanner = new Scanner(System.in)
     var sc = new Scanner(System.in)
     println("Select Income Id to Delete: ")
       var incId = sc.nextInt()
        scanner.nextLine()
    statement.executeUpdate("DELETE FROM Income WHERE incomeId = " + incId) // Change query to your table
    retrieveIncome()

    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }
  

  
  def deleteExpenses():Unit = {
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/CashFlow" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "Lionprince123_" // Update to include your password

    var connection:Connection = null




    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM Expenses") // Change query to your table
      println("Expenses: ")
      log.write("Executing 'SELECT * FROM Expenses'")
      while ( resultSet.next() ) {
        print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " " + resultSet.getString(4))
        println()
      }
      
     var scanner = new Scanner(System.in)
     var sc = new Scanner(System.in)
     println("Select Expense Id to Delete: ")
       var exId = sc.nextInt()
        scanner.nextLine()

    

    statement.executeUpdate("DELETE FROM Expenses WHERE ExpenseId = " + exId) // Change query to your table
    retrieveExpense()

     

    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
  }
}