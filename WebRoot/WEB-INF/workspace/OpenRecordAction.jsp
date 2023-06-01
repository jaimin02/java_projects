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
			var fileName=document.getElementById('docName').value;
	    	//var index=uploadFile.lastIndexOf('.');
	    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
	    	var fileext;
	    	var strChar;
	    	fileName = fileName.trim();
			if (fileName == "" || fileName == null){
				alert("Please enter Document name.");
				document.getElementById('docName').style.backgroundColor="#FFE6F7"; 
	      		return false; 
			}
	     	for (i = 0; i < fileName.length; i++)
	    	{
	 			strChar = fileName.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
			 		alert("Invalid file name. \n\nOnly alphabets, digits,dot,underscore and dash are allowed.");
		      		document.addUsertoProjectForm.fileName.style.backgroundColor="#FFE6F7"; 
		      		return false;  			
	 				}
			}
	     	var txtAttr = document.getElementsByClassName("txt");
	    	var dateAttr = document.getElementsByClassName("date");
	    	if(txtAttr.lengh != 0){
	    	for (var i = 0; i < txtAttr.length; i++) {
	    		 var attrVal = txtAttr[i].value;
	    		 attrVal= attrVal.trim();
	    		 if(attrVal==""){
	    			 alert("Please specify value.");
	    			 return false;
	    		 }
	    	 }
	    	if(dateAttr.lengh != 0){
	    		for (var i = 0; i < dateAttr.length; i++) {
	    			 var attrVal = dateAttr[i].value;
	    			 attrVal= attrVal.trim();
	    			 if(attrVal==""){
	    				 alert("Please specify value.");
	    				 return false;
	    			 }
	    		 }
	    	 }
	    	}
			return true;
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
<s:form action="AddRecord" method="post" id="addUsertoProjectForm" name="addUsertoProjectForm" onsubmit="return Filevalidate();">
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
											<td align="left"><textarea rows="3" cols="30" style="width:280px;"
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
							
</table>
<br/>

<s:submit cssClass="button" id="Save" value="Save" name="buttonName"></s:submit>
<input type="button" value="Close" class="button" onclick="window.location='ChangeRequest.do?docId=<s:property value="WorkspaceId"/>&docType=<s:property value="docType"/>'">
<s:hidden name="WorkspaceId"></s:hidden>
<s:hidden name="docType"></s:hidden>
</s:form>
<br/>
</div>
</div>
</div>
</body>
</html>
