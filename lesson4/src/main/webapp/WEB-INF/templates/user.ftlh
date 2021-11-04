<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="u.name" type="java.lang.String" -->
<#-- @ftlvariable name="u.handle" type="java.lang.String" -->
<#-- @ftlvariable name="u.id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id?? && user_id?is_number && user_id gt 0>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>

    <#if u?has_content>
        <div class="UserBox">
            <div class="name">
                <@c.userlink u ></@c.userlink>
            </div>
            <img src="img/no-title.jpg" alt="">
            <ul>
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
                <li>posts:
                    <#assign res = posts?filter(post -> post.user_id == u.id)?size>
                    <a href="posts?user_id=${u.id}"> ${res}</a>
                </li>
            </ul>
        </div>
        <div class="jumps">
            <div class="prev">
                <#assign uprev = c.getPrev(users, "id", u.id)!/>
                <#if uprev?has_content>
                    <div class="hasNeighbour">
                        <a href="/user?handle=${uprev.handle}">🠐</a>
                    </div>
                <#else>
                    <div class="hasntNeighbour">
                        🠐
                    </div>
                </#if>
            </div>

            <div class="next">
                <#assign unext = c.getNext(users, "id", u.id)!/>
                <#if unext?has_content>
                    <div class="hasNeighbour">
                        <a href="/user?handle=${unext.handle}">🠒</a>
                    </div>
                <#else>
                    <div class="hasntNeighbour">
                        🠒
                    </div>
                </#if>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
