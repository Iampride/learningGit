<%@ page import="java.security.PublicKey" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.*" %>
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
    <title>处理用户登录界面</title>
</head>
<body>
</body>
<%
    class DoLogin{
        private String userName;
        private String password;
        private ArrayList<UserData> userdataList = new ArrayList<>();

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
        }

        public void readUserData(){
            try {
                String path = "/home/nc/Program/Java/task/Experiment4/doc/user.txt";
                BufferedReader userDataRead = new BufferedReader(new FileReader(path));
                String line = null;
                while ((line = userDataRead.readLine()) != null) {
                    String[] s = line.split(",");
                    userdataList.add(new UserData(s[0], s[1]));
                }
                userDataRead.close();
            }catch (IOException E){
                E.printStackTrace();
            }
        }


        public String checkHadRegistered(){
            readUserData();
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
    window.location.href='login.jsp';
</script>


</html>
