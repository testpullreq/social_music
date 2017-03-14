<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div>
    <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
        <a class="btn btn-success" href="/admin/main">Admin panel</a>
    </security:authorize>
    <security:authorize access="isAnonymous()">
        <button class="btn btn-success" id="sign_in">sign in</button>
        <a class="btn btn-success" href="/registration">Sign up</a>
    </security:authorize>
    <security:authorize access="isAuthenticated()">
        <a class="btn btn-success" href="/cabinet/profile">Profile</a>
        <button class="btn btn-success" id="logout">log out</button>
    </security:authorize>
</div>
<div>
    <a href="/claim_types/all" class="btn btn-success">claim types</a>
</div>