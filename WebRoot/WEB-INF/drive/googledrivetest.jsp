<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  
 
<script
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"
	type="text/javascript"></script> 
	
	<link
	href='<%=request.getContextPath() %>/js/jquery/jquery-ui-1.8.0.min.css'
	rel='stylesheet' type='text/css' />

<script
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'
	type='text/javascript'></script>
	
  
<script src="<%=request.getContextPath()%>/js/newtree/jquery.cookie.js"
	type="text/javascript"></script>
<link
	href="<%=request.getContextPath()%>/js/newtree/skin/ui.dynatree.css"
	rel="stylesheet" type="text/css">
	<style>
	.column-left{ float: left; width: 33%; }
.column-right{ float: right; width: 33%; }
.column-center{ display: inline-block; width: 33%; }
	
	</style>

<script
	src="<%=request.getContextPath()%>/js/newtree/jquery.dynatree.js"
	type="text/javascript"></script>

<script type="text/javascript">



    // The Browser API key obtained from the Google Developers Console.
    // Replace with your own Browser API key, or your own key.
    var developerKey = 'AIzaSyB59qcEwKN_MIWi8pi5r-HJ4QGvDEzGE-A';

    // The Client ID obtained from the Google Developers Console. Replace with your own Client ID.
    var clientId = "273703485680-qvqljc7d35f9olbi1m8fj3luv5pcj8jq.apps.googleusercontent.com"

    // Replace with your own App ID. (Its the first number in your Client ID)
    var appId = "273703485680";

    // Scope to use to access user's Drive items.
    var scope = ['https://www.googleapis.com/auth/drive'];

    var pickerApiLoaded = false;
    var oauthToken;
    var picker;

    // Use the Google API Loader script to load the google.picker script.
    function loadPicker() {
      gapi.load('auth', {'callback': onAuthApiLoad});
      gapi.load('picker', {'callback': onPickerApiLoad});
    }

    function onAuthApiLoad() {
      window.gapi.auth.authorize(
          {
            'client_id': clientId,
            'scope': scope,
            'immediate': false
          },
          handleAuthResult);
    }

    function onPickerApiLoad() {
      pickerApiLoaded = true;
      createPicker();
    }

    function handleAuthResult(authResult) {
      if (authResult && !authResult.error) {
        oauthToken = authResult.access_token;
        createPicker();
      }
    }

    // Create and render a Picker object for searching images.
    function createPicker() {
      if (pickerApiLoaded && oauthToken) {
        var view = new google.picker.View(google.picker.ViewId.DOCUMENTS);
        view.setMimeTypes("*.*");
        picker = new google.picker.PickerBuilder()
            .enableFeature(google.picker.Feature.NAV_HIDDEN)
            .enableFeature(google.picker.Feature.MULTISELECT_ENABLED)
            .setAppId(appId)
            .setOAuthToken(oauthToken)
            .addView(view)
            .addView(new google.picker.DocsUploadView())
            .setDeveloperKey(developerKey)
            .setCallback(pickerCallback)
            .build();
         picker.setVisible(true);
      }
    }

    // A simple callback implementation.
    function pickerCallback(data) {
        
      if (data.action == google.picker.Action.PICKED) {
        var fileId = data.docs[0].id;
        var desc=data.docs[0].description;
        var fileName=data.docs[0].name;
        var parentId=data.docs[0].parentId;
        var parentId=data.docs[0].type;


        picker.setVisible(false);
        
        var fileMetaData="Description :"+desc + " , FileName :"+fileName;
        //debugger;
        alert('The user selected: ' + fileId); 	

        $("#result").text(fileMetaData);
   
      }
    }
    </script>
  </head>
  <body>
  
  	<input type="button" value="Open Picker" onclick="loadPicker();">
    
    
    
   
    
   

<div class="container">
   <div class="column-left">
   
	<div id="tree" style="width:200px;">
		<ul>

			<li id="id3" class="folder">Standard Folder 
				<ul>
					<li id="id3.1">Sub-item 3.1
					<li id="id3.2">Sub-item 3.2
				</ul>

			<li id="id4" class="folder">item 4.
				<ul>
					<li id="id4.1">Sub-item 4.1
					<li id="id4.2">Sub-item 4.2
					<li id="id4.3" class="folder">Sub-item 4.3
						<ul>
							<li id="id4.3.1">Sub-item 4.3.1
							<li id="id4.3.2" class="folder">Sub-item 4.3.2
							<ul>
								<li id="id4.3.2.1">Sub-item 4.3.2.1
								<li id="id4.3.2.2">Sub-item 4.3.2.2
							</ul>
						</ul>
					<li id="id4.4">Sub-item 4.4
				</ul>

			<li id="id5" class="expanded folder">Advanced examples
				<ul>
					<li data="key: 'node5.1'">item5.1: 
					
				</ul>
		</ul>
	</div>
   </div>
   <div class="column-center">
    <div id="googleTree">
    	<s:property value="googleDriveTree"/>
    </div>
   </div>
   <div class="column-right">
   
     <div id="result"></div>
   </div>
</div>
     
<script type="text/javascript">
	$(function(){
		// Initialize the tree inside the <div>element.
		// The tree structure is read from the contained <ul> tag.
		$("#tree").dynatree({
			title: "Sample",
			onActivate: function(node) {
				//debugger;
			if(!node.data.isFolder)
				loadPicker();
				
				
			},
			onDeactivate: function(node) {
				
			},
			onFocus: function(node) {
				
			},
			onBlur: function(node) {
				
			}
		});
	
		//debugger;
		$('#googleTree').html($("#googleTree").text());
		$("#googleTree").dynatree({
			title: "Sample",
			onActivate: function(node) {
				//debugger;
			//if(!node.data.isFolder){
				//loadPicker();
				var path="";
				var n=node;
				while(true)
				{
					if(n.getParent().parent==null)
					{
						break;
					}
					path=n.getParent().data.title+"/"+path;
					n=n.getParent();
				}	
				var str="localexplorer:C:/Users/Administrator/Google Drive/"+path+node.data.title;
				//var str =path+"/"+dtnode.data.href.replace("file:", "localexplorer:");

				window.open(str,"_self");
				
				
			},
			onDeactivate: function(node) {

				
			},
			onFocus: function(node) {
				
			},
			onBlur: function(node) {
				
			}
		});
		


		
		
	});
</script>
    <a href="C:\Users\Administrator\Google Drive\Sample PDF.pdf">Open File</a>

    <!-- The Google API Loader script. -->
    <script type="text/javascript" src="https://apis.google.com/js/api.js"></script>
  </body>
</html>