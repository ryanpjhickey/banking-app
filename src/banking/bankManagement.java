package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class bankManagement { // these class provides all
							// bank method

	private static final int NULL = 0;

	static Connection gco = connection.getConnection();
	static String sql = "";
	public static boolean
	createAccount(String username,
				int pw) // create account function
	{
		try {
			// validation
			if (username == "" || pw == NULL) {
				System.out.println("All Field Required!");
				return false;
			}
			// query
			Statement pstate = gco.createStatement();
			sql = "INSERT INTO user(username,bal,pw) values('"
				+ username + "',1000," + pw + ")";

			// Execution
			if (pstate.executeUpdate(sql) == 1) {
				System.out.println(username
								+ ", Now You Login!");
				return true;
			}
			// return
		}
		catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username Not Available!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean
	loginAccount(String username, int pw) // login method
	{
		try {
			// validation
			if (username == "" || pw == NULL) {
				System.out.println("All Field Required!");
				return false;
			}
			// query
			sql = "select * from user where username='"
				+ username + "' and pw=" + pw;
			PreparedStatement pstate
				= gco.prepareStatement(sql);
			ResultSet resSet = pstate.executeQuery();
			// Execution
			BufferedReader sc = new BufferedReader(
				new InputStreamReader(System.in));

			if (resSet.next()) {
				// after login menu driven interface method

				int ch = 5;
				int amt = 0;
				int senderAcc = resSet.getInt("acc_no");
				;
				int receiveAcc;
				while (true) {
					try {
						System.out.println(
							"Hello, "
							+ resSet.getString("username"));
						System.out.println(
							"1)Transfer Money");
						System.out.println("2)View Balance");
						System.out.println("5)LogOut");

						System.out.print("Enter Choice:");
						ch = Integer.parseInt(
							sc.readLine());
						if (ch == 1) {
							System.out.print(
								"Enter Receiver A/c No:");
							receiveAcc = Integer.parseInt(
								sc.readLine());
							System.out.print(
								"Enter Amount:");
							amt = Integer.parseInt(
								sc.readLine());

							if (bankManagement
									.transferMoney(
										senderAcc, receiveAcc,
										amt)) {
								System.out.println(
									"MSG : Money Sent Successfully!\n");
							}
							else {
								System.out.println(
									"ERR : Failed!\n");
							}
						}
						else if (ch == 2) {

							bankManagement.getBalance(
								senderAcc);
						}
						else if (ch == 5) {
							break;
						}
						else {
							System.out.println(
								"Err : Enter Valid input!\n");
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else {
				return false;
			}
			// return
			return true;
		}
		catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username Not Available!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void
	getBalance(int accNo) // fetch bal method
	{
		try {

			// query
			sql = "select * from user where acc_no="
				+ accNo;
			PreparedStatement pstate
				= gco.prepareStatement(sql);

			ResultSet resSet = pstate.executeQuery(sql);
			System.out.println(
				"-----------------------------------------------------------");
			System.out.printf("%12s %10s %10s\n",
							"Account No", "Name",
							"Balance");

			// Execution

			while (resSet.next()) {
				System.out.printf("%12d %10s %10d.00\n",
								resSet.getInt("acc_no"),
								resSet.getString("username"),
								resSet.getInt("bal"));
			}
			System.out.println(
				"-----------------------------------------------------------\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean transferMoney(int sender_acc,
										int receiver_acc,
										int amount)
		throws SQLException // transfer money method
	{
		// validation
		if (receiver_acc == NULL || amount == NULL) {
			System.out.println("All Field Required!");
			return false;
		}
		try {
			gco.setAutoCommit(false);
			sql = "select * from user where acc_no="
				+ sender_acc;
			PreparedStatement ps
				= gco.prepareStatement(sql);
			ResultSet resSet = ps.executeQuery();

			if (resSet.next()) {
				if (resSet.getInt("bal") < amount) {
					System.out.println(
						"Insufficient Balance!");
					return false;
				}
			}

			Statement pstate = gco.createStatement();

			// debit
			gco.setSavepoint();

			sql = "update user set bal=bal-"
				+ amount + " where acc_no=" + sender_acc;
			if (pstate.executeUpdate(sql) == 1) {
				System.out.println("Amount Debited!");
			}

			// credit
			sql = "update user set bal=bal+"
				+ amount + " where acc_no=" + receiver_acc;
			pstate.executeUpdate(sql);

			gco.commit();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			gco.rollback();
		}
		// return
		return false;
	}
}
