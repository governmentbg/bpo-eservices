// word representation in wizard mode is available on the server
// there is no need to pass it as a parameter
SimilarMarks.getSimilarMarksList("",
    function() // onSuccess
    {
        if(!SimilarMarks.listIsEmpty())
        {
            $("#similarMarks_noResults").hide();
            SimilarMarks.Pag.navigateToPage(1)
            $("#numberOfSimilar").html(SimilarMarks.similarMarksList.length);
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