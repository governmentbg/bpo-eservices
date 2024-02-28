$("#similarMarkModalTrigger").live("click", function ()
{
    var wordRepresentation = $("input[name='mainForm.wordRepresentation']").val();
    if (wordRepresentation == null || wordRepresentation.trim() == "")
    {
        // display a message that the type of mark selected cannot be compared to others
        showWarningModal($("#similarMarks_noWordRepresentation").html());
        return;
    }
    showLoadingScreen();
    SimilarMarks.getSimilarMarksList(wordRepresentation,
        function() // onSuccess
        {
            if(!SimilarMarks.listIsEmpty())
            {
                SimilarMarks.Pag.navigateToPage(1);
                var numberResults = SimilarMarks.similarMarksList.length;
                $("#numberOfSimilar").html(numberResults);

                if(numberResults === 450){
                    $("#similarTrademarksError").removeClass('hidden');
                }else{
                    $("#similarTrademarksError").addClass('hidden');
                }

                $("#similarMarksModal").modal("show");
            }
            else
            {
                showMessageModal($("#similarMarks_noResultsFound").html());
            }
            hideLoadingScreen();
        }, function() // onError
        {
            hideLoadingScreen();
            showWarningModal($("#generic_errors_service_fail").html());
        })
})