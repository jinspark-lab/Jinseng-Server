package common;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;


public class StatusReporter {

	public StatusReporter(){
		
	}
	
	public void PrintProperties(){
		System.getProperties().list(System.out);
	}
	
	public void PrintDiskIO(){
		
	}
	
	public void PrintDiskUsage(){

		File[] root = File.listRoots();
		for(File diskPartition : root){
			long totalCapacity = diskPartition.getTotalSpace();
			long freePartitionSpace = diskPartition.getFreeSpace();
			long usablePartitionSpace = diskPartition.getUsableSpace();

			System.out.println("Total Size in " + diskPartition + " drive : " + totalCapacity / (1024 * 1024 * 1024) + "GB");
			System.out.println("Usable space : " + usablePartitionSpace / (1024 * 1024 * 1024) + "GB");
			System.out.println("Free space : " + freePartitionSpace / (1024 * 1024 * 1024) + "GB");
		}
		
	}
	
	public void PrintSystemMemoryUsage(){
        MemoryMXBean membean = ManagementFactory.getMemoryMXBean( );
        MemoryUsage heap = membean.getHeapMemoryUsage( );
        System.out.println( "Heap Memory: " + heap.toString( ) );
        MemoryUsage nonheap = membean.getNonHeapMemoryUsage( );
        System.out.println( "NonHeap Memory: " + nonheap.toString( ) );


	}
	
	public void PrintServerMemoryUsage(){
		
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long usedMemory = totalMemory - freeMemory;
		
		System.out.println("Total Memory : " + totalMemory / (1024 * 1024) + "MB");
		System.out.println("Free Memory : " + freeMemory / (1024 * 1024) + "MB");
		System.out.println("Used Memory : " + usedMemory / (1024 * 1024) + "MB");
	}
	
	public void PrintCpuUsage(){
		OperatingSystemMXBean osMxBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		System.out.println("CPU Usage " + osMxBean.getSystemLoadAverage());
	}
	
	public void PrintThreadUsage(){
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		System.out.println("Thread Count : " + threadBean.getThreadCount());
		System.out.println("Thread CPU Time : " + threadBean.getCurrentThreadCpuTime());
		System.out.println("Thread User Time : " + threadBean.getCurrentThreadUserTime());
	}
	
	public void PrintThreadInfo(){
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] threadInfo = threadBean.getThreadInfo(threadBean.getAllThreadIds());
		for(ThreadInfo tInfo : threadInfo){
			long blockTime = tInfo.getBlockedTime();
			long waitTime = tInfo.getWaitedTime();
			long cpuTime = threadBean.getThreadCpuTime(tInfo.getThreadId());
			long userTime = threadBean.getThreadUserTime(tInfo.getThreadId());
			String msg = String.format(
					"%s: %d ns cpu time, %d ns user time, blocked for %d ms, waited %d ms",
					tInfo.getThreadName(), cpuTime, userTime, blockTime, waitTime);
			System.out.println(msg);
		}
	}
	
	public void PrintOSInfo(){
		OperatingSystemMXBean osbean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		
        System.out.println( "OS Name: " + osbean.getName( ) );
        System.out.println( "OS Arch: " + osbean.getArch( ) );
        System.out.println( "Available Processors: " + osbean.getAvailableProcessors( ) );
        System.out.println( "SystemLoadAverage: " + osbean.getSystemLoadAverage( ) );

	}
}
