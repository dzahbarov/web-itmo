<#-- @ftlvariable name="error" type="String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="create-article form-box">
        <div class="header">Publish article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="publish"/>
                <div class="log">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>

                    <div class="value">
                        <input name="title" id="title">
                    </div>
                </div>

                <div class="log">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea rows="10" cols="45" name="text" id="text"></textarea>
                    </div>
                </div>
                <div class="error"></div>

                <div class="button-field">
                    <input type="submit" value="publish">
                </div>
            </form>
        </div>
    </div>
    <script>

        $(function () {
            $(".create-article form").submit(function () {
                const $error = $(this).find(".error");

                const data = {};
                data.title = $(this).find("input[name='title']").val();
                data.text = $(this).find("textarea[name='text']").val();
                data.action = "publish"

                ajax($error, data);
                return false;
            });
        })
    </script>
</@c.page>