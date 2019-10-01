<%@ page import="java.io.*" %>
<%@ page import="com.sun.xml.internal.ws.policy.privateutil.PolicyUtils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
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
        private Statement s;    //操作JDBC
        private Connection C;
        PreparedStatement ps=null;


        private ArrayList<ClientData> clientList= new ArrayList<>();

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

            String url = "jdbc:mysql://127.0.0.1:3306/JavaWeb?characterEncoding=utf-8";
            String user = "nc";
            String psw = "klaythompson11";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("驱动初始化成功！");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try{
                C = DriverManager.getConnection(url, user, psw);
                s = C.createStatement();
                String query = "SELECT * FROM UserData ";
                ResultSet r = s.executeQuery(query);
                while( r.next() ){
                    clientList.add(new ClientData(r.getString(1),
                            r.getString(2), r.getString(3)));
                    System.out.println("读到数据");
                }
            }catch (SQLException E){
                E.printStackTrace();
            }

        }

        private void saveClientData(){
            try{
                System.out.println("saveeeee");
                String insert = "INSERT INTO UserData(UserName, password, sex) VALUES(?, ?, ?)";
                ps = C.prepareStatement(insert);
                ps.setString(1, userName);
                ps.setString(2, password);
                ps.setString(3, sex);
                ps.executeUpdate();     //必须要更新
                System.out.println("写入数据");
            }catch (SQLException E){
                E.printStackTrace();
            }

        }


        public String checkHadRegistered(){
            System.out.println("checkkkk");
            if( clientList.size() == 0 ){
                saveClientData();
                return message = "注册成功" + userName;
            }
            for(ClientData c : clientList){
                if(userName.equals(c.getUserName())){
                    return message = "注册失败,该用户已经注册";
                }
                System.out.println(clientList.get(0));
            }
            saveClientData();
            return message = "注册成功";
        }

        public void closeResource(){
            try{
                C.close();
                s.close();
            }catch (SQLException E){
                E.printStackTrace();
            }

        }
    }

    ClientDataDeal deal = new ClientDataDeal();
    String message = deal.checkHadRegistered();
    deal.closeResource();
%>



<script language="javascript">
    alert("<%=message%>");
    window.location.href='register02.jsp';
</script>
</html>
