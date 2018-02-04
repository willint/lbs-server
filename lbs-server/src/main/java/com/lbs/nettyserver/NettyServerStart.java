package com.lbs.nettyserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lbs.nettyserver.handler.ChannelInitialHandler;

/**
 * 
 *
 *
 */
public class NettyServerStart
{
  private static int port;
  public static ApplicationContext factory;
  private static final Log logger = LogFactory.getLog(NettyServerStart.class);
  public static void main(String[] args)
    throws Exception
  {
//	  DOMConfigurator.configureAndWatch("config/log4j.xml");
    if (args.length > 0)
      port = Integer.parseInt(args[0]);
    else {
      port = 8189;
    }
    run();
  }

  private static void run()
    throws Exception
  {
    logger.info("server is running……");
    System.out.println("server is running……");
    factory = new ClassPathXmlApplicationContext("config/application-context.xml");
    ChannelInitialHandler initializer = (ChannelInitialHandler)factory.getBean(ChannelInitialHandler.class);

    int filePort = port + 1;
    int msgPort = port + 2;
    initializer.setBizPort(port);
    initializer.setFilePort(filePort);
    initializer.setMsgPort(msgPort);
    NettyServer server = new NettyServer(port, filePort, msgPort);
    server.setInitializer(initializer);
    server.run();
//	  System.out.println( Md5Util.getEncryptedPwd("2017053100011"));
  }
}