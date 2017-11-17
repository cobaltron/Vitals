//---------For retrieving system data---------------------------------------------------------
import java.io.*;
import java.util.*;
import java.util.Locale;
import java.lang.management.*;
import java.net.InetAddress;  
import java.net.NetworkInterface;  
import java.net.SocketException;  
import java.net.UnknownHostException; 
import com.sun.servicetag.SystemEnvironment;
import java.lang.management.ManagementFactory;
//----------For Logger Function---------------------------------------------------------------
import java.io.IOException;  
import java.util.logging.FileHandler;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
import java.util.logging.SimpleFormatter;  
//--------------------------------------------------------------------------------------------- 
public class system_log 
{
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static void main(String args[])throws InterruptedException  
    {
        SystemEnvironment se = SystemEnvironment.getSystemEnvironment();
        com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        system_log rlahiri=new system_log();
        long tram=mxbean.getTotalSwapSpaceSize()/(1024*1024*1024);
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        String exe=System.getProperty("user.dir");
        String exe_log=exe.substring(0,2)+"/"+exe.substring(3)+"/MyLogFile.log";
        Logger logger = Logger.getLogger("MyLog");  
        TimeZone tz = Calendar.getInstance().getTimeZone();
        FileHandler fh; 
        InetAddress ip;
        try 
        {  
            fh = new FileHandler(exe_log);  
            logger.addHandler(fh); 
            //logger.setLevel(Level.ALL);  
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
        } 
        catch (UnknownHostException e) 
        {  
            e.printStackTrace();  
        } 
        catch (SocketException e)
        {  
            e.printStackTrace();  
        }  
        catch (Exception e)
        {  
            e.printStackTrace();  
        }  
        try
        {
            ip = InetAddress.getLocalHost();  
            //System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Network Details   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
            //System.out.println("Current host name : " + ip.getHostName());
            logger.info("Current host name : "+ip.getHostName());
            //System.out.println("Current IP address : " + ip.getHostAddress());
            logger.info("Current IP address : "+ ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);  
            byte[] mac = network.getHardwareAddress();  
            //System.out.print("Current MAC address : ");  
            StringBuilder sb = new StringBuilder();  
            for (int i = 0; i < mac.length; i++)
            {  
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));       
            }  
            //System.out.println(sb.toString());
            logger.info("Current MAC Address :"+sb.toString());
        }
        catch (SecurityException e) 
        {
            e.printStackTrace();  
        } 
        catch (IOException e) 
        {  
            e.printStackTrace();  
        }  
        logger.info("OS Name: "+System.getProperty("os.name"));
        logger.info("OS Version:" +System.getProperty("os.version"));
        logger.info(System.getProperty("os.arch") + " bit architechture");
        logger.info("Desktop environment appears to be: " + System.getProperty("sun.desktop"));
        logger.info("User Name="+System.getProperty("user.name")+ " has a home directory at: " + System.getProperty("user.home"));
        logger.info("Desktop environment appears to be: " + System.getProperty("sun.desktop"));
        logger.info("TotalRAM:"+mxbean.getTotalSwapSpaceSize()/(1024*1024*1024)+""+"GB");
        logger.info("Free Virtual Memory: "+freeMemory);
        logger.info("Total Virtual Memory : "+totalMemory);
        logger.info("Maximum Virtual Memory : "+maxMemory);
        logger.info("RAM Size="+memorySize+"Bytes");
        logger.info(System.getProperty("java.runtime.name") + " version " + System.getProperty("java.runtime.version") + System.getProperty("java.vm.version") + " by " + System.getProperty("java.vm.vendor"));
        logger.info("ID: "+tz.getID());
        logger.info("Temp directory is: " + System.getProperty("java.io.tmpdir"));
        logger.info("Execution Directory: " + System.getProperty("user.dir"));
        logger.info("BIOS Serial no:"+se.getSerialNumber());
        logger.info("System model:"+se.getSystemModel());
        logger.info("System Manufacturer:"+se.getSystemManufacturer());
        //rlahiri.log(tram,freeMemory,totalMemory,maxMemory,memorySize,sm,sman,bio);//,ipn,iphad);
        if (isWindows()) 
        {
            //System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Processor Details   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
            System.out.println("This is Microsoft Windows");
            //System.out.println("Processor Identifier:"+System.getenv("PROCESSOR_IDENTIFIER"));
            logger.info("Processor Identifier:"+System.getenv("PROCESSOR_IDENTIFIER"));
            //System.out.println("Processor Architecture:"+System.getenv("PROCESSOR_ARCHITECTURE"));
            logger.info("Processor Architecture:"+System.getenv("PROCESSOR_ARCHITECTURE"));
            //System.out.println("PROCESSOR_ARCHITEW6432:"+System.getenv("PROCESSOR_ARCHITEW6432"));
            logger.info("PROCESSOR_ARCHITEW6432:"+System.getenv("PROCESSOR_ARCHITEW6432"));
            //System.out.println("Number Of Processors(cores):"+System.getenv("NUMBER_OF_PROCESSORS"));
            logger.info("Number Of Processors(cores):"+System.getenv("NUMBER_OF_PROCESSORS"));
        } 
        else if (isMac()) 
        {
            System.out.println("This is Mac");
            //System.out.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
            logger.info("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
        }
        else if (isUnix()) 
        {
            System.out.println("This is Unix or Linux");
            //System.out.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
            logger.info("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
        }
        else if (isSolaris()) 
        {
            System.out.println("This is Solaris");
            //System.out.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
            logger.info("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
        }
        else 
        {
            System.out.println("Your OS is not supported!!");
        }
        Date d = new Date();
        //System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"    Os Details   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        //System.out.println("OS Name: "+OS.toUpperCase());
        //System.out.println("OS Version " + System.getProperty("os.version") + " on " + System.getProperty("os.arch") + " bit architechture");
        //String userName = System.getProperty("user.name");
        //System.out.println("Desktop environment appears to be: " + System.getProperty("sun.desktop"));
        //System.out.println("User Name="+userName + " has a home directory at: " + System.getProperty("user.home"));
        //System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Time And Date   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        System.out.println("TimeZone: "+tz.getDisplayName());
        System.out.println("The date is " + d);
        //System.out.println("ID: "+tz.getID());
        //System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Locale  "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        System.out.println("System Locale: "+Locale.getDefault());
        //System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Memory Status   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        /*System.out.println("TotalRAM:"+mxbean.getTotalSwapSpaceSize()/(1024*1024*1024)+""+"GB");
        System.out.println("RAM Size="+memorySize+" Bytes");
        System.out.println("Free Virtual Memory: " + freeMemory+" MB");
        System.out.println("Total Virtual Memory : "+ totalMemory+" MB");
        System.out.println("Maximum Virtual Memory : "+ maxMemory+" MB");
        System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Java Environment   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        System.out.println(System.getProperty("java.runtime.name") + " version " + System.getProperty("java.runtime.version") + System.getProperty("java.vm.version") + " by " + System.getProperty("java.vm.vendor"));
        System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Directory and Paths   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        System.out.println("Execution Directory: " + System.getProperty("user.dir"));
        System.out.println("Temp directory is: " + System.getProperty("java.io.tmpdir"));
        System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   System Deatails   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        System.out.println("BIOS Serial no:"+se.getSerialNumber());
        System.out.println("System model:"+se.getSystemModel());
        System.out.println("System Manufacturer:"+se.getSystemManufacturer());
        */
        File[] roots = File.listRoots();
        System.out.println();
        //System.out.println("||"+'\u2591'+'\u2591'+'\u2591'+'\u2591'+'\u2592'+'\u2592'+'\u2592'+'\u2593'+'\u2593'+'\u2593'+'\u2593'+"   Hard Disk Drives & Removable Storage   "+'\u2593'+'\u2593'+'\u2593'+'\u2593'+'\u2592'+'\u2592'+'\u2592'+'\u2591'+'\u2591'+'\u2591'+'\u2591'+"||");
        for (File root : roots)
        {
            //System.out.println("File system root path: " + root.getAbsolutePath());
            logger.info("File system root path: " + root.getAbsolutePath());
            //System.out.println("Total space (Megabytes): " + root.getTotalSpace() / 1048576);
            logger.info("Total space (Megabytes): " + root.getTotalSpace() / 1048576);
            //System.out.println("Usable space (Megabytes): " + root.getUsableSpace() / 1048576);
            logger.info("Usable space (Megabytes): " + root.getUsableSpace() / 1048576);
            System.out.println();
        }
    }
    public static boolean isWindows() 
    {
        return (OS.indexOf("win") >= 0);
    }
    public static boolean isMac() 
    {
        return (OS.indexOf("mac") >= 0);
    }
    public static boolean isUnix() 
    {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
    public static boolean isSolaris() 
    {
        return (OS.indexOf("sunos")>= 0);
    }
    //----------------------------------------------------------------------------------------------------------------------
}

