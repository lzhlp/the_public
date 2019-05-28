/**
 * 用户注册
 */
function registered() {
	var username = $("#username").val();
	var password = $("#password").val();
	/*var verify = $("#logincode").val();*/
	if (username == "" || username == null) {
		alert("请输入用户名!", "系统提示");
		return;
	}
	if (password == "" || password == null) {
		alert("请输入密码!", "系统提示");
		return;
	}
	$.ajax({
		type : "post",
		url : "http://47.94.198.47:8082/myuser/registered",
		data : {
			"username" : username,
			"password":password,
		},

		dataType : "json",
		success : function(data) {
			if (data.result == "1") {
				alert("登陆成功"+data.id);
				$("#password").val("");
				$("#username").val("");
				return;
			} else {
				alert("登录失败");
				$("#password").val("");
				$("#username").val("");
				return;
			}
		}
	});
}


/**
 * 用户登录
 */
function login() {
	var username = $("#username").val();
	var password = $("#password").val();
	if (username == "" || username == null) {
		alert("请输入用户名!", "系统提示");
		return;
	}
	if (password == "" || password == null) {
		alert("请输入密码!", "系统提示");
		return;
	}
	$.ajax({
		type : "post",
		url : "http://47.94.198.47:8082/myuser/login",
		data : {
			"username" : username,
			"password":password,
		},
		dataType : "json",
		success : function(data) {
			if (data.result == "1") {
				alert("登陆成功"+data.username);
				$("#incang").val(data.username);
				$("#aid").val(data.id);
				index();
			} else {
				alert("登录失败");
				$("#password").val("");
				$("#username").val("");
				return;
			}
		}
	});
}

function index(){
	var username = $("#incang").val();
	var id = $("#aid").val();
	window.location.href = "http://47.94.198.47:8082/myuser/index?username="+username+"&id="+id;
	window.event.returnValue=false;
}

function tologin(){
	window.location.href = "http://47.94.198.47:8082/myuser/tologin";
	window.event.returnValue=false;
}

function upload(){
	$(addForm).form({
		url : "http://47.94.198.47:8082/myuser/addloda",
		onSubmit : function() {
			var isValid = $(this).form('validate');
			return isValid;
		},
		dataType:"json",
		success : function(data) {
			var json = JSON.parse(data);
			if (json.result == "1") {
				alert("上传附件成功");
				return;
			} else {
				alert(json.message);
				return;
			}
		}
	});
	$(addForm).submit();
}


function upload1(){
	
	$(addForm).form({
		url : "http://47.94.198.47:8082/myuser/addloda",
		onSubmit : function() {
			var isValid = $(this).form('validate');
			return isValid;
		},
		dataType:"json",
		success : function(data) {
			var json = JSON.parse(data);
			if (json.result == "1") {
				alert("上传附件成功");
				return;
			} else {
				alert(json.message);
				return;
			}
		}
	});
	$(addForm).submit();
}
function checkFileType(file){
	
	var url = file.baseURI.substring(8,file.baseURI.length);
	$("#bbb").val(url);
}
