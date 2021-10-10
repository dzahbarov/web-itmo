<#import "./common.ftlh" as c>

<@c.page>

    <#list posts?sort_by("id")?reverse as p>
        <@postBlock p users/>
    </#list>

</@c.page>



<#macro postBlock post users>
<#-- @ftlvariable name="post" type="ru.itmo.web.lesson4.model.Post" -->
<#-- @ftlvariable name="users" type="ru.itmo.web.lesson4.model.User[]" -->
    <article>
        <div class="title">${post.title}</div>
        <#assign author = c.findBy(users, "id", post.user_id)>
        <div class="information">By ${author.name}</div>
        <div class="body">
            ${post.text}
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>