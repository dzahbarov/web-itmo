<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Status</th>

            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="4">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                    <tr>
                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>
                        <td class="disabled">
                            <form method="post" action="">
                                <input type="hidden" id="userId" name="userId" value="${user.id}">
                                <#if user.disabled>
                                    <input type="hidden" id="userStatus" name="userStatus" value=0>
                                    <input type="submit" value="enable" class="enable">
                                <#else>
                                    <input type="hidden" id="userStatus" name="userStatus" value=1>
                                    <input type="submit" value="disable" class="disable">
                                </#if>

                            </form>
                        </td>

                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
