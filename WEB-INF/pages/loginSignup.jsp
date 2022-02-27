<%-- 
    Document   : loginSignupBody
    Created on : 14-Jun-2010, 10:26:19
    Author     : Reality Software | www.realitysoftware.ca
    Released by: Free Web Design Community | www.designity.org
    Note       : This is a free template released under the Creative Commons Attribution 3.0 license,
                 which means you can use it in any way you want provided you keep links to authors intact.
                 Don't want our links in template? You can pay a link removal fee: www.realitysoftware.ca/templates/
                 You can also purchase a PSD-file for this template.
--%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="resources/css/wsportal.css" />
    <title>WS Portal Login</title>
</head>
<body>
    <div id="container">
        <!-- main -->
        <div id="main">
            <div id="logo">
                <table>
                    <tr><td><h1>Login</h1></td><td><img src="resources/images/web-services.png" alt="ws-portal logo" width="50" height="50"/></td></tr>
                </table>
                <form action="login" method="post">
                    <table>
                        <tr><td>Username:</td><td><input id="usernameLogin" name="username" type="text" /></td></tr>
                        <tr><td>Password:</td><td><input id="passwordLogin" name="password" type="password" /></td></tr>
                    </table>
                    <table>
                        <tr><td><input type="submit" value="login" /></td></tr>
                    </table>
                </form>
            </div><!-- end logo -->

            <div id="text">
                <h1>Register:</h1>
                <form action="signup" method="post">
                    <table>
                        <tr><td>Username:</td><td><input id="usernameSignup" name="username" type="text" /></td></tr>
                        <tr><td>Password:</td><td><input id="passwordReg" name="password" type="password" /></td></tr>
                        <tr><td>First Name:</td><td><input id="name" name="name" type="text" /></td></tr>
                        <tr><td>Last Name:</td><td><input id="surname" name="surname" type="text" /></td></tr>
                    </table>
                    <table>
                        <tr><td><input type="submit" value="sign-up" /></td></tr>
                    </table>
                </form>
            </div><!-- end text-->
            <%
                        String error = (String) request.getAttribute("x");
                        if (error != null && error.equals("1")) {
            %>
            <div id="text">Invalid user, try again.</div><!-- end text-->
            <%                            } else if (error != null && error.equals("0")) {
            %>
            <div id="text">Registration failure.</div><!-- end text-->
            <%                            }
            %>
        </div><!-- end main -->

        <!-- footer -->
        <div id="footer">
            <div id="left_footer">&copy; Copyright 2010 WS-Portal: Powered by Carmine Dodaro e Simone Spaccarotella</div>
        </div><!-- end footer -->
    </div><!-- end container -->
</body>