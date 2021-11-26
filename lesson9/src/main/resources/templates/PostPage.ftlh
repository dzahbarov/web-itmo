<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post=post/>
        <#if user??>
            <form method="post" action="">
                <input type="hidden" name="action" value="addComment"/>
                <div class="value">
                    <textarea id="text" name="text"></textarea>
                </div>
                <@c.error "comment.text"/>
                <div class="button-field">
                    <input type="submit" value="Add comment">
                </div>
            </form>
        </#if>

        <#list post.comments as comment>
            <div class="comment">${comment.user.login}: ${comment.text}</div>
        </#list>
    <#else>
        No such post
    </#if>

</@c.page>
