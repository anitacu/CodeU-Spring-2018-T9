<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>

<% HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("map");%>

<html>
<head>
  <title>Admin page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <%@include file= "navbar.jsp"%>

  <div id="container">
    <h1>Admin Page</h1>

      <% for (HashMap.Entry<String, String> entry : map.entrySet()) { %>
        <p> <b> <%= entry.getKey() %> </b> <%= entry.getValue() %> </p>
      <% } %>

    <p>New features coming soon...</p>

    <form action="/admin" method="POST">
        <p> <b>Make someone admin:<b> </p>
        <input type="text" name="toBeAdminUser" value="" placeholder="username" >
        <br/>
        <button type="submit">Submit</button>
    </form>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } else if(request.getAttribute("success") != null){%>
        <h2 style="color:green"><%= request.getAttribute("success") %></h2>
    <% } %>

  </div>
</body>
</html>
