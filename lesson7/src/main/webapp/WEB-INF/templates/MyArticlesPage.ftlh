<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#-- @ftlvariable name="article" type="ru.itmo.wp.model.domain.Article" -->
<#import "commons.ftlh" as c>

<@c.page>
    <main>
        <div class="error"></div>
        <div class="art-table datatable">
            <div class="caption">My articles</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Hide/Show</th>
                </tr>
                </thead>
                <tbody>
                <div class="hh"></div>
                <#list articles as article>
                    <tr class="myClass">
                        <th>${article.id}</th>
                        <th>${article.title}</th>
                        <th class="bottom">
                            <div class="button-field">
                                <#if article.hidden>
                                    <input type="submit" value="Show" class="button-field1" data-articleId="${article.id}">
                                <#else>
                                    <input type="submit" value="Hide" class="button-field1" data-articleId="${article.id}">
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

            $tbody.find(".button-field1").click(function () {
                const $bottom = $(this);
                const req = {};
                req.action = "changeStatus";
                req.artId = $bottom.attr("data-articleId");
                if ($bottom.attr("value") === "Show") {
                    req.status = false;
                } else if ($bottom.attr("value") === "Hide") {
                    req.status = true;
                } else {
                    req.status = -1;
                }

                function success() {
                    if ($bottom.attr("value") === "Show") {
                        $bottom.val("Hide")
                    } else if ($bottom.attr("value") === "Hide") {
                        $bottom.val("Show")
                    }
                }
                ajax($error, req, success);
            })
        })
    </script>

</@c.page>
