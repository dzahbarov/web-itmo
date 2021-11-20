window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = function ($error, query, success= (response) => {}) {
    $.ajax({
        type: "POST",
        url: "",
        data: query,
        dataType: "json",
        success: function (response) {
            success(response)
            if (response["error"]) {
                $error.text(response["error"]);
            } else if (response["redirect"]){
                location.href = response["redirect"];
            }
        }
    });
}
