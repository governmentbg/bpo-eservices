var ForeignRegistration = {};

$("#foreignRegistrationTrigger").live("click", function(){
    toggleForeignRegistrationAdd();
});

$(".addForeignRegistration").live("click", function (event)
{
    $(".addForeignRegistration").attr("disabled",true);
    ForeignRegistration.ajax.addForeignRegistrationPost();
});

$(".cancelForeignRegistration").live("click", function (){
    $("#foreignRegistrationDiv").html("");
    toggleForeignRegistrationAdd();
    scrollToForeignRegistrationTop();
});

$(".deleteForeignRegistrationButton").live("click", function (){
    $(".cancelForeignRegistration").click();
    var id = $(this).attr("data-val");
    var onConfirmYes = partial(ForeignRegistration.ajax.removeForeignRegistration, id);
    showConfirmModal($("#delete_confirmation_foreign_registration").html(), onConfirmYes);
});

$(".editForeignRegistrationButton").live("click", function (event){
    showForeignRegistrationTab($(this).attr("data-val"))
});

ForeignRegistration.nav =
    {
        addForeignRegistration:"addForeignRegistration.htm",
        removeForeignRegistration:"removeForeignRegistration.htm"
    };

ForeignRegistration.ajax =
    {
        getForeignRegistrationForm: function(id)
        {
            let data="";
            if (id) {
                data = "id=" + id;
            }
            removeForeignRegistrationErrors();
            $.ajax({
                url:ForeignRegistration.nav.addForeignRegistration,
                type:"GET",
                async:false,
                data: data,
                success:function (html)
                {
                    updateForeignRegistrationSection(html, true);
                },
                error:function (error)
                {
                    genericHandleError("An error occured while processing your request. Please try again later.",
                        "#foreignRegistrationCardList", true);
                }
            });
        },

        addForeignRegistrationPost: function()
        {
            var dataToSend = $("#foreignRegistrationDiv form").serialize();
            removeForeignRegistrationErrors();
            $.ajax(
                {
                    url: ForeignRegistration.nav.addForeignRegistration,
                    type: "POST",
                    data: dataToSend,
                    success:function (html)
                    {
                        if ($("input#formReturned", $(html)).val() == "true")
                        {
                            // then display the errors
                            displayForeignRegistrationValidationErrors(html);
                            return;
                        } else {
                            refreshForeignRegistrationCards(html);
                        }
                        $(".cancelForeignRegistration").click();

                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.", "#foreignRegistrationDiv", true);
                    },
                    complete: function() {
                        $(".addForeignRegistration").attr("disabled",false);
                    }
                });
        },

        removeForeignRegistration: function(id)
        {
            removeForeignRegistrationErrors();
            $.ajax({
                    url:ForeignRegistration.nav.removeForeignRegistration,
                    data:"id=" + id,
                    type:"GET",
                    success:function (html)
                    {
                        refreshForeignRegistrationCards(html);
                    },
                    error:function (error)
                    {
                        genericHandleError("An error occured while processing your request. Please try again later.",
                            "#foreignRegistrationCardList", true);
                    }
                }
            );
        },
    };

function updateForeignRegistrationSection(html)
{
    $("#foreignRegistrationDiv").html(html);
}

function displayForeignRegistrationValidationErrors(html)
{
    $("#foreignRegistrationDiv").html(html);
}


function scrollToForeignRegistrationTop()
{
    scrollToContainer("#foreignRegistrationCardList");
}

function toggleForeignRegistrationAdd()
{
    if($("#tabForeignRegistration").is(":hidden"))
    {
        showForeignRegistrationTab();
    }
    else
    {
        hideForeignRegistrationTab();
    }
}

function showForeignRegistrationTab(id)
{
    $("#tabForeignRegistration").show();
    ForeignRegistration.ajax.getForeignRegistrationForm(id)
    $("#foreignRegistrationTrigger").addClass("active");

}

function hideForeignRegistrationTab()
{

    $("#tabForeignRegistration").hide();
    $("#foreignRegistrationTrigger").removeClass("active");
}

function refreshForeignRegistrationCards(html)
{
    $("#foreignRegistrationCardList").html(html);

    scrollToForeignRegistrationTop();

    checkMaxForeignRegistrations();
}

function checkMaxForeignRegistrations(){
    var max=$('#maxForeignRegistrations').val();

    var rowCount = $('#foreignRegistrationCardList tr').length-1;

    if(rowCount==max) {
        $("#foreignRegistrationTrigger").hide();
    } else {
        $("#foreignRegistrationTrigger").show();
    }
}

function removeForeignRegistrationErrors()
{
    removePreviousErrors("#foreignRegistrationCardList");
    removePreviousErrors("#foreignRegistrationDiv");
}