<%@ page import="java.io.*" %>
<%@ page import="com.sun.xml.internal.ws.policy.privateutil.PolicyUtils" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: nc
  Date: 2019/9/24
  Time: 下午7:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>处理登录界面</title>
</head>

<% request.setCharacterEncoding("UTF-8");

    class ClientDataDeal{
        public String message=null;    //用与发送注册消息
        private String userName;
        private String password;
        private String sex;
        public ArrayList<ClientData> clientList= new ArrayList<>();

        class ClientData{
            private String userName;
            private String password;
            private String sex;

            public ClientData(String userName, String password, String sex){
                this.userName = userName;
                this.password = password;
                this.sex = sex;
            }

            public ClientData(){}

            public String getUserName(){
                return userName;
            }


        }
        ClientDataDeal(){
            userName = request.getParameter("user");
            password = request.getParameter("password");
            sex = request.getParameter("sex");
        }

        private void saveClientData(){
            try{
                String path = "/home/nc/Program/Java/task/Experiment4/doc/user.txt";
                BufferedWriter userDataWrite = new BufferedWriter(new FileWriter(path, true));
                String data = userName + "," + password + "," + sex + "\n";
                userDataWrite.write(data, 0, data.length());
                userDataWrite.close();
            }catch (IOException E){
                E.printStackTrace();
            }
        }

        private void ReadClientData(){
            try{
                String path = "/home/nc/Program/Java/task/Experiment4/doc/user.txt";
                BufferedReader userClientRead =  new BufferedReader(new FileReader(path));
                String line = null;
                while ( (line = userClientRead.readLine()) != null ){
                    String [] s = line.split(",");
                    clientList.add(new ClientData(s[0], s[1], s[2]));
                }
                userClientRead.close();
            }catch (IOException E){
                E.printStackTrace();
            }
        }

        public String checkHadRegistered(){
            ReadClientData();
            if( clientList.size() == 0 ){
                saveClientData();
                return message = "注册成功" + userName;
            }
            for(ClientData c : clientList){
                if(userName.equals(c.getUserName())){
                    return message = "注册失败,该用户已经注册";
                  }
                }
            saveClientData();
            return message = "注册成功";
            }
    }

    ClientDataDeal deal = new ClientDataDeal();
    String message = deal.checkHadRegistered();
%>



<script language="javascript">
    alert("<%=message%>");
    window.location.href='register.jsp';
</script>
</html>
