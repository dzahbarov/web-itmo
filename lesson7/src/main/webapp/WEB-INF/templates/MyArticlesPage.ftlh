<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#-- @ftlvariable name="article" type="ru.itmo.wp.model.domain.Article" -->
<#import "commons.ftlh" as c>

<@c.page>
    <main>
        <div class="art-table">
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Hide/Show</th>
                </tr>
                </thead>
                <tbody>
                <#list articles as article>
                    <tr data-articleId="${article.id}">
                        <th>${article.id}</th>
                        <th>${article.title}</th>
                        <th>
                            <div class="button-field">
                                <#if article.hidden>
                                    <input type="submit" value="Show" class="hideToShow">
                                 <#else>
                                     <input type="submit" value="Hide" class="showToHide">
                                </#if>
                            </div>
                        </th>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

    </main>

    <script>
        $(function () {
            const $error = $(this).find(".error");
            const $tbody = $(".art-table").find("tbody");
            const artId = $(this).attr("data-articleId");

            $("input [type='submit']").val("kek");

            $tbody.find(".hideToShow").click(function (){
                const req = {};
                req.action = "changeStatus";
                req.artId = artId;
                ajax($error, req);
                $tbody.find(".hideToShow").text("ui");
            })

            $tbody.find(".showToHide").click(function (){
                const req = {};
                req.artId = artId;
                req.action = "changeStatus";
                ajax($error, req);
            })

        })
    </script>

</@c.page>
