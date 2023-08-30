<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/inc/header.jsp" %>
	<div class="container">
        <div class="col-lg-4"></div>
        <div class="col-lg-4">
            <div class ="jumbotron" style="padding-top:20px;">
                <form method = "post" action="memberJoinAction.me"> <!-- joinAction 페이지로 화면 넘기기 -->
                    <h3 style="text-align:center;">회원가입</h3>
                    <div class ="form-group">
                        <input type ="text" class="form-control" placeholder="아이디" name ="member_id" maxlength='20' required="required">
                    </div>
                    <div class ="form-group">
                        <input type ="password" class="form-control" placeholder="비밀번호" name ="member_pw" maxlength='20' required="required">
                    </div>
                    <div class ="form-group">
                        <input type ="text" class="form-control" placeholder="닉네임" name ="member_name" maxlength='20' required="required">
                    </div>
                    <div class ="form-group">
                        <input type ="number" class="form-control" placeholder="나이" name ="member_age" maxlength='20' required="required" min="0">
                    </div>
                    <div class ="form-group" style="text-align: center;">
                    	<div class ="btn-group" cata-toggle="buttons">
                    		<label class ="btn btn-primary"> <!-- 버튼 활성화 -->
                    			<input type ="radio" name ="member_gender" autocomplete ="off" value = "남" checked>남자</label>
                    		<label class ="btn btn-primary"> <!-- 버튼 활성화 NO -->
                    			<input type ="radio" name ="member_gender" autocomplete ="off" value = "여" checked>여자</label>
                    	</div>         		
                    </div>
                    <div class ="form-group">
                        <input type ="email" class="form-control" placeholder="이메일" name ="member_email" maxlength='30'>
                    </div>
                    <input type="submit" class="btn btn-primary form-control" value="회원가입">
                </form>
            </div> 
        </div> 
        <div class="col-lg-4"></div>
    </div>
</body>
</html>