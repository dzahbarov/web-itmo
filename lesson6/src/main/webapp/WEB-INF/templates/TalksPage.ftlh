<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="userService" type="ru.itmo.wp.model.service.UserService" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="sendTo" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Send Message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="send"/>
                <div class="sendTo">
                    <div class="name">
                        <label for="targetUser">SendTo</label>
                    </div>

                    <div class="value">
                        <select name="sendTo" id="targetUser">
                            <#list userService.findAll() as userField>
                                <#if user.id != userField.id>
                                    <option id="targetUser" name="sendTo"> ${userField.login!}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>

                <div class="Message">
                    <div class="name">
                        <label for="message">Message</label>
                    </div>
                    <div class="value">
                        <textarea rows="10" cols="45" name="text" id="text"></textarea>
                    </div>
                </div>

                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>

                <div class="button-field">
                    <input type="submit" value="Send">
                </div>


            </form>
        </div>
    </div>
    <div class="body">

        <div class="datatable">
            <div class="caption">Talks</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>SourceUser</th>
                    <th>TargetUser</th>
                    <th>Message</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <#list talks as talk>
                    <tr>
                        <td>${talk.id}</td>
                        <td>${userService.find(talk.sourceUserId).login}</td>
                        <td>${userService.find(talk.targetUserId).login}</td>
                        <td>${talk.text}</td>
                        <td>${talk.creationTime}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

    </div>


</@c.page>
