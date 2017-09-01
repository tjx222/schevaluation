<%@tag pageEncoding="UTF-8"%>
<input type="hidden" name="_TOKEN_" value="${requestScope._TOKEN_ }"/>
<input type="hidden" name="${requestScope._TOKEN_}" value="${sessionScope[requestScope._TOKEN_] }"/>