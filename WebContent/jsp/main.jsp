<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*"%>
<html>
<head>
<title> 文件读取</title>
<meta http-equiv="refresh" content="5">
</head>
<body>
<%
File file_directory = new File("");
String directory = file_directory.getAbsolutePath();
FileReader fr=new FileReader(directory + "//recevive_file//test.txt");//建立FileReader对象，并实例化为fr
BufferedReader br=new BufferedReader(fr);//建立BufferedReader对象，并实例化为br
String Line=br.readLine();//从文件读取一行字符串
//判断读取到的字符串是否不为空
while(Line!=null){
  out.println(Line + "<br>");//输出从文件中读取的数据
  Line=br.readLine(); //从文件中继续读取一行数据
}
br.close();//关闭BufferedReader对象
fr.close();//关闭文件
%>
</body>
</html> 