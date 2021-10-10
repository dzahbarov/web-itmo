<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<#import "./index.ftlh" as i>
<@c.page>
    <@i.postBlock c.findBy(posts, "id", post_id) users/>
</@c.page>