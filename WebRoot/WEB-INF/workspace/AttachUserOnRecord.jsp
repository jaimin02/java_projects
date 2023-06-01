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
</style>


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
		
		
		/*  $("#duration").keypress(function (e) {
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		     return false;
		    }
		   }); */
		
		
		$("#remark").val("");
		   getUsers();
	/* 
		 $('#usertable').dataTable( {
				
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
				
		}); */
	
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
		                    search_html = '<div class="fs-search"><input type="text" style="width: 190px;"placeholder="' + this.settings.searchText + '" /></div>';
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
		 function getUsers() {
			  
			  //debugger;
			  var wsId= '<s:property value="WorkspaceId"/>';
			  /* var selectevalue = document.getElementById("userGroupYN").value;
			  if(selectevalue=="user"){
				document.getElementById("SaveUserOnRecord_userGroupCode").value="-1";
			  }
			  else{
				document.getElementById("userWiseGroupCode").value="-1";
			  } */
			  var userGroupId = 2;//document.getElementById("userWiseGroupCode").value;
			  var userType;
			  usergroupCode = userGroupId;
             /*  var usergroup = document.getElementById("SaveUserOnRecord_userGroupCode").value;
  			  var usergroupCode;
  			
  			if(usergroup!=-1){
  				usergroupCode = usergroup;
  			}
  			else{
  				usergroupCode = userGroupId
  			} */
  			
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
				 url: "showUserGroupDtlForDocSign_ex.do?WorkspaceId="+wsId+"&nodeId="+<s:property value="nodeId"/>+"&userGroupId=" + userGroupId,
						beforeSend: function()
						{
			  			 opt = "No Users are Found";		
						 document.getElementById('showUserDtl').innerHTML = opt;
						},
						success: function(data) 
					  	{
							//debugger;
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
		function validate(buttonName)
		{
			//debugger;
			
			//var select=document.addUsertoProjectForm.userCode.options;
			var count=0;
			var button = buttonName;
			var userType = "<s:property value='#session.usertypename'/>";
			//var timeDuration = document.getElementById("duration").value;
			/* if(document.addUsertoProjectForm.userGroupYN.value == -1){
			
				alert("Please select User/UserGroupName");
				document.addUsertoProjectForm.userGroupYN.style.backgroundColor="#FFE6F7"; 
	     		document.addUsertoProjectForm.userGroupYN.focus();
	     		return false;
     		}
     		else if (document.addUsertoProjectForm.userGroupYN.value == 'user')
     		{ */
     			if(document.addUsertoProjectForm.userWiseGroupCode.value==-1)
     			{
     				alert("Please select User Profile.");
					document.addUsertoProjectForm.userWiseGroupCode.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoProjectForm.userWiseGroupCode.focus();
     				return false;
     			}
     			if(document.addUsertoProjectForm.userCode.value=='')
     			{
     				alert("Please select User Name.");
					document.addUsertoProjectForm.userCode.focus();
     				return false;
     			
     			}
     			if(document.addUsertoProjectForm.userCode.value==-1)
     			{
     				alert("Please select User Name.");
					document.addUsertoProjectForm.userCode.focus();
     				return false;
     			
     			}
     			//When no user is avae
     			if($('#showUserDtl').children().length<=0){
    				alert("User is not found in selected group");
    				return false;
    			}
     		/* }
     		else if (document.addUsertoProjectForm.userGroupYN.value == 'usergroup')
     		{
     			if(document.addUsertoProjectForm.userGroupCode.value==-1)
     			{
     				alert("Please select UserGroupName");
					document.addUsertoProjectForm.userGroupCode.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoProjectForm.userGroupCode.focus();
     				return false;
     			}
     		
     		} */
			var select=document.addUsertoProjectForm.userCode.options;
    		for(i=0;i<select.length;i++) {
    			if(select[i].selected)
    			{
    				count++;
    			}
    			
    		} 
    		if(count==0)
    		{
    			alert("Please select atleast one user");
    			return false;
    		}
    		
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
    		
    		if(buttonName=="Update Rights")
    		{
    			if (!confirm("You are about to attach stage/s to user/s which would overwrite their rights.\nDo you want to continue?"))
    				return false;
    		}
    		if(buttonName=="Remove Rights")
    		{
    			if (!confirm("Do You want to Remove Rights?"))
    				return false;
    		}
    		return true;   		

     	
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
		function HideDateSelection()
		{
			if ($("#unlimited").is(':checked')) {
			
				$("#frmdate").hide();
			      $("#todate").hide();
			     } else {
			    	$("#frmdate").show();
				      $("#todate").show();
				   }
		}
		function HideStageSelection()
		{
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
			}  
			if ($("#modulespecrights").is(':checked')) {
				$("#hidestage").hide();
			}
			else
			{
				$("#hidestage").show();
			}
		}		
		function deleteWorkspceUser(wsId,userCode,userGroupCode){
			//debugger;
			var remark = prompt("Please specify reason for change.");
			remark = remark.trim();
			if (remark != null && remark != ""){
			 	var url = "deleteWorkspaceUser.do?workspaceid=" + wsId + "&userId=" + userCode +"&userGroupId="+ userGroupCode + "&remark=" + remark;
				location.href = url;	
			}
			else if(remark==""){
				//debugger;
				alert("Please specify reason for change.");
				return false;
			}
		}
		function workspaceUserHistory(wsId,userCode,userGroupCode)
		{
		 	//debugger;
			if (wsId == null || wsId=='')
			{
				alert("Please Select Project");
				return false;
			}else{	
				
				str="showWorkspaceUserDetail_b.do?workspaceid=" + wsId + " &userId="+ userCode + "&userGroupId=" + userGroupCode;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
			 	win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
				return true;
			}
		}		
		
		function deleteRights(stageIds,userCode,nodeId){
			//debugger;
			 var wsId= '<s:property value="WorkspaceId"/>';
				var ok=confirm("Do You want to Remove Rights?")
				if(ok==true){
					var remark = prompt("Please specify reason for change.");
					remark = remark.trim();
					 if (remark != null && remark != ""){ 
						 $('#remark').val(remark);
						 $.ajax( {
								url:'RemoveAssignedUserRightsForRecord_ex.do?WorkspaceId='+wsId+'&stageIds='+stageIds+'&userCode='+userCode+'&nodeId='+nodeId+'&remark='+remark,
								beforeSend : function() {
									
								},
								success : function(data) {
									window.location.reload();
									},
								error: function(data) 
								  {
									alert("Something went wrong while Removing rights.");
								  },
										async: false
							});
						 return true;
					}
					 else{
						 alert("Please specify reason for change.");
						 return false;
					 }
				}
				else{
					return false;
				}
				
			
			
			return true;
			
		}	
		
		function openlinkEdit(buttonName,stageIds,userCode,userGroupCode,nodeId,roleCode){
 			//debugger;
 			var button = buttonName;
 			 var wsId = "<s:property value='WorkspaceId'/>";
 			var modulewiserights="modulewiserights";
 			str="ChangeRecordRights_b.do?WorkspaceId="+wsId+"&userGroupId="+userGroupCode+"&stageIds="+stageIds+"&userCode="+userCode+"&nodeId="+nodeId+"&RightsType="+modulewiserights+'&roleCode='+roleCode;
 			win3=window.open(str,"ThisWindow2","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=500,resizable=no,titlebar=no");	
 			win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
 			return true;
 		}
		
		function updateUserRole(buttonName,stageIds,userCode,userGroupCode,nodeId,roleCode){
 			//debugger;
 			var button = buttonName;
 			 var wsId = "<s:property value='WorkspaceId'/>";
 			var modulewiserights="modulewiserights";
 			str="ChangeRecordRole_b.do?WorkspaceId="+wsId+"&userGroupId="+userGroupCode+"&stageIds="+stageIds+"&userCode="+userCode+"&nodeId="+nodeId+"&RightsType="+modulewiserights+'&roleCode='+roleCode;
 			win3=window.open(str,"ThisWindow2","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=500,resizable=no,titlebar=no");	
 			win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
 			return true;
 		}
		function checkRights(ckType){
			
			//debugger;
			var usergroupCode = document.getElementById("userWiseGroupCode").value;
			var userType;
			var projectCode = '<s:property value="projectCode"/>';
	 	if(projectCode!="0003"){
			var checkboxes = [];
			$("input:checkbox[name=stageIds]:checked").each(function(){
				checkboxes.push($(this).val());
			});
			//debugger;
		    var ckName = document.getElementsByName(ckType.name);
		    var checked = document.getElementById(ckType.id);
		    
			if(checkboxes.includes("10")){
				if (checked.checked) {
				      for(var i=0; i < ckName.length; i++){
				          if(ckName[i].value != "10")
				    	  	ckName[i].checked = false;				            				         
				      } 
				    }
			}
			if(checkboxes.includes("20")){
				if (checked.checked) {
				      for(var i=0; i < ckName.length; i++){
				          if(ckName[i].value != "20")
				    	  	ckName[i].checked = false;				            				         
				      } 
				    }
			}
			if(checkboxes.includes("100")){
				if (checked.checked) {
				      for(var i=0; i < ckName.length; i++){
				          if(ckName[i].value != "100")
				    	  	ckName[i].checked = false;				            				         
				      } 
				    }
			}
		    
			if(ckType.value==10 || ckType.value==20 || ckType.value==100){
			
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
		function moduleUserHistory(nodeId)
		{
		 var workspaceId= "<s:property value='WorkspaceId'/>";
		 str="ShowAssignModuleUserDetailHistory_b.do?docId="+workspaceId+"&recordId="+<s:property value="nodeId"/>;
		 win3=window.open(str,"ThisWindow2","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
		 win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(400/2));	
		 return true;
		}
		function refreshParent() 
		{
			window.opener.parent.history.go(0);
			self.close();
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

<div class="boxborder"><div class="all_title"><b style="float:left:">Attach User On New Created Document</b></div>

<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">

<s:form action="SaveUserOnRecord" method="post" name="addUsertoProjectForm">

	<s:hidden name="WorkspaceId"></s:hidden>
	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="userGroupId"></s:hidden>
	
<div id="imp" style="float: right; margin-right: 10px; margin-top: -10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
	<table width="100%">

		<%-- <tr>
			<td class="title" align="left" width="25%"
				style="padding: 2px; padding-right: 8px; padding-left: 5px; padding-right: 8px;">User / Group</td>
			<td align="left"><select name="userGroupYN" id="userGroupYN"
				onchange="return showcombobox();">
				<option value="-1">Select User or UserGroup</option>
				<option value="user">User</option>
				<!-- <option value="usergroup">User Group</option> -->
			</select></td>
		</tr>
		<tr id="usergroupcombobox" style="display: none;">
			<td class="title" align="left" width="25%"
				style="padding: 2px; padding-right: 8px; padding-left: 5px; padding-right: 8px;">User Group </td>
			<td align="left"><s:select list="workspaceUserGroupsDetails"
				name="userGroupCode" headerKey="-1"
				headerValue="Select User Group" listKey="userGroupCode"
				listValue="userGroupName" theme="simple" onchange="getUserGroups();">
			</s:select></td>
		</tr> --%>
		<%-- <tr id="usercombobox">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px; padding-left: 5px; padding-right: 8px;">User Profile
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left">			
			 <s:select list="workspaceUserGroupsDetails"
				name="userWiseGroupCode" id="userWiseGroupCode" cssStyle="width: 199px;"
				headerValue="Select User Profile" listKey="userGroupCode"
				listValue="userGroupName" theme="simple" onchange="getUsers();">
			</s:select> <ajax:event ajaxRef="addUsertoProject/getUserDtl" /></td>
		</tr> --%>

		<tr>
			<td align="right" id="unametd" style="padding: 2px; padding-right: 8px;" class="title" width="50%">User
			<span style="font-size:20px;color:red">*</span></td>
			<td align="left">
			<div id="showUserDtl"></div>
			</td>
		</tr>

		<tr >
			 <td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
			  <label class="title" > Stages</label>
			  <span style="font-size:20px;color:red">*</span></td>
			  <td align="left"> <s:iterator value="getStageDetail">
				  <input style="height: 11px; width: 12px;"type="checkbox" name="stageIds" id="stages_<s:property value = "stageId"/>"
					value="<s:property value="stageId"/>" onclick="return checkRights(this)">
						<LABEL class="even" for="stages_<s:property value = "stageId"/>"><s:property
							value="StageDesc" /></LABEL>
					</s:iterator>
			</td>
		</tr>
		<%-- <s:if test="#session.usertypename == 'WA'">
				<tr>
					<td><label class="title"> Duration(Man Hours) :</label>
					<input type="text" name="duration" value="" id="duration" style="width:100px"/></td>
				</tr>
		</s:if>
 --%>
		<tr><td></td></tr>
		<tr>
				<td colspan="2" align="center">
					<s:if test="#session.usertypename == 'WA'">
						 <s:submit value="Assign Rights" cssClass="button" name="buttonName"
						onclick="return validate('Update Rights');" />
					</s:if>
						<input type="button" value="Audit Trail"
						class="button" name="Audit Trail" onclick="return moduleUserHistory('<s:property value="nodeId" />');" />
						<input type="button" value="Close"
						class="button" name="Close" onclick="return refreshParent();" />
					</td>
				</tr>
	</table>

	<ajax:enable />
</s:form> <br>
<div style="max-height: 400px; overflow: auto; border: 1px solid; width: 98%; margin-left: 12px;"
	align="center">
<table align="center" style="width:100%;" class="datatable" border="1px solid">
	<thead>
		<tr>
			<th>#</th>
			<th>Username</th>
			<th>User Role</th>
			<th>Rights</th>
			<th colspan="3">Action</th>
		</tr>
	</thead>
	<tbody>
		<s:set name="prevUserName" value=""></s:set>
		<s:set name="counter" value="0" />
		<s:iterator value="getUserRightsDetail" id="getUserRightsDetail"
			status="status">
			<s:if test="#prevUserName != userName">
				<s:set name="outerUserName" value="userName"></s:set>
				<s:set name="userCount" value="0"></s:set>
				<s:subset source="getUserRightsDetail" start="#status.index">
					<s:iterator status="innerStatus">
						<s:if test="#outerUserName == userName">
							<s:set name="userCount" value="#innerStatus.count"></s:set>
						</s:if>
					</s:iterator>
				</s:subset>
				<s:set name="counter" value="#counter+1"></s:set>
			</s:if>

			<tr class="even none">

				<s:if test="#prevUserName != userName">
					<td rowspan="<s:property value="#userCount"/>"><s:property
						value="#counter" /></td>
					<td rowspan="<s:property value="#userCount"/>"><s:property
						value="userName" /></td>
				</s:if>
				<td><s:property value="roleName" /></td>
				<td
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<s:property value="stageDesc" /></td>
				<td>
			<s:if test="#session.usertypename == 'WA'">
					<s:if test=" userFlag == 'N' && userTypeCode!='0002'">
					<a href="#" name="buttonName" title ="Delete Rights" id="delete"
							onclick="return deleteRights('<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />');">
							<img border="0px" alt="Delete Rights" src="images/Common/delete.svg" height="25px" width="25px">
					</a>
					</s:if>
					<s:else>-</s:else>
			</s:if>
			<s:else>-</s:else>
			</td>
				<td>
				
				<s:if test="#session.username == userName && userTypeCode=='0002'">
				-
				</s:if>
				<s:elseif test="userTypeCode=='0003' &&  userFlag == 'N'">
					<s:if test="#session.username == userName && #session.usertypename != 'WA'">
						
						<input type="image" title="Update User" alt="Submit" src="images/Common/update_user.svg" height="25px" width="25px"  style="margin-top: 2px; margin-bottom: 3px"
						onclick="return openlinkEdit('Change Rights','<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="userGroupCode" />','<s:property	value="nodeId" />','<s:property	value="roleCode" />');"  />
					</s:if>
					<s:elseif test="#session.usertypename == 'WA' && isVoidFlag==true">
						<input type="image" title="Update User" alt="Submit" src="images/Common/update_user.svg" height="25px" width="25px" style="margin-top: 2px; margin-bottom: 3px"
						onclick="return openlinkEdit('Change Rights','<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="userGroupCode" />','<s:property	value="nodeId" />','<s:property	value="roleCode" />');"  />
					</s:elseif>
					<s:else>-</s:else>
				</s:elseif>
				
				<s:else>-</s:else>
				</td>
				<td>		
					<s:if test="#session.usertypename == 'WA'">
						<input type="image" title="Update User Role" alt="Submit" src="images/Common/change_user_role.svg" height="25px" width="25px"  style="margin-top: 2px; margin-bottom: 3px"
						onclick="return updateUserRole('Update Rights','<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="userGroupCode" />','<s:property	value="nodeId" />','<s:property	value="roleCode" />');"  />
					</s:if>
					<s:else>-</s:else>
				</td>
			</tr>
			<s:set name="prevUserName" value="userName"></s:set>
		</s:iterator>
	</tbody>
</table>
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
				