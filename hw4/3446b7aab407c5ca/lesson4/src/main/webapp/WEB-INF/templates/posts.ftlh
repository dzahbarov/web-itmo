<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>


<@c.page>
    <#list posts?filter(post -> post.user_id == user_id)?sort_by("id")?reverse as p>
        <@c.postBlock p users false true true/>
    </#list>
</@c.page>
