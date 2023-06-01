<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>

<style type="text/css">

input[type="checkbox"][readonly] {
  pointer-events: none;
 }
#usertable_filter input {
background-color: #2e7eb9;
color:#ffffff;
}
#usertable_length select {
background-color: #2e7eb9;
color:#ffffff;
}


#popupContact {
top:-35px !important;
width: 570px !important;
}
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgb(0 0 0 / 73%); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  /*padding: 20px;*/
  border: 1px solid #888;
  width: 80%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
@font-face {
	    font-family: 'password';
	    font-style: normal;
	    font-weight: 400;
	   /*  src: url('images/password.ttf'); */
		src: url(data:font/woff;charset:utf-8;base64,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) format("woff");

	   
	}          
	#reConfPass {
            font-family: 'password';
        }
        
dojoLayoutContainer{ position: relative; display: block; }
body .dojoAlignTop, body .dojoAlignBottom, body .dojoAlignLeft, body .dojoAlignRight { position: absolute; overflow: hidden; }
body .dojoAlignClient { position: absolute }
.dojoAlignClient { overflow: auto; }
.dojoTabContainer {
	position : relative;
}

.dojoTabPaneWrapper {
	border : 1px solid #6290d2;
	_zoom: 1; /* force IE6 layout mode so top border doesnt disappear */
	display: block;
	clear: both;
}

.dojoTabLabels-top {
	position : relative;
	top : 0px;
	left : 0px;
	overflow : visible;
	margin-bottom : -1px;
	width : 100%;
	z-index: 2;	/* so the bottom of the tab label will cover up the border of dojoTabPaneWrapper */
}

.dojoTabNoLayout.dojoTabLabels-top .dojoTab {
	margin-bottom: -1px;
	_margin-bottom: 0px; /* IE filter so top border lines up correctly */
}

.dojoTab {
	position : relative;
	float : left;
	padding-left : 9px;
	border-bottom : 1px solid #6290d2;
	background : url(<%=request.getContextPath()%>/images/tab_left.gif) no-repeat left top;
	cursor: pointer;
	white-space: nowrap;
	z-index: 3;
}

.dojoTab div {
	display : block;
	padding : 4px 15px 4px 6px;
	background : url(<%=request.getContextPath()%>/images/tab_top_right.gif) no-repeat right top;
	color : #333;
	font-size : 90%;
}

.dojoTab .close {
	display : inline-block;
	height : 12px;
	width : 12px;
	padding : 0 12px 0 0;
	margin : 0 -10px 0 10px;
	cursor : default;
	font-size: small;
}

.dojoTab .closeImage {
	background : url(<%=request.getContextPath()%>/images/tab_close.gif) no-repeat right top;
}

.dojoTab .closeHover {
	background : url(<%=request.getContextPath()%>/images/tab_close_h.gif);
}

.dojoTab.current {
	padding-bottom : 1px;
	border-bottom : 0;
	background-position : 0 -150px;
}

.dojoTab.current div {
	padding-bottom : 5px;
	margin-bottom : -1px;
	background-position : 100% -150px;
}

/* bottom tabs */

.dojoTabLabels-bottom {
	position : relative;
	bottom : 0px;
	left : 0px;
	overflow : visible;
	margin-top : -1px;
	width : 100%;
	z-index: 2;
}

.dojoTabNoLayout.dojoTabLabels-bottom {
	position : relative;
}

.dojoTabLabels-bottom .dojoTab {
	border-top :  1px solid #6290d2;
	border-bottom : 0;
	background : url(<%=request.getContextPath()%>/images/tab_bot_left.gif) no-repeat left bottom;
}

.dojoTabLabels-bottom .dojoTab div {
	background : url(<%=request.getContextPath()%>/images/tab_bot_right.gif) no-repeat right bottom;
}

.dojoTabLabels-bottom .dojoTab.current {
	border-top : 0;
	background : url(<%=request.getContextPath()%>/images/tab_bot_left_curr.gif) no-repeat left bottom;
}

.dojoTabLabels-bottom .dojoTab.current div {
	padding-top : 4px;
	background : url(<%=request.getContextPath()%>images/tab_bot_right_curr.gif) no-repeat right bottom;
}

/* right-h tabs */

.dojoTabLabels-right-h {
	overflow : visible;
	margin-left : -1px;
	z-index: 2;
}

.dojoTabLabels-right-h .dojoTab {
	padding-left : 0;
	border-left :  1px solid #6290d2;
	border-bottom : 0;
	background : url(<%=request.getContextPath()%>images/tab_bot_right.gif) no-repeat right bottom;
	float : none;
}

.dojoTabLabels-right-h .dojoTab div {
	padding : 4px 15px 4px 15px;
}

.dojoTabLabels-right-h .dojoTab.current {
	border-left :  0;
	border-bottom :  1px solid #6290d2;
}

/* left-h tabs */

.dojoTabLabels-left-h {
	overflow : visible;
	margin-right : -1px;
	z-index: 2;
}

.dojoTabLabels-left-h .dojoTab {
	border-right :  1px solid #6290d2;
	border-bottom : 0;
	float : none;
	background : url(<%=request.getContextPath()%>images/tab_top_left.gif) no-repeat left top;
}

.dojoTabLabels-left-h .dojoTab.current {
	border-right : 0;
	border-bottom :  1px solid #6290d2;
	padding-bottom : 0;
	background : url(<%=request.getContextPath()%>/images/tab_top_left.gif) no-repeat 0 -150px;
}

.dojoTabLabels-left-h .dojoTab div {
	background : 0;
	border-bottom :  1px solid #6290d2;
}

</style>

<s:head theme="ajax" />
 <script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
 <script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>

 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>
<script>
   var $j = jQuery.noConflict(true);
</script> 
<link href="<%=request.getContextPath()%>/css/fSelect.css" rel="stylesheet" type="text/css" media="screen" />
	
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>  --%>


<s:head />

<script type="text/javascript">
//var userType;

	$(document).ready(function() { 
		//debugger;
		
		$("#remark").val("");
		 /* $('#usertable').dataTable( {
				
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
				
		}); */
		
		$('.target').change(function(){
			//debugger;	
			var correct=true;
			var docName=document.getElementById('docName').value;
			docName = docName.trim();
			//docName=document.getElementById('docName').value; 
			//debugger;
			var urlOfAction
			var WorkspaceId = '<s:property value="WorkspaceId"/>';
			if(docName==""){
				$('#message').html("");
				alert("Please specify Document.")
				return false;
			}else{
			urlOfAction="DocNameExistsForUpdate_ex.do";
			}
			var dataofAction="docName="+docName+"&WorkspaceId="+WorkspaceId;
			if (correct==true)
			{
				$.ajax({
					type: "GET", 
	   				url: urlOfAction, 
	   				data: dataofAction, 
	   				cache: false,
	   				dataType:'text',
					success: function(response){
						$('#message').html(response);
						if(response.indexOf('available') == -1){
							document.getElementById('Save').disabled='disabled';
							return false;
						}
						else if(response.indexOf('available') != -1)
						{
							document.getElementById('Save').disabled='';
							return true;
						}
					}
				});
			}
		});
		
		
		
		
	
	});
	$j(document).ready(function() { 
		
		/* fSelect 1.0.1 - https://github.com/mgibbs189/fselect */

		

		    String.prototype.unaccented = function() {
		        var accent = [
		            /[\300-\306]/g, /[\340-\346]/g, // A, a
		            /[\310-\313]/g, /[\350-\353]/g, // E, e
		            /[\314-\317]/g, /[\354-\357]/g, // I, i
		            /[\322-\330]/g, /[\362-\370]/g, // O, o
		            /[\331-\334]/g, /[\371-\374]/g, // U, u
		            /[\321]/g, /[\361]/g, // N, n
		            /[\307]/g, /[\347]/g, // C, c
		        ];
		        var noaccent = ['A','a','E','e','I','i','O','o','U','u','N','n','C','c'];

		        var str = this;
		        for (var i = 0; i < accent.length; i++) {
		            str = str.replace(accent[i], noaccent[i]);
		        }

		        return str;
		    }

		    $j.fn.fSelect = function(options) {

		        if ('string' === typeof options) {
		            var settings = options;
		        }
		        else {
		            var settings = $j.extend({
		                placeholder: 'Select User',
		                numDisplayed: 3,
		                overflowText: '{n} selected',
		                searchText: 'Search',
		                noResultsText: 'No results found',
		                showSearch: true,
		                optionFormatter: false
		            }, options);
		        }


		        /**
		         * Constructor
		         */
		        function fSelect(select, settings) {
		            this.$jselect = $j(select);
		            this.settings = settings;
		            this.create();
		        }


		        /**
		         * Prototype class
		         */
		        fSelect.prototype = {
		            create: function() {
		                this.idx = 0;
		                this.optgroup = 0;
		                this.selected = [].concat(this.$jselect.val()); // force an array
		                this.settings.multiple = this.$jselect.is('[multiple]');

		                var search_html = '';
		                var no_results_html = '';
		                var choices_html = this.buildOptions(this.$jselect);

		                if (this.settings.showSearch) {
		                    search_html = '<div class="fs-search"><input type="text" placeholder="' + this.settings.searchText + '" /></div>';
		                }
		                if ('' !== this.settings.noResultsText) {
		                    no_results_html = '<div class="fs-no-results hidden">' + this.settings.noResultsText + '</div>';
		                }

		                var html = '<div class="fs-label-wrap"><div class="fs-label"></div><span class="fs-arrow"></span></div>';
		                html += '<div class="fs-dropdown hidden">{search}{no-results}<div class="fs-options">' + choices_html + '</div></div>';
		                html = html.replace('{search}', search_html);
		                html = html.replace('{no-results}', no_results_html);

		                this.$jselect.wrap('<div class="fs-wrap' + (this.settings.multiple ? ' multiple' : '') + '" tabindex="0" />');
		                this.$jselect.addClass('hidden');
		                this.$jselect.before(html);
		                this.$jwrap = this.$jselect.closest('.fs-wrap');
		                this.$jwrap.data('id', window.fSelect.num_items);
		                window.fSelect.num_items++;

		                this.reloadDropdownLabel();
		            },

		            reload: function() {
		                this.destroy();
		                this.create();
		            },

		            destroy: function() {
		                this.$jwrap.find('.fs-label-wrap').remove();
		                this.$jwrap.find('.fs-dropdown').remove();
		                this.$jselect.unwrap().removeClass('hidden');
		            },

		            buildOptions: function($jelement) {
		                var $jthis = this;

		                var choices = '';
		                $jelement.children().each(function(i, el) {
		                    var $jel = $j(el);

		                    if ('optgroup' == $jel.prop('nodeName').toLowerCase()) {
		                        choices += '<div class="fs-optgroup-label" data-group="' + $jthis.optgroup + '">' + $jel.prop('label') + '</div>';
		                        choices += $jthis.buildOptions($jel);
		                        $jthis.optgroup++;
		                    }
		                    else {
		                        var val = $jel.prop('value');
		                        var classes = $jel.attr('class');
		                        classes = ('undefined' !== typeof classes) ? ' ' + classes : '';

		                        // exclude the first option in multi-select mode
		                        if (0 < $jthis.idx || '' != val || ! $jthis.settings.multiple) {
		                            var disabled = $jel.is(':disabled') ? ' disabled' : '';
		                            var selected = -1 < $j.inArray(val, $jthis.selected) ? ' selected' : '';
		                            var group = ' g' + $jthis.optgroup;
		                            var row = '<div class="fs-option' + selected + disabled + group + classes + '" data-value="' + val + '" data-index="' + $jthis.idx + '"><span class="fs-checkbox"><i></i></span><div class="fs-option-label">' + $jel.html() + '</div></div>';

		                            if ('function' === typeof $jthis.settings.optionFormatter) {
		                                row = $jthis.settings.optionFormatter(row);
		                            }

		                            choices += row;
		                            $jthis.idx++;
		                        }
		                    }
		                });

		                return choices;
		            },

		            reloadDropdownLabel: function() {
		                var settings = this.settings;
		                var labelText = [];

		                this.$jwrap.find('.fs-option.selected').each(function(i, el) {
		                    labelText.push($j(el).find('.fs-option-label').html());
		                });

		                if (labelText.length < 1) {
		                    labelText = settings.placeholder;
		                }
		                else if (labelText.length > settings.numDisplayed) {
		                    labelText = settings.overflowText.replace('{n}', labelText.length);
		                }
		                else {
		                    labelText = labelText.join(', ');
		                }

		                this.$jwrap.find('.fs-label').html(labelText);
		                this.$jwrap.toggleClass('fs-default', labelText === settings.placeholder);
		            }
		        }


		        /**
		         * Loop through each matching element
		         */
		        return this.each(function() {
		            var data = $j(this).data('fSelect');

		            if (!data) {
		                data = new fSelect(this, settings);
		                $j(this).data('fSelect', data);
		            }

		            if ('string' === typeof settings) {
		                data[settings]();
		            }
		        });
		    }


		    /**
		     * Events
		     */
		    window.fSelect = {
		        'num_items': 0,
		        'active_id': null,
		        'active_el': null,
		        'last_choice': null,
		        'idx': -1
		    };

		    $j(document).on('click', '.fs-option:not(.hidden, .disabled)', function(e) {
		        var $jwrap = $j(this).closest('.fs-wrap');
		        var $jselect = $jwrap.find('select');
		        var do_close = false;

		        // prevent selections
		        if ($jwrap.hasClass('fs-disabled')) {
		            return;
		        }

		        if ($jwrap.hasClass('multiple')) {
		            var selected = [];

		            // shift + click support
		            if (e.shiftKey && null != window.fSelect.last_choice) {
		                var current_choice = parseInt($j(this).attr('data-index'));
		                var addOrRemove = ! $j(this).hasClass('selected');
		                var min = Math.min(window.fSelect.last_choice, current_choice);
		                var max = Math.max(window.fSelect.last_choice, current_choice);

		                for (i = min; i <= max; i++) {
		                    $jwrap.find('.fs-option[data-index='+ i +']')
		                        .not('.hidden, .disabled')
		                        .each(function() {
		                            $j(this).toggleClass('selected', addOrRemove);
		                        });
		                }
		            }
		            else {
		                window.fSelect.last_choice = parseInt($j(this).attr('data-index'));
		                $j(this).toggleClass('selected');
		            }

		            $jwrap.find('.fs-option.selected').each(function(i, el) {
		                selected.push($j(el).attr('data-value'));
		            });
		        }
		        else {
		            var selected = $j(this).attr('data-value');
		            $jwrap.find('.fs-option').removeClass('selected');
		            $j(this).addClass('selected');
		            do_close = true;
		        }

		        $jselect.val(selected);
		        $jselect.fSelect('reloadDropdownLabel');
		        $jselect.change();

		        // fire an event
		        $j(document).trigger('fs:changed', $jwrap);

		        if (do_close) {
		            closeDropdown($jwrap);
		        }
		    });

		    $j(document).on('keyup', '.fs-search input', function(e) {
		        if (40 == e.which) { // down
		            $j(this).blur();
		            return;
		        }

		        var $jwrap = $j(this).closest('.fs-wrap');
		        var matchOperators = /[|\\{}()[\]^$j+*?.]/g;
		        var keywords = $j(this).val().replace(matchOperators, '\\$j&');

		        $jwrap.find('.fs-option, .fs-optgroup-label').removeClass('hidden');

		        if ('' != keywords) {
		            $jwrap.find('.fs-option').each(function() {
		                var regex = new RegExp(keywords.unaccented(), 'gi');
		                var formatedValue = $j(this).find('.fs-option-label').text().unaccented();

		                if (null === formatedValue.match(regex)) {
		                    $j(this).addClass('hidden');
		                }
		            });

		            $jwrap.find('.fs-optgroup-label').each(function() {
		                var group = $j(this).attr('data-group');
		                var num_visible = $j(this).closest('.fs-options').find('.fs-option.g' + group + ':not(.hidden)').length;
		                if (num_visible < 1) {
		                    $j(this).addClass('hidden');
		                }
		            });
		        }

		        setIndexes($jwrap);
		        checkNoResults($jwrap);
		    });

		    $j(document).on('click', function(e) {
		        var $jel = $j(e.target);
		        var $jwrap = $jel.closest('.fs-wrap');

		        if (0 < $jwrap.length) {

		            // user clicked another fSelect box
		            if ($jwrap.data('id') !== window.fSelect.active_id) {
		                closeDropdown();
		            }

		            // fSelect box was toggled
		            if ($jel.hasClass('fs-label') || $jel.hasClass('fs-arrow')) {
		                var is_hidden = $jwrap.find('.fs-dropdown').hasClass('hidden');

		                if (is_hidden) {
		                    openDropdown($jwrap);
		                }
		                else {
		                    closeDropdown($jwrap);
		                }
		            }
		        }
		        // clicked outside, close all fSelect boxes
		        else {
		            closeDropdown();
		        }
		    });

		    $j(document).on('keydown', function(e) {
		        var $jwrap = window.fSelect.active_el;
		        var $jtarget = $j(e.target);

		        // toggle the dropdown on space
		        if ($jtarget.hasClass('fs-wrap')) {
		            if (32 == e.which || 13 == e.which) {
		                e.preventDefault();
		                $jtarget.find('.fs-label').trigger('click');
		                return;
		            }
		        }
		        // preserve spaces during search
		        else if (0 < $jtarget.closest('.fs-search').length) {
		            if (32 == e.which) {
		                return;
		            }
		        }
		        else if (null === $jwrap) {
		            return;
		        }

		        if (38 == e.which) { // up
		            e.preventDefault();

		            $jwrap.find('.fs-option.hl').removeClass('hl');

		            var $jcurrent = $jwrap.find('.fs-option[data-index=' + window.fSelect.idx + ']');
		            var $jprev = $jcurrent.prevAll('.fs-option:not(.hidden, .disabled)');

		            if ($jprev.length > 0) {
		                window.fSelect.idx = parseInt($jprev.attr('data-index'));
		                $jwrap.find('.fs-option[data-index=' + window.fSelect.idx + ']').addClass('hl');
		                setScroll($jwrap);
		            }
		            else {
		                window.fSelect.idx = -1;
		                $jwrap.find('.fs-search input').focus();
		            }
		        }
		        else if (40 == e.which) { // down
		            e.preventDefault();

		            var $jcurrent = $jwrap.find('.fs-option[data-index=' + window.fSelect.idx + ']');
		            if ($jcurrent.length < 1) {
		                var $jnext = $jwrap.find('.fs-option:not(.hidden, .disabled):first');
		            }
		            else {
		                var $jnext = $jcurrent.nextAll('.fs-option:not(.hidden, .disabled)');
		            }

		            if ($jnext.length > 0) {
		                window.fSelect.idx = parseInt($jnext.attr('data-index'));
		                $jwrap.find('.fs-option.hl').removeClass('hl');
		                $jwrap.find('.fs-option[data-index=' + window.fSelect.idx + ']').addClass('hl');
		                setScroll($jwrap);
		            }
		        }
		        else if (32 == e.which || 13 == e.which) { // space, enter
		            e.preventDefault();

		            $jwrap.find('.fs-option.hl').click();
		        }
		        else if (27 == e.which) { // esc
		            closeDropdown($jwrap);
		        }
		    });

		    function checkNoResults($jwrap) {
		        var addOrRemove = $jwrap.find('.fs-option:not(.hidden)').length > 0;
		        $jwrap.find('.fs-no-results').toggleClass('hidden', addOrRemove);
		    }

		    function setIndexes($jwrap) {
		        $jwrap.find('.fs-option.hl').removeClass('hl');
		        $jwrap.find('.fs-search input').focus();
		        window.fSelect.idx = -1;
		    }

		    function setScroll($jwrap) {
		        var $jcontainer = $jwrap.find('.fs-options');
		        var $jselected = $jwrap.find('.fs-option.hl');

		        var itemMin = $jselected.offset().top + $jcontainer.scrollTop();
		        var itemMax = itemMin + $jselected.outerHeight();
		        var containerMin = $jcontainer.offset().top + $jcontainer.scrollTop();
		        var containerMax = containerMin + $jcontainer.outerHeight();

		        if (itemMax > containerMax) { // scroll down
		            var to = $jcontainer.scrollTop() + itemMax - containerMax;
		            $jcontainer.scrollTop(to);
		        }
		        else if (itemMin < containerMin) { // scroll up
		            var to = $jcontainer.scrollTop() - containerMin - itemMin;
		            $jcontainer.scrollTop(to);
		        }
		    }

		    function openDropdown($jwrap) {
		        window.fSelect.active_el = $jwrap;
		        window.fSelect.active_id = $jwrap.data('id');
		        window.fSelect.initial_values = $jwrap.find('select').val();
		        $j(document).trigger('fs:opened', $jwrap);
		        $jwrap.find('.fs-dropdown').removeClass('hidden');
		        $jwrap.addClass('fs-open');
		        setIndexes($jwrap);
		        checkNoResults($jwrap);
		    }

		    function closeDropdown($jwrap) {
		        if ('undefined' == typeof $jwrap && null != window.fSelect.active_el) {
		            $jwrap = window.fSelect.active_el;
		        }
		        if ('undefined' !== typeof $jwrap) {
		            // only trigger if the values have changed
		            var initial_values = window.fSelect.initial_values;
		            var current_values = $jwrap.find('select').val();
		            if (JSON.stringify(initial_values) != JSON.stringify(current_values)) {
		                $j(document).trigger('fs:closed', $jwrap);
		            }
		        }

		        $j('.fs-wrap').removeClass('fs-open');
		        $j('.fs-dropdown').addClass('hidden');
		        window.fSelect.active_el = null;
		        window.fSelect.active_id = null;
		        window.fSelect.last_choice = null;
		    }

	});
		function refreshParent() 
		{
			//window.opener.parent.history.go(0);
			self.close();
		}
		
		
		 function getUsers() {
			  
			  //debugger;
			  var selectevalue = document.getElementById("userGroupYN").value;
			  if(selectevalue=="user"){
				document.getElementById("AddUserToProject_userGroupCode").value="-1";
			  }
			  else{
				document.getElementById("userWiseGroupCode").value="-1";
			  }
			  var userGroupId = document.getElementById("userWiseGroupCode").value;
			  var userType;
              var usergroup = document.getElementById("AddUserToProject_userGroupCode").value;
  			  var usergroupCode;
  			
  			if(usergroup!=-1){
  				usergroupCode = usergroup;
  			}
  			else{
  				usergroupCode = userGroupId
  			}
  			
  			//debugger;
  			if(usergroupCode!=-1){
  			$.ajax({		
  				  url: "checkUseType_ex.do?usergroupId="+usergroupCode,
  				  success: function(data) 
  				  {
  					  //alert(data);
  					  userType = data;
  				  },
  				  async: false
  			});
  			}
	        

		
			 $j.ajax({			
						url: 'showUserGroupDtl_ex.do?userGroupId=' + userGroupId,
						beforeSend: function()
						{
			  			 opt = "No Users are Found";		
						 document.getElementById('showUserDtl').innerHTML = opt;
						},
						success: function(data) 
					  	{
						 var opt = "  ";			  		
					       if(data.length > 0)
						   {
					    	//debugger;
					    	var usersArr = data.split(',');
					    	 for(i=0; i<usersArr.length;i++)
						       {
					    		var keyValuePair = usersArr[i];
					    		var keyValuePairArr = keyValuePair.split('::');
					    		var key = keyValuePairArr[0];
					    		var value = keyValuePairArr[1];
					    		opt += "<option value='"+key+"'>"+value+"</option>";				    				
					    		}	
					    		opt = '<select name="userCode" class="temp" multiple="multiple">'+
					    		opt +
				  				  '</select>';
					    		   document.getElementById('showUserDtl').innerHTML = opt;
					    			
					    			window.fs_test=$j('.temp').fSelect();
							    	
					    			//window.fs_test = $j('.commentUsers').fSelect();
							}	
					  	}
			});
		}
		function getUserGroups() {
			  
			  //debugger;
			  var selectevalue = document.getElementById("userGroupYN").value;
			  if(selectevalue=="user"){
				document.getElementById("AddUserToProject_userGroupCode").value="-1";
			  }
			 else{
				document.getElementById("userWiseGroupCode").value="-1";
			 }
			  var userGroupId = document.getElementById("userWiseGroupCode").value;
              var usergroup = document.getElementById("AddUserToProject_userGroupCode").value;
			  var usergroupCode;
			  var userType;
			  
			if(usergroup!=-1){
				usergroupCode = usergroup;
			}
			else{
				usergroupCode = userGroupId
			}
			
			//debugger;
			
			if(usergroupCode!=-1){
			$.ajax({		
				  url: "checkUseType_ex.do?usergroupId="+usergroupCode,
				  success: function(data) 
				  {
					  //alert(data);
					  userType = data;
				  },
				  async: false
			});
			}
	      if(userType=='WU'){
	        	document.getElementById("modulespecrights").checked = true;
	        	 $("#hidestage").hide();
			}
	      else{
	    	  document.getElementById("modulespecrights").checked = false;
	    	  $("#hidestage").show();
	      }
		}
		function validate()
		{
			//debugger;
			var unlimitedrights= $('#unlimited:checked').val();
			
			if(document.addUsertoProjectForm.userGroupYN.value == -1){
			
				alert("Please select User/UserGroupName");
				document.addUsertoProjectForm.userGroupYN.style.backgroundColor="#FFE6F7"; 
	     		document.addUsertoProjectForm.userGroupYN.focus();
	     		return false;
     		}
     		else if (document.addUsertoProjectForm.userGroupYN.value == 'user')
     		{
     			if(document.addUsertoProjectForm.userWiseGroupCode.value==-1)
     			{
     				alert("Please select UserGroupName(userWise)");
					document.addUsertoProjectForm.userWiseGroupCode.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoProjectForm.userWiseGroupCode.focus();
     				return false;
     			}
     			if(document.addUsertoProjectForm.userCode.value=='')
     			{
     				alert("Please select UserName");
					document.addUsertoProjectForm.userCode.focus();
     				return false;
     			
     			}
     			if(document.addUsertoProjectForm.userCode.value==-1)
     			{
     				alert("Please select UserName");
					document.addUsertoProjectForm.userCode.focus();
     				return false;
     			
     			}
     			//When no user is avae
     			if($('#showUserDtl').children().length<=0){
    				alert("User is not found in selected group");
    				return false;
    			}
     		}
     		else if (document.addUsertoProjectForm.userGroupYN.value == 'usergroup')
     		{
     			if(document.addUsertoProjectForm.userGroupCode.value==-1)
     			{
     				alert("Please select UserGroupName");
					document.addUsertoProjectForm.userGroupCode.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoProjectForm.userGroupCode.focus();
     				return false;
     			}
     		
     		}
			
		if(document.addUsertoProjectForm.fromDt.value=='' && unlimitedrights!=1)
     		{
     			alert("Please select From Date");
     			document.addUsertoProjectForm.fromDt.style.backgroundColor="#FFE6F7"; 
		     	document.addUsertoProjectForm.fromDt.focus();
     			return false;
     		}

     		if(document.addUsertoProjectForm.toDt.value=='' && unlimitedrights!=1)
     		{
     			alert("Please select To Date");
     			document.addUsertoProjectForm.toDt.style.backgroundColor="#FFE6F7"; 
		     	document.addUsertoProjectForm.toDt.focus();
     			return false;
     		}
			
     		var fdate1 = document.getElementById('fromDt').value;
       		var tdate1 = document.getElementById('toDt').value;
       		var fdate = Date.parse(fdate1);
			var tdate = Date.parse(tdate1);
       		var alertMsg =  'To Date must be greater than or equal to From Date.'; 
             //debugger;					
       		if(fdate != '' && tdate != '' && fdate > tdate && unlimitedrights!=1)
       	    {
       		    alert(alertMsg);
       		    document.getElementById('toDt').value = "";
       		    document.addUsertoProjectForm.toDt.style.backgroundColor="#FFE6F7"; 
	     		document.addUsertoProjectForm.toDt.focus();
       		    return false;
       	    }         		

     		if (!$("#modulespecrights").is(':checked')) {
     		//debugger;
			var stages = document.addUsertoProjectForm.stageIds;
			var stg="";
			for(i=0; i < stages.length; i++){
				if(stages[i].checked){
					if(stg==""){
						stg=stages[i].value;
					}
					else{
						stg+=","+stages[i].value;
					}
				}
			}
			if(stg == ''){
				alert("Please select stage(s).");
				return false;
			}
     	}
     	
	return true;
	}
		
 		function showcombobox()
	    {
	    	//debugger;
	    	var selectcombo=document.addUsertoProjectForm.userGroupYN.value;
	    	var usertab=document.getElementById('showUserDtl').innerHTML='';
	    	
	    	
	    	if(selectcombo=="user")
		    {
		    	
		    	document.getElementById('usergroupcombobox').style.display='none';
	    		document.getElementById('usercombobox').style.display='contents';
	    		document.getElementById('unametd').style.display='block';
	    	}
	    	else if(selectcombo=="usergroup")
	    	{
	    		
	    		document.getElementById('usergroupcombobox').style.display='contents';
	    		document.getElementById('usercombobox').style.display='none';
	    		document.getElementById('unametd').style.display='none';
	    	}
	    	
	    }
	
		
		function checkRights(ckType){
			
			var selectevalue = document.getElementById("userGroupYN").value;
			if(selectevalue=="user"){
				document.getElementById("AddUserToProject_userGroupCode").value="-1";
			}
			else{
				document.getElementById("userWiseGroupCode").value="-1";
			}
			var usergroupId
			var usergroup = document.getElementById("AddUserToProject_userGroupCode").value;
			var usergroupCode = document.getElementById("userWiseGroupCode").value;
			
			if(usergroup!=-1){
				usergroupId = usergroup;
			}
			else{
				usergroupId = usergroupCode
			}
			
			var userType;
			//debugger;
			if(usergroupId!=-1){
			$.ajax({		
				  url: "checkUseType_ex.do?usergroupId="+usergroupId,
				  success: function(data) 
				  {
					  //alert(data);
					  userType = data;
				  },
				  async: false
			});
			
			var checkboxes = [];
			$("input:checkbox[name=stageIds]:checked").each(function(){
				checkboxes.push($(this).val());
			});
			//debugger;
		    var ckName = document.getElementsByName(ckType.name);
		    var checked = document.getElementById(ckType.id);
		    
			if(userType!="WA" && checkboxes.includes("10")){
				if (checked.checked) {
				      for(var i=0; i < ckName.length; i++){
				          if(ckName[i].value != "10")
				    	  	ckName[i].checked = false;				            				         
				      } 
				    }
			}
			if(userType!="WA" && checkboxes.includes("20")){
				if (checked.checked) {
				      for(var i=0; i < ckName.length; i++){
				          if(ckName[i].value != "20")
				    	  	ckName[i].checked = false;				            				         
				      } 
				    }
			}
		    
			if(userType!="WA" && (ckType.value==10 || ckType.value==20)){
			
		    if (checked.checked) {
		      for(var i=0; i < ckName.length; i++){
		          if(!ckName[i].checked){
		              ckName[i].disabled = true;
		          }else{
		              ckName[i].disabled = false;
		          }
		      } 
		    }
		    else {
		      for(var i=0; i < ckName.length; i++){
		        ckName[i].disabled = false;
		      } 
		    }
			}
			}
		}
		function Filevalidate()
		
		{
			//debugger;
			var returnFlag = "true";
			var fileName=document.getElementById('docName').value;
			
	    	//var index=uploadFile.lastIndexOf('.');
	    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
	    	var fileext;
	    	var strChar;
	    	
	    	fileName = fileName.trim();
			if (fileName == "" || fileName == null){
				alert("Please enter Document name.");
				document.getElementById('docName').style.backgroundColor="#FFE6F7";
				returnFlag ="false";
	      		return false; 
			}
	     	for (i = 0; i < fileName.length; i++)
	    	{
	 			strChar = fileName.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
			 		alert("Invalid file name. \n\nOnly alphabets, digits,dot,underscore and dash are allowed.");
		      		document.addUsertoProjectForm.fileName.style.backgroundColor="#FFE6F7"; 
		      		returnFlag ="false";
		      		return false;  			
	 				}
			}
	     	var RefrenceFileUpload = "<s:property value='RefrenceFileUpload'/>";
	     	//debugger;
	     	
	     	if(RefrenceFileUpload=="Yes" || RefrenceFileUpload=="yes"){
					
	     		if(document.getElementById('files1')!=""){	
				    var input = document.getElementById('files1');
				    var children = [];
				    for (var i = 0; i < input.files.length; ++i) {
				        children[i] = input.files.item(i).name;
				    }	
				   for (var i = 0; i < children.length; ++i)
					{
				  		var child = children[i];
				  
				 		 for(var j=0; j<child.length; j++){
							strChar = child.charAt(j);
			 				if (strInvalidChars.indexOf(strChar)!= -1)
							{
			 					alert("Invalid Reference Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
		      					return false;  			
							}
					  	}
		   			}
				}
	     		//debugger;
	     		/* var fileInput = document.getElementById('files1');	
				 if(fileInput.files.length > 0) {
	     	//var ReffileName = document.getElementById('RefFileName').value;
	     	//ReffileName=ReffileName.trim();
			
	     	var uploadRefFile = document.addUsertoProjectForm.uploadRefFile.value;
			//uploadRefFile = uploadRefFile.replace(/^.*[\\\/]/,'');
			 var refExt = uploadRefFile.replace(/^.*[\\\/]/, '');
			 document.getElementById('RefFileName').value = uploadRefFile.replace(/^.*[\\\/]/, '');; */
			
			
	     	/* for (i = 0; i < ReffileName.length; i++)
	    	{
	 			strChar = ReffileName.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
			 		alert("Invalid Refrence file name. \n\nOnly alphabets, digits,dot,underscore and dash are allowed.");
			 		document.getElementById('RefFileName').style.backgroundColor="#FFE6F7"; 
		      		returnFlag ="false";
		      		return false;  			
	 				}
			}
	     	if(ReffileName=="" && uploadRefFile!=""){
	     		alert("Please Enter Refrence Document Name.");
	     		document.getElementById('RefFileName').style.backgroundColor="#FFE6F7"; 
	     		returnFlag ="false";
	      		return false;  
	     	}
	     	
	     	if(ReffileName!="" && uploadRefFile==""){
	     		alert("Please Select Refrence Document.");
	     		returnFlag ="false";
	     		return false;
	     	} 
	     		}*/
	     }
	     	var txtAttr = document.getElementsByClassName("txt");
	    	var dateAttr = document.getElementsByClassName("date");
	    	if(txtAttr.lengh != 0){
	    	for (var i = 0; i < txtAttr.length; i++) {
	    		 var attrVal = txtAttr[i].value;
	    		 attrVal= attrVal.trim();
	    		 if(attrVal==""){
	    			 alert("Please specify value.");
	    			 returnFlag ="false";
	    			 return false;
	    		 }
	    	 }
	    	if(dateAttr.lengh != 0){
	    		for (var i = 0; i < dateAttr.length; i++) {
	    			 var attrVal = dateAttr[i].value;
	    			 attrVal= attrVal.trim();
	    			 if(attrVal==""){
	    				 alert("Please specify value.");
	    				 returnFlag ="false";
	    				 return false;
	    			 }
	    		 }
	    	 }
	    	}
	    	
	    	var uploadFile = document.addUsertoProjectForm.uploadFile.value;
	    	
			//var remarkvalue = document.workspaceNodeAttrForm.remark.value;
	    	//var hiddenFieldvalue = document.workspaceNodeAttrForm.secret.value;
	    	//var deltefilevalue = $('input[name="deleteFile"]:checked').length
	    	uploadFile = uploadFile.replace(/^.*[\\\/]/,'');
	    	var index=uploadFile.lastIndexOf('.');
	    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
	    	var fileext = uploadFile.substring(index+1).toLowerCase();
	    	var strChar;
	    	if(uploadFile=="")
			{
					alert("Please Select Document");
					document.addUsertoProjectForm.uploadFile.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoProjectForm.uploadFile.focus();
		     		returnFlag ="false";
					return false;
			}
	    	
	    	if((fileext !="pdf" && fileext!="doc" && fileext!="docx") ){
				alert("Please upload valid extension(e.g. .pdf, .doc & .docx) Document.");
				returnFlag ="false";
				return false;
			}
	    	
	     	for (i = 0; i < uploadFile.length; i++)
	    	{
	 			strChar = uploadFile.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
			 		alert("Invalid Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
		      			document.addUsertoProjectForm.uploadFile.style.backgroundColor="#FFE6F7"; 
		      			returnFlag ="false";
		      			return false;  			
	 				}
		   	}
	    if(returnFlag =="true"){
	    	return true;	
	    }
	    else{
	    	return false;
	    }
	}
		
		function updateList() {
		    var input = document.getElementById('files1');
		    var children = "";
		    var children = [];
		    for (var i = 0; i < input.files.length; ++i) {
		        children[i] = input.files.item(i).name;
		    }
		    document.getElementById('RefFileName').value=children;
		}
		
</script>

</head>
<body>
<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>
<div align="center" style="color: green;"><s:actionmessage /></div>
<br>
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float:left;">${docTypeName} Detail</b></div>
</div>
<s:form action="AddRecordForESign" method="post" enctype="multipart/form-data" id="addUsertoProjectForm" name="addUsertoProjectForm" 
		onsubmit="return Filevalidate();">
<div class="errordiv" align="center" style="font-size: 14px; margin-bottom: 20px; color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div id="imp" style="float: right; margin-right: 10px; margin-top: -10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
<br>

<table class="doubleheight" style="margin-top: -16px;">
							<s:if test="attrDtl.size == 0">
								<tr>
									<td style='color: navy; font-size: 12px;'>No details available.</td>
								</tr>
							</s:if>
							<s:else>

								
								<s:set name="elementid" value="1"></s:set>

								<s:set name="prevattrid" value="-1" /> 
								<tr>
								<td class="title" align="right" width="50%">Document
								<span style="font-size:20px;color:red">*</span> &nbsp; </td>
								<td align="left">
								<input type="text"	class="target" id="docName" name="docName" />
								</td>
									</tr>						
								<s:iterator value="attrDtl">
						
									<s:if test="attrType == 'Text'">

										<tr class="none">
										
											<td class="title" align="right" width="50%">${attrName }
												<span style="font-size:20px;color:red">*</span> &nbsp;</td>
											<td align="left"><input class="txt" type="text" 
												name="attrValueId<s:property value="#elementid"/>"
												value="<s:property value="attrValue"/>"> <input
												type="hidden"
												name="attrType<s:property value="#elementid"/>" value="Text">
											<input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"></td>
										</tr>

										<s:set name="elementid" value="#elementid + 1"></s:set>
									</s:if>
									<s:elseif test="attrType == 'TextArea'">

										<tr class="none">
											<td class="title" align="right" width="50%">${attrName}  
												<span style="font-size:20px;color:red">*</span> &nbsp;</td>
											<td align="left"><textarea rows="6" cols="30" style="width:200px;"
												name="attrValueId<s:property value="#elementid"/>"><s:property
												value="attrValue" /></textarea> <input type="hidden"
												name="attrType<s:property value="#elementid"/>"
												value="TextArea"> <input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"></td>
										</tr>
										<s:set name="elementid" value="#elementid + 1"></s:set>
									</s:elseif>
									<s:elseif test="attrType == 'Date'">

										<tr class="none">
											<td class="title" align="right" width="50%">${attrName } 
											<span style="font-size:20px;color:red">*</span> &nbsp;</td>
											<td align="left"><input class="date" type="hidden"
												name="attrType<s:property value="#elementid"/>" value="Date">
											<input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"> <input type="text"
												name="attrValueId<s:property value="#elementid"/>"
												class="attrValueIdDate" readonly="readonly"
												id="attrValueId<s:property value="#elementid"/>"
												value="<s:property value="attrValue"/>">
											(YYYY/MM/DD) &nbsp;<IMG
												onclick="
																						if(document.getElementById('attrValueId<s:property value="#elementid"/>').value != '' 
																								&& confirm('Are you sure?'))
																							document.getElementById('attrValueId<s:property value="#elementid"/>').value = '';
																					"
												src="<%=request.getContextPath() %>/images/clear.jpg"
												align="middle" title="Clear Date"></td>
										</tr>
										<s:set name="elementid" value="#elementid + 1"></s:set>
									</s:elseif>
									<s:elseif test="attrType == 'Combo'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
											<tr class="none">
												<td class="title" align="right" width="50%">${attrName } 
													<span style="font-size:20px;color:red">*</span> &nbsp;</td>
												<td align="left"><select
													name="attrValueId<s:property value="#elementid"/>">
													<s:set name="outterAttrId" value="attrId" />
													<s:set name="outterAttrValue" value="attrMatrixValue" />
													<s:if test="attrValue==''">
														<OPTION value="">Please select value</OPTION>
													</s:if>
													<s:iterator value="attrDtl">
														<s:if test="#outterAttrId == attrId">
															<OPTION value="<s:property value="attrMatrixValue" />"
																<s:if test="attrMatrixValue == attrValue">selected="selected"</s:if>>
															<s:property value="attrMatrixValue" /></OPTION>
														</s:if>
													</s:iterator>
												</select> <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="Combo"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
									<s:elseif test="attrType == 'MultiSelectionCombo'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
										<s:if test="#session.usertypename == 'WA' && attrId==32">
											<tr class="none">
												<td class="title" align="right" width="50%">${attrName }  &nbsp;</td>
											
												<td align="left">
												<select multiple="multiple" class="commentUsers" id="milestoneId" 
													name="attrValueId<s:property value="#elementid"/>">
													<s:set name="outterAttrId" value="attrId" />
													<s:set name="outterAttrValue" value="attrMatrixValue" />
													<s:iterator value="attrDtl">
														<s:if test="#outterAttrId == attrId">
															<OPTION value="<s:property value="attrMatrixValue" />"
																<s:if test="%{attrValue.contains(attrMatrixValue)}">selected="selected"</s:if>>
															<s:property value="attrMatrixValue" /></OPTION>
														</s:if>
													</s:iterator>
												</select>
											
												 <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="MultiSelectionCombo"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											</s:if>
											<s:else>
												<tr class="none">
											<td class="title" align="right" width="50%">${attrName } &nbsp;</td>
											<td align="left"><span><s:property value="attrValue"/></span>
											<input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"></td>
										</tr>
										
											</s:else>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
									<s:elseif test="attrType == 'AutoCompleter'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
											<tr class="none">
												<td class="title" align="right" width="50%">${attrName } &nbsp;</td>
												<td align="left"><s:iterator value="filterAutoCompleterList">
													<s:if test="top[0] == attrId">
														<s:autocompleter name="attrValueId%{#elementid}"
															list="top[1]" listKey="top" listValue="top"
															autoComplete="false"
															cssStyle="margin: 0; padding: 2px; width:400px;"
															dropdownHeight="145" loadOnTextChange="false"
															resultsLimit="all" forceValidOption="true"
															value="%{attrValue}">
														</s:autocompleter>
													</s:if>
												</s:iterator> <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="AutoCompleter"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
									<s:elseif test="attrType == 'Dynamic'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
											<tr class="none">
												<td class="title" align="right" width="50%">${attrName } &nbsp;</td>
												<td align="left"><s:iterator value="filterDynamicList">
													<s:if test="top[0] == attrId">
														<s:select list="top[1]" headerKey=""
															headerValue="Please Select Value" value="%{attrValue}"
															name="attrValueId%{#elementid}">
														</s:select>
													</s:if>
												</s:iterator> <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="Dynamic"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
								</s:iterator>								
							</s:else>
		<tr class="none" id="fileTr">
			<td class="title" align="right" width="50%">
				<span class="title">New Document</span>
				<span style="font-size:20px;color:red">*</span> &nbsp;</td>
				<td align="left">
				<!-- <input type="file" id="uploadFile"> -->
				<s:file name="uploadFile" required="true"></s:file>(e.g. .pdf, .doc & .docx)
				</td>
		</tr>
		<s:if test='RefrenceFileUpload =="Yes"'>
		<%-- <tr class="none" id="fileTr">
			<td class="title" align="right" width="50%">
			<span class="title"> Reference Document Name </span> &nbsp;
				<td align="left">
					<input type="hidden"	class="target" id="RefFileName" name="RefFileName" />
				</td>
		</tr> --%>
		<tr class="none" id="fileTr">
			<td class="title" align="right" width="50%">
				<span class="title">Upload Reference Document</span> &nbsp;</td>
				<td align="left">
				<!-- <input type="file" id="uploadFile"> -->
				<input type="hidden" class="target" id="RefFileName" name="RefFileName" />
				<%-- <s:file name="uploadRefFile" required="true"></s:file> --%>
				<input type="file" name="files1" id="files1" multiple onchange="javascript:updateList()" />
				</td>
		</tr>
		</s:if>
		<%-- <tr class="none" id="fileTr">
			<td class="title" align="right" width="50%">
				<span class="title">Reason for Change </span>&nbsp;</td>
				<td align="left"><textarea rows="3" cols="30" id = "remark" name="remark"></textarea></td>
		</tr>					 --%>
							
							
</table>
<br/>

	<!-- <input type="button" value="Save Document" id="savesendbtn" class="button" onclick="return saveFile();" />&nbsp; -->

<s:submit cssClass="button" id="Save" value="Save" name="buttonName"></s:submit>
<input type="button" value="Close" class="button" onclick="window.location='ESignature.do?docId=<s:property value="WorkspaceId"/>&docType=<s:property value="docType"/>'">
<s:hidden name="WorkspaceId"></s:hidden>
<s:hidden name="docType"></s:hidden>
<%-- <s:hidden name="RefFileName" id="refExt"></s:hidden> --%>
<%-- <s:hidden name="refExt"></s:hidden> --%>
</s:form>
<br/>
</div>
</div>
</div>




</body>
</html>
