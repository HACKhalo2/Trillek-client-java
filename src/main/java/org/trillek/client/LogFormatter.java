package org.trillek.client;

public class LogFormatter {

	private static LogFormatter _instance = null;
	private boolean debug = false;

	public static LogFormatter getInstance(boolean debug) {
		if(_instance == null) {
			_instance = new LogFormatter(debug);
		}

		return _instance;
	}

	public static LogFormatter getInstance() {
		if(_instance == null) {
			_instance = new LogFormatter(false);
		}

		return _instance;
	}

	private LogFormatter(final boolean debug) {
		this.debug = debug;
	}

	public void printException(Throwable t) {
		if(t == null) return; //If the Throwable is null, ignore it
		int indent = 1;

		//Print exceptions only if DebugMode is on or if the Throwable is an instance of a RuntimeException
		if(this.debug || (t instanceof RuntimeException)) {
			this.exception(t.getClass().getName(), indent);
			this.exception("Message: '"+t.getLocalizedMessage()+"'", indent);
			System.err.println(this.indentString("Stack Trace:", indent));
			indent++;

			StackTraceElement[] stack = t.getStackTrace();
			if(stack == null || stack.length == 0) {
				//Return if there were no StackTraceElements (or if it was null)
				System.err.println(this.indentString("(No StackTraceElements Available)", indent));
				return;
			}
			indent++;

			for(StackTraceElement line : stack) {
				if(line != null) System.err.println(this.indentString(line.toString(), indent));
			}
		}
	}

	public void exception(String message, final int indent) {
		String msg = "[EXCEPTION] ";
		if(message == null) msg += "(none)";
		else msg += message;

		System.err.println(this.indentString(msg, indent));
	}

	public void err(String message, final int indent) {
		this.err(null, message, indent);
	}

	public void err(String category, String message, final int indent) {
		String header = this.buildHeader("ERROR", category);

		System.err.println(this.indentString(header+message, indent));
	}

	public void warn(String message, final int indent) {
		this.warn(null, message, indent);
	}

	public void warn(String category, String message, final int indent) {
		String header = this.buildHeader("WARN", category);

		System.out.println(this.indentString(header+message, indent));
	}

	public void info(String message, final int indent) {
		this.info(null, message, indent);
	}

	public void info(String category, String message, final int indent) {
		String header = this.buildHeader("info", category);

		System.out.println(this.indentString(header+message, indent));
	}

	/**
	 * Returns if the RenderEngine's debug mode is enabled.<br />
	 * If debug mode is <b>true</b>, Exceptions and debug messages will be printed to the console,<br />
	 * else they will be suppressed. Info, Warning, and Error messages will still print with debug mode on.<br />
	 * 
	 */
	public void debug(String message, final int indent) {
		this.debug(null, message, indent);
	}

	public void debug(String category, String message, final int indent) {
		if(this.debug) {
			String header = this.buildHeader("debug", category);

			System.out.println(this.indentString(header+message, indent));
		}
	}

	/**
	 * Indents a String, similar to Paul's
	 * 
	 * @param message The message to indent
	 * @param indent the amount to indent
	 * @return the indented message
	 */
	private String indentString(final String message, final int indent) {
		String spacer = "";
		for(int i = 0; i < indent; i++) {
			spacer += "  ";
		}

		return new String(spacer+message);
	}

	/**
	 * Builds the header to the Message String
	 * 
	 * @param header The header to use
	 * @param category the optional category to use (use <b>null</b> for none)
	 * @return the Built header
	 */
	private String buildHeader(final String header, final String category) {
		String temp = "["+header;
		if(category != null) temp += "-"+category+"] ";
		else temp += "] ";

		return temp;
	}
}
