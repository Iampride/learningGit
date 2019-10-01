<%@ page import="java.security.PublicKey" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.jws.soap.SOAPBinding" %>
<%--
  Created by IntelliJ IDEA.
  User: nc
  Date: 2019/9/25
  Time: 上午7:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>处理用户登录界面02</title>
</head>
<body>
</body>
<%
    class DoLogin{
        private String userName;
        private String password;
        private ArrayList<UserData> userdataList = new ArrayList<>();
        private Statement s;    //操作JDBC
        private Connection C;

        class UserData{
            private String userName;
            private String password;

            UserData(){}
            UserData(String userName, String password){
                this.userName = userName;
                this.password = password;
            }

            public String getUserName(){
                return userName;
            }

            public String getPassword(){
                return password;
            }

        }


        public DoLogin(){
            this.userName = request.getParameter("user");
            this.password = request.getParameter("password");
            userName = request.getParameter("user");
            password = request.getParameter("password");

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
                    userdataList.add(new UserData(r.getString(1),
                            r.getString(2)));
                    System.out.println("读到数据");
                }
            }catch (SQLException E){
                E.printStackTrace();
            }
        }



        public String checkHadRegistered(){
            for(UserData u : userdataList){
                if(userName.equals(u.getUserName()) && password.equals(u.getPassword())){
                    return "登录成功";
                }
            }
            return "登录失败, 请检查用户名或密码是否正确";
        }
    }

    DoLogin d = new DoLogin();
    String message = d.checkHadRegistered();
%>

<script language="javascript">
    alert("<%=message%>");
    window.location.href='login02.jsp';
</script>


</html>
