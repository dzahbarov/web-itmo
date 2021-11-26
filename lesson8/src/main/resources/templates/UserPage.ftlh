<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        <div class="userId">
            Id: ${user.id}
        </div>

        <div class="userLogin">
            login: ${user.login}
        </div>

        <div class="userCreationTime">
            creationTime: ${user.creationTime}
        </div>
    <#else>
        No such user
    </#if>

</@c.page>
