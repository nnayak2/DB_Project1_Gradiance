
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;


public class firstExample {

    static String studentName = "";
	static String studentId = ""; 
	static String tokenNum = "";
	static String profName = "";
	static String profId = "";
	
	
	public static void main(String[] args) 
	{
    	menu1();
	}
	
    public static void menu1()	
    {	
		int	 num = 0;
		boolean flag = true;
    	while(flag)
    	{
    		num = 0;
    		System.out.println("!!! GRADIANCE C Main Menu !!!");
    		System.out.println("Enter 1 to login");
    		System.out.println("Enter 2 to create a Student Account");
    		System.out.println("Enter 3 to exit");
    		
    		System.out.println("Enter Choice :");

    		Scanner scanIn = new Scanner(System.in);

    		while (!(scanIn.hasNextInt()))
			{
				System.out.println("Enter a valid Integer ");
				scanIn.next();
			}
		
			num = scanIn.nextInt();

    	    switch(num)
    	    {
        		case 1: menu21(); 
        			    break;
        		
        		case 2: addStudent();
        				break;
        		
        		case 3: flag = false;
        			    System.out.println("BYE BYE !!!");
        			    break;
        		
        		default: System.out.println("Invalid Input");
        				 break;
    	    }
    	}
    }
    
    public static void menu21()	
    {
    	String userId = "";
    	String pswrd = "";
    	String tAFlag = "N";
    	
    	while(true)
    	{
    		System.out.println("Press 0 to return to the Main Menu ");
    		
    		System.out.println("Enter UID ");
    		Scanner scanIn = new Scanner(System.in);
    		userId = scanIn.nextLine();
       		if (userId.equals("0"))
       		{
       			return;
       		}
    		
    		System.out.println("Password");
    		pswrd = scanIn.nextLine();
       		if (pswrd.equals("0"))
       		{
       			return;
       		}

    		
    		Connection con = null;
    		PreparedStatement ps_1 = null;
    		ResultSet rs1 = null;

			try  
			{
				con = JDBCHelper.getConnection();
				ps_1 = con.prepareStatement("SELECT Sname,IsTA FROM Student WHERE ID = ? AND Pswrd = ?");

				ps_1.setString(1, userId.trim());
				ps_1.setString(2, pswrd);
				
				rs1 = ps_1.executeQuery();
								
				if (!rs1.next())
				{

						rs1 = null;
						ps_1 = null;
						
						ps_1 = con.prepareStatement("SELECT Pname FROM Professor WHERE ID = ? AND Pswrd = ?");
						ps_1.setString(1, userId.trim());
						ps_1.setString(2, pswrd);

						rs1 = ps_1.executeQuery();
						
						if (!rs1.next())
						{
							System.out.println("Username or password Invalid"); 
						}
						
						else 
						{
							profId = userId;
							profName = rs1.getString("Pname");
							System.out.println("Welcome Prof " + profName);
				 
							menu41();
				    	}
				}
				
				else 
				{
					studentId = userId;
					studentName = rs1.getString("Sname");
					tAFlag = rs1.getString("IsTA");
					
					if (tAFlag.equals("Y"))
					{
						System.out.println("Welcome " + studentName);
						System.out.println("TA MENU");
						TAInter();
					}
					
					else
					{
						System.out.println("Welcome " + studentName);
						menu31();						
					}
					
		    	}
		}
			
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
      }
    }
    
    public static void TAInter()
    {
    	int choice = 0;
    	boolean flag = true;
    	while(flag)
    	{
    		System.out.println("Enter 1 to View Student Menu");
    		System.out.println("Enter 2 to View TA Menu");
    		System.out.println("Enter 3 to go Back");
    		
    		Scanner scanIn = new Scanner(System.in);
    		while (!(scanIn.hasNextInt()))
			{
				System.out.println("Enter a valid Integer ");
				scanIn.next();
			}

    		choice = scanIn.nextInt();
    		
    		switch(choice)
    		{
    			case 1: menu31(); 
    				    break;
    			
    			case 2: menu52(); 
    			        break;

    			case 3: flag = false;
    					return;

    			default: 
    				System.out.println("Invalid Input");
    				break;
    		}
    	}
    }

    public static void menu31()
    {
    	int choice = 0;
    	boolean flag = true;
    	while(flag)
    	{
    		System.out.println("enter 1 to Select course");
    		System.out.println("enter 2 to Add course");
    		System.out.println("enter 3 to go Back");
    		
    		Scanner scanIn = new Scanner(System.in);

    		while (!(scanIn.hasNextInt()))
			{
				System.out.println("Enter a valid Integer ");
				scanIn.next();
			}

    		choice = scanIn.nextInt();
    		
    		switch(choice)
    		{
    			case 1: menu32(); 
    				    break;
    			
    			case 2: menu33(); 
    			        break;

    			case 3: flag = false;
    					return; 
    			
    			default: 
    				System.out.println("Invalid Input");
    				break;
    		}
    	}
    }
    
    public static void menu32()
    {	
    	while(true)
    	{	
    		Connection con = null;
    		PreparedStatement ps_1 = null;
    		PreparedStatement ps_2 = null;
    		ResultSet rs1 = null;
    		ResultSet rs2 = null;

    		int index = 0;
    		String courseToken  = "";
    		String[] courseArray = new String[20];
    		String courseId = "";
    		String courseName = "";
			int choice = 0;
    		try
    		{   
    			con = JDBCHelper.getConnection();
    			ps_1 = con.prepareStatement("SELECT TokenNum FROM Enrollment WHERE ID = ?");
    			ps_1.setString(1, studentId);

    			rs1 = ps_1.executeQuery();
				index = 0;

    			while(rs1.next())
    			{	
    				ps_2 = null;
    				rs2 = null;
    				
    				courseToken = rs1.getString("TokenNum");
    				
    				ps_2 = con.prepareStatement("SELECT CourseID, CourseName FROM Course WHERE TokenNum = ?");
    				ps_2.setString(1, courseToken);
    				rs2 = ps_2.executeQuery();
    				if(rs2.next())
    				{	
    					courseId = rs2.getString("CourseID");
    					courseName = rs2.getString("CourseName");
    					System.out.println("Enter : " + index + " for " + courseId + "-" + courseName);
    					courseArray[index] = courseToken;
    					index++;

    				}
    			}
    			
    			System.out.println("Enter : "+ index + " to go back");
    			
    			Scanner scanIn = new Scanner(System.in);
        		while (!(scanIn.hasNextInt()))
    			{
    				System.out.println("Enter a valid Integer ");
    				scanIn.next();
    			}

    			
    			choice = scanIn.nextInt();
    			if (choice == index)
    			{
    				return;
    			}
    			else if(choice >= 0 && choice < index)
    			{
    				tokenNum = courseArray[choice];
    				menu3A();
    			}
    			else
    			{
    				System.out.println("Invalid option");
    			}
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    		finally
    		{
    			JDBCHelper.close(rs2);
    			JDBCHelper.close(rs1);
    			JDBCHelper.close(ps_2);
    			JDBCHelper.close(ps_1);
    			JDBCHelper.close(con);
    		}
    	}
    }

    public static void menu3A()
    {   
    	boolean flag = true;
    	int choice = 0;

    	while (flag)
    	{
    		System.out.println("Press 1 to view scores");
    		System.out.println("Press 2 to Attempt Homework");
    		System.out.println("Press 3 to view past submissions");
    		System.out.println("Press 4 to view Notifications");
    		System.out.println("Press 5 to go Back");
    	
    		Scanner scanIn = new Scanner(System.in);
    		while (!(scanIn.hasNextInt()))
			{
				System.out.println("Enter a valid Integer ");
				scanIn.next();
			}

    		choice = scanIn.nextInt();
    		
    		switch(choice)
    		{
    		case 1: menu3A1();
    				break;
    		
    		case 2: menu3A2();
    			    break;
    		
    		case 3: menu3A3();
    				break;
    		
    		case 4: menu3A4();
    				break;
    		
    		case 5: flag = false;
    				return;
    				
    		default: System.out.println("Wrong Input");
    				 break;
    		}
    	}
    }
    
    public static void menu33()
    {	
    	int token = 0; 
    	Vector<String> tokenN = null;
    	TreeSet<String> tokenE = null;
    	boolean flag = true;
    	int count = 0;

		Connection con = null;
		PreparedStatement ps_1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement ps_2 = null;
		ResultSet rs2 = null;
		System.out.println("flow");

		String Level = "";
    	String IsTA = "";
    	try
    	{
			System.out.println("flow");
    		
    		count = 0;
    		while(flag)
        	{
    			System.out.println("flow");
    		
    			tokenN = new Vector<String>();
    			tokenE = new TreeSet<String>();
    		
    			SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
    			String d = sd.format(new Date());
				
    			con = JDBCHelper.getConnection();
    		
    			ps_1 = con.prepareStatement("SELECT SLEVEL,IsTA FROM STUDENT WHERE ID = ?");
    			ps_1.setString(1, studentId);
    			
    			rs1 = ps_1.executeQuery();
    			rs1.next();    			
    			Level = rs1.getString("SLEVEL");
    			IsTA = rs1.getString("IsTA");
    			System.out.println(IsTA);
    			if (Level.equals("G"))
    				Level = "Graduate";
    			else
    				Level = "Undergrad";
    			
    			
				ps_2 = con.prepareStatement("SELECT TOKENNUM FROM ENROLLMENT WHERE ID = ?");
				ps_2.setString(1, studentId);
				
				rs2 = ps_2.executeQuery();
				while (rs2.next())
				{
					tokenE.add(rs2.getString("TOKENNUM"));
				}
				
				ps_1 = con.prepareStatement("SELECT TOKENNUM, COURSEID, COURSENAME FROM COURSE WHERE ENROLLENDDATE > TO_DATE(?,'DD-MM-YYYY') AND COURSELEVEL = ?");
				ps_1.setString(1, d);
				ps_1.setString(2, Level);
    		
				rs1 = ps_1.executeQuery();
				count = 0;
				
				System.out.println("List of active courses");
				
				while (rs1.next())
				{
					String abc = rs1.getString("TOKENNUM");
					if (tokenE.contains(rs1.getString("TOKENNUM")))
					{
						System.out.print("  " + rs1.getString("TOKENNUM") + " " + rs1.getString("COURSEID") + "-" + rs1.getString("COURSENAME") + " ");
						System.out.println("(Enrolled)");
					}
					else
					{
						if (IsTA.equals("Y"))
						{
							ps_2 = con.prepareStatement("SELECT COUNT(*) FROM TASSISTANT WHERE TOKENNUM = ? AND ID = ?");
							ps_2.setString(1, abc);
							ps_2.setString(2, studentId);
							
							rs2 = ps_2.executeQuery();
							rs2.next();
							if(rs2.getInt(1) != 0)
							{
								continue;
							}
						}
						tokenN.add(abc);
						System.out.print(count + " " + rs1.getString("TOKENNUM") + " " + rs1.getString("COURSEID") + "-" + rs1.getString("COURSENAME") + " ");
						System.out.println("");
						count++;
					}
				}
    		
				System.out.println(count + " Return");
    		
				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}
				token = scanIn.nextInt();
    			
				if (token == count)
				{
					flag = false;	
					return;
				}
				
				else if (token >= 0 && token < count)
				{
					int maxEnrolled = 0;
					int	currentEnroll = 0;
    					
					if (IsTA.equals("Y"))
					{
						if (checkTAEnrollment(tokenN.elementAt(token)))
						{
							System.out.println("FLOW");
							return;
						}
					}
					
					
					ps_1 = con.prepareStatement("SELECT CourseID, CourseName, TokenNum, EnrollEndDate, MaxEnrolled, SEnrolled FROM Course WHERE TokenNum = ?"
    							+ "	AND EnrollEndDate > TO_DATE(?,'DD-MM-YYYY')");
    				ps_1.setString(1, tokenN.elementAt(token));
    				ps_1.setString(2,d);
    				
    				rs1 = ps_1.executeQuery();
        				
    				if(!rs1.next())
            		{
            			System.out.println("Enrollment date exceeded");
            		}

    				else
    				{
    					maxEnrolled = rs1.getInt("MaxEnrolled");
    					currentEnroll = rs1.getInt("SEnrolled");
    					
    					if(currentEnroll >= maxEnrolled)
    					{
    						System.out.println("Course is full. Cannot register");
    					}
    					
    					else
    					{	
    						ps_1 = null;
    						rs1 = null;
    						currentEnroll++;
    						
    						ps_1=con.prepareStatement("INSERT INTO Enrollment VALUES (?,?,?)");
    						ps_1.setString(1, studentId);
    						ps_1.setString(2, tokenN.elementAt(token));
    						ps_1.setString(3, studentName);

    						maxEnrolled = ps_1.executeUpdate();
    							
    						if (maxEnrolled == 1)
    						{
    							ps_1 = null;
    							ps_1=con.prepareStatement("UPDATE COURSE SET SENROLLED = ? WHERE TokenNum = ?");
    							ps_1.setInt(1, currentEnroll);
   								ps_1.setString(2, tokenN.elementAt(token));
   								
   								maxEnrolled = ps_1.executeUpdate();
 
   								if (maxEnrolled == 1)
    							{
    								System.out.println("Enrolled");
    								System.out.println("Press any key to return");
    								scanIn.nextLine();
    								return;
    							}
    							else
    							{
    								System.out.println("Unable to enroll");
    							}
    						}
    						else
    						{
    							System.out.println("Unable to Update");
    						}
    					}
    				}
				}
				else
				{
					System.out.println("Invalid input");
				}

	    	}
    	}	
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		JDBCHelper.close(rs1);
    		JDBCHelper.close(rs2);
    		JDBCHelper.close(ps_2);
    		JDBCHelper.close(ps_1);
    		JDBCHelper.close(con);
    	}

    }
    
    public static void menu3A1()
    {
		Connection con = null;
		PreparedStatement ps_1 = null;
		ResultSet rs1 = null;

		PreparedStatement ps_2 = null;
		ResultSet rs2 = null;

    	try
		{
    		
    		int hwc = 0;
    		while(true)
    	    {
    			con = JDBCHelper.getConnection();
    			System.out.println("Scores");
    			
    			ps_1 = con.prepareStatement("SELECT EID,COUNT(*) as hwcnt FROM  VIEW_SCORES WHERE STUD_ID = ? AND TOKENNUM = ? GROUP BY EID");
    			ps_1.setString(1, studentId);
    			ps_1.setString(2, tokenNum);
    			
    			rs1 = ps_1.executeQuery();
    			while (rs1.next())
    			{	
    				int hwcnt = rs1.getInt("hwcnt");
    			
    				hwc = 0;
    				while(hwc <= hwcnt)
    				{
    					hwc++;
    					ps_2 = con.prepareStatement("SELECT SCORES FROM VIEW_SCORES WHERE EID = ? AND STUD_ID = ? AND TOKENNUM = ? AND ATTEMPT_NO = ?");
    					ps_2.setString(1, rs1.getString("EID"));
    					ps_2.setString(2, studentId);
    					ps_2.setString(3, tokenNum);
    					ps_2.setInt(4, hwc);
    					
    					rs2 = ps_2.executeQuery();
    					if (rs2.next())
    					System.out.println("HW" + rs1.getString("EID") + " Attempt Number:" + hwc +" Score:"+rs2.getInt("SCORES"));
    				}
    			}
    			
    			System.out.println("Press any key to return");
    			Scanner scanIn = new Scanner(System.in);
    			scanIn.nextLine();
    			return;
    				
    		}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		JDBCHelper.close(rs1);
    		JDBCHelper.close(rs2);
    		JDBCHelper.close(ps_2);
    		JDBCHelper.close(ps_1);
    		JDBCHelper.close(con);
    	}
    }
    
    public static void menu3A2()
    {
		
		Connection con = null;
		PreparedStatement ps_1 = null;
		ResultSet rs1 = null;

		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		String d = sd.format(new Date());
		
		Vector<String> hwrk = null;
		Vector<String> attempts = null;
		
		int index = 0, choice = 0,hw = 0, att = 0;
		String tempString = "";

		try
    	{
    		
    		con = JDBCHelper.getConnection();
    		
    		while(true)
    		{
    			hwrk = new Vector<String>();
    			attempts = new Vector<String>();
    			
    			System.out.println("Attempt Homework ");
    			
    			updateExercise();
    			
    			ps_1 = con.prepareStatement("SELECT EID FROM EXERCISE WHERE TOKENNUM = ? AND EENDDATE >= TO_DATE(?,'DD-MM-YYYY')");
    			ps_1.setString(1, tokenNum);
    			ps_1.setString(2, d);
    			
    			rs1 = ps_1.executeQuery();
    			
    			while (rs1.next())
    			{
    				hwrk.add(rs1.getString("EID"));
    			}
    			
    			
    			Iterator<String> itr = hwrk.iterator();
    			index = 0;
    			while (itr.hasNext())
    			{
        			ps_1 = null;
        			rs1 = null;
        			
        			tempString =  itr.next();
        			

    				ps_1 = con.prepareStatement("SELECT ATTEMPTSNO FROM ATTEMPT WHERE TOKENNUM = ? AND HOMEWORK_NO = ? AND STUDID = ?");
    				ps_1.setString(1, tokenNum);
    				ps_1.setString(2, tempString.trim());
    				ps_1.setString(3, studentId);
    				
    				rs1 = ps_1.executeQuery();
    				rs1.next();
    				
    				System.out.println(index + " " + "HW"+ tempString + " Attempts Remaining : " + (rs1.getInt("ATTEMPTSNO") == -1? "Unlimited" : rs1.getInt("ATTEMPTSNO")));
    				attempts.add(rs1.getString("ATTEMPTSNO"));
    				index++;
    			}
    			
    			System.out.println("Press "+ index +" to go back");
    			
    			Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}
    			choice = scanIn.nextInt();
    			
    			if(choice == index)
    			{
    				return;
    			}	
    			
    			else if (choice >= 0 && choice < index)
    			{
    				hw = Integer.parseInt(hwrk.get(choice));
    				att = Integer.parseInt(attempts.get(choice));
    				
    				if (att == 0 )
    				{
    					System.out.println("No attempts left for the selected homework");
    				}
    				else
    				{
    					menu3A21(hw);
    				}
    			}
    			else
    			{
    				System.out.println("Invalid Choice");

    			}
    		}
    	
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}

    }
    
    public static void updateExercise()
    {
		Connection con = null;
		
		PreparedStatement ps_1 = null;
		ResultSet rs1 = null;

		PreparedStatement ps_2 = null;
		ResultSet rs2 = null;
		
		int retries = 0;
		
    	try
    	{
    		con = JDBCHelper.getConnection();
			ps_1 = con.prepareStatement("SELECT EID,NumRetries FROM EXERCISE WHERE TOKENNUM = ?");
			ps_1.setString(1, tokenNum);
			
			rs1 = ps_1.executeQuery();
			while (rs1.next())
			{
				ps_2 = con.prepareStatement("SELECT COUNT(*) FROM ATTEMPT WHERE HOMEWORK_NO = ? AND TOKENNUM = ? AND STUDID = ?");
				ps_2.setString(1, rs1.getString("EID"));
				ps_2.setString(2, tokenNum);
				ps_2.setString(3, studentId);
				
				rs2 = ps_2.executeQuery();
				rs2.next();
				
				if (rs2.getInt(1) == 0)
				{
					retries = rs1.getInt("NumRetries");
                    ps_2 = con.prepareStatement("INSERT INTO ATTEMPT(TOKENNUM,STUDID,HOMEWORK_NO,ATTEMPTSNO) VALUES (?,?,?,?)");

                    ps_2.setString(1, tokenNum);
                    ps_2.setString(2, studentId);
                    ps_2.setString(3, rs1.getString("EID"));
                    ps_2.setInt(4, retries);
				
                    int count = ps_2.executeUpdate();
                    if (count != 1)
                    {
                    	System.out.println("Error");
                    	return;
                    }
				
				}
			}
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(rs2);
			JDBCHelper.close(ps_2);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}

    }
        
    public static void menu3A21(int hwrk_num)
    {
    	
		System.out.println("Questions for HW"+hwrk_num);
    	
		Connection con = null;

    	PreparedStatement ps_1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps_2 = null;
		ResultSet rs2 = null;

		Savepoint savePoint = null;
		
		int choice = 0;
		String tempString = "";
		int correctPoints = 0, inCorrectPoints = 0, score = 0;
		
    	try
    	{
			con = JDBCHelper.getConnection();
			con.setAutoCommit(false);
			
    		while(true)
    		{    			
    			savePoint = con.setSavepoint();
    			ps_1 = con.prepareStatement("SELECT PntCorrect, PntInPenalty FROM EXERCISE WHERE TOKENNUM = ? AND EID = ?");
    			ps_1.setString(1, tokenNum);
    			ps_1.setString(2, Integer.toString(hwrk_num));
    			
    			rs1 = ps_1.executeQuery();
    			rs1.next();
    			
    			correctPoints = rs1.getInt("PntCorrect");
    			inCorrectPoints = rs1.getInt("PntInPenalty");
    			
    			ps_1 = null;
    			rs1 = null;
    			  			
    			ps_1 = con.prepareStatement("SELECT Qid, Question, Optiontext1, Optiontext2, Optiontext3, Optiontext4, CORRECTVALUE,ATTEMPT FROM SUBMISSION " + 
        				"WHERE EID = ? AND TOKENNUM = ? AND SID = ? AND SUBMITTED = ?");
    			ps_1.setString(1,Integer.toString(hwrk_num));
    			ps_1.setString(2,tokenNum);
    			ps_1.setString(3, studentId);
    			ps_1.setString(4, "N");

              	rs1 = ps_1.executeQuery();
        		
              	if (!rs1.next())
              	{
              		updatehomework(hwrk_num);
              	}
              	
              	else
              	{
              		int count = 1;
              		System.out.println("HW"+hwrk_num +"    Questions");
              		score = 0;
              		
              		do
        			{	
              			System.out.println(rs1.getString("Question"));
              			System.out.println("1) "+rs1.getString("OptionText1"));
              			System.out.println("2) "+rs1.getString("OptionText2"));
              			System.out.println("3) "+rs1.getString("OptionText3"));
              			System.out.println("4) "+rs1.getString("OptionText4"));
              			
              			System.out.println("Your Choice ? :");
              			Scanner scanIn = new Scanner(System.in);
                		while (!(scanIn.hasNextInt()))
            			{
            				System.out.println("Enter a valid Integer ");
            				scanIn.next();
            			}
                		choice = scanIn.nextInt();
                		
                		while (choice < 0 || choice > 5)
                		{
                			System.out.println("Enter choice between 1 and 4");
                    		choice = scanIn.nextInt();
                		}
                		
                		System.out.println("Enter a short explaination for your answer (Optional) < 100 characters");
                		Scanner scanString = new Scanner(System.in);
                		tempString = scanString.nextLine();

                		if (Integer.toString(choice).equals(rs1.getString("CORRECTVALUE")))
                		{
                			score += correctPoints;
                		}
                		else 
                		{
                			score -= inCorrectPoints;
                		}

                		ps_2 = null;
                		
                		if (tempString.length() > 100)
                			tempString = tempString.substring(0,100); 
                		
                		ps_2 = con.prepareStatement("UPDATE SUBMISSION SET SUBMITTED = ?, Selected_option = ?, Expltn = ? " +
                				"WHERE EID = ? AND TOKENNUM = ? AND SID = ? AND ATTEMPT = ? AND QID = ?");
                		ps_2.setString(1,"Y");
                		ps_2.setString(2, Integer.toString(choice));
                		ps_2.setString(3, tempString);
            			ps_2.setString(4,Integer.toString(hwrk_num));
            			ps_2.setString(5,tokenNum);
            			ps_2.setString(6,studentId);
            			ps_2.setInt(7,rs1.getInt("ATTEMPT"));
            			ps_2.setString(8,rs1.getString("QID"));
                		
                		count = ps_2.executeUpdate();
                		if (count != 1)
                		{
                			System.out.println("cnt"+count);
                			System.out.println("Unable to update database");
                			con.rollback(savePoint);
                			con.setAutoCommit(true);
                			return;
                		}
                		
        			} while (rs1.next());
              		
              		ps_1 = null;
              		rs1 = null;
              		
              		int att = 1;
              		
              		ps_1 = con.prepareStatement("SELECT MAX(ATTEMPT_NO) FROM VIEW_SCORES WHERE TOKENNUM = ? AND EID = ? AND STUD_ID = ?");
              		ps_1.setString(1, tokenNum);
              		ps_1.setString(2, Integer.toString(hwrk_num));
              		ps_1.setString(3, studentId);
              		
              		rs1 = ps_1.executeQuery();
              		rs1.next();
              		
              		if (rs1.getInt(1) == 0)
              			att = 1;
              		else
              			att = rs1.getInt(1) + 1;

              		ps_1 = null;
              		rs1 = null;
              		
              		ps_1 = con.prepareStatement("INSERT INTO VIEW_SCORES (TOKENNUM, EID, STUD_ID, ATTEMPT_NO, SCORES) VALUES (?,?,?,?,?)");
              		ps_1.setString(1, tokenNum);
              		ps_1.setString(2, Integer.toString(hwrk_num));
              		ps_1.setString(3, studentId);
              		ps_1.setInt(4,  att);
              		ps_1.setInt(5, score);
              		
              		count = ps_1.executeUpdate();
              		
              		if (count != 1)
              		{
              			System.out.println("Error Updating scores");
            			con.rollback(savePoint);
            			con.setAutoCommit(true);
            			return;
              		}
//////////////////////////////////////////////////
              		
              		ps_1 = null;
              		rs1 = null;
              		
              		ps_1 = con.prepareStatement("SELECT COUNT(*) FROM FSCORE WHERE	TOKENNUM = ? AND EID = ? AND STUD_ID = ?");
              		ps_1.setString(1, tokenNum);
              		ps_1.setString(2, Integer.toString(hwrk_num));
              		ps_1.setString(3, studentId);
              	    
              		rs1 = ps_1.executeQuery();
              		rs1.next();
              		
              		if (rs1.getInt(1) == 0)
              		{
              			ps_2 = con.prepareStatement("INSERT INTO FSCORE(TOKENNUM,EID,STUD_ID,SCORES) VALUES (?,?,?,?)");
              			ps_2.setString(1, tokenNum);
              			ps_2.setString(2, Integer.toString(hwrk_num));
              			ps_2.setString(3, studentId);
              			ps_2.setInt(4, score);
              			
              			count = ps_2.executeUpdate();
                  		if (count != 1)
                  		{
                  			System.out.println("Error Updating scores");
                			con.rollback(savePoint);
                			con.setAutoCommit(true);
                			return;
                  		}
              		}
              		else
              		{
              			ps_2 = null;
              			rs2 = null;
              			ps_2 = con.prepareStatement("SELECT ScoreSelection FROM Exercise WHERE TOKENNUM = ? AND EId = ?");
              			ps_2.setString(1, tokenNum);
              			ps_2.setString(2, Integer.toString(hwrk_num));

              			rs2 = ps_2.executeQuery();
              			rs2.next();
              			String sel = rs2.getString("SCORESELECTION");
              			
              			if (sel.equals("MAX"))
              			{
              				ps_1 = con.prepareStatement(" SELECT MAX(SCORES) FROM VIEW_SCORES WHERE TOKENNUM = ? AND EId = ? AND STUD_ID = ?");
                  			ps_1.setString(1, tokenNum);
                  			ps_1.setString(2, Integer.toString(hwrk_num));
                  			ps_1.setString(3, studentId);

                  			rs1 = ps_1.executeQuery();
                  			rs1.next();
                  			int tScore = rs1.getInt(1);

                  			ps_1 = con.prepareStatement("UPDATE FSCORE SET SCORES = ? WHERE TOKENNUM = ? AND EID = ? AND STUD_ID = ?");
                  			ps_1.setInt(1,tScore);
                  			ps_1.setString(2, tokenNum);
                  			ps_1.setString(3, Integer.toString(hwrk_num));
                  			ps_1.setString(4,studentId);
                  			
                  			count = ps_1.executeUpdate();
                  			if (count != 1)
                      		{
                      			System.out.println("Error Updating scores");
                    			con.rollback(savePoint);
                    			con.setAutoCommit(true);
                    			return;
                      		}
              			}

              			
              			else if (sel.equals("AVG"))
              			{
              				ps_1 = con.prepareStatement(" SELECT AVG(SCORES) FROM VIEW_SCORES WHERE TOKENNUM = ? AND EId = ? AND STUD_ID = ?");
                  			ps_1.setString(1, tokenNum);
                  			ps_1.setString(2, Integer.toString(hwrk_num));
                  			ps_1.setString(3, studentId);

                  			rs1 = ps_1.executeQuery();
                  			rs1.next();
                  			float tfScore = rs1.getInt(1);

                  			ps_1 = con.prepareStatement("UPDATE FSCORE SET SCORES = ? WHERE TOKENNUM = ? AND EID = ? AND STUD_ID = ?");
                  			ps_1.setFloat(1,tfScore);
                  			ps_1.setString(2, tokenNum);
                  			ps_1.setString(3, Integer.toString(hwrk_num));
                  			ps_1.setString(4,studentId);
                  			
                  			count = ps_1.executeUpdate();
                  			if (count != 1)
                      		{
                      			System.out.println("Error Updating scores");
                    			con.rollback(savePoint);
                    			con.setAutoCommit(true);
                    			return;
                      		}
              			}

              			else 
              			{
                  			ps_1 = con.prepareStatement("UPDATE FSCORE SET SCORES = ? WHERE TOKENNUM = ? AND EID = ? AND STUD_ID = ?");
                  			ps_1.setFloat(1,score);
                  			ps_1.setString(2, tokenNum);
                  			ps_1.setString(3, Integer.toString(hwrk_num));
                  			ps_1.setString(4,studentId);
                  			
                  			count = ps_1.executeUpdate();
                  			if (count != 1)
                      		{
                      			System.out.println("Error Updating scores");
                    			con.rollback(savePoint);
                    			con.setAutoCommit(true);
                    			return;
                      		}
              			}
              		}

/////////////////////////////////////////////////////
              		ps_1 = null;
              		rs1 = null;
              		
              		ps_1 = con.prepareStatement("SELECT ATTEMPTSNO FROM ATTEMPT WHERE TOKENNUM = ? AND STUDID = ? AND HOMEWORK_NO = ?");
              		ps_1.setString(1, tokenNum);
              		ps_1.setString(2, studentId);
              		ps_1.setString(3, Integer.toString(hwrk_num));

              		rs1 = ps_1.executeQuery();
              		rs1.next();
              		
              		att = rs1.getInt("ATTEMPTSNO");
              		
              		if (att > 0)
              	    {
              			att--;
              	    
              			ps_1 = null;
              			rs1 = null;
              		
              			ps_1 = con.prepareStatement("UPDATE ATTEMPT SET ATTEMPTSNO = ? WHERE TOKENNUM = ? AND STUDID = ? AND HOMEWORK_NO = ?");
              			ps_1.setInt(1, att);
              			ps_1.setString(2, tokenNum);
              			ps_1.setString(3, studentId);
              			ps_1.setString(4, Integer.toString(hwrk_num));
              			
              			count = ps_1.executeUpdate();
              		
              			if (count != 1)
              			{
              	    		con.rollback(savePoint);
              				System.out.println("Error Updating table");
                			con.rollback(savePoint);
                			con.setAutoCommit(true);
                			return;
              			}
              			else
              			{
                      		con.commit();              				
              			}
              	    }	
              		
        			con.setAutoCommit(true);
              		
              		System.out.println("Press any key to return");
              		Scanner temp = new Scanner(System.in);
              		temp.nextLine();
              		return;
              		
              	}
            }
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		JDBCHelper.close(rs1);
    		JDBCHelper.close(ps_1);
    		JDBCHelper.close(ps_2);
    		JDBCHelper.close(con);
    	}
    		
    }
  
	public static void updatehomework(int hwrk_num)
	{
    	PreparedStatement ps_1 = null;
		ResultSet rs1 = null;

    	PreparedStatement ps_2 = null;
		ResultSet rs2 = null;

    	Connection con = null;

		int index = 0, select = 0, opt = 0,counter = 0;
		String tempString = "", value = "";
		long randomSeed = 0;
		int corr = 0;
		String[] options = null;		
		String[] allOptions = null;

		Vector<String> questions = null;
		Vector<String> questionText = null;

		Random rand = new Random();
		
		try
		{
			options = new String[4];		
			allOptions = new String[20];

			questions = new Vector<String>();
			questionText = new Vector<String>();
			
			con = JDBCHelper.getConnection();

			Date d = new Date();
			
			int att = 1;
			ps_2 = con.prepareStatement("SELECT MAX(ATTEMPT) FROM SUBMISSION WHERE EID = ? AND TOKENNUM = ? AND SID = ? AND SUBMITTED = ?");
			ps_2.setString(1, Integer.toString(hwrk_num));
			ps_2.setString(2, tokenNum);
			ps_2.setString(3, studentId);
			ps_2.setString(4, "Y");
			
			rs2 = ps_2.executeQuery();
			rs2.next();
			
			if (rs2.getInt(1) == 0)
			{
				att = 1;
			}
			else
			{
				att = rs2.getInt(1) + 1;
			}

			ps_1 = con.prepareStatement("SELECT RANDOMSEED FROM Exercise WHERE TOKENNUM = ? AND EId = ?");
    		ps_1.setString(1,tokenNum);
    		ps_1.setString(2,Integer.toString(hwrk_num));
    		rs1 = ps_1.executeQuery();
    		
    		rs1.next();

    		randomSeed = rs1.getLong("RandomSeed");
    		randomSeed = d.getTime() - randomSeed;
    		
    		rand.setSeed(randomSeed);
    		
    		ps_1 = null;
    		rs1 = null;

			ps_1 = con.prepareStatement("SELECT ID FROM FormExercise WHERE TOKENNUM = ? AND EId = ?");
    		ps_1.setString(1,tokenNum);
    		ps_1.setInt(2,hwrk_num);

    		rs1 = ps_1.executeQuery();
    		while(rs1.next())
    		{	
    			questions.add(rs1.getString("ID"));
    		}
    		
    		ps_1 = null;
    		rs1 = null;
    		
    		Iterator<String> itr = questions.iterator();

    		while (itr.hasNext())
    		{
    			tempString = itr.next();
    			if (tempString.charAt(0) == 'Q')
    			{
    				ps_1 = con.prepareStatement("SELECT TEXT FROM QUESTION WHERE QID = ?");
    				ps_1.setString(1, tempString);
    				
    				rs1 = ps_1.executeQuery();
    				rs1.next();	
    				questionText.add(rs1.getString("TEXT"));
    			}
    			else
    			{
    				ps_1 = con.prepareStatement("SELECT PTEXT FROM PQUESTION WHERE PQID = ?");
    				ps_1.setString(1, tempString);
    				
    				rs1 = ps_1.executeQuery();
    				rs1.next();
    				questionText.add(rs1.getString("PTEXT"));
    			}		
    		}
    		
    		
    		itr = questions.iterator();
    		index = 0;
    		
    		while (itr.hasNext())
    		{
				index = 0;
    			tempString = itr.next();
    			tempString = tempString.trim();
    			
    			if (tempString.charAt(0) == 'Q')
    			{
    				//Correct Option
    				ps_1 = con.prepareStatement("SELECT Optiontext FROM OPTIONS WHERE QID = ? AND CRCTOP = ?");
    				ps_1.setString(1, tempString);
    				ps_1.setString(2, "Y");
    	    				
    				rs1 = ps_1.executeQuery();
    				
    				while(rs1.next())
    				{
    					allOptions[index] = rs1.getString("OptionText");
    					index++;
    				}

    				
    				select = rand.nextInt(index);
    				opt = rand.nextInt(4);
       			    				
    				options[opt] = allOptions[select];
    				corr = opt;
    				
    				ps_1 = null;
    				rs1 = null;
    				
    				//Incorrect options
    				ps_1 = con.prepareStatement("SELECT Optiontext FROM OPTIONS WHERE QID = ? AND CRCTOP = ?");
    				ps_1.setString(1, tempString);
    				ps_1.setString(2, "N");
    				
    				rs1 = ps_1.executeQuery();

    				allOptions = new String[15];
    				index = 0;

    				while(rs1.next())
    				{
    					allOptions[index] = rs1.getString("Optiontext");
    					index++;
    				}
    				
    				TreeSet<Integer> o = new TreeSet<Integer>();
    				
    				select = rand.nextInt(index);
    				o.add(select);
    				
    				while (true) 
    				{
        				select = rand.nextInt(index);
        				if (o.add(select))
        					break;
    				}
    				
    				while (true) 
    				{
        				select = rand.nextInt(index);
        				if (o.add(select))
        					break;
    				}
    				
    				int i = 0;
    				Iterator<Integer> itrInt = o.iterator();
    				while (itrInt.hasNext())
    				{
    					if (i == corr)
    					{
    						i++;
    						continue;
    					}
    					else
    					{			
    						options[i] = allOptions[itrInt.next()];
    						i++;		
    					}
    				}
    				
    			}

    			else
    			{
    				allOptions = new String[20];
    				options = new String[4];
    				
    				ps_1 = con.prepareStatement("SELECT DISTINCT PVALUE FROM POPTIONS WHERE PID = ?");
    				ps_1.setString(1, tempString);
    				
    				rs1 = ps_1.executeQuery();
    				
    				index  = 0;
    				
    				while(rs1.next())
    				{
    					allOptions[index] = rs1.getString("PVALUE");
    					index++;
    				}
    				
    				select = rand.nextInt(index);
    				value = allOptions[select];
    				
    				ps_1 = null;
    				rs1 = null;
    				
    				ps_1 = con.prepareStatement("SELECT POptiontext FROM POPTIONS WHERE PID = ? AND PVALUE = ? AND PCORRECT = ?");
    				ps_1.setString(1, tempString);
    				ps_1.setString(2, value);
    				ps_1.setString(3, "Y");
    				
    				rs1 = ps_1.executeQuery();
    				
    				allOptions = new String[20];
    				index = 0;
    				    				
    				while(rs1.next())
    				{
    					allOptions[index] = rs1.getString("POptionText");
    					index++;
    				}

    				select = rand.nextInt(index);
    				opt = rand.nextInt(4);
    				
    				options[opt] = allOptions[select];
    				corr = opt;
    				
    				ps_1 = null;
    				rs1 = null;
    				    				
    				//Incorrect options
    				ps_1 = con.prepareStatement("SELECT POptiontext FROM POPTIONS WHERE PID = ? AND PVALUE = ? AND PCORRECT = ?");
    				ps_1.setString(1, tempString);
    				ps_1.setString(2, value);
    				ps_1.setString(3, "N");
    				
    				rs1 = ps_1.executeQuery();

    				allOptions = new String[15];
    				index = 0;

    				while(rs1.next())
    				{
    					allOptions[index] = rs1.getString("POptiontext");
    					index++;
    				}
    				
    				Set<Integer> o1 = new TreeSet<Integer>();
    				
    				select = rand.nextInt(index);
    				o1.add(select);
    				
    				while (true) 
    				{
        				select = rand.nextInt(index);
        				if (o1.add(select))
        					break;
    				}
    				
    				while (true) 
    				{
        				select = rand.nextInt(index);
        				if (o1.add(select))
        					break;
    				}
    				
    				int i = 0;
    				Iterator<Integer> itrInt1 = o1.iterator();
    				while (itrInt1.hasNext())
    				{
    					if (i == corr)
    					{
    						i++;
    						continue;
    					}
    					else
    					{
    						options[i] = allOptions[itrInt1.next()];
    						i++;		
    					}
    				}
    			}
    			
    		//Update table
			ps_1 = null;
			rs1 = null;
			
			
			if (tempString.charAt(0) == 'Q')
			{
				
						
				ps_1 = con.prepareStatement("INSERT INTO Submission (EId,TOKENNUM,QId,sId, Question, Optiontext1, Optiontext2, Optiontext3, Optiontext4,"+
						"CORRECTVALUE,ATTEMPT) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				ps_1.setString(1, Integer.toString(hwrk_num));
				ps_1.setString(2, tokenNum);
				ps_1.setString(3, tempString);
				ps_1.setString(4, studentId);
				ps_1.setString(5, questionText.elementAt(counter));
				counter++;
				ps_1.setString(6,options[0]);
				ps_1.setString(7,options[1]);
				ps_1.setString(8,options[2]);
				ps_1.setString(9,options[3]);
				corr++;
				ps_1.setString(10, Integer.toString(corr));
				ps_1.setInt(11, att);
				
				int cnt = ps_1.executeUpdate();
				if (cnt != 1)
				{
					System.out.println("Unable to add questions");
				}
			}
			else
			{
				ps_1 = con.prepareStatement("INSERT INTO Submission (EId,TOKENNUM,QId,sId, Question, Optiontext1, Optiontext2, Optiontext3, Optiontext4,"+
						"CORRECTVALUE,ParamValue,ATTEMPT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
				ps_1.setString(1, Integer.toString(hwrk_num));
				ps_1.setString(2, tokenNum);
				ps_1.setString(3, tempString);
				ps_1.setString(4, studentId);
				ps_1.setString(5, questionText.elementAt(counter));
				counter++;
				ps_1.setString(6,options[0]);
				ps_1.setString(7,options[1]);
				ps_1.setString(8,options[2]);
				ps_1.setString(9,options[3]);
				corr++;
				ps_1.setString(10, Integer.toString(corr));
				ps_1.setString(11, value);
				ps_1.setInt(12, att);
				int cnt = ps_1.executeUpdate();

				if (cnt != 1)
				{
					System.out.println("Unable to add questions");
				}
				
			}
    	  }	
		}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		JDBCHelper.close(rs1);
    		JDBCHelper.close(rs2);
    		JDBCHelper.close(ps_2);
    		JDBCHelper.close(ps_1);
    		JDBCHelper.close(con);
    	}
		
		
		
	}
    
	public static void addStudent()
    {
        	System.out.println("!!! Add Student Menu !!!");
        	System.out.println("Enter 0 at any time to return to the main menu");
        	
        	while(true)
        	{
        		Scanner scanIn = new Scanner(System.in);
        		
        		String studId = "";
        		String password = "" ;
        		String sName = "";
        		String gradLevel = "";
        		
        		int count = 0;
        		
        		Connection con = null;
        		PreparedStatement ps_1 = null;
        		ResultSet rs1 = null;

    			try  
    			{
    				System.out.println("Enter the student id(Max 10)");
    				studId = scanIn.nextLine();
    				
    				if (studId.equals("0"))
    				{
    					return;
    				}
    				
    				System.out.println("Enter the password(Max 10)");
    				password = scanIn.nextLine();

    				if (password.equals("0"))
    				{
    					return;
    				}

    				
    				System.out.println("Enter the student Name (Max 30)");
    				sName = scanIn.nextLine();

    				if (sName.equals("0"))
    				{
    					return;
    				}

    				
    				System.out.println("Enter the grad Level (G - Graduate or UG - UnderGraduate)");
    				gradLevel = scanIn.nextLine();
    				
    				if (gradLevel.equals("0"))
    				{
    					return;
    				}

    				
    				if (studId.trim().equals(""))
    				{
    					System.out.println("Student Id has  been left blank Please Re - enter");
    				}
    				
    				else if (password.trim().equals(""))
    				{
    					System.out.println("Password has been left blank Please Re - enter");
    				}
    				
    				else if ( sName.trim().equals(""))
    				{
    					System.out.println("Name has been left blank Please Re - enter");
    				}
    				
    				else if (gradLevel.trim().equals(""))
    				{
    					System.out.println("Graduate Level has been left blank Please Re - enter");
    				}

    				else if (studId.length() > 10)
    				{
    					System.out.println("Enter Student Id less than 10 characters");
    				}

    				else if (password.length() > 10)
    				{
    					System.out.println("Enter Password less than 10 characters");
    				}
    				
    				else if (sName.length() > 25)
    				{
    					System.out.println("Enter Student Name less than 25 characters");
    				}

    				else if (!(gradLevel.equals("G")  || gradLevel.equals("UG")))
    				{
    					System.out.println("Invalid Graduate level Selected, Please choose G or UG");
    				}


    				else
    				{
    					con = JDBCHelper.getConnection();
    					ps_1 = con.prepareStatement("SELECT COUNT(*) FROM STUDENT WHERE ID = ? ");
    					ps_1.setString(1, studId);
    					
    					rs1 = ps_1.executeQuery();
    					rs1.next();
    					if (rs1.getInt(1) == 0)
    					{
    						ps_1 = con.prepareStatement("INSERT INTO STUDENT (SNAME,ID,PSWRD,SLevel) VALUES (?,?,?,?)");

    						ps_1.setString(1, sName);
    						ps_1.setString(2, studId);
    						ps_1.setString(3, password);
    						ps_1.setString(4, gradLevel);
    				
    						count = ps_1.executeUpdate();
    								
    						if (count != 1)
    						{	
    							System.out.println("Unable to update Already exists"); 
    						}
    					
    						else 
    						{
    							System.out.println("User added successfully");
    							System.out.println("Welcome to gradiance C "+sName);
    							return;
    						}
    					}
    					
    					else
    					{
    						System.out.println("User Id "+ studId + " Already exists");
    					}
    				
    				}
    			}
    			
    			catch (Exception e)
    			{
    				e.printStackTrace();
    			}
    			finally
    			{
    				JDBCHelper.close(rs1);
    				JDBCHelper.close(ps_1);
    				JDBCHelper.close(con);
    			}
        	
         }

    }

	public static void menu41() 
	{
		int a = 0;		
		
		while (true) 
		{
			System.out.println("1. Select Course:");
			System.out.println("2. Add Course:");
			System.out.println("3. Logout");

			Scanner scanIn = new Scanner(System.in);
			
			while (!(scanIn.hasNextInt()))
			{
				System.out.println("Enter a valid Integer ");
				scanIn.next();
			}

			a = scanIn.nextInt();

			switch (a) 
			{		
				case 1: menu42();
						break;
				
				case 2: selecttoken();
						break;
				
				case 3: profId = "";
						profName = "";
						tokenNum = "";
						return;
						
				default: System.out.println("Invalid choice. Try again:");
						 break;
			}
		}
	}
	
	public static void menu42() 
	{
		Connection con = null;
    	PreparedStatement ps_1 = null;
    	ResultSet rs1 = null;

		try
		{
    		con = JDBCHelper.getConnection();
			while (true) 
			{
				
				ps_1 = con.prepareStatement("SELECT TOKENNUM, COURSEID, COURSENAME FROM COURSE WHERE PID = ?");
				ps_1.setString(1,profId);
				rs1 = ps_1.executeQuery();

				int i = 0;
				Vector<String> v = new Vector<String>();
				System.out.println("Enter the option number to select the course" );
				while (rs1.next()) 
				{
					String s = rs1.getString("TOKENNUM");
					String t = rs1.getString("COURSEID");
					String n = rs1.getString("COURSENAME");
					System.out.println(i + " " + s + "  " + t + "-" + n);
					v.addElement(new String(s));
					i++;
				}
				System.out.println(i + " Go Back");

				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}

				int a = scanIn.nextInt();

				if (a == i)
					return;
				else if (a < i && a >= 0) 
				{
					tokenNum = v.elementAt(a);
					menu4a();
				} 
				
				else 
				{
					System.out.println("Invalid choice.");
				}
			}
		}
		catch (Exception oops) 
		{
			oops.printStackTrace();
		}
		finally
    	{
    			JDBCHelper.close(rs1);
    			JDBCHelper.close(ps_1);
    			JDBCHelper.close(con);
    	}

	}

	public static void menu4a() 
	{
		while (true) 
		{
			System.out.println("For Course Token : "+tokenNum);
			System.out.println("1. Add homework");
			System.out.println("2. Add/Remove questions to homework");
			System.out.println("3. Edit homework");
			System.out.println("4. View homework");
			System.out.println("5. View Notification");
			System.out.println("6. Reports");
			System.out.println("7. Back");

			Scanner scanIn = new Scanner(System.in);
			while (!(scanIn.hasNextInt()))
			{
				System.out.println("Enter a valid Integer ");
				scanIn.next();
			}

			int a = scanIn.nextInt();

			switch (a) 
			{
				case 1: menu4a1();
						break;

				case 2: menu4a2();
						break;
						
				case 3: menu4a3();
						break;
						
				case 4: menu4a4();
						break;
						
				case 5: menu4a5();
						break;
						
				case 6: menu4a6();
						break;
						
				case 7: return;
				
				default: System.out.println("Invalid option");
						 break;
			}
		}
	}
	
	public static void menu4a1()
	{

		Scanner scanInt = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);

		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		String d = sd.format(new Date());

		
		String startDate, endDate, topics, selectionScheme;
		int noattmpts,diffLow,diffHigh, corrrectPoints, incorrrectPoints;
		long seed;
		int eid;
		
		Connection con = null;
    	PreparedStatement ps_1 = null;
    	ResultSet rs1 = null;

		System.out.println("Please enter the following:");
			
		System.out.println("-start date (DD-MM-YYYY)");
		startDate = scanString.nextLine();
		
		System.out.println("-end date (DD-MM-YYYY)");
		endDate = scanString.nextLine();

		System.out.println("-number of attempts (-1 for unlimited) defaults to Unlimited");
		while (!(scanInt.hasNextInt()))
		{
			System.out.println("Enter a valid Integer ");
			scanInt.next();
		}
		noattmpts = scanInt.nextInt();
		if (noattmpts < 0)
			noattmpts = -1;

		System.out.println("-topics (:: seperated) WARNING: Case sensitive Please add as mapped to the course");
		topics = scanString.nextLine();

		System.out.println("-difficulty range Lower value");
		while (!(scanInt.hasNextInt()))
		{
			System.out.println("Enter a valid Integer ");
			scanInt.next();
		}
		diffLow = scanInt.nextInt();
		
		System.out.println("-difficulty range Higher value");
		while (!(scanInt.hasNextInt()))
		{
			System.out.println("Enter a valid Integer ");
			scanInt.next();
		}
		
		diffHigh = scanInt.nextInt();

		System.out.println("Enter a random seed value, Default will be used if 0 is entered");
		while (!(scanInt.hasNextLong()))
		{
			System.out.println("Enter a valid Long value");
			scanInt.next();
		}
		seed = scanInt.nextLong();
		if (diffLow >= diffHigh)
		{
			System.out.println("Invalid difficulty range");
			return;
		}
		
		System.out.println("-score selection scheme(LAT - Latest Score, MAX - Maximum Score, AVG - Average Score) Defaults to MAX");
		selectionScheme = scanString.nextLine();
		if (!(selectionScheme.equals("LAT") || selectionScheme.equals("MAX") || selectionScheme.equals("AVG")))
			selectionScheme = "MAX";

		
		System.out.println("-Correct answer points Defaults to positive");
		while (!(scanInt.hasNextInt()))
		{
			System.out.println("Enter a valid Integer ");
			scanInt.next();
		}
		corrrectPoints = scanInt.nextInt();
		if (corrrectPoints < 0)
			corrrectPoints = -1 * corrrectPoints;
		
		System.out.println("-Incorrect answer points");
		while (!(scanInt.hasNextInt()))
		{
			System.out.println("Enter a valid Integer ");
			scanInt.next();
		}
		incorrrectPoints = scanInt.nextInt();
		if (incorrrectPoints < 0)
			incorrrectPoints = -1 * incorrrectPoints;
		
		try
		{
			con = JDBCHelper.getConnection();
			
			ps_1 = null;
			rs1 = null;
						
			System.out.println(startDate);
			System.out.println(d);

			ps_1 = con.prepareStatement("SELECT COUNT(*) FROM COURSE WHERE TOKENNUM = ? AND CSTARTDATE <= TO_DATE(?,'DD-MM-YYYY') AND CENDDATE > TO_DATE(?,'DD-MM-YYYY')");
			ps_1.setString(1, tokenNum);
			ps_1.setString(2, startDate);
			ps_1.setString(3, startDate);			

			rs1 = ps_1.executeQuery();
			
			if (!rs1.next())
			{
				System.out.println("Exercise start date must be greater than or equal to course start date and lesser than course end date ");
			}
			
			else
			{
				ps_1 = null;
				rs1 = null;
				
				ps_1 = con.prepareStatement("SELECT 1*(TO_DATE(?,'DD-MM-YYYY') - TO_DATE(?,'DD-MM-YYYY')) FROM DUAL");
				ps_1.setString(1, startDate);
				ps_1.setString(2, d);
				
				rs1 = ps_1.executeQuery();
				rs1.next();
				
				if (rs1.getInt(1) <= 0)
				{
					System.out.println("enter Start Date greater than current date");
					return;
				}
					
				ps_1 = con.prepareStatement("SELECT COUNT(*) FROM COURSE WHERE TOKENNUM = ? AND CSTARTDATE < TO_DATE(?,'DD-MM-YYYY') AND CENDDATE > TO_DATE(?,'DD-MM-YYYY')");
				ps_1.setString(1, tokenNum);
				ps_1.setString(2, endDate);
				ps_1.setString(3, endDate);			
				rs1 = ps_1.executeQuery();

				if (!rs1.next())
				{
					System.out.println("Exercise end date must be greater than or equal to course start date and lesser than course end date ");
				}

				ps_1 = con.prepareStatement("SELECT 1*(TO_DATE(?,'DD-MM-YYYY') - TO_DATE(?,'DD-MM-YYYY')) FROM DUAL");
				ps_1.setString(1, endDate);
				ps_1.setString(2, d);
				
				rs1 = ps_1.executeQuery();
				rs1.next();
				
				if (rs1.getInt(1) <= 0)
				{
					System.out.println("enter End Date greater than current date");
					return;
				}

				
				else
				{

					if (endDate.equals(startDate))
					{
						System.out.println("Please keep atleast one day for an exercise");
					}
					
					else						
					{
						ps_1 = null;
						rs1 = null;
						
						ps_1 = con.prepareStatement("SELECT 1 * (TO_DATE(?,'DD-MM-YYYY') - TO_DATE(?,'DD-MM-YYYY')) AS DateDiff FROM DUAL");
						ps_1.setString(1, endDate);
						ps_1.setString(2, startDate);			
						rs1 = ps_1.executeQuery();
						rs1.next();
						
						int te = rs1.getInt(1);
						
						if (te < 0)
						{
							System.out.println("Start date greater than end date");
						}
						else
						{	
							ps_1 = null;
							rs1 = null;
						
							ps_1 = con.prepareStatement("SELECT MAX(EID) FROM EXERCISE WHERE TOKENNUM = ?");
							ps_1.setString(1, tokenNum);
							rs1 = ps_1.executeQuery();
							rs1.next();
						
							if (rs1.getInt(1) == 0)
								eid = 1;
							else
							{
								eid = rs1.getInt(1);
								eid++;
							}
						
							int counter = 0;
							String[] topicArray = new String[20];
							
							if (!verifyTopics(topics))
								return;
							
							if (seed == 0)
							{
								ps_1 = null;
								rs1 = null;
								
								ps_1 = con.prepareStatement("INSERT INTO EXERCISE (TOKENNUM,EId,NumRetries,EStartDate,EEndDate, ScoreSelection,PntCorrect,PntInPenalty,EDIFFICULTYLOW,"+
										"EDIFFICULTYHIGH) VALUES (?,?,?,TO_DATE(?,'DD-MM-YYYY'),TO_DATE(?,'DD-MM-YYYY'),?,?,?,?,?)");
								ps_1.setString(1, tokenNum);
								ps_1.setString(2, Integer.toString(eid));
								ps_1.setInt(3, noattmpts);
								ps_1.setString(4, startDate);
								ps_1.setString(5, endDate);
								ps_1.setString(6, selectionScheme);
								ps_1.setInt(7, corrrectPoints);
								ps_1.setInt(8, incorrrectPoints);
								ps_1.setInt(9, diffLow);
								ps_1.setInt(10, diffHigh);

								counter = ps_1.executeUpdate();
								if (counter == 1)
								{
									int counter1 = 0;
									topicArray = topics.split("::");
								
									for (String t : topicArray)
									{
										ps_1 = null;
										rs1 = null;
										
										ps_1 = con.prepareStatement("INSERT INTO TOPICEXERCISEMAP (EID,TOKENNUM,TOPIC) VALUES (?,?,?)");
										ps_1.setString(1, Integer.toString(eid));
										ps_1.setString(2, tokenNum);
										ps_1.setString(3, t);
									
										counter1 = ps_1.executeUpdate();
										if (counter1 != 1)
										{
											System.out.println("Update Failed");
											return;
										}
										
									
									}
									System.out.println("Update Successful");
									return;
								}
								else
								{
									System.out.println("Update Failed");
									return;
								}	
							}
						
							else
							{
								ps_1 = null;
								rs1 = null;
									
								ps_1 = con.prepareStatement("INSERT INTO EXERCISE (TokENNUM,EId,NumRetries,EStartDate,EEndDate, ScoreSelection,PntCorrect, PntInPenalty , EDIFFICULTYLOW ,"+
															"EDIFFICULTYHIGH,RandomSeed) VALUES (?,?,?,TO_DATE(?,'DD-MM-YYYY'),TO_DATE(?,'DD-MM-YYYY'),?,?,?,?,?,?)");
								ps_1.setString(1, tokenNum);
								ps_1.setString(2, Integer.toString(eid));
								ps_1.setInt(3, noattmpts);
								ps_1.setString(4, startDate);
								ps_1.setString(5, endDate);
								ps_1.setString(6, selectionScheme);
								ps_1.setInt(7, corrrectPoints);
								ps_1.setInt(8, incorrrectPoints);
								ps_1.setInt(9, diffLow);
								ps_1.setInt(10, diffHigh);
								ps_1.setLong(11,seed);

								counter = ps_1.executeUpdate();
								if (counter == 1)
								{
									int counter1 = 0;
									topicArray = topics.split("::");
								
									for (String t : topicArray)
									{
										ps_1 = null;
										rs1 = null;
	
										ps_1 = con.prepareStatement("INSERT INTO TOPICEXERCISEMAP (EID,TOKENNUM,TOPIC) VALUES (?,?,?)");
										ps_1.setString(1, Integer.toString(eid));
										ps_1.setString(2, tokenNum);
										ps_1.setString(3, t);
									
										counter1 = ps_1.executeUpdate();
										if (counter1 != 1)
										{
											System.out.println("Update Failed");
											return;
										}
									
									}
											System.out.println("Update Successful");
											return;
								}
								else
								{
									System.out.println("Update Failed");
									return;
								}	
							}
						}
							
					}
					
				}

			}
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally
    	{
    			JDBCHelper.close(rs1);
    			JDBCHelper.close(ps_1);
    			JDBCHelper.close(con);
    	}
		
	}
	
	public static boolean verifyTopics(String topics)
	{
		String[] topicsArray = new String[20];
		
		Connection con = null;
		PreparedStatement ps_1 = null;
		ResultSet rs1 = null;
		
		Set<String> T = new HashSet<String>();
		
		try
		{

			con = JDBCHelper.getConnection();
			
			ps_1 = con.prepareStatement("SELECT Topic  FROM TOPICCOURSEMAP WHERE TOKENNUM = ?");
			ps_1.setString(1, tokenNum);
			
			rs1 = ps_1.executeQuery();
			while (rs1.next())
			{
				T.add(rs1.getString("TOPIC"));
			}
			
			topicsArray = topics.split("::");
			
			for (String t : topicsArray)
			{
				if (t == null)
					continue;
				
				t = t.trim();
				if (T.contains(t))
					return true;
			}
			return false;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
			
		finally
	    {
	    			JDBCHelper.close(rs1);
	    			JDBCHelper.close(ps_1);
	    			JDBCHelper.close(con);
	    }
		return false;
	}
	
	public static void menu4a2() 
	{
		Connection con = null;
    	PreparedStatement ps_1 = null;
    	ResultSet rs1 = null;

		int index = 0;
		Vector<String> v = new Vector<String>();

		try 
		{
			while (true) 
			{
				System.out.println("List of existing homeworks for "+tokenNum);
				
				con = JDBCHelper.getConnection();
				ps_1 = con.prepareStatement("SELECT EId FROM Exercise WHERE TOKENNUM = ?");
				ps_1.setString(1, tokenNum);
				
				rs1 = ps_1.executeQuery();
				index = 0;
				while (rs1.next()) 
				{
					String s = rs1.getString("EId");
					System.out.println(index + ": HW" + s);
					v.addElement(s);
					index++;
				}
				
				System.out.println(index + ": Back");

				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}

				int a = scanIn.nextInt();

				if (a == index)
					return;
				else if (a < index && a >= 0) 
				{
					String eid = v.elementAt(a);
					menu4a21(eid);
				} 
				else
				{
					System.out.println("Invalid choice.");
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	

	}

	public static void menu4a21(String Id) 
	{
		while (true) 
		{
			System.out.println("1. Search and Add question");
			System.out.println("2. Remove question");
			System.out.println("3. Back");

			Scanner scanIn = new Scanner(System.in);
			while (!(scanIn.hasNextInt()))
			{
				System.out.println("Enter a valid Integer ");
				scanIn.next();
			}
			int a = scanIn.nextInt();

			switch (a) 
			{
				case 1: menu4a211(Id);
						break;
				case 2: menu4a212(Id);
						break;
				case 3: return;
				default: System.out.println("Invalid choice, try again.");
						 break;
			}
		}
	}
	
	public static void menu4a211(String Id)
	{
		System.out.println("Enter question number to add");
		Connection con = null;
    	PreparedStatement ps_1 = null;
    	ResultSet rs1 = null;
    	
    	int diffLow = 0, diffHigh = 0, index = 0, choice = 0; 
    	TreeSet<String> currentExercise = null;
    	Vector<String> Questions = null ;
    	String[] topicsArray = null;
  
		try
		{
			con = JDBCHelper.getConnection();
			
			while (true)
			{

				topicsArray = new String[20];
				ps_1 = con.prepareStatement("SELECT EDIFFICULTYLOW, EDIFFICULTYHIGH FROM EXERCISE WHERE EID = ? AND TOKENNUM = ?");
				ps_1.setString(1, Id);
				ps_1.setString(2, tokenNum);
				
				rs1 = ps_1.executeQuery();
				rs1.next();
				diffLow = rs1.getInt("EDIFFICULTYLOW");
				diffHigh = rs1.getInt("EDIFFICULTYHIGH");
				
				ps_1 = null;
				rs1 = null;
				
				ps_1 = con.prepareStatement("SELECT ID FROM FORMEXERCISE WHERE EID = ? AND TOKENNUM = ?");
				ps_1.setString(1, Id);
				ps_1.setString(2, tokenNum);
					
				rs1 = ps_1.executeQuery();
				
				currentExercise =  new TreeSet<String>();
				while(rs1.next())
				{
					currentExercise.add(rs1.getString("ID"));					
				}
				
				
				ps_1 = null;
				rs1 = null;
				
				ps_1 = con.prepareStatement("SELECT TOPIC FROM TopicExerciseMap WHERE EID = ? AND TOKENNUM = ?");
				ps_1.setString(1, Id.trim());
				ps_1.setString(2, tokenNum);

				rs1 = ps_1.executeQuery();
				index = 0;
				
				while(rs1.next())
				{
					topicsArray[index] = rs1.getString("TOPIC");
					index++;
					
				}
				Questions = new Vector<String>();
				choice = 0;

				for (String t : topicsArray)
				{
					if (t == null)
						break;
					
					ps_1 = null;
					rs1 = null;
					
					ps_1 = con.prepareStatement("SELECT QID, TEXT FROM QUESTION WHERE QLEVEL >= ? AND QLEVEL <= ? AND TOPIC = ?");
					ps_1.setInt(1, diffLow);
					ps_1.setInt(2, diffHigh);
					ps_1.setString(3, t);					
					
					rs1 = ps_1.executeQuery();
					while (rs1.next())
					{
						if (!currentExercise.contains(rs1.getString("QID")))
						{
							Questions.add(rs1.getString("QID"));
							System.out.println(choice+"   "+rs1.getString("QID")+"   "+rs1.getString("TEXT"));
							choice++;
						}
					}
					ps_1 = null;
					rs1 = null;
					
					ps_1 = con.prepareStatement("SELECT PQID, PTEXT FROM PQUESTION WHERE PLEVEL >= ? AND PLEVEL <= ? AND PTOPIC = ?");
					ps_1.setInt(1, diffLow);
					ps_1.setInt(2, diffHigh);
					ps_1.setString(3, t);
					
					rs1 = ps_1.executeQuery();
					while (rs1.next())
					{
						if (!currentExercise.contains(rs1.getString("PQID")))
						{
							Questions.add(rs1.getString("PQID"));
							System.out.println(choice+"   "+rs1.getString("PQID")+"   "+rs1.getString("PTEXT"));
							choice++;
						}
					}
					
					
				}

				System.out.println("Press " + choice + " to go Back");

				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}
				int a = scanIn.nextInt();

				if (a == choice) 
				{
					return;
				} 
				else if (a >= 0 && a < choice) 
				{
					ps_1 = null;
					rs1 = null;
					
					ps_1 = con.prepareStatement("INSERT INTO FORMEXERCISE (EID,TOKENNUM,ID) VALUES (?,?,?)");
					ps_1.setString(1, Id.trim());
					ps_1.setString(2, tokenNum);
					ps_1.setString(3, Questions.elementAt(a));
					
					int count = ps_1.executeUpdate();
					if (count == 1)
					{
						System.out.println("Update Successful");
					}
					
					else
					{
						System.out.println("Update failed ");
					}
				}
				else
				{
					System.out.println("Invalid Option");
				}

			}

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	}

	public static void menu4a212(String Id)
	{
		Connection con = null;
    	PreparedStatement ps_1 = null;
    	ResultSet rs1 = null;
    	Vector<String> qid ; 
    	int index = 0;
    	String s = "";

    	try
		{
			con = JDBCHelper.getConnection();
			while (true) 
			{
				System.out.println("For HW"+Id.trim());
				ps_1 = con.prepareStatement("SELECT Id FROM FormExercise WHERE EId = ? AND TOKENNUM = ?");
				
				ps_1.setString(1, Id.trim());
				ps_1.setString(2, tokenNum);
				
				rs1 = ps_1.executeQuery();
				qid = new Vector<String>();
				
				while (rs1.next())
				{
					s = rs1.getString("Id");
					qid.addElement(s);
				}

				Iterator<String> vitr = qid.iterator();
				String temp = "";
				index = 0;
				while (vitr.hasNext())
				{
					temp = vitr.next();
					ps_1 = null;
					rs1 = null;
					
					if (temp.charAt(0) == 'Q')
					{
						ps_1 = con.prepareStatement("SELECT TEXT FROM QUESTION WHERE QID = ?");
						ps_1.setString(1, temp.trim());

						rs1 = ps_1.executeQuery();
						rs1.next();
						
						System.out.println(index + " " + temp + ": " + rs1.getString("TEXT"));
					}
					
					else
					{
						ps_1 = con.prepareStatement("SELECT PTEXT FROM PQUESTION WHERE PQID = ?");
						ps_1.setString(1, temp.trim());

						rs1 = ps_1.executeQuery();
						rs1.next();
						
						System.out.println(index + " " + temp + ": " + rs1.getString("PTEXT"));						
					}
					
					index++;
				}
				System.out.println(index + ": Back");

				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}
				int a = scanIn.nextInt();

				if (a == index) 
				{
					return;
				}
				
				else if (a < index && a >= 0) 
				{
					ps_1 = con.prepareStatement("DELETE FROM FORMEXERCISE WHERE ID = ? AND TOKENNUM = ? AND EID = ?");
					ps_1.setString(1, qid.elementAt(a).trim());
					ps_1.setString(2, tokenNum);
					ps_1.setString(3, Id.trim());
					
					int count = ps_1.executeUpdate();
					if (count == 1)
					{
						System.out.println("Update Successful");
						
					}
					else
					{
						System.out.println("Update failed");
					}
				}

			}

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}

	}

	
	public static void menu4a3()
	{
		Connection con = null;
    	PreparedStatement ps_1 = null;
    	ResultSet rs1 = null;
		int index = 0;
		Vector<String> hwrk = new Vector<String>();
		
		try 
		{	
			System.out.println("Select homework to Edit");
			con = JDBCHelper.getConnection();
			while (true) 
			{
				ps_1 = con.prepareStatement("SELECT EID FROM EXERCISE WHERE TOKENNUM = ?");
				ps_1.setString(1, tokenNum);
				
				
				rs1 = ps_1.executeQuery();
				index = 0;
				while (rs1.next())
				{
					System.out.println(index + " : " + "HW"+ rs1.getString("EId"));
					hwrk.addElement(rs1.getString("EId"));
					index++;
				}
				
				System.out.println(index + ": Back");

				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}
				int a = scanIn.nextInt();

				if (a == index)
					return;
				else if (a < index && a >= 0) 
				{
					String temp = hwrk.elementAt(a);
					menu4a31(temp);
				} 
				else 
				{
					System.out.println("Invalid choice.");
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}

	}

	public static void menu4a31(String Id)
	{
		Connection con = null;
    	PreparedStatement ps_1 = null;
    	ResultSet rs1 = null;
    	String s = "" ;
		int count = 0,i = 0;
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		String d = sd.format(new Date());
		char[] rev = null;
		
    	try
		{
			con = JDBCHelper.getConnection();

    		while(true)
			{	
				System.out.println("Edit HW"+Id);
				System.out.println("Choose what to update:");
				System.out.println("1. Start date");
				System.out.println("2. End date");
				System.out.println("3. Number of attempts");
				System.out.println("4. Score selection");
				System.out.println("5. Correct answer points");
				System.out.println("6. Incorrect answer points");
				System.out.println("7. Back");


				Scanner scanString = new Scanner(System.in);

				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}

				int a = scanIn.nextInt();
			

				switch (a) 
				{
					case 1: ps_1 = con.prepareStatement("SELECT EEndDate,ESTARTDATE FROM EXERCISE WHERE TOKENNUM = ?  AND EID = ?");
							ps_1.setString(1, tokenNum);
							ps_1.setString(2, Id.trim());
							
							rs1 = ps_1.executeQuery();
							rs1.next();
					
							System.out.println("Enter a start date (DD-MM-YYYY) ------- Current Start Date :"+rs1.getString("ESTARTDATE"));
							s = scanString.nextLine();
							String eEndDate = "";
							
							eEndDate = rs1.getString("EEndDate");
							rev = eEndDate.toCharArray();
							eEndDate = String.copyValueOf(rev,8,2) + "-" + String.copyValueOf(rev,5,2) + "-" + String.copyValueOf(rev,0,4); 								
							System.out.println(eEndDate);
							ps_1 = null;
							rs1 = null;
							
							ps_1 = con.prepareStatement("UPDATE EXERCISE SET EStartDate = TO_DATE(?,'DD-MM-YYYY') WHERE EID = ? AND TOKENNUM = ? AND" +
									" TO_DATE(?,'DD-MM-YYYY') < TO_DATE(?,'DD-MM-YYYY') AND TO_DATE(?,'DD-MM-YYYY') > TO_DATE(?,'DD-MM-YYYY') ");
							ps_1.setString(1, s);
							ps_1.setString(2, Id.trim());
							ps_1.setString(3, tokenNum);
							ps_1.setString(4, s);
							ps_1.setString(5, eEndDate);
							ps_1.setString(6, s);
							ps_1.setString(7, d);
							
							count = ps_1.executeUpdate();
							if (count == 1)
							{
								System.out.println("Start Date successfully changed");
							}	
							else
							{
								System.out.println("Start Date update failed, Invalid Date passed");
							}	
							break;
					
					
					
					case 2: ps_1 = con.prepareStatement("SELECT CEndDate FROM COURSE WHERE TOKENNUM = ?");
							ps_1.setString(1, tokenNum);
							
							String cEndDate = "",eStartDate = "";
							
							rs1 = ps_1.executeQuery();
							rs1.next();
					
							cEndDate = rs1.getString("CEndDate");
							rev = cEndDate.toCharArray();
							cEndDate = String.copyValueOf(rev,8,2) + "-" + String.copyValueOf(rev,5,2) + "-" + String.copyValueOf(rev,0,4); 								
												
							ps_1 = null;
							rs1 = null;
					
							ps_1 = con.prepareStatement("SELECT EStartDate,EEndDate FROM EXERCISE WHERE TOKENNUM = ?  AND EID = ?");
							ps_1.setString(1, tokenNum);
							ps_1.setString(2, Id);
					
							rs1 = ps_1.executeQuery();
							rs1.next();
							
							System.out.println("Enter an end date (DD-MM-YYYY) ----------- Current End Date :"+rs1.getString("EEndDate"));
							s = scanString.nextLine();
			
							eStartDate = rs1.getString("EStartDate");
							rev = eStartDate.toCharArray();
							eStartDate = String.copyValueOf(rev,0,10); 								
							eStartDate = String.copyValueOf(rev,8,2) + "-" + String.copyValueOf(rev,5,2) + "-" + String.copyValueOf(rev,0,4); 								

							ps_1 = null;
							rs1 = null;
					
							ps_1 = con.prepareStatement("UPDATE EXERCISE SET EEndDate = TO_DATE(?,'DD-MM-YYYY') WHERE EID = ? AND TOKENNUM = ? AND " +
									" TO_DATE(?,'DD-MM-YYYY') > TO_DATE(?,'DD-MM-YYYY') AND TO_DATE(?,'DD-MM-YYYY') > TO_DATE(?,'DD-MM-YYYY')");
							ps_1.setString(1, s);
							ps_1.setString(2, Id);
							ps_1.setString(3, tokenNum);
							ps_1.setString(4, s);
							ps_1.setString(5, eStartDate);
							ps_1.setString(6, s);
							ps_1.setString(7, d);
					
							count = ps_1.executeUpdate();
							if (count == 1)
							{
								System.out.println("End Date successfully changed");
							}	
							else
							{
								System.out.println("End Date update failed, Invalid Date passed");
							}	
							break;
							
					case 3: System.out.println("Enter no of attempts ");
							while (!(scanIn.hasNextInt()))
							{
								System.out.println("Enter a valid Integer ");
								scanIn.next();
							}
							i = scanIn.nextInt();
							
							if (i < 0 || i == 0)
							{
								i = -1;
							}
							
							ps_1 = con.prepareStatement("UPDATE Exercise SET NumRetries = ? WHERE EID = ? AND TOKENNUM = ?");
							ps_1.setInt(1, i);				
							ps_1.setString(2, Id);				
							ps_1.setString(3, tokenNum);				
							count = ps_1.executeUpdate();	
							if (count == 1)
							{
								System.out.println("Number of retries successfully changed");
							}	
							else
							{
								System.out.println("Number of retries update failed");
							}	
							break;
				
				
					case 4:System.out.println("Enter score selection ");
						   s = scanString.nextLine();
						   if (s.equals("AVG") || s.equals("MAX") || s.equals("LAT"))
						   {	   
							   ps_1 = con.prepareStatement("UPDATE Exercise SET ScoreSelection = ? WHERE EID = ? AND TOKENNUM = ?");
							   ps_1.setString(1, s);				
							   ps_1.setString(2, Id);				
							   ps_1.setString(3, tokenNum);		
							   
							   count = ps_1.executeUpdate();	
							   if (count == 1)
							   {
								   System.out.println("Score selection method successfully changed");
							   }	
							   else
							   {
								   System.out.println("Score selection method update failed");
							   }	
						   }
						   else
						   {
							   System.out.println("Invalid value for Score selection method ");
						   }
						   break;
						   
					case 5: System.out.println("Enter correct answer pts ");
							while (!(scanIn.hasNextInt()))
							{
								System.out.println("Enter a valid Integer ");
								scanIn.next();
							}
							i = scanIn.nextInt();
							if (i < 0)
								i = -1 * i;
						
							ps_1 = con.prepareStatement("UPDATE Exercise SET PntCorrect = ? WHERE EID = ? AND TOKENNUM = ?");
							ps_1.setInt(1, i);				
							ps_1.setString(2, Id);				
							ps_1.setString(3, tokenNum);				
							count = ps_1.executeUpdate();	
							if (count == 1)
							{	
								System.out.println("Points for correct answer successfully changed");
							}	
							else
							{
								System.out.println("Points for correct answer update failed");
							}	
							break;

					case 6:	System.out.println("Enter incorrect answer pts ");
							while (!(scanIn.hasNextInt()))
							{
								System.out.println("Enter a valid Integer ");
								scanIn.next();
							}
							i = scanIn.nextInt();
							if (i < 0)
								i = -1 * i;
						
							
							ps_1 = con.prepareStatement("UPDATE Exercise SET PntInPenalty = ? WHERE EID = ? AND TOKENNUM = ?");
							ps_1.setInt(1, i);				
							ps_1.setString(2, Id);				
							ps_1.setString(3, tokenNum);				
							count = ps_1.executeUpdate();	
							if (count == 1)
							{	
								System.out.println("Points for incorrect answer successfully changed");
							}	
							else
							{
								System.out.println("Points for incorrect answer update failed");
							}	
							break;

					case 7: return;
					
					default: System.out.println("Invalid choice. ");
							 break;
				}


			}//while end brace
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	}

	public static void menu4a4()
	{
		Connection con = null;
	    PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;

		int index = 0;
		Vector<String> v = new Vector<String>();

		try 
		{
			con = JDBCHelper.getConnection();

			while (true) 
			{
				System.out.println("List of existing homeworks for "+tokenNum);
				index = 0;
				ps_1 = con.prepareStatement("SELECT EId FROM Exercise WHERE TOKENNUM = ?");
				ps_1.setString(1, tokenNum);
					
				rs1 = ps_1.executeQuery();

				while (rs1.next()) 
				{
					String s = rs1.getString("EId");
					System.out.println(index + ": HW" + s);
					v.addElement(s);
					index++;
				}
					
				System.out.println(index + ": Back");
				
				Scanner scanIn = new Scanner(System.in);
				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}					
				int a = scanIn.nextInt();
				
				if (a == index)
						return;
				else if (a >= 0 && a < index) 
				{
					String eid = v.elementAt(a);
					menu4a41(eid);
				} 
				else
				{
					System.out.println("Invalid choice.");
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
		

	}

	public static void menu4a41(String Id)
	{
		Connection con = null;
	    PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;
	    String s = "";
	    long l = 0;
	    int a = 0;
	    
		try
		{
			con = JDBCHelper.getConnection();
			ps_1 = con.prepareStatement("SELECT TOKENNUM,EId,RandomSeed,NumRetries,EStartDate,EEndDate,ScoreSelection,PntCorrect,PntInPenalty,EDIFFICULTYLOW,EDIFFICULTYHIGH "+
					"FROM Exercise WHERE EId = ? AND TOKENNUM = ?");
			ps_1.setString(1, Id);
			ps_1.setString(2, tokenNum);
			
			rs1 = ps_1.executeQuery();
			
		    while (rs1.next()) 
		    {
				 s = rs1.getString("TOKENNUM");
				 System.out.println("Token Number :"+s);

				 s = rs1.getString("EId");
				 System.out.println("Exercise Id HW"+s);

				 l = rs1.getLong("RandomSeed");
				 System.out.println("Random Seed "+ l);

				 a = rs1.getInt("NumRetries");
				 if (a == -1)
				 {
					 System.out.println("Number of retries : Unlimited");
				 }
				 else
				 {
					 System.out.println("Number of retries :"+a);
				 }

				 s = rs1.getString("EStartDate");
				 System.out.println("Exercise start date :"+s);

				 s = rs1.getString("EEndDate");
				 System.out.println("Exercise End date :"+s);

				 s = rs1.getString("ScoreSelection");
				 if (s.equals("AVG"))
				 {
					 System.out.println("Score Selection : Average Score");
				 }
				 else if (s.equals("MAX"))
				 {
					 System.out.println("Score Selection : Maximum Score");
				 }
				 else
				 {
					 System.out.println("Score Selection : Latest Attempt");					 
				 }

				 a = rs1.getInt("PntCorrect");
				 System.out.println("Points for correct answer : "+a);					 

				 a = rs1.getInt("PntInPenalty");
				 System.out.println("Points for Incorrect answer : -"+a);					 

				 a = rs1.getInt("EDIFFICULTYLOW");
				 System.out.println("Low range of difficulty : "+a);
				 
				 a = rs1.getInt("EDIFFICULTYHIGH");
				 System.out.println("High range of difficulty : "+a);					 

		    }
		    
		    System.out.println("Topics are:");
		    
		    ps_1 = null;
		    rs1 = null;
		    
		    ps_1 = con.prepareStatement("SELECT Topic from TopicExerciseMap WHERE EId = ? AND TOKENNUM = ?");
		    ps_1.setString(1, Id.trim());
		    ps_1.setString(2, tokenNum.trim());
		    		    
		    rs1 = ps_1.executeQuery();

		    while(rs1.next())
		    {
		    	System.out.println(rs1.getString("Topic"));
		    }
		    System.out.println("Enter a character to return");
		    Scanner scanIn = new Scanner(System.in);
		    scanIn.nextLine();
		    return;
		    
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	}

	public static void menu4a5()
	{
		Connection con = null;
	    PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;
	    boolean flag = false;	
	    try
		{
			con = JDBCHelper.getConnection();

			ps_1 = con.prepareStatement("SELECT MSG FROM ALERTS WHERE Id = ? AND MSGREAD = ?");
			ps_1.setString(1, profId);
			ps_1.setString(2, "N");
			
			rs1 = ps_1.executeQuery();
			
			System.out.println("ALERTS :");
		    while (rs1.next()) 
		    {
		    	flag = true;
		    	System.out.println(rs1.getString("MSG"));    
		    }
	    	Scanner scanIn = new Scanner(System.in);
		    
		    if (flag)
		    {
		    	System.out.println("Press Y to acknowledge having read the message");
		    	String temp = scanIn.nextLine();
		    	while (!temp.equals("Y"))
		    	{
		    		System.out.println("Press Y to acknowledge having read the message");
		    		temp = scanIn.nextLine();
		    	}
		    	
		    	ps_1 = con.prepareStatement("UPDATE ALERTS SET MSGREAD = ? WHERE ID = ?");
		    	ps_1.setString(1, "Y");
		    	ps_1.setString(2, profId);
		    	
		    	ps_1.executeUpdate();
		    	
		    	
		    	return;
		    }
		    
		    else
		    {
		    	System.out.println("No alerts");
		    	System.out.println("Enter a character to return");
		    	scanIn.nextLine();
		    	return;
		    }
		    
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	}

	public static void menu4a6()
	{
		//Professor reporting
		Connection con = null;
	    
		PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;
	    
		PreparedStatement ps_2 = null;
	    ResultSet rs2 = null;

	    int a = 0,count = 0;
	    try
		{
			con = JDBCHelper.getConnection();
			while(true)
			{
				System.gc();
				System.out.println("Reporting Options: ");
				System.out.println("1. Find students who did not take homework 1");
				System.out.println("2. Find students who scored the maximum score on the first attempt for homework 1");
				System.out.println("3. Find students who scored the maximum score on the first attempt for each homework");
				System.out.println("4. For each student, show total score for each homework and average score across all homeworks");
				System.out.println("5. For each homework, show average number of attempts");
				System.out.println("6. To return");

				Scanner scanString = new Scanner(System.in);
				Scanner scanIn = new Scanner(System.in);

				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}
				a = scanIn.nextInt();

				switch(a)
				{
					case 1: ps_1 = con.prepareStatement("SELECT SNAME,ID FROM ENROLLMENT WHERE TOKENNUM = ? AND ID NOT IN "+
									" (SELECT DISTINCT STUD_ID FROM VIEW_SCORES WHERE TOKENNUM = ? AND EID = ?) ");
						   	ps_1.setString(1, tokenNum);
						   	ps_1.setString(2, tokenNum);
						   	ps_1.setString(3, "1");
						    
						    rs1 = ps_1.executeQuery();
						    System.out.println("List of students who did not take homework 1 for :"+tokenNum);
						    count = 0;
						    while (rs1.next())
						    {
						    	count++;
						    	System.out.println(count +". "+ rs1.getString("SNAME"));						    	
						    }
						    
						    if (count == 0)
						    {
						    	System.out.println("All students have taken the howework (if any)");
						    }
						    System.out.println("Enter any character to return");
						    scanString.nextLine();
							break;
							
					case 2: ps_1 = con.prepareStatement("SELECT E.SNAME,E.ID FROM ENROLLMENT E WHERE E.ID IN " +
															"(SELECT STUD_ID FROM VIEW_SCORES WHERE EID = ? AND ATTEMPT_NO = ? AND TOKENNUM = ? AND "+ 
																"SCORES = (SELECT MAX(SCORES) FROM VIEW_SCORES WHERE EID = ? AND ATTEMPT_NO = ? AND TOKENNUM = ?))");
							ps_1.setString(1, "1");
							ps_1.setInt(2, 1);
							ps_1.setString(3, tokenNum);
							ps_1.setString(4, "1");
							ps_1.setInt(5, 1);
							ps_1.setString(6, tokenNum);
							rs1 = ps_1.executeQuery();
							
							System.out.println("List of students :");
							while (rs1.next())
							{
								System.out.println(rs1.getString("ID") + " " +  rs1.getString("SNAME"));
							}							
						    System.out.println("Enter any character to return");
						    scanString.nextLine();
							break;

					case 3: ps_1 = con.prepareStatement("SELECT TEMP.EID,TEMP.STUD_ID,E.SNAME FROM ENROLLMENT E JOIN " +
										"(SELECT EID,STUD_ID,MAX(SCORES) AS MX FROM VIEW_SCORES WHERE TOKENNUM = ? GROUP BY EID,STUD_ID) TEMP ON E.ID = TEMP.STUD_ID");
							ps_1.setString(1, tokenNum);
							
							rs1 = ps_1.executeQuery();
							while (rs1.next())
							{
								System.out.println(rs1.getString("STUD_ID") + "  " + rs1.getString("SNAME") + " HW" + rs1.getString("EID"));
							}
						    System.out.println("Enter any character to return");
						    scanString.nextLine();
							break;
							
					case 4:	ps_1 = con.prepareStatement(" SELECT E.SNAME,F1.EID,F1.STUD_ID,F1.SCORES,F2.SCR FROM FSCORE F1 JOIN " +
										"(SELECT AVG(SCORES) AS SCR,STUD_ID FROM FSCORE WHERE TOKENNUM = ? GROUP BY STUD_ID) F2 ON F2.STUD_ID = F1.STUD_ID AND TOKENNUM = ? JOIN " +
											" ENROLLMENT E ON E.ID = F1.STUD_ID");
							ps_1.setString(1, tokenNum);
							ps_1.setString(2, tokenNum);
							
							rs1 = ps_1.executeQuery();
							while (rs1.next())
							{
								System.out.println(rs1.getString("STUD_ID") + "  " + rs1.getString("SNAME") + " HW" + rs1.getString("EID") + " Score:" + rs1.getString("SCORES")   + " AVG" + rs1.getFloat("SCR"));								
							}

					
					
							System.out.println("Enter any character to return");
							scanString.nextLine();
							break;

					case 5: ps_1 = con.prepareStatement("SELECT AVG(TEMP.CNT),TEMP.EID FROM " + 
									"(SELECT MAX(ATTEMPT_NO) AS CNT,EID,STUD_ID FROM VIEW_SCORES WHERE TOKENNUM = ? GROUP BY EID,STUD_ID) TEMP GROUP BY TEMP.EID");
							ps_1.setString(1, tokenNum);
							
							rs1 = ps_1.executeQuery();
							while (rs1.next())
							{
								System.out.println("HW" + rs1.getString("EID") + " AVG - "+ rs1.getFloat(1));
							}
							System.out.println("Enter any character to return");
							scanString.nextLine();
							break;

					case 6: return;

					default: System.out.println("Invalid Choice");
							 break;
							 
				}
			}
		    
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(rs2);
			JDBCHelper.close(ps_2);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	}
	
    public static void menu3A3()
    {
		Connection con = null;
	 
		PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;

		PreparedStatement ps_2 = null;
	    ResultSet rs2 = null;

	    Vector<String> value = new Vector<String>();
	    
		int count = 0,max = 0, index = 0;
		
	    String tempString = "";

		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		String d = sd.format(new Date());

	    try 
	    {
			con = JDBCHelper.getConnection();
	    	System.out.println("View Past submissions");
	    	while(true)
	    	{
	    		System.out.println("Past Due Date");
	    		ps_1 = con.prepareStatement("SELECT EID FROM EXERCISE WHERE TOKENNUM = ? AND EENDDATE < TO_DATE(?,'DD-MM-YYYY')");
	    		ps_1.setString(1, tokenNum);
	    		ps_1.setString(2,d);
	    		
	    		rs1 = ps_1.executeQuery();
	    		
	    		count = 1;
				index = 0;

	    		while (rs1.next())
	    		{
	    			ps_2 = con.prepareStatement("SELECT MAX(ATTEMPT_NO) FROM VIEW_SCORES WHERE STUD_ID = ? AND TOKENNUM = ? AND EID = ?");
	    			ps_2.setString(1, studentId);
	    			ps_2.setString(2,tokenNum);
	    			ps_2.setString(3, rs1.getString("EID"));
	    			
	    			rs2 = ps_2.executeQuery();

	    			while(rs2.next())
	    			{
	    				
	    				count = 1;
	    				max = rs2.getInt(1);
	    				while (count <= max)
	    				{
	    					System.out.println(index + ":  " + "HW"+rs1.getString("EID") + " Attempt :" + count);
	    					tempString = rs1.getString("EID") + ":" + index;
	    					value.add(tempString);
	    					count++;
	    					index++;
	    				}
	    			}
	    		}

	    		
	    		System.out.println("Within Due Date");
	    		ps_1 = con.prepareStatement("SELECT EID FROM EXERCISE WHERE TOKENNUM = ? AND EENDDATE > TO_DATE(?,'DD-MM-YYYY')");
	    		ps_1.setString(1, tokenNum);
	    		ps_1.setString(2,d);
	    		
	    		rs1 = ps_1.executeQuery();
	    		
	    		count = 1;
				index = 0;

	    		while (rs1.next())
	    		{
	    			ps_2 = con.prepareStatement("SELECT MAX(ATTEMPT_NO) FROM VIEW_SCORES WHERE STUD_ID = ? AND TOKENNUM = ? AND EID = ?");
	    			ps_2.setString(1, studentId);
	    			ps_2.setString(2,tokenNum);
	    			ps_2.setString(3, rs1.getString("EID"));
	    			
	    			rs2 = ps_2.executeQuery();

	    			while(rs2.next())
	    			{
	    				
	    				count = 1;
	    				max = rs2.getInt(1);
	    				while (count <= max)
	    				{
	    					System.out.println(index + ":  " + "HW"+rs1.getString("EID") + " Attempt :" + count);
	    					tempString = rs1.getString("EID") + ":" + count;
	    					value.add(tempString);
	    					count++;
	    					index++;
	    				}
	    			}
	    		}
	    		
	    		System.out.println("Enter "+ index + " to return");
	    		
	    		Scanner scanIn = new Scanner(System.in);
	    		while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}

	    		int choice = scanIn.nextInt();

	    		if(choice == index)
	    		{
	    			return;
	    		}

	    		else if (choice >= 0 && choice < index)
	    		{
	    			menu3A3Attempts(value.elementAt(choice));
	    		}
	    		else
	    		{
	    	        System.out.println("Invalid Choice");	    		
	    	    }
	    	}
        }


		catch (Exception e)
		{
				e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}

    }
    
    public static void menu3A3Attempts(String tok)
    {
		Connection con = null;
		PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;

	    PreparedStatement ps_2 = null;
	    ResultSet rs2 = null;
	    
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		String d = sd.format(new Date());

	    String[] stringSplit = tok.split(":");
	    
	    boolean flag = false;
	    String eEndDate = "";
	    
	    try 
	    {
	    	con = JDBCHelper.getConnection();
	    	Scanner scanString = new Scanner(System.in);
	    	
	    	ps_1 = con.prepareStatement("SELECT eENDDATE FROM EXERCISE WHERE EID = ? AND TOKENNUM = ?");
	    	ps_1.setString(1, stringSplit[0]);
	    	ps_1.setString(2, tokenNum);
	    	
	    	rs1 = ps_1.executeQuery();
	    	rs1.next();
	    	eEndDate = rs1.getString("EENDDATE");
	    	eEndDate = eEndDate.substring(8,10) + "-" + eEndDate.substring(5,7) + "-" + eEndDate.substring(0,4);
	    	
	    	ps_1 = con.prepareStatement("SELECT 1*(TO_DATE(?,'DD-MM-YYYY') - TO_DATE(?,'DD-MM-YYYY')) FROM DUAL");
	    	ps_1.setString(1, eEndDate);
	    	ps_1.setString(2, d);
	    	
	    	rs1 = ps_1.executeQuery();
	    	rs1.next();
	    	if (rs1.getInt(1) > 0)
	    	{
	    		flag = false;
	    	}
	    	else
	    	{
	    		flag = true;
	    	}
	    	
	    	

	    	ps_1 = con.prepareStatement("SELECT Qid,Question,Optiontext1,Optiontext2,Optiontext3,Optiontext4,Selected_option,Expltn,CORRECTVALUE,ParamValue " +
	    			"FROM SUBMISSION WHERE EID = ? AND TOKENNUM = ? AND SID = ? AND ATTEMPT = ?"); 
	    	ps_1.setString(1, stringSplit[0]);
	    	ps_1.setString(2, tokenNum);
	    	ps_1.setString(3, studentId);
	    	ps_1.setString(4, stringSplit[1]);
	    	
	    	rs1 = ps_1.executeQuery();
	    	
	    	while (rs1.next())
	    	{
	    		System.out.println(rs1.getString("Question"));
	    		if (rs1.getString("QID").charAt(0) == 'P')
	    		{
	    			System.out.println("Param Value "+rs1.getString("ParamValue"));
	    		}
	    		System.out.println("1)"+rs1.getString("Optiontext1"));
	    		System.out.println("2)"+rs1.getString("Optiontext2"));
	    		System.out.println("3)"+rs1.getString("Optiontext3"));
	    		System.out.println("4)"+rs1.getString("Optiontext4"));
	    		System.out.println("Option Selected :"+rs1.getString("Selected_option"));
	    		if (rs1.getString("Expltn") != null)
	    		{
		    		System.out.println("Your explaination :"+rs1.getString("Expltn"));
	    		}
	    		
	    		if (rs1.getString("Selected_option").equals(rs1.getString("CORRECTVALUE")))
	    		{
	    			System.out.println("Correct : YES");
	    		}
	    		else
	    		{
	    			System.out.println("Correct : NO");
	    			
	    		}
	    		
	    		String temp = "";
	    		int par = Integer.parseInt(rs1.getString("Selected_option"));
	    		switch (par)
	    		{
	    			case 1 : temp = rs1.getString("Optiontext1");
	    			         break;
	    				
	    			case 2 : temp = rs1.getString("Optiontext2");
			         		 break;
			         		 
	    			case 3 : temp = rs1.getString("Optiontext3");
			         		 break;

	    			default: temp = rs1.getString("Optiontext4");
	    					 break;
	    		
	    		
	    		}
	    		
	    		if (rs1.getString("QID").charAt(0) == 'P')
	    		{
					ps_2 = con.prepareStatement("SELECT POptionExpln FROM POptions WHERE PID = ? AND POptiontext = ? AND PVALUE = ?");
					ps_2.setString(1, rs1.getString("QID"));	
					ps_2.setString(2, temp);	
					ps_2.setString(3, rs1.getString("ParamValue"));	

					rs2 = ps_2.executeQuery();
					rs2.next();					
					
					if (flag)
	    			{
	    				if (!(rs1.getString("Selected_option").equals(rs1.getString("CORRECTVALUE"))))
	    				{
	    					System.out.println("Expln :"+rs2.getString("POptionExpln"));
	    				}
	    			}
	    			else
	    			{
						ps_2 = con.prepareStatement("SELECT DISTINCT DXPLN FROM PQuestion WHERE PQID = ?");
						ps_2.setString(1, rs1.getString("QID"));	

						rs2 = ps_2.executeQuery();
						rs2.next();					

	    				System.out.println("D Expln"+rs2.getString("DXPLN"));
	    			}
	    		}
	    		else
	    		{

	    			if (flag)
	    			{
	    				if (rs1.getString("Selected_option").equals(rs1.getString("CORRECTVALUE")))
	    				{
	    					ps_2 = con.prepareStatement("SELECT OptionExpln FROM Options WHERE QID = ? AND Optiontext = ?");
	    					ps_2.setString(1, rs1.getString("QID"));	
	    					ps_2.setString(2, temp);	

	    					rs2 = ps_2.executeQuery();
	    					rs2.next();					
		    				if (!(rs1.getString("Selected_option").equals(rs1.getString("CORRECTVALUE"))))
		    				{
		    					System.out.println("Expln :"+rs2.getString("OptionExpln"));
		    				}
	    				}
	    			}
	    			else
	    			{
    					ps_2 = con.prepareStatement("SELECT DXPLN FROM QUESTION WHERE QID = ?");
    					ps_2.setString(1, rs1.getString("QID"));	
    					
    					rs2 = ps_2.executeQuery();
    					rs2.next();					
	    				if (!(rs1.getString("Selected_option").equals(rs1.getString("CORRECTVALUE"))))
	    				{
	    					System.out.println("DExpln :"+rs2.getString("DXPLN"));
	    				}	
	    			}	
	    		}
		    	System.out.println("Hit enter to go to next");
		    	scanString.nextLine();

	    	}
	    	System.out.println("End of submission, hit return to go back");
	    	scanString.nextLine();
	    	
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {	
	    	JDBCHelper.close(rs1);
	    	JDBCHelper.close(rs2);
	    	JDBCHelper.close(ps_2);
	    	JDBCHelper.close(ps_1);
	    	JDBCHelper.close(con);
	    }

   }

   public static void menu3A4()
   {
		Connection con = null;
	    PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;

	    PreparedStatement ps_2 = null;
	    ResultSet rs2 = null;
	 
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		String d = sd.format(new Date());
		
		boolean flag = false;
		Vector<String> value  = new Vector<String>();
	    String tempString = "";
	    String msg = "";
		try
		{
			con = JDBCHelper.getConnection();
			
			ps_1 = con.prepareStatement("SELECT Eid FROM EXERCISE WHERE (EENDDATE - TO_DATE(?,'DD-MM-YYYY')) = 1");
			ps_1.setString(1, d);
			
			rs1 = ps_1.executeQuery();
			while (rs1.next())
			{
				tempString = rs1.getString("EID");
				ps_2 = con.prepareStatement("SELECT MAX(ATTEMPT_NO) FROM VIEW_SCORES WHERE TOKENNUM = ? AND EID = ? AND STUD_ID = ?");
				ps_2.setString(1, tokenNum);
				ps_2.setString(2, tempString);
				ps_2.setString(3, studentId);
				
				rs2 = ps_2.executeQuery();
				rs2.next();
				if (rs2.getInt(1) == 0)
				{
					value.add(tempString.trim());
				}
			}
			
			
			Iterator<String> itr = value.iterator();
			while (itr.hasNext())
			{
				
				ps_1 = null;
				rs1 = null;
				String hwrk = itr.next();
				
				ps_1 = con.prepareStatement("SELECT HWRKNO FROM ALERTS WHERE ID = ? AND TOKENNUM = ? AND MSGREAD = ?");
				ps_1.setString(1, studentId);
				ps_1.setString(2, tokenNum);
				ps_1.setString(3, "Y");
				rs1 = ps_1.executeQuery();
				
				Set<String> k = new TreeSet<String>();
				while(rs1.next())
				{
					k.add(rs1.getString(1).trim());
				}
				
				if (k.add(hwrk))
				{
					msg = "HW "+ hwrk + " has not yet been attempted ";
					ps_1 = con.prepareStatement("INSERT INTO ALERTS (Id,MSG,HWRKNO,TOKENNUM) VALUES (?,?,?,?)");
					ps_1.setString(1, studentId);
					ps_1.setString(2, msg);
					ps_1.setString(3, hwrk);
					ps_1.setString(4, tokenNum);

					int count = ps_1.executeUpdate();
					if (count != 1)
					{
						System.out.println("Error in alerts");
					}	
				}
			}
			ps_1 = null;
			rs1 = null;
			
			ps_1 = con.prepareStatement("SELECT MSG FROM ALERTS WHERE Id = ? AND MSGREAD = ?");
			ps_1.setString(1, studentId);
			ps_1.setString(2, "N");
			
			rs1 = ps_1.executeQuery();
			
			System.out.println("ALERTS :");
			Scanner scanIn = new Scanner(System.in);

			while (rs1.next()) 
		    {
				flag = true;
		    	System.out.println(rs1.getString("MSG"));    
		    }
		    
			if (flag)
			{
				System.out.println("Enter Y to acknowledge having read the message");
				
				tempString = scanIn.nextLine();
				while (!tempString.equals("Y"))
				{
					System.out.println("Press Y to acknowledge having read the message");
					tempString = scanIn.nextLine();
				}

				ps_1 = con.prepareStatement("UPDATE ALERTS SET MSGREAD = ? WHERE ID = ? AND TOKENNUM = ?");
		    	ps_1.setString(1, "Y");
		    	ps_1.setString(2, studentId);
		    	ps_1.setString(3, tokenNum);
		    	
		    	int tcount = ps_1.executeUpdate();
		    	if (tcount != 1)
		    	{
					System.out.println("Error in alerts");
		    	}
			}
			
			else
			{
				System.out.println("No messages to display");
				System.out.println("Enter any character to return back");
				scanIn.nextLine();
				return;
			}
		    
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}

	   
   }
   
   public static void menu52()

   {//TA TRIGGER IS PENDING !!!!!!!
	   Connection con = null;
	   PreparedStatement ps_1 = null;
	   ResultSet rs1 = null;
	   Vector<String> value = new Vector<String>();
	   int count = 0; 
	   try 
	   {
		   con = JDBCHelper.getConnection();
		   ps_1 = con.prepareStatement("SELECT TOKENNUM FROM TASSISTANT WHERE ID = ?");
		   ps_1.setString(1, studentId);
		   
		   rs1 = ps_1.executeQuery();
		   while (rs1.next())
		   {
			   value.add(rs1.getString("TOKENNUM"));
		   }
		   
		   Iterator<String> itr = value.iterator();
		   while (itr.hasNext())
		   {
			   
			   ps_1 = null;
			   rs1 = null;
			   
			   ps_1 = con.prepareStatement("SELECT COURSEID, COURSENAME FROM COURSE WHERE TOKENNUM = ?");
			   ps_1.setString(1, itr.next());
			   
			   rs1 = ps_1.executeQuery();
			   rs1.next();
			   System.out.println(count + " " + "Course Id: " + rs1.getString("COURSEID") + "Course Name: " + rs1.getString("COURSENAME")); 
			   count++;
			   
		   }
		   System.out.println("Enter "+ count +" to return");
		   Scanner scanIn = new Scanner(System.in);
			
		   while (!(scanIn.hasNextInt()))
		   {
			  System.out.println("Enter a valid Integer ");
			  scanIn.next();
		   }

		   int a = scanIn.nextInt();
		   if (a == count)
				return;
		   else if (a < count && a >= 0) 
		   {
			   menu5A(value.elementAt(a));
		   } 
		   else 
		   {
				System.out.println("Invalid choice.");
		   }
	   }
		catch (Exception e)
		{
				e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}		
   }

   public static void menu5A(String token)
   {
	   while (true)
	   {
		   System.out.println("enter 1 to View Homework");
		   System.out.println("enter 2 to View Reports");
		   System.out.println("enter 3 to go Back");

		   Scanner scanIn = new Scanner(System.in);
		   while (!(scanIn.hasNextInt()))
		   {
			  System.out.println("Enter a valid Integer ");
			  scanIn.next();
		   }

		   int choice = scanIn.nextInt();

		   switch(choice)
		   {
		   		case 1: Menu5A1(token); 
		   				break;

		   		case 2: Menu5A2(token); 
		   				break;

		   		case 3: return;
		   		
		   		default: System.out.println("Invalid Input");
		   				 break;

		  }

	   }
   }

   public static void Menu5A1(String token)
   {
	   Connection con = null;
	   PreparedStatement ps_1 = null;
	   ResultSet rs1 = null;
	   
	   int count = 0,index = 0;
	   
	   try 
	   {
		   con = JDBCHelper.getConnection();
		   System.out.println("View Homework");
		   ps_1 = con.prepareStatement("SELECT  COUNT(*) as cntSub  FROM EXERCISE WHERE TOKENNUM = ?");
		   ps_1.setString(1, token);
		   
		   rs1 = ps_1.executeQuery();
		   rs1.next();
		   count = rs1.getInt(1);
		   index = 1;
		   while(index <= count)
		   {
			   System.out.println(index + " HW" + index);
			   index++;
		   }
		   
		   System.out.println("Press " + index + " to go back");

		   Scanner scanIn = new Scanner(System.in);
		   while (!(scanIn.hasNextInt()))
		   {
			  System.out.println("Enter a valid Integer ");
			  scanIn.next();
		   }
		   int choice = scanIn.nextInt();

		   if(choice == index)
		   {
			   return;
		   }
		   else if (choice < index && choice > 0) 
		   {
				menu5A11(token,choice);
		   } 
		   else 
		   {
			  System.out.println("Invalid choice.");
		   }

	   }
		catch (Exception e)
		{
				e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}		

   }
   
   public static void menu5A11(String token,int hwrk)
   {
	   Connection con = null;
	   
	   PreparedStatement ps_1 = null;
	   ResultSet rs1 = null;

	   PreparedStatement ps_2 = null;
	   ResultSet rs2 = null;

	   String tempString = ""; 
	   try 
	   {
		   con = JDBCHelper.getConnection();
		   
		   System.out.println("HW"+hwrk);
		   ps_1 = con.prepareStatement("SELECT RandomSeed,NumRetries,EStartDate,EEndDate,ScoreSelection,PntCorrect,PntInPenalty,EDIFFICULTYLOW, EDIFFICULTYHIGH "+
				   						"FROM EXERCISE WHERE EID = ? AND TOKENNUM = ?");
		   ps_1.setString(1, Integer.toString(hwrk));
		   ps_1.setString(2, token);
		   rs1 = ps_1.executeQuery();
		   rs1.next();
		   
		   System.out.println("Random Seed : "+rs1.getLong("RANDOMSEED"));
		   if (rs1.getInt("NumRetries") == -1)
		   {   
			   System.out.println("Number of retries : Unlimited");
		   }
		   else
		   {	   
			   System.out.println("Number of retries : "+rs1.getInt("NumRetries"));
		   }
		   System.out.println("Exercise start date : "+rs1.getString("ESTARTDATE"));
		   System.out.println("Exercise end date : "+rs1.getString("EENDDATE"));
		   if (rs1.getString("ScoreSelection").equals("MAX"))
		   {
			   System.out.println("Score Selection : Maximum");			   
		   }
		   else if (rs1.getString("ScoreSelection").equals("AVG"))
		   {
			   System.out.println("Score Selection : Average");			   
		   }
		   else
		   {
			   System.out.println("Score Selection : Latest");			   
		   }

		   System.out.println("Points for a correct answer : "+rs1.getInt("PNTCORRECT"));
		   System.out.println("Points for a correct answer : "+rs1.getInt("PNTINPENALTY"));
		   
		   System.out.println("Difficulty Range : "+ rs1.getInt("EDIFFICULTYLOW") + " - " + rs1.getInt("EDIFFICULTYHIGH"));

		   ps_1 = null;
		   rs1 = null;
		   
		   ps_1 = con.prepareStatement("SELECT TOPIC FROM TopicExerciseMap WHERE EID = ? AND TOKENNUM = ?");
		   ps_1.setString(1, Integer.toString(hwrk));
		   ps_1.setString(2, token);

		   rs1 = ps_1.executeQuery();
		   System.out.print("Topics :");
		   while (rs1.next())
		   {
			   System.out.println("      "+rs1.getString("TOPIC"));
		   }
		   
		   ps_1 = null;
		   rs1 = null;
		   
		   ps_1 = con.prepareStatement("SELECT ID FROM FormExercise WHERE EID = ? AND TOKENNUM = ?");
		   ps_1.setString(1, Integer.toString(hwrk));
		   ps_1.setString(2, token);

		   rs1 = ps_1.executeQuery();
		   System.out.println("Questions :");
		   while (rs1.next())
		   {
			   tempString = rs1.getString("ID");
			   ps_2 = null;
			   rs2 = null;
			   
			   
			   if (tempString.charAt(0) == 'P')
			   {
				   ps_2 = con.prepareStatement("SELECT PTEXT FROM PQUESTION WHERE PQID = ?");
				   ps_2.setString(1, tempString);

				   rs2 = ps_2.executeQuery();
				   rs2.next();
				   System.out.println(rs2.getString("PTEXT"));
			   }
			   else
			   {
				   ps_2 = con.prepareStatement("SELECT TEXT FROM QUESTION WHERE QID = ?");
				   ps_2.setString(1, tempString);

				   rs2 = ps_2.executeQuery();
				   rs2.next();
				   System.out.println(rs2.getString("TEXT"));
				   
			   }
		   }
		   System.out.println("Press any key to return ");
		   Scanner scanString = new Scanner(System.in);
		   scanString.nextLine();
		   
	   }
		catch (Exception e)
		{
				e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(rs2);
			JDBCHelper.close(ps_2);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}		
	
		 
}
   
   public static void selecttoken()
   {
		Connection con = null;
		PreparedStatement ps_1 = null;
		ResultSet rs1 = null;

		PreparedStatement ps_2 = null;
		ResultSet rs2 = null;
		
		Set<String> eList = new TreeSet<String>();
		Vector<String> t = new Vector<String>();
		int count = 0;
		String pId = "", temp = "";
		
		try
		{
			con = JDBCHelper.getConnection();
			while (true)
			{
				System.out.println("List of courses");
				ps_1 = con.prepareStatement("SELECT TokenNum,CourseId,CourseName,CStartDate,EnrollEndDate,CEndDate,PId,CourseLevel,MaxEnrolled " + 
											"FROM Course_Inactive");
				rs1 = ps_1.executeQuery();
				while (rs1.next())
				{
					temp =  rs1.getString("TOKENNUM") + "  " + rs1.getString("COURSEID") + "-" + rs1.getString("COURSENAME") + " ";
					pId = rs1.getString("PID");
					if (rs1.wasNull())
					{
						System.out.println("Choice "+ count + " " + temp);
						t.add(rs1.getString("TOKENNUM"));
						count++;		
					}
					else
					{
						ps_2 = con.prepareStatement("SELECT PNAME FROM PROFESSOR WHERE ID = ?");
						ps_2.setString(1, pId);
						rs2 = ps_2.executeQuery();
						rs2.next();
						
						temp += "Prof :" + rs2.getString("PNAME");
						eList.add(temp);
					}
				}
				
				System.out.println("List of courses which have been assigned");
				Iterator<String> itr = eList.iterator();
				while (itr.hasNext())
				{
					System.out.println(itr.next());
				}
				
				System.out.println("Enter "+ count + " to return");
				
				Scanner scanInt = new Scanner(System.in);
				while (!(scanInt.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
				    scanInt.next();
				}
				int choice = scanInt.nextInt();

				if(choice == count)
				{
				   return;
			    }
			    else if (choice < count && choice >= 0) 
				{
						ps_1 = null;
						rs1 = null;
						
						ps_1 = con.prepareStatement("UPDATE COURSE_INACTIVE SET PID = ? WHERE TOKENNUM = ?");
						ps_1.setString(1, profId);
						ps_1.setString(2, t.elementAt(choice));
						
						int c = ps_1.executeUpdate();
						if (c != 1)
						{
							System.out.println("Update Failed");
						}
						else
						{
							System.out.println("Update Successful");
						}
				} 
				else 
				{
					  System.out.println("Invalid choice.");
				}
			
				System.out.println("Press any key to return");
				Scanner scanString = new Scanner(System.in);
				scanString.nextLine();
				break;
				
			}
   		}
   	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(rs2);
			JDBCHelper.close(ps_2);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	   
   }

   public static void Menu5A2(String token)
  {
		//Professor reporting
		Connection con = null;
	    
		PreparedStatement ps_1 = null;
	    ResultSet rs1 = null;
	    
		PreparedStatement ps_2 = null;
	    ResultSet rs2 = null;

	    int a = 0,count = 0;
	    try
		{
			con = JDBCHelper.getConnection();
			while(true)
			{
				System.gc();
				System.out.println("Reporting Options: ");
				System.out.println("1. Find students who did not take homework 1");
				System.out.println("2. Find students who scored the maximum score on the first attempt for homework 1");
				System.out.println("3. Find students who scored the maximum score on the first attempt for each homework");
				System.out.println("4. For each student, show total score for each homework and average score across all homeworks");
				System.out.println("5. For each homework, show average number of attempts");
				System.out.println("6. To return");

				Scanner scanString = new Scanner(System.in);
				Scanner scanIn = new Scanner(System.in);

				while (!(scanIn.hasNextInt()))
				{
					System.out.println("Enter a valid Integer ");
					scanIn.next();
				}
				a = scanIn.nextInt();

				switch(a)
				{
					case 1: ps_1 = con.prepareStatement("SELECT SNAME,ID FROM ENROLLMENT WHERE TOKENNUM = ? AND ID NOT IN "+
									" (SELECT DISTINCT STUD_ID FROM VIEW_SCORES WHERE TOKENNUM = ? AND EID = ?) ");
						   	ps_1.setString(1, token);
						   	ps_1.setString(2, token);
						   	ps_1.setString(3, "1");
						    
						    rs1 = ps_1.executeQuery();
						    System.out.println("List of students who did not take homework 1 for :"+tokenNum);
						    count = 0;
						    while (rs1.next())
						    {
						    	count++;
						    	System.out.println(count +". "+ rs1.getString("SNAME"));						    	
						    }
						    
						    if (count == 0)
						    {
						    	System.out.println("All students have taken the howework");
						    }
						    System.out.println("Enter any character to return");
						    scanString.nextLine();
							break;
							
					case 2: ps_1 = con.prepareStatement("SELECT E.SNAME,E.ID FROM ENROLLMENT E WHERE E.ID IN " +
															"(SELECT STUD_ID FROM VIEW_SCORES WHERE EID = ? AND ATTEMPT_NO = ? AND TOKENNUM = ? AND "+ 
																"SCORES = (SELECT MAX(SCORES) FROM VIEW_SCORES WHERE EID = ? AND ATTEMPT_NO = ? AND TOKENNUM = ?))");
							ps_1.setString(1, "1");
							ps_1.setInt(2, 1);
							ps_1.setString(3, token);
							ps_1.setString(4, "1");
							ps_1.setInt(5, 1);
							ps_1.setString(6, token);
							rs1 = ps_1.executeQuery();
							
							System.out.println("List of students :");
							while (rs1.next())
							{
								System.out.println(rs1.getString("ID") + " " +  rs1.getString("SNAME"));
							}							
						    System.out.println("Enter any character to return");
						    scanString.nextLine();
							break;

					case 3: ps_1 = con.prepareStatement("SELECT TEMP.EID,TEMP.STUD_ID,E.SNAME FROM ENROLLMENT E JOIN " +
										"(SELECT EID,STUD_ID,MAX(SCORES) AS MX FROM VIEW_SCORES WHERE TOKENNUM = ? GROUP BY EID,STUD_ID) TEMP ON E.ID = TEMP.STUD_ID");
							ps_1.setString(1, token);
							
							rs1 = ps_1.executeQuery();
							while (rs1.next())
							{
								System.out.println(rs1.getString("STUD_ID") + "  " + rs1.getString("SNAME") + " HW" + rs1.getString("EID"));
							}
						    System.out.println("Enter any character to return");
						    scanString.nextLine();
							break;
							
					case 4:	ps_1 = con.prepareStatement(" SELECT E.SNAME,F1.EID,F1.STUD_ID,F1.SCORES,F2.SCR FROM FSCORE F1 JOIN " +
										"(SELECT AVG(SCORES) AS SCR,STUD_ID FROM FSCORE WHERE TOKENNUM = ? GROUP BY STUD_ID) F2 ON F2.STUD_ID = F1.STUD_ID AND TOKENNUM = ? JOIN " +
											" ENROLLMENT E ON E.ID = F1.STUD_ID");
							ps_1.setString(1, token);
							ps_1.setString(2, token);
							
							rs1 = ps_1.executeQuery();
							while (rs1.next())
							{
								System.out.println(rs1.getString("STUD_ID") + "  " + rs1.getString("SNAME") + " HW" + rs1.getString("EID") + " Score:" + rs1.getString("SCORES")   + " AVG" + rs1.getFloat("SCR"));								
							}

					
					
							System.out.println("Enter any character to return");
							scanString.nextLine();
							break;

					case 5: ps_1 = con.prepareStatement("SELECT AVG(TEMP.CNT),TEMP.EID FROM " + 
									"(SELECT MAX(ATTEMPT_NO) AS CNT,EID,STUD_ID FROM VIEW_SCORES WHERE TOKENNUM = ? GROUP BY EID,STUD_ID) TEMP GROUP BY TEMP.EID");
							ps_1.setString(1, token);
							
							rs1 = ps_1.executeQuery();
							while (rs1.next())
							{
								System.out.println("HW" + rs1.getString("EID") + " AVG - "+ rs1.getFloat(1));
							}
							System.out.println("Enter any character to return");
							scanString.nextLine();
							break;

					case 6: return;

					default: System.out.println("Invalid Choice");
							 break;
							 
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(rs2);
			JDBCHelper.close(ps_2);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}

  }

   public static boolean checkTAEnrollment(String token)
  {
		Connection con = null;
		PreparedStatement ps_1 = null;
		ResultSet rs1 = null;

		PreparedStatement ps_2 = null;
		ResultSet rs2 = null;

		PreparedStatement ps_3 = null;
		ResultSet rs3 = null;

		String prof1 = "";
		String msg = "" ;
		int count = 0;
		token = token.trim();
		System.out.println(token);
		Set<String> eTopics = new TreeSet<String>();
		try
		{
			con = JDBCHelper.getConnection();
			
			ps_1 = con.prepareStatement("Select TOPIC FROM TOPICCOURSEMAP WHERE TOKENNUM = ?");
			ps_1.setString(1,token);
			rs1 = ps_1.executeQuery();
			
			while (rs1.next())
			{
				System.out.println(rs1.getString("TOPIC"));
				eTopics.add(rs1.getString("TOPIC"));
			}
			
			
			
			ps_1 = con.prepareStatement("SELECT TOKENNUM FROM TASSISTANT WHERE ID = ?");
			ps_1.setString(1, studentId);
			String tempToken = "";
			
			rs1 = ps_1.executeQuery();
			
			while (rs1.next())
			{
				tempToken = rs1.getString("TOKENNUM");
				ps_2 = con.prepareStatement("SELECT TOPIC FROM TOPICCOURSEMAP WHERE TOKENNUM = ?");
				ps_2.setString(1, tempToken);
				
				rs2 = ps_2.executeQuery();
				while (rs2.next())
				{
					if (eTopics.contains(rs2.getString("TOPIC")))
					{
						System.out.println("Course Topics overlap, you will not be enrolled");
						ps_3 = con.prepareStatement("SELECT PID FROM COURSE WHERE TOKENNUM = ?");
						ps_3.setString(1, token);
						
						rs3 = ps_3.executeQuery();
						rs3.next();
						
						prof1 = rs3.getString("PID");
						msg = "Student " + studentName + " Id:" + studentId + " has enroll overlap"; 
						ps_3 = null;
						rs3 = null;
						
						
						ps_3 = con.prepareStatement("INSERT INTO ALERTS (ID,MSG) VALUES (?,?)");
						ps_3.setString(1, prof1);
						ps_3.setString(2, msg);
								
						count = ps_3.executeUpdate();
						if (count != 1)
						{
							System.out.println("Update failed");
							return true;
						}
						
						else
						{
							ps_3 = con.prepareStatement("SELECT PID FROM COURSE WHERE TOKENNUM = ?");
							ps_3.setString(1, tempToken);
							
							rs3 = ps_3.executeQuery();
							rs3.next();
							
							prof1 = rs3.getString("PID");
							msg = "Student " + studentName + " Id:" + studentId + " has enroll overlap"; 
							ps_3 = null;
							rs3 = null;
							
							
							ps_3 = con.prepareStatement("INSERT INTO ALERTS (ID,MSG) VALUES (?,?)");
							ps_3.setString(1, prof1);
							ps_3.setString(2, msg);
									
							count = ps_3.executeUpdate();
							if (count != 1)
							{
								System.out.println("Update failed");
							}
							return true;
						}
						
					}
				  }
			}			
 		}
 	
		catch (Exception e)
		{
			e.printStackTrace();

		}
		finally
		{
			JDBCHelper.close(rs1);
			JDBCHelper.close(rs2);
			JDBCHelper.close(rs3);
			JDBCHelper.close(ps_3);
			JDBCHelper.close(ps_2);
			JDBCHelper.close(ps_1);
			JDBCHelper.close(con);
		}
	   
		return false;
  }
}
