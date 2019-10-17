import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.zip.InflaterInputStream;
import java.util.zip.DeflaterOutputStream;
class Facebook extends Thread
{
	static String username;
	static long thread_id;
	//Multi-Threaded Function To Check Duplicate User
	public void run()
	{
		try
		{
			File f=new File("C:\\Facebook\\"+thread_id+"\\online.fb");
			RandomAccessFile raf=new RandomAccessFile("C:\\Facebook\\"+thread_id+"\\Notifications.fb","r");
			long fl=raf.length();
			raf.close();
			while(thread_id!=0)
			{
				removeDuplicateLine("C:\\Facebook\\"+thread_id+"\\Notifications.fb");
				raf=new RandomAccessFile("C:\\Facebook\\"+thread_id+"\\Notifications.fb","r");
				if(raf.length()>fl)
				{
					Toolkit.getDefaultToolkit().beep();
					wait(100);
					Toolkit.getDefaultToolkit().beep();
					wait(100);
					Toolkit.getDefaultToolkit().beep();
				}
				fl=raf.length();
				raf.close();
				if(!f.exists())
				{
					clear();
					Toolkit.getDefaultToolkit().beep();
					wait(100);
					Toolkit.getDefaultToolkit().beep();
					System.out.println("\t\tYou Are Logged Out From Facebook\n\t\tReason:\n\t\tAnother User Opened Your Account And Chooses To Log You Out!!!");
                    new Scanner(System.in).nextLine();
                    thread_id=0;
                    username="";
                    exit(1);
                }
            }
        }
        catch(Exception e){}
        if(thread_id==0)
            return;
   }
   //Generates Random Number
   static int rnd(int r)
   {
            int k=0;
            k=(int)Math.abs(System.nanoTime()%r+1);
            return k;
   }
   //Logs In Interface
   static void logIn()throws Exception
   {
        String email,password;
   /*
    Note: The File fbmain.fb contains logging in Information Such As Password,Email,UserId
            The File fbmain.fb Is Written AS Follows:-
            Email           |
            Password        |  User1
            UserId          |
            Email           |
            Password        |  User2
            UserId          |
            Email           |
            Password        |  User3
            UserId          |
   */
        clear();
        System.out.println();
        Scanner sc=new Scanner(System.in);
        boolean flag=true;
        while(flag)
        {
            File f=new File("C:\\Facebook\\fbmain.fb");
            Scanner read=new Scanner(f);
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("******************     --------------------------------      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     |           LOG IN             |      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     --------------------------------      ******************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.println("\n\n\n\n\n\n\n\n");
            System.out.println("\tPress # to Go To Main Menu");
            System.out.print("\tInput Your Email Address :- ");
            email=sc.nextLine();
            email=email.trim();
            if(email.equals("#"))
            {
                flag=false;
                continue;
            }
            System.out.print("\tInput Your Password :- ");
            password=sc.nextLine();
            String tpass="";
            int trial=2;
            boolean wemail=true;
            if(email.equals(""))
            {
                System.out.println("\t\t\tEmail Not Inputted !!!");
                wait(1500);
                clear();
                continue;
            }
            read.nextLine();
            while(read.hasNextLine())
            {
                String s=read.nextLine();
                String t=read.nextLine();
                String k=read.nextLine();
                if(s.equals(email))
                {
                    wemail=false;
                    long u=Long.parseLong(k);
                    username=change(u);
                    tpass=t;
                }
            }
            read.close();
            if(wemail)
            {
                try
                {
                    Toolkit.getDefaultToolkit().beep();
                    Thread.sleep(100);
                    Toolkit.getDefaultToolkit().beep();
                }
                catch(InterruptedException ie)
                {}
                System.out.println("\t\tEmail Address Not Found !!");
                System.out.println("\t\tPlease Try Again !!");
                System.out.println("\t\tPress # To Go To Main Menu");
                System.out.println("\t\tPress Enter To Continue");
                String ch=sc.nextLine();
                if(ch.trim().equals("#"))
                mainMenu();
                else
                 clear();
            }
            else
            {
                boolean flagp=true;
                if(password.equals(tpass))
                {
                    flag=false;
                    openAccount();
                }
                else
                {
                    while(flagp)
                    {
                        clear();
                        System.out.println();
                        Toolkit.getDefaultToolkit().beep();
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("\t\t\tWrong Password Inputted !!!\nTry Number :- " + trial);
                        System.out.println("Email :- "+email);
                        System.out.print("Enter your Password :- ");
                        password=sc.nextLine();
                        if(password.equals(tpass))
                        {
                            flag=false;
                            flagp=false;
                            clear();
                            SimpleDateFormat df=new SimpleDateFormat("HH");
                            Date dt=new Date();
                            int k=Integer.parseInt(df.format(dt));
                            String wish="\n\n\n\n\n\n\n\n\n\n\n\n                               ";
                            if(k>=4 && k<=11)
                            {
                                wish+=" Good Morning !!!";
                            }
                            else if(k>=12 && k<=15)
                            {
                                wish+="Good Afternoon !!!";
                            }
                            else if(k>=16 && k<=20)
                            {
                                wish+=" Good Evening !!!";
                            }
                            else
                            {
                                wish+="  Good Night !!!";
                            }
                            System.out.println(wish+"\n\n\n\n\n\n\n\n\n");
                            System.out.println("Press Enter To Continue...");
                            new Scanner(System.in).nextLine();
                            openAccount();
                        }
                        else
                        {
                            boolean flagc=true;
                            if(trial%5==0)
                            {
                                while(flagc)
                                {
                                    System.out.print("Have You Forgotten Your Password (Y/N): ");
                                    String c=sc.nextLine();
                                    if(c.trim().equalsIgnoreCase("Y"))
                                    {
                                        flagc=false;
                                        password=tpass;
                                        forgotPassword();
                                    }
                                    else if(c.trim().equalsIgnoreCase("N"))
                                    {
                                        flagc=false;
                                    }
                                    else
                                    System.out.println("Please Give A Valid Input (Y/N)");
                                }
                            }
                            trial++;
                        }
                    }
                }
            }
        }
    }
    //Sign In InterFace
   static void signIn() throws Exception
   {
        Scanner sc=new Scanner(System.in);
        boolean flag=false,check=true;;
        clear();
           String user="",e_mail="",pass="",gen="",birth="",pn="";
        while(!flag)
        {
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("******************     --------------------------------      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     |           SIGN  IN           |      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     --------------------------------      ******************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.println("\n\n\n\n\n\n\n\n");
            System.out.println("Press # To Go To Main Menu");
            System.out.print("Enter Your First Name:");
            String user1=sc.nextLine().trim();
            if(user1.trim().equals("#"))
            {
                flag=true;
                check=false;
                continue;
            }
            System.out.println();
            System.out.print("Enter Your Last Name:");
            String user2=sc.nextLine().trim();
            user=user1+" "+user2;
            System.out.println();
            System.out.print("Enter Your Email:");
            e_mail=sc.nextLine();
            System.out.println();
            System.out.println("Enter Your Password(Don't Include Spaces At First Or Last Of Your Password):");
            pass=sc.nextLine();
            System.out.println();
            System.out.print("Enter Your Gender (M/F):");
            gen=sc.nextLine();
            System.out.println();
            System.out.print("Enter Your BirthDay In dd/mm/yyyy Format(Ex:- 01/02/2016):");
            birth=sc.nextLine();
            System.out.println();
            System.out.print("Enter Your Phone Number:");
            pn=sc.nextLine();
            System.out.println();
            boolean flag1=checkValidUsername(user);
            while(!flag1)
            {
                System.out.print("Enter Your First Name:");
                user1=sc.nextLine().trim();
                System.out.print("Enter Your Last Name:");
                user2=sc.nextLine().trim();
                user=user1+" "+user2;
                System.out.println();
                flag1=checkValidUsername(user);
            }
            boolean flag2=checkValidEmail(e_mail);
            while(!flag2)
            {
                System.out.print("Re-Enter Your Email:");
                e_mail=sc.nextLine();
                System.out.println();
                flag2=checkValidEmail(e_mail);
            }
            boolean flag3=checkValidPassword(pass);
            while(!flag3)
            {
                System.out.println("Re-Enter Your Password(Don't Include Spaces At First Or Last Of Your Password):");
                pass=sc.nextLine();
                System.out.println();
                flag3=checkValidPassword(pass);
            }
            boolean flag4=checkValidGender(gen);
            while(!flag4)
            {
                System.out.print("Re-Enter Your Gender (M/F):");
                gen=sc.nextLine();
                System.out.println();
                flag4=checkValidGender(gen);
            }
            boolean flag5=checkValidDate(birth);
            while(!flag5)
            {
                System.out.print("Re-Enter Your BirthDay In dd/mm/yyyy Format(Ex:- 01/02/2016):");
                birth=sc.nextLine();
                System.out.println();
                flag5=checkValidDate(birth);
            }
            boolean flag6=checkValidNumber(pn);
            while(!flag6)
            {
                System.out.print("Re-Enter Your Phone Number:");
                pn=sc.nextLine();
                System.out.println();
                flag6=checkValidNumber(pn);
            }
            flag=flag1 && flag2 && flag3 && flag4 && flag5 && flag6;
            int d=Integer.parseInt(birth.substring(0,birth.indexOf('/')));
            int m=Integer.parseInt(birth.substring(birth.indexOf('/')+1,birth.lastIndexOf('/')));
            int y=Integer.parseInt(birth.substring(birth.lastIndexOf('/')+1));
            if(d<10)
            {
                birth="0"+d+birth.substring(birth.indexOf('/'));
            }
            if(m<10)
            {
                birth=birth.substring(0,3)+"0"+m+birth.substring(birth.lastIndexOf('/'));
            }
        }
        if(check)
        {
        System.out.println("Entries Taken Successfully !!!");
        //C:\\Facebook\\fbmain.fb
        File f=new File("C:\\Facebook\\fbmain.fb");
        BufferedWriter o1=new BufferedWriter(new FileWriter(f,true));
        PrintWriter pw=new PrintWriter(o1);
        pw.println(e_mail);
        pw.println(pass);
        long id=getLastId()+1;
        pw.println(id);
        pw.close();
        //C:\\Facebook\\Username.fb
        /*
            Note: The File Username.fb contains Username.
            The File Username.fb Is Written AS Follows:-
            UserId          |
            UserName        |  User1
        */
        f=new File("C:\\Facebook\\Username.fb");
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
        pw.println(id);
        pw.println(user);
        pw.close();
        //C:\\Facebook\\<UserId>
        String s="C:\\Facebook\\"+id;
        f=new File(s);
        f.mkdir();
        //C:\\Facebook\\<UserId>\\Profile.fb
        String temp=s+"\\Profile.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println(id);
        pw.println(e_mail);
        pw.println(pass);
        pw.println(gen);
        pw.println(birth);
        pw.println(pn);
        pw.println(getDate());
        pw.close();
        //C:\\Facebook\\<UserId>\\Friends.fb
        temp=s+"\\Friends.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println();
        pw.close();
        //C:\\Facebook\\<UserId>\\TimeLine.fb
        temp=s+"\\TimeLine.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println();
        pw.close();
        //C:\\Facebook\\<UserId>\\Requests.fb
        temp=s+"\\Requests.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println();
        pw.close();
        //C:\\Facebook\\<UserId>\\Timeline.fb
        temp=s+"\\Timeline.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println();
        pw.close();
        //C:\\Facebook\\<UserId>\\Notifications.fb
        temp=s+"\\Notifications.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println();
        pw.close();
        //C:\\Facebook\\<UserId>\\Status.fb
        temp=s+"\\Status.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println("\tFeeling Happy After Joining Facebook !!!");
        pw.close();
        //C:\\Facebook\\<UserId>\\Settings.fb
        temp=s+"\\Settings.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        //Login Alerts Off-0 On-1
        pw.println("0");
        //Future Posts Public-0 Friend-1
        pw.println("1");
        //View Details EveryOne-0 Friends-1 No-One-2
        pw.println("0");
        //Send Request Everyone-0 No-one-1
        pw.println("0");
        //See Your Timeline Everyone-0 No-One-1
        pw.print("0");
        pw.close();
        //C:\\Facebook\\<UserId>\\Homepage.fb
        temp=s+"\\Homepage.fb";
        f=new File(temp);
        pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
        pw.println("\t\t\tThanks For Signing To Facebook");
        pw.println("\t\t\t                                -Created by SpeedX");
        pw.println("\t\t\t                      -Created by Gyanaranjan Sahoo");
        pw.println();
        pw.println();
        pw.println();
        pw.println();
        pw.println();
        pw.println();
        pw.println();
        pw.close();
        System.out.println("Press Enter To Continue...");
        String asd=sc.nextLine();
        username=user;
        openAccount();
        }
    }
    //Change Id to Username
    static String change(long i)throws Exception
    {
        File f=new File("C:\\Facebook\\Username.fb");
        Scanner sc=new Scanner(f);
        String res="";
        while(sc.hasNextLine())
        {
            String l1=sc.nextLine();
            String l2=sc.nextLine();
            if(Long.parseLong(l1)==i)
            {
                res=l2;
                break;
            }
        }
        sc.close();
        return res;
    }
    //Change Username To Id
    static long change(String i)throws Exception
    {
        File f=new File("C:\\Facebook\\Username.fb");
        Scanner sc=new Scanner(f);
        long id=0;
        while(sc.hasNextLine())
        {
            String l1=sc.nextLine();
            String l2=sc.nextLine();
            if(l2.equalsIgnoreCase(i) || l2.trim().equalsIgnoreCase(i) )
            {
                id=Long.parseLong(l1);
                break;
            }
        }
        sc.close();
        return id;
    }
    //Returns Last Id
    static long getLastId()throws Exception
    {
        File f=new File("C:\\Facebook\\Username.fb");
        Scanner sc=new Scanner(f);
        long res=0;
        while(sc.hasNextLine())
        {
            String l1=sc.nextLine();
            String l2=sc.nextLine();
            res=Long.parseLong(l1);
        }
        sc.close();
        return res;
    }
    //Returns Current Date
    static String getDate()throws Exception
    {
        GregorianCalendar gr=new GregorianCalendar();
        int d=gr.get(Calendar.DATE);
        int m=gr.get(Calendar.MONTH)+1;
        int y=gr.get(Calendar.YEAR);
        String d1,m1;
        if(d<10)
        d1="0"+d;
        else
        d1=d+"";
        if(m<10)
        m1="0"+m;
        else
        m1=m+"";
        String s=d1+"-"+m1+"-"+y;
        return s;
    }
    //Removes Duplicate Lines
    static void removeDuplicateLine(String file)throws Exception
    {
        File f=new File(file);
        Scanner sc=new Scanner(f);
        String line="";
        int nline=0;
        while(sc.hasNextLine())
        {
            ++nline;
            line+=sc.nextLine()+"\n";
        }
        sc.close();
        String arrLine[]=new String[nline];
        int p=0;
        for(int i=0;i<nline;i++)
        {
            arrLine[i]=line.substring(p,line.indexOf('\n',p));
            p=line.indexOf('\n',p)+1;
        }
        for(int i=nline-1;i>=0;i--)
        {
            String tmp=arrLine[i];
            if(tmp.equals(""))
                continue;
            for(int j=0;j<i;j++)
            {
                if(tmp.equalsIgnoreCase(arrLine[j]))
                    arrLine[j]="";
            }
        }
        String ans="";
        for(int i=0;i<nline;i++)
        {
            if(arrLine[i].equals(""))
                continue;
            ans+=arrLine[i]+"\n";
        }
        if(ans.lastIndexOf('\n')!=-1)
        ans=ans.substring(0,ans.lastIndexOf('\n'));
        PrintWriter pr=new PrintWriter(new BufferedWriter(new FileWriter(file)));
        if(file.endsWith("Requests.fb")||file.endsWith("requests.fb"))
        {
            pr.println("\n"+ans);
        }
        else
        {
            pr.println(ans);
        }
        pr.close();
    }
    //Returns Gender From User Id
    static char getGender(long id) throws Exception
    {
        Scanner sc=new Scanner(new File("C:\\Facebook\\"+id+"\\Profile.fb"));
        String d="";
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        d=sc.nextLine();
        sc.close();
        return d.charAt(0);
    }
    //Chat InterFace
    static void chat()throws Exception
    {
        long id=change(username);
        clear();
            System.out.println();
        Scanner sc=new Scanner(System.in);
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("******************     --------------------------------      ******************\n");
        System.out.print("******************     |                              |      ******************\n");
        System.out.print("******************     |             CHAT             |      ******************\n");
        System.out.print("******************     |                              |      ******************\n");
        System.out.print("******************     --------------------------------      ******************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.print("Press Enter To Check Who Are Online");
        sc.nextLine();
        boolean flag=true;
        while(flag)
        {
            clear();
            File f=new File("C:\\Facebook\\"+id+"\\Friends.fb");
            Scanner read=new Scanner(f);
            read.nextLine();
            String on="";
            int a=0;
            String onlin[];
            Scanner block;
            while(read.hasNextLine())
            {
                long fid=Long.parseLong(read.nextLine());
                read.nextLine();
                    boolean blocked=false;
                if(new File("C:\\Facebook\\"+fid+"\\BlockList.fb").exists())
                {
                    block=new Scanner(new File("C:\\Facebook\\"+fid+"\\BlockList.fb"));
                    while(block.hasNextLine())
                    {
                        long bid=Long.parseLong(block.nextLine());
                        if(bid==id)
                        blocked=true;
                    }
                    block.close();
                }
                if(blocked)
                {
                    continue;
                }
                f=new File("C:\\Facebook\\"+fid+"\\online.fb");
                if(f.exists())
                {
                    a++;
                    on+=change(fid)+"\n";
                }
            }
            read.close();
            onlin=new String[a];
            if(a==0)
            {
                System.out.println("\t\tNo One Is Online To Chat");
            }
            else
            {
                int t1=0;
                for(int i=0;i<a;i++)
                {
                    onlin[i]=on.substring(t1,on.indexOf("\n",t1));
                    t1=on.indexOf("\n",t1)+1;
                    System.out.println("\t\t\t"+(i+1)+" - "+onlin[i]);
                }
            }
            System.out.println("\t\tPress "+(a+1)+" To Refresh Chat List");
            System.out.println("\t\tPress "+(a+2)+" For More Friends");
            System.out.println("\t\tPress "+(a+3)+" To Go Back");
            System.out.print("\t\tInput A Number:- ");
            int inp=0;
            while(inp==0)
            {
                inp=input(a+3);
            }
            String name="";
            if(inp==(a+3))
            {
                flag=false;
                continue;
            }
            else if(inp==(a+2))
            {
                System.out.println("\t\tEnter The Name Of Your Friend");
                String pnm=sc.nextLine().trim();
                read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                if(pnm.equals(""))
                    continue;
                int c=0;
                boolean flag4=true;
                String list="";
                read.nextLine();
                read.nextLine();
                while(read.hasNextLine())
                {
                    read.nextLine();
                    String un=read.nextLine().trim();
                    if(searchString(un,pnm))
                    {
                        ++c;
                        flag4=false;
                        list+=un+"\n";
                    }
                }
                read.close();
                if(flag4)
                {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("\t\tNo Such User Exists !!!!");
                    System.out.println("\tPress Enter To Continue...");
                    sc.nextLine();
                    continue;
                }
                String nl[]=new String[c];
                int t1=0;
                for(int j=0;j<c;j++)
                {
                    nl[j]=list.substring(t1,list.indexOf("\n",t1));
                    t1=list.indexOf("\n",t1)+1;
                    System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                }
                System.out.println("\t\t"+(c+1)+" - Back");
                System.out.print("Input a Desired Number To Select User:- ");
                inp=0;
                while(inp==0)
                {
                    inp=input(c+1);
                }
                if(inp==(c+1))
                    continue;
                name=nl[--inp].trim();
            }
            else if(inp==(a+1))
            {
                continue;
            }
            else
            {
                name=onlin[--inp].trim();
            }
                a=0;
                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                read.nextLine();
                while(read.hasNextLine())
                {
                    long ide=Long.parseLong(read.nextLine());
                    String bir=read.nextLine();
                    String us=change(ide);
                    boolean blocked=false;
                    if(new File("C:\\Facebook\\"+ide+"\\BlockList.fb").exists())
                    {
                        block=new Scanner(new File("C:\\Facebook\\"+ide+"\\BlockList.fb"));
                        while(block.hasNextLine())
                        {
                            long bid=Long.parseLong(block.nextLine());
                            if(bid==id)
                            blocked=true;
                        }
                        block.close();
                    }
                    if(blocked)
                    {
                        continue;
                    }
                    if(us.equalsIgnoreCase(name))
                    {
                        a=1;
                    }
                }
                read.close();
                long fid=change(name);
                id=change(username);
                if(fid==0)
                {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("No Such User Exists !!!");
                    sc.nextLine();
                    continue;
                }
                f=new File("C:\\Facebook\\"+fid+"\\"+id+".fb");
                if(f.exists())
                {
                    String ac="C:\\Facebook\\"+id+"\\"+fid+".fb";
                    clear();
                    refreshPage(ac,"Chat With: "+name);
                }
                else
                {
                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                    pw.println("");
                    pw.close();
                    f=new File("C:\\Facebook\\"+id+"\\"+fid+".fb");
                    pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                    pw.println("");
                    pw.close();
                }
                if(a==1)
                {
                    f=new File("C:\\Facebook\\"+fid+"\\online.fb");
                    if(f.exists())
                    {
                        boolean cflag=true;
                        while(cflag)
                        {
                            System.out.println("\t\t\tInput GB To Go Back");
                            System.out.println("\t\t\tInput CL To Clear List");
                            System.out.println("\t\t\tInput RP To Refresh Page");
                            System.out.println("\t\t\tInput Your Chat Message???");
                            String msg=sc.nextLine();
                            if(msg.equalsIgnoreCase("GB"))
                            {
                                cflag=false;
                                continue;
                            }
                            else if(msg.trim().equalsIgnoreCase(""))
                            {
                                System.out.println("\t\tMessage Is Null !!!");
                                sc.nextLine();
                                continue;
                            }
                            else if(msg.equalsIgnoreCase("RP"))
                            {
                                String ac="C:\\Facebook\\"+id+"\\"+fid+".fb";
                                refreshPage(ac,"Chat With:"+name);
                                continue;
                            }
                            else if(msg.equalsIgnoreCase("CL"))
                            {
                                f=new File("C:\\Facebook\\"+id+"\\"+fid+".fb");
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                                pw.println("");
                                pw.close();
                                System.out.println("\t\t\tChat List Cleared\n\t\t\tPress Enter To Continue");
                                sc.nextLine();
                                continue;
                            }
                            else
                            {
                                int len=msg.length();
                                id=change(username);
                                f=new File("C:\\Facebook\\"+id+"\\"+fid+".fb");
                                //Your id\Friend id.fb
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                cflag=true;
                                while(cflag)
                                {
                                    for(int i=1;i<=50;i++)
                                    {
                                        pw.print(" ");
                                    }
                                    if(len<=30)
                                    {
                                        for(int i=1;i<=30-len-2;i++)
                                        {
                                            pw.print(" ");
                                        }
                                        pw.print(msg);
                                        pw.println(".\n\n");
                                        cflag=false;
                                        continue;
                                    }
                                    else
                                    {
                                        for(int i=0;i<len;)
                                        {
                                            for(int j=1;j<=50;j++)
                                            {
                                                pw.print(" ");
                                            }
                                            i+=30;
                                            if(i>len)
                                            {
                                                for(int j=1;j<=(30-(len-(i-30)))-3;j++)
                                                {
                                                    pw.print(" ");
                                                }
                                                pw.print(msg.substring(i-30));
                                                pw.println(".\n\n");
                                                cflag=false;
                                                break;
                                            }
                                            else
                                            {
                                                pw.println(msg.substring(i-30,i));
                                            }
                                        }
                                    }
                                }
                                pw.close();
                                //Friendid\YourId.fb
                                f=new File("C:\\Facebook\\"+fid+"\\"+id+".fb");
                                pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                pw.println(username+":-");
                                if(len<=30)
                                {
                                    pw.print(msg);
                                    pw.println(".\n\n");
                                }
                                else
                                {
                                    for(int i=0;i<len;i++)
                                    {
                                        pw.print(msg.charAt(i));
                                        if(i%29==0)
                                        pw.println("");
                                    }
                                    pw.println(".\n\n");
                                }
                                pw.close();
                                //Friendid\Notifications.fb
                                f=new File("C:\\Facebook\\"+fid+"\\Notifications.fb");
                                pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                pw.println(username+" Messaged You !!!");
                                String spa="";
                                for(int spac=0;spac<68;spac++)
                                {
                                    spa+=" ";
                                }
                                spa+="-"+getDate();
                                pw.println(spa);
                                pw.close();
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\n\t\t\tMessage Successfully Sent!!!");
                                cflag=true;

		String ac="C:\\Facebook\\"+id+"\\"+fid+".fb";
                                refreshPage(ac,"Chat With:"+name);
                            }
                        }
                    }
                    else
                    {
                        System.out.println("\t\t\t"+name+" is Offline!!!");
                        boolean asd=true;
                        while(true)
                        {
                            System.out.println("\n\t\t\tDo You Want To Send A Message(Y/N)");
                            String ipl=sc.nextLine();
                            ipl=ipl.trim();
                            if(ipl.equalsIgnoreCase("Y"))
                            {
                                break;
                            }
                            else if(ipl.equalsIgnoreCase("N"))
                            {
                                asd=false;
                                break;
                            }
                            else
                            {
                                System.out.println("\t\t\tInvalid Input !!!\n\tPlease Input Y or N ");
                            }
                        }
                        if(asd)
                        {
                            System.out.println("\t\t\tInput Your Message???");
                            String msg=sc.nextLine();
                            int len=msg.length();
                            id=change(username);
                            f=new File("C:\\Facebook\\"+id+"\\"+fid+".fb");
                            //Your id\Friend id.fb
                            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                            boolean cflag=true;
                            while(cflag)
                            {
                                System.out.println("\t\t\tInput GB To Go Back");
                                System.out.println("\t\t\tInput CL To Clear List");
                                System.out.println("\t\t\tInput RP To Refresh Page");
                                System.out.println("\t\t\tInput Your Message???");
                                msg=sc.nextLine();
                                if(msg.equalsIgnoreCase("GB"))
                                {
                                    cflag=false;
                                    continue;
                                }
                                else if(msg.equalsIgnoreCase("RP"))
                                {
                                    String ac="C:\\Facebook\\"+id+"\\"+fid+".fb";
                                    refreshPage(ac,"Chat With:"+name);
                                    continue;
                                }
                                else if(msg.equalsIgnoreCase("CL"))
                                {
                                    f=new File("C:\\Facebook\\"+id+"\\"+fid+".fb");
                                    pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                                    pw.println("");
                                    System.out.println("\t\t\tChat List Cleared\n\t\t\tPress Enter To Continue");
                                    sc.nextLine();
                                    continue;
                                }
                                else
                                {
		String ac="C:\\Facebook\\"+id+"\\"+fid+".fb";
                                    refreshPage(ac,"Chat With:"+name);
                                    len=msg.length();
                                    id=change(username);
                                    f=new File("C:\\Facebook\\"+id+"\\"+fid+".fb");
                                    //Your id\Friend id.fb
                                    pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                    cflag=true;
                                    while(cflag)
                                    {
                                        for(int i=1;i<=50;i++)
                                        {
                                            pw.print(" ");
                                        }
                                        if(len<=30)
                                        {
                                            for(int i=1;i<=30-len-2;i++)
                                            {
                                                pw.print(" ");
                                            }
                                            pw.print(msg);
                                            pw.println(".\n\n");
                                            cflag=false;
                                            continue;
                                        }
                                        else
                                        {
                                            for(int i=0;i<len;)
                                            {
                                                for(int j=1;j<=50;j++)
                                                {
                                                    pw.print(" ");
                                                }
                                                i+=30;
                                                if(i>len)
                                                {
                                                    for(int j=1;j<=(30-(len-(i-30)))-3;j++)
                                                    {
                                                        pw.print(" ");
                                                    }
                                                    pw.print(msg.substring(i-30));
                                                    pw.println(".\n\n");
                                                    cflag=false;
                                                    break;
                                                }
                                                else
                                                {
                                                    pw.println(msg.substring(i-30,i));
                                                }
                                            }
                                        }
                                    }
                                    pw.close();
                                    //Friendid\YourId.fb
                                    f=new File("C:\\Facebook\\"+fid+"\\"+id+".fb");
                                    pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                    pw.println(username+":-");
                                    if(len<=30)
                                    {
                                        pw.print(msg);
                                        pw.println(".\n\n");
                                    }
                                    else
                                    {
                                        for(int i=0;i<len;i++)
                                        {
                                            pw.print(msg.charAt(i));
                                            if(i%29==0)
                                            pw.println("");
                                        }
                                        pw.println(".\n\n");
                                    }
                                    pw.close();
                                    //Friendid\Notifications.fb
                                    f=new File("C:\\Facebook\\"+fid+"\\Notifications.fb");
                                    pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                    pw.println(username+" Messaged You !!!");
                                    String spa="";
                                    for(int spac=0;spac<68;spac++)
                                    {
                                        spa+=" ";
                                    }
                                    spa+="-"+getDate();
                                    pw.println(spa);
                                    pw.close();
                                    Toolkit.getDefaultToolkit().beep();
                                    System.out.println("\n\t\t\tMessage Successfully Sent!!!");
                                }
                            }
                        }
                    }
                }
                else
                {
                    System.out.println("\t\t\t"+name+" Is Not Your Friend");
                    System.out.println("\t\t\tPress Enter To Continue");
                    sc.nextLine();
                }
        }
    }
    //Post InterFace
    static void post()throws Exception
    {
        for(int i=0;i<500;i++)
        {
            System.out.println();
        }
        Scanner sc=new Scanner(System.in);
        System.out.print("\t\t\tShare With :");
        long id=change(username);
        Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Settings.fb"));
        read.nextLine();
        String asdt=read.nextLine();
        read.close();
        int sw;
        if(asdt.equals("0"))
        {
            sw=0;
            System.out.println("Public");
        }
        else
        {
            sw=1;
            System.out.println("Friends");
        }
        System.out.println("\t\tPlease Press Enter Without Typing Anything To Go Back");
        System.out.println("\t\t\tPlease Type Your Post/Status");
        String post=sc.nextLine();
        post=post.trim();
        Scanner block;
        File f;
        if(!post.equals(""))
        {
            boolean flag=true;
            while(flag)
            {
                System.out.println("\t\t\tPress 1 To Update Status  ");
                System.out.println("\t\t\tPress 2 To Post This      ");
                System.out.println("\t\t\tPress 3 To Go Back");
                System.out.print("\n\t\tEnter You Choice :- ");
                int i=0;
                while(i==0)
                {
                    i=input(3);
                }
                PrintWriter pw;
                String c=getGender(id)+"";
                String gen="";
                if(c.equalsIgnoreCase("M"))
                gen="his";
                else
                gen="her";
                if(i==1)
                {
                    if(sw==0)
                    {
                        read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                        int count=0;
                        while(read.hasNextLine())
                        {
                            String ide=read.nextLine();
                            String us=read.nextLine();
                            if(us.equalsIgnoreCase(username))
                            {
                                continue;
                            }
                            if(count==1)
                            {
                                boolean blocked=false;
                                if(new File("C:\\Facebook\\"+ide+"\\BlockList.fb").exists())
                                {
                                    block=new Scanner(new File("C:\\Facebook\\"+ide+"\\BlockList.fb"));
                                    while(block.hasNextLine())
                                    {
                                        long bid=Long.parseLong(block.nextLine());
                                        if(bid==id)
                                            blocked=true;
                                    }
                                    block.close();
                                }
                                if(blocked)
                                    continue;
                                f=new File("C:\\Facebook\\"+ide+"\\HomePage.fb");
                                pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                pw.println("\t\t\t"+username+" Updated "+gen+" Status:-");
                                pw.println("\t\t"+post);
                                pw.close();
                            }
                            else
                                count++;
                        }
                        read.close();
                    }
                    else if(sw==1)
                    {
                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                        read.nextLine();
                        while(read.hasNextLine())
                        {
                            String ide=read.nextLine();
                            String us=read.nextLine();
                            if(us.equalsIgnoreCase(username))
                            {
                                continue;
                            }
                            f=new File("C:\\Facebook\\"+ide+"\\HomePage.fb");
                            boolean blocked=false;
                            if(new File("C:\\Facebook\\"+ide+"\\BlockList.fb").exists())
                            {
                                block=new Scanner(new File("C:\\Facebook\\"+ide+"\\BlockList.fb"));
                                while(block.hasNextLine())
                                {
                                    long bid=Long.parseLong(block.nextLine());
                                    if(bid==id)
                                        blocked=true;
                                }
                                block.close();
                            }
                            if(blocked)
                                continue;
                            pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                            pw.println("\t\t\t"+username+" Updated "+gen+" Status:-");
                            pw.println("\t\t"+post);
                            pw.close();
                        }
                        read.close();
                    }
                    id=change(username);
                    pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\HomePage.fb",true)));
                    pw.println("\t\t\tYou Updated your Status:-");
                    pw.println("\t\t"+post);
                    pw.close();
                    Toolkit.getDefaultToolkit().beep();
                    String acb="C:\\Facebook\\"+id+"\\Status.fb";
                    f=new File(acb);
                    pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                    pw.println(post);
                    pw.close();
                    System.out.println("\t\tStatus Updated !!!");
                    sc.nextLine();
                }
                else if(i==2)
                {
                    if(sw==0)
                    {
                        read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                        int count=0;
                        while(read.hasNextLine())
                        {
                            String ide=read.nextLine();
                            String us=read.nextLine();
                            if(us.equalsIgnoreCase(username))
                            {
                                continue;
                            }
                            if(count==1)
                            {
                                f=new File("C:\\Facebook\\"+ide+"\\HomePage.fb");
                                boolean blocked=false;
                                if(new File("C:\\Facebook\\"+ide+"\\BlockList.fb").exists())
                                {
                                    block=new Scanner(new File("C:\\Facebook\\"+ide+"\\BlockList.fb"));
                                    while(block.hasNextLine())
                                    {
                                        long bid=Long.parseLong(block.nextLine());
                                        if(bid==id)
                                            blocked=true;
                                    }
                                    block.close();
                                }
                                if(blocked)
                                    continue;
                                pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                                pw.println("\t\t\t"+username+" Posted :-");
                                pw.println("\t\t"+post);
                                pw.close();
                            }
                            else
                                count++;
                        }
                        read.close();
                    }
                    else if(sw==1)
                    {
                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                        read.nextLine();
                        while(read.hasNextLine())
                        {
                            String ide=read.nextLine();
                            String us=read.nextLine();
                            if(us.equalsIgnoreCase(username))
                            {
                                continue;
                            }
                            f=new File("C:\\Facebook\\"+ide+"\\HomePage.fb");
                            boolean blocked=false;
                            if(new File("C:\\Facebook\\"+ide+"\\BlockList.fb").exists())
                            {
                                block=new Scanner(new File("C:\\Facebook\\"+ide+"\\BlockList.fb"));
                                while(block.hasNextLine())
                                {
                                    long bid=Long.parseLong(block.nextLine());
                                    if(bid==id)
                                        blocked=true;
                                }
                                block.close();
                            }
                            if(blocked)
                                continue;
                            pw=new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
                            pw.println("\t\t\t"+username+" Posted :-");
                            pw.println("\t\t"+post);
                            pw.close();
                        }
                        read.close();
                    }
                    id=change(username);
                    pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\HomePage.fb",true)));
                    pw.println("\t\t\t You Posted :-");
                    pw.println("\t\t"+post);
                    pw.close();
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("\t\tPost Posted !!!");
                    sc.nextLine();
                }
                flag=false;
            }
        }
    }
    //Profile Details
    static void profile() throws Exception
    {
        Scanner sc=new Scanner(System.in);
        boolean flag1=true;
        while(flag1)
        {
            long id=change(username);
            boolean flag2=true;
			clear();
            System.out.println("\t\t\t\t  Profile");
            System.out.println("\t\t\t\t ---------");
            String date=getDate();
            int l=date.length();
            for(int i=1;i<(80-l);i++)
            {
                System.out.print(" ");
            }
            System.out.println(date+"\n");
            System.out.println("  "+username);
            l=username.length()+4;
            for(int i=1;i<=l;i++)
            {
                System.out.print("-");
            }
            System.out.println("\n\n\n\n\n");
            System.out.println("\t\t\tPress 1 for TimeLine");
            System.out.println("\t\t\tPress 2 to View Your Details");
            System.out.println("\t\t\tPress 3 to Go Back");
            System.out.print("\n\t\tEnter You Choice :- ");
            int i=0;
            while(i==0)
            {
                i=input(3);
            }
            if(i==1)
            {
                while(flag2)
                {
					clear();
                    System.out.println("\t\t\t\t  Timeline");
                    System.out.println("\t\t\t\t ----------");
                    date=getDate();
                    l=date.length();
                    for(i=1;i<(80-l);i++)
                    {
                        System.out.print(" ");
                    }
                    System.out.println(date+"\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    System.out.println("\t\t\tPress 1 To View    Timeline");
                    System.out.println("\t\t\tPress 2 To Post in Timeline");
                    System.out.print("\n\t\tEnter You Choice :- ");
                    i=0;
                    while(i==0)
                    {
                        i=input(2);
                    }
                    if(i==1)
                    {
                        String a="C:\\Facebook\\"+id+"\\TimeLine.fb";
                        refreshPage(a,"TimeLine");
                        boolean check=checkEmptyFile("C:\\Facebook\\"+id+"\\TimeLine.fb");
                        if(check)
                        {
                            System.out.println("\t\tYour Timeline Is Empty");
                        }
                        sc.nextLine();
                        flag2=false;
                    }
                    else
                    {
                        System.out.println("\t\t\tInput # To Go Back");
                        System.out.println("\t\t\tInput Your Message");
                        String post=sc.nextLine();
                        post=post.trim();
                        if(post.equals("#"))
                        {
                            flag2=false;
                            continue;
                        }
                        else
                        {
                            String a="C:\\Facebook\\"+id+"\\TimeLine.fb";
                            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(a,true)));
                            pw.println("\t\t\t"+username+" posted:-");
                            pw.println("\t\t"+post+"\n\n");
                            pw.close();
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\tPost Successfull !!!");
                            flag2=false;
                        }
                    }
                }
            }
            else if(i==2)
            {
                viewDetails(id);
                System.out.println();
                System.out.println(" Note: To Change Information Go To Settings");
            }
            else
            {
                flag1=false;
            }
        }
    }
    //Search String s in String main
    static boolean searchString(String main,String s)
    {
        for(int i=s.length();i<=main.length();i++)
        {
            String t=main.substring(i-s.length(),i);
            if(t.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }
    //Friends Menu InterFace
    static void friends()throws Exception
    {
        long id=change(username);
        Scanner sc=new Scanner(System.in);
        boolean flag=true;
        while(flag)
        {   clear();
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("******************     +------------------------------+      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     |           FRIENDS            |      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     +------------------------------+      ******************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.println("*******************************************************************************");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |            1 - Find Friends                  |    ***********");
            System.out.println("************    |            2 - Check Requests                |    ***********");
            System.out.println("************    |            3 - Pokes                         |    ***********");
            System.out.println("************    |            4 - Group                         |    ***********");
            System.out.println("************    |            5 - View All Friends              |    ***********");
            System.out.println("************    |            6 - Back                          |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("*******************************************************************************");
            System.out.print("\n\n\t\tEnter You Choice :- ");
            int i=0;
            while(i==0)
            {
                i=input(6);
            }
            Scanner read;
            if(i==1)
            {
                System.out.println("\t\tEnter The Name Of Your Friend");
                String pnm=sc.nextLine().trim();
                read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                if(pnm.equals(""))
                    continue;
                int c=0;
                boolean flag4=true;
                String list="";
                read.nextLine();
                read.nextLine();
                while(read.hasNextLine())
                {
                    read.nextLine();
                    String un=read.nextLine().trim();
                    if(searchString(un,pnm))
                    {
                        ++c;
                        flag4=false;
                        list+=un+"\n";
                    }
                }
                read.close();
                if(flag4)
                {
                    System.out.println("\t\tNo Such User Exists !!!!");
                    System.out.println("\tPress Enter To Continue...");
                    sc.nextLine();
                    continue;
                }
                String nl[]=new String[c];
                int t1=0;
                for(int j=0;j<c;j++)
                {
                    nl[j]=list.substring(t1,list.indexOf("\n",t1));
                    t1=list.indexOf("\n",t1)+1;
                    System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                }
                System.out.println("\t\t"+(c+1)+" - Back");
                System.out.print("Input a Desired Number To Select User:- ");
                int inp=0;
                while(inp==0)
                {
                    inp=input(c+1);
                }
                if(inp==(c+1))
                {
                    continue;
                }
                String user=nl[--inp].trim();
                long fid=change(user);
                boolean fmenu=true;
                if(fid!=0)
                {
                    if(new File("C:\\Facebook\\"+fid+"\\BlockList.fb").exists())
                    {
                        read=new Scanner(new File("C:\\Facebook\\"+fid+"\\BlockList.fb"));
                        boolean blocked=false;
                        while(read.hasNextLine())
                        {
                            long bid=Long.parseLong(read.nextLine());
                            if(bid==id)
                            {
                                blocked=true;
                            }
                        }
                        read.close();
                        if(blocked)
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\tThis User Blocked You !!!");
                            sc.nextLine();
                            continue;
                        }
                    }
                    if(fid==id)
                    {
                        System.out.println("\t\tYou Can't Find Yourself In This Menu");
                        sc.nextLine();
                        fmenu=false;
                    }
                    if(fmenu)
                    {
                        boolean flag1=true;
                        while(flag1)
                        {
                            clear();
                            System.out.println();
                            for(int l=0;l<(79-user.length())/2;l++)
                            {
                                System.out.print(" ");
                            }
                            System.out.println(user);
                            for(int l=0;l<(79-user.length()-4)/2;l++)
                            {
                                System.out.print(" ");
                            }
                            System.out.print("--");
                            for(int l=0;l<user.length();l++)
                            {
                                System.out.print("-");
                            }
                            System.out.println("--");
                            System.out.println("*******************************************************************************");
                            System.out.println("*******************************************************************************");
                            System.out.println("************    +----------------------------------------------+    ***********");
                            System.out.println("************    |                                              |    ***********");
                            System.out.println("************    |            1 - View Details                  |    ***********");
                            System.out.println("************    |            2 - Message                       |    ***********");
                            System.out.println("************    |            3 - Send Requests                 |    ***********");
                            System.out.println("************    |            4 - TimeLine                      |    ***********");
                            System.out.println("************    |            5 - Back                          |    ***********");
                            System.out.println("************    |                                              |    ***********");
                            System.out.println("************    +----------------------------------------------+    ***********");
                            System.out.println("*******************************************************************************");
                            System.out.println("*******************************************************************************");
                            System.out.print("\n\n\t\tEnter You Choice :- ");
                            int ch=0;
                            while(ch==0)
                            {
                                ch=input(5);
                            }
                            if(ch==1)
                            {
                                Scanner perm=new Scanner(new File("C:\\Facebook\\"+fid+"\\Settings.fb"));
                                String per="";
                                perm.nextLine();
                                perm.nextLine();
                                per=perm.nextLine();
                                perm.close();
                                boolean flag2=false;
                                if(per.equals("2"))
                                {
                                    System.out.println("\t\tYou Don't Have Permissions To View This !!!");
                                    flag2=false;
                                }
                                else if(per.equals("1"))
                                {
                                    perm=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                                    perm.nextLine();
                                    while(perm.hasNextLine())
                                    {
                                        long temp_id=Long.parseLong(perm.nextLine());
                                        perm.nextLine();
                                        if(temp_id==id)
                                        {
                                            flag2=true;
                                        }
                                    }
                                    perm.close();
                                }
                                else
                                {
                                    flag2=true;
                                }
                                if(flag2)
                                {
                                    viewDetails(fid);
                                }
                                else
                                {
                                    sc.nextLine();
                                }
                            }
                            else if(ch==2)
                            {
                                System.out.println("\t\tEnter Your Message:");
                                String mess=sc.nextLine();
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                                pw.println(username+" Messaged You !!! You Can See It Homepage !!");
                                String spa="";
                                for(int spac=0;spac<68;spac++)
                                {
                                    spa+=" ";
                                }
                                spa+="-"+getDate();
                                pw.println(spa);
                                pw.close();
                                pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\HomePage.fb",true)));
                                pw.println("\t\t\t"+username+" Messaged You :- ");
                                pw.println("\t\t"+mess);
                                pw.close();
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tMessage Successfully Sent !!!");
                            }
                            else if(ch==3)
                            {
                                Scanner perm=new Scanner(new File("C:\\Facebook\\"+fid+"\\Settings.fb"));
                                String per="";
                                perm.nextLine();
                                perm.nextLine();
                                perm.nextLine();
                                per=perm.nextLine();
                                perm.close();
                                boolean flag2=false;
                                if(per.equals("1"))
                                {
                                    System.out.println("\t\tYou Don't Have Permissions To Send Requests !!!");
                                    sc.nextLine();
                                    flag2=false;
                                }
                                else
                                {
                                    flag2=true;
                                }
                                if(flag2)
                                {
                                    fid=change(user);
                                    boolean proceed=true;
                                    perm=new Scanner(new File("C:\\Facebook\\"+fid+"\\Requests.fb"));
                                    if(perm.hasNextLine())
                                        perm.nextLine();
                                    while(perm.hasNextLine())
                                    {
                                        String s2=perm.nextLine().trim();
                                        long rid=Long.parseLong(s2);
                                        if(rid==fid)
                                        {
                                            proceed=false;
                                        }
                                    }
                                    perm.close();
                                    if(proceed)
                                    {
                                        perm=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                                        perm.nextLine();
                                        while(perm.hasNextLine())
                                        {
                                            long rid=Long.parseLong(perm.nextLine());
                                            perm.nextLine();
                                            if(rid==fid)
                                            {
                                                proceed=false;
                                            }
                                        }
                                        perm.close();
                                        if(proceed)
                                        {
                                            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Notifications.fb",true)));
                                            pw.println("You Sent A Request To "+user);
                                            String spa="";
                                            for(int spac=0;spac<68;spac++)
                                            {
                                                spa+=" ";
                                            }
                                            spa+="-"+getDate();
                                            pw.println(spa);
                                            pw.close();
                                            pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                                            pw.println(username+" Sent A Request To You!!!");
                                            spa="";
                                            for(int spac=0;spac<68;spac++)
                                            {
                                                spa+=" ";
                                            }
                                            spa+="-"+getDate();
                                            pw.println(spa);
                                            pw.close();
                                            pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Requests.fb",true)));
                                            pw.println(id);
                                            pw.close();
                                            Toolkit.getDefaultToolkit().beep();
                                            System.out.println("\t\tRequest Sucessfully Sent !!!");
                                        }
                                        else
                                        {
                                            System.out.println("\t\t"+user+" Is Already Your Friend");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("\t\tYou Already Sent A Request To "+change(fid)+" !!!");
                                    }
                                    System.out.println("\tPress Enter To Continue...");
                                    sc.nextLine();
                                }
                            }
                            else if(ch==4)
                            {
                                Scanner perm=new Scanner(new File("C:\\Facebook\\"+fid+"\\Settings.fb"));
                                String per="";
                                perm.nextLine();
                                perm.nextLine();
                                perm.nextLine();
                                perm.nextLine();
                                per=perm.nextLine();
                                perm.close();
                                boolean flag2=true;
                                if(per.equals("1"))
                                {
                                    System.out.println("\t\tYou Don't Have Permissions To See Or Post In His/Her Timeline !!!");
                                    sc.nextLine();
                                    flag2=false;
                                }
                                if(flag2)
                                {
                                    boolean flag3=true;
                                    while(flag3)
                                    {
                                        System.out.println("\t1. See "+user +" Timeline");
                                        System.out.println("\t2. Post in "+user +" Timeline");
                                        System.out.println("\t3. Back");
                                        System.out.print("\n\t\tEnter You Choice :- ");
                                        ch=0;
                                        while(ch==0)
                                        {
                                            ch=input(3);
                                        }
                                        if(ch==1)
                                        {
                                            if(!checkEmptyFile("C:\\Facebook\\"+fid+"\\TimeLine.fb"))
                                            refreshPage("C:\\Facebook\\"+fid+"\\TimeLine.fb","TimeLine");
                                            else
                                            {
                                                System.out.println("\t\tTimeline Is Empty !!!");
                                            }
                                            System.out.println("\tPress Enter To Continue");
                                            sc.nextLine();
                                        }
                                        else if(ch==2)
                                        {
                                            System.out.println("\t\t\tInput # To Go Back");
                                            System.out.println("\t\t\tInput Your Message");
                                            String post=sc.nextLine();
                                            post=post.trim();
                                            if(post.equals("#"))
                                            {
                                            }
                                            else
                                            {
                                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\TimeLine.fb",true)));
                                                pw.println("\t\t\t"+username+" posted:-");
                                                pw.println("\t\t"+post+"\n\n");
                                                pw.close();
                                                Toolkit.getDefaultToolkit().beep();
                                                System.out.println("\t\tPost Successfull !!!");
                                            }
                                        }
                                        else
                                        {
                                            flag3=false;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                flag1=false;
                            }
                        }
                    }
                }
                else
                {
                    System.out.println("\t\tNo Friends With Such Name");
                }
            }
            else if(i==2)
            {
                removeDuplicateLine("C:\\Facebook\\"+id+"\\Requests.fb");
                id=change(username);
                boolean flag3=true;
                while(flag3)
                {
                    clear();
                    System.out.println();
                    String top="Requests";
                    System.out.println("\t\t\t\t  "+top);
                    if(!top.equals(""))
                    {
                        System.out.print("\t\t\t\t--");
                        for(int i1=0;i1<top.length();i1++)
                        System.out.print("-");
                        System.out.println("--");
                    }
                    String s=getDate();
                    for(int i1=0;i1<(79-s.length());i1++)
                        System.out.print(" ");
                    System.out.println(s);
                    System.out.println("  "+username);
                    for(int i1=1;i1<=username.length()+4;i1++)
                        System.out.print("-");
                    System.out.println("\n\n\n");
                    if(checkEmptyFile("C:\\Facebook\\"+id+"\\Requests.fb"))
                    {
                        System.out.println("\n\n\n\n\t\tYou Don't Have Any Requests !!!");
                        sc.nextLine();
                        flag3=false;
                    }
                    else
                    {
                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Requests.fb"));
                        if(read.hasNextLine())
                            read.nextLine();
                        int nf=0;
                        while(read.hasNextLine())
                        {
                            nf++;
                            read.nextLine();
                        }
                        read.close();
                        String request[]=new String[nf];
                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Requests.fb"));
                        if(read.hasNextLine())
                            read.nextLine();
                        nf=0;
                        System.out.println("\tInput The Number To Accept Requests:-");
                        while(read.hasNextLine())
                        {
                            long rid=Long.parseLong(read.nextLine());
                            if(change(rid).equals(""))
                            {
                                continue;
                            }
                            request[nf++]=change(rid);
                            System.out.println("\t\t"+nf+" - "+change(rid));
                        }
                        System.out.println("\t\tTotal Requests :- "+nf);
                        System.out.println("\tPress "+(nf+1)+" To Go Back.");
                        System.out.print("\tInput A Number To Accept Request :- ");
                        int no=0;
                        while(no==0)
                        {
                            no=input(nf+1);
                        }
                        if(no==(nf+1))
                        {
                            flag3=false;
                            continue;
                        }
                        String accept=request[nf-1];
                        long fid=change(accept);
                        read=new Scanner(new File("C:\\Facebook\\"+fid+"\\Settings.fb"));
                        read.nextLine();
                        read.nextLine();
                        int per1=Integer.parseInt(read.nextLine());
                        read.close();
                        if(per1<=1)
                        {
                            read=new Scanner(new File("C:\\Facebook\\"+fid+"\\Profile.fb"));
                            read.nextLine();
                            read.nextLine();
                            read.nextLine();
                            read.nextLine();
                            String bir1=read.nextLine();
                            read.close();
                            bir1=bir1.substring(0,5);
                            read=new Scanner(new File("C:\\Facebook\\"+change(username)+"\\Profile.fb"));
                            read.nextLine();
                            read.nextLine();
                            read.nextLine();
                            read.nextLine();
                            String bir2=read.nextLine();
                            read.close();
                            bir2=bir2.substring(0,5);
                            if(bir1.equals(bir2))
                                System.out.println("\tWhat A Coincidence !!! You Two Are Born On Same Day!!!");
                        }
                        //Edit Requests.fb
                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Requests.fb"));
                        String friends="\n";
                        if(read.hasNextLine())
                            read.nextLine();
                        while(read.hasNextLine())
                        {
                            long rid=Long.parseLong(read.nextLine());
                            if(rid==change(accept))
                            {
                                continue;
                            }
                            friends+=rid+"\n";
                        }
                        read.close();
                        //Remove Record From Requests.fb
                        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Requests.fb")));
                        pw.print(friends);
                        pw.close();
                        //Add BirthDay To Yourid\Friends.fb
                        fid=change(accept);
                        read=new Scanner(new File("C:\\Facebook\\"+fid+"\\Profile.fb"));
                        read.nextLine();
                        read.nextLine();
                        read.nextLine();
                        read.nextLine();
                        String birth=read.nextLine();
                        read.close();
                        pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Friends.fb",true)));
                        new Scanner(System.in).nextLine();
                        if(checkEmptyFile("C:\\Facebook\\"+id+"\\Friends.fb"))
                        {
                            pw.println(fid);
                            pw.print(birth);
                        }
                        else
                        {
                            pw.println("\n"+fid);
                            pw.print(birth);
                        }
                        pw.close();
                        //Add BirthDay To Friendid\Friends.fb
                        id=change(username);
                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Profile.fb"));
                        read.nextLine();
                        read.nextLine();
                        read.nextLine();
                        read.nextLine();
                        birth=read.nextLine();
                        read.close();
                        fid=change(accept);
                        pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Friends.fb",true)));
                        if(checkEmptyFile("C:\\Facebook\\"+fid+"\\Friends.fb"))
                        {
                            pw.println(id);
                            pw.print(birth);
                        }
                        else
                        {
                            pw.println("\n"+id);
                            pw.print(birth);
                        }
                        pw.close();
                        //Notifications For You
                        pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Notifications.fb",true)));
                        pw.println("You Accepted "+accept+" Request !!!");
                        String spa="";
                        for(int spac=0;spac<68;spac++)
                        {
                            spa+=" ";
                        }
                        spa+="-"+getDate();
                        pw.println(spa);
                        pw.close();
                        //Notifications For Your Friend
                        pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                        pw.println(username+" Accepted Your Request !!!");
                        spa="";
                        for(int spac=0;spac<68;spac++)
                        {
                            spa+=" ";
                        }
                        spa+="-"+getDate();
                        pw.println(spa);
                        pw.close();
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("\t\tRequest Accepted !!!");
                        System.out.println("\t"+username+" And "+accept +" Are Friends Now !!!");
                        sc.nextLine();
                    }
                }
            }
            else if(i==3)
            {
                clear();
                    System.out.println();
                System.out.println("\t\tPress Enter To Go Back");
                System.out.println("\t\tEnter The Name Of Your Friend To Poke");
                String pnm=sc.nextLine().trim();
                read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                if(pnm.equals(""))
                    continue;
                int c=0;
                boolean flag4=true;
                String list="";
                read.nextLine();
                read.nextLine();
                while(read.hasNextLine())
                {
                    read.nextLine();
                    String un=read.nextLine().trim();
                    if(searchString(un,pnm))
                    {
                        ++c;
                        flag4=false;
                        list+=un+"\n";
                    }
                }
                read.close();
                if(flag4)
                {
                    System.out.println("\t\tNo Such User Exists !!!!");
                    System.out.println("\tPress Enter To Continue...");
                    sc.nextLine();
                    continue;
                }
                String nl[]=new String[c];
                int t1=0;
                for(int j=0;j<c;j++)
                {
                    nl[j]=list.substring(t1,list.indexOf("\n",t1));
                    t1=list.indexOf("\n",t1)+1;
                    System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                }
                System.out.println("\t\t"+(c+1)+" - Back");
                System.out.print("Input a Desired Number To Select User:- ");
                int inp=0;
                while(inp==0)
                {
                    inp=input(c+1);
                }
                if(inp==(c+1))
                    continue;
                String poke=nl[--inp].trim();
                long fid=change(poke);
                if(poke.equalsIgnoreCase(""))
                {
                    continue;
                }
                if(fid==0)
                {
                    System.out.println("\t\tNo Such User Exists !!!");
                    System.out.println("\tPress Enter To Continue...");
                    sc.nextLine();
                    continue;
                }
                else if(fid==change(username))
                {
                    System.out.println("\t\tWhy are You Trying To Poke Yourself ???");
                    System.out.println("\tPress Enter To Continue...");
                    sc.nextLine();
                    continue;
                }
                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                read.nextLine();
                boolean proceed=false;
                while(read.hasNextLine())
                {
                    long rid=Long.parseLong(read.nextLine());
                    read.nextLine();
                    if(rid==fid)
                    {
                        proceed=true;
                    }
                }
                read.close();
                if(proceed)
                {
                    //Notifications For You
                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Notifications.fb",true)));
                    pw.println("You Poked "+poke+" !!!");
                    String spa="";
                    for(int spac=0;spac<68;spac++)
                    {
                        spa+=" ";
                    }
                    spa+="-"+getDate();
                    pw.println(spa);
                    pw.close();
                    //Notifications For Your Friend
                    pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                    pw.println(username+" Poked You !!!");
                    spa="";
                    for(int spac=0;spac<68;spac++)
                    {
                        spa+=" ";
                    }
                    spa+="-"+getDate();
                    pw.println(spa);
                    pw.close();
                        Toolkit.getDefaultToolkit().beep();
                    System.out.println("\t\tYou Poked "+poke+" !!!");
                }
                else
                {
                    System.out.println("\t\t"+poke+" Is Not Your Friend !!!");
                }
                    sc.nextLine();
            }
            else if(i==4)
            {
                id=change(username);
                boolean flag1=true;
                while(flag1)
                {
                    clear();
                    System.out.println();
                    System.out.println("\t\t1 - Create A Group");
                    System.out.println("\t\t2 - Open Existing Group");
                    System.out.println("\t\t3 - Back");
                    System.out.print("\n\t\tEnter You Choice :- ");
                    int in=0;
                    while(in==0)
                    {
                        in=input(3);
                    }
                    clear();
                    System.out.println();
                    if(in==1)
                    {
                        System.out.print("\t\tEnter A Group Name: ");
                        String group=sc.nextLine();
                        if(group.equals(""))
                        {
                            continue;
                        }
                        try
                        {
                            Integer.parseInt(group);
                            System.out.println("\t\tYour Group Name Can't Be Only Number !!!");
                            System.out.println("\tPress Enter To Continue...");
                            sc.nextLine();
                            continue;
                        }
                        catch(NumberFormatException nfe)
                        {
                        }
                        if(group.endsWith(".fb"))
                            group=group.substring(0,group.length()-3);
                        String tempg=group+".fb";
                        if(tempg.equalsIgnoreCase(".fb") ||tempg.equalsIgnoreCase("Rate.fb") || tempg.equalsIgnoreCase("DeadLine.fb") || tempg.equalsIgnoreCase("Friends.fb") || tempg.equalsIgnoreCase("Game.fb") || tempg.equalsIgnoreCase("HomePage.fb") || tempg.equalsIgnoreCase("Notifications.fb") || tempg.equalsIgnoreCase("Profile.fb") || tempg.equalsIgnoreCase("Requests.fb") || tempg.equalsIgnoreCase("Settings.fb") || tempg.equalsIgnoreCase("TimeLine.fb") || tempg.equalsIgnoreCase("online.fb") || tempg.equalsIgnoreCase("BlockList.fb") || tempg.equalsIgnoreCase("Status.fb")  || tempg.equalsIgnoreCase("Problem.fb"))
                        {
                            System.out.println("\t\tThis Name Is Reserved For System Purposes");
                            System.out.println("\t\tPlease Choose Another Name !!!");
                            System.out.println("\tPress Enter To Continue...");
                            sc.nextLine();
                            continue;
                        }
                        if(new File("C:\\Facebook\\"+id+"\\"+group+".fb").exists())
                        {
                            System.out.println("\t\tThis Group Is Already Created !!!");
                            System.out.println("\tPress Enter To Continue...");
                            sc.nextLine();
                            continue;
                        }
                        new File("C:\\Facebook\\"+id+"\\"+group+".fb").createNewFile();
                        boolean flag2=true;
                        String mem=change(username)+"\n";
                        while(flag2)
                        {
                            clear();
                            System.out.println();
                            System.out.println("\tIf List Is Over Press Enter (Without Typing Anything)");
                            System.out.println("\tPlease Input  Your Friends Name To  Add In The Group");
                            String pnm=sc.nextLine().trim();
                            read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                            if(pnm.equals(""))
                                break;
                            int c=0;
                            boolean flag4=true;
                            String list="";
                            read.nextLine();
                            read.nextLine();
                            while(read.hasNextLine())
                            {
                                read.nextLine();
                                String un=read.nextLine().trim();
                                if(searchString(un,pnm))
                                {
                                    ++c;
                                    flag4=false;
                                    list+=un+"\n";
                                }
                            }
                            read.close();
                            if(flag4)
                            {
                                System.out.println("\t\tNo Such User Exists !!!!");
                                System.out.println("\tPress Enter To Continue...");
                                sc.nextLine();
                                continue;
                            }
                            String nl[]=new String[c];
                            int t1=0;
                            for(int j=0;j<c;j++)
                            {
                                nl[j]=list.substring(t1,list.indexOf("\n",t1));
                                t1=list.indexOf("\n",t1)+1;
                                System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                            }
                            System.out.println("\t\t"+(c+1)+" - Back");
                            System.out.print("Input a Desired Number To Select User:- ");
                            int inp=0;
                            while(inp==0)
                            {
                                inp=input(c+1);
                            }
                            if(inp==(c+1))
                                continue;
                            String temp=nl[--inp].trim();
                            if(temp.equals(""))
                            {
                                flag2=false;
                                continue;
                            }
                            else if(temp.equalsIgnoreCase(username))
                            {
                                System.out.println("\t\tYour Name Will Be Automatically Added !!!\n\t\tYou Need Not To Add Your Name");
                                System.out.println("\tPress Enter To Continue...");
                                sc.nextLine();
                                continue;
                            }
                            else
                            {
                                read=new Scanner(new File("C:\\Facebook\\"+id+"\\"+group+".fb"));
                                while(read.hasNextLine())
                                {
                                    if(change(temp)==Long.parseLong(read.nextLine()))
                                    {
                                        System.out.println("\t\tThis User Is Already In This Group");
                                        System.out.println("\tPress Enter To Continue...");
                                        sc.nextLine();
                                        continue;
                                    }
                                }
                                read.close();
                                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                                read.nextLine();
                                long fid=change(temp);
                                if(fid==0)
                                {
                                    System.out.println("\t\tNo Such User Exists !!!");
                                        System.out.println("\tPress Enter To Continue...");
                                    sc.nextLine();
                                    continue;
                                }
                                boolean proceed=false;
                                while(read.hasNextLine())
                                {
                                    long rid=Long.parseLong(read.nextLine());
                                    read.nextLine();
                                    if(rid==fid)
                                    {
                                        proceed=true;
                                    }
                                }
                                read.close();
                                if(proceed)
                                {
                                    boolean blocked=false;
                                    if(new File("C:\\Facebook\\"+fid+"\\BlockList.fb").exists())
                                    {
                                        Scanner block=new Scanner(new File("C:\\Facebook\\"+fid+"\\BlockList.fb"));
                                        while(block.hasNextLine())
                                        {
                                            long bid=Long.parseLong(block.nextLine());
                                            if(bid==id)
                                            blocked=true;
                                        }
                                        block.close();
                                    }
                                    if(blocked)
                                    {
                                        System.out.println("\t\t"+temp+" Blocked You !!!");
                                        System.out.println("\tPress Enter To Continue...");
                                        sc.nextLine();
                                        continue;
                                    }
                                    else
                                    {
                                        mem+=fid+"\n";
                                        System.out.println("\t\t"+temp+" Added !!!");
                                        System.out.println("\tPress Enter To Continue...");
                                        sc.nextLine();
                                        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                                        pw.println(username+" Added You To The Group "+group);
                                        String spa="";
                                        for(int spac=0;spac<68;spac++)
                                        {
                                            spa+=" ";
                                        }
                                        spa+="-"+getDate();
                                        pw.println(spa);
                                        pw.close();
                                    }
                                }
                                else
                                {
                                    System.out.println("\t\t"+temp+" Is Not Your Friend !!!");
                                        System.out.println("\tPress Enter To Continue...");
                                    sc.nextLine();
                                    continue;
                                }
                            }
                        }
                        if(mem.equals(""))
                        {
                            continue;
                        }
                        else
                        {
                            if(mem.length()!=0)
                            mem=mem.substring(0,mem.length()-1);
                            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\"+group+".fb")));
                            pw.print(mem);
                            pw.close();
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\t\tFriends Successfully Added !!!");
                            System.out.println("\tPress Enter To Continue...");
                            sc.nextLine();
                            flag2=false;
                        }
                    }
                    else if(in==2)
                    {
                        System.out.print("\t\tEnter Your Group Name: ");
                        String pnm=sc.nextLine().trim();
                        if(pnm.equals(""))
                            continue;
                        String list="";
                        int c=0;
                        boolean flag4=true;
                        File[] files=new File("C:\\Facebook\\"+id+"\\").listFiles();
                        for(File f:files)
                        {
                            if(searchString(f.getName(),pnm))
                            {
                                list+=f.getName().substring(0,f.getName().length()-3)+"\n";
                                ++c;
                                flag4=false;
                            }
                        }
                        if(flag4)
                        {
                            System.out.println("\t\tNo Such Group Exists !!!!");
                            System.out.println("\tPress Enter To Continue...");
                            sc.nextLine();
                            continue;
                        }
                        String nl[]=new String[c];
                        int t1=0;
                        System.out.println("Input a Desired Number To Select Group:-");
                        for(int j=0;j<c;j++)
                        {
                            nl[j]=list.substring(t1,list.indexOf("\n",t1));
                            t1=list.indexOf("\n",t1)+1;
                            System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                        }
                        System.out.println("\t\t"+(c+1)+" - Back");
                        int inp=0;
                        while(inp==0)
                        {
                            inp=input(c+1);
                        }
                        if(inp==(c+1))
                            continue;
                        String group=nl[--inp].trim();
                        if(new File("C:\\Facebook\\"+id+"\\"+group+".fb").exists())
                        {
                            boolean flag2=true;
                            while(flag2)
                            {
                                clear();
                                System.out.println();
                                System.out.println("\t\t1.Add Friends");
                                System.out.println("\t\t2.Delete Group");
                                System.out.println("\t\t3.See Members");
                                System.out.println("\t\t4.Send Message");
                                System.out.println("\t\t5.Remove Friend");
                                System.out.println("\t\t6.Back");
                                System.out.print("\n\t\tEnter You Choice :- ");
                                int ch=0;
                                while(ch==0)
                                {
                                    ch=input(6);
                                }
                                if(ch==1)
                                {
                                    boolean flag3=true;
                                    String mem=change(username)+"\n";
                                    while(flag3)
                                    {
                                        clear();
                                        System.out.println();
                                        System.out.println("\tIf List Is Over Press Enter (Without Typing Anything)");
                                        System.out.println("\tPlease Input  Your Friends Name To  Add In The Group");
                                        pnm=sc.nextLine().trim();
                                        read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                                        if(pnm.equals(""))
                                            break;
                                        c=0;
                                        flag4=true;
                                        list="";
                                        read.nextLine();
                                        read.nextLine();
                                        while(read.hasNextLine())
                                        {
                                            read.nextLine();
                                            String un=read.nextLine().trim();
                                            if(searchString(un,pnm))
                                            {
                                                ++c;
                                                flag4=false;
                                                list+=un+"\n";
                                            }
                                        }
                                        read.close();
                                        if(flag4)
                                        {
                                            System.out.println("\t\tNo Such User Exists !!!!");
                                            System.out.println("\tPress Enter To Continue...");
                                            sc.nextLine();
                                            continue;
                                        }
                                        String nl1[]=new String[c];
                                        t1=0;
                                        for(int j=0;j<c;j++)
                                        {
                                            nl1[j]=list.substring(t1,list.indexOf("\n",t1));
                                            t1=list.indexOf("\n",t1)+1;
                                            System.out.println("\t\t"+(j+1)+" - "+nl1[j]);
                                        }
                                        System.out.println("\t\t"+(c+1)+" - Back");
                                        System.out.print("Input a Desired Number To Select User:- ");
                                        inp=0;
                                        while(inp==0)
                                        {
                                            inp=input(c+1);
                                        }
                                        if(inp==(c+1))
                                            continue;
                                        String temp=nl1[--inp].trim();
                                        if(temp.equals(""))
                                        {
                                            flag3=false;
                                            continue;
                                        }
                                        else if(temp.equalsIgnoreCase(username))
                                        {
                                            System.out.println("\t\tYour Name Will Be Automatically Added !!!\n\t\tYou Need Not To Add Your Name");
                                            System.out.println("\tPress Enter To Continue...");
                                            sc.nextLine();
                                            continue;
                                        }
                                        else
                                        {
                                            read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                                            read.nextLine();
                                            long fid=change(temp);
                                            if(fid==0)
                                            {
                                                System.out.println("\t\tNo Such User Exists !!!");
                                                    System.out.println("\tPress Enter To Continue...");
                                                sc.nextLine();
                                                continue;
                                            }
                                            boolean proceed=false;
                                            while(read.hasNextLine())
                                            {
                                                long rid=Long.parseLong(read.nextLine());
                                                read.nextLine();
                                                if(rid==fid)
                                                {
                                                    proceed=true;
                                                }
                                            }
                                            read.close();
                                            read=new Scanner(new File("C:\\Facebook\\"+id+"\\"+group+".fb"));
                                            while(read.hasNextLine())
                                            {
                                                if(change(temp)==Long.parseLong(read.nextLine()))
                                                {
                                                    System.out.println("\t\tThis User Is Already In This Group");
                                                    System.out.println("\tPress Enter To Continue...");
                                                    sc.nextLine();
                                                    continue;
                                                }
                                            }
                                            read.close();
                                            if(proceed)
                                            {
                                                boolean blocked=false;
                                                if(new File("C:\\Facebook\\"+fid+"\\BlockList.fb").exists())
                                                {
                                                    Scanner block=new Scanner(new File("C:\\Facebook\\"+fid+"\\BlockList.fb"));
                                                    while(block.hasNextLine())
                                                    {
                                                        long bid=Long.parseLong(block.nextLine());
                                                        if(bid==id)
                                                            blocked=true;
                                                    }
                                                    block.close();
                                                }
                                                if(blocked)
                                                {
                                                    System.out.println("\t\t"+temp+" Blocked You !!!");
                                                    System.out.println("\tPress Enter To Continue...");
                                                    sc.nextLine();
                                                    continue;
                                                }
                                                else
                                                {
                                                    mem+=fid+"\n";
                                                    System.out.println("\t\t"+temp+" Added !!!");
                                                    System.out.println("\tPress Enter To Continue...");
                                                    sc.nextLine();
                                                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                                                    pw.println(username+" Added You To The Group "+group);
                                                    String spa="";
                                                    for(int spac=0;spac<68;spac++)
                                                    {
                                                        spa+=" ";
                                                    }
                                                    spa+="-"+getDate();
                                                    pw.println(spa);
                                                    pw.close();
                                                }
                                            }
                                            else
                                            {
                                                System.out.println("\t\t"+temp+" Is Not Your Friend !!!");
                                                    System.out.println("\tPress Enter To Continue...");
                                                sc.nextLine();
                                                continue;
                                            }
                                        }
                                    }
                                    if(mem.equals(""))
                                    {
                                        continue;
                                    }
                                    else
                                    {
                                        if(mem.length()!=0)
                                        mem=mem.substring(0,mem.length()-1);
                                        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\"+group+".fb",true)));
                                        pw.print("\n"+mem);
                                        pw.close();
                                        Toolkit.getDefaultToolkit().beep();
                                        System.out.println("\t\t\tFriends Successfully Added !!!");
                                        sc.nextLine();
                                        flag3=false;
                                    }
                                }
                                else if(ch==2)
                                {
                                    boolean flag3=true;
                                    while(flag3)
                                    {
                                        System.out.println("\t\tAre You Sure To Delete This Group??? (y/n)");
                                        String c1=sc.nextLine();
                                        if(c1.trim().equalsIgnoreCase("Y"))
                                        {
                                            flag3=false;
                                            File f=new File("C:\\Facebook\\"+id+"\\"+group+".fb");
                                            f.delete();
                                            Toolkit.getDefaultToolkit().beep();
                                            System.out.println("\t\t\tGroup Deleted !!!");
                                            sc.nextLine();
                                            flag2=false;
                                        }
                                        else if(c1.trim().equalsIgnoreCase("N"))
                                        {
                                            flag3=false;
                                            continue;
                                        }
                                        else
                                        System.out.println("Please Give A Valid Input (Y/N)");
                                    }
                                }
                                else if(ch==3)
                                {
                                    refreshPage("C:\\Facebook\\"+id+"\\"+group+".fb",group);
                                    sc.nextLine();
                                }
                                else if(ch==4)
                                {
                                    System.out.println("\t\tPlease Press Enter Without Typing Anything To Go Back");
                                    System.out.println("\t\t\tPlease Type Your Message");
                                    String post=sc.nextLine();
                                    post=post.trim();
                                    if(post.equals(""))
                                    {
                                        continue;
                                    }
                                    else
                                    {
                                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\"+group+".fb"));
                                        while(read.hasNextLine())
                                        {
                                            long fid=Long.parseLong(read.nextLine());
                                            if(fid!=0)
                                            {
                                                if(new File("C:\\Facebook\\"+fid+"\\BlockList.fb").exists())
                                                {
                                                    read=new Scanner(new File("C:\\Facebook\\"+fid+"\\BlockList.fb"));
                                                    boolean blocked=false;
                                                    while(read.hasNextLine())
                                                    {
                                                        long bid=Long.parseLong(read.nextLine());
                                                        if(bid==id)
                                                        {
                                                            blocked=true;
                                                        }
                                                    }
                                                    read.close();
                                                    if(blocked)
                                                    {
                                                        System.out.println("\t\t"+change(fid)+" Blocked You !!!");
                                                        System.out.println("\tPress Enter To Continue...");
                                                        sc.nextLine();
                                                        continue;
                                                    }
                                                }
                                                //Friendsid\Notifications.fb
                                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                                                pw.println(username+" Sent A Post To You!!!\n\t\tYou can See It In HomePage");
                                                String spa="";
                                                for(int spac=0;spac<68;spac++)
                                                {
                                                    spa+=" ";
                                                }
                                                spa+="-"+getDate();
                                                pw.println(spa);
                                                pw.close();
                                                //Friendsid\HomePage.fb
                                                pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\HomePage.fb",true)));
                                                pw.println("\t\t\t"+username+" Posted :- ");
                                                pw.println("\t\t"+post);
                                                pw.close();
                                            }
                                            else
                                            {
                                                System.out.println("\t\t This User Has Removed The Account !!!");
                                                sc.nextLine();
                                            }
                                        }
                                        read.close();
                                    }
                                }
                                else if(ch==5)
                                {
                                    boolean flag3=true;
                                    while(flag3)
                                    {
                                        System.out.println("\tPress Enter Without Typing Anything To Go Back");
                                        System.out.println("\tPlease Input Your Friends Name To Remove From The Group");
                                        pnm=sc.nextLine().trim();
                                        read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                                        if(pnm.equals(""))
                                            continue;
                                        c=0;
                                        flag4=true;
                                        list="";
                                        read.nextLine();
                                        read.nextLine();
                                        while(read.hasNextLine())
                                        {
                                            read.nextLine();
                                            String un=read.nextLine().trim();
                                            if(searchString(un,pnm))
                                            {
                                                ++c;
                                                flag4=false;
                                                list+=un+"\n";
                                            }
                                        }
                                        read.close();
                                        if(flag4)
                                        {
                                            System.out.println("\t\tNo Such User Exists !!!!");
                                            System.out.println("\tPress Enter To Continue...");
                                            sc.nextLine();
                                            continue;
                                        }
                                        String nl1[]=new String[c];
                                        t1=0;
                                        for(int j=0;j<c;j++)
                                        {
                                            nl1[j]=list.substring(t1,list.indexOf("\n",t1));
                                            t1=list.indexOf("\n",t1)+1;
                                            System.out.println("\t\t"+(j+1)+" - "+nl1[j]);
                                        }
                                        System.out.println("\t\t"+(c+1)+" - Back");
                                        System.out.print("Input a Desired Number To Select User:- ");
                                        int inp1=0;
                                        while(inp1==0)
                                        {
                                            inp1=input(c+1);
                                        }
                                        if(inp1==(c+1))
                                        {
                                            flag3=false;
                                            continue;
                                        }
                                        String temp=nl1[--inp1].trim();
                                        if(temp.trim().equalsIgnoreCase(username))
                                        {
                                            System.out.println("\t\tYour Can't Remove Yourself !!!\n\t\tYou Are The Administrator Of Group "+group);
                                            System.out.println("\tPress Enter To Continue...");
                                            sc.nextLine();
                                            continue;
                                        }
                                        else
                                        {
                                            read=new Scanner(new File("C:\\Facebook\\"+id+"\\"+group+".fb"));
                                            long fid=change(temp);
                                            if(fid==0)
                                            {
                                                System.out.println("\t\tNo Such User Exists !!!");
                                                System.out.println("\tPress Enter To Continue...");
                                                sc.nextLine();
                                                continue;
                                            }
                                            boolean proceed=false;
                                            String newgroup=change(username)+"\n";
                                            while(read.hasNextLine())
                                            {
                                                long rid=Long.parseLong(read.nextLine());
                                                if(rid==fid)
                                                {
                                                    proceed=true;
                                                    continue;
                                                }
                                                else
                                                {
                                                    newgroup+=rid+"\n";
                                                }
                                            }
                                            read.close();
                                            if(proceed)
                                            {
                                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+fid+"\\Notifications.fb",true)));
                                                pw.println(username+" Removed You From The Group "+group);
                                                String spa="";
                                                for(int spac=0;spac<68;spac++)
                                                {
                                                    spa+=" ";
                                                }
                                                spa+="-"+getDate();
                                                pw.println(spa);
                                                pw.close();
                                                pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\"+group+".fb")));
                                                pw.print(newgroup);
                                                pw.close();
                                                Toolkit.getDefaultToolkit().beep();
                                                System.out.println("\t\t"+temp+" Removed From Group "+ group);
                                                System.out.println("\tPress Enter To Continue...");
                                                sc.nextLine();
                                            }
                                            else
                                            {
                                                System.out.println(temp+" Is Not In Group "+group+" !!!");
                                                System.out.println("\tPress Enter To Continue...");
                                                sc.nextLine();
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    flag2=false;
                                }
                            }
                        }
                    }
                    else
                    {
                        flag1=false;
                    }
                }
            }
            else if(i==5)
            {
                clear();
                    System.out.println();
                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                read.nextLine();
                int no=0;
                while(read.hasNextLine())
                {
                    long rid=Long.parseLong(read.nextLine());
                    read.nextLine();
                    if(change(rid).equals(""))
                    {
                        continue;
                    }
                    System.out.println("\t\t"+change(rid));
                    no++;
                }
                read.close();
                System.out.println("\t\t\t Total Friends :- " + no);
                sc.nextLine();
            }
            else
            {
                flag=false;
                return ;
            }
        }
    }
    //Checks Valid Username Or Not
    static boolean checkValidUsername(String user)throws Exception
    {
        boolean flag=true;
        boolean check=true;
        String s="";
        user=user.trim();
        if(user.equals(""))
        {
            flag=false;
            s+="\tUsername Can't be Null \n";
        }
        int sp=0;
        if(user.length()>50)
        {
            s+="\tPlease Input Your Original Name(below 50 chars).\n";
            flag=false;
        }
        for(int i=0;i<user.length();i++)
        {
            if(!Character.isLetter(user.charAt(i)))
            {
                if(!Character.isSpaceChar(user.charAt(i)))
                {
                    check=false;
                    flag=false;
                }
                else
                    sp++;
            }
        }
        if(sp<1)
        {
            flag=false;
            s+="\tPlease Input First And Last Name\n";
        }
        if(!check)
            s+="\tUsername must have Only Alphabets\n";
        long l=change(user);
        if(l!=0)
        {
            flag=false;
            s+="\tUsername Exists Please Input Another Username Or Add Initials";
        }
        if(!s.equals(""))
        {
            System.out.println("  Invalid Username");
            System.out.println("----------------------");
            System.out.println(s);
        }
        return flag;
    }
    //Checks Valid Email Or Not
    static boolean checkValidEmail(String e_mail)throws Exception
    {
        boolean flag=true;
        String s="";
        e_mail=e_mail.trim();
        if(e_mail.equals(""))
        {
            flag=false;
            s+="\tEmail Address Can't be Null \n";
        }
        else
        {
            File f=new File("C:\\Facebook\\fbmain.fb");
            int dot=0,at=0;
            boolean sym=false;
            Scanner read=new Scanner(f);
            for(int i=0;i<e_mail.length();i++)
            {
                if(Character.isLetterOrDigit(e_mail.charAt(i)) || e_mail.charAt(i)=='@' || e_mail.charAt(i)=='.')
                {
                    if(e_mail.charAt(i)=='@')
                    at++;
                    else if(e_mail.charAt(i)=='.')
                    dot++;
                }
                else
                {
                    sym=true;
                    flag=false;
                    break;
                }
            }
            if(dot!=1)
            {
                s+="\tEmail must have only one \'.\' \n";
                flag=false;
            }
            else
            {
            }
            if(e_mail.charAt(e_mail.indexOf("@")+1)=='.')
            {
                flag=false;
                s+="\tFaulty Email Detected!!! @ can't be succeeded by \'.\'\n";
              }
            if(e_mail.indexOf(".",e_mail.indexOf("@"))==-1)
            {
                flag=false;
                s+="\tIn Email @ must be first then your website name .com\n";
              }
            if(at!=1)
            {               flag=false;
                                      s+="\tEmail must have only one \'@\' \n";
            }
            if(sym)
            s+="\tEmail can't have Special Characters except @ and . \n";
            int f1=e_mail.indexOf('@');
            int f2=e_mail.indexOf('.');
            if(!(e_mail.endsWith("gmail.com") || e_mail.endsWith("facebook.com")))
            {
                flag=false;
                s+="\tOnly Account of Google is Accepted.\n";
            }
            String a=read.nextLine();
            while(read.hasNextLine())
            {
                if(e_mail.equals(read.nextLine()))
                {
                    flag=false;
                    s+="\tSuch Email Exists Please Give Another Email !!\n";
                }
                a=read.nextLine();
                a=read.nextLine();
            }
            read.close();
        }
          if(!s.equals(""))
          {
              System.out.println("  Invalid Email Address ");
              System.out.println("-------------------------");
              System.out.println(s);
          }
          return flag;
    }
    //Checks Valid Phone Number Or Not
    static boolean checkValidNumber(String phn1)throws Exception
    {
        boolean flag=true;
        String s="";
        phn1=phn1.trim();
        boolean check=false;
        for(int i=0;i<phn1.length();i++)
        {
            if(!Character.isDigit(phn1.charAt(i)))
            {
                check=true;
            }
        }
        if(check)
        {
            flag=false;
            s+="\tPlease Input A Valid Phone Number.\n\tPlease Don't Include +91 in Number.\n";
        }
        if(phn1.length()!=10)
        {
            flag=false;
            s+="\tPlease Input A Valid Phone Number having 10 Digits.\n";
        }
        Scanner read=new Scanner(new File("C:\\Facebook\\Username.fb"));
        boolean next=read.hasNextLine();
        while(next)
        {
            long a=Long.parseLong(read.nextLine());
            String br=read.nextLine();
            File f=new File("C:\\Facebook\\"+a+"\\Profile.fb");
            String same="";
            if(f.exists())
            {
                Scanner sc1=new Scanner(f);
                for(int i=1;i<=6;i++)
                {
                    same=sc1.nextLine();
                }
                if(phn1.equals(same))
                {
                    s+="\tThis Phone Has Aready Used To Activate Another Account\n";
                    flag=false;
                    next=false;
                    continue;
                }
            }
            next=read.hasNextLine();
        }
        read.close();
        if(!s.equals(""))
        {
            System.out.println("  Invalid Phone Number ");
            System.out.println("-------------------------");
            System.out.println(s);
        }
        return flag;
    }
    //Checks Valid Gender   Or Not
    static boolean checkValidGender(String gen1)throws Exception
    {
        boolean flag=true;
        String s="";
        gen1=gen1.trim();
        if(gen1.equals(""))
        {
            flag=false;
            s+="\tGender can't be null.\n";
        }
        if(!(gen1.equalsIgnoreCase("M") || gen1.equalsIgnoreCase("F")))
        {
            flag=false;
            s+="You can Only Input M Or F .\n";
        }
        if(!s.equals(""))
        {
            System.out.println("  Invalid Gender ");
            System.out.println("-------------------");
            System.out.println(s);
        }
        return flag;
    }
    //Checks Valid Password Or Not
    static boolean checkValidPassword(String password)throws Exception
    {
        boolean flag=true;
        String s="";
        password=password.trim();
        if(password.length()<4)
        {
            flag=false;
            s+="\tPassword Too Short Weak Password!!\n";
        }
        if(password.equals(""))
        {
            flag=false;
            s+="\tPassword Can't be Null Or Spaces Only\n";
        }
        if(!s.equals(""))
        {
            System.out.println("    Invalid Password ");
            System.out.println("-------------------------");
            System.out.println(s);
        }
        return flag;
    }
    //Checks Valid Birthday Or Not
    static boolean checkValidDate(String birth)throws Exception
    {
        boolean flag=true;
        String s="";
        GregorianCalendar dat=new GregorianCalendar();
        try
        {
            int d,m,y,day=0,ly,py;
            int ns=0;
            for(int i=0;i<birth.length();i++)
            {
                if(birth.charAt(i)=='/')
                    ns++;
            }
            if(ns!=2)
            {
                System.out.println("  Invalid BirthDay ");
                System.out.println("---------------------");
                System.out.println("\tPlease Enter In Correct Format As Shown!!!");
                return false;
            }
            d=Integer.parseInt(birth.substring(0,birth.indexOf('/')));
            m=Integer.parseInt(birth.substring(birth.indexOf('/')+1,birth.lastIndexOf('/')));
            y=Integer.parseInt(birth.substring(birth.lastIndexOf('/')+1));
            py=dat.get(Calendar.YEAR);
            py=py-12;
            ly=py-90;
            if(y>ly && y<=py)
            {
                if(m>=1 && m<=12)
                {
                    switch(m)
                    {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:day=31;break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:day=30;break;
                        case 2:
                        {
                            if (y%4==0)
                                day= 29;
                            else
                                day= 28;
                            break;
                        }
                    }
                    if(d>=1 && d<=day)
                        {}
                    else
                    {
                        flag=false;
                        s+="\tPlease Input A Valid Day\n";
                    }
                }
                else
                {
                    flag=false;
                    s+="\tPlease Input A Valid Month\n";
                }
            }
            else if(y>py)
            {
                flag=false;
                s+="\tYou Must be Atleast 12 years to Open A Facebook Account.\n";
            }
            else if(y<ly)
            {
                flag=false;
                s+="\tPlease Input Your Original Birthday In Facebook Account.\n";
            }
        }
        catch(NumberFormatException nfe)
        {
            flag=false;s+="\tPlease Input A Date.\n";
        }
        if(!s.equals(""))
        {
            System.out.println("  Invalid BirthDay ");
            System.out.println("---------------------");
            System.out.println(s);
        }
        return flag;
    }
    //Settings Menu Interface
    static void settings()throws Exception
    {
        long id=change(username);
        Scanner sc=new Scanner(System.in);
        boolean flagm=true;
        while(flagm)
        {
            clear();
            System.out.println();
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("******************     +------------------------------+      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     |           SETTINGS           |      ******************\n");
            System.out.print("******************     |                              |      ******************\n");
            System.out.print("******************     +------------------------------+      ******************\n");
            System.out.print("*******************************************************************************\n");
            System.out.print("*******************************************************************************\n");
            System.out.println("*******************************************************************************");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |            1 - General                       |    ***********");
            System.out.println("************    |            2 - Security                      |    ***********");
            System.out.println("************    |            3 - Privacy                       |    ***********");
            System.out.println("************    |            4 - Blocking                      |    ***********");
            System.out.println("************    |            5 - Report A Problem              |    ***********");
            System.out.println("************    |            6 - Support Inbox                 |    ***********");
            System.out.println("************    |            7 - Back                          |    ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("*******************************************************************************");
            System.out.print("\n\t\tPress Any Option To Continue :-");
            int n=0;
            while(n==0)
            {
                n=input(7);
            }
            if(n==1)
            {
                boolean flag1=true;
                while(flag1)
                {
                    clear();
                    System.out.println();
                    System.out.println("\t\t\t\t  General");
                    System.out.print("\t\t\t\t--");
                    for(int i=0;i<"General".length();i++)
                        System.out.print("-");
                    System.out.println("--");
                    String get=getDate();
                    for(int i=0;i<(79-get.length());i++)
                        System.out.print(" ");
                    System.out.println(get);
                    System.out.println("  "+username);
                    for(int i=1;i<=username.length()+4;i++)
                        System.out.print("-");
                    System.out.println("\n\n\n\n");
                    Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Profile.fb"));
                    String ide=read.nextLine();
                    String email=read.nextLine();
                    String pass=read.nextLine();
                    String gen=read.nextLine();
                    String dob=read.nextLine();
                    String phn=read.nextLine();
                    String doo=read.nextLine();
                    read.close();
                    String pa="";
                    for (int i=0;i<pass.length();i++)
                    {
                        pa+="X";
                    }
                    System.out.println("\t\t1.Name:- "+username);
                    System.out.println("\t\t2.E-Mail:- "+email);
                    System.out.println("\t\t3.Phone:- "+phn);
                    System.out.println("\t\t4.Gender:- "+gen);
                    System.out.println("\t\t5.Password:- "+pa);
                    System.out.println("\t\t6.Date Of Birth:- "+dob);
                    System.out.println("\t\t7.Back");
                    System.out.print("\n\t\tEnter Any Number As Your Choice To Change It:- ");
                    int ch=0;
                    while(ch==0)
                    {
                        ch=input(7);
                    }
                    boolean flag=true;
                    String s="";
                    if(ch==1)
                    {
                        boolean flag2=true;
                        while(flag2)
                        {
                            System.out.print("\t\tEnter New Username:- ");
                            String user=sc.nextLine();
                            flag=checkValidUsername(user);
                            if(!flag)
                            sc.nextLine();
                            if(flag)
                            {
                                read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                                s="";
                                while(read.hasNextLine())
                                {
                                    long nid=Long.parseLong(read.nextLine());
                                    String name=read.nextLine();
                                    if(nid==id)
                                    {
                                        name=user;
                                    }
                                    s=s+nid+"\n"+name+"\n";
                                }
                                read.close();
                                if(s.length()!=0)
                                s=s.substring(0,s.length()-1);
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\Username.fb")));
                                pw.print(s);
                                pw.close();
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tName Successfully Changed");
                                System.out.println("\t\tIf You Don't See Changes Any Where Restart The Application");
                                sc.nextLine();
                                flag2=false;
                                username=user;
                            }
                            else
                            {
                                System.out.println("\tPlease Input Correct Username");
                            }
                        }
                    }
                    else if(ch==2)
                    {
                        boolean flag2=true;
                        while(flag2)
                        {
                            System.out.print("\t\tEnter New E-mail:- ");
                            String e_mail=sc.nextLine();
                            flag=checkValidEmail(e_mail);
                            if(!flag)
                            sc.nextLine();
                            if(flag)
                            {
                                read=new Scanner(new File("C:\\Facebook\\fbmain.fb"));
                                s="\n";
                                read.nextLine();
                                while(read.hasNextLine())
                                {
                                    String email1=read.nextLine();
                                    String pass1=read.nextLine();
                                    long nid=Long.parseLong(read.nextLine());
                                    if(nid==id)
                                    {
                                        email1=e_mail;
                                        email=e_mail;
                                        s=s+email1+"\n"+pass1+"\n"+nid+"\n";
                                    }
                                    if(s.length()!=0)
                                    s=s.substring(0,s.length()-1);
                                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\fbmain.fb")));
                                    pw.print(s);
                                    pw.close();
                                    pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Profile.fb")));
                                    pw.println(id);
                                    pw.println(email);
                                    pw.println(pass);
                                    pw.println(gen);
                                    pw.println(dob);
                                    pw.println(phn);
                                    pw.print(doo);
                                    pw.close();
                                    Toolkit.getDefaultToolkit().beep();
                                    System.out.println("\t\tEmail Successfully Changed");
                                    System.out.println("\t\tIf You Don't See Changes Restart The Application");
                                    sc.nextLine();
                                    flag2=false;
                                }
                                read.close();
                            }
                            else
                            {
                                System.out.println("\tPlease Input Correct Email");
                            }
                        }
                    }
                    else if(ch==3)
                    {
                        boolean flag2=true;
                        while(flag2)
                        {
                            System.out.print("\t\tEnter New Phone Number:- ");
                            String phn1=sc.nextLine();
                            flag=checkValidNumber(phn1);
                            if(!flag)
                            sc.nextLine();
                            if(flag)
                            {
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Profile.fb")));
                                pw.println(id);
                                pw.println(email);
                                pw.println(pass);
                                pw.println(gen);
                                pw.println(dob);
                                pw.println(phn1);
                                pw.print(doo);
                                pw.close();
                                phn=phn1;
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tPhone Number Successfully Changed");
                                System.out.println("\t\tIf You Don't See Changes Restart The Application");
                                sc.nextLine();
                                flag2=false;
                            }
                            else
                            {
                                System.out.println("\tPlease Input Correct Number");
                            }
                        }
                    }
                    else if(ch==4)
                    {
                        boolean flag2=true;
                        while(flag2)
                        {
                            System.out.print("\t\tEnter Your Gender (M\\F):- ");
                            String gen1=sc.nextLine();
                            flag=checkValidGender(gen1);
                            if(!flag)
                            sc.nextLine();
                            if(flag)
                            {
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Profile.fb")));
                                pw.println(id);
                                pw.println(email);
                                pw.println(pass);
                                pw.println(gen1);
                                pw.println(dob);
                                pw.println(phn);
                                pw.print(doo);
                                pw.close();
                                gen=gen1;
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tGender Successfully Changed !!!");
                                System.out.println("\t\tIf You Don't See Changes Restart The Application");
                                sc.nextLine();
                                flag2=false;
                            }
                            else
                            {
                                System.out.println("\tPlease Input Correct Data");
                            }
                        }
                    }
                    else if(ch==5)
                    {
                        System.out.print("Input Your Current Password:- ");
                        String tpass=sc.nextLine();
                        if(tpass.equals(pass))
                        {
                            boolean flag2=true;
                            while(flag2)
                            {
                                System.out.print("\t\tEnter New Password:- ");
                                String password=sc.nextLine();
                                s="";
                                flag=checkValidPassword(password);
                                if(!flag)
                                sc.nextLine();
                                if(flag)
                                {
                                    read=new Scanner(new File("C:\\Facebook\\fbmain.fb"));
                                    s="\n";
                                    read.nextLine();
                                    while(read.hasNextLine())
                                    {
                                        String email1=read.nextLine();
                                        String pass1=read.nextLine();
                                        long nid=Long.parseLong(read.nextLine());
                                        if(nid==id)
                                        {
                                            pass1=password;
                                            pass=password;
                                        }
                                        s=s+email1+"\n"+pass1+"\n"+nid+"\n";
                                    }
                                    if(s.length()!=0)
                                    s=s.substring(0,s.length()-1);
                                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\fbmain.fb")));
                                    pw.print(s);
                                    pw.close();
                                    pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Profile.fb")));
                                    pw.println(id);
                                    pw.println(email);
                                    pw.println(password);
                                    pw.println(gen);
                                    pw.println(dob);
                                    pw.println(phn);
                                    pw.print(doo);
                                    pw.close();
                                    Toolkit.getDefaultToolkit().beep();
                                    System.out.println("\t\tPassword Successfully Changed");
                                    System.out.println("\t\tIf You Don't See Changes Restart The Application");
                                    sc.nextLine();
                                    flag2=false;
                                    read.close();
                                }
                                else
                                {
                                    System.out.println("\tPlease Input Valid Password");
                                }
                            }
                        }
                        else
                        {
                            System.out.println("\t\tWrong Password Input!!!");
                            System.out.println("\t\tPassword Do Not Match!!");
                        }
                    }
                    else if(ch==6)
                    {
                        boolean flag2=true;
                        while(flag2)
                        {
                            System.out.print("Enter Your BirthDay In dd/mm/yyyy Format(Ex:- 01/02/2016):- ");
                            String birth=sc.nextLine();
                            flag=checkValidDate(birth);
                            if(!flag)
                                sc.nextLine();
                            if(flag)
                            {
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Profile.fb")));
                                pw.println(id);
                                pw.println(email);
                                pw.println(pass);
                                pw.println(gen);
                                pw.println(birth);
                                pw.println(phn);
                                pw.print(doo);
                                pw.close();
                                dob=birth;
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tDate Of Birth Successfully Changed");
                                System.out.println("\t\tIf You Don't See Changes Restart The Application");
                                sc.nextLine();
                                flag2=false;
                            }
                            else
                            {
                                System.out.println("\tPlease Input Correct Date");
                            }
                        }
                    }
                    else if(ch==7)
                    {
                        flag1=false;
                    }
                }
            }
            else if(n==2)
            {
                boolean flag1=true;
                while(flag1)
                {
                    Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Settings.fb"));
                    String la=read.nextLine();
                    read.close();
                    String stat;
                    if(la.equals("0"))
                    {
                        stat="Off";
                    }
                    else
                    {
                        stat="On";
                    }
                    System.out.println("\t\t1.LogIn Alerts:- "+stat);
                    System.out.println("\t\t2.Remove Account");
                    System.out.println("\t\t3.Back");
                    System.out.print("\n\t\tEnter Any Number To Change It:-");
                    int i=0;
                    while(i==0)
                    {
                        i=input(3);
                    }
                    if(i==1)
                    {
                        String change="";
                        if(la.equals("0"))
                        {
                            change="1";
                        }
                        else
                        {
                            change="0";
                        }
                        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Settings.fb"));
                        int c=0;
                        String ch="";
                        while(read.hasNextLine())
                        {
                            ++c;
                            String rd=read.nextLine();
                            if(c==1)
                            {
                                ch=change+"\n";
                            }
                            else
                            {
                                ch=ch+rd+"\n";
                            }
                        }
                        read.close();
                        if(ch.length()!=0)
                        ch=ch.substring(0,ch.length()-1);
                        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Settings.fb")));
                        pw.print(ch);
                        pw.close();
                    }
                    else if(i==2)
                    {
                        for(int cl=0;cl<=500;cl++)
                        {
                            System.out.println();
                        }
                        System.out.println("Leave Reason Empty To Go Back.");
                        System.out.print("Reason Of Deletion: ");
                        if(sc.nextLine().trim().equals(""))
                            continue;
                        for(int cl=0;cl<=500;cl++)
                        {
                            System.out.println();
                        }
                        System.out.println("\t\tThanks For Being A Part Of Facebook.");
                        System.out.println("\t\tWe Will Focus On Your Reason To Make Facebook Better");
                        System.out.println("\tPress Enter To Continue...");
                        sc.nextLine();
                        for(int cl=0;cl<=500;cl++)
                        {
                            System.out.println();
                        }
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("\t\tYour Account Deletion Scheduled!!!");
                        System.out.println("\t\tYou Have 14 days To Cancel Deletion!!!");
                        System.out.println("\t\tTo Cancel It Just Log In And Hit Cancel.");
                        System.out.println("\tPress Enter To Continue & Log Out...");
                        sc.nextLine();
                        for(int cl=0;cl<=500;cl++)
                        {
                            System.out.println();
                        }
                        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+change(username)+"\\DeadLine.fb")));
                        pw.print(getDate());
                        pw.close();
                        thread_id=0;
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ie)
                        {}
                        new File("C:\\Facebook\\"+change(username)+"\\online.fb").delete();
                        username="";
                        mainMenu();
                    }
                    else
                    flag1=false;
                    for(int cl=0;cl<=500;cl++)
                    {
                        System.out.println();
                    }
                }
            }
            else if(n==3)
            {
                id=change(username);
                boolean flag1=true;
                while(flag1)
                {
                    Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Settings.fb"));
                    String la,lac,fp,fpc,vd,vdc,sr,src,st,stc;
                    la=read.nextLine();
                    fp=read.nextLine();
                    vd=read.nextLine();
                    sr=read.nextLine();
                    st=read.nextLine();
                    read.close();
                    if(la.equals("0"))
                        lac="Off";
                    else
                        lac="On";
                    if(fp.equals("0"))
                        fpc="Public";
                    else
                        fpc="Friend";
                    if(vd.equals("0"))
                        vdc="Everyone";
                    else if(vd.equals("1"))
                        vdc="Friend";
                    else
                        vdc="No-One";
                    if(sr.equals("0"))
                        src="Everyone";
                    else
                        src="No-One";
                    if(st.equals("0"))
                        stc="Everyone";
                    else
                        stc="No-One";
                    System.out.println("\t1.Login Alerts :- "+lac);// Off-0 On-1
                    System.out.println("\t2.Who Can View Your Future Posts :- "+fpc);//Public-0 Friend-1
                    System.out.println("\t3.Who Can View Your Details :- "+vdc);//EveryOne-0 Friends-1 No-One-2
                    System.out.println("\t4.Who Can Send You Request :- "+src);// Everyone-0 No-one-1
                    System.out.println("\t5.Who Can See Your Timeline :- "+stc);//Everyone-0 No-One-1
                    System.out.println("\t6.Back");
                    System.out.print("\n\t\tEnter Any Number To Change It:-");
                    int i=0;
                    while(i==0)
                    {
                        i=input(6);
                    }
                    if(i==1)
                    {
                        if(la.equals("0"))
                        {
                            la="1";
                        }
                        else
                        {
                            la="0";
                        }
                    }
                    else if(i==2)
                    {
                        if(fp.equals("0"))
                        {
                            fp="1";
                        }
                        else
                        {
                            fp="0";
                        }
                    }
                    else if(i==3)
                    {
                        System.out.println("\t\t1.Everyone");
                        System.out.println("\t\t2.Friends");
                        System.out.println("\t\t3.No-one");
                        System.out.print("\n\t\tEnter Your Choice :-");
                        int ch=0;
                        while(ch==0)
                        {
                            ch=input(3);
                        }
                        ch--;
                        vd=ch+"";
                    }
                    else if(i==4)
                    {
                        if(sr.equals("0"))
                        {
                            sr="1";
                        }
                        else
                        {
                            sr="0";
                        }
                    }
                    else if(i==5)
                    {
                        if(st.equals("0"))
                        {
                            st="1";
                        }
                        else
                        {
                            st="0";
                        }
                    }
                    else
                    {
                        flag1=false;
                    }
                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Settings.fb")));
                    pw.println(la);
                    pw.println(fp);
                    pw.println(vd);
                    pw.println(sr);
                    pw.print(st);
                    pw.close();
                    for(int cl=0;cl<=500;cl++)
                    {
                        System.out.println();
                    }
                }
            }
            else if(n==4)
            {
                boolean flag1=true;
                Scanner read;
                id=change(username);
                String path="C:\\Facebook\\"+id+"\\BlockList.fb";
                System.out.println("\tOnce You Block Anyone he/she Can't Post To You Or Send Requests Or Chat\n\n");
                while(flag1)
                {
                    clear();
                    System.out.println();
                    if(!new File(path).exists())
                    {
                        boolean flag2=true;
                        while(flag2)
                        {
                            System.out.println("\t\tYou Have Not Blocked Anyone Yet");
                            System.out.println("\t\tPress Enter To Go Back");
                            System.out.println("\t\tInput The Username To Block");
                            read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                            String pnm=sc.nextLine();
                            if(pnm.equals(""))
                            {
                                flag1=false;
                                flag2=false;
                                continue;
                            }
                            int c=0;
                            boolean flag4=true;
                            String list="";
                            read.nextLine();
                            read.nextLine();
                            while(read.hasNextLine())
                            {
                                read.nextLine();
                                String un=read.nextLine().trim();
                                if(searchString(un,pnm))
                                {
                                    ++c;
                                    flag4=false;
                                    list+=un+"\n";
                                }
                            }
                            read.close();
                            if(flag4)
                            {
                                System.out.println("\t\tNo Such User Exists !!!!");
                                System.out.println("\tPress Enter To Continue...");
                                sc.nextLine();
                                continue;
                            }
                            String nl[]=new String[c];
                            int t1=0;
                            for(int j=0;j<c;j++)
                            {
                                nl[j]=list.substring(t1,list.indexOf("\n",t1));
                                t1=list.indexOf("\n",t1)+1;
                                System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                            }
                            System.out.println("\t\t"+(c+1)+" - Back");
                            System.out.print("Input a Desired Number To Select User:- ");
                            int inp=0;
                            while(inp==0)
                            {
                                inp=input(c+1);
                            }
                            if(inp==(c+1))
                            {
                                flag1=false;
                                flag2=false;
                                continue;
                            }
                            String name=nl[--inp].trim();
                            if(name.equals(""))
                            {
                            }
                            else
                            {
                                long blockid=change(name);
                                if(blockid==id)
                                {
                                    System.out.println("\t\tYou Can't Block Yourself !!!");
                                    sc.nextLine();
                                    continue;
                                }
                                if(blockid!=0)
                                {
                                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(path,true)));
                                    pw.println(blockid);
                                    pw.close();
                                    System.out.println("\t\t"+ name +" Blocked !!!");
                                    flag2=false;
                                }
                                else
                                {
                                    System.out.println("\t\tUserName Not Found\n\t\tPlease Input Correct Username");
                                }
                            }
                            for(int cl=0;cl<=500;cl++)
                            {
                                System.out.println();
                            }
                        }
                    }
                    else
                    {
                        boolean flag2=true;
                        while(flag2)
                        {
                            clear();
                            System.out.println();
                            System.out.println("\t\t1. Block   User");
                            System.out.println("\t\t2. Unblock User");
                            System.out.println("\t\t3. Check Block List");
                            System.out.println("\t\t4. Back");
                            System.out.print("\n\t\tEnter Your Choice :-");
                            int ch=0;
                            while(ch==0)
                            {
                                ch=input(4);
                            }
                            if(ch==1)
                            {
                                boolean flag3=true;
                                while(flag3)
                                {
                                    System.out.println("\t\tPress Enter To Go Back");
                                    System.out.println("\t\tInput The Username To Block");
                                    read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                                    String pnm=sc.nextLine();
                                    if(pnm.equals(""))
                                    {
                                        flag3=false;
                                        continue;
                                    }
                                    int c=0;
                                    boolean flag4=true;
                                    String list="";
                                    read.nextLine();
                                    read.nextLine();
                                    while(read.hasNextLine())
                                    {
                                        read.nextLine();
                                        String un=read.nextLine().trim();
                                        if(searchString(un,pnm))
                                        {
                                            ++c;
                                            flag4=false;
                                            list+=un+"\n";
                                        }
                                    }
                                    read.close();
                                    if(flag4)
                                    {
                                        System.out.println("\t\tNo Such User Exists !!!!");
                                        System.out.println("\tPress Enter To Continue...");
                                        sc.nextLine();
                                        continue;
                                    }
                                    String nl[]=new String[c];
                                    int t1=0;
                                    for(int j=0;j<c;j++)
                                    {
                                        nl[j]=list.substring(t1,list.indexOf("\n",t1));
                                        t1=list.indexOf("\n",t1)+1;
                                        System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                                    }
                                    System.out.println("\t\t"+(c+1)+" - Back");
                                    System.out.print("Input a Desired Number To Select User:- ");
                                    int inp=0;
                                    while(inp==0)
                                    {
                                        inp=input(c+1);
                                    }
                                    if(inp==(c+1))
                                    {
                                        flag3=false;
                                        continue;
                                    }
                                    String name=nl[--inp].trim();
                                    if(name.equals(""))
                                    {
                                    }
                                    else
                                    {
                                        long blockid=change(name);
                                        if(blockid==id)
                                        {
                                            System.out.println("\t\tYou Can't Block Yourself !!!");
                                            sc.nextLine();
                                            continue;
                                        }
                                        read=new Scanner(new File(path));
                                        boolean flag5=true;
                                        while(read.hasNextLine())
                                        {
                                            String check=read.nextLine();
                                            if(check.equals(blockid))
                                            {
                                                System.out.println("\t\tUser Already Blocked !!!");
                                                sc.nextLine();
                                                flag5=false;
                                            }
                                        }
                                        read.close();
                                        if(blockid!=0)
                                        {
                                            if(flag5)
                                            {
                                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(path,true)));
                                                pw.println(blockid);
                                                pw.close();
                                                System.out.println("\t\t"+ name +" Blocked !!!");
                                                sc.nextLine();
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("\t\tUserName Not Found\n\t\tPlease Input Correct Username");
                                            sc.nextLine();
                                        }
                                    }
                                   clear();
                                }
                            }
                            else if(ch==2)
                            {
                                boolean flag3=true;
                                while(flag3)
                                {
                                    refreshPage(path,"Block List");
                                    System.out.println("\t\tPress Enter To Go Back");
                                    System.out.println("\t\tInput The Username To Un-Block");
                                    read=new Scanner(new File("C:\\Facebook\\BlockList.fb"));
                                    String pnm=sc.nextLine();
                                    if(pnm.equals(""))
                                    {
                                        flag3=false;
                                        continue;
                                    }
                                    int c=0;
                                    boolean flag4=true;
                                    String list="";
                                    while(read.hasNextLine())
                                    {
                                        long bid=read.nextLong();
                                        String un=change(bid);
                                        if(searchString(un,pnm))
                                        {
                                            ++c;
                                            flag4=false;
                                            list+=un+"\n";
                                        }
                                    }
                                    read.close();
                                    if(flag4)
                                    {
                                        System.out.println("\t\tNo Such User Exists/Blocked !!!!");
                                        System.out.println("\tPress Enter To Continue...");
                                        sc.nextLine();
                                        continue;
                                    }
                                    String nl[]=new String[c];
                                    int t1=0;
                                    for(int j=0;j<c;j++)
                                    {
                                        nl[j]=list.substring(t1,list.indexOf("\n",t1));
                                        t1=list.indexOf("\n",t1)+1;
                                        System.out.println("\t\t"+(j+1)+" - "+nl[j]);
                                    }
                                    System.out.println("\t\t"+(c+1)+" - Back");
                                    System.out.print("Input a Desired Number To Select User:- ");
                                    int inp=0;
                                    while(inp==0)
                                    {
                                        inp=input(c+1);
                                    }
                                    if(inp==(c+1))
                                    {
                                        flag3=false;
                                        continue;
                                    }
                                    String name=nl[--inp].trim();
                                    name=name.trim();
                                    if(name.equals(username))
                                    {
                                        System.out.println("\t\tYou Can't Unblock Yourself !!!");
                                        System.out.println("\tPress Enter To Continue...");
                                        sc.nextLine();
                                        continue;
                                    }
                                    else
                                    {
                                        long blockid=change(name);
                                        read=new Scanner(new File(path));
                                        boolean flag5=false;
                                        String write="";
                                        while(read.hasNextLine())
                                        {
                                            String check=read.nextLine();
                                            if(check.equals(blockid))
                                            {
                                                flag5=true;
                                                continue;
                                            }
                                            write+=check+"\n";
                                        }
                                        read.close();
                                        if(blockid!=0)
                                        {
                                            if(flag5)
                                            {
                                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(path)));
                                                pw.print(write);
                                                pw.close();
                                            }
                                            else
                                            {
                                                System.out.println("\t\t"+name+"is not Blocked");
                                                sc.nextLine();
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("\t\tUserName Not Found\n\t\tPlease Input Correct Username");
                                            sc.nextLine();
                                        }
                                    }
                                    for(int cl=0;cl<=500;cl++)
                                    {
                                        System.out.println();
                                    }
                                }
                            }
                            else if(ch==3)
                            {
                                if(new File(path).exists())
                                {
                                    refreshPage(path,"Block List");
                                    sc.nextLine();
                                }
                                else
                                    System.out.println("\tYou Have Not Blocked Anyone Yet !!!");
                                            sc.nextLine();
                            }
                            else
                            {
                                flag2=false;
                                flag1=false;
                            }
                        }
                    }
                    for(int cl=0;cl<=500;cl++)
                    {
                        System.out.println();
                    }
                }
            }
            else if(n==5)
            {
                id=change(username);
                System.out.println("Please Enter Your Problem Or Bug You Found\nThe SpeedX Company Will Fix It On Next Release");
                String pro=sc.nextLine();
                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Problem.fb",true)));
                pw.print(getDate()+"\n"+pro);
                pw.close();
                pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Notifications.fb",true)));
                pw.println("Thanks For Reporting Problem!!!");
                String spa="";
                for(int spac=0;spac<68;spac++)
                {
                    spa+=" ";
                }
                spa+="-"+getDate();
                pw.println(spa);
                pw.close();
                sc.nextLine();
            }
            else if(n==6)
            {
                System.out.println("\t\tThanks For Opening Account in Facebook ");
                System.out.println("\t\tRefer Facebook To Your Friend And Get Amazing Gifts");
                if(new File("C:\\Facebook\\"+id+"\\Problem.fb").exists())
                {
                    Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Problem.fb"));
                    while(read.hasNextLine())
                    {
                        String date=read.nextLine();
                        String pro =read.nextLine();
                        System.out.println("\tReporting Date: "+date);
                        System.out.println("\tThanks For Reporting: "+pro+"\n");
                    }
                    read.close();
                }
                else
                {
                    System.out.println("\t\tYou Have Not Reported Any Problem Yet !!!");
                }
                sc.nextLine();
            }
            else
            {
                flagm=false;
            }
        }
    }
    //Removes Account
    static void removeAccount(long id)throws Exception
    {
        Scanner sc=new Scanner(System.in);
        Scanner read;
        System.gc();
        File dlt[]=new File("C:\\Facebook\\"+id+"\\").listFiles();
        for(File path: dlt)
        {
            path.delete();
        }
        File path=new File("C:\\Facebook\\"+id);
        if(path.exists())
            path.delete();
        File del;
        //Delete  Record From Username.fb
        del=new File("C:\\Facebook\\username.fb");
        read=new Scanner(del);
        String str="";
        while(read.hasNextLine())
        {
            long ide=Long.parseLong(read.nextLine());
            String user=read.nextLine();
            if(ide==id)
            {
                continue;
            }
            else
            {
                str=str+ide+"\n"+user+"\n";
            }
        }
        read.close();
        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\username.fb")));
        pw.print(str);
        pw.close();
        //Delete Record From fbmain.fb
        del=new File("C:\\Facebook\\fbmain.fb");
        read=new Scanner(del);
        str="\n";
        read.nextLine();
        while(read.hasNextLine())
        {
            String email=read.nextLine();
            String pass=read.nextLine();
            long ide=Long.parseLong(read.nextLine());
            if(ide==id)
            {
                continue;
            }
            else
            {
                str=str+email+"\n"+pass+"\n"+ide+"\n";
            }
            File up[]=new File("C:\\Facebook\\"+ide+"\\").listFiles();
            for(File upath: up)
            {
                String tempg=upath.getName();
                if(tempg.equalsIgnoreCase("Rate.fb") || tempg.equalsIgnoreCase("DeadLine.fb") || tempg.equalsIgnoreCase("Game.fb") || tempg.equalsIgnoreCase("HomePage.fb") || tempg.equalsIgnoreCase("Notifications.fb") || tempg.equalsIgnoreCase("Profile.fb") || tempg.equalsIgnoreCase("Settings.fb") || tempg.equalsIgnoreCase("TimeLine.fb") || tempg.equalsIgnoreCase("online.fb") || tempg.equalsIgnoreCase("Status.fb")  || tempg.equalsIgnoreCase("Problem.fb"))
                    continue;
                if(tempg.equalsIgnoreCase("Friends.fb"))
                {
                    Scanner read1=new Scanner(new File("C:\\Facebook\\"+ide+"\\"+tempg));
                        read1.nextLine();
                    String write="";
                    while(read1.hasNextLine())
                    {
                        String idd=read1.nextLine();
                        String bir=read1.nextLine();
                        if(Long.parseLong(idd)==ide)
                            continue;
                        write+=idd+"\n"+bir+"\n";
                    }
                    read1.close();
                    if(write.length()!=0)
                    write=write.substring(0,write.length()-1);
                    pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+ide+"\\"+tempg)));
                    pw.print("\n"+write);
                    pw.close();
                }
                else
                {
                    Scanner read1=new Scanner(new File("C:\\Facebook\\"+ide+"\\"+tempg));
                    if( tempg.equalsIgnoreCase("Requests.fb"))
                        if(read1.hasNextLine())
                            read1.nextLine();
                    String write="";
                    while(read1.hasNextLine())
                    {
                        String idd=read1.nextLine();
                        if(Long.parseLong(idd)==ide)
                            continue;
                        write+=idd+"\n";
                    }
                    read1.close();
                    if(write.length()!=0)
                    write=write.substring(0,write.length()-1);
                    pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+ide+"\\"+tempg)));
                    pw.print(write);
                    pw.close();
                }
            }
        }
        read.close();
        if(!str.equals("\n"))
        {
            if(str.length()!=0)
            str=str.substring(0,str.length()-1);
        }
        else
        {
            str="\n";
        }
        pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\fbmain.fb")));
        pw.print(str);
        pw.close();
    }
    //Shows Details About A User
    static void viewDetails(long id)throws Exception
    {
        Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Profile.fb"));
        String ide=read.nextLine();
        String mail=read.nextLine();
        String pass=read.nextLine();
        String gen=read.nextLine();
        String dob=read.nextLine();
        String phn=read.nextLine();
        String aco=read.nextLine();
        read.close();
        if(gen.equalsIgnoreCase("m"))
        {
            gen="Male";
        }
        else
        {
            gen="Female";
        }
        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Status.fb"));
        String stat=read.nextLine();
        read.close();
        System.out.println("\t\t\tEmail:-"+mail);
        System.out.println("\t\t\tGender:- "+gen);
        System.out.println("\t\t\tDate Of Birth:- "+dob);
        System.out.println("\t\t\tPhone Number:- "+phn);
        System.out.println("\t\t\tDay Of Opening Account:- "+aco);
        System.out.println("\t\t\tStatus :- "+stat);
        System.out.println();
        new Scanner(System.in).nextLine();
    }
    //Check If A File Is Empty Or Not
    static boolean checkEmptyFile(String k)throws Exception
    {
        Scanner sc=new Scanner(new File(k));
        String s="",t="";
        while(sc.hasNextLine())
        {
            s=s+sc.nextLine()+"\n";
        }
        sc.close();
        for(int i=0;i<s.length();i++)
        {
            if(!(s.charAt(i)==' '|| s.charAt(i)=='\n'))
            {
                t+=s.charAt(i);
            }
        }
        if(t.trim().equals(""))
        return true;
        else
        return false;
    }
    //Shows Apps And Games
    static void appsManagement()throws Exception
    {
        boolean flag=true;
        Scanner sc=new Scanner(System.in);
        while(flag)
        {
            for(int cl=0;cl<=500;cl++)
            {
            System.out.println();
            }
            System.out.println("\tPress 1 To Play  Tic Tac Toe");
            System.out.println("\tPress 2 To Play  Anagram Solver");
            System.out.println("\tPress 3 To Open  Encrypt !T");
            System.out.println("\tPress 4 To Go Back");
            System.out.println("\tMore Games & Apps Coming Soon!!!");
            System.out.print("\n\t\tEnter You Choice :- ");
            int i=0;
            while(i==0)
            {
                i=input(4);
            }
            if(i==1)
            {
                long id=change(username);
                if(!new File("C:\\Facebook\\"+id+"\\Game.fb").exists())
                {
                    PrintWriter pr=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Game.fb")));
                    pr.println("1");//Level
                    pr.println("0");//Xp
                    if(getDate().startsWith("26-09"))
                        pr.println("1000");
                    else
                    pr.println("500");//Coins
                    pr.println("0");//Cheats Off-0 On-1
                    pr.println("0");//XP Booster Off-0 On-1
                    pr.println("0");//Coin Booster Off-0 On-1
                    pr.println("1");//Defeating-Level
                    pr.print("!");
                    pr.close();
                    if(getDate().startsWith("26-09"))
                    {
                        System.out.println("\t\t\tToday Is Facebook's And Gyanaranjan Sahoo's Birthday");
                        System.out.println("\t\tSo You Are A Lucky Player As You Get 1000 Coins !!!");
                    }
                    else
                    System.out.println("\t\tYou Are A Lucky Player And You Get 500 Coins !!!");
                    System.out.println("\t\tPress Enter To Continue !!!");
                    sc.nextLine();
                }
                TicTacToe.Main();
                System.out.println("\t\tThanks!!! For Playing Games \n\t\tMore Games Coming Soon!!!");
            }
            else if(i==2)
            {
                AnagramSolver.Main();
                System.out.println("\t\tThanks!!! For Playing Games \n\t\tMore Games Coming Soon!!!");
            }
            else if(i==3)
            {
                EncryptDecrypt.Main();
                System.out.println("\t\tThanks!!! For Using Apps \n\t\tMore Apps Coming Soon!!!");
            }
            else
            {
                System.out.println("\t\tMore Apps & Games Coming Soon!!!");
                flag=false;
            }
            sc.nextLine();
        }
    }
    //HomeScreen InterFace
    static void openAccount() throws Exception
    {
        Scanner sc=new Scanner(System.in);
        long id=change(username);
        Scanner read;
        clear();
        //Wishes You According To Time
        SimpleDateFormat df=new SimpleDateFormat("HH");
        Date dt=new Date();
        int k1=Integer.parseInt(df.format(dt));
        String wish="                               ";
        if(k1>=4 && k1<=11)
        {
            wish+=" Good Morning !!!";
        }
        else if(k1>=12 && k1<=15)
        {
            wish+="Good Afternoon !!!";
        }
        else if(k1>=16 && k1<=20)
        {
            wish+=" Good Evening !!!";
        }
        else
        {
            wish+="  Good Night !!!";
        }
        print(wish);
        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        System.out.print("Press Enter To Continue...");
        new Scanner(System.in).nextLine();
        //Cancel Deletion Of Account
        File f=new File("C:\\Facebook\\"+id+"\\DeadLine.fb");
        if(f.exists())
        {
            read=new Scanner(f);
            String s3=read.nextLine();
            read.close();
            String s2=getDate();
            int d2=Integer.parseInt(s2.substring(0,2));
            int d3=Integer.parseInt(s3.substring(0,2));
            int m2=Integer.parseInt(s2.substring(3,5));
            int m3=Integer.parseInt(s3.substring(3,5));
            int y2=Integer.parseInt(s2.substring(6));
            int y3=Integer.parseInt(s3.substring(6));
            int n2=0;
            int n3=0;
            for(int j=1;j<=(y2-2016);j++)
            {
                for(int k=1;k<=12;k++)
                    n2+=days(k,2015+j);
            }
            for(int j=1;j<m2;j++)
                n2+=days(j,y2);
            n2+=d2;
            for(int j=1;j<=(y3-2016);j++)
            {
                for(int k=1;k<=12;k++)
                    n3+=days(k,2015+j);
            }
            for(int j=1;j<m3;j++)
                n3+=days(j,y3);
            n3+=d3;
            System.out.println(n3+14-n2+" Day(s) Left To Cancel Account Deletion");
            boolean flag1=true;
            while(flag1)
            {
                System.out.print("\n\t\t\tDo You Want To Cancel Deletion(Y/N):- ");
                String ipl=sc.nextLine();
                ipl=ipl.trim();
                if(ipl.equalsIgnoreCase("Y"))
                {
                    f.delete();
                    flag1=false;
                }
                else if(ipl.equalsIgnoreCase("N"))
                {
                    System.out.println("\t\tYou Can't Proceed Unless You Cancel Deletion");
                    System.out.println("\t\tPress Enter To Continue...");
                    sc.nextLine();
                    username="";
                    thread_id=0;
                    flag1=false;
                    mainMenu();
                }
                else
                {
                    System.out.println("\t\tPlease Enter A Valid Input(Y/N)");
                }
            }
        }
        //LogIn Alerts
        read=new Scanner(new File("C:\\Facebook\\"+id+"\\Settings.fb"));
        if(Integer.parseInt(read.nextLine())==1)
        {
            PrintWriter pr=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Notifications.fb",true)));
            Date dNow=new Date();
            SimpleDateFormat ft=new SimpleDateFormat("EEEE dd.MM.yyyy 'at' hh:mm:ss a");
            pr.println("Log In:  "+ft.format(dNow));
            pr.close();
        }
        read.close();
        //Delete online.fb when JVM stops
        new File("C:\\Facebook\\"+id+"\\online.fb").deleteOnExit();
        //Check Already Opened Account
        String s="C:\\Facebook\\"+id+"\\online.fb";
        f=new File(s);
        thread_id=change(username);
        if(f.exists())
        {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("\t Your Account is Opened At Another Place \n\t Or You Have Not Logged Out Last Time");
            System.out.println("\t Press Enter To Log Out From Anywhere Your Account is Opened\n");
            System.out.println(" Note:-\n\t If You Doubt Your Account is Opened At Another Place;Please Change Your Password Immediately.");
            System.out.println(" \n\tPlease Log Out Instantly & Log In Again To Successfully Log Out Other Users...");
            sc.nextLine();
            f.delete();
            wait(2000);
            f.createNewFile();
            Facebook fb=new Facebook();
            fb.start();
        }
        else
        {            
            f.createNewFile();
            Facebook fb=new Facebook();
            fb.start();
        }
        boolean flag=true;
        while(flag)
        {
            s="C:\\Facebook\\"+id+"\\Friends.fb";
            read=new Scanner(new File(s));
            s=read.nextLine();
            String not="C:\\Facebook\\"+id+"\\Notifications.fb";
            //Friends BirthDay
            while(read.hasNextLine())
            {
                long a=Long.parseLong(read.nextLine());
                String ur=change(a);
                String br=read.nextLine();
                s=getDate();
                int d=Integer.parseInt(br.substring(0,2));
                int m=Integer.parseInt(br.substring(3,5));
                int d1=Integer.parseInt(s.substring(0,2));
                int m1=Integer.parseInt(s.substring(3,5));
                if(m1==m)
                {
                    PrintWriter pr=new PrintWriter(new BufferedWriter(new FileWriter(not,true)));
                    if(d1==d)
                    {
                        pr.println("Today is "+ur+" Birthday");
                    }
                    if((d1+1)==d)
                    {
                        pr.println("Tommorrow is "+ur+" Birthday");
                    }
                    String spa="";
                    for(int spac=0;spac<68;spac++)
                    {
                        spa+=" ";
                    }
                    spa+="-"+getDate();
                    pr.println(spa);
                    pr.close();
                }
            }
            read.close();
            //Your BirthDay
            not="C:\\Facebook\\"+id+"\\Profile.fb";
            read=new Scanner(new File(not));
            not="C:\\Facebook\\"+id+"\\Notifications.fb";
            String bir="";
            for(int i=1;i<=5;i++)
            {
                bir=read.nextLine();
            }
            read.close();
            s=getDate();
            int d=Integer.parseInt(bir.substring(0,2));
            int m=Integer.parseInt(bir.substring(3,5));
            int d1=Integer.parseInt(s.substring(0,2));
            int m1=Integer.parseInt(s.substring(3,5));
            if(m1==m)
            {
                PrintWriter pr=new PrintWriter(new BufferedWriter(new FileWriter(not,true)));
                if(d1==d)
                {
                    pr.println("Today is Your Birthday !!!\nSpeedX & Facebook Wishes You A Belated Happy BirthDay !!!");
                }
                if((d1+1)==d)
                {
                    pr.println("Tomorrow is Your Birthday !!!\nFacebook Wishes You A Very Happy BirthDay !!!");
                }
                String spa="";
                for(int spac=0;spac<68;spac++)
                {
                    spa+=" ";
                }
                spa+="-"+getDate();
                pr.println(spa);
                pr.close();
            }
            //Facebook BirthDay
            not="C:\\Facebook\\"+id+"\\Notifications.fb";
            bir="26/09/2001";
            s=getDate();
            d=Integer.parseInt(bir.substring(0,2));
            m=Integer.parseInt(bir.substring(3,5));
            d1=Integer.parseInt(s.substring(0,2));
            m1=Integer.parseInt(s.substring(3,5));
            if(m1==m)
            {
                PrintWriter pr=new PrintWriter(new BufferedWriter(new FileWriter(not,true)));
                if(d1==d)
                {
                    pr.println("Today is Facebook's Birthday !!!\nAmazing Offer By Facebook:Get 1000 coins In Tic-Tac-Toe Only New Users !!!");
                }
                String spa="";
                for(int spac=0;spac<68;spac++)
                {
                    spa+=" ";
                }
                spa+="-"+getDate();
                pr.println(spa);
                pr.close();
            }
            //Special Days Notifications
            String daynm[]={"Global Family Day","New Year's Day","Day of Silence","World Braille Day","World Literary Day","NRI Day","Laughing Day","International Thank-You Day","National Youth Day","Army Day","Netaji Jayanti","National Voter's Day","Indian Republic Day","World Leprosy Day","Martyr's Day","Street Children's Day","National Girl Day","World Cancer Day","Rose Day","Propose Day","Chocolate Day","Bagels and Lox Day","Pizza Pie Day","Teddy Day","Promise Day","Kiss Day","Darwin Day","Hug Day","Valentine's Day","International Mother Language Day","World Thinking Day","National Science Day","International Day of the Seal","International Women's Day","No Smoking Day","World Disable Day","World Consumer Rights Day","Saint Patrick's Day","Earth Hour - 8pm Local Time","World Frog Day","World Down Syndrome Day","World Poetry Day","World Day for Water","World Meteorological Day","World Tuberculosis Day","Fool's Day","Utkal Divas","International Children's Book Day","World Health Day","International Special Librarian's Day","World Parkinson's Disease Day","Yuri`s Night","World Haemophilia Day","World Heritage Day","International Creativity and Innovation Day","Earth Day","World Book Day","World Penguin Day","World Malaria Day","World Press Freedom Day","International Midwives Day","World Red Cross Day","World Lupus Day","National Technology Day","International Nurses Day","IEEE Global Engineering Day","International Day of Families","Telecommunication Day","International Museum Day","World Day for Cultural Diversity","International Day for Biological Diversity","World Turtle Day","World Schizophrenia Day","Commonwealth Day","Towel Day","World No-Tobacco Day","International Children's Day","World Environment Day","World Ocean Day","World Blood Donor Day","International Day of the African Child","World Day to Combat Desertification & Drought","World Refugee Day","International Yoga Day","Father's Day","World Music Day","International Olympic Day","International Day Against Drug Abuse & Trafficking","Doctor's Day","Independence Day in U.S","World Population Day","World Snake Day","Kargil Vijay Divas","World Breast Feeding Day","Universal & International Infinity Day","International Biodiesel Day","International Youth Day","International Left-handers Day","World Lizard Day","Indian Independence Day","World Photography Day","World Mosquito Day","International Day for the Remembrance of the Slave Trade & its Abolition","Sanskrit Day","Women's Equality Day","National Sports Day","Teacher's Day","International Literacy Day","International Chocolate Day","Hindi Day","Engineer's Day","International Day of Democracy","International Day for the Preservation of the Ozone Layer","Talk Like a Pirate Day","World Gratitude Day","International Day of Peace","World Car-Free Day","World Tourism Day","Inventors Day","World Heart Day","International Music Day","World Farm Animals Day","Mahatma Gandhi Birthday","World Temperance Day","World Animal Day","World Teacher's Day","Indian Air force Day","World Post Day","World Mental Health Day","World Food Day","International Day for the Eradication of Poverty","United Nations Day","International Internet Day","World Town Planning Day","Education Day","Children's Day","International Day for Tolerance","International Student's Day","Citizen's Day","Universal Children's Day","World Television Day","International Day for the Elimination of Violence Against Women","International Computer Security Day","World AIDS Day","International Day for the Abolition of Slavery","World Handicapped Day","Navy Day","International Volunteer's Day","International Civil Aviation Day","Human Rights Day","Farmer's Day","National Consumer Day","Christmas Eve","Christmas Day","Boxing Day"};
            String date[]={"01/01","01/01","03/01","04/01","08/01","09/01","10/01","11/01","12/01","15/01","23/01","25/01","26/01","30/01","30/01","31/01","02/02","04/02","07/02","08/02","09/02","09/02","09/02","10/02","11/02","12/02","12/02","13/02","14/02","21/02","22/02","28/02","01/03","08/03","13/03","15/03","15/03","17/03","19/03","20/03","21/03","21/03","22/03","23/03","24/03","01/04","01/04","02/04","07/04","10/04","11/04","12/04","17/04","18/04","21/04","22/04","23/04","25/04","25/04","03/05","05/05","08/05","10/05","11/05","12/05","13/05","15/05","17/05","18/05","21/05","22/05","23/05","24/05","24/05","25/05","31/05","01/06","05/06","08/06","14/06","16/06","17/06","20/06","21/06","21/06","21/06","23/06","26/06","01/07","04/07","11/07","16/07","26/07","01/08","08/08","10/08","12/08","13/08","14/08","15/08","19/08","20/08","23/08","24/08","26/08","29/08","05/09","08/09","13/09","14/09","15/09","15/09","16/09","19/09","21/09","21/09","22/09","27/09","29/09","29/09","01/10","02/10","02/10","03/10","04/10","05/10","08/10","09/10","10/10","16/10","17/10","24/10","29/10","08/11","11/11","14/11","16/11","17/11","19/11","20/11","21/11","25/11","30/11","01/12","02/12","03/12","04/12","05/12","07/12","10/12","23/12","24/12","24/12","25/12","26/12"};
            s=getDate();
            int cd=Integer.parseInt(s.substring(0,2));
            int cm=Integer.parseInt(s.substring(3,5));
            PrintWriter pk=new PrintWriter(new BufferedWriter(new FileWriter(not,true)));
            for(int i=0;i<date.length;i++)
            {
                int fm=Integer.parseInt(date[i].substring(3,5));
                if(fm<cm)
                    continue;
                if(fm>cm)
                    break;
                int fd=Integer.parseInt(date[i].substring(0,2));
                if(cd==fd)
                {
                    pk.println("Today is "+daynm[i]+" !!!");
                }
                if((cd+1)==fd)
                {
                    pk.println("Tommorrow is "+daynm[i]+" !!!");
                }
                String spa="";
                for(int spac=0;spac<68;spac++)
                {
                    spa+=" ";
                }
                spa+="-"+getDate();
                pk.println(spa);
            }
            pk.close();
            //Remove Duplicate Lines From BlockList
            if(new File("C:\\Facebook\\"+id+"\\BlockList.fb").exists())
            {
                removeDuplicateLine("C:\\Facebook\\"+id+"\\BlockList.fb");
            }
            //Remove Duplicate Lines From Notifications
            not="C:\\Facebook\\"+id+"\\Notifications.fb";
            removeDuplicateLine(not);
            //Remove Duplicate Lines From HomePage
            not="C:\\Facebook\\"+id+"\\HomePage.fb";
            removeDuplicateLine(not);
            clear();
            int loop=40-username.length()/2;
            for(int i=1;i<=loop;i++)
            {
                System.out.print(" ");
            }
            System.out.println("  "+username);
            for(int i=1;i<=loop;i++)
            {
                System.out.print(" ");
            }
            System.out.print("--");
            for(int i=0;i<username.length();i++)
            System.out.print("-");
            System.out.println("--");
            s=getDate();
            for(int i=0;i<(79-s.length());i++)
            System.out.print(" ");
            System.out.println(s+"\n");
            System.out.println("*******************************************************************************");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |       WHAT DO YOU WANT TO DO ?               |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |            1 - Homepage                      |    ***********");
            System.out.println("************    |            2 - Notifications                 |    ***********");
            System.out.println("************    |            3 - \"What's On Your Mind\"         |    ***********");
            System.out.println("************    |            4 - Profile                       |    ***********");
            System.out.println("************    |            5 - Chat                          |    ***********");
            System.out.println("************    |            6 - Friends                       |    ***********");
            System.out.println("************    |            7 - Apps & Games                  |    ***********");
            System.out.println("************    |            8 - Settings                      |    ***********");
            System.out.println("************    |            9 - Log Out                       |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("*******************************************************************************");
            System.out.print("\t\tEnter Your Choice:-  ");
            s=sc.nextLine();
            s=s.trim();
            if(s.equals("1"))
            {
                String a="C:\\Facebook\\"+id+"\\Homepage.fb";
                removeDuplicateLine(a);
                refreshPage(a,"HomePage");
                sc.nextLine();
            }
            else if(s.equals("2"))
            {
                String a="C:\\Facebook\\"+id+"\\Notifications.fb";
                removeDuplicateLine(a);
                refreshPage(a,"Notifications");
                if(checkEmptyFile(a))
                {
                    System.out.println("\t\tYou Have No Notifications");
                }
                sc.nextLine();
            }
            else if(s.equals("3"))
            {
                post();
            }
            else if(s.equals("4"))
            {
                profile();
            }
            else if(s.equals("5"))
            {
                chat();
            }
            else if(s.equals("6"))
            {
                friends();
            }
            else if(s.equals("7"))
            {
                appsManagement();
            }
            else if(s.equals("8"))
            {
                settings();
            }
            else if(s.equals("9"))
            {
                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Settings.fb"));
                if(Integer.parseInt(read.nextLine())==1)
                {
                    PrintWriter pr=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\Notifications.fb",true)));
                    Date dNow=new Date();
                    SimpleDateFormat ft=new SimpleDateFormat("EEEE dd.MM.yyyy 'at' hh:mm:ss a");
                    pr.println("Log Out: "+ft.format(dNow));
                    pr.close();
                }
                read.close();
                flag=false;
                String a="C:\\Facebook\\"+id+"\\online.fb";
                thread_id=0;
                try
                {
                    Thread.sleep(500);
                }
                catch(InterruptedException ie)
                {}
                File f1=new File(a);
                if(f1.exists())
                f1.delete();
                clear();
                System.out.println("\t\tIf You Liked This Application,"+
                                "\n\t\t                    You Can Donate Money To Us...");
                System.out.println("\t\tIt Will Encourage Us !!!");
                System.out.println("\t\tTo Donate Please Contact:- 7205595198");
                if(!new File("C:\\Facebook\\"+change(username)+"\\Rate.fb").exists())
                {
                    boolean flagc=true;
                    while(flagc)
                    {
                        System.out.println("\tDo You Want To Rate This App ??? (y/n)");
                        String c=sc.nextLine();
                        if(c.trim().equalsIgnoreCase("Y"))
                        {
                            flagc=false;
                            System.out.print("\t\tPlease Give Your Ratings Between 1 To 5: ");
                            int i=0;
                            while(i==0)
                            {
                                i=input(5);
                                if(i==0)
                                {
                                    System.out.print("\t\tPlease Give Your Ratings Between 1 To 5: ");
                                }
                            }
                            try
                            {
                                System.out.print("Rated : ");
                                for(int rt=0;rt<i;rt++)
                                {
                                    System.out.print("* ");
                                    Toolkit.getDefaultToolkit().beep();
                                    Thread.sleep(1000);
                                }
                            }
                            catch(InterruptedException ie)
                            {}
                            System.out.println("\t\tThanks For Your Ratings!!!");
                            read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                            read.nextLine();
                            read.nextLine();
                            while(read.hasNextLine())
                            {
                                id=Long.parseLong(read.nextLine());
                                read.nextLine();
                                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+id+"\\HomePage.fb",true)));
                                if(id==change(username))
                                {
                                    pw.println("\t\t\tYou Rated Facebook: "+i+"/5");
                                }
                                else
                                {
                                    pw.println("\t\t\t"+username+" Rated Facebook: "+i+"/5");
                                }
                                pw.close();
                            }
                            read.close();
                            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+change(username)+"\\Rate.fb")));
                            pw.print(i);
                            pw.close();
                        }
                        else if(c.trim().equalsIgnoreCase("N"))
                        {
                            flagc=false;
                        }
                        else
                            System.out.println("Please Give A Valid Input (Y/N)");
                    }
                }
                System.out.println("\t\t\tLogging Out...");
                System.out.println("\t\t\tPress Enter To Continue...");
                sc.nextLine();
            }
            else
            {
                System.out.println("Invalid Number");
                System.out.println("Press Enter To Continue");
                String asd=sc.nextLine();
            }
            clear();
        }
    }
    //Returns number Of Days
    static int days(int m,int y)
    {
        int day=0;
        switch(m)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:day=31;break;
            case 4:
            case 6:
            case 9:
            case 11:day=30;break;
            case 2:{
                    if (y%4==0)
                    day= 29;
                    else
                    day= 28;
                    break;}
        }
        return day;
    }
    //Refreshes Current Page
    static void refreshPage(String s,String top) throws Exception
    {
        clear();
        Scanner sc=new Scanner(new File(s));
        System.out.println("\t\t\t\t  "+top);
        if(!top.equals(""))
        {
            System.out.print("\t\t\t\t--");
            for(int i=0;i<top.length();i++)
            System.out.print("-");
            System.out.println("--");
        }
        s=getDate();
        for(int i=0;i<(79-s.length());i++)
        System.out.print(" ");
        System.out.println(s);
        System.out.println("  "+username);
        for(int i=1;i<=username.length()+4;i++)
        System.out.print("-");
        System.out.println("\n\n\n\n\n\n");
        while(sc.hasNextLine())
        {
            s=sc.nextLine();
            try
            {
                long lg=Long.parseLong(s);
                if(!change(lg).equals(""))
                {
                    System.out.println(change(lg));
                }
                else
                {
                    System.out.println(s);
                }
            }
            catch(Exception nfe)
            {
                System.out.println(s);
            }
        }
    }
    //Returns One Time Password
    static String getOtp()throws Exception
    {
        int p1=0,num=0,p2=0,s=0,n=0,pw=0;
        String otp="";
        try
        {
            while(true)
            {
                p1=rnd(5);
                Thread.sleep(rnd(10));
                p2=rnd(5);
                if(p1==p2)
                    continue;
                break;
            }
            while(num<42798 || num>299528)
            {
                num=rnd(299529);
                Thread.sleep(rnd(10));
                pw=rnd(7);
                s=((Integer.parseInt(Integer.toOctalString(num)))+rnd(7))*pw;
            }
            while(Integer.toString(s).length()>6)
                s/=10;
            otp=Integer.toString(s);
            for(int i=1;i<=2;i++)
            {
                char c;
                int k[]={rnd(2),rnd(2),rnd(2)};
                if(k[0]==1&&k[1]==1 ||k[0]==1&&k[2]==1 ||k[1]==1&&k[2]==1)
                {
                    for(int j=65;;j++)
                    {
                        Thread.sleep(rnd(100));
                        c=(char)j;
                        int rn=rnd(5);
                        if(rn==3)
                            break;
                        if(j==90)
                            j=65;
                    }
                }
                else
                {
                    for(int j=97;;j++)
                    {
                        Thread.sleep(rnd(100));
                        c=(char)j;
                        int rn=rnd(5);
                        if(rn==3)
                            break;
                        if(j==122)
                            j=97;
                    }
                }
                if(i==1)
                {
                    otp=otp.substring(0,p1)+c+otp.substring(p1+1);
                }
                else
                    otp=otp.substring(0,p2)+c+otp.substring(p2+1);
            }
        }
        catch(InterruptedException ie)
        {
        }
        return otp;
    }
    //Forgot Password Option InterFace
    static void forgotPassword()throws Exception
    {
        Scanner sc=new Scanner(System.in);
            Scanner read;
        System.out.println("\t\tInput The Method You Want To Use To Recover Your Password");
        System.out.println("\t\t1.Security Question");
        System.out.println("\t\t2.Trusted Contacts");
        System.out.println("\t\t3.Back");
        System.out.print("\n\tEnter Your Choice:- ");
        long id=change(username);
        int ch=0;
        while(ch==0)
            ch=input(3);
        if(ch==1)
        {
            int r=(int)Math.abs(rnd(9));
            if(r==1 || r==2)
            {
                System.out.println("\t\tInput Your Level On Tic-Tac-Toe Game ???");
                int num=0;
                while(num==0)
                {
                    num=input(1000000);
                }
                int lev=0;
                if(new File("C:\\Facebook\\"+id+"\\Game.fb").exists())
                {
                    read=new Scanner(new File("C:\\Facebook\\"+id+"\\Game.fb"));
                    lev=Integer.parseInt(read.nextLine());
                    read.close();
                }
                if(num-lev==1 || num-lev==0 || num-lev==-1)
                {
                    read=new Scanner(new File("C:\\Facebook\\fbmain.fb"));
                    read.nextLine();
                    while(read.hasNextLine())
                    {
                        read.nextLine();
                        String pass=read.nextLine();
                        long temp_id=Long.parseLong(read.nextLine());
                        if(id==temp_id)
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\t\tPassword: "+pass);
                            sc.nextLine();
                            return;
                        }
                    }
                    read.close();
                }
                else
                {
                    System.out.println("\t\tWrong Level !!! ");
                    System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                    System.out.println("\t\t\tGyanaranjan Sahoo");
                    System.out.println("\t\t\tSpeedX Productions");
                    System.out.println("\t\t\tPhone:- 7205595198");
                    sc.nextLine();
                    return;
                }
            }
            else if(r==3 || r==5)
            {
                System.out.println("\t\tEnter Number Of Friends You Have In Facebook ???");
                int num=0;
                while(num==0)
                    num=input(1000000);
                int lev=0;
                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                read.nextLine();
                while(read.hasNextLine())
                {
                    ++lev;
                    read.nextLine();
                    read.nextLine();
                }
                read.close();
                if(num-lev==1 || num-lev==0 || num-lev==-1)
                {
                    read=new Scanner(new File("C:\\Facebook\\fbmain.fb"));
                    read.nextLine();
                    while(read.hasNextLine())
                    {
                        read.nextLine();
                        String pass=read.nextLine();
                        long temp_id=Long.parseLong(read.nextLine());
                        if(id==temp_id)
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\t\tPassword: "+pass);
                            sc.nextLine();
                            return;
                        }
                    }
                    read.close();
                }
                else
                {
                    System.out.println("\t\tWrong Number !!! ");
                    System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                    System.out.println("\t\t\tGyanaranjan Sahoo");
                    System.out.println("\t\t\tSpeedX Productions");
                    System.out.println("\t\t\tPhone:- 7205595198");
                    sc.nextLine();
                    return;
                }
            }
            else if(r==6 || r==4)
            {
                String name[]=new String[3];
                System.out.println("\t\tInput A Friend's Name");
                for(int i=0;i<3;i++)
                {
                    name[i]=sc.nextLine().trim();
                    if(i!=2)
                    {
                        System.out.println("\t\tInput Another Friend's Name");
                    }
                }
                boolean pass[]=new boolean[3];
                for (int i=0;i<3;i++)
                {
                    pass[i]=false;
                }
                for(int i=0;i<3;i++)
                {
                    read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                    read.nextLine();
                    while(read.hasNextLine())
                    {
                        long fid=Long.parseLong(read.nextLine());
                        read.nextLine();
                        String user=change(fid).trim();
                        if(user.equalsIgnoreCase(name[i]))
                        {
                            pass[i]=true;
                        }
                    }
                    read.close();
                }
                if(name[0].equalsIgnoreCase(name[1]) ||name[1].equalsIgnoreCase(name[2]) ||name[0].equalsIgnoreCase(name[2]))
                {
                    System.out.println("\t\tSame Names Inputted !!! ");
                    System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                    System.out.println("\t\t\tGyanaranjan Sahoo");
                    System.out.println("\t\t\tSpeedX Productions");
                    System.out.println("\t\t\tPhone:- 7205595198");
                    sc.nextLine();
                    return;
                }
                if(pass[0] && pass[1] && pass[2])
                {
                    read=new Scanner(new File("C:\\Facebook\\fbmain.fb"));
                    read.nextLine();
                    while(read.hasNextLine())
                    {
                        read.nextLine();
                        String password=read.nextLine();
                        long temp_id=Long.parseLong(read.nextLine());
                        if(id==temp_id)
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\t\tPassword: "+password);
                            sc.nextLine();
                            return;
                        }
                    }
                    read.close();
                }
                else
                {
                    System.out.println("\t\tWrong Names Inputted !!! ");
                    System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                    System.out.println("\t\t\tGyanaranjan Sahoo");
                    System.out.println("\t\t\tSpeedX Productions");
                    System.out.println("\t\t\tPhone:- 7205595198");
                    sc.nextLine();
                    return;
                }
            }
            else
            {
                System.out.println("\t\tInput Your Date Of Joining in Format(dd/mm/yyyy)???");
                String birth=sc.nextLine().trim();
                boolean flag=true;
                try
                {
                    int d1=Integer.parseInt(birth.substring(0,birth.indexOf('/')));
                    int m1=Integer.parseInt(birth.substring(birth.indexOf('/')+1,birth.lastIndexOf('/')));
                    int y1=Integer.parseInt(birth.substring(birth.lastIndexOf('/')+1));
                }
                catch(NumberFormatException nfe)
                {
                    flag=false;
                }
                if(!flag)
                {
                    System.out.println("\t\tWrong Date Format!!! ");
                    System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                    System.out.println("\t\t\tGyanaranjan Sahoo");
                    System.out.println("\t\t\tSpeedX Productions");
                    System.out.println("\t\t\tPhone:- 7205595198");
                    sc.nextLine();
                    return;
                }
                int d=Integer.parseInt(birth.substring(0,birth.indexOf('/')));
                int m=Integer.parseInt(birth.substring(birth.indexOf('/')+1,birth.lastIndexOf('/')));
                int y=Integer.parseInt(birth.substring(birth.lastIndexOf('/')+1));
                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Profile.fb"));
                read.nextLine();
                read.nextLine();
                read.nextLine();
				read.nextLine();
				read.nextLine();
				read.nextLine();
                String pn=read.nextLine();
                read.close();
                int d2=Integer.parseInt(pn.substring(0,2));
                int m2=Integer.parseInt(pn.substring(3,5));
                int y2=Integer.parseInt(pn.substring(6));
                if(y2==y && m2==m &&(d2-d<=5 && d2-d>=-5 ))
                {
                    read=new Scanner(new File("C:\\Facebook\\fbmain.fb"));
                    read.nextLine();
                    while(read.hasNextLine())
                    {
                        read.nextLine();
                        String pass=read.nextLine();
                        long temp_id=Long.parseLong(read.nextLine());
                        if(id==temp_id)
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\t\tPassword: "+pass);
                            sc.nextLine();
                            return;
                        }
                    }
                    read.close();
                }
                else
                {
                    System.out.println("\t\tWrong Date !!! ");
                    System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                    System.out.println("\t\t\tGyanaranjan Sahoo");
                    System.out.println("\t\t\tSpeedX Productions");
                    System.out.println("\t\t\tPhone:- 7205595198");
                    sc.nextLine();
                    return;
                }
            }
        }
        else if(ch==2)
        {
            String r=getOtp();
            String name[]=new String[3];
            System.out.println("\t\tInput A Friend's Name");
            for(int i=0;i<3;i++)
            {
                name[i]=sc.nextLine().trim();
                if(i!=2)
                {
                    System.out.println("\t\tInput Another Friend's Name");
                }
            }
            boolean pass[]=new boolean[3];
            for (int i=0;i<3;i++)
            {
                pass[i]=false;
            }
            for(int i=0;i<3;i++)
            {
                read=new Scanner(new File("C:\\Facebook\\"+id+"\\Friends.fb"));
                read.nextLine();
                while(read.hasNextLine())
                {
                    long fid=Long.parseLong(read.nextLine());
                    read.nextLine();
                    String user=change(fid).trim();
                    if(user.equalsIgnoreCase(name[i]))
                    {
                        pass[i]=true;
                    }
                }
                read.close();
            }
            if(pass[0] && pass[1] && pass[2])
            {
                for(int i=0;i<3;i++)
                {
                    long user_id=change(name[i]);
                    PrintWriter pw=new PrintWriter(new FileWriter("C:\\Facebook\\"+user_id+"\\Notifications.fb"));
                    pw.println(username+" Sent The Password Reset Code To You !!!");
                    pw.println("\t\t\tPassword Reset Code Is :- "+r);
                    String spa="";
                    for(int spac=0;spac<68;spac++)
                    {
                        spa+=" ";
                    }
                    spa+="-"+getDate();
                    pw.println(spa);
                    pw.close();
                }
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\tPassword Reset Code Successfully Sent To Your Friends !!!");
                System.out.println("\t\tContact Them And Get The Code");
                System.out.println("Enter The Code Here :");
                String code=sc.nextLine();
                if(r.equals(code))
                {
                    read=new Scanner(new File("C:\\Facebook\\fbmain.fb"));
                    read.nextLine();
                    while(read.hasNextLine())
                    {
                        read.nextLine();
                        String password=read.nextLine();
                        long temp_id=Long.parseLong(read.nextLine());
                        if(id==temp_id)
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.out.println("\t\t\tPassword: "+password);
                            sc.nextLine();
                            return;
                        }
                    }
                    read.close();
                }
                else
                {
                    System.out.println("\t\tWrong Code Inputted !!! ");
                    System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                    System.out.println("\t\t\tGyanaranjan Sahoo");
                    System.out.println("\t\t\tSpeedX Productions");
                    System.out.println("\t\t\tPhone:- 7205595198");
                    sc.nextLine();
                    return;
                }
            }
            else
            {
                System.out.println("\t\tWrong Names Inputted !!! ");
                System.out.println("\t\tTo Unlock Your Account Please Contact :-");
                System.out.println("\t\t\tGyanaranjan Sahoo");
                System.out.println("\t\t\tSpeedX Productions");
                System.out.println("\t\t\tPhone:- 7205595198");
                sc.nextLine();
                return;
            }
        }
        else
        {
            return;
        }
    }
    //MainMenu Of Facebook-v2
    static void mainMenu()throws Exception
    {
        //Delete Scheduled Account
        Scanner sc=new Scanner(new File("C:\\Facebook\\Username.fb"));
        sc.nextLine();
        sc.nextLine();
        while(sc.hasNextLine())
        {
            String ide=sc.nextLine();
            sc.nextLine();
            File f=new File("C:\\Facebook\\"+ide+"\\DeadLine.fb");
            if(f.exists())
            {
                Scanner read=new Scanner(f);
                String s3=read.nextLine();
                read.close();
                String s2=getDate();
                int d2=Integer.parseInt(s2.substring(0,2));
                int d3=Integer.parseInt(s3.substring(0,2));
                int m2=Integer.parseInt(s2.substring(3,5));
                int m3=Integer.parseInt(s3.substring(3,5));
                int y2=Integer.parseInt(s2.substring(6));
                int y3=Integer.parseInt(s3.substring(6));
                int n2=0;
                int n3=0;
                for(int j=1;j<=(y2-2016);j++)
                {
                    for(int k=1;k<=12;k++)
                    n2+=days(k,2015+j);
                }
                for(int j=1;j<m2;j++)
                    n2+=days(j,y2);
                n2+=d2;
                for(int j=1;j<=(y3-2016);j++)
                {
                    for(int k=1;k<=12;k++)
                        n3+=days(k,2015+j);
                }
                for(int j=1;j<m3;j++)
                    n3+=days(j,y3);
                n3+=d3;
                if(n3+14<=n2)
                {
                    removeAccount(Long.parseLong(ide));
                }
            }
        }
        sc.close();
        boolean flag=true;
        sc=new Scanner(System.in);
        while(flag)
        {
            //Checks If Facebook Is Hidden If Not Hides It
            try
            {
                if(!new File("C:\\Facebook\\").isHidden())
                {
                    Process p=Runtime.getRuntime().exec("attrib +s +h C:\\Facebook");
                    p.waitFor();
                }
            }
            catch(InterruptedException ie)
            {}
            clear();
            System.out.println();
            System.out.println("*******************************************************************************");
            System.out.println("************                                                        ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************    |                MAIN  MENU                    |    ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************                                                        ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |       WHAT DO YOU WANT TO DO ?               |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |          1 - LOG IN                          |    ***********");
            System.out.println("************    |          2 - SIGN IN                         |    ***********");
            System.out.println("************    |          3 - FORGOT PASSWORD                 |    ***********");
            System.out.println("************    |          4 - ABOUT                           |    ***********");
            System.out.println("************    |          5 - EXIT                            |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************                                                        ***********");
            System.out.println("*******************************************************************************");
            System.out.print("\n\t\t\tPlease Enter Your Choice:-   ");
            int inp=0;
            while(inp==0)
            {
                inp=input(5);
            }
            if(inp==1)
            {
                logIn();
            }
            else if(inp==2)
            {
                signIn();
            }
            else if(inp==3)
            {
                boolean flag1=true;
                while(flag1)
                {
                    File f=new File("C:\\Facebook\\fbmain.fb");
                    Scanner read=new Scanner(f);
                    clear();
                        System.out.println();
                    System.out.println("\tPress # to Go To Main Menu");
                    System.out.println("\tInput Your Email Address ???");
                    String email=sc.nextLine();
                    email=email.trim();
                    if(email.equals("#"))
                    {
                        flag1=false;
                        continue;
                    }
                    boolean wemail=true;
                    if(email.equals(""))
                    {
                        System.out.println("\t\t\tEmail Not Inputted !!!");
                        sc.nextLine();
                        clear();
                        System.out.println();
                        continue;
                    }
                    read.nextLine();
                    while(read.hasNextLine())
                    {
                        String s=read.nextLine();
                        read.nextLine();
                        String k=read.nextLine();
                        if(s.equals(email))
                        {
                            wemail=false;
                            long id=Long.parseLong(k);
                            username=change(id);
                        }
                    }
                    read.close();
                    if(wemail)
                    {
                        System.out.println("\t\tEmail Address Not Found !!");
                        System.out.println("\t\tPlease Try Again !!");
                        System.out.println("\t\tPress # To Go To Main Menu");
                        System.out.println("\t\tPress Enter To Continue");
                        String ch=sc.nextLine();
                        if(ch.trim().equals("#"))
                        {
                            flag1=false;
                        }
                        else
                        {
                            continue;
                        }
                    }
                    else
                    {
                        forgotPassword();
                        flag1=false;
                    }
                }
            }
            else if(inp==4)
            {
                about();
            }
            else
            {
                exit(0);
            }
        }
    }
    //Prints The String With Time Interval
    static void print(String s)
    {
        try
        {
            for(int i=0;i<s.length();i++)
            {
                System.out.print(s.charAt(i));
                if(s.charAt(i)==' ')
                    continue;
                Thread.sleep(50);
            }
            System.out.println();
        }
        catch(Exception e)
        {
            return;
        }
    }
    //Displays About The Facebook
    static void about()throws Exception
    {
        clear();
        print("\t\t\t\t    CREDITS");
        System.out.print("\t\t\t");
        print("------------------------------");
        System.out.println();
        wait(500);
        print("\t\t\t       Facebook-v2.0");
        print("\t\t\t    --------------------");
        wait(500);
        print("\t\tFacebook is a Offline Social Networking Application.           ");
        wait(500);
        print("     You Can Enjoy More When You Have More Users.You Can Use This     *********");
        wait(500);
        print("     With Multi-Monitor System. You Can Use This To Chat Or Play      **     **");
        wait(500);
        print("                    Games Or Use Its Application                      **       ");
        wait(500);
        print("            If You Like This Project,Please Lend A Helping Hand     ******     ");
        wait(500);
        print("                    Call:- 7205595198                                 **       ");
        wait(500);
        print("                                                                      **       ");
        wait(500);
        print("                            Next Release:-Facebook-v3.0 (GUI Based)            ");
        wait(500);
        print("                                More Features Coming Soon...                   ");
        wait(500);
        print("   ***********                   Created By:- SpeedX                           ");
        wait(500);
        print("   **                            Class - Null          Section - Void          ");
        wait(500);
        print("   **                          School - Error 404                              ");
        wait(500);
        print("   ***********                   Teacher-In-Charge - Zuckerberg                ");
        wait(500);
        print("            **                                                                 ");
        wait(500);
        print("            **         If You Found Any  Problem Or Bug Please Inform Me...    ");
        wait(500);
        print("   ***********                Phone:-7205595198                                ");
        wait(500);
        for(int i=0;i<26;i++)
        {
            System.out.println();
            wait(500);
        }
    }
    //Gets A valid Input
    static int input(int n)throws Exception
    {
        Scanner sc=new Scanner(System.in);
        try
        {
            String str=sc.nextLine().trim();
            int in=Integer.parseInt(str);
            if(in>=1 && in<=n)
            return in;
            else
            {
                System.out.print("Please Input a Number Between 1 to " + n+" :- ");
                return 0;
            }
        }
        catch(NumberFormatException nf)
        {
            System.out.print("\n\t\t\tPlease Input A Number :- ");
            return 0;
        }
    }
    //Exits From Facebook
    static void exit(int choice)throws Exception
    {
        clear();
        try
        {
            new File("C:\\Facebook\\"+change(username)+"\\online.fb").delete();
        }
        catch(Exception e)
        {}
        System.out.println("          _____  _                    _         _     _             _ ");
        System.out.println("         (_   _)( )                  ( )       ( )   ( )           ( )");
        System.out.println("           | |  | |__     _ _   ___  | |/')    `\\`\\_/'/'_    _   _ | |");
        System.out.println("           | |  |  _ `\\ /'_` )/' _ `\\| , <       `\\ /'/'_`\\ ( ) ( )| |");
        System.out.println("           | |  | | | |( (_| || ( ) || |\\`\\       | |( (_) )| (_) || |");
        System.out.println("           (_)  (_) (_)`\\__,_)(_) (_)(_) (_)      (_)`\\___/'`\\___/'(_)");
        System.out.println("                                                                    _");;
        System.out.println("                        ~_^ For Using Facebook! ^_~                (_)");
        for(int i=0;i<9;i++)
            System.out.println();
        wait(5000);
            String close[]={"Saving Data","Encrypting Files","Un-Loading Games","Disconnecting Server"};
        clear();
        int m=0,t=100;
        System.out.print("\t\t\tClosing Facebook ");
        wait(1000);
        System.out.print(".");
        wait(700);
        System.out.print(".");
        wait(1000);
        System.out.print(".");
        wait(700);
        if(choice!=1)
		{
			System.out.println("\n\t\t\tGoing Back "+t+" %");
			System.out.print("\t\t\t");
			for(int j=1;j<=(t/5);j++)
			{
				System.out.print((char)2);
			}
			System.out.println();
			wait(900);clear();
			System.out.print("\t\t\tClosing Facebook ...");
			while(m<15 || m>23)
			{
				m=rnd(25);
			}
			DataInputStream dis=new DataInputStream(System.in);
			int i2=0;
			while(dis.available()==0)
			{
				if(i2++==3)
				break;
					System.out.print("\n\t\t\t" + close[i2]);
					t-=m;
					if(t<0)
						t=0;
					System.out.println("\n\t\t\tLoading "+t+" %");
					System.out.print("\t\t\t");
					for(int j=1;j<=(t/5);j++)
						System.out.print((char)2);
					System.out.println();
					wait(900);clear();
					System.out.print("\t\t\tClosing Facebook ...");
					if(m<20)
						m+=3;
					else
						m+=2;
			}
		}
        System.out.println("\n\t\t\tFacebook Closed");
        System.out.print("\t\t\t");
        wait(600);clear();
        Toolkit.getDefaultToolkit().beep();
        System.out.println("            _ +----------------------------------------------------+ _    ");
        System.out.println("           /o)|                    Created By :-                   |(o\\   ");
        System.out.println("          / / |                  Gyanaranjan Sahoo                 | \\ \\  ");
        System.out.println("         ( (_ |  _          Blessed Sacrament High School       _  | _) ) ");
        System.out.println("        ((\\ \\)+-/o)--------------------------------------------(o\\-+(/ /))");
        System.out.println("        (\\\\\\ \\_/ /                                              \\ \\_/ ///)");
        System.out.println("         \\      /                                                \\      / ");
        System.out.println("          \\____/                                                  \\____/  ");
        for(int j=0;j<6;j++)
        {
            wait(500);
            System.out.println();
        }
        Toolkit.getDefaultToolkit().beep();
        if(choice!=2)
        {
            System.out.println("\t\t\tTaking Backup!!!");
            System.out.println("\t\t\tPlease Wait...");
            for(int i=1;i<=5;i++)
            {
                if(new File("C:\\Facebook\\error"+i+".fb").exists())
                {
                    new File("C:\\Facebook\\error"+i+".fb").delete();
                }
            }
            takeBackup();
            System.out.println("\t\t\tBackup Complete!!!");
        }
        System.exit(0);
    }
    //Delete Facebook Files To Restore It
    static void deleteFacebook()throws Exception
    {
        String s=displayContents(new File("C:\\Facebook\\"),"");
        int p=0,count=0;
        while(s.indexOf('\n',p)!=-1)
        {
            p=s.indexOf('\n',p)+1;
            ++count;
        }
        String files[]=new String[count];
        count=p=0;
        while(s.indexOf('\n',p)!=-1)
        {
            files[count++]=s.substring(p,s.indexOf('\n',p));
            p=s.indexOf('\n',p)+1;
        }
        for(int i=files.length-1;i>=0;i--)
        {
            if(files[i].endsWith("error5.fb") ||files[i].endsWith("Facebook\\") )
                continue;
            if(!new File(files[i]).delete())
            {
                new File(files[i]).deleteOnExit();
            }
        }
        System.out.println("\t\tCannot Connect To Server...");
        System.out.println("\t\tPlease Restart Facebook!!!");
        System.out.println("\t\t\tPress Enter To Continue..");
        new Scanner(System.in).nextLine();
        System.exit(0);
    }
    //Displays All The Files In That Folder Recursively
    static String displayContents(File dir,String s)throws Exception
    {
        File files[]=dir.listFiles();
        for(File file:files)
        {
            if(file.isDirectory())
            {
                s+=file.getCanonicalPath()+"\n";
                s+=displayContents(new File(file.getCanonicalPath()),"");
            }
            else
            {
                s+=file.getCanonicalPath()+"\n";
            }
        }
        return s;
    }
    //Edits String To Display Escape Sequence
    static String changeString(String s)throws Exception
    {
        String ans="";
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='\\')
                ans+="\\\\";
            else if(s.charAt(i)=='\"')
                ans+="\\\"";
            else
                ans+=s.charAt(i);
        }
        return ans;
    }
    //Takes Backup Of Facebook Files
    static void takeBackup()throws Exception
    {
        String name=displayContents(new File("C:\\Facebook\\"),"");
        int line=0;
        for(int i=0;i<name.length();i++)
        {
            if(name.charAt(i)=='\n')
            {
                ++line;
            }
        }
        String file[]=new String[line];
        line=0;
        String word="";
        for(int i=0;i<name.length();i++)
        {
            if(name.charAt(i)=='\n')
            {
                file[line++]=word;
                word="";
            }
            else
            {
                word+=name.charAt(i);
            }
        }
        int line1=0,fnc=2;
        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("FacebookBackup.java")));
        pw.println("import java.io.*;");
        pw.println("class FacebookBackup");
        pw.println("{");
        pw.println("public static void main(String arg[])throws Exception");
        pw.println("{");
        pw.println("PrintWriter pw;");
        pw.println("new File(\"C:\\\\Facebook\\\\\").mkdir();");
        pw.println("main1();");
        pw.println("System.out.println(\"Files Successfully Restored !!!\");");
        pw.println("}");
        pw.println("static void main1()throws Exception");
        pw.println("{");
        pw.println("PrintWriter pw;");
        for(String nm:file)
        {
            File f=new File(nm);
            nm=changeString(nm);
            if(f.isDirectory())
            {
                pw.println("new File(\""+nm+"\").mkdir();");
                ++line1;
            }
            else
            {
                RandomAccessFile raf=new RandomAccessFile(nm,"r");
                long fl=raf.length();
                if(raf.length()/1024>=128)
                    continue;
                pw.println("pw=new PrintWriter(new BufferedWriter(new FileWriter(\""+nm+"\")));");
                ++line1;
                pw.print("pw.print(\"");
                ++line1;
                Scanner read=new Scanner(f);
                boolean flag=true;
                while(read.hasNextLine())
                {
                    String rd=read.nextLine();
                    rd=changeString(rd);
                    pw.print(rd+"\\n");
                    ++line1;
                    if(line1>8000)
                    {
                        pw.println("\");");
                        pw.println("pw.close();");
                        flag=false;
                        line1=7;
                        pw.println("main"+fnc+"();");
                        pw.println("}");
                        pw.println("static void main"+fnc+"()throws Exception");
                        pw.println("{");
                        pw.println("PrintWriter pw;");
                        pw.println("pw=new PrintWriter(new BufferedWriter(new FileWriter(\""+nm+"\",true)));");
                        pw.print("pw.print(\"");
                        fnc++;
                    }
                }
                read.close();
                {
                    pw.println("\");");
                    pw.println("pw.close();");
                    ++line1;
                }
            }
        }
        pw.println("}");
        pw.println("}");
        pw.close();
        try
        {
            Process p;
            p=Runtime.getRuntime().exec("javac FacebookBackup.java");
            p.waitFor();
        }
        catch(InterruptedException ie)
        {}
    }
    //Clears Screen
    static void clear()throws Exception
    {
        for(int i=0;i<500;i++)
        {
            System.out.println();
        }
    }
    //Waits Specified Time
    static void wait(int r)throws Exception
    {
        try
        {
            Thread.sleep(r);
        }
        catch(InterruptedException ie)
        {
            return;
        }
    }
    //Restores Facebook From Backup
    static boolean restoreFacebook()throws Exception
    {
        new File("C:\\Facebook\\error5.fb").delete();
        new File("C:\\Facebook\\").delete();
        if(new File("FacebookBackup.class").exists())
        {
            System.out.println("\t\t\tRestoring Facebook!!!");
            System.out.println("\t\tPlease Wait...");
            try
            {
                Process p=Runtime.getRuntime().exec("java FacebookBackup");
                p.waitFor();
                return true;
            }
            catch(InterruptedException ie)
            {}
        }
        return false;
    }
    public static void main(String arg[])throws Exception
    {
        //Turns Numlock On
        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK,Boolean.TRUE);
        clear();
        if(new File("C:\\Facebook\\error5.fb").exists())
        {
            if(!new File("FacebookBackup.class").exists())
            {
                System.out.println("\t\t\tFacebookBackup.java Is Not Compiled Please Compile It!!!");
                System.out.println("\t\tThen Restart Facebook...\nPress Enter To Continue...");
                new Scanner(System.in).nextLine();
                boolean flag3=true;
                while(flag3)
                {
                    System.out.print("\n\t\t\tDo You Want To Compile It And Restart Facebook(Y/N): ");
                    String ipl=new Scanner(System.in).nextLine();
                    ipl=ipl.trim();
                    if(ipl.equalsIgnoreCase("Y"))
                    {
                        System.exit(0);
                    }
                    else if(ipl.equalsIgnoreCase("N"))
                    {
                        flag3=false;
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("\t\tPlease Enter A Valid Input");
                    }
                }
            }
            else
            {
                restoreFacebook();
            }
        }
        File f=new File("C:\\Facebook");
        if(!f.exists())
        {
            if(!restoreFacebook())
            {
                f.mkdir();
                f=new File("C:\\Facebook\\fbmain.fb");
                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                pw.println();
                pw.close();
                f=new File("C:\\Facebook\\username.fb");
                pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                pw.println("99999");
                pw.println("     ");
                pw.close();
                f=new File("C:\\Facebook\\ScoreBoard.fb");
                pw=new PrintWriter(new BufferedWriter(new FileWriter(f)));
                pw.println("SpeedX");
                pw.println("7");
                pw.println("Xizzy");
                pw.println("6");
                pw.println("Champion");
                pw.println("5");
                pw.println("Blaze");
                pw.println("5");
                pw.println("Buzz");
                pw.println("5");
                pw.println("Spady");
                pw.println("4");
                pw.println("Gyanaranjan");
                pw.println("3");
                pw.println("Extreme Gaming");
                pw.println("3");
                pw.println("Blizzard");
                pw.println("2");
                pw.println("Hunter");
                pw.print("1");
                pw.close();
            }
        }
        String display[]={"Connecting To Server","Decrypting Application","Loading Application","Loading Games","Ready To Use"};
        int m=0,add=0;
        clear();
        System.out.print("\t\t\tStarting Application ");
        wait(700);
        System.out.print(".");
        wait(700);
        System.out.print(".");
        wait(700);
        System.out.print(".");
        while(m<10 || m>21)
        {
            m=rnd(21);
        }
        DataInputStream dis=new DataInputStream(System.in);
        int i2=0;
        while(dis.available()==0)
        {
            if(i2++==4)
            break;
                add=add+m;
                System.out.print("\n\t\t\t" + display[i2]);
                System.out.println("\n\t\t\tLoading "+add+" %");
                System.out.print("\t\t\t");
                for(int j=1;j<=(add/5);j++)
                {
                    System.out.print((char)2);
                }
                System.out.println();
                wait(700);
                clear();
                   System.out.println();
                System.out.print("\t\t\tStarting Application ...");
                if(i2==0)
                m=18;
                else if(i2==1)
                m=17;
                else if(i2==2)
                m=20;
                else if(i2==3)
                m=19;
        }
            System.out.println("\n\t\t\tLoad Complete");
            System.out.print("\t\t\t");
            for(int i=1;i<=20;i++)
            System.out.print((char)2);
            System.out.println();
            wait(700);
            clear();
            System.out.println("\t@@      @@  @@@@@@@  @@     @@@@@@  @@@@@@@  @@     @@  @@@@@@@");
            System.out.println("\t@@      @@  @@       @@     @@      @@   @@  @@@@ @@@@  @@");
            System.out.println("\t@@  @@  @@  @@@@@@@  @@     @@      @@   @@  @@  @  @@  @@@@@@@");
            System.out.println("\t@@  @@  @@  @@       @@     @@      @@   @@  @@     @@  @@");
            System.out.println("\t@@@@@@@@@@  @@@@@@@  @@@@@@ @@@@@@  @@@@@@@  @@     @@  @@@@@@@\n");
            System.out.println("\t\t\t\t@@@@@@@@@@  @@@@@@@");
            System.out.println("\t\t\t\t    @@      @@   @@");
            System.out.println("\t\t\t\t    @@      @@   @@");
            System.out.println("\t\t\t\t    @@      @@   @@");
            System.out.println("\t\t\t\t    @@      @@@@@@@");
            System.out.println("               @                   ");
            System.out.println("  @@@@@@@    @@ @@      @@@@@@@  @@@@@@@  @@@@@@    @@@@@@@  @@@@@@@  @@   @@ ");
            System.out.println("  @@        @@   @@     @@       @@       @@    @)  @@   @@  @@   @@  @@  @@  ");
            System.out.println("  @@@@@    @@@@@@@@@    @@       @@@@@    @@@@@@    @@   @@  @@   @@  @@@@   ");
            System.out.println("  @@      @@       @@   @@       @@       @@    @)  @@   @@  @@   @@  @@  @@  ");
            System.out.println("  @@     @@         @@  @@@@@@@  @@@@@@@  @@@@@@    @@@@@@@  @@@@@@@  @@   @@ ");
               System.out.println("\t\t\tPress Enter To Continue");
            new Scanner(System.in).nextLine();
            clear();
        boolean flag=true;
        while(flag)
        {
            try
            {
                mainMenu();
            }
            catch(Exception e)
            {
                clear();
                Toolkit.getDefaultToolkit().beep();
                System.out.println("Some Exception Occured !!!");
                if(!new File("C:\\Facebook\\errorlog.fb").exists())
                    new File("C:\\Facebook\\errorlog.fb").createNewFile();
                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\errorlog.fb",true)));
                        pw.println("Exception: "+e);
                pw.close();
                for(int i=1;i<=5;i++)
                {
                    if(new File("C:\\Facebook\\error"+i+".fb").exists())
                    {
                        continue;
                    }
                    else
                        new File("C:\\Facebook\\error"+i+".fb").createNewFile();
                    break;
                }
                if(new File("C:\\Facebook\\error5.fb").exists())
                {
                    deleteFacebook();
                }
                removeDuplicateLine("C:\\Facebook\\errorlog.fb");
                boolean flag1=true;
                while(flag1)
                {
                    System.out.print("\n\t\t\tDo You Want To Continue(Y/N): ");
                    String ipl=new Scanner(System.in).nextLine();
                    ipl=ipl.trim();
                    if(ipl.equalsIgnoreCase("Y"))
                    {
                        username="";
                        thread_id=0;
                        flag1=false;
                    }
                    else if(ipl.equalsIgnoreCase("N"))
                    {
                        exit(2);
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("\t\tPlease Enter A Valid Input");
                    }
                }
            }
        }
    }
}
class TicTacToe
{
   static String position[]=new String[9];
   static String nm1,nm2,user,comp;
   static int level,xp,coins,defeatlevel,match_3=0,won;
   static boolean cheat,xpbooster,coinbooster;
   //shows main screen
    static void clear()
    {
        clr();
        System.out.println();
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("******************                                          *******************\n");
        System.out.print("******************     Welcome to SpeedX Productions!!!     *******************\n");
        System.out.print("******************                                          *******************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("******************     --------------------------------      ******************\n");
        System.out.print("******************     ||                            ||      ******************\n");
        System.out.print("******************     ||   The Game of Tic-Tac-Toe  ||      ******************\n");
        System.out.print("******************     ||                            ||      ******************\n");
        System.out.print("******************     --------------------------------      ******************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("\n\n\n\n\n");
        for(int i=0;i<9;i++)
        {
            position[i]=" ";
        }
        nm1=Facebook.username;
        nm2="SpeedX(Player 2)";
    }
    // prints table
   static void print()
    {
        System.out.println("\t\t\t\t     |     |     ");
        System.out.println("\t\t\t\t  "+position[0]+"  |  "+position[1]+"  |   "+position[2]);
        System.out.println("\t\t\t\t-----|-----|-----");
        System.out.println("\t\t\t\t  "+position[3]+"  |  "+position[4]+"  |   "+position[5]);
        System.out.println("\t\t\t\t-----|-----|-----");
        System.out.println("\t\t\t\t  "+position[6]+"  |  "+position[7]+"  |   "+position[8]);
        System.out.println("\t\t\t\t     |     |     ");
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");
    }
    //checks For Valid Input
   static boolean check(int i,String s)
   {
        if(position[i-1].equals(" "))
        {
            position[i-1]=s;
            return false;
        }
        else
        return true;
   }
    //Logic For Computer To Put Symbol
   static int hardGame()
   {
        if(position[0].equals(comp) && position[1].equals(comp) && position[2].equals(" "))
            return 2;
        else if(position[3].equals(comp) && position[4].equals(comp) && position[5].equals(" "))
            return 5;
        else if(position[6].equals(comp) && position[7].equals(comp) && position[8].equals(" "))
            return 8;
        else if(position[1].equals(comp) && position[2].equals(comp) && position[0].equals(" "))
            return 0;
        else if(position[4].equals(comp) && position[5].equals(comp) && position[3].equals(" "))
            return 3;
        else if(position[7].equals(comp) && position[8].equals(comp) && position[6].equals(" "))
            return 6;
        else if(position[0].equals(comp) && position[2].equals(comp) && position[1].equals(" "))
            return 1;
        else if(position[3].equals(comp) && position[5].equals(comp) && position[4].equals(" "))
            return 4;
        else if(position[6].equals(comp) && position[8].equals(comp) && position[7].equals(" "))
            return 7;
        else if(position[0].equals(comp) && position[3].equals(comp) && position[6].equals(" "))
            return 6;
        else if(position[1].equals(comp) && position[4].equals(comp) && position[7].equals(" "))
            return 7;
        else if(position[2].equals(comp) && position[5].equals(comp) && position[8].equals(" "))
            return 8;
        else if(position[3].equals(comp) && position[6].equals(comp) && position[0].equals(" "))
            return 0;
        else if(position[4].equals(comp) && position[7].equals(comp) && position[1].equals(" "))
            return 1;
        else if(position[5].equals(comp) && position[8].equals(comp) && position[2].equals(" "))
            return 2;
        else if(position[0].equals(comp) && position[6].equals(comp) && position[3].equals(" "))
            return 3;
        else if(position[1].equals(comp) && position[7].equals(comp) && position[4].equals(" "))
            return 4;
        else if(position[2].equals(comp) && position[8].equals(comp) && position[5].equals(" "))
            return 5;
        else if(position[0].equals(comp) && position[4].equals(comp) && position[8].equals(" "))
            return 8;
        else if(position[4].equals(comp) && position[8].equals(comp) && position[0].equals(" "))
            return 0;
        else if(position[0].equals(comp) && position[8].equals(comp) && position[4].equals(" "))
            return 4;
        else if(position[2].equals(comp) && position[4].equals(comp) && position[6].equals(" "))
            return 6;
        else if(position[6].equals(comp) && position[4].equals(comp) && position[2].equals(" "))
            return 2;
        else if(position[6].equals(comp) && position[2].equals(comp) && position[4].equals(" "))
            return 4;
        else if(position[0].equals(user) && position[1].equals(user) && position[2].equals(" "))
            return 2;
        else if(position[3].equals(user) && position[4].equals(user) && position[5].equals(" "))
            return 5;
        else if(position[6].equals(user) && position[7].equals(user) && position[8].equals(" "))
            return 8;
        else if(position[1].equals(user) && position[2].equals(user) && position[0].equals(" "))
            return 0;
        else if(position[4].equals(user) && position[5].equals(user) && position[3].equals(" "))
            return 3;
        else if(position[7].equals(user) && position[8].equals(user) && position[6].equals(" "))
            return 6;
        else if(position[0].equals(user) && position[2].equals(user) && position[1].equals(" "))
            return 1;
        else if(position[3].equals(user) && position[5].equals(user) && position[4].equals(" "))
            return 4;
        else if(position[6].equals(user) && position[8].equals(user) && position[7].equals(" "))
            return 7;
        else if(position[0].equals(user) && position[3].equals(user) && position[6].equals(" "))
            return 6;
        else if(position[1].equals(user) && position[4].equals(user) && position[7].equals(" "))
            return 7;
        else if(position[2].equals(user) && position[5].equals(user) && position[8].equals(" "))
            return 8;
        else if(position[3].equals(user) && position[6].equals(user) && position[0].equals(" "))
            return 0;
        else if(position[4].equals(user) && position[7].equals(user) && position[1].equals(" "))
            return 1;
        else if(position[5].equals(user) && position[8].equals(user) && position[2].equals(" "))
            return 2;
        else if(position[0].equals(user) && position[6].equals(user) && position[3].equals(" "))
            return 3;
        else if(position[1].equals(user) && position[7].equals(user) && position[4].equals(" "))
            return 4;
        else if(position[2].equals(user) && position[8].equals(user) && position[5].equals(" "))
            return 5;
        else if(position[0].equals(user) && position[4].equals(user) && position[8].equals(" "))
            return 8;
        else if(position[4].equals(user) && position[8].equals(user) && position[0].equals(" "))
            return 0;
        else if(position[0].equals(user) && position[8].equals(user) && position[4].equals(" "))
            return 4;
        else if(position[2].equals(user) && position[4].equals(user) && position[6].equals(" "))
            return 6;
        else if(position[6].equals(user) && position[4].equals(user) && position[2].equals(" "))
            return 2;
        else if(position[6].equals(user) && position[2].equals(user) && position[4].equals(" "))
            return 4;
        else if(position[0].equals(user) && position[4].equals(comp) && position[8].equals(user))
            return 5;
        else if(position[4].equals(user) && position[8].equals(user) && position[0].equals(comp))
            return 6;
        else if(position[2].equals(user) && position[4].equals(comp) && position[6].equals(user))
            return 3;
        else if(position[4].equals(" "))
            return 4;
        else if(position[0].equals(" "))
            return 0;
        else
            return 3;
    }
    //checks if Any One Wins
   static String win()
   {
        if(!position[0].equals(" ") && position[0].equals(position[3]) && position[3].equals(position[6]))
        return position[0];
        else if(!position[1].equals(" ") && position[1].equals(position[4]) && position[4].equals(position[7]))
        return position[1];
        else if(!position[2].equals(" ") && position[2].equals(position[5]) && position[5].equals(position[8]))
        return position[2];
        else if(!position[0].equals(" ") && position[0].equals(position[1]) && position[1].equals(position[2]))
        return position[0];
        else if(!position[3].equals(" ") && position[3].equals(position[4]) && position[4].equals(position[5]))
        return position[3];
        else if(!position[6].equals(" ") && position[6].equals(position[7]) && position[7].equals(position[8]))
        return position[6];
        else if(!position[0].equals(" ") && position[0].equals(position[4]) && position[4].equals(position[8]))
        return position[0];
        else if(!position[2].equals(" ") && position[2].equals(position[4]) && position[4].equals(position[6]))
        return position[2];
        else
        return "";
    }
    //Shows Who Won
   static boolean check()
   {
        boolean flag=true;
        String s="";
        s=win();
        if(s.equals("X"))
        {
            if(user.equals("X"))
            {
                System.out.println(nm1+" Won It !!!");
                won=0;
            }
            else
            {
                System.out.println(nm2+" Won It !!!");
                won=2;
            }
            wait(1500);
            Toolkit.getDefaultToolkit().beep();
            return true;
        }
        else if(s.equals("O"))
        {
            if(user.equals("O"))
            {
                System.out.println(nm1+" Won It !!!");
                won=0;
            }
            else
            {
                System.out.println(nm2+" Won It !!!");
                won=2;
            }
            wait(1500);
            Toolkit.getDefaultToolkit().beep();
            return true;
        }
        else
        {
            for(int i=0;i<position.length;i++)
            {
                if(position[i]==" ")
                flag=false;
            }
            if(flag)
            {
                System.out.println("Its Tie Between "+nm1+" And "+nm2+" !!!");
                won=1;
                wait(1500);
                Toolkit.getDefaultToolkit().beep();
                return true;
            }
        }
        return false;
    }
   //Generates Random Number
    static int rnd(int r)
   {
            int k=0;
            while(k<=0 || k>r)
            k=(int)Math.abs(System.nanoTime()%r+1);
            return k;
   }
   //Easy Level Computer Logic
    static void easy()
    {
        boolean flag=true;
        while(flag)
        {
            int i=(int)Math.abs(rnd(9));
            if (i>=1 && i<=9)
            flag=check(i,comp);
        }
   }
   //Gets Input From User
   static void userInput()
    {
        Scanner sc=new Scanner(System.in);
        String us="";
        boolean flag=true;
        System.out.println("\t"+Facebook.username+" Turn:");
        System.out.println("Please Input the block number to input " + user);
        us=user;
        do
        {
            int i=0;
            while(i==0)
                i=input(9);
            flag=check(i,us);
            if(flag)
                System.out.println("It is Already Given!!! Please input another number");
        }
        while(flag);
   }
   //Computer Plays A Easy Move
   static void easyMode()
   {
       while(true)
       {
           if(user=="X")
           {
               userInput();
               wait(1500);
               clr();
               System.out.println();
               print();
               if(check())
                   break;
               easy();
               wait(1500);
               clr();
               System.out.println();
               print();
               if(check())
                   break;
            }
           if(comp=="X")
            {
                easy();
                wait(1500);
                clr();
                System.out.println();
                print();
                if(check())
                   break;
                userInput();
                wait(1500);
                clr();
                System.out.println();
                print();
                if(check())
                   break;
            }
         }
   }
    //Gets A valid Input
    static int input(int n)
    {
        Scanner sc=new Scanner(System.in);
        try
        {
            String str=sc.nextLine().trim();
            int in=Integer.parseInt(str);
            if(in>=1 && in<=n)
            return in;
            else
            {
                System.out.print("Please Input a Number Between 1 to " + n+" :- ");
                return 0;
            }
        }
        catch(NumberFormatException nf)
        {
            System.out.print("\n\t\t\tPlease Input A Number :- ");
            return 0;
        }
    }
    //checks For Overlapping
   static void put()
    {
        int p=hardGame();
        if(position[p].equals(" "))
        position[p]=comp;
        else
        easy();
    }
    //Computer Plays A Hard Move
   static void hardMode()
   {
       while(true)
          {
            if(user=="X")
            {
                userInput();
                wait(1500);
                clr();
                System.out.println();
                print();
                if(check())
                    break;
                put();
                wait(1500);
                clr();
                System.out.println();
                print();
                if(check())
                    break;
            }
            if(comp=="X")
            {
                put();
                wait(1500);
                clr();
                System.out.println();
                print();
                if(check())
                    break;
                userInput();
                wait(1500);
                clr();
                System.out.println();
                print();
                if(check())
                    break;
                }
          }
   }
   //Option To Play With Game Legends
    static void compPlay()throws Exception
    {
        Scanner sc=new Scanner(System.in);
        for(int i=0;i<9;i++)
        {
            position[i]=" ";
        }
        String Arr[]={"God Of Death","Spady","Gyana","Blaze","Buzz","Blaze","Buzz","Blizard","Hunter","Extreme Gamer","Im-Mortals","God Of Death","Spady","Gyana","Blaze","Buzz","Blizard","Hunter","Extreme Gamer","Im-Mortals","SpeedX"};
        boolean flag6=false;
        for(int i=0;i<21;i++)
        {
            if(Arr[i].equalsIgnoreCase(nm2))
                flag6=true;
        }
        if(flag6)
        {
            while(true)
            {
                System.out.println("\n\n");
                System.out.println("\t\t\tWhat symbol do you take ???");
                System.out.println("\t\t\tPress X or O");
                String inz=sc.nextLine().trim();
                if(inz.equalsIgnoreCase("X"))
                {
                    user="X";
                    comp="O";
                    break;
                }
                else if(inz.equalsIgnoreCase("O") || inz.equalsIgnoreCase("0"))
                {
                    comp="X";
                    user="O";
                    break;
                }
                else
                {
                    System.out.println("Please Input A Valid Character");
                    wait(700);
                }
            }
            System.out.println("\t\t\tYou Chosed \" "+ user + " \"");
            System.out.println("");
            wait(1500);
            clr();
                System.out.println();
            print();
            hardMode();
            if(won==0)
            {
                System.out.println("\t\t\tCoins Earned:-"+30*level);
                System.out.println("\t\t\tXP Earned:-"+30*level);
                xp=xp+(30*level);
                coins=coins+(30*level);
            }
            else
            {
                System.out.println("\t\t\tCoins Earned:-"+10*level);
                System.out.println("\t\t\tXP Earned:-"+10*level);
                xp=xp+(10*level);
                coins=coins+(10*level);
            }
            sc.nextLine();
            increaseLevel();
            return;
        }
        if(coins>=20*level*2)
        {
            coins=coins-(20*level);
        }
        else
        {
            System.out.println("\t\tYou Don't Have Enough Money To Play !!!");
            System.out.println("\t\tPlease Buy A Coin Pack !!!");
            return;
        }
        while(true)
        {
            System.out.println("\n\n");
            System.out.println("\t\t\tWhat symbol do you take ???");
            System.out.println("\t\t\tPress X or O");
            String inz=sc.nextLine().trim();
            if(inz.equalsIgnoreCase("X"))
            {
                user="X";
                comp="O";
                break;
            }
            else if(inz.equalsIgnoreCase("O") || inz.equalsIgnoreCase("0"))
            {
                comp="X";
                user="O";
                break;
            }
            else
            {
                System.out.println("Please Input A Valid Character");
                wait(700);
            }
        }
        System.out.println("\t\t\tYou Chosed \" "+ user + " \"");
        System.out.println("");
        int c;
        wait(1500);
        clr();
         System.out.println();
        print();
        int temp[]={0,0,0};
        if(cheat)
       {
            boolean flag=true;
            while(flag)
            {
                System.out.println("\n\t\t\tDo You Want To Use Cheat(Y/N)?");
                String ipl=sc.nextLine();
                ipl=ipl.trim();
                if(ipl.equalsIgnoreCase("Y"))
                {
                    clr();
                    System.out.println();
                    flag=false;
                    cheat=false;
                    temp[0]=1;
                    won=0;
                    for(int i=0;i<9;i++)
                    {
                        position[i]=user;
                    }
                    print();
                    System.out.println("\n\n\n\n\n");
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("\t\t"+nm1+" Won !!!");
                }
                else if(ipl.equalsIgnoreCase("N"))
                {
                    flag=false;
                }
                else
                {
                    System.out.println("\t\tPlease Enter A Valid Input");
                }
            }
       }
        if(coinbooster)
        {
            boolean flag=true;
            while(flag)
            {
                System.out.println("\n\t\t\tDo You Want To Use Coin Booster(Y/N)?");
                String ipl=sc.nextLine();
                ipl=ipl.trim();
                if(ipl.equalsIgnoreCase("Y"))
                {
                    flag=false;
                    coinbooster=false;
                    temp[1]=1;
                }
                else if(ipl.equalsIgnoreCase("N"))
                {
                    flag=false;
                }
                else
                {
                    System.out.println("\t\tPlease Enter A Valid Input");
                }
            }
       }
        if(xpbooster)
        {
            boolean flag=true;
            while(flag)
            {
                System.out.println("\n\t\t\tDo You Want To Use XP Booster(Y/N)?");
                String ipl=sc.nextLine();
                ipl=ipl.trim();
                if(ipl.equalsIgnoreCase("Y"))
                {
                    flag=false;
                    xpbooster=false;
                    temp[2]=1;
                }
                else if(ipl.equalsIgnoreCase("N"))
                {
                    flag=false;
                }
                else
                {
                    System.out.println("\t\tPlease Enter A Valid Input");
                }
            }
       }
       if(temp[0]==0)
       {
           if(getLevel(Facebook.change(nm2))<=30)
           {
               easyMode();
            }
            else if(getLevel(Facebook.change(nm2))>30)
            {
                hardMode();
            }
       }
       if(won==0)
        {
            Scanner read1=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
            int arrComp[]={0,0,0,0,0,0,0,0,0,0};
            while(read1.hasNextLine())
            {
                String s=read1.nextLine();
                boolean flag=s.equalsIgnoreCase("!");
                while(flag)
                {
                    flag=read1.hasNextLine();
                    if(flag)
                    {
                        arrComp[Integer.parseInt(read1.nextLine())]=1;
                    }
                }
            }
            read1.close();
            String getCoins[]={"Coin:- 40  XP:- 20","Coin:- 70  XP:- 50","Coin:- 80  XP:- 60","Coin:- 60  XP:- 60","Coin:- 80  XP:- 80","Coin:- 100  XP:- 150","Coin:- 100  XP:- 200","Coin:- 500  XP:- 500","Coin:- 100  XP:- 300","Coin:- 5000  XP:- 5000"};
            int i=6;
            if(arrComp[i]==0)
            {
                if(level==11)
                {
                    System.out.println("Achievement 7 Completed !!!");
                    System.out.println("\t"+getCoins[i]);
                    coins+=100;
                    xp+=200;
                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb",true)));
                    pw.print("\n"+6);
                    pw.close();
                    increaseLevel();
                }
            }
           int tempxp=20*level;
           if(temp[2]==1)
           {
               tempxp*=2;
           }
           xp+=tempxp;
           System.out.println("\t\tXP Earned: "+tempxp);
           int tempcoins=50*level;
           if(temp[1]==1)
           {
               tempcoins*=2;
           }
           coins=coins+tempcoins;
           System.out.println("\t\tCoins Achieved: "+tempcoins);
           increaseLevel();
           sc.nextLine();
           Scanner read=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
           String write="";
           while(read.hasNextLine())
           {
               String tempStr=read.nextLine();
               if(tempStr.equals("!"))
               {
                   write+=Facebook.change(nm2)+"\n!\n";
               }
               else
               {
                   write+=tempStr+"\n";
               }
           }
           read.close();
            if(write.length()!=0)
           write=write.substring(0,write.length()-1);
           PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb")));
           pw.print(write);
           pw.close();
        }
        else if(won==2)
        {
           coins=coins-(20*level);
           System.out.println("\t\tCoins Losed: "+20*level);
           sc.nextLine();
        }
        getAchievements();
    }
    //Checks XP & Increases Level
    static void increaseLevel()throws Exception
    {
        int totxp=100;
        for(int i=2;i<=level;i++)
        {
            totxp=totxp+(totxp*2)/3;
        }
        if(xp>=totxp)
        {
            level++;
            xp=xp-totxp;
            for(int i=0;i<500;i++)
            {
                System.out.println();
            }
            System.out.println("\t\tLevel Up "+(char)24+""+(char)24+""+(char)24+"");
            System.out.println("\t\tLevel:- "+level);
            System.out.println("\t\tPress Enter To Continue To Lucky Draw !!!");
            new Scanner(System.in).nextLine();
            for(int i=0;i<500;i++)
            {
                System.out.println();
            }
            String LArr[]={"70 Coins","70 XP","50 XP","50 Coins","","","","",""};
            String HArr[]={"200 XP","200 Coins","250 Coins","250 XP","","","","",""};
            System.out.println("+----------+                     +----------+                      +----------+");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|  CARD 1  |                     |  CARD 2  |                      |  CARD 3  |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("+----------+                     +----------+                      +----------+");
            System.out.println();
            System.out.println("+----------+                     +----------+                      +----------+");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|  CARD 4  |                     |  CARD 5  |                      |  CARD 6  |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("+----------+                     +----------+                      +----------+");
            System.out.println();
            System.out.println("+----------+                     +----------+                      +----------+");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|  CARD 7  |                     |  CARD 8  |                      |  CARD 9  |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("|          |                     |          |                      |          |");
            System.out.println("+----------+                     +----------+                      +----------+");
            System.out.print("\t\t\tChoose A Card:- ");
            int i1=0;
            while(i1==0)
            {
                i1=input(9);
            }
            for(int i=0;i<500;i++)
            {
                System.out.println();
            }
            String ld="";
            int ln=(int)Math.abs(rnd(8));
            if(level<10)
                ld=LArr[ln];
            else
                ld=HArr[ln];
            String in="",cox="";
            for(int i=0;i<ld.length();i++)
            {
                if(Character.isDigit(ld.charAt(i)))
                {
                    in+=ld.charAt(i)+"";
                }
                else
                {
                    cox+=ld.charAt(i)+"";
                }
            }
            cox=cox.trim();
            Toolkit.getDefaultToolkit().beep();
            System.out.println("                                 +----------+");
            System.out.println("                                 |          |");
            if(cox.equals("") || cox.equalsIgnoreCase("XP"))
            System.out.print("                                 |");
            else
                System.out.print("                                 | ");
            for(int i=0;i<(10-cox.length())/2;i++)
            {
                System.out.print(" ");
            }
            System.out.print(cox);
            for(int i=0;i<(10-cox.length())/2;i++)
            {
                System.out.print(" ");
            }
            System.out.println("|");
            System.out.print("                                 |");
            for(int i=0;i<(10-in.length())/2;i++)
            {
                System.out.print(" ");
            }
            System.out.print(in);
            for(int i=0;i<(10-in.length())/2;i++)
            {
                System.out.print(" ");
            }
            System.out.println("|");
            System.out.println("                                 |          |");
            System.out.println("                                 |          |");
            System.out.println("                                 +----------+");
            if(ld.equals(""))
            {
                System.out.println("\t\tSorry !!! You Won Nothing !!!");
                System.out.println("\t\tIts A Empty Card Like Your Luck !!!");
            }
            else
            {
                System.out.println("\t\tYou Won "+ld+" !!!");
                if(level<10)
                {
                    if(ln==0)
                        coins+=70;
                    else if(ln==1)
                        xp+=70;
                    else if(ln==2)
                        xp+=50;
                    else if(ln==3)
                        coins+=50;
                }
                else
                {
                    if(ln==0)
                        xp+=200;
                    else if(ln==1)
                        coins+=200;
                    else if(ln==2)
                        coins+=250;
                    else if(ln==3)
                        xp+=250;
                }
            }
            new Scanner(System.in).nextLine();
            boolean asd=true;
            while(asd)
            {
                System.out.println("\t\tDo You Want To Share (Y/N)?");
                String ipl=new Scanner(System.in).nextLine();
                ipl=ipl.trim();
                if(ipl.equalsIgnoreCase("Y"))
                {
                    Scanner read=new Scanner(new File("C:\\Facebook\\Username.fb"));
                    while(read.hasNextLine())
                    {
                        String ide=read.nextLine();
                        read.nextLine();
                        if(new File("C:\\Facebook\\"+ide+"\\Game.fb").exists())
                        {
                            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+ide+"\\HomePage.fb",true)));
                            int lev=level-1;
                            pw.println("\t\t\t"+nm1+" Level Up From "+lev+" To "+level);
                            pw.close();
                        }
                    }
                    read.close();
                    asd=false;
                }
                else if(ipl.equalsIgnoreCase("N"))
                {
                    asd=false;
                }
                else
                {
                    System.out.println("\t\t\tInvalid Input !!!\n\tPlease Input Y or N ");
                }
            }
        }
        String name[]=new String[10];
        int lev[]=new int[10];
        Scanner sc=new Scanner(new File("C:\\Facebook\\ScoreBoard.fb"));
        for(int i=0;i<10;i++)
        {
            name[i]=sc.nextLine();
            lev[i]=Integer.parseInt(sc.nextLine());
        }
        sc.close();
        if(level<lev[9])
            return;
        boolean flag=true;
        int p=0;
        for(int i=0;i<9;i++)
        {
            if(name[i].equalsIgnoreCase(nm1))
            {
                lev[i]=level;
                flag=false;
                break;
            }
            if(name[i].equalsIgnoreCase("SpeedX"))
            {
                p=i;
            }
        }
        if(flag)
        {
            name[9]=nm1;
            lev[9]=level;
        }
        if(getHighLevel()+3>=lev[p])
        {
            lev[p]=lev[p]+(getHighLevel()+3-lev[p])+rnd(5);
        }
        int m1=0;
        for(int i=0;i<9;i++)
        {
            m1=i;
            for(int j=i+1;j<=9;j++)
            {
                if(lev[m1]<lev[j])
                    m1=i;
            }
            int m=lev[i];
            lev[i]=lev[m1];
            lev[m1]=m;
            String nm=name[i];
            name[i]=name[m1];
            name[m1]=nm;
        }
        PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\ScoreBoard.fb")));
        for(int i=0;i<10;i++)
        {
            pw.println(name[i]);
            if(i==9)
            {
                pw.print(lev[i]);
            }
            else
            {
                pw.println(lev[i]);
            }
        }
        pw.close();
    }
    //Reads Data From File Game.fb To Memory
    static void read()throws Exception
    {
       Scanner read=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
       level=Integer.parseInt(read.nextLine());
       xp=Integer.parseInt(read.nextLine());
       coins=Integer.parseInt(read.nextLine());
       if(Integer.parseInt(read.nextLine())==0)
       {
           cheat=false;
       }
       else
       {
           cheat=true;
       }
       if(Integer.parseInt(read.nextLine())==0)
       {
           xpbooster=false;
       }
       else
       {
           xpbooster=true;
       }
       if(Integer.parseInt(read.nextLine())==0)
       {
           coinbooster=false;
       }
       else
        {
           coinbooster=true;
        }
       defeatlevel=Integer.parseInt(read.nextLine());
       read.close();
       match_3=0;
       won=0;
    }
    //Shows Scoreboard
   static void scoreBoard()throws Exception
   {
       Scanner read=new Scanner(new File("C:\\Facebook\\ScoreBoard.fb"));
       System.out.println("    Player Name                                                          Level  ");
       System.out.println(" -------------------                                                  ----------");
       for(int i=1;i<=10;i++)
        {
            String name=read.nextLine();
            String lev=read.nextLine();
            System.out.print(name);
            int l=70-name.length();
            for(int j=0;j<l;j++)
            {
                System.out.print(" ");
            }
            System.out.print("   ");
            System.out.println(lev);
            System.out.println();
        }
        read.close();
        read=new Scanner(System.in);
        System.out.println("Press Enter To Go To Main Menu");
        read.nextLine();
   }
   //Sets Your Name On ScoreBoard
   static int getHighLevel()throws Exception
    {
        Scanner sc=new Scanner(new File("C:\\Facebook\\ScoreBoard.fb"));
        String name[]=new String[10];
        int lev[]=new int[10];
        int h=0,p=0;
        for(int i=0;i<10;i++)
        {
            name[i]=sc.nextLine();
            lev[i]=Integer.parseInt(sc.nextLine());
            if(lev[i]>h)
            {
                h=lev[i];
                p=i;
            }
        }
        sc.close();
        if(name[p].equalsIgnoreCase("SpeedX"))
        {
            h=0;
            for(int i=0;i<10;i++)
            {
                if(p==i)
                    continue;
                if(lev[i]>h)
                {
                    h=lev[i];
                }
            }
        }
        return h;
    }
    //Returns level
    static int getLevel(long id)throws Exception
    {
        if(new File("C:\\Facebook\\"+id+"\\Game.fb").exists())
        {
            Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Game.fb"));
            int lev=0;
            lev=Integer.parseInt(read.nextLine());
            read.close();
            return lev;
        }
        else
            return 0;
    }
    //Returns The Level Of Player Defeated Yet
    static int getDefeatLevel(long id)throws Exception
    {
        Scanner read=new Scanner(new File("C:\\Facebook\\"+id+"\\Game.fb"));
        int lev=0;
        read.nextLine();
        read.nextLine();
        read.nextLine();
        read.nextLine();
        read.nextLine();
        read.nextLine();
        lev=Integer.parseInt(read.nextLine());
        read.close();
        return lev;
    }
    //Gives Your Achievements
    static void getAchievements()throws Exception
    {
        Scanner sc=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
        int arrComp[]={0,0,0,0,0,0,0,0,0,0};
        while(sc.hasNextLine())
        {
            String s=sc.nextLine();
            boolean flag=s.equalsIgnoreCase("!");
            while(flag)
            {
                flag=sc.hasNextLine();
                if(flag)
                {
                    arrComp[Integer.parseInt(sc.nextLine())]=1;
                }
            }
        }
        sc.close();
        String getCoins[]={"Coin:- 40  XP:- 20","Coin:- 70  XP:- 50","Coin:- 80  XP:- 60","Coin:- 60  XP:- 60","Coin:- 80  XP:- 80","Coin:- 100  XP:- 150","Coin:- 100  XP:- 200","Coin:- 500  XP:- 500","Coin:- 100  XP:- 300","Coin:- 5000  XP:- 5000"};
        int i=0;
        String write="";
        if(arrComp[i]==0)
        {
            if(level==1 && won==2)
            {
                System.out.println("Achievement 1 Completed !!!");
                System.out.println("\t"+getCoins[i]);
                coins+=40;
                xp+=20;
                increaseLevel();
                write+=i+"\n";
            }
        }
        i=2;
        if(arrComp[i]==0)
        {
            if(won==0)
            {
                match_3++;
            }
            else
            {
                match_3=0;
            }
            if(level==3 && match_3==3)
            {
                System.out.println("Achievement 3 Completed !!!");
                System.out.println("\t"+getCoins[i]);
                coins+=80;
                xp+=60;
                increaseLevel();
                write+=i+"\n";
            }
        }
        i=3;
        if(arrComp[i]==0)
        {
            if(level==3)
            {
                System.out.println("Achievement 4 Completed !!!");
                System.out.println("\t"+getCoins[i]);
                coins+=60;
                xp+=60;
                increaseLevel();
                write+=i+"\n";
            }
        }
        i=4;
        if(arrComp[i]==0)
        {
            if(level==5)
            {
                System.out.println("Achievement 5 Completed !!!");
                System.out.println("\t"+getCoins[i]);
                coins+=80;
                xp+=80;
                increaseLevel();
                write+=i+"\n";
            }
        }
        i=5;
        if(arrComp[i]==0)
        {
            if(level==10)
            {
                System.out.println("Achievement 6 Completed !!!");
                System.out.println("\t"+getCoins[i]);
                coins+=100;
                xp+=150;
                increaseLevel();
                write+=i+"\n";
            }
        }
        i=7;
        if(arrComp[i]==0)
        {
            if(coins>=10000)
            {
                System.out.println("Achievement 8 Completed !!!");
                System.out.println("\t"+getCoins[i]);
                coins+=500;
                xp+=500;
                increaseLevel();
                write+=i+"\n";
            }
        }
        if(!write.equals(""))
        {
            new Scanner(System.in).nextLine();
            write=write.substring(0,write.length()-1);
            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb",true)));
            pw.print("\n"+write);
            pw.close();
        }
    }
    //Shows Your Achievements
    static void achievements()throws Exception
    {
        Scanner sc=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
        int complete=0,remain=10;
        int arrComp[]={0,0,0,0,0,0,0,0,0,0};
        String arrAchieve[]={"Lose A Match In Level 1","Buy A Coin Booster In Level 2","Win 3 Matches Continuously In Level 3","Reach Level 3","Reach Level 5","Reach Level 10","Win A Match In Level 11","Reach The Limit Of 10000 Coins","Buy A Cheat In Level 5","Buy A Coin Pack"};
        String getCoins[]={"Coin:- 40  XP:- 20","Coin:- 70  XP:- 50","Coin:- 80  XP:- 60","Coin:- 60  XP:- 60","Coin:- 80  XP:- 80","Coin:- 100  XP:- 150","Coin:- 100  XP:- 200","Coin:- 500  XP:- 500","Coin:- 100  XP:- 300","Coin:- 5000  XP:- 5000"};
        while(sc.hasNextLine())
        {
            String s=sc.nextLine();
            boolean flag=s.equalsIgnoreCase("!");
            while(flag)
            {
                flag=sc.hasNextLine();
                if(flag)
                {
                    arrComp[Integer.parseInt(sc.nextLine())]=1;
                    complete++;
                }
            }
        }
        sc.close();
        remain=10-complete;
        for(int i=0;i<10;i++)
        {
            System.out.print(arrAchieve[i]);
            for(int sp=0;sp<(80-arrAchieve[i].length()-11);sp++)
                System.out.print(" ");
            if(arrComp[i]==1)
            {
                System.out.println("Completed");
            }
            else
            {
                System.out.println("Incomplete");
            }
            System.out.println("\t"+getCoins[i]);
        }
        System.out.println();
        System.out.println("\tAchivements Completed :-"+complete);
        System.out.println("\tAchivements Remaining :-"+remain);
        System.out.println();
        System.out.println("\t\tPlease Get TicTacToe Ultimate With More Features !!!\n\t\t   Contact:-7205595198 ---------------- SpeedX");
        new Scanner(System.in).nextLine();
    }
    //Sets An Enemy For You
    static void setEnemy()throws Exception
    {
        long defeated[];
        Scanner read;
        read=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
        String temp="",tmpArr="";
        int c=0;
        read.nextLine();
        read.nextLine();
        read.nextLine();
        read.nextLine();
        read.nextLine();
        read.nextLine();
        read.nextLine();
        while(read.hasNextLine())
        {
            temp=read.nextLine();
            if(temp.equals("!"))
                break;
            else
            {
                tmpArr+=temp+"\n";
                c++;
            }
        }
        read.close();
        if(c==0)
        {
            defeated=new long[1];
            defeated[0]=0;
        }
        else
        {
            defeated=new long[c];
            int k=0;
            for(int i=0;i<c;i++)
            {
                defeated[i]=Long.parseLong(tmpArr.substring(k,tmpArr.indexOf('\n',k)));
                k=tmpArr.indexOf('\n')+1;
            }
        }
        int l=getHighLevel();
        int defeatlevel=getDefeatLevel(Facebook.change(nm1));
        boolean flag=defeatlevel<=l;
        while(flag)
        {
            boolean flag1=true;
            read=new Scanner(new File("C:\\Facebook\\Username.fb"));
            while(read.hasNextLine())
            {
                read.nextLine();
                String us=read.nextLine();
                long id=Facebook.change(us);
                if(id==0 || id==99999 || id==Facebook.change(nm1))
                {
                    continue;
                }
                if(defeatlevel==getLevel(id))
                {
                    boolean flag2=true;
                    for (int i=0;i<defeated.length;i++)
                    {
                        if(id==defeated[i])
                        {
                            flag2=false;
                        }
                    }
                    if(flag2)
                    {
                        nm2=us;
                        System.out.println("\t\tYour Opponent is "+nm2);
                        System.out.println("\t\tPress Enter To Continue");
                        new Scanner(System.in).nextLine();
                        compPlay();
                        flag1=false;
                        flag=false;
                    }
                }
            }
            read.close();
            if(flag1)
            {
                defeatlevel++;
                for(int i=0;i<defeated.length;i++)
                {
                    defeated[i]=0;
                }
                read=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
                String temp1="",temp2,arr[];
                boolean flag3=false;
                int p=0,c1=0;
                String achive="";
                while(read.hasNextLine())
                {
                    temp2=read.nextLine();
                    c1++;
                    if(temp2.equals("!"))
                    {
                        p=c1;
                    }
                    temp1+=temp2+"\n";
                }
                read.close();
                arr=new String[c1];
                int k=0;
                for(int i=0;i<c1;i++)
                {
                    arr[i]=temp1.substring(k,temp1.indexOf('\n',k));
                    k=temp1.indexOf('\n',k)+1;
                }
                for(int i=p;i<c1;i++)
                {
                    if(i+1==c1)
                    achive+=arr[i];
                    else
                    achive+=arr[i]+"\n";
                }
                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb")));
                pw.println(level);
                pw.println(xp);
                pw.println(coins);
                if(cheat)
                {
                    pw.println("1");
                }
                else
                {
                    pw.println("0");
                }
                if(xpbooster)
                {
                    pw.println("1");
                }
                else
                {
                    pw.println("0");
                }
                if(coinbooster)
                {
                    pw.println("1");
                }
                else
                {
                    pw.println("0");
                }
                pw.println(defeatlevel);
                if(!achive.trim().equals(""))
                {
                    pw.println("!");
                }
                else
                {
                    pw.print("!");
                }
                if(!achive.trim().equals(""))
                pw.print(achive);
                pw.close();
            }
            if(flag)
            flag=defeatlevel<=l;
        }
        if(defeatlevel>l)
        {
            read=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
            defeatlevel=level;
            String temp1="",temp2,arr[];
            int p=0,c1=0;
            String achive="";
            while(read.hasNextLine())
            {
                temp2=read.nextLine();
                c1++;
                if(temp2.equals("!"))
                {
                    p=c1;
                }
                temp1+=temp2+"\n";
            }
            read.close();
            arr=new String[c1];
            int k=0;
            for(int i=0;i<c1;i++)
            {
                arr[i]=temp1.substring(k,temp1.indexOf('\n',k));
                k=temp1.indexOf('\n',k)+1;
            }
            for(int i=p;i<c1;i++)
            {
                if(i+1==c1)
                {
                    achive+=arr[i];
                }
                else
                {
                    achive+=arr[i]+"\n";
                }
            }
            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb")));
            pw.println(level);
            pw.println(xp);
            pw.println(coins);
            if(cheat)
            {
                pw.println("1");
            }
            else
            {
                pw.println("0");
            }
            if(xpbooster)
            {
                pw.println("1");
            }
            else
            {
                pw.println("0");
            }
            if(coinbooster)
            {
                pw.println("1");
            }
            else
            {
                pw.println("0");
            }
            pw.println(l);
            if(!achive.trim().equals(""))
                pw.println("!");
            else
                pw.print("!");
            if(!achive.trim().equals(""))
                pw.print(achive);
                pw.close();
            System.out.println("\t\tYou Are At Top !!!");
            System.out.println("\t\tNow You Can Fight With Legends !!!");
            System.out.println("\t\tPress Enter To Continue...");
            new Scanner(System.in).nextLine();
            String Arr[]={"God Of Death","Spady","Gyana","Blaze","Buzz","Blaze","Buzz","Blizard","Hunter","Extreme Gamer","Im-Mortals","God Of Death","Spady","Gyana","Blaze","Buzz","Blizard","Hunter","Extreme Gamer","Im-Mortals","SpeedX"};
            int r=(int)Math.abs(rnd(20));
            nm2=Arr[r];
            System.out.println("\t\tYour Opponent is "+nm2);
            System.out.println("\t\tPress Enter To Continue...");
            new Scanner(System.in).nextLine();
            compPlay();
        }
    }
    //starts Game
    static void startGame()throws Exception
    {
       boolean flag=true;
       Scanner sc=new Scanner(System.in);
        clear();
        sc.nextLine();
        read();
       for(int i=0;i<9;i++)
       {
           position[i]=" ";
       }
       String close[]={"Clearing Data","Unregistering Names","Resetting Game"};
        increaseLevel();
       while(flag)
        {
            clr();
            //Coins
            String value="";
            value=coins+"";
            int l1=29-("Coins: ".length()+value.length()+1);
            System.out.print("      Coins: "+(char)36+" "+value);
            for(int i=0;i<l1;i++)
            {
                System.out.print(" ");
            }
            //Xp
            value="";
            value=xp+"";
            int totxp=100;
            for(int i=2;i<=level;i++)
            {
                totxp=totxp+(totxp*2)/3;
            }
            l1=28-("XP:- ".length()+value.length()+1+Integer.toString(totxp).length());
            System.out.print("XP:- "+value+" / "+totxp);
            for(int i=0;i<l1;i++)
            {
                System.out.print(" ");
            }
            //Level
            value="";
            value=level+"";
            System.out.print("Level: "+value);
            System.out.println("\n");
            //Cheat Available
            if(cheat)
            {
                value="Cheat Available";
            }
            else
            {
                value="No Cheat";
            }
            l1=25-value.length();
            System.out.print("     "+value);
            for(int i=0;i<l1;i++)
            {
                System.out.print(" ");
            }
            //xpbooster
            if(xpbooster)
            {
                value="XP Booster Available";
            }
            else
            {
                value="No XP Booster";
            }
            l1=26-value.length();
            System.out.print(" "+value);
            for(int i=0;i<l1;i++)
            {
                System.out.print(" ");
            }
            //coinbooster
            if(coinbooster)
            {
                value="Coin Booster Available";
            }
            else
            {
                value="No Coin Booster";
            }
            System.out.println(" "+value);
            System.out.println("*******************************************************************************");
            System.out.println("*******************************************************************************");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |            1 - Battle                        |    ***********");
            System.out.println("************    |            2 - Achievements                  |    ***********");
            System.out.println("************    |            3 - Score Board                   |    ***********");
            System.out.println("************    |            4 - Buy Items                     |    ***********");
            System.out.println("************    |            5 - Get Coins                     |    ***********");
            System.out.println("************    |            6 - Help                          |    ***********");
            System.out.println("************    |            7 - Back                          |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("*******************************************************************************");
            System.out.println("*******************************************************************************");
            System.out.println("\n");
            System.out.print("\n\t\tPlease Enter Your Choice:- ");
            int inp=0;
            while(inp==0)
            {
                inp=input(7);
            }
            if(inp==1)
            {
                clr();
                setEnemy();
            }
            else if(inp==2)
            {
                clr();
                achievements();
            }
            else if(inp==3)
            {
                clr();
                increaseLevel();
                scoreBoard();
            }
            else if(inp==4)
            {
                clr();
                boolean flag1=true;
                while(flag1)
                {
                    int arrComp[]={0,0,0,0,0,0,0,0,0,0};
                    Scanner read=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
                    while(read.hasNextLine())
                    {
                        boolean flag2=read.nextLine().equalsIgnoreCase("!");
                        while(flag2)
                        {
                            flag2=read.hasNextLine();
                            if(flag2)
                            {
                                arrComp[Integer.parseInt(read.nextLine())]=1;
                            }
                        }
                    }
                    read.close();
                    clr();
                    System.out.println("\t\t\tBuy Items Here !!!");
                    int amt=0;
                    amt=100*level;
                    System.out.println("\t\t1-Cheat       For "+(char)36+""+amt+" Coins");
                    amt=80*level;
                    System.out.println("\t\t2-XPBooster   For "+(char)36+""+amt+" Coins");
                    amt=90*level;
                    System.out.println("\t\t3-CoinBooster For "+(char)36+""+amt+" Coins");
                    System.out.println("\t\t4-Back");
                    System.out.print("\n\t\t\tPlease Enter Your Choice:-   ");
                    int i=0;
                    while(i==0)
                    {
                        i=input(4);
                    }
                    if(i==1)
                    {
                        if(!cheat)
                        {
                            amt=100*level;
                            if(coins>=amt)
                            {
                                coins=coins-amt;
                                cheat=true;
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tCheat Successfully Bought !!!");
                                if(level==5 && arrComp[8]==0)
                                {
                                    System.out.println("Achievement 9 Completed !!!");
                                    System.out.println("\tCoin:- 100  XP:- 300");
                                    coins+=100;
                                    xp+=300;
                                    increaseLevel();
                                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb",true)));
                                    pw.print("\n"+8);
                                    pw.close();
                                }
                            }
                            else
                            {
                                System.out.println("\tYou Don't Have Sufficient Coins !!!");
                                System.out.println("\t\tPlease Get A Loan !!!");
                            }
                        }
                        else
                        {
                            System.out.println("\tYou Already Have A Cheat You Can't Buy Another Cheat !!!");
                            System.out.println("\tPlease Use It To Buy Another Cheat ");
                        }
                        System.out.println("\t\tPress Enter To Continue !!!");
                        sc.nextLine();
                    }
                    else if(i==2)
                    {
                        if(!xpbooster)
                        {
                            amt=80*level;
                            if(coins>=amt)
                            {
                                coins=coins-amt;
                                xpbooster=true;
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tXP Booster Successfully Bought !!!");
                            }
                            else
                            {
                                System.out.println("\tYou Don't Have Sufficient Coins !!!");
                                System.out.println("\t\tPlease Get A Loan !!!");
                            }
                        }
                        else
                        {
                            System.out.println("\tYou Already Have A XP Booster You Can't Buy Another XP Booster !!!");
                            System.out.println("\tPlease Use It To Buy Another XP Booster ");
                        }
                        System.out.println("\t\tPress Enter To Continue !!!");
                        sc.nextLine();
                    }
                    else if(i==3)
                    {
                        if(!coinbooster)
                        {
                            amt=90*level;
                            if(coins>=amt)
                            {
                                coins=coins-amt;
                                coinbooster=true;
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("\t\tCoin Booster Successfully Bought !!!");
                                if(level==2 && arrComp[1]==0)
                                {
                                    System.out.println("Achievement 2 Completed !!!");
                                    System.out.println("\t"+"Coin:- 70  XP:- 50");
                                    coins+=70;
                                    xp+=50;
                                    increaseLevel();
                                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb",true)));
                                    pw.print("\n"+1);
                                    pw.close();
                                }
                            }
                            else
                            {
                                System.out.println("\tYou Don't Have Sufficient Coins !!!");
                                System.out.println("\t\tPlease Get A Loan !!!");
                            }
                        }
                        else
                        {
                            System.out.println("\tYou Already Have A Coin Booster You Can't Buy Another Coin Booster !!!");
                            System.out.println("\tPlease Use It To Buy Another Coin Booster ");
                        }
                        System.out.println("\t\tPress Enter To Continue !!!");
                        sc.nextLine();
                    }
                    else
                    {
                        flag1=false;
                    }
                }
            }
            else if(inp==5)
            {
                clr();
                char CHAR=(char)36;
                System.out.println("                    Tic-Tac-Toe Ultimate  ---  Coming Soon!!!");
                System.out.println("                +----------------------------------------------+               ");
                System.out.println("                |                                              |               ");
                System.out.println("                |     1 - Get 500   Coins   -   "+CHAR+" 1            |               ");
                System.out.println("                |     2 - Get 2000  Coins   -   "+CHAR+" 3            |               ");
                System.out.println("                |     3 - Get 5000  Coins   -   "+CHAR+" 6            |               ");
                System.out.println("                |     4 - Get 7500  Coins   -   "+CHAR+" 8            |               ");
                System.out.println("                |     5 - Get 10000 Coins   -   "+CHAR+" 10           |               ");
                System.out.println("                |     6 - Back                                 |               ");
                System.out.println("                |                                              |               ");
                System.out.println("                +----------------------------------------------+               ");
                System.out.println("             Upgrade To Tic-Tac-Toe Ultimate at $19.99/- Only !!!");
                int i=0;
                while(i==0)
                {
                    i=input(6);
                }
                if(i==6)
                {
                    continue;
                }
                System.out.println("\t\tCan'T Connect To Server");
                System.out.println("\t\tPlease Contact Gyanaranjan Sahoo To Solve Problems");
                System.out.println("\t\tOr To Get Coins At A Lower Rate !!!");
                System.out.println("\t\tPhone:- 7205595198 ");
                new Scanner(System.in).nextLine();
            }
            else if(inp==6)
            {
                for(int i=0;i<500;i++)
                    System.out.println();
                System.out.println("Instructions:\n\n"
                                    +"Your goal is to be the first player to get 3 X's or O's in a row.\n"
                                    +"(Horizontally, Diagonally, or Vertically)\n");
                System.out.println("You Can Input Numbers for Each Block.Codes Are:-");
                System.out.println("\t\t\t\t     |     |     ");
                System.out.println("\t\t\t\t  1  |  2  |   3");
                System.out.println("\t\t\t\t-----+-----+-----");
                System.out.println("\t\t\t\t  4  |  5  |   6");
                System.out.println("\t\t\t\t-----+-----+-----");
                System.out.println("\t\t\t\t  7  |  8  |   9");
                System.out.println("\t\t\t\t     |     |     ");
                System.out.println("About:\n\n" +
                                    "\t\t\tTitle: Tic-Tac-Toe\n" +
                                    "\t\t\tCreator: Gyanaranjan Sahoo\n" +
                                    "\t\t\tPhone: 7205595198\n"+
                                    "\t\t\tName In Game: SpeedX\n"+
                                    "\t\t\tVersion: 3.5\n");
                System.out.println("\tUpgrade To Tic-Tac-Toe Ultimate at $19.99/- Only !!!");
                System.out.println("Press Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
            else
            {
                Scanner read=new Scanner(new File("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb"));
                String temp1="",temp2,arr[];
                boolean flag3=false;
                int p=0,c1=0;
                String achive="";
                while(read.hasNextLine())
                {
                    temp2=read.nextLine();
                    c1++;
                    if(temp2.equals("!"))
                        p=c1;
                    temp1+=temp2+"\n";
                }
                read.close();
                arr=new String[c1];
                int k=0;
                for(int i=0;i<c1;i++)
                {
                    arr[i]=temp1.substring(k,temp1.indexOf('\n',k));
                    k=temp1.indexOf('\n',k)+1;
                }
                for(int i=p;i<c1;i++)
                {
                    if(i+1==c1)
                    achive+=arr[i];
                    else
                    achive+=arr[i]+"\n";
                }
                PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("C:\\Facebook\\"+Facebook.change(nm1)+"\\Game.fb")));
                pw.println(level);
                pw.println(xp);
                pw.println(coins);
                if(cheat)
                {
                    pw.println("1");
                }
                else
                {
                    pw.println("0");
                }
                if(xpbooster)
                {
                    pw.println("1");
                }
                else
                {
                    pw.println("0");
                }
                if(coinbooster)
                {
                    pw.println("1");
                }
                else
                {
                    pw.println("0");
                }
                pw.println(defeatlevel);
                if(!achive.trim().equals(""))
                {
                    pw.println("!");
                }
                else
                {
                    pw.print("!");
                }
                if(!achive.trim().equals(""))
                pw.print(achive);
                pw.close();
                clr();
                int in=0;
                while(in<=95 || in>99)
                {
                    in=rnd(100);
                }
                DataInputStream dis=new DataInputStream(System.in);
                int i2=0;
                while(dis.available()==0)
                {
                    if(i2++==3)
                    {
                        break;
                    }
                    int m=0;
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t   \\|");
                    System.out.println("\t\t\t\t  ="+in+"%");
                    wait(300);
                    clr();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t   \\");
                    System.out.println("\t\t\t\t  ="+in+"%");
                    System.out.print("\t\t\t\t   /");
                    wait(300);
                    clr();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("");
                    System.out.println("\t\t\t\t  ="+in+"%");
                    System.out.print("\t\t\t\t   /|");
                    wait(300);
                    clr();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("");
                    System.out.println("\t\t\t\t   "+in+"%");
                    System.out.print("\t\t\t\t   /|\\");
                    wait(300);
                    clr();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t     ");
                    System.out.println("\t\t\t\t   "+in+"%=");
                    System.out.print("\t\t\t\t    |\\");
                    wait(300);
                    clr();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t     /");
                    System.out.println("\t\t\t\t   "+in+"%=");
                    System.out.print("\t\t\t\t     \\");
                    wait(300);
                    clr();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t    |/");
                    System.out.println("\t\t\t\t   "+in+"%=");
                    wait(300);
                    clr();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t   \\|/");
                    System.out.println("\t\t\t\t   "+in+"%");
                    wait(300);
                    clr();
                    while(m<=19 || m>24)
                    {
                        m=rnd(30);
                    }
                    in-=m;
                }
                System.out.println("\n\t\t\t\tGame Closed!!!");
                flag=false;
            }
        }
       return;
   }
   //clears screen
    static void clr()
    {
        for(int i=0;i<500;i++)
            System.out.println();
    }
    //Waits Specified Time
    static void wait(int r)
    {
        try
        {
            Thread.sleep(r);
        }
        catch(InterruptedException ie)
        {
            return;
        }
    }
    //Main Method Of Tic-Tac-Toe
   public static void Main()throws Exception
    {
        clr();
        int in=0;
        while(in<=17 || in>25)
        {
            in=rnd(30);
        }
        DataInputStream dis=new DataInputStream(System.in);
        int i2=0;
        while(dis.available()==0)
        {
            if(i2++==3)
            {
                break;
            }
            int m=0;
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\|/");
            System.out.println("\t\t\t\t  "+in+"%");
            wait(300);
            clr();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t   |/");
            System.out.println("\t\t\t\t  "+in+"%=");
            wait(300);
            clr();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t    /");
            System.out.println("\t\t\t\t  "+in+"%=");
            System.out.print("\t\t\t\t    \\");
            wait(300);
            clr();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t     ");
            System.out.println("\t\t\t\t  "+in+"%=");
            System.out.print("\t\t\t\t   |\\");
            wait(300);
            clr();
            System.out.println("\t\t\t\tLoading");
            System.out.println("");
            System.out.println("\t\t\t\t  "+in+"%");
            System.out.print("\t\t\t\t  /|\\");
            wait(300);
            clr();
            System.out.println("\t\t\t\tLoading");
            System.out.println("");
            System.out.println("\t\t\t\t ="+in+"%");
            System.out.print("\t\t\t\t  /|");
            wait(300);
            clr();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\");
            System.out.println("\t\t\t\t ="+in+"%");
            System.out.print("\t\t\t\t  /");
            wait(300);
            clr();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\|");
            System.out.println("\t\t\t\t ="+in+"%");
            wait(300);;
            clr();
            while(m<17 || m>25)
            {
                m=rnd(30);
            }
            in+=m;
        }
        clr();
           startGame();
    }
}
class EncryptDecrypt
{
    TimerTask task = new TimerTask()
    {
        public void run()
        {
            try
            {
                Toolkit.getDefaultToolkit().beep();
                wait(500);
                Toolkit.getDefaultToolkit().beep();
                wait(500);
                Toolkit.getDefaultToolkit().beep();
                wait(500);
            }
            catch(InterruptedException ie)
            {}
            System.out.println("\n\n\t\tYou did Not Enter Correct Password Within 30 Seconds...");
            System.out.println("\tYou Are Going To See The Exit Way!!!");
            System.out.println("Press Enter To Continue...");
            new Scanner(System.in).nextLine();
            clear();
            try
            {
                Facebook.exit(0);
            }
            catch(Exception e)
            {}
        }
    };
    //Clears Screen
    static void clear()
    {
        for(int i=0;i<500;i++)
        {
            System.out.println();
        }
    }
    //Waits Specified Time
    static void wait(int r)
    {
        try
        {
            Thread.sleep(r);
        }
        catch(InterruptedException ie)
        {
            return;
        }
    }
    //Gets A valid Input
    static int input(int n)
    {
        Scanner sc=new Scanner(System.in);
        try
        {
            String str=sc.nextLine().trim();
            int in=Integer.parseInt(str);
            if(in>=1 && in<=n)
            return in;
            else
            {
                System.out.print("Please Input a Number Between 1 to " + n+" :- ");
                return 0;
            }
        }
        catch(NumberFormatException nf)
        {
            System.out.print("\n\t\t\tPlease Input A Number :- ");
            return 0;
        }
    }
    static void encrypt(String fil)throws Exception
    {
        Scanner s=new Scanner(System.in);
        FileInputStream fin=new FileInputStream(fil);
        fil=fil+".fbencrypt";
        FileOutputStream fout=new FileOutputStream(fil);
        DeflaterOutputStream out=new DeflaterOutputStream(fout);
        int index=fil.lastIndexOf('\\');
        int filel=fil.length()-10;
        RandomAccessFile raf=new RandomAccessFile(fil.substring(0,fil.length()-10),"r");
        float size=raf.length()/(float)1048576.0;
        raf.close();
        System.out.println("File Size:-"+ size+" MB\n Estimated Time To Encrypt:- "+(size+9)+" Seconds.");
        System.out.println("File \"" + fil.substring(++index,filel) + "\" will be encrypted \nPress Enter To Continue ");
        s.nextLine();
        long time=System.currentTimeMillis();
            System.out.println("\t\t\tEncrypting !!!"+
                             "\n\t\t\tPlease Wait..."+
                             "\n1.Reading File...");
            wait(5000);
            System.out.println("\t\t\tStep 1 Completed !!!");
            System.out.println("\n2.Searching Algorithm To Encrypt...");
            wait(2000);
            System.out.println("\t\t\tStep 2 Completed !!!");
            System.out.println("\n3.Applying Algorithm To Encrypt...");
            System.out.println("\t\tEncrypt Speed 1Mbps");
            try
            {
                int i;
                while((i=fin.read())!=-1)
                {
                    out.write((byte)i);
                    out.flush();
                }
                System.out.println("\t\t\tFile Encrypted !!!");
                System.out.println("\t\tTime Taken: "+(System.currentTimeMillis()-time)/1000.0+" Seconds");
            }
            catch(Exception e)
            {
                System.out.println("Exception Occurred :- "+e);
            }
            finally
            {
                fin.close();
                out.close();
            }
        System.out.println("\t\tPress Enter To Continue...");
        new Scanner(System.in).nextLine();
    }
    public boolean passwordInput(String pass)
    {
        Timer timer = new Timer();
        timer.schedule(task,30*1000);//30 Seconds
        boolean asd=true;
        while(asd)
        {
            System.out.println("\tThe File Is Protected Please Input Password To Decrypt In 30 Seconds");
            System.out.println("\t\tPress Enter To Go Back");
            System.out.print("\t\tPassword Please:");
            String ipl=new Scanner(System.in).nextLine();
            ipl=ipl.trim();
            pass=decryptPassword(pass);
            if(ipl.equals(pass))
            {
                timer.cancel();
                return true;
            }
            else if(ipl.equals(""))
            {
                asd=false;
            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                wait(500);
                Toolkit.getDefaultToolkit().beep();
                wait(500);
                Toolkit.getDefaultToolkit().beep();
                wait(500);
                System.out.println("\t\t\tWrong Password!!!");
            }
        }
        return false;
    }
    static void decrypt(String fil)throws Exception
    {
        if(fil.endsWith(".fbencrypt"))
            fil=fil.substring(0,fil.length()-10);
        if(!new File(fil+".fbencrypt").exists())
        {
            System.out.println("\t\tFile Not Encrypted Yet !!!");
            System.out.println("\t\tPress Enter To Continue...");
            new Scanner(System.in).nextLine();
            return;
        }
        BufferedReader buf=new BufferedReader(new FileReader(fil+".fbencrypt"));
        String pass="",temp="";
        while((temp=buf.readLine())!=null)
        {
            pass=temp;
        }
        buf.close();
        boolean flag3=false;
        if(pass.startsWith("Encrypt:"))
        {
            flag3=new EncryptDecrypt().passwordInput(pass.substring(8));
            if(!flag3)
                return;
        }
        if(new File(fil).exists())
        {
            System.out.println("A File With Same Name Exists!!!");
            boolean asd=true;
            while(asd)
            {
                System.out.print("\t\tDo You Want To OverWrite (Y/N):");
                String ipl=new Scanner(System.in).nextLine();
                ipl=ipl.trim();
                if(ipl.equalsIgnoreCase("Y"))
                {
                    new File(fil).delete();
                    asd=false;
                }
                else if(ipl.equalsIgnoreCase("N"))
                {
                    asd=false;
                    return;
                }
                else
                {
                    System.out.println("\t\t\tInvalid Input !!!\n\tPlease Input Y or N ");
                }
            }
        }
        if(flag3)
        {
            buf=new BufferedReader(new FileReader(fil+".fbencrypt"));
            String write="";
            while((temp=buf.readLine())!=null)
            {
                pass=temp;
                if(pass.startsWith("Encrypt:"))
                    continue;
                write+=pass+"\n";
            }
            buf.close();
            if(!write.equals(""))
            {
                write=write.substring(0,write.length()-1);
                BufferedWriter pw=new BufferedWriter(new FileWriter(fil+".fbencrypt"));
                pw.write(write);
                pw.close();
            }
        }
        RandomAccessFile raf=new RandomAccessFile(fil+".fbencrypt","r");
        float size=raf.length()/(float)1048576.0;
        raf.close();
        FileInputStream fin=new FileInputStream(fil+".fbencrypt");
        FileOutputStream fout=new FileOutputStream(fil);
        InflaterInputStream in=new InflaterInputStream(fin);
        System.out.println("File Size:-"+ size+" MB\n Estimated Time To Decrypt:- "+(size+9)+" Seconds.");
        System.out.println("File \"" + fil + "\" will be Decrypted \nPress Enter To Continue ");
        new Scanner(System.in).nextLine();
        long time=System.currentTimeMillis();
            System.out.println("\t\t\tDecrypting !!!"+
                             "\n\t\t\tPlease Wait..."+
                             "\n1.Reading File...");
            wait(2000);
            System.out.println("\t\t\tStep 1 Completed !!!");
            System.out.println("\n2.Searching Algorithm To Decrypt...");
            wait(1000);
            System.out.println("\t\t\tStep 2 Completed !!!");
            System.out.println("\n3.Applying Algorithm To Decrypt...");
            try
            {
                int i;
                while((i=in.read())!=-1)
                {
                    fout.write((byte)i);
                    fout.flush();
                }
                System.out.println("\t\t\tFile Decrypted !!!");
                System.out.println("\t\tTime Taken: "+(System.currentTimeMillis()-time)/1000.0+" Seconds");
            }
            catch(Exception e)
            {
                System.out.println("Exception Occurred :- "+e);
            }
            finally
            {
                fin.close();
                fout.close();
                in.close();
            }
            if(!new File(fil+".fbencrypt").delete())
                new File(fil+".fbencrypt").deleteOnExit();
            System.out.println("\t\tPress Enter To Continue...");
        new Scanner(System.in).nextLine();
    }
    //Place File On Desktop
    static void changeLocation(String fil)throws Exception
    {
        fil+=".fbencrypt";
        File f=new File(fil);
        int index=fil.lastIndexOf('\\');
        fil=fil.substring(++index);
        boolean flag=true;
        String user=System.getProperty("user.name");
        while(flag)
        {
            System.out.print("Do You Want To Place It At Desktop(Y/N):");
            String i=new Scanner(System.in).nextLine();
            i=i.trim();
            if (i.equalsIgnoreCase("Y"))
            {
                f.createNewFile();
                flag=false;
                InputStream is=null;
                OutputStream os=null;
                try
                {
                    is=new FileInputStream(f);
                    if(new File("C:\\Users\\"+user+"\\Desktop\\").exists())
                    os=new FileOutputStream(new File("C:\\Users\\"+user+"\\Desktop\\"+fil));
                    else
                    os=new FileOutputStream(new File("C:\\Documents and Settings\\"+user+"\\Desktop\\"+fil));
                    byte[] buffer=new byte[1024];
                    int length;
                    while((length=is.read(buffer))>0)
                    {
                        os.write(buffer,0,length);
                    }
                    System.out.println("\t\t\tMove Complete!!!");
                }
                finally
                {
                    is.close();
                    os.close();
                }
                System.out.println("\t\t\tPress Enter To Continue...");
                if(!f.delete())
                {
                    f.deleteOnExit();
                }
                new Scanner(System.in).nextLine();
                return;
            }
            else if(i.equalsIgnoreCase("N"))
            {
                return;
            }
            else
            System.out.println("\t\t\tPlease Input A Valid Input (Y/N)");
        }
    }
    //Deletes The Main File Which Contains Data To Be Encrypted
    public static void deleteMainFile(String fil)throws IOException
    {
        boolean flag=true;
        while(flag)
        {
            System.out.print("Do You Want To Delete The Main File?(Y/N):");
            String i=new Scanner(System.in).nextLine();
            i=i.trim();
            if (i.equalsIgnoreCase("Y"))
            {
                File f=new File(fil);
                boolean flag2=f.delete();
                if(!flag2)
                {
                    f.deleteOnExit();
                }
                if(flag2)
                {
                    System.out.println("\t\t\tMain File Deleted!!!");
                }
                else
                {
                    System.out.println("\t\t\tMain File Scheduled To Be Deleted!!!");
                }
                new Scanner(System.in).nextLine();
                flag=false;
                return;
            }
            else if(i.equalsIgnoreCase("N"))
            {
                return;
            }
            else
            System.out.println("\t\t\tPlease Input A Valid Input (Y/N)");
        }
    }
    //Encrypts Password
    static String encryptPassword(String pass)
    {
        char ch[]=pass.toCharArray();
        int l=ch.length/2;
        for(int i=0;i<ch.length;i++)
        {
            ch[i]-=i;
            ch[i]+=l;
        }
        return new String(ch);
    }
    //Decrypts Password
    static String decryptPassword(String pass)
    {
        char ch[]=pass.toCharArray();
        int l=ch.length/2;
        for(int i=0;i<ch.length;i++)
        {
            ch[i]-=l;
            ch[i]+=i;
        }
        return new String(ch);
    }
    public static void Main()throws Exception
    {
        Scanner sc=new Scanner(System.in);
        clear();
        String close[]={"Saving Changes","Closing File","Removing Streams"};
        DataInputStream dis=new DataInputStream(System.in);
        int i2=0;
        while(dis.available()==0)
        {
            if(i2++==1)
            break;
                clear();
                System.out.println();
                System.out.print("\t\t\tOpening Encrypt !T ");
                System.out.print(".");
                wait(700);
                System.out.print(".");
                wait(700);
                System.out.print(".");
                wait(700);
        }
        while (true)
        {
            mainloop:
            {
                 clear();
                System.out.println();
                System.out.print("*******************************************************************************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("******************                                          *******************\n");
                System.out.print("******************     Welcome to SpeedX Productions!!!     *******************\n");
                System.out.print("******************                                          *******************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("******************     ++----------------------------++      ******************\n");
                System.out.print("******************     ||                            ||      ******************\n");
                System.out.print("******************     ||          Encrypt !T        ||      ******************\n");
                System.out.print("******************     ||                            ||      ******************\n");
                System.out.print("******************     ++----------------------------++      ******************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("*******************************************************************************\n");
                System.out.print("\n");
                System.out.println("\t\t\tPress 1 To Encrypt ");
                System.out.println("\t\t\tPress 2 To Decrypt");
                System.out.println("\t\t\tPress 3 To Encrypt With Password");
                System.out.println("\t\t\tPress 4 To Help");
                System.out.println("\t\t\tPress 5 To Close");
                System.out.print("\n\t\tEnter Your Choice:-");
                int i=0;
               while (i==0)
               {
                   i=input(5);
                   if(i==-1)
                       return;
               }
               if (i==1)
                {
                    File f;String fil;String k="Input the Name of File to be Encrypted";
                    String user="SpeedX";
                    do
                    {
                        System.out.println(k);
                        System.out.println("\t\tPress Enter To Go Back");
                        fil=sc.nextLine();
                        if(fil.equals(""))
                            break mainloop;
                        f=new File(fil);
                        k="File not Found!! \n Please Make Sure it is in bin folder else specify path\nExample:- C:\\\\Users\\\\"+user+"\\\\Desktop\\\\SpeedX.fbencrypt\nInput File Name Again";
                    }
                    while(!(f.exists()));
                            clear();
                    if(fil.endsWith(".fbencrypt"))
                    {
                            fil=fil.substring(0,fil.length()-10);
                    }
                    if(new File(fil+".fbencrypt").exists())
                    {
                        System.out.println("File Already Encrypted!!!");
                        System.out.println("Press Enter to Continue...");
                        sc.nextLine();
                        break mainloop;
                    }
                    encrypt(fil);
                    changeLocation(fil);
                    deleteMainFile(fil);
                }
                else if(i==2)
                {
                    File f;String fil;String k="Input the Name of File to be Decrypted";
                    String user="SpeedX";
                    do
                    {
                        System.out.println(k);
                        System.out.println("\t\tPress Enter To Go Back");
                        fil=sc.nextLine();
                        if(fil.equals(""))
                            break mainloop;
                        else if(fil.endsWith(".fbencrypt"))
                            fil=fil.substring(0,fil.length()-10);
                        f=new File(fil+".fbencrypt");
                        k="File not Found!! \n Please Make Sure it is in bin folder else specify path\nExample:- C:\\\\Users\\\\"+user+"\\\\Desktop\\\\SpeedX.fbencrypt\nInput File Name Again";
                    }
                    while(!(f.exists()));
                            clear();
                    decrypt(fil+".fbencrypt");
                }
                else if(i==3)
                {
                    File f;String fil;String k="Input the Name of File to be Encrypted";
                    String user=System.getProperty("user.name");
                    do
                    {
                        System.out.println(k);
                        System.out.println("\t\tPress Enter To Go Back");
                        fil=sc.nextLine();
                        if(fil.equals(""))
                            break mainloop;
                        f=new File(fil);
                        k="File not Found!! \n Please Make Sure it is in bin folder else specify path\nExample:- C:\\\\Users\\\\"+user+"\\\\Desktop\\\\SpeedX.fbencrypt\nInput File Name Again";
                    }
                    while(!(f.exists()));
                            clear();
                    boolean flag=true;
                    String pass="";
                    while(flag)
                    {
                        System.out.println("\t\tPress Enter To Go Back...");
                        System.out.print("\t\tInput Password:");
                        String pass1=sc.nextLine().trim();
                        if(pass1.equals(""))
                        break mainloop;
                        System.out.print("\t\tRe-Enter Password To Confirm:");
                        String pass2=sc.nextLine().trim();
                        if(pass1.equals(pass2))
                        {
                            pass=pass1;
                            flag=false;
                        }
                        else
                        {
                            System.out.println("\t\tPasswords Do Not Match!!!");
                        }
                    }
                    clear();
                    encrypt(fil);
                    pass=encryptPassword(pass);
                    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(fil+".fbencrypt",true)));
                    pw.print("\nEncrypt:"+pass);
                    pw.close();
                        changeLocation(fil);
                    deleteMainFile(fil);
                }
                else if(i==4)
                {
                    clear();
                    System.out.println();
                    System.out.println("About:\n\n" +
                                        "\t\t\tTitle: Encrypt !T\n" +
                                        "\t\t\tCreator: Gyanaranjan Sahoo\n" +
                                        "\t\t\tPhone: 7205595198\n"+
                                        "\t\t\tVersion: 1.5\n");
                    System.out.println("\tUpgrade To Encrypt !T Pro at $19.99/- Only !!!");
                    System.out.println("Press Enter To Continue...");
                    sc.nextLine();
                    clear();
                }
                else
                {
                    clear();
                    System.out.println();
                    System.out.print("\t\t\tClosing Encrypt !T ");
                    wait(700);
                    System.out.print(".");
                    wait(700);
                    System.out.print(".");
                    wait(700);
                    System.out.print(".");
                    dis=new DataInputStream(System.in);
                    i2=0;
                    while(dis.available()==0)
                    {
                        if(i2++==2)
                        break;
                                System.out.print("\n\t\t\t" + close[i2]);
                                wait(700);
                                clear();
                                System.out.println();
                                System.out.print("\t\t\tClosing Encrypt !T ...");
                    }
                    System.out.println("\n\t\t\tEncrypt !T Closed");
                    break;
                }
            }
        }
    }
}
class AnagramSolver
{
    static int coins=500,level=1,xp=0;
    static String dicWord[][]={{"acid","ally","bait","byte","chef","clue","daze","doth","earl","evil","frog","fund","gram","gulf","hack","hurl","idle","iris","jade","jolt","kerb","kiln","lake","lark","mail","malt","nape","neon","oath","orbs","play","plea","quad","quiz","rage","reed","scar","send","taut","thaw","unix","urge","vein","volt","wand","writ","xeno","xmas","yarn","yoke","zeus","zing"},{"abandoned","accessible","baboonish","blackboard","classmates","commodore","discovered","dissipate","eagerness","economists","fabricated","fruitcake","gigabytes","grasslands","highlight","hygroscope","icehouses","inspectors","junctions","jellybeans","keyboards","knockdowns","labelling","laboratory","mackintosh","multiplex","natalities","necessity","offloading","ordinance","pacemaker","postoffice","quadratic","quarantine","radiology","reactivate","servitude","shattering","tableland","throughout","ultrasonic","utilities","vacancies","vermicelli","wackiness","waterproof","xenophobia","xerophyte","yachtings","yardmaster","zoologist","zoological"},{"abbreviated","abbreviation","babysitting","bacchanalian","cabinetmaker","cabinetwork","damnability","deaccessions","earnestness","earsplitting","fabricating","facelessness","gainfulness","gallbladders","haberdashers","habiliments","icebreakers","iconoclastic","jackhammers","jacksonville","kakistocracy","kettledrums","laborsaving","laboratorian","macadamizes","machinations","naphthalene","narcissistic","oarsmanship","obdurateness","pacesetters","pacification","quackishness","quacksalver","racecourses","racketeering","sabbaticals","saccharinely","tablecloths","tangentially","ulcerations","ultimateness","vacationing","vacationland","wainscoting","wallpapering","xanthophyll","xylophonists","yachtswoman","yugoslavians","zoopathology","zymoplastic"}};
    static boolean time_over=false;
    TimerTask task = new TimerTask()
    {
        public void run()
        {
            try
            {
                Toolkit.getDefaultToolkit().beep();
                wait(500);
                Toolkit.getDefaultToolkit().beep();
                wait(500);
                Toolkit.getDefaultToolkit().beep();
                wait(500);
            }
            catch(InterruptedException ie)
            {}
            System.out.println("\n\n\t\tTime Over !!!");
            System.out.println("\tYou Failed To Answer Within The Given Time!!!");
            System.out.println("Press Enter To Continue...");
            new Scanner(System.in).nextLine();
            time_over=true;
        }
    };
   //Generates Random Word
    static String[] getRandomWord(char c)throws IOException
    {
        String word[]=new String[10];
        int lev=0;
        if(c=='e')
        {
            lev=0;
        }
        else if(c=='m')
        {
            lev=1;
        }
        else
        {
            lev=2;
        }
        for(int i=0;i<10;i++)
        {
            String tmpWord=dicWord[lev][rnd(51)];
            boolean flag=false;
            for(int j=0;j<i;j++)
            {
                if(word[j].equalsIgnoreCase(tmpWord))
                    flag=true;
            }
            if(flag)
            {
                i--;
                continue;
            }
            else
            {
                word[i]=tmpWord;
            }
        }
            return word;
    }
   //Jumbles In-putted Word
    static String jumble(String w,char c)
    {
        int lev;
        if(c=='e')
            lev=10;
        else if(c=='m')
            lev=25;
        else
            lev=50;
        char in[]=w.toCharArray();
        for(int i=0;i<lev;)
        {
            int r1=(int)Math.abs(rnd(in.length)-1);
            int r2=(int)Math.abs(rnd(in.length)-1);
            if(r1<in.length && r1>=0 && r2<in.length && r2>=0)
            {
                i++;
                char t=in[r1];
                in[r1]=in[r2];
                in[r2]=t;
            }
        }
        String jumbledString="";
        for(int i=0;i<in.length;i++)
            jumbledString+=in[i]+"";
        return jumbledString;
    }
    //Easy Level InterFace
    boolean easyLevel()throws IOException
    {
                clear();
                    System.out.println();
        System.out.println("\tThere Are 10 Questions...\n"
                          +"\tYou Need To Answer All Questions Within 230 Seconds...\n"
                          +"\tYou Need 7 Correct Answer To Proceed To Next Level...\n"
                          +"\tPress Enter To Continue...\n");
        new Scanner(System.in).nextLine();
        int correct=0,question=0,tmpcoins=0,tmpxp=0;
        long time=System.currentTimeMillis();
        String[] word1=getRandomWord('e');
        Timer timer = new Timer();
        timer.schedule(task,230*1000);//230 Seconds
        for(;question<10;question++)
        {
                clear();
                    System.out.println();
            for(int i=0;i<12;i++)
                System.out.println();
            String jw=jumble(word1[question],'e');
            String word="";
            for(int i=0;i<jw.length();i++)
                word+=jw.charAt(i)+" ";
            for(int i=0;i<(80-jw.length())/2;i++)
            {
                System.out.print(" ");
            }
            System.out.println(word.toUpperCase());
            for(int i=0;i<12;i++)
                System.out.println();
            System.out.println("\t\tTime Left: "+(200.0-(System.currentTimeMillis()-time)/1000));
            System.out.print("Enter Your Answer: ");
            String inp= new Scanner(System.in).nextLine().trim();
            if(word1[question].equalsIgnoreCase(inp))
            {
                timer.cancel();
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\t\t\tCorrect Answer!!!");
                ++correct;
                tmpcoins+=30*level;
                tmpxp+=10*level;
            }
            else
            {
                System.out.println("\t\t\tWrong Answer!!!");
                System.out.println("\t\t\tCorrect Answer:-"+word1[question]);
                tmpcoins-=10*level;
            }
            if(time_over)
            {
                System.out.println("\t\t"+(9-question)+" Skipped !!!");
                tmpcoins-=10*level*(9-question);
                time_over=false;
            }
            else
            {
                System.out.println("\tPress Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
        }
        timer.cancel();
        System.out.println("Total Questions:- 10");
        System.out.println("Number Of Answers Correct:- "+correct);
        System.out.println("Win %:- "+correct/10.0*100+" %");
        if(tmpcoins<0)
            System.out.println("Coins Losed:- "+(int)Math.abs(tmpcoins));
        else
            System.out.println("Coins Gained:- "+tmpcoins);
        System.out.println("XP Gained:- "+tmpxp);
        xp+=tmpxp;
        System.out.println("Time Taken:- "+(System.currentTimeMillis()-time)/1000.0+" Seconds");
        coins+=tmpcoins;
        System.out.println("\tPress Enter To Continue...");
        new Scanner(System.in).nextLine();
        if(correct>=7)
        {
            boolean flag=true;
            while(flag)
            {
                System.out.println("Do You Want To Proceed To Next Level?(Y/N)");
                String i=new Scanner(System.in).nextLine();
                i=i.trim();
                if (i.equalsIgnoreCase("Y"))
                {
                    flag=false;
                    return true;
                }
                else if(i.equalsIgnoreCase("N"))
                {
                    return false;
                }
                else
                    System.out.println("\t\t\tPlease Input A Valid Input (Y/N)");
            }
            return true;
        }
        else
        return false;
    }
    //Medium Level InterFace
    boolean mediumLevel()throws IOException
    {
                clear();
                    System.out.println();
        System.out.println("\tThere Are 10 Questions...\n"
                          +"\tYou Need To Answer All Questions Within 280 Seconds...\n"
                          +"\tYou Need  08 Correct Answers To Proceed To Next Level...\n"
                          +"\tPress Enter To Continue...\n");
        new Scanner(System.in).nextLine();
        int correct=0,question=0,tmpcoins=0,tmpxp=0;
        long time=System.currentTimeMillis();
        String[] word1=getRandomWord('m');
        Timer timer = new Timer();
        timer.schedule(task,280*1000);//280 Seconds
        for(;question<10;question++)
        {
                clear();
                    System.out.println();
            for(int i=0;i<12;i++)
                System.out.println();
            String jw=jumble(word1[question],'m');
            String word="";
            for(int i=0;i<jw.length();i++)
                word+=jw.charAt(i)+" ";
            for(int i=0;i<(80-jw.length())/2;i++)
            {
                System.out.print(" ");
            }
            System.out.println(word.toUpperCase());
            for(int i=0;i<12;i++)
                System.out.println();
            System.out.println("\t\tTime Left: "+(200.0-(System.currentTimeMillis()-time)/1000));
            System.out.print("Enter Your Answer: ");
            String inp= new Scanner(System.in).nextLine().trim();
            if(word1[question].equalsIgnoreCase(inp))
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\t\t\tCorrect Answer!!!");
                ++correct;
                tmpcoins+=50*level;
                tmpxp+=20*level;
            }
            else
            {
                System.out.println("\t\t\tWrong Answer!!!");
                System.out.println("\t\t\tCorrect Answer:-"+word1[question]);
                tmpcoins-=20*level;
            }
            if(time_over)
            {
                System.out.println("\t\t"+(9-question)+" Skipped !!!");
                tmpcoins-=20*level*(9-question);
                time_over=false;
            }
            else
            {
                System.out.println("\tPress Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
        }
                timer.cancel();
        System.out.println("Total Questions:- 10");
        System.out.println("Number Of Answers Correct:- "+correct);
        System.out.println("Win %:- "+correct/10.0*100+" %");
        if(tmpcoins<0)
            System.out.println("Coins Losed:- "+(int)Math.abs(tmpcoins));
        else
            System.out.println("Coins Gained:- "+tmpcoins);
        System.out.println("XP Gained:- "+tmpxp);
        xp+=tmpxp;
        System.out.println("Time Taken:- "+(System.currentTimeMillis()-time)/1000.0+" Seconds");
        coins+=tmpcoins;
        System.out.println("\tPress Enter To Continue...");
        new Scanner(System.in).nextLine();
        if(correct>=8)
        {
            boolean flag=true;
            while(flag)
            {
                System.out.println("Do You Want To Proceed To Next Level?(Y/N)");
                String i=new Scanner(System.in).nextLine();
                i=i.trim();
                if (i.equalsIgnoreCase("Y"))
                {
                    flag=false;
                    return true;
                }
                else if(i.equalsIgnoreCase("N"))
                {
                    return false;
                }
                else
                    System.out.println("\t\t\tPlease Input A Valid Input (Y/N)");
            }
            return true;
        }
        else
        return false;
    }
    //Hard Level InterFace
    boolean hardLevel()throws IOException
    {
                clear();
                    System.out.println();
        System.out.println("\tThis Is The Final Level!!!");
        System.out.println("\tThere Are 10 Questions...\n"
                          +"\tYou Need To Answer All Questions Within 230 Seconds...\n"
                          +"\tYou Need  10 Correct Answer To Be The Champion Of The Game...\n"
                          +"\tPress Enter To Continue...\n");
        new Scanner(System.in).nextLine();
        int correct=0,question=0,tmpcoins=0,tmpxp=0;
        long time=System.currentTimeMillis();
            String[] word1=getRandomWord('h');
        Timer timer = new Timer();
        timer.schedule(task,330*1000);//330 Seconds
        for(;question<10;question++)
        {
                clear();
                    System.out.println();
            for(int i=0;i<12;i++)
                System.out.println();
            String jw=jumble(word1[question],'h');
            String word="";
            for(int i=0;i<jw.length();i++)
                word+=jw.charAt(i)+" ";
            for(int i=0;i<(80-jw.length())/2;i++)
            {
                System.out.print(" ");
            }
            System.out.println(word.toUpperCase());
            for(int i=0;i<12;i++)
                System.out.println();
            System.out.println("\t\tTime Left: "+(200.0-(System.currentTimeMillis()-time)/1000));
            System.out.print("Enter Your Answer: ");
            String inp= new Scanner(System.in).nextLine().trim();
            if(word1[question].equalsIgnoreCase(inp))
            {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("\t\t\tCorrect Answer!!!");
                ++correct;
                tmpcoins+=70*level;
                tmpxp+=30*level;
            }
            else
            {
                System.out.println("\t\t\tWrong Answer!!!");
                System.out.println("\t\t\tCorrect Answer:-"+word1[question]);
                tmpcoins-=30*level;
            }
            if(time_over)
            {
                System.out.println("\t\t"+(9-question)+" Skipped !!!");
                tmpcoins-=30*level*(9-question);
                time_over=false;
            }
            else
            {
                System.out.println("\tPress Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
        }
        timer.cancel();
        System.out.println("Total Questions:- 10");
        System.out.println("Number Of Answers Correct:- "+correct);
        System.out.println("Win %:- "+correct/10.0*100+" %");
        if(tmpcoins<0)
            System.out.println("Coins Losed:- "+(int)Math.abs(tmpcoins));
        else
            System.out.println("Coins Gained:- "+tmpcoins);
        System.out.println("XP Gained:- "+tmpxp);
        xp+=tmpxp;
        System.out.println("Time Taken:- "+(System.currentTimeMillis()-time)/1000.0+" Seconds");
        coins+=tmpcoins;
        System.out.println("\tPress Enter To Continue...");
        new Scanner(System.in).nextLine();
        if(correct==10)
        {
            System.out.println("\tSecond Winner Award Goes To "+Facebook.username+" !!!");
            System.out.println("\tFirst Winner Award Always Goes To SpeedX(Gyanaranjan Sahoo) !!!");
            new Scanner(System.in).nextLine();
            return true;
        }
        else
        return false;
    }
    //Gets A valid Input
    static int input(int n)
    {
        Scanner sc=new Scanner(System.in);
        try
        {
            String str=sc.nextLine().trim();
            int in=Integer.parseInt(str);
            if(in>=1 && in<=n)
            return in;
            else
            {
                System.out.print("Please Input a Number Between 1 to " + n+" :- ");
                return 0;
            }
        }
        catch(NumberFormatException nf)
        {
            System.out.print("\n\t\t\tPlease Input A Number :- ");
            return 0;
        }
    }
   //Generates Random Number
    static int rnd(int r)
    {
            int k=0;
            k=(int)(Math.random()*r+1);
            return k;
    }
    //Main Screen Interface
    static void mainMenu()throws Exception
    {
        boolean flag=true;
        if(Facebook.getDate().startsWith("26-09"))
        {
            System.out.println("\t\t\tToday Is Facebook's And Gyanaranjan Sahoo's Birthday");
            System.out.println("\t\tSo You Are A Lucky Player As You Get 1000 Coins !!!");coins=1000;
        }
        else
        System.out.println("\t\tYou Are A Lucky Player And You Get 500 Coins !!!");
        System.out.println("\t\tPress Enter To Continue !!!");
        new Scanner(System.in).nextLine();
        while(flag)
        {
            clear();
            //Coins
            String value="";
            value=coins+"";
            int l1=29-("Coins: ".length()+value.length()+1);
            System.out.print("      Coins: "+(char)36+" "+value);
            for(int i=0;i<l1;i++)
            {
                System.out.print(" ");
            }
            //Xp
            value="";
            value=xp+"";
            int totxp=100;
            for(int i=2;i<=level;i++)
            {
                totxp=totxp+(totxp*2)/3;
            }
            l1=28-("XP:- ".length()+value.length()+1+Integer.toString(totxp).length());
            System.out.print("XP:- "+value+" / "+totxp);
            for(int i=0;i<l1;i++)
            {
                System.out.print(" ");
            }
            //Level
            value="";
            value=level+"";
            System.out.print("Level: "+value);
            System.out.println("\n");
            System.out.println(" Cheat:n\\a                        XPBooster:n\\a               CoinBooster:n\\a");
            System.out.println("*******************************************************************************");
            System.out.println("*******************************************************************************");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    |            1 - Play                          |    ***********");
            System.out.println("************    |            2 - Achievements                  |    ***********");
            System.out.println("************    |            3 - Score Board                   |    ***********");
            System.out.println("************    |            4 - Buy Items                     |    ***********");
            System.out.println("************    |            5 - Get Coins                     |    ***********");
            System.out.println("************    |            6 - Help                          |    ***********");
            System.out.println("************    |            7 - Back                          |    ***********");
            System.out.println("************    |                                              |    ***********");
            System.out.println("************    +----------------------------------------------+    ***********");
            System.out.println("*******************************************************************************");
            System.out.println("*******************************************************************************");
            System.out.println("\n");
            System.out.print("\n\t\tEnter Your Choice:- ");
            int i=0;
            while(i==0)
            {
                i=input(7);
            }
            if(i==1)
            {
                for(int inp=0;inp<500;inp++)
                {
                    System.out.println();
                }
                System.out.println("\t\t\t\t1 - Easy");
                System.out.println("\t\t\t\t2 - Medium");
                System.out.println("\t\t\t\t3 - Hard");
                System.out.println("\t\t\t\t4 - Back");
            System.out.print("\n\t\tEnter Your Choice:- ");
                int in=0;
                while(in==0)
                {
                    in=input(4);
                }
                AnagramSolver as=new AnagramSolver();
                if(in==1)
                {
                    if(as.easyLevel())
                    {
                        if(as.mediumLevel())
                        {
                            if(as.hardLevel())
                            {
                                System.out.println("             Upgrade To Anagram Solver Pro at $19.99/- Only !!!");
                                System.out.println("             With All New Features,Levels,Questions & Lot More!!!");
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else
                        {
                            continue;
                        }
                    }
                    else
                    {
                        continue;
                    }
                }
                else if(in==2)
                {
                    if(as.mediumLevel())
                        {
                            if(as.hardLevel())
                            {
                                System.out.println("             Upgrade To Anagram Solver Pro at $19.99/- Only !!!");
                                System.out.println("             With All New Features,Levels,Questions & Lot More!!!");
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else
                        {
                            continue;
                        }
                }
                else if(in==3)
                {
                    if(as.hardLevel())
                    {
                        System.out.println("             Upgrade To Anagram Solver Pro at $19.99/- Only !!!");
                        System.out.println("             With All New Features,Levels,Questions & Lot More!!!");
                    }
                    else
                    {
                        continue;
                    }
                }
                else
                {
                    continue;
                }
                new Scanner(System.in).nextLine();
            }
            else if(i==2)
            {
                for(int inp=0;inp<500;inp++)
                {
                    System.out.println();
                }
                System.out.println("This Feature Is Not In Trial Version !!!");
                System.out.println("Please See Help To Activate Your Trial Version !!!");
                System.out.println("Press Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
            else if(i==3)
            {
                for(int inp=0;inp<500;inp++)
                {
                    System.out.println();
                }
                System.out.println("Your Name Will Not Be Listed Till You Activate This !!!");
                System.out.println("Please See Help To Activate Your Trial Version !!!");
                System.out.println("Press Enter To Continue...");
                new Scanner(System.in).nextLine();
                for(int inp=0;inp<500;inp++)
                {
                    System.out.println();
                }
                Scanner read=new Scanner(new File("C:\\Facebook\\Scoreboard.fb"));
                System.out.println("    Player Name                                                          Level  ");
                System.out.println(" -------------------                                                  ----------");
                System.out.print("  SpeedX                                                                  ");
                read.nextLine();
                System.out.println(read.nextLine());
                read.close();
                System.out.println("  Gyana                                                                    7    ");
                System.out.println("  Ranjan                                                                   6    ");
                System.out.println("  Blaze                                                                    5    ");
                System.out.println("  Buzz                                                                     5    ");
                System.out.println("  Spady                                                                    4    ");
                System.out.println("  ImMortal                                                                 3    ");
                System.out.println("  Blizzard                                                                 2    ");
                System.out.println("  Hunter                                                                   1    ");
                System.out.println("Press Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
            else if(i==4)
            {
                for(int inp=0;inp<500;inp++)
                {
                    System.out.println();
                }
                System.out.println("This Feature Is Not In Trial Version !!!");
                System.out.println("Please See Help To Activate Your Trial Version !!!");
                System.out.println("Press Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
            else if(i==5)
            {
                for(int inp=0;inp<500;inp++)
                {
                    System.out.println();
                }
                System.out.println("                    Anagram Solver Pro  -------  Coming Soon!!!");
                System.out.println("                +----------------------------------------------+               ");
                System.out.println("                |                                              |               ");
                System.out.println("                |     1 - Get 500   Coins   -   $ 1            |               ");
                System.out.println("                |     2 - Get 2000  Coins   -   $ 3            |               ");
                System.out.println("                |     3 - Get 5000  Coins   -   $ 6            |               ");
                System.out.println("                |     4 - Get 7500  Coins   -   $ 8            |               ");
                System.out.println("                |     5 - Get 10000 Coins   -   $ 10           |               ");
                System.out.println("                |     6 - Back                                 |               ");
                System.out.println("                |                                              |               ");
                System.out.println("                +----------------------------------------------+               ");
                System.out.println("             Upgrade To Anagram Solver Pro at $19.99/- Only !!!");
                int in=0;
                while(in==0)
                {
                    in=input(6);
                }
                if(in==6)
                {
                    continue;
                }
                System.out.println("\t\tCan'T Connect To Server");
                System.out.println("\t\tPlease Contact Gyanaranjan Sahoo To Solve Problems");
                System.out.println("\t\tOr To Get Coins At A Lower Rate !!!");
                System.out.println("\t\tPhone:- 7205595198 ");
                new Scanner(System.in).nextLine();
            }
            else if(i==6)
            {
                for(int inp=0;inp<500;inp++)
                {
                    System.out.println();
                }
                System.out.println("Instructions:\n\n"
                                    +"Your goal Find The Correct Word From Jumbled Words!!!\n"
                                    +"This Is Your Trial Version !!!\n"
                                    +"You May Have Some Features Locked Or n\\a!!!\n\n\n\n"
                                    +"\t\t\tPlease Activate Or Buy The Original Version At $9.99/-\n"
                                    +"\t\t\tTo Activate Contact:----------7205595198\n\n\n\n\n");
                System.out.println("About:\n\n" +
                                    "\t\t\tTitle: Anagram Solver\n" +
                                    "\t\t\tCreator: Gyanaranjan Sahoo\n" +
                                    "\t\t\tPhone: 7205595198\n"+
                                    "\t\t\tVersion: Trial\n");
                System.out.println("\tUpgrade To Anagram Solver Pro at $19.99/- Only !!!");
                System.out.println("Press Enter To Continue...");
                new Scanner(System.in).nextLine();
            }
            else
            {
                clear();
                int in=0;
                while(in<=93 || in>100)
                {
                    in=rnd(101);
                }
                DataInputStream dis=new DataInputStream(System.in);
                int i2=0;
                while(dis.available()==0)
                {
                    if(i2++==3)
                    break;
                    int m=0;
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t    \\|/");
                    System.out.println("\t\t\t\t   ="+in+"%=");
                    System.out.print("\t\t\t\t    /|\\");
                    wait(700);
                    clear();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t    \\|/");
                    System.out.println("\t\t\t\t    "+in+"%=");
                    System.out.print("\t\t\t\t    /|\\");
                    wait(700);
                    System.out.print("\b\b\b |\\");
                    wait(800);
                    System.out.print("\b\b \\");
                    wait(800);
                    System.out.print("\b");
                    wait(800);
                    clear();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t    \\|/");
                    System.out.println("\t\t\t\t    "+in+"%=");
                    wait(750);
                    clear();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t    \\|/");
                    System.out.println("\t\t\t\t    "+in+"%");
                    wait(750);
                    clear();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t    \\|");
                    System.out.println("\t\t\t\t    "+in+"%");
                    wait(750);
                    clear();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println("\t\t\t\t    \\");
                    System.out.println("\t\t\t\t    "+in+"%");
                    wait(750);
                    clear();
                    System.out.println("\t\t\t\tUn-Loading");
                    System.out.println();
                    System.out.println("\t\t\t\t    "+in+"%");
                    wait(750);
                    clear();
                    while(m<=17 || m>24)
                    {
                        m=rnd(30);
                    }
                    in-=m;
                }
                System.out.println("\n\t\t\tGame Closed!!!");
                flag=false;
            }
        }
    }
    //Clears screen
    static void clear()
    {
        for(int i=0;i<500;i++)
        {
            System.out.println();
        }
    }
    //Waits Specified Time
    static void wait(int r)
    {
        try
        {
            Thread.sleep(r);
        }
        catch(InterruptedException ie)
        {
            return;
        }
    }
    public static void Main()throws Exception
    {
        clear();
        int in=0;
        while(in<=17 || in>25)
        {
            in=rnd(30);
        }
        DataInputStream dis=new DataInputStream(System.in);
        int i2=0;
        while(dis.available()==0)
        {
            if(i2++==3)
            break;
            int m=0;
            System.out.println("\t\t\t\tLoading");
            System.out.println();
            System.out.println("\t\t\t\t  "+in+"%");
            wait(700);
            clear();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\");
            System.out.println("\t\t\t\t  "+in+"%");
            wait(700);
            clear();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\|");
            System.out.println("\t\t\t\t  "+in+"%");
            wait(700);
            clear();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\|/");
            System.out.println("\t\t\t\t  "+in+"%");
            wait(700);
            clear();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\|/");
            System.out.println("\t\t\t\t  "+in+"%=");
            wait(700);
            clear();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\|/");
            System.out.println("\t\t\t\t  "+in+"%=");
            System.out.print("\t\t\t\t    \\");
            wait(500);
            System.out.print("\b\b|\\");
            wait(700);
            System.out.print("\b\b\b/|\\");
            wait(700);
            clear();
            System.out.println("\t\t\t\tLoading");
            System.out.println("\t\t\t\t  \\|/");
            System.out.println("\t\t\t\t ="+in+"%=");
            System.out.print("\t\t\t\t  /|\\");
            wait(700);clear();
            while(m<17 || m>25)
            {
                m=rnd(30);
            }
            in+=m;
        }
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("******************                                          *******************\n");
        System.out.print("******************     Welcome to SpeedX Productions!!!     *******************\n");
        System.out.print("******************                                          *******************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("******************     --------------------------------      ******************\n");
        System.out.print("******************     ||                            ||      ******************\n");
        System.out.print("******************     ||       Anagram Solver       ||      ******************\n");
        System.out.print("******************     ||                            ||      ******************\n");
        System.out.print("******************     --------------------------------      ******************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("*******************************************************************************\n");
        System.out.print("\n\n\n\n\n");
        new Scanner(System.in).nextLine();
        mainMenu();
    }
}
