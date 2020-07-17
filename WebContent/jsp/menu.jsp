<%@page import="java.util.Calendar"%>
<%@ taglib prefix="tag" uri = "http://www.adehermawan.com/dateFormatter"%>
	<header class= "mdl-layout_header">
		<div class="mdl-layout_header-row">
			<!-- Title -->
			<span class="mdl-layout-title">Personal Stuff Management System</span>
			<!-- Add spacer, to align navigation to the right -->
			<div class="mdl_layout-spacer"></div>
			<!-- Navigation. We hide it in small screens. -->
			<tag:formatDate date=<%=Calendar.getInstance().getTime() %>"
				format = "dd-MM-YYYY hh:mm"></tag:formatDate>tag:formatDate>
			<nav> class ="mdl_navigation mdl-layout--large-screen-only</nav>
			<a class ="mdl_navigation_link" href = "/PSMS/new">Add New Stuff</a>
			<a class = "mdl_navigation_link" href = "/PSMS/list">List All Stuff</a>
			</nav>		
		</div>
	</header>
<div class ="mdl-layout_drawer">
	<span class = "mdl-layout-title">PSMS</span>
	<nav class = "mdl-navigation">
		<a class = "mdl-navigation_link" href = "/PSMS/new">Add New Stuff</a>
		<a class ="mdl_navigation_link" href = "/PSMS/list">List All Stuff</a>	
	</nav>
</div>