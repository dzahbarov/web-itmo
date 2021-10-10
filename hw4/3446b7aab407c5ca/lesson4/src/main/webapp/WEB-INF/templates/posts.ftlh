<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<#import  "./index.ftlh" as i>

<@c.page>
    <#list posts?filter(post -> post.user_id == user_id)?sort_by("id")?reverse as p>
        <@i.postBlock p users/>
    </#list>
</@c.page>
