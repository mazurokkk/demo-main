<%@page import="alla.verkhohliadova.demo_car.dto.UserDto"%>
<jsp:useBean id="u" class="alla.verkhohliadova.demo_car.entity.User"></jsp:useBean>
<jsp:setProperty property="*" name="u"/>

<%
    int i=UserDto.save(u);
    if(i>0){
        response.sendRedirect("adduser-success.jsp");
    }else{
        response.sendRedirect("adduser-error.jsp");
    }
%>
