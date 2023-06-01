// variables
var dropArea = document.getElementById('dropArea');
var canvas = document.querySelector('canvas');
var context = canvas.getContext('2d');
//var count = document.getElementById('count');
var destinationUrl ="SaveNodeAttrAction.do";
var result = document.getElementById('result');
var list = [];
var totalSize = 0;
var totalProgress = 0;


// main initialization
(function(){

    // init handlers
    function initHandlers() {
        dropArea.addEventListener('drop', handleDrop, false);
        dropArea.addEventListener('dragover', handleDragOver, false);
        
       // alert("init");
    }

    // draw progress
    function drawProgress(progress) {
//        context.clearRect(0, 0, canvas.width, canvas.height); // clear context
//
//        context.beginPath();
//        context.strokeStyle = '#4B9500';
//        context.fillStyle = '#4B9500';
//        context.fillRect(0, 0, progress * 500, 20);
//        context.closePath();
//
//        // draw progress (as text)
//        context.font = '16px Verdana';
//        context.fillStyle = '#000';
//        context.fillText('Progress: ' + Math.floor(progress*100) + '%', 50, 15);
    }

    // drag over
    function handleDragOver(event) {
        event.stopPropagation();
        event.preventDefault();

        dropArea.className = 'hover';
    }

    // drag drop
    function handleDrop(event) {
    	
    	//alert($(this).html());
        event.stopPropagation();
        event.preventDefault();

        processFiles(event.dataTransfer.files);
    }

    // process bunch of files
    function processFiles(filelist) {
        if (!filelist || !filelist.length || list.length) return;

        totalSize = 0;
        totalProgress = 0;
        result.textContent = '';

        for (var i = 0; i < filelist.length && i < 5; i++) {
            list.push(filelist[i]);
            totalSize += filelist[i].size;
        }
        uploadNext();
    }

    // on complete - start next file
    function handleComplete(size) {
    	
    	result.innerHTML="File Uploaded Successfully";
    	$("#ui-dynatree-id-"+gNodeId).addClass("pdf");
        totalProgress += size;
        drawProgress(totalProgress / totalSize);
        uploadNext();
    }

    // update progress
    function handleProgress(event) {
        var progress = totalProgress + event.loaded;
        drawProgress(progress / totalSize);
    }

    // upload file
    function uploadFile(file, status) {

    	
        // prepare XMLHttpRequest
        var xhr = new XMLHttpRequest();
        xhr.open('POST', destinationUrl);
        xhr.onload = function() {
            result.innerHTML += this.responseText;
            handleComplete(file.size);
        };
        xhr.onerror = function() {
        	
            result.textContent = this.responseText;
            handleComplete(file.size);
        };
        xhr.upload.onprogress = function(event) {
            handleProgress(event);
        };
        xhr.upload.onloadstart = function(event) {
        };

       // alert($("#"+gNodeId).text());
        fileName=$("#"+gNodeId).text();
        var operation=$("#dragOperation").val(); // new.replace,append
        // prepare FormData
        var formData = new FormData();  
        formData.append('uploadFile', file); // This name should be there in action
        formData.append('username',"abc");
        formData.append('nodeId',gNodeId);
        formData.append('keyword',"");
        formData.append('description',"");
        formData.append('versionPrefix',"A");
        formData.append('versionSeperator',"-");
        formData.append('versionSuffix',"1");
        formData.append('attrCount',"3");
        formData.append('isReplaceFileName',"N");
        formData.append('nodeFolderName',fileName);
        formData.append('deleteFile',"N");
        formData.append('remark',"");
        formData.append('userSourceFile',"N");
        formData.append('isReplaceFileName',"Y");
        
        formData.append('Drag',"Y");
        
        
        formData.append('attrValueId1',"PDF 1.4");  //Application version
        formData.append('attrType1',"Text");  //Application version
        formData.append('attrName1',"application-version");
        formData.append('attrId1',"2");
       // alert(operation);
        formData.append('attrValueId2',operation);  //Application version
        formData.append('attrType2',"Combo");  //Application version
        formData.append('attrName2',"operation");
        formData.append('attrId2',"5");
        
        
        formData.append('attrValueId3',"Electoronic Submission");  //Application version
        formData.append('attrType3',"Combo");  //Application version
        formData.append('attrName3',"Submission Type");
        formData.append('attrId3',"24");
        
        // just need to pass attrName and attrType
       // formData.append('attrValueId2',"new"); // operation type
       // formData.append('attrValueId3',"Electronic Submission"); // Submission type
        
        
        xhr.send(formData);
    }

    // upload next file
    function uploadNext() {
        if (list.length) {
           // count.textContent = list.length - 1;
            dropArea.className = 'uploading';

            var nextFile = list.shift();
            if (nextFile.size >= 99262144) { // 256kb=262144
                result.innerHTML += '<div class="f">Too big file (max filesize exceeded)</div>';
                handleComplete(nextFile.size);
            } else {
                uploadFile(nextFile, status);
            }
        } else {
            dropArea.className = '';
        }
    }
    initHandlers();
})();