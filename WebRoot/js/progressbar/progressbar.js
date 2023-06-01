/*jslint white: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: false, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, maxerr: 14 */
/*global window: false, ActiveXObject: false*/

/*
The onreadystatechange property is a function that receives the feedback. It is important to note that the feedback
function must be assigned before each send, because upon request completion the onreadystatechange property is reset.
This is evident in the Mozilla and Firefox source.
*/

/* enable strict mode */
"use strict";

// global variables
var progress,			// progress element reference
	request,			// request object
	intervalID = false;	// interval ID

// define reference to the progress bar and create XMLHttp request object
window.onload = function () {
	progress = document.getElementById('progress');
	request = initXMLHttpClient();
}

// create XMLHttp request object in a cross-browser manner
function initXMLHttpClient() {
	var XMLHTTP_IDS,
		xmlhttp,
		success = false,
		i;
	// Mozilla/Chrome/Safari/IE7/IE8 (normal browsers)
	try {
		xmlhttp = new XMLHttpRequest(); 
	}
	// IE(?!)
	catch (e1) {
		XMLHTTP_IDS = [ 'MSXML2.XMLHTTP.5.0', 'MSXML2.XMLHTTP.4.0',
						'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP' ];
		for (i = 0; i < XMLHTTP_IDS.length && !success; i++) {
			try {
				success = true;
				xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
			}
			catch (e2) {}
		}
		if (!success) {
			throw new Error('Unable to create XMLHttpRequest!');
		}
	}
	return xmlhttp;
}

// send request to the server
function send_request() {
	request.open('GET', 'ajax-progress-bar.php', true);	// open asynchronus request
	request.onreadystatechange = request_handler;		// set request handler	
	request.send(null);									// send request
}

// request handler (defined in send_request)
function request_handler() {
	var level;
	if (request.readyState === 4) { // if state = 4 (operation is completed)
		if (request.status === 200) { // and the HTTP status is OK
			// get progress from the XML node and set progress bar width and innerHTML
			level = request.responseXML.getElementsByTagName('PROGRESS')[0].firstChild;
			progress.style.width = progress.innerHTML = level.nodeValue + '%';
		}
		else { // if request status isn't OK
			progress.style.width = '100%';
			progress.innerHTML = 'Error: [' + request.status + '] ' + request.statusText;
		}        
	}
}

// button actions (start / stop)
function polling_start() {
	if (!intervalID) {
		intervalID = window.setInterval('send_request()', 1000);
	}
}
function polling_stop() {
	window.clearInterval(intervalID);
	intervalID = false;
}
