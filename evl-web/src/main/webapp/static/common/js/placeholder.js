$.fn.placeholder = function(option, callback) {
	    var settings = $.extend({
	        word: '',
	        color: '#ccc',
	        evtType: 'focus',
	        unEdit:'0',
	        edit:'1'
	    }, option)
	 
	    function bootstrap($that) {
	        // some alias
	        var word    = settings.word
	        var color   = settings.color
	        var evtType = settings.evtType
	        var unEdit  = settings.unEdit
	        var edit    = settings.edit
	        // default
	        var defColor = $that.css('color')
	        var defVal   = $that.val()
	 
	        if (defVal == '' || defVal == word) {
	            $that.css({color: color}).val(word)
	            $that.attr("data-flag",unEdit)
	        } else {
	            $that.css({color: "#4a4a4a"})
	        }
	 
	        function switchStatus(isDef) {
	            if (isDef) {
	                $that.val('').css({color: "#4a4a4a"})
	                $that.attr("data-flag",edit)
	            } else {
	                $that.val(word).css({color: color});
	                $that.attr("data-flag",unEdit);
	            }
	        }
	        function asFocus() {
	            $that.bind(evtType, function() {
	                var txt = $that.val()
	                if (txt == word) {
	                    switchStatus(true)
	                }
	            }).bind('blur', function() {
	                var txt = $that.val();
	                if (txt == '' || txt == word) {
	                    switchStatus(false)
	                }
	            })
	        }
	        function asKeydown() {
	            $that.bind('focus', function() {
	                var elem = $that[0]
	                var val  = $that.val()
	                if (val == word) {
	                    setTimeout(function() {
	                    }, 10)                 
	                }
	            })
	        }
	 
	        if (evtType == 'focus') {
	            asFocus()
	        } else if (evtType == 'keydown') {
	            asKeydown()
	        }
	 
	        // keydown事件里处理placeholder
	        $that.keydown(function() {
	            var val = $that.val()
	            
	            if (val == word) {
	                switchStatus(true)
	            }
	        }).keyup(function() {
	            var val = $that.val()
	            if (val == '' || val == word) {
	                switchStatus(false)
	            }
	        })
	    }
	 
	    return this.each(function() {
	        var $elem = $(this)
	        bootstrap($elem)
	        if ($.isFunction(callback)) callback($elem)
	    })
	}