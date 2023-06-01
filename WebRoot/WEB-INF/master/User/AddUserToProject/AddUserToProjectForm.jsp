<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>

<style type="text/css">

input[type="checkbox"][readonly] {
  pointer-events: none;
 }
/* #usertable_filter input {
background-color: #2e7eb9;
color:#ffffff;
}
#usertable_length select {
background-color: #2e7eb9;
color:#ffffff;
} */
.ms-options{
margin-left: 50% !important
}
</style>


<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
<%--  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.12.4.js"></script>
<script>
   var $j = jQuery.noConflict(true);
</script> 
<%-- <link href="<%=request.getContextPath()%>/css/fSelect.css" rel="stylesheet" type="text/css" media="screen" /> --%>
	
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>  --%>

<link href="<%=request.getContextPath()%>/css/jquery.multiselect.css" rel="stylesheet" type="text/css" media="screen" />


<s:head />

<script type="text/javascript">
//var userType;
	$(document).ready(function() { 
		//debugger;
		var date = (new Date().getFullYear()).toString();
		$("#copyright").text("\u00A9"+date +" Sarjen Systems Pvt. Ltd.");
		$("#remark").val("");
		$("#fromDt").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'd-M-yy'});
		$("#toDt").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'd-M-yy'});
		/* $("#fromDt").datepicker( "setDate" , new Date() );		
		var nextDate = new Date();
		nextDate.setYear(nextDate.getFullYear()+15);
		nextDate.setDate(nextDate.getDate());
		$( "#toDt" ).datepicker( "setDate", nextDate); */

		 $('#usertable').dataTable( {
				
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
				
		});
	
	});
$j(document).ready(function() { 
		
		/**
 * Display a nice easy to use multiselect list
 * @Version: 2.4.19
 * @Author: Patrick Springstubbe
 * @Contact: @JediNobleclem
 * @Website: springstubbe.us
 * @Source: https://github.com/nobleclem/jQuery-MultiSelect
 *
 * Usage:
 *     $j('select[multiple]').multiselect();
 *     $j('select[multiple]').multiselect({ texts: { placeholder: 'Select options' } });
 *     $j('select[multiple]').multiselect('reload');
 *     $j('select[multiple]').multiselect( 'loadOptions', [{
 *         name   : 'Option Name 1',
 *         value  : 'option-value-1',
 *         checked: false,
 *         attributes : {
 *             custom1: 'value1',
 *             custom2: 'value2'
 *         }
 *     },{
 *         name   : 'Option Name 2',
 *         value  : 'option-value-2',
 *         checked: false,
 *         attributes : {
 *             custom1: 'value1',
 *             custom2: 'value2'
 *         }
 *     }]);
 *
 **/

    var defaults = {
        columns: 1,     // how many columns should be use to show options
        search : false, // include option search box

        // search filter options
        searchOptions : {
            delay        : 250,                  // time (in ms) between keystrokes until search happens
            showOptGroups: false,                // show option group titles if no options remaining
            searchText   : true,                 // search within the text
            searchValue  : false,                // search within the value
            onSearch     : function( element ){} // fires on keyup before search on options happens
        },

        // plugin texts
        texts: {
            placeholder    : 'Select options', // text to use in dummy input
            search         : 'Search',         // search input placeholder text
            selectedOptions: ' selected',      // selected suffix text
            selectAll      : 'Select all',     // select all text
            unselectAll    : 'Unselect all',   // unselect all text
            noneSelected   : 'None Selected'   // None selected text
        },

        // general options
        selectAll          : false, // add select all option
        selectGroup        : false, // select entire optgroup
        minHeight          : null,   // minimum height of option overlay
        maxHeight          : null,  // maximum height of option overlay
        maxWidth           : 575,  // maximum width of option overlay (or selector)
        maxPlaceholderWidth: null,  // maximum width of placeholder button
        maxPlaceholderOpts : 10,    // maximum number of placeholder options to show until "# selected" shown instead
        showCheckbox       : true,  // display the checkbox to the user
        checkboxAutoFit    : false,  // auto calc checkbox padding
        optionAttributes   : [],    // attributes to copy to the checkbox from the option element

        // Callbacks
        onLoad        : function( element ){},           // fires at end of list initialization
        onOptionClick : function( element, option ){},   // fires when an option is clicked
        onControlClose: function( element ){},           // fires when the options list is closed
        onSelectAll   : function( element, selected ){}, // fires when (un)select all is clicked
        onPlaceholder : function( element, placeholder, selectedOpts ){}, // fires when the placeholder txt is updated
    };

    var msCounter    = 1; // counter for each select list
    var msOptCounter = 1; // counter for each option on page

    // FOR LEGACY BROWSERS (talking to you IE8)
    if( typeof Array.prototype.map !== 'function' ) {
        Array.prototype.map = function( callback, thisArg ) {
            if( typeof thisArg === 'undefined' ) {
                thisArg = this;
            }

            return $j.isArray( thisArg ) ? $j.map( thisArg, callback ) : [];
        };
    }
    if( typeof String.prototype.trim !== 'function' ) {
        String.prototype.trim = function() {
            return this.replace(/^\s+|\s+$/g, '');
        };
    }

    function MultiSelect( element, options )
    {
        this.element           = element;
        this.options           = $j.extend( true, {}, defaults, options );
        this.updateSelectAll   = true;
        this.updatePlaceholder = true;
        this.listNumber        = msCounter;

        msCounter = msCounter + 1; // increment counter

        /* Make sure its a multiselect list */
        if( !$j(this.element).attr('multiple') ) {
            throw new Error( '[jQuery-MultiSelect] Select list must be a multiselect list in order to use this plugin' );
        }

        /* Options validation checks */
        if( this.options.search ){
            if( !this.options.searchOptions.searchText && !this.options.searchOptions.searchValue ){
                throw new Error( '[jQuery-MultiSelect] Either searchText or searchValue should be true.' );
            }
        }

        /** BACKWARDS COMPATIBILITY **/
        if( 'placeholder' in this.options ) {
            this.options.texts.placeholder = this.options.placeholder;
            delete this.options.placeholder;
        }
        if( 'default' in this.options.searchOptions ) {
            this.options.texts.search = this.options.searchOptions['default'];
            delete this.options.searchOptions['default'];
        }
        /** END BACKWARDS COMPATIBILITY **/

        // load this instance
        this.load();
    }

    MultiSelect.prototype = {
        /* LOAD CUSTOM MULTISELECT DOM/ACTIONS */
        load: function() {
            var instance = this;

            // make sure this is a select list and not loaded
            if( (instance.element.nodeName != 'SELECT') || $j(instance.element).hasClass('jqmsLoaded') ) {
                return true;
            }

            // sanity check so we don't double load on a select element
            $j(instance.element).addClass('jqmsLoaded ms-list-'+ instance.listNumber ).data( 'plugin_multiselect-instance', instance );

            // add option container
            $j(instance.element).after('<div id="ms-list-'+ instance.listNumber +'" class="ms-options-wrap" style="width:200px;"><button type="button"><span>None Selected</span></button><div class="ms-options"><ul></ul></div></div>');

            var placeholder = $j(instance.element).siblings('#ms-list-'+ instance.listNumber +'.ms-options-wrap').find('> button:first-child');
            var optionsWrap = $j(instance.element).siblings('#ms-list-'+ instance.listNumber +'.ms-options-wrap').find('> .ms-options');
            var optionsList = optionsWrap.find('> ul');

            // don't show checkbox (add class for css to hide checkboxes)
            if( !instance.options.showCheckbox ) {
                optionsWrap.addClass('hide-checkbox');
            }
            else if( instance.options.checkboxAutoFit ) {
                optionsWrap.addClass('checkbox-autofit');
            }

            // check if list is disabled
            if( $j(instance.element).prop( 'disabled' ) ) {
                placeholder.prop( 'disabled', true );
            }

            // set placeholder maxWidth
            if( instance.options.maxPlaceholderWidth ) {
                placeholder.css( 'maxWidth', instance.options.maxPlaceholderWidth );
            }

            // override with user defined maxHeight
            if( instance.options.maxHeight ) {
                var maxHeight = instance.options.maxHeight;
            }
            else {
                // cacl default maxHeight
                var maxHeight = ($j(window).height() - optionsWrap.offset().top + $j(window).scrollTop() - 20);
            }

            // maxHeight cannot be less than options.minHeight
            maxHeight = maxHeight < instance.options.minHeight ? instance.options.minHeight : maxHeight;

            optionsWrap.css({
                maxWidth : instance.options.maxWidth,
                minHeight: instance.options.minHeight,
                maxHeight: maxHeight,
            });

            // isolate options scroll
            // @source: https://github.com/nobleclem/jQuery-IsolatedScroll
            optionsWrap.on( 'touchmove mousewheel DOMMouseScroll', function ( e ) {
                if( ($j(this).outerHeight() < $j(this)[0].scrollHeight) ) {
                    var e0 = e.originalEvent,
                        delta = e0.wheelDelta || -e0.detail;

                    if( ($j(this).outerHeight() + $j(this)[0].scrollTop) > $j(this)[0].scrollHeight ) {
                        e.preventDefault();
                        this.scrollTop += ( delta < 0 ? 1 : -1 );
                    }
                }
            });

            // hide options menus if click happens off of the list placeholder button
            $j(document).off('click.ms-hideopts').on('click.ms-hideopts', function( event ){
                if( !$j(event.target).closest('.ms-options-wrap').length ) {
                    $j('.ms-options-wrap.ms-active > .ms-options').each(function(){
                        $j(this).closest('.ms-options-wrap').removeClass('ms-active');

                        var listID = $j(this).closest('.ms-options-wrap').attr('id');

                        var thisInst = $j(this).parent().siblings('.'+ listID +'.jqmsLoaded').data('plugin_multiselect-instance');

                        // USER CALLBACK
                        if( typeof thisInst.options.onControlClose == 'function' ) {
                            thisInst.options.onControlClose( thisInst.element );
                        }
                    });
                }
            // hide open option lists if escape key pressed
            }).on('keydown', function( event ){
                if( (event.keyCode || event.which) == 27 ) { // esc key
                    $j(this).trigger('click.ms-hideopts');
                }
            });

            // handle pressing enter|space while tabbing through
            placeholder.on('keydown', function( event ){
                var code = (event.keyCode || event.which);
                if( (code == 13) || (code == 32) ) { // enter OR space
                    placeholder.trigger( 'mousedown' );
                }
            });

            // disable button action
            placeholder.on( 'mousedown', function( event ){
                // ignore if its not a left click
                if( event.which && (event.which != 1) ) {
                    return true;
                }

                // hide other menus before showing this one
                $j('.ms-options-wrap.ms-active').each(function(){
                    if( $j(this).siblings( '.'+ $j(this).attr('id') +'.jqmsLoaded')[0] != optionsWrap.parent().siblings('.ms-list-'+ instance.listNumber +'.jqmsLoaded')[0] ) {
                        $j(this).removeClass('ms-active');

                        var thisInst = $j(this).siblings( '.'+ $j(this).attr('id') +'.jqmsLoaded').data('plugin_multiselect-instance');

                        // USER CALLBACK
                        if( typeof thisInst.options.onControlClose == 'function' ) {
                            thisInst.options.onControlClose( thisInst.element );
                        }
                    }
                });

                // show/hide options
                optionsWrap.closest('.ms-options-wrap').toggleClass('ms-active');

                // recalculate height
                if( optionsWrap.closest('.ms-options-wrap').hasClass('ms-active') ) {
                    optionsWrap.css( 'maxHeight', '' );

                    // override with user defined maxHeight
                    if( instance.options.maxHeight ) {
                        var maxHeight = instance.options.maxHeight;
                    }
                    else {
                        // cacl default maxHeight
                        var maxHeight = ($j(window).height() - optionsWrap.offset().top + $j(window).scrollTop() - 20);
                    }

                    if( maxHeight ) {
                        // maxHeight cannot be less than options.minHeight
                        maxHeight = maxHeight < instance.options.minHeight ? instance.options.minHeight : maxHeight;

                        optionsWrap.css( 'maxHeight', 175 );
                    }
                    optionsWrap.css('overflow',auto);
                }
                else if( typeof instance.options.onControlClose == 'function' ) {
                    instance.options.onControlClose( instance.element );
                }
            }).click(function( event ){ event.preventDefault(); });

            // add placeholder copy
            if( instance.options.texts.placeholder ) {
                placeholder.find('span').text( instance.options.texts.placeholder );
            }

            // add search box
            if( instance.options.search ) {
                optionsList.before('<div class="ms-search"><input type="text" value="" placeholder="'+ instance.options.texts.search +'" /></div>');

                var search = optionsWrap.find('.ms-search input');
                search.on('keyup', function(){
                    // ignore keystrokes that don't make a difference
                    if( $j(this).data('lastsearch') == $j(this).val() ) {
                        return true;
                    }

                    // pause timeout
                    if( $j(this).data('searchTimeout') ) {
                        clearTimeout( $j(this).data('searchTimeout') );
                    }

                    var thisSearchElem = $j(this);

                    $j(this).data('searchTimeout', setTimeout(function(){
                        thisSearchElem.data('lastsearch', thisSearchElem.val() );

                        // USER CALLBACK
                        if( typeof instance.options.searchOptions.onSearch == 'function' ) {
                            instance.options.searchOptions.onSearch( instance.element );
                        }

                        // search non optgroup li's
                        var searchString = $j.trim( search.val().toLowerCase() );
                        if( searchString ) {
                            optionsList.find('li[data-search-term*="'+ searchString +'"]:not(.optgroup)').removeClass('ms-hidden');
                            optionsList.find('li:not([data-search-term*="'+ searchString +'"], .optgroup)').addClass('ms-hidden');
                        }
                        else {
                            optionsList.find('.ms-hidden').removeClass('ms-hidden');
                        }

                        // show/hide optgroups based on if there are items visible within
                        if( !instance.options.searchOptions.showOptGroups ) {
                            optionsList.find('.optgroup').each(function(){
                                if( $j(this).find('li:not(.ms-hidden)').length ) {
                                    $j(this).show();
                                }
                                else {
                                    $j(this).hide();
                                }
                            });
                        }

                        instance._updateSelectAllText();
                    }, instance.options.searchOptions.delay ));
                });
            }

            // add global select all options
            if( instance.options.selectAll ) {
                optionsList.before('<a href="#" class="ms-selectall global">' + instance.options.texts.selectAll + '</a>');
            }

            // handle select all option
            optionsWrap.on('click', '.ms-selectall', function( event ){
                event.preventDefault();

                instance.updateSelectAll   = false;
                instance.updatePlaceholder = false;

                var select = optionsWrap.parent().siblings('.ms-list-'+ instance.listNumber +'.jqmsLoaded');

                if( $j(this).hasClass('global') ) {
                    // check if any options are not selected if so then select them
                    if( optionsList.find('li:not(.optgroup, .selected, .ms-hidden) input[type="checkbox"]:not(:disabled)').length ) {
                        // get unselected vals, mark as selected, return val list
                        optionsList.find('li:not(.optgroup, .selected, .ms-hidden) input[type="checkbox"]:not(:disabled)').closest('li').addClass('selected');
                        optionsList.find('li.selected input[type="checkbox"]:not(:disabled)').prop( 'checked', true );
                    }
                    // deselect everything
                    else {
                        optionsList.find('li:not(.optgroup, .ms-hidden).selected input[type="checkbox"]:not(:disabled)').closest('li').removeClass('selected');
                        optionsList.find('li:not(.optgroup, .ms-hidden, .selected) input[type="checkbox"]:not(:disabled)').prop( 'checked', false );
                    }
                }
                else if( $j(this).closest('li').hasClass('optgroup') ) {
                    var optgroup = $j(this).closest('li.optgroup');

                    // check if any selected if so then select them
                    if( optgroup.find('li:not(.selected, .ms-hidden) input[type="checkbox"]:not(:disabled)').length ) {
                        optgroup.find('li:not(.selected, .ms-hidden) input[type="checkbox"]:not(:disabled)').closest('li').addClass('selected');
                        optgroup.find('li.selected input[type="checkbox"]:not(:disabled)').prop( 'checked', true );
                    }
                    // deselect everything
                    else {
                        optgroup.find('li:not(.ms-hidden).selected input[type="checkbox"]:not(:disabled)').closest('li').removeClass('selected');
                        optgroup.find('li:not(.ms-hidden, .selected) input[type="checkbox"]:not(:disabled)').prop( 'checked', false );
                    }
                }

                var vals = [];
                optionsList.find('li.selected input[type="checkbox"]').each(function(){
                    vals.push( $j(this).val() );
                });
                select.val( vals ).trigger('change');

                instance.updateSelectAll   = true;
                instance.updatePlaceholder = true;

                // USER CALLBACK
                if( typeof instance.options.onSelectAll == 'function' ) {
                    instance.options.onSelectAll( instance.element, vals.length );
                }

                instance._updateSelectAllText();
                instance._updatePlaceholderText();
            });

            // add options to wrapper
            var options = [];
            $j(instance.element).children().each(function(){
                if( this.nodeName == 'OPTGROUP' ) {
                    var groupOptions = [];

                    $j(this).children('option').each(function(){
                        var thisOptionAtts = {};
                        for( var i = 0; i < instance.options.optionAttributes.length; i++ ) {
                            var thisOptAttr = instance.options.optionAttributes[ i ];

                            if( $j(this).attr( thisOptAttr ) !== undefined ) {
                                thisOptionAtts[ thisOptAttr ] = $j(this).attr( thisOptAttr );
                            }
                        }

                        groupOptions.push({
                            name   : $j(this).text(),
                            value  : $j(this).val(),
                            checked: $j(this).prop( 'selected' ),
                            attributes: thisOptionAtts
                        });
                    });

                    options.push({
                        label  : $j(this).attr('label'),
                        options: groupOptions
                    });
                }
                else if( this.nodeName == 'OPTION' ) {
                    var thisOptionAtts = {};
                    for( var i = 0; i < instance.options.optionAttributes.length; i++ ) {
                        var thisOptAttr = instance.options.optionAttributes[ i ];

                        if( $j(this).attr( thisOptAttr ) !== undefined ) {
                            thisOptionAtts[ thisOptAttr ] = $j(this).attr( thisOptAttr );
                        }
                    }

                    options.push({
                        name      : $j(this).text(),
                        value     : $j(this).val(),
                        checked   : $j(this).prop( 'selected' ),
                        attributes: thisOptionAtts
                    });
                }
                else {
                    // bad option
                    return true;
                }
            });
            instance.loadOptions( options, true, false );

            // BIND SELECT ACTION
            optionsWrap.on( 'click', 'input[type="checkbox"]', function(){
                $j(this).closest( 'li' ).toggleClass( 'selected' );

                var select = optionsWrap.parent().siblings('.ms-list-'+ instance.listNumber +'.jqmsLoaded');

                // toggle clicked option
                select.find('option[value="'+ instance._escapeSelector( $j(this).val() ) +'"]').prop(
                    'selected', $j(this).is(':checked')
                ).closest('select').trigger('change');

                // USER CALLBACK
                if( typeof instance.options.onOptionClick == 'function' ) {
                    instance.options.onOptionClick(instance.element, this);
                }

                instance._updateSelectAllText();
                instance._updatePlaceholderText();
            });

            // BIND FOCUS EVENT
            optionsWrap.on('focusin', 'input[type="checkbox"]', function(){
                $j(this).closest('label').addClass('focused');
            }).on('focusout', 'input[type="checkbox"]', function(){
                $j(this).closest('label').removeClass('focused');
            });

            // USER CALLBACK
            if( typeof instance.options.onLoad === 'function' ) {
                instance.options.onLoad( instance.element );
            }

            // hide native select list
            $j(instance.element).hide();
        },

        /* LOAD SELECT OPTIONS */
        loadOptions: function( options, overwrite, updateSelect ) {
            overwrite    = (typeof overwrite == 'boolean') ? overwrite : true;
            updateSelect = (typeof updateSelect == 'boolean') ? updateSelect : true;

            var instance    = this;
            var select      = $j(instance.element);
            var optionsList = select.siblings('#ms-list-'+ instance.listNumber +'.ms-options-wrap').find('> .ms-options > ul');
            var optionsWrap = select.siblings('#ms-list-'+ instance.listNumber +'.ms-options-wrap').find('> .ms-options');

            if( overwrite ) {
                optionsList.find('> li').remove();

                if( updateSelect ) {
                    select.find('> *').remove();
                }
            }

            var containers = [];
            for( var key in options ) {
                // Prevent prototype methods injected into options from being iterated over.
                if( !options.hasOwnProperty( key ) ) {
                    continue;
                }

                var thisOption      = options[ key ];
                var container       = $j('<li/>');
                var appendContainer = true;

                // OPTION
                if( thisOption.hasOwnProperty('value') ) {
                    if( instance.options.showCheckbox && instance.options.checkboxAutoFit ) {
                        container.addClass('ms-reflow');
                    }

                    // add option to ms dropdown
                    instance._addOption( container, thisOption );

                    if( updateSelect ) {
                        var selOption = $j('<option/>', {
                            value: thisOption.value,
                            text : thisOption.name
                        });

                        // add custom user attributes
                        if( thisOption.hasOwnProperty('attributes') && Object.keys( thisOption.attributes ).length ) {
                            selOption.attr( thisOption.attributes );
                        }

                        // mark option as selected
                        if( thisOption.checked ) {
                            selOption.prop( 'selected', true );
                        }

                        select.append( selOption );
                    }
                }
                // OPTGROUP
                else if( thisOption.hasOwnProperty('options') ) {
                    var optGroup = $j('<optgroup/>', {
                        label: thisOption.label
                    });

                    optionsList.find('> li.optgroup > span.label').each(function(){
                        if( $j(this).text() == thisOption.label ) {
                            container       = $j(this).closest('.optgroup');
                            appendContainer = false;
                        }
                    });

                    // prepare to append optgroup to select element
                    if( updateSelect ) {
                        if( select.find('optgroup[label="'+ thisOption.label +'"]').length ) {
                            optGroup = select.find('optgroup[label="'+ thisOption.label +'"]');
                        }
                        else {
                            select.append( optGroup );
                        }
                    }

                    // setup container
                    if( appendContainer ) {
                        container.addClass('optgroup');
                        container.append('<span class="label">'+ thisOption.label +'</span>');
                        container.find('> .label').css({
                            clear: 'both'
                        });

                        // add select all link
                        if( instance.options.selectGroup ) {
                            container.append('<a href="#" class="ms-selectall">' + instance.options.texts.selectAll + '</a>');
                        }

                        container.append('<ul/>');
                    }

                    for( var gKey in thisOption.options ) {
                        // Prevent prototype methods injected into options from
                        // being iterated over.
                        if( !thisOption.options.hasOwnProperty( gKey ) ) {
                            continue;
                        }

                        var thisGOption = thisOption.options[ gKey ];
                        var gContainer  = $j('<li/>');
                        if( instance.options.showCheckbox && instance.options.checkboxAutoFit ) {
                            gContainer.addClass('ms-reflow');
                        }

                        // no clue what this is we hit (skip)
                        if( !thisGOption.hasOwnProperty('value') ) {
                            continue;
                        }

                        instance._addOption( gContainer, thisGOption );

                        container.find('> ul').append( gContainer );

                        // add option to optgroup in select element
                        if( updateSelect ) {
                            var selOption = $j('<option/>', {
                                value: thisGOption.value,
                                text : thisGOption.name
                            });

                            // add custom user attributes
                            if( thisGOption.hasOwnProperty('attributes') && Object.keys( thisGOption.attributes ).length ) {
                                selOption.attr( thisGOption.attributes );
                            }

                            // mark option as selected
                            if( thisGOption.checked ) {
                                selOption.prop( 'selected', true );
                            }

                            optGroup.append( selOption );
                        }
                    }
                }
                else {
                    // no clue what this is we hit (skip)
                    continue;
                }

                if( appendContainer ) {
                    containers.push( container );
                }
            }
            optionsList.append( containers );

            // pad out label for room for the checkbox
            if( instance.options.checkboxAutoFit && instance.options.showCheckbox && !optionsWrap.hasClass('hide-checkbox') ) {
                var chkbx = optionsList.find('.ms-reflow:eq(0) input[type="checkbox"]');
                if( chkbx.length ) {
                    var checkboxWidth = chkbx.outerWidth();
                        checkboxWidth = checkboxWidth ? checkboxWidth : 15;

                    optionsList.find('.ms-reflow label').css(
                        'padding-left',
                        (parseInt( chkbx.closest('label').css('padding-left') ) * 2) + checkboxWidth
                    );

                    optionsList.find('.ms-reflow').removeClass('ms-reflow');
                }
            }

            // update placeholder text
            instance._updatePlaceholderText();

            // RESET COLUMN STYLES
            optionsWrap.find('ul').css({
                'column-count'        : '',
                'column-gap'          : '',
                '-webkit-column-count': '',
                '-webkit-column-gap'  : '',
                '-moz-column-count'   : '',
                '-moz-column-gap'     : ''
            });

            // COLUMNIZE
            if( select.find('optgroup').length ) {
                // float non grouped options
                optionsList.find('> li:not(.optgroup)').css({
                    'float': 'left',
                    width: (100 / instance.options.columns) +'%'
                });

                // add CSS3 column styles
                optionsList.find('li.optgroup').css({
                    clear: 'both'
                }).find('> ul').css({
                    'column-count'        : instance.options.columns,
                    'column-gap'          : 0,
                    '-webkit-column-count': instance.options.columns,
                    '-webkit-column-gap'  : 0,
                    '-moz-column-count'   : instance.options.columns,
                    '-moz-column-gap'     : 0
                });

                // for crappy IE versions float grouped options
                if( this._ieVersion() && (this._ieVersion() < 10) ) {
                    optionsList.find('li.optgroup > ul > li').css({
                        'float': 'left',
                        width: (100 / instance.options.columns) +'%'
                    });
                }
            }
            else {
                // add CSS3 column styles
                optionsList.css({
                    'column-count'        : instance.options.columns,
                    'column-gap'          : 0,
                    '-webkit-column-count': instance.options.columns,
                    '-webkit-column-gap'  : 0,
                    '-moz-column-count'   : instance.options.columns,
                    '-moz-column-gap'     : 0
                });

                // for crappy IE versions float grouped options
                if( this._ieVersion() && (this._ieVersion() < 10) ) {
                    optionsList.find('> li').css({
                        'float': 'left',
                        width: (100 / instance.options.columns) +'%'
                    });
                }
            }

            // update un/select all logic
            instance._updateSelectAllText();
        },

        /* UPDATE MULTISELECT CONFIG OPTIONS */
        settings: function( options ) {
            this.options = $j.extend( true, {}, this.options, options );
            this.reload();
        },

        /* RESET THE DOM */
        unload: function() {
            $j(this.element).siblings('#ms-list-'+ this.listNumber +'.ms-options-wrap').remove();
            $j(this.element).show(function(){
                $j(this).css('display','').removeClass('jqmsLoaded');
            });
        },

        /* RELOAD JQ MULTISELECT LIST */
        reload: function() {
            // remove existing options
            $j(this.element).siblings('#ms-list-'+ this.listNumber +'.ms-options-wrap').remove();
            $j(this.element).removeClass('jqmsLoaded');

            // load element
            this.load();
        },

        // RESET BACK TO DEFAULT VALUES & RELOAD
        reset: function() {
            var defaultVals = [];
            $j(this.element).find('option').each(function(){
                if( $j(this).prop('defaultSelected') ) {
                    defaultVals.push( $j(this).val() );
                }
            });

            $j(this.element).val( defaultVals );

            this.reload();
        },

        disable: function( status ) {
            status = (typeof status === 'boolean') ? status : true;
            $j(this.element).prop( 'disabled', status );
            $j(this.element).siblings('#ms-list-'+ this.listNumber +'.ms-options-wrap').find('button:first-child')
                .prop( 'disabled', status );
        },

        /** PRIVATE FUNCTIONS **/
        // update the un/select all texts based on selected options and visibility
        _updateSelectAllText: function(){
            if( !this.updateSelectAll ) {
                return;
            }

            var instance = this;

            // select all not used at all so just do nothing
            if( !instance.options.selectAll && !instance.options.selectGroup ) {
                return;
            }

            var optionsWrap = $j(instance.element).siblings('#ms-list-'+ instance.listNumber +'.ms-options-wrap').find('> .ms-options');

            // update un/select all text
            optionsWrap.find('.ms-selectall').each(function(){
                var unselected = $j(this).parent().find('li:not(.optgroup,.selected,.ms-hidden)');

                $j(this).text(
                    unselected.length ? instance.options.texts.selectAll : instance.options.texts.unselectAll
                );
            });
        },

        // update selected placeholder text
        _updatePlaceholderText: function(){
            if( !this.updatePlaceholder ) {
                return;
            }

            var instance       = this;
            var select         = $j(instance.element);
            var selectVals     = select.val() ? select.val() : [];
            var placeholder    = select.siblings('#ms-list-'+ instance.listNumber +'.ms-options-wrap').find('> button:first-child');
            var placeholderTxt = placeholder.find('span');
            var optionsWrap    = select.siblings('#ms-list-'+ instance.listNumber +'.ms-options-wrap').find('> .ms-options');

            // if there are disabled options get those values as well
            if( select.find('option:selected:disabled').length ) {
                selectVals = [];
                select.find('option:selected').each(function(){
                    selectVals.push( $j(this).val() );
                });
            }

            // get selected options
            var selOpts = [];
            for( var key in selectVals ) {
                // Prevent prototype methods injected into options from being iterated over.
                if( !selectVals.hasOwnProperty( key ) ) {
                    continue;
                }

                selOpts.push(
                    $j.trim( select.find('option[value="'+ instance._escapeSelector( selectVals[ key ] ) +'"]').text() )
                );

                if( selOpts.length >= instance.options.maxPlaceholderOpts ) {
                    break;
                }
            }

            // UPDATE PLACEHOLDER TEXT WITH OPTIONS SELECTED
            placeholderTxt.text( selOpts.join( ', ' ) );

            if( selOpts.length ) {
                optionsWrap.closest('.ms-options-wrap').addClass('ms-has-selections');

                // USER CALLBACK
                if( typeof instance.options.onPlaceholder == 'function' ) {
                    instance.options.onPlaceholder( instance.element, placeholderTxt, selOpts );
                }
            }
            else {
                optionsWrap.closest('.ms-options-wrap').removeClass('ms-has-selections');
            }

            // replace placeholder text
            if( !selOpts.length ) {
                placeholderTxt.text( instance.options.texts.placeholder );
            }
            // if copy is larger than button width use "# selected"
            else if( (placeholderTxt.width() > placeholder.width()) || (selOpts.length != selectVals.length) ) {
                placeholderTxt.text( selectVals.length + instance.options.texts.selectedOptions );
            }
        },

        // Add option to the custom dom list
        _addOption: function( container, option ) {
            var instance = this;
            var optionNameText = $j('<div/>').html( option.name ).text();

            var thisOption = $j('<label/>', {
                for : 'ms-opt-'+ msOptCounter
            }).html( option.name );

            var thisCheckbox = $j('<input>', {
                type : 'checkbox',
                title: optionNameText,
                id   : 'ms-opt-'+ msOptCounter,
                value: option.value
            });

            // add user defined attributes
            if( option.hasOwnProperty('attributes') && Object.keys( option.attributes ).length ) {
                thisCheckbox.attr( option.attributes );
            }

            if( option.checked ) {
                container.addClass('default selected');
                thisCheckbox.prop( 'checked', true );
            }

            thisOption.prepend( thisCheckbox );

            var searchTerm = '';
            if( instance.options.searchOptions.searchText ) {
                searchTerm += ' ' + optionNameText.toLowerCase();
            }
            if( instance.options.searchOptions.searchValue ) {
                searchTerm += ' ' + option.value.toLowerCase();
            }

            container.attr( 'data-search-term', $j.trim( searchTerm ) ).prepend( thisOption );

            msOptCounter = msOptCounter + 1;
        },

        // check ie version
        _ieVersion: function() {
            var myNav = navigator.userAgent.toLowerCase();
            return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
        },

        // escape selector
        _escapeSelector: function( string ) {
            if( typeof $j.escapeSelector == 'function' ) {
                return $j.escapeSelector( string );
            }
            else {
                return string.replace(/[!"#$j%&'()*+,.\/:;<=>?@[\\\]^`{|}~]/g, "\\$&");
            }
        }
    };

    // ENABLE JQUERY PLUGIN FUNCTION
    $j.fn.multiselect = function( options ){
        if( !this.length ) {
            return;
        }

        var args = arguments;
        var ret;

        // menuize each list
        if( (options === undefined) || (typeof options === 'object') ) {
            return this.each(function(){
                if( !$j.data( this, 'plugin_multiselect' ) ) {
                    $j.data( this, 'plugin_multiselect', new MultiSelect( this, options ) );
                }
            });
        } else if( (typeof options === 'string') && (options[0] !== '_') && (options !== 'init') ) {
            this.each(function(){
                var instance = $j.data( this, 'plugin_multiselect' );

                if( instance instanceof MultiSelect && typeof instance[ options ] === 'function' ) {
                    ret = instance[ options ].apply( instance, Array.prototype.slice.call( args, 1 ) );
                }

                // special destruct handler
                if( options === 'unload' ) {
                    $j.data( this, 'plugin_multiselect', null );
                }
            });

            return ret;
        }
    };

	});
	
		/*  function getUsers() {
		  var userGroupId = document.getElementById("userWiseGroupCode").value;
			//debugger;
		  $.ajax(
					{			
						url: 'showGroupWiseUserDtl.do?userGroupId=' + userGroupId,
						beforeSend: function()
						{
							;
							$('#showUserDtl').html('');								
						},
						success: function(data) 
				  		{
							$('#showUserDtl').html(data);
							window.fs_test = $('#showUserDtl #userCode').fSelect();
							//$( ".fs-wrap" ).remove()
						}	  				    		
					});
		} */
		
		 function getUsers() {
			  
			  debugger;
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
  			 var ele=document.getElementsByName('stageIds');
	         if(userType=='WU'){
	        	document.getElementById("modulespecrights").checked = true;
	        	$("#hidestage").hide();
	        	for(var i=0; i<ele.length; i++){  
	                  if(ele[i].type=='checkbox')  
	                      ele[i].checked=false;  
	              }
			}
	         else{
	        	 document.getElementById("modulespecrights").checked = false;
	        	 //$("#hidestage").show();
	        	  
	                for(var i=0; i<ele.length; i++){  
	                    if(ele[i].type=='checkbox')  
	                        ele[i].checked=true;  
	                }  
	         }

	         var wsId=doc=document.getElementById("wsId").value
			 $j.ajax({			
						url: 'showUserGroupDtl_ex.do?userGroupId=' + userGroupId+'&workspaceId='+wsId,
						beforeSend: function()
						{
			  			 opt = "No Users available";		
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
				  				  '</select> &nbsp;';
					    		   document.getElementById('showUserDtl').innerHTML = opt;
					    		   
					    		   window.fs_test= $j('.temp').multiselect({
				    		            columns: 4,
				    		            placeholder: 'Select User',
				    		            search: true,
				    		            searchOptions: {
				    		                'default': 'Search User'
				    		            },
				    		            selectAll: true
				    					
				    		        });
					    			//window.fs_test=$j('.3col active').fSelect();
							    	
					    			//window.fs_test = $j('.commentUsers').fSelect();
							}	
					  	}
			});
		}
		function getUserGroups() {
			  
			  debugger;
			  //var selectevalue = document.getElementById("userGroupYN").value;
			  var selectevalue = document.addUsertoProjectForm.userGroupYN.value;
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
			var ele=document.getElementsByName('stageIds');  
	      if(userType=='WU'){
	        	document.getElementById("modulespecrights").checked = true;
	        	 $("#hidestage").hide();
	        	 for(var i=0; i<ele.length; i++){  
	                  if(ele[i].type=='checkbox')  
	                      ele[i].checked=false;  
	              }
			}
	      else{
	    	  document.getElementById("modulespecrights").checked = false;
	    	  //$("#hidestage").show();
	    	  
              for(var i=0; i<ele.length; i++){  
                  if(ele[i].type=='checkbox')  
                      ele[i].checked=true;  
              }
	      }
		}
		function validate()
		{
			debugger;
			var unlimitedrights= $('#unlimited:checked').val();
			//var unlimitedrights= 1;
			var remark = document.addUsertoProjectForm.remark.value;
			
			/* if(document.addUsertoProjectForm.userGroupYN.value == -1){
			
				alert("Please select User/UserGroupName");
				document.addUsertoProjectForm.userGroupYN.style.backgroundColor="#FFE6F7"; 
	     		document.addUsertoProjectForm.userGroupYN.focus();
	     		return false;
     		} */
			if(document.addUsertoProjectForm.userGroupYN.value == ""){
				
				alert("Please select User/UserProfile");
	     		return false;
     		}
     		else if (document.addUsertoProjectForm.userGroupYN.value == 'user')
     		{
     			if(document.addUsertoProjectForm.userWiseGroupCode.value==-1)
     			{
     				alert("Please select User Profile");
					document.addUsertoProjectForm.userWiseGroupCode.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoProjectForm.userWiseGroupCode.focus();
     				return false;
     			}
     			if(document.addUsertoProjectForm.userCode.value=='')
     			{
     				alert("Please select User Name");
					document.addUsertoProjectForm.userCode.focus();
     				return false;
     			
     			}
     			if(document.addUsertoProjectForm.userCode.value==-1)
     			{
     				alert("Please select User Name");
					document.addUsertoProjectForm.userCode.focus();
     				return false;
     			
     			}
     			//When no user is avae
     			if($('#showUserDtl').children().length<=0){
    				alert("User is not found in selected profile");
    				return false;
    			}
     		}
     		else if (document.addUsertoProjectForm.userGroupYN.value == 'usergroup')
     		{
     			if(document.addUsertoProjectForm.userGroupCode.value==-1)
     			{
     				alert("Please select User Profile");
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

     		/* if (!$("#modulespecrights").is(':checked')) {
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
				alert("Please select stage(s)...");
				return false;
			}
     	} */
      if (remark == "" || remark == null ){
     	alert("Please specify remark.");
		document.addUsertoProjectForm.remark.style.backgroundColor="#FFE6F7"; 
	    document.addUsertoProjectForm.remark.focus();
 		return false;
     }
     	
	return true;
	}
		
 		function showcombobox()
	    {
	    	debugger;
	    	$("#remrk").show();
	    	var selectcombo=document.addUsertoProjectForm.userGroupYN.value;
	    	var usertab=document.getElementById('showUserDtl').innerHTML='';
	    	
	    	
	    	if(selectcombo=="user")
		    {
		    	
		    	document.getElementById('usergroupcombobox').style.display='none';
	    		document.getElementById('usercombobox').style.display='contents';
	    		document.getElementById('unametd').style.display='';
	    		
	    		$('#userWiseGroupCode').val('3');
	    		getUsers();

	    	}
	    	else if(selectcombo=="usergroup")
	    	{
	    		
	    		document.getElementById('usergroupcombobox').style.display='contents';
	    		document.getElementById('usercombobox').style.display='none';
	    		document.getElementById('unametd').style.display='none';
	    	}
	    	
	    }
	    
	    function openlinkEdit(workSpaceId,userCode,userGroupCode)
	    {
			 	str="EditWorkspaceUser.do?workSpaceId="+workSpaceId+"&userId="+userCode+"&userGroupId="+userGroupCode;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=550,resizable=no,titlebar=no")	
				win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(400/2));
				return true;
		}
		function openlinkView(workSpaceId,userId)
		{
			
			 	str="ViewWorkspaceUserRights.do?workSpaceId="+workSpaceId+"&userCode="+userId;
				win3 =window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=700,width=1000,resizable=no,titlebar=no top=100")	
				win3.moveTo(screen.width/2-(1000/2),screen.height/2-(750/2));
				return true;
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
	/* 	$('#unlimited').click(function() {
			alert("Hi");
		    if ($(this).is(':checked')) {
		       alert("checked");
		    } else {
		      alert("unchecked");
		    }
		}); */
		
		
		function deleteWorkspceUser(wsId,userCode,userGroupCode){
			//debugger;
			var remark = prompt("Please specify Reason for Change.");
			remark = remark.trim();
			if (remark != null && remark != ""){
			 	var url = "deleteWorkspaceUser.do?workspaceid=" + wsId + "&userId=" + userCode +"&userGroupId="+ userGroupCode + "&remark=" + remark;
				location.href = url;	
			}
			else if(remark==""){
				//debugger;
				alert("Please specify Reason for Change.");
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
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1500,resizable=no,titlebar=no");
			 	win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
				return true;
			}
		}
		
		function deletedWorkspaceUserHistory(wsId)
		{
		 	//debugger;
			if (wsId == null || wsId=='' )
			{
				alert("Please Select Project");
				return false;
			}else{	
				
				str="showDeletedWorkspaceUserDetail_b.do?workspaceid=" + wsId ;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1500,resizable=no,titlebar=no");
			 	win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
				return true;
			}
		}
		
		function allWorkspaceUserHistory(wsId)
		{
		 	//debugger;
			if (wsId == null || wsId=='' )
			{
				alert("Please Select Project");
				return false;
			}else{	
				
				str="showAllWorkspaceUserDetail_b.do?workspaceid=" + wsId ;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1500,resizable=no,titlebar=no");
			 	win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
				return true;
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
		
</script>

</head>
<body>

<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>
<div align="center" style="color: green;"><s:actionmessage /></div>
<br />
<div class="container-fluid">
<div class="col-md-12">
<div class="boxborder"><div class="all_title"><b style="float:left">User Allocation</b></div>
<!-- <div align="center"><img
	src="images/Header_Images/User/Attach_User_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->

<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="AddUserToProject" method="post" name="addUsertoProjectForm">

	<s:hidden name="workspaceid"></s:hidden>
<div id="imp" style="float: right; margin-right: 10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
	<table width="100%">
	<tr>
			<td class="title" align="right" width="50%"	style="padding: 2px; padding-right: 8px;">
				<input type="radio" name="userGroupYN" id="userGroupYN"
				onchange="return showcombobox();" value="user" />
				<span class="title">User</span>
			</td>
			<td><input type="radio" name="userGroupYN" id="userGroupYN"
				onchange="return showcombobox();" value="usergroup" />
			<span class="title">User Profile</span>&nbsp;</td>
		<%-- <tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">User/Group</td>
			<td align="left"><select name="userGroupYN" id="userGroupYN"
				onchange="return showcombobox();">
				<option value="-1">Select User or UserGroup</option>
				<option value="user">User</option>
				<option value="usergroup">User Group</option>
			</select>&nbsp; <span style="font-size:20px;color:red">*</span></td>
		</tr> --%>
		<tr id="usergroupcombobox" style="display: none;">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">User Profile
				<span style="font-size:20px;color:red">*</span> </td>
			<td align="left"><s:select list="workspaceUserGroupsDetails"
				name="userGroupCode" headerKey="-1"
				headerValue="Select User Profile" listKey="userGroupCode"
				listValue="userGroupName" theme="simple" onchange="getUserGroups();">
			</s:select></td>
		</tr>
		<tr id="usercombobox" style="display: none;">
		<input id="wsId" name="workspaceId" type="hidden" value='<s:property value="workspaceid" />'>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">User Profile
				<span style="font-size:20px;color:red">*</span> </td>
			<td align="left">			
			 <s:select list="workspaceUserGroupsDetails"
				name="userWiseGroupCode" id="userWiseGroupCode" headerKey="-1"
				headerValue="Select User Profile" listKey="userGroupCode"
				listValue="userGroupName" theme="simple" onchange="getUsers();">
			</s:select>
			<%-- <ajax:event ajaxRef="addUsertoProject/getUserDtl" /> --%></td>
		</tr>

		<tr>
			<td id="unametd" align="right" style="padding: 2px; padding-right: 8px;display: none;" class="title" align="right"
				width="25%">User<span style="font-size:20px;color:red">*</span> </td>
			<td align="left">
				<div id="showUserDtl" style="padding-top:17px;"></div>
			</td>
		</tr>
		 <tr style="display:none">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Unlimited Rights</td>
			<td align="left"><input type="checkbox" name="unlimited" checked
				 id="unlimited" value="1" onclick="return HideDateSelection();"/></td>
		</tr> 
		
		<tr style="display:none">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Module Specific Rights</td>
			
			<td align="left"id="tmp1"><input type="checkbox" name="modulespecrights"
				 id="modulespecrights" value="Y" onclick="return HideStageSelection();"/></td>
				 
			<!-- <td align="left" id="tmp2" style="display:none;"><input type="checkbox" checked="checked" readonly 
			 	name="modulespecrights" id="modulespecrights" value="Y" onclick="return HideStageSelection();"/></td> -->
		</tr> 
		
		<tr id="frmdate" style="display:none;">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">From Date</td>
			<td align="left"><input type="text" name="fromDt"
				readonly="readonly" id="fromDt"></td>
		</tr>

		<tr id="todate" style="display:none;">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">To Date</td>
			<td align="left"><input type="text" name="toDt"
				readonly="readonly" id="toDt"></td>
		</tr> 


		<tr id="hidestage" style="display:none">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Stages</td>
			<%-- <td align="left"><s:iterator value="getStageDetail">
				<div><input type="checkbox" name="stageIds"
					id="stages_<s:property value = "stageId"/>"
					value="<s:property value="stageId"/>" onclick="return checkRights(this)"> <LABEL
					for="stages_<s:property value = "stageId"/>"><s:property
					value="StageDesc" /></LABEL></div>
			</s:iterator></td> --%>
			<td align="left"><s:iterator value="getStageDetail">
				<div><input type="checkbox" name="stageIds"
					id="stages_<s:property value = "stageId"/>"
					value="<s:property value="stageId"/>"> <LABEL
					for="stages_<s:property value = "stageId"/>"><s:property
					value="StageDesc" /></LABEL></div>
			</s:iterator></td>
		</tr>


		<tr id="remrk" style="display:none">
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Remark
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left">
			<%-- <s:textfield name="remark" id="remark" size="50"></s:textfield> --%>
			<TEXTAREA rows="3" style="width: 200px;" name="remark"></TEXTAREA>			
			</td>
		</tr>
		<tr><td></td></tr>
		<tr>
			<td colspan="2" align="center"><s:submit value="Assign User" cssClass="button"
				onclick="return validate();" /> <input type="button" class="button"
				value="Back"
				onclick="window.location='AddUsertoProjectSummary.do';"></td>
		</tr>
	</table>

	<ajax:enable />
</s:form> <br>

<div align="Right">
	<input type="button" class="button" value="User rights removal history" style="margin-right: 10px; margin-bottom: 10px;"
                          onclick="return deletedWorkspaceUserHistory('<s:property value="workspaceid"/>');">
	<input type="button" class="button" value="Audit Trial" style="margin-right: 10px; margin-bottom: 10px;"
                          onclick="return allWorkspaceUserHistory('<s:property value="workspaceid"/>');">
</div>
                          
<div class="datatablePadding" id="userforprojectlist" align="center">
<%int srNo = 1; %>
<table id="usertable" width="100%" align="center" class="datatable paddingtable">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Project</th>
			<th style="border: 1px solid black;">User Profile</th>
			<th style="border: 1px solid black;">Username</th>
			<th style="border: 1px solid black;">User Role</th>
	<%-- 		<th style="border: 1px solid black;">From date</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>
			<th style="border: 1px solid black;">To Date</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if> --%>
			<th style="border: 1px solid black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>
			<th style="border: 1px solid black;">Rights Given by</th>
			<th colspan="3" style="border: 1px solid black;">Actions</th>
			
			<!-- <th style="border: 1px solid black;">Edit</th>
			<th style="border: 1px solid black;">Remove</th>
			<th style="border: 1px solid black;">Audit Trail</th> -->
			<th style="border: 1px solid black;">Rights Type</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="WorkspaceUserDetailList" status="status">

			<tr class="<s:if test="#status.even">two</s:if><s:else>one</s:else>">

				<td style="border: 1px solid black;"><%=srNo++ %></td>
				<td style="border: 1px solid black;"><s:property value="workspacedesc" /></td>
				<td style="border: 1px solid black;"><s:property value="userGroupName" /></td>
				<td style="border: 1px solid black;"><s:property value="username" /></td>
				<td style="border: 1px solid black;"><s:property value="roleName" /></td>
				<%--  <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> --%>
				<%-- <td style="border: 1px solid black;"><s:property value="FromISTDate" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="FromESTDate" /></td>
			</s:if>
				 <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td>
				<td style="border: 1px solid black;"><s:property value="ToISTDate" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="ToESTDate" /></td>
			</s:if> --%>
				<%--  <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> --%>
				<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
			</s:if>
				<td style="border: 1px solid black;"><s:property value="rightsGivenBy" /></td>
				
				<td style="border: 1px solid black;">
				<s:if test="statusIndi != 'D' && (#session.usertypename != 'WA' || userTypeName != 'WA')">
					<div><a title="Edit" href="#" onclick="openlinkEdit('<s:property value="workSpaceId"/>',<s:property value="userCode"/>,<s:property value="userGroupCode"/>)">
					<img border="0px" alt="Edit" src="images/Common/edit.svg"
						height="25px" width="25px"> </a></div>
				</s:if> <s:else>
					 <img border="0px"
						alt="Edit" src="images/Common/edit.svg" height="25px" width="25px">
				</s:else></td>

				<td style="border: 1px solid black;">
				<s:if test="userCode != lastAdminCode && userFlag == 'N' ">
					<s:if test="statusIndi != 'D'">
						<div ><a title="Remove" href="#" onclick="deleteWorkspceUser('<s:property value="workSpaceId"/>',
						                         '<s:property value="userCode"/>','<s:property value="userGroupCode"/>');">
							<!-- href="deleteWorkspaceUser.do?workspaceid=<s:property value="workSpaceId"/>&userId=<s:property value="userCode"/>&userGroupId=<s:property value="userGroupCode"/>">-->
						<img border="0px" alt="Delete" src="images/Common/delete.svg"
							height="25px" width="25px"> </a></div>
					</s:if>
				</s:if>
				<s:else><img border="0px" alt="Delete" src="images/Common/delete.svg"height="25px" width="25px">
				</s:else>
				</td>
				<td style="border: 1px solid black;">
					<div><a title="Audit Trail" onclick="workspaceUserHistory('<s:property value="workSpaceId"/>','<s:property value="userCode"/>','<s:property value="userGroupCode"/>');"
							href="#"><%-- href="showWorkspaceUserDetail.do?workspaceid=<s:property value="workSpaceId"/>&userId=<s:property value="userCode"/>&userGroupId=<s:property value="userGroupCode"/>"> --%>
						<img border="0px" alt="Audit Trail" src="images/Common/details.svg"
							height="25px" width="25px"> </a></div>
				</td>
				<td style="border: 1px solid black;"><s:if test="%{RightsType=='modulewise'}">Section Wise</s:if><s:else>Project Wise </s:else></td>
			</tr>
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
