<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<#import "./index.ftlh" as i>
<@c.page>
    <@c.postBlock c.findBy(posts, "id", post_id) users true true true/>
</@c.page>