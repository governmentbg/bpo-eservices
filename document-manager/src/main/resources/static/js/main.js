$(".opts-nav li a").on("click", function (){
    $(".opts-nav li a").each(function (i, el){
        $(el).removeClass("active");
        $(el).closest("li").removeClass("active");
    });
    $(this).addClass("active");
    $(this).closest("li").addClass("active");
    if($(this).attr("data-target") && $(this).attr("data-target") != ""){
        $(".nav-hide").hide();
        $("#"+$(this).attr("data-target")).show();
    }
});

$(".receipt-form-btn").on("click", function(e){
    e.preventDefault();
    $("#receipt-form").attr("action", $(this).attr("data-action"));
    $("#receipt-form").submit();
});