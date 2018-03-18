package sock;

// 文件名 GreetingClient.java
 
import java.net.*;
import java.io.*;
import java.util.Date;
 
public class GreetingClient
{
   public static void main(String [] args)
   {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      try
      {
    	  //连接远程主机
         System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
         Socket client = new Socket(serverName, port);
         System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
         //创建文件流
         File file =new File("/home/bolvvv/Desktop/test.txt");
         FileInputStream fis =new FileInputStream(file);
         
         //创建与服务器连接的输出流
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         //发送文件基本信息
         out.writeUTF(file.getName());
         out.flush();
         out.writeLong(file.length());
         out.flush();
         //传输文件
         byte[] sendBytes =new byte[1024];
         int length =0;
         while((length = fis.read(sendBytes,0, sendBytes.length)) >0){
             out.write(sendBytes,0, length);
             out.flush();
         }
         //获取服务器信息
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         System.out.println("服务器响应： " + in.readUTF());
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}