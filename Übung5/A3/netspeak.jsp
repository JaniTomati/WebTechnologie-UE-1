<!DOCTYPE html>
<%@ page import="java.net.*"%>
<html>
  <head> 
  <title>NeedToSpeak</title> 
  </head>
  <body>
   <%String query = request.getParameter("query");%>
   <h3>Ask Netspeak</h3>
   <%if(query==null){
    query ="";%>
   <form action="netspeak.jsp" method="get">
      <input type = "text" name="query" value ="<%=query%>" placeholder="Type request + Press Enter + Press Search" size = "35">
      <input type="submit" value="Enter">
   </form>
   <%}else{
    String link_ = "http://api.netspeak.org/netspeak3/search?query=";
      query = URLEncoder.encode(query, "UTF-8");
      link_ = link_ +query+"%20%3F&topk=5";%>
      <a href=<%=link_%>>Search</a>
      <%}%>
  </body>
</html>
