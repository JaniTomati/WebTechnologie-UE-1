<%@page language="java" contentType="text/html" pageEncoding="UTF-8" import="java.net.*" import="java.util.Scanner" import="java.io.*" %>

<!DOCTYPE html>
<html lang="de">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"
    <title>Titel</title>
  </head>
  <body>
  	<% //hier kommt der Java code hin.
  		String queryString = request.getParameter("query");
  		if (queryString == null) {
  		 	queryString = "you";
  		 } 
  		URL u = new URL("http://api.netspeak.org/netspeak3/search?query="+queryString+"%3F&topk=5");

  		BufferedReader in = new BufferedReader(
        new InputStreamReader(u.openStream()));

        String inputLine;
        String[] keyword = new String[5];
        int i = 0;
        while ((inputLine = in.readLine()) != null){
        	String[] temp = inputLine.split("	+");
        	keyword[i] = temp[temp.length-1];
        	i++;
        	//out.println("<br>");
        	//out.println('<button formaction = "netspeak.jsp" name = "kotkacker" formmethod = "get" >' + keyword + '</button>');
        	//out.println(keyword + "<br>");
   		}
        

        in.close();
    %>

  	<button formaction = "netspeak.jsp" name = "kotkacker" formmethod = "get" > <%= keyword[0]%>  </button>
  	<button formaction = "netspeak.jsp" name = "kotkacker1" formmethod = "get" > <%= keyword[1]%>  </button>
  	<button formaction = "netspeak.jsp" name = "kotkacker2" formmethod = "get" > <%= keyword[2]%>  </button>
  	<button formaction = "netspeak.jsp" name = "kotkacker3" formmethod = "get" > <%= keyword[3]%>  </button>
  	<button formaction = "netspeak.jsp" name = "kotkacker4" formmethod = "get" > <%= keyword[4]%>  </button>

  </body>
</html>