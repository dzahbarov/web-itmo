window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}


window.ajax = function ($error, query) {
    $.ajax({
        type: "POST",
        url: "",
        data: query,
        dataType: "json",
        success: function (response) {
            if (response["error"]) {
                $error.text(response["error"]);
            } else if (response["redirect"]){
                location.href = response["redirect"];
            }
        }
    });
}