<html>
<head>
    <jsp:include page="../common/include.jsp" />
    <title>Sign up</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<form id="registration_form">
    <div class="form-group">
        <label>email:</label>
        <input type="email" name="email" class="form-control">
    </div>
    <div class="form-group">
        <label>password:</label>
        <input type="password" name="password" class="form-control">
    </div>
    <button class="btn btn-success">Sign up</button>
</form>

<script src="/resources/js/main/sign_up.js"></script>

<jsp:include page="../common/footer.jsp" />
</body>
</html>
