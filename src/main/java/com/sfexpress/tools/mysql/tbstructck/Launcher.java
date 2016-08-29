package com.sfexpress.tools.mysql.tbstructck;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
 * web应用启动器
 * 
 * @author CrazyPig
 * @since 2016-08-03
 *
 */
public class Launcher {
	
	public static final int DEFAULT_PORT = 8080;
	public static final String DEFAULT_CONTEXT_PATH = "/mysql-tbstruct-cmpt-ck";
	private static final String DEFAULT_APP_CONTEXT_PATH = "src/main/webapp";
	
	private static int port = DEFAULT_PORT;
	private static String contextPath = DEFAULT_CONTEXT_PATH;
	
	private static Options opts;
	private static HelpFormatter hf = new HelpFormatter();
	private static final String USAGE = "./startup.bat(startup.sh) -d <arg-d> -p <arg-p>";
	
	public static void main(String[] args) {
		
		createOptions();
		try {
			parseOptions(args);
		} catch (ParseException e) {
//			e.printStackTrace();
			hf.printHelp(USAGE, opts);
			System.exit(-1);
		}
		
		runJettyServer(port, contextPath);
		
	}
	
	public static void runJettyServer(int port, String contextPath) {
		
		Server server = createJettyServer(port, contextPath);
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				server.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 		
	}
	
	public static Server createJettyServer(int port, String contextPath) {
		
		Server server = new Server(port);
        server.setStopAtShutdown(true);

        ProtectionDomain protectionDomain = Launcher.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
		String warFile = location.toExternalForm();
		
        WebAppContext context = new WebAppContext(warFile, contextPath);
        context.setServer(server);

        // 设置work dir,war包将解压到该目录，jsp编译后的文件也将放入其中。
        String currentDir = new File(location.getPath()).getParent();
        File workDir = new File(currentDir, "work");
        context.setTempDirectory(workDir);

        server.setHandler(context);
        return server;
		
	}
	
	public static Server createDevServer(int port, String contextPath) {
		
		Server server = new Server();
		server.setStopAtShutdown(true);
		
		ServerConnector connector = new ServerConnector(server);
		// 设置服务端口
		connector.setPort(port);
		connector.setReuseAddress(false);
		server.setConnectors(new Connector[] {connector});
		
		// 设置web资源根路径以及访问web的根路径
		WebAppContext webAppCtx = new WebAppContext(DEFAULT_APP_CONTEXT_PATH, contextPath);
		webAppCtx.setDescriptor(DEFAULT_APP_CONTEXT_PATH + "/WEB-INF/web.xml");
		webAppCtx.setResourceBase(DEFAULT_APP_CONTEXT_PATH);
		webAppCtx.setClassLoader(Thread.currentThread().getContextClassLoader());
		server.setHandler(webAppCtx);
		
		return server;
	}
	
	private static void createOptions() {
		
		opts = new Options();
		opts.addOption(new Option("d", true, "web base context directory, default : /mysql-tbstruct-ck"));
		Option p = new Option("p", true, "port, default : 8080");
		p.setType(Integer.class);
		opts.addOption(p);
		opts.addOption(new Option("h", false, "print help"));
	
	}
	
	private static void parseOptions(String[] args) throws ParseException {
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmdLine = parser.parse(opts, args);
		
		if(cmdLine.hasOption("h")) {
			hf.printHelp(USAGE, opts);
			System.exit(0);
		}
		
		if(cmdLine.hasOption("d")) {
			contextPath = cmdLine.getOptionValue("d");
		}
		if(cmdLine.hasOption("p")) {
			port = Integer.valueOf(cmdLine.getOptionValue("p"));
		}
	}
}
