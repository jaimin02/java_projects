<%@ taglib prefix="s" uri="/struts-tags"%>





<br />
<div align="center" style="color: green; font-weight: bold;"><s:actionmessage />
</div>


<div align="center" style="color: red; font-weight: bold;"><s:actionerror />
<s:fielderror></s:fielderror></div>
<br />
<br />

<div id="ShowSubInfoDiv"></div>


<br>
<div id="upload_form"><s:form action="UploadedFile"
	name="SeqUploadForm" method="post" enctype="multipart/form-data">
	<div id="outerUploadDiv" align="center"><input type="button"
		class="button" value="Back" onclick="javascript:history.go(0);">
	<br></br>
	<div class="" align="center">
				<span style="color:#c00000">Make sure you have uploaded previous sequence inside <b><s:property value="manualPrevSeq" /></b></span>
	</div>
	<!-- <div id="uploadDiv" class="title"
		style="height: 300px; overflow-y: auto;"><br />
	<span>Upload Sequence 0000: </span><input type="file" name="upload"
		id="upload_0000" size="50"></div>
	<div><br />
	<a href="javascript:void(0);" id="addSeqLink"
		onclick="createFileTag('uploadDiv');" style="color: blue;">Add
	0001</a> &nbsp;&nbsp; <a href="javascript:void(0);" id="removeSeqLink"
		onclick="removeFileTag('uploadDiv');"
		style="display: none; color: blue;">Remove 0001</a></div>
	<input type="hidden" name="startSequence" id="startSequence"
		value='0000'>--></div>
	
	<s:hidden id = "ws_id" name="workspaceId" value="%{workspaceId}"></s:hidden>
	<!--<s:submit name="submitBtn" cssClass="button" value="Upload"
		onclick="return validateFiles();"></s:submit>
	<!--<s:submit name="submitBtn" cssClass="button" value="Upload" theme="ajax" cssStyle="visibility: hidden;" targets="mDiv"></s:submit>
	<input type="button" class="button" value="Upload" onclick="uploadSeq('mDiv');">-->
	</s:form>
</div>		

		

