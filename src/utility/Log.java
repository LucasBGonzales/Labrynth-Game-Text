package utility;

import javax.swing.JOptionPane;

/**
 * Log class provides easy means to convey information, errors and warnings
 * in the program by printing to the console.
 * @author Lucas Gonzales
 * @version 23.11.2017
 */
public class Log {
	public static final int LEVEL_DISABLED = 0;
	public static final int LEVEL_ERROR = 1;
	public static final int LEVEL_WARNING = 2;
	public static final int LEVEL_INFO = 3;
	private static boolean m_enableDebug;
	
	private int m_LogLevel;
	
	public Log()
	{ this(LEVEL_INFO); }
	
	public Log(int level)
	{
		m_LogLevel = level;
		m_enableDebug = false;
	}

	public void setDebug(boolean arg0)
	{ m_enableDebug = arg0; }
	
	public void setLevel(int level)
	{ m_LogLevel = level; }
	
	public void print(String message)
	{ System.out.print(message); }
	
	public void println(String message)
	{ System.out.println(message); }
	
	public void printDialog(String message)
	{
		JOptionPane.showMessageDialog(null,  message);
	}
	
	public void debug(String message)
	{
		if(m_enableDebug)
			System.out.println("[DEBUG]: " + message);
	}

	public void warn(String message)
	{
		if (m_LogLevel >= LEVEL_WARNING)
			System.out.println("[WARNING]: " + message);
	}

	public void info(String message)
	{
		if (m_LogLevel >= LEVEL_INFO)
			System.out.println("[INFO]: " + message);
	}

	public void error(String message)
	{
		if(m_LogLevel>= LEVEL_ERROR)
			System.out.println("[ERROR]: " + message);
	}
}
