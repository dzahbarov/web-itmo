<#import "commons.ftlh" as c>

<@c.page>

    <div class="notice form-box">
        <div class="header">Add notice</div>
        <div class="body">
            <form method="post" action="">
                <div class="content">
                    <div class="name">
                        <label for="content">Content</label>
                    </div>
                    <div class="value">
                        <textarea rows="20" cols="45" name="content" id="content">${noticeForm.content!}</textarea>
                    </div>
                </div>
                <@c.error "noticeForm.content"/>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="add notice">
                </div>
            </form>
        </div>
    </div>


</@c.page>
