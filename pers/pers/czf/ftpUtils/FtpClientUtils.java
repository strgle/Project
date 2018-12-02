package pers.czf.ftpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

public class FtpClientUtils{
	
	public static byte[] downLoad(String host,int port,String username,String password,String path){
		
		byte[] osbyte = new byte[0];
		
		FTPClient client = new FTPClient();  
        client.setType(FTPClient.TYPE_BINARY); 
        
        // 不指定端口，则使用默认端口21  
        try {
			client.connect(host, port);
			 // 用户登录  
	        client.login(username, password);
	        
	        ByteArrayOutputStream os = new  ByteArrayOutputStream(); 
	        client.download(path, os, 0, null);
	        client.disconnect(true);
	        osbyte = os.toByteArray();
		} catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException | FTPAbortedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return osbyte;
	}
	
	public static void main(String[] args) throws Exception{
		try {  
            // 创建客户端  
            FTPClient client = new FTPClient(); 
            client.setCompressionEnabled(true);
            client.setType(FTPClient.TYPE_BINARY); 
            // 不指定端口，则使用默认端口21  
            client.abortCurrentConnectionAttempt();
            client.connect("10.254.2.13", 21);  
           
            client.login("lims", "lims");
            ByteArrayOutputStream os = new  ByteArrayOutputStream(); 
            client.changeDirectory("mall");
            FTPFile[] files =  client.list();
            for(FTPFile file:files){
            	switch (file.getType()) {
					case FTPFile.TYPE_DIRECTORY:
						System.out.println("目录："+file.getName());
						break;
					case FTPFile.TYPE_FILE:
						System.out.println("文件："+file.getName());
						break;
					case FTPFile.TYPE_LINK:
						System.out.println("链接："+file.getLink());
						break;
					default:
						System.out.println("没有内容");
						break;
				};
            }
	        client.disconnect(true);
	        System.out.println(os.toByteArray().length);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
}
