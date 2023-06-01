<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>




<body>
 
<!--  <table style = "margin-top: 55px;">
	
		
		<tr align="left">
					<td class="title" id="profileTD" style="padding: 2px; padding-right: 8px;">Profile</td>
			<td>
				<div id="showProfile">
					<select name="profileData" id="profileData" >
  						<option value="-1">Select Profile</option>
					</select>
				</div> 
			</td>
		 </tr> 
		 <tr>
		 	<td>
		 	</td>
		 </tr>
		 <tr>
		 	
			<td colspan="2" align="center">
				<submit name="submitBtn" id="profileData" value="Submit" cssClass="button" indicator="indicator" onclick="return validate();" />
				<input type="button" name="submitBtn" id="profileData" value="Submit" class="button" onclick="return validate();" />

			</td>
		</tr>
</table> -->

<div style="  font: 15px 'Exo 2', sans-serif; position: fixed;
  top: 35%; left: 43%;"><b style = "font-size:19px;">Please Select Profile To login</b></div>
<div id="container" style="position: fixed;top: 49%;left: 50%;margin-top: -50px; margin-left: -100px;"></div>
    <div>
    	
    	<input type="button" name="profileData" id="container" value="Submit" class="button"  style="position: fixed;
  top: 50%; left: 51%;" onclick="return validate();" />
    <!-- <button id="submit" style="margin-top: 10px; padding: 2 2; font: 15px 'Exo 2', sans-serif;">Please Select Profiles</button> -->
    </div>

<script type="text/javascript">

window.onload = function(){  
	debugger;
	var isAdUser = document.getElementById("isAdUser").value;
	//var adUserName = document.getElementById("adUserName").value;
	var UserName = document.getElementById('username').value;
	if(UserName != "" && isAdUser != ""){
		var Value="Select Profile";
		//alert("You selected: " + UserName);
		//document.getElementById("siteDataByCountryTD").style.display = "block";
		
		$.ajax({			
			url: 'GetProfileNames_ex.do?loginUserName=' + UserName,
					
			beforeSend: function()
			{
				$('#showProfile').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
			},
			success: function(data) 
	  		{
				//debugger;
				//$('#showUserDtl').html("");
				/* var opt;		
				if (data=="false"){
					alert("You don't have AD User rights. Please login with correct Username and Password");
					document.getElementById("profileTD").style.display = "block";
	    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
	    			document.getElementById('showProfile').innerHTML = opt;
	    			var out="Logout.do";
	    			location.href=out;
				}
				else if (data=="true"){
					alert("You have AD User rights. Please login with correct AD Username and Password");
					document.getElementById("profileTD").style.display = "block";
	    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
	    			document.getElementById('showProfile').innerHTML = opt;
	    			var out="Logout.do";
	    			location.href=out;
				}
				else if (data=="NotFound"){
					alert("Invalid Username. Please login with correct Username");
					document.getElementById("profileTD").style.display = "block";
	    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
	    			document.getElementById('showProfile').innerHTML = opt;
	    			var out="Logout.do";
	    			location.href=out;
				} */
				if(data.length > 0)
		    	{
	    			debugger;
	    			//alert(data);
	    			//document.getElementById("profileTD").style.display = "block";
	    			var appendkey=[];
	    			var usersArr = data.split('&');
	    			 opt = "";	
	    			 
	    			
	    			for(i=0; i<usersArr.length;i++)
		    		{
	    				var keyValuePair = usersArr[i];
	    				var keyValuePairArr = keyValuePair.split('::');
	    				appendkey.push(keyValuePairArr)
		    		}	
	    			
	    			
	    				/* $(document).ready(function() {
	    					  $('#submit').click(function() {

    						  for(i=0; i<appendkey.length;i++)
    			    			{	
    							  	var key=[]; 
    				    			var value=[];
    			    				key.push(appendkey[i][0]);
    			    				value.push(appendkey[i][1]);
    			    				$('#container').append(
    		    					        $('<input>').prop({
    		    					          type: 'radio',
    		    					          id: key,
    		    					          name: 'contact',
    		    					          value: value
    		    					        })
    		    					      ).append(
    		    					        $('<label>').prop({
    		    					          for: value
    		    					        }).html(value)
    		    					      )
    			    			}
	    					    
	    					  })
	    					}); */
	    					$(document).ready(function() {
	    						 
	    							  for(i=0; i<appendkey.length;i++)
	      			    				{	
	      							  	var key=[]; 
	      				    			var value=[];
	      			    				key.push(appendkey[i][0]);
	      			    				value.push(appendkey[i][1]);
	    						      $('#container')
	    						        .append('<input type="radio" id='+key+' name="profileData" value='+key+' style="margin: 6px;">')
	    						        .append('<label style="font: 15px Exo 2 sans-serif;" for='+key+'>'+value+'</label></div>')
	    						        .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
	    						    }
	    						  
	    						});


	    			
	    			
	    			
 	    				/*opt += "<input type=radio id="+key+" name=fav_language >";
	    				opt += "<label for='"+key+"'>"+value+"</label><br>"; 
	    				opt += '<label for=" "+key+" ">"+value+"</label><br>' */ 
	    				  				    				
	    		
	    			
	    			/*  opt = '<select name="profileData" id = "profileData" style="width:100%; padding: 1px 22px 1px 1px; font-size: 16px;" >'+
	    					opt +
							  '</select>';  */
	    			/* document.getElementById('showProfile').innerHTML = opt;
	    			document.getElementById("profileTD").innerHTML = "Select Profile"; */ 
	    				    				
				}	
	    		else{
	    			alert(data);
	    			document.getElementById("profileTD").style.display = "block";
	    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
	    			//opt = 'No Profile found';
	    			document.getElementById('showProfile').innerHTML = opt;
	    			//document.getElementById("showProfile").style.marginLeft = "74px";
	    		}
	  		}
		});
		
	}
	else{
		return false;
	}
	
}

 function validate(){
	debugger;
	var UsergrpCode = $("input[name='profileData']:checked").val();
	if(UsergrpCode==null){
		alert("PLease Select User Profile.");
		return false;
		}
	else{
		$.ajax({			
			url: 'GetSelectedProfile_ex.do?userGroupCode=' + UsergrpCode,
					
			beforeSend: function()
			{
				$('#showProfile').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
			},
			success : function(data) {
				if(data=="true"){
					
					
					var out='pwdExpire';
					location.href=out;
				
				}
				else{
					
					var out='Welcome.do';
					location.href=out;
					
					
				}			
			},
			error: function(data) 
			  {
				alert("Something went wrong while Removing rights.");
			  },
		});
	}  
}
</script>

<input type="hidden" value='${username}' name="username" id="username" style="display: none;">
<input type="hidden" value='${isAdUser}' name="isAdUser" id="isAdUser" style="display: none;">
			
</body>
</html>