// word representation in wizard mode is available on the server
// there is no need to pass it as a parameter
SimilarMarks.getSimilarMarksList("",
    function() // onSuccess
    {
        if(!SimilarMarks.listIsEmpty())
        {
            $("#similarMarks_noResults").hide();
            SimilarMarks.Pag.navigateToPage(1)

            var numberResults = SimilarMarks.similarMarksList.length;
            $("#numberOfSimilar").html(numberResults);

            if(numberResults === 450){
                $("#similarTrademarksError").removeClass('hidden');
            }else{
                $("#similarTrademarksError").addClass('hidden');
            }

            $("#similarTrademarksTableHolder").removeClass("hidden");
        }
        else
        {
            $("#similarMarks_noResults").html($("#similarMarks_noResultsFound").html());
        }
    },
    function() // onError
    {
        $("#similarMarks_noResults").html($("#generic_errors_service_fail").html());
    }
);