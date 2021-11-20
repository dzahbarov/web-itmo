<#-- @ftlvariable name="admin" type="java.lang.Boolean" -->
<#-- @ftlvariable name="currentUser" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="error"></div>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user-id"></td>
                    <td class="user_login"></td>
                    <#if admin >
                        <td class="user_admin">
                            <div class="status"></div>
                            <a href="#"></a>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <#if admin>
                    <td colspan="4">
                        No data
                    </td>
                <#else >
                    <td colspan="3">
                        No data
                    </td>
                </#if>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $error = $(this).find(".error");
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            $.ajax({
                dataType: "json",
                data: {
                    action: "findAll"
                },
                success: function (response) {
                    response["users"].forEach(function (user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user-id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr").attr("data-userId", user["id"]);

                        if (user["admin"] === false) {
                            $tr.find(".user_admin .status").text("User");
                            $tr.find(".user_admin a").text("Enable");
                        } else {
                            $tr.find(".user_admin .status").text("Admin");
                            $tr.find(".user_admin a").text("Disable");
                        }
                        $tr.find(".user_admin").attr("data-userId", user["id"])
                        $tr.find(".user_admin").attr("data-userStatus", user["admin"]);

                        $(".users tbody").append($tr);
                    });

                    $(".users tr").click(function () {
                        const data = {}
                        data.action = "findUser"
                        data.userId = $(this).attr("data-userId")

                        function success(response) {
                            notify(response["user"].creationTime);
                        }

                        ajax($error, data, success)
                    });

                    $(".user_admin a").click(function () {
                        const $admin_panel = $(this).parent()
                        const $link = $admin_panel.find("a")
                        const $role = $admin_panel.find(".status")

                        const req = {};
                        req.action = "changeStatus";
                        req.userId = $admin_panel.attr("data-userId");

                        req.status = $admin_panel.attr("data-userStatus") !== "true";

                        function success() {
                            if ($admin_panel.attr("data-userStatus") === "true") {
                                $link.text("Enable")
                                $role.text("User");
                                $admin_panel.attr("data-userStatus", false);
                            } else {
                                $link.text("Disable")
                                $role.text("Admin");
                                $admin_panel.attr("data-userStatus", true);
                            }
                        }
                        ajax($error, req, success);
                    });
                },
                type: "POST"
            });
        })
    </script>
</@c.page>
