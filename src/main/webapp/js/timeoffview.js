$(document).ready(function() {
    $("#toggle-admin").click(function() {
        $("#table-container").toggleClass("col-md-12 col-md-8");
        $(".hide").toggle();
    });
});