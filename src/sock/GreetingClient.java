package sock;

// 文件名 GreetingClient.java

import java.net.*;
import java.io.*;
import java.util.Date;

public class GreetingClient {
	public static void main(String[] args) throws FileNotFoundException {
		// 设置判断文件是否更改的时间间隔
		final int INTERVAL_OF_TIME = 5000;// 5s
		//设置最新时间
		long lastTime = new Date().getTime();
		long nowTime;
		// 设定文件大小
		long last_file_lenth = 0;
		//设置端口和ip地址
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		while (true) {
			nowTime=new Date().getTime();
			if ((nowTime - lastTime) >= INTERVAL_OF_TIME) {
				try {
					//刷新最新时间
					lastTime = new Date().getTime();
					// 创建文件流
					File file = new File("/home/bolvvv/Desktop/test.txt");
					if (file.length() > last_file_lenth) {
						// 更新最新文件大小
						last_file_lenth = file.length();
						//创建文件流
						FileInputStream fis = new FileInputStream(file);
						// 连接远程主机
						System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
						Socket client = new Socket(serverName, port);
						System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
						// 创建与服务器连接的输出流
						OutputStream outToServer = client.getOutputStream();
						DataOutputStream out = new DataOutputStream(outToServer);
						// 发送文件基本信息
						out.writeUTF(file.getName());
						out.flush();
						out.writeLong(file.length());
						out.flush();
						// 传输文件
						byte[] sendBytes = new byte[1024];
						int length = 0;
						while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
							out.write(sendBytes, 0, length);
							out.flush();
						}
						client.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else continue;
		}
	}
}