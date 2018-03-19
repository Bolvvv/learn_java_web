package sock;
// 文件名 GreetingServer.java
 
import java.net.*;
import java.io.*;
 
public class GreetingServer extends Thread
{
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(100000);
   }
 
   public void run()
   {
      while(true)
      {
         try
         {
            //等待连接
            System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
            //阻塞，一直等待新的连接
            Socket server = serverSocket.accept();
            System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
            
            //创建输入流
            DataInputStream in = new DataInputStream(server.getInputStream());
            String fileName = in.readUTF();
            long fileLength = in.readLong();
            FileOutputStream fos =new FileOutputStream(new File("/home/bolvvv/Desktop/recevive_file/" + fileName));
            byte[] sendBytes =new byte[1024];
            int transLen =0;
            //接收文件
            System.out.println("----开始接收文件<" + fileName +">,文件大小为<" + fileLength +">----");
            while(true){
                int read =0;
                read = in.read(sendBytes);
                if(read == -1)
                    break;
                transLen += read;
                System.out.println("接收文件进度" +100 * transLen/fileLength +"%...");
                fos.write(sendBytes,0, read);
                fos.flush();
            }
            System.out.println("----接收文件<" + fileName +">成功-------");
            server.close();
         }catch(SocketTimeoutException s)//捕获异常
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {
      int port = Integer.parseInt(args[0]);
      try
      {
         Thread t = new GreetingServer(port);
         t.run();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
