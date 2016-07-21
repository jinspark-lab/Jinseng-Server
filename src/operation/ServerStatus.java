package operation;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import common.TextUtil;
import connection.ConnectionManager;

public class ServerStatus {
	
	private static ServerStatus stat = new ServerStatus();

	private static String serverName = "";
	private static long numberOfCurrentUsers = 0;
	private static long numberOfTotalUsers = 0;
	private static int numberOfThreads = 0;
	private static long numberOfRequest = 0;
	
	private static long serverTotalMemory = 0;
	private static long serverFreeMemory = 0;
	private static long serverUsedMemory = 0;
	private static long serverRemainMemory = 0;
	private static double serverMemoryUsage = 0.0;
	private static long serverRunningTime = 0;
	
	private static String vendorName = "";
	private static double numberOfCache = 0;

	private static Map<String, String> systemInfo = new LinkedHashMap<String, String>();
	private static Map<String, Double> systemDiskUsage = new LinkedHashMap<String, Double>();

	private static String jvmName = "";
	private static String jvmVersion = "";
	private static String jvmVendor = "";
	private static String specName = "";

	private static long processCpuTime = 0;
	
	private static String osName = "";
	private static String osArch = "";
	private static String osVersion = "";
	private static double cpuUsage = 0.0;
	private static int availableProcessors = 0;
	
	private static ConnectionManager manager = ConnectionManager.GetInstance();
	
	private ServerStatus(){
		
	}
	
	private static void getRuntimeInfo(){
		
		RuntimeMXBean rbean = ManagementFactory.getRuntimeMXBean();
		serverRunningTime = rbean.getUptime();
		jvmName = rbean.getVmName();
		jvmVersion = rbean.getVmVersion();
		jvmVendor = rbean.getVmVendor();
		specName = rbean.getSpecName();

	}
	
	private static void getOSInfo(){
		
		OperatingSystemMXBean osbean = ManagementFactory.getOperatingSystemMXBean();
		osName = osbean.getName();
		osArch = osbean.getArch();
		osVersion = osbean.getVersion();
		cpuUsage = osbean.getSystemLoadAverage();
		availableProcessors = osbean.getAvailableProcessors();

	}
	
	private static void getMemoryInfo(){

//		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		Runtime rntime = Runtime.getRuntime();
		serverTotalMemory = rntime.totalMemory();
		serverFreeMemory = rntime.freeMemory();
		serverUsedMemory = serverTotalMemory - serverFreeMemory;
		serverRemainMemory = rntime.maxMemory();
		serverMemoryUsage = ((double)serverUsedMemory * 100 / (double)serverTotalMemory);
	}
	
	private static void getServerManagementInfo(){
		serverName = manager.getServerName();
		numberOfCurrentUsers = manager.getNumberOfCurrentUsers();
		numberOfTotalUsers = manager.getNumberOfTotalUsers();
	}
	
	private static void getDiskInfo(){
		File[] drives = File.listRoots();
		if(drives != null && drives.length > 0){
			for(File disk : drives){
				long totalSpace = disk.getTotalSpace();
				long usedSpace = totalSpace - disk.getFreeSpace();
				double usage = (double)usedSpace * 100 / (double)totalSpace;
				systemDiskUsage.put(disk.getName(), usage);
				
				FileSystemView fsv = FileSystemView.getFileSystemView();
				systemInfo.put(disk.getName(), fsv.getSystemTypeDescription(disk));
			}
		}
	}
	
	public static ServerStatus getStatus(){
		getRuntimeInfo();
		getOSInfo();
		getMemoryInfo();
		getServerManagementInfo();
		getDiskInfo();
		return stat;
	}

	public String toString(){
		
		String context = "Server Name:" + serverName + TextUtil.CRLF + "Number Of Current Users:" + numberOfCurrentUsers + TextUtil.CRLF + "Number Of Total Users:" + numberOfTotalUsers + TextUtil.CRLF + "Server Total Memory:" + serverTotalMemory + 
				TextUtil.CRLF + "Server Free Memory:" + serverFreeMemory + TextUtil.CRLF + "Server Used Memory:" + serverUsedMemory + TextUtil.CRLF + "Server Remain Memory:" + serverRemainMemory + TextUtil.CRLF + "Server Memory Usage:" + serverMemoryUsage +
				TextUtil.CRLF + "Running Time:" + serverRunningTime + TextUtil.CRLF + "OS Name:" + osName + TextUtil.CRLF + "OS Arch:" + osArch + TextUtil.CRLF + "OS Version:" + osVersion + TextUtil.CRLF + "CPU Usage:" + cpuUsage + TextUtil.CRLF + "Available Processors:" + availableProcessors;
		context += TextUtil.CRLF + "System Information" + TextUtil.CRLF;
		for(String key : systemInfo.keySet()){
			context += "Name:" + key + "\tDesc:" + systemInfo.get(key) + "\tUsage:" + systemDiskUsage.get(key) + TextUtil.CRLF;
		}
		context += "JVM Name:" + jvmName + TextUtil.CRLF + "JVM Version:" + jvmVersion + TextUtil.CRLF + "jvmVendor:" + jvmVendor + TextUtil.CRLF + "jvmSpec:" + specName;
		context += TextUtil.CRLF;
		
		return context;
	}
}
