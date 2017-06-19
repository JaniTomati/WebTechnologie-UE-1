<!DOCTYPE html>
<%@ page import="java.net.*"%>
<html>
  <head> 
  <title>NeedToSpeak</title> 
  </head>
  <body>
   <%String query = request.getParameter("query");%>
   <h3>Ask Netspeak</h3>
   <form action="netspeak.jsp" method="get">
      <input type = "text" name="query" placeholder=" ?" size = "30">
      <input type="submit" value="Enter">
   </form>
   <%String link_ = "http://api.netspeak.org/netspeak3/search?query=";%>
   <%if ( query != null ){
      String req = URLEncoder.encode(query, "UTF-8");
      String result = link_ +req+"%20%3F&topk=5";%>
      <a href=<%=result%>>Search</a>
      <%}%>
  </body>
</html>
