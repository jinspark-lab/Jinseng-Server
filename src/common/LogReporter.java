package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogReporter {
	/***
	 * 		LogReporter class uses JUL (Java Util Logging). So It depends on JUL configuration and policies.
	 */
	private static final Logger LOGGER = Logger.getLogger(LogReporter.class.getName());
	private static Handler consoleHandle = new ConsoleHandler();									//Basically LogReporter uses consoleHandler.
	private static Map<String, FileHandler> fileHandles = new HashMap<String, FileHandler>();	//Store file mapper in memory.
	
	//Filter not yet be implemented!!!
	
	public LogReporter(){
		
	}
	
	/***
	 * Append Console Handler to main logger.
	 */
	public static void AppendConsoleLogger(){
		consoleHandle.setLevel(Level.ALL);
		consoleHandle.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(consoleHandle);
	}
	public static void AppendConsoleLogger(Level level){
		consoleHandle.setLevel(level);
		consoleHandle.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(consoleHandle);
	}
	public static void AppendConsoleLogger(Level level, Formatter logFormat){
		consoleHandle.setLevel(level);
		consoleHandle.setFormatter(logFormat);
		LOGGER.addHandler(consoleHandle);
	}
	
	/***
	 *  Remove Console Handler from main logger.
	 */
	public static void RemoveConsoleLogger(){
		LOGGER.removeHandler(consoleHandle);
	}
	
	/***
	 *  Append File Handler to main logger.
	 * @param fileName logging file name.
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void AppendFileLogger(String fileName) throws SecurityException, IOException{
		fileHandles.put(fileName, new FileHandler(fileName));
		fileHandles.get(fileName).setLevel(Level.ALL);
		fileHandles.get(fileName).setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fileHandles.get(fileName));
	}
	public static void AppendFileLogger(String fileName, Level level) throws SecurityException, IOException{
		fileHandles.put(fileName,  new FileHandler(fileName));
		fileHandles.get(fileName).setLevel(level);
		fileHandles.get(fileName).setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fileHandles.get(fileName));
	}
	public static void AppendFileLogger(String fileName, Level level, Formatter logFormat) throws SecurityException, IOException{
		fileHandles.put(fileName,  new FileHandler(fileName));
		fileHandles.get(fileName).setLevel(level);
		fileHandles.get(fileName).setFormatter(logFormat);
		LOGGER.addHandler(fileHandles.get(fileName));
	}
	
	/***
	 *  Remove File Handler from main logger.
	 * @param fileName logging file name.
	 */
	public static void RemoveFileLogger(String fileName){
		LOGGER.removeHandler(fileHandles.get(fileName));
		fileHandles.remove(fileName);
	}
	
	/***
	 *  Info Logger
	 * @param msg means log record.
	 */
	public static void LogInfo(String msg){
		LOGGER.info(msg);
	}
	
	/***
	 *  Error Logger
	 * @param msg means log record.
	 */
	public static void LogError(String msg){
		LOGGER.severe(msg);;
	}
}
