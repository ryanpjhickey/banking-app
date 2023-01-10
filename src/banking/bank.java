package banking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bank {
	public static void main(String args[]) //main class of bank
		throws IOException
	{

		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		String username = "";
		int pw;
		int acc_no;
		int char;

		while (true) {
			System.out.println("\n ->|| Welcome to InBank ||<- \n");
			System.out.println("1)Create Account");
			System.out.println("2)Login Account");

			try {
				System.out.print("\n Enter Input:"); //user input
				char = Integer.parseInt(bReader.readLine());

				switch (char) {
				case 1:
					try {
						System.out.print("Enter Unique UserName:");
						username = bReader.readLine();
						System.out.print("Enter New Password:");
						pw = Integer.parseInt(bReader.readLine());

						if (bankManagement.createAccount(username, pw)) {
							System.out.println("MSG : Account Created Successfully!\n");
						}
						else {
							System.out.println("ERR : Account Creation Failed!\n");
						}
					}
					catch (Exception e) {
						System.out.println("ERR : Enter Valid Data::Insertion Failed!\n");
					}
					break;

				case 2:
					try {
						System.out.print("Enter UserName:");
						username = bReader.readLine();
						System.out.print("Enter Password:");
						pw = Integer.parseInt(bReader.readLine());

						if (bankManagement.loginAccount(username, pw)) {
							System.out.println("MSG : Logout Successfully!\n");
						}
						else {
							System.out.println("ERR : login Failed!\n");
						}
					}
					catch (Exception e) {
						System.out.println("ERR : Enter Valid Data::Login Failed!\n");
					}
					break;

				default: 
					System.out.println("Invalid Entry!\n");
				}

				if (char == 5) {
					System.out.println("Exited Successfully!\n\n Thank You :)");
					break;
				}
			}
			catch (Exception e) {
				System.out.println("Enter Valid Entry!");
			}
		}
		bReader.close();
	}
}
