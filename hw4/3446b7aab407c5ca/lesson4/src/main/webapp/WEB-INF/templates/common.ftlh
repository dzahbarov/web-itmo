<#-- @ftlvariable name="color" type="ru.itmo.web.lesson4.model.Color" -->
<#-- @ftlvariable name="current_menu" type="String" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if current_menu == item.href>
                            <div class="current">
                                <@menuItem item></@menuItem>
                            </div>
                        <#else>
                            <@menuItem item></@menuItem>
                        </#if>

                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?sort_by("id")?reverse as p>
            <@postBlock p users false false false/>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameonly=false>
    <#if nameonly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#elseif user.color == "RED">
        <div class="red">
            <a href="/user?handle=${user.handle}">${user.name}</a>
        </div>
    <#elseif user.color == "GREEN">
        <div class="green">
            <a href="/user?handle=${user.handle}">${user.name}</a>
        </div>
    <#elseif user.color == "BLUE">
        <div class="blue">
            <a href="/user?handle=${user.handle}">${user.name}</a>
        </div>
    </#if>
</#macro>


<#macro menuItem item>
    <a href="${item.href}">${item.name}</a>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function getNext items key value>
    <#list items?sort_by("id") as item>
        <#if item[key] gt value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function getPrev items key value>
    <#list items?sort_by("id")?reverse as item>
        <#if item[key] lt value>
            <#return item/>
        </#if>
    </#list>
</#function>


<#macro postBlock post users full=false title=false extrainf=false>
<#-- @ftlvariable name="post" type="ru.itmo.web.lesson4.model.Post" -->
<#-- @ftlvariable name="users" type="ru.itmo.web.lesson4.model.User[]" -->
    <article>
        <#if title && !full>
            <div class="title">
                <a href="/post?post_id=#{post.id}">${post.title}</a>
            </div>
        <#elseif title>
            <div class="title">
                ${post.title}
            </div>
        <#else>
            <div class="header">Post #${post.id}</div>
        </#if>

        <#assign author = findBy(users, "id", post.user_id)>
        <#if extrainf>
            <div class="information">By ${author.name}</div>
        </#if>

        <div class="body">
            <#if full>
                <div class="fulltext">
                    ${post.text}
                </div>
            <#else>
                <div class="shorttext">
                    ${post.text}
                </div>
            </#if>
        </div>

        <div class="footer">
            <#if extrainf>
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
            <#else>
                <a href="/post?post_id=#{post.id}">View all</a>
            </#if>
        </div>
    </article>
</#macro>
