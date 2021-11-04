<#import "common.ftlh" as c>

<@c.page>
    <#list posts?sort_by("id")?reverse as p>
        <@c.postBlock p users false true true/>
    </#list>
</@c.page>


