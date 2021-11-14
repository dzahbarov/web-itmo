<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#-- @ftlvariable name="article" type="ru.itmo.wp.model.domain.Article" -->
<#import "commons.ftlh" as c>

<@c.page>
    <main>
        <div class="articles">
            <div class="noData">
                No data
            </div>
            <template>
                <article>
                    <div class="title"></div>
                    <div class="information"></div>
                    <div class="body"></div>

                    <div class="footer">
                        <div class="left">
                            <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                            <span class="positive-score">+173</span>
                            <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                        </div>
                        <div class="right">
                            <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                            <div class="date"></div>
                            <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                            <a href="#">68</a>
                        </div>
                    </div>
                </article>
            </template>
        </div>
    </main>

    <script>
        $(function () {
            const $noData = $(".articles .noData");
            $noData.text();

            $.ajax({
                dataType: "json",
                data: {
                    action: "findAll"
                },
                success: function(response) {
                    response["articles"].forEach(function(article) {
                        $noData.hide();
                        const $tpl = $($(".articles template").prop("content")).clone();
                        $tpl.find(".title").text(article.title);
                        $tpl.find(".information").text("By " + response["userIdByArticle"][article.userId] + ", " + article.creationTime);
                        $tpl.find(".body").text(article.text);
                        $tpl.find(".date").text(article.creationTime);

                        $(".articles").append($tpl);
                    });
                },
                type: "POST"
            });
        })
    </script>

</@c.page>
