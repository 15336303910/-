$(function(){
	var email = $('#email');
	var pass1 = $('#password1');
	var pass2 = $('#password2');
	var arrow = $('#main .arrow');
	/*验证旧密码是否为空*/
	email.on('blur',function(){
		if(email.val()==""){
			email.parent().addClass('error').removeClass('success');
		}else{
			email.parent().removeClass('error').addClass('success');
		}		
	});
	pass1.complexify({
		minimumChars:6,
		strengthScaleFactor:0.7
	},function(valid,complexity){
		if(valid){
			pass2.removeAttr('disabled');
			pass1.parent().removeClass('error').addClass('success');
		}else{
			pass2.attr('disabled','true');
			pass1.parent().removeClass('success').addClass('error');
		}		
		var calculated = (complexity/100)*268 - 134;
		var prop = 'rotate('+(calculated)+'deg)';
		arrow.css({
			'-moz-transform':prop,
			'-webkit-transform':prop,
			'-o-transform':prop,
			'-ms-transform':prop,
			'transform':prop
		});
	});
	/*验证重复新密码是否和新密码保持一致*/
	pass2.on('keydown input',function(){		
		if(pass2.val() == pass1.val()){			
			pass2.parent().removeClass('error').addClass('success');
		}else{
			pass2.parent().removeClass('success').addClass('error');
		} 
	});	
});
