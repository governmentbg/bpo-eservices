SimilarMarks =
{
    getUsedGsClasses: function()
    {
        return SimilarMarks.usedGsClasses;
    },
    setUsedGsClasses: function(list)
    {
        SimilarMarks.usedGsClasses = list;
    },
    classWasSelected: function(gsClass)
    {
        return SimilarMarks.getUsedGsClasses().indexOf(gsClass) != -1;
    },
    getSimilarMarksList:function (wordRepresentation, onSuccess, onError)
    {
        $.ajax({
            url:"getSimilarMarksList.htm",
            type:"GET",
            data:{"markDenomination":wordRepresentation},
            success:function (json)
            {
                if(SimilarMarks.containsError(json))
                {
                    onError();
                    return;
                }
                SimilarMarks.similarMarksList = json;

                // getting classes used in this application
                $.ajax({
                    url: "getGSClasses.htm",
                    type: "GET",
                    success: function(json)
                    {
                        SimilarMarks.setUsedGsClasses(json);

                        onSuccess();
                    }
                });
            },
            error: function(html)
            {
                onError();
            }
        })
    },
    listIsEmpty:function ()
    {
        return SimilarMarks.similarMarksList == null ||
            SimilarMarks.similarMarksList == "" ||
            SimilarMarks.similarMarksList.length == 0;
    },
    // pageNumber : starts with 1
    getSimilarMarksPaginated:function (pageNumber, rowsPerPage)
    {
        var start = (pageNumber - 1) * rowsPerPage;
        var end = pageNumber * rowsPerPage;
        return SimilarMarks.similarMarksList.slice(start, end);
    },
    buildTableRow:function (similarMarkObject, hasUrl)
    {
        var row = "<tr>";
        row += "<td>" + similarMarkObject.applicationNumber + "</td>";
        row += "<td>" + similarMarkObject.name + "</td>";
        row += "<td>" + similarMarkObject.type + "</td>";
        row += "<td>" + similarMarkObject.office + "</td>";
        row += "<td>" + similarMarkObject.ownerName + "</td>";

        if(similarMarkObject.inputTerms != null)
        {
            var tip = "";
            for (var i = 0; i < similarMarkObject.inputTerms.length; i++)
            {
                tip += similarMarkObject.inputTerms[i];
                if(i < similarMarkObject.inputTerms.length - 1)
                {
                    tip += ", ";
                }
            }
            row += "<td title='" + tip + "'><ul>";
            if(similarMarkObject.inputTerms.length < 6)
            {
                for (var i = 0; i < similarMarkObject.inputTerms.length; i++)
                {
                    row += SimilarMarks.wrapNiceClass(similarMarkObject.inputTerms[i]);
                }
            }
            else
            {
                for (var i = 0; i < 6; i++)
                {
                    row += SimilarMarks.wrapNiceClass(similarMarkObject.inputTerms[i]);
                }
                row += "...";
            }
            row += "</ul></td>";
        }
        else
        {
            row +="<td></td>";
        }


        if (hasUrl)
        {
            var detailsUrl = htmlDecode($(".similarTMDetailsUrlTemplate").html().trim());
            var parsedUrl = parseLinkUsingKeyValuePairs(
                [
                    ["trademarkId", similarMarkObject.applicationNumber],
                    ["officeCode", similarMarkObject.office]
                ],
                detailsUrl);
            row += "<td class='action'><a class='icon-search' target='_blank' href='" + parsedUrl + "'></a></td>";
        }
        row += "</tr>";
        return row;
    },
    wrapNiceClass: function(className)
    {
        if(SimilarMarks.classWasSelected(className))
        {
            return "<li class='nice-class active'>" + className + "</span>";
        }
        else
        {
            return "<li class='nice-class'>" + className + "</span>";
        }
    },
    buildTablePage:function (pageNumber, rowsPerPage)
    {
        var pageList = SimilarMarks.getSimilarMarksPaginated(pageNumber, rowsPerPage);
        var hasUrl = $("#similarMarkUrlColumn").size() != 0;
        var page = "";
        for (var i = 0; i < pageList.length; i++)
        {
            page += SimilarMarks.buildTableRow(pageList[i], hasUrl);
        }
        return page;
    },
    showTablePage:function (tableSelector, pageNumber, rowsPerPage)
    {
        // clear similar trademarks table by removing all rows except first, which is the header
        $(tableSelector).find("tr:gt(0)").remove();

        $(tableSelector + " tbody:last").append(SimilarMarks.buildTablePage(pageNumber, rowsPerPage));
    },
    containsError:function (html)
    {
        if ($(".errorOccurred", $(html)).size() > 0)
        {
            return true;
        }
        return false;
    },
    sort: function(property, descending)
    {
        SimilarMarks.similarMarksList.sort(Common.Sort.predicate(property, descending));
        SimilarMarks.Pag.navigateToPage(SimilarMarks.Pag.currentPage)
    }
}

SimilarMarks.Pag =
{
    currentPage:1,
    getPageSize:function ()
    {
        return $("#similarMarksPageSize").html();
    },
    getNumberOfPages:function ()
    {
        return Math.ceil(SimilarMarks.similarMarksList.length / SimilarMarks.Pag.getPageSize());
    },
    navigateToPage:function (pageNumber)
    {
        var tableSelector = "#similarTrademarksTable";
        SimilarMarks.Pag.currentPage = pageNumber;
        SimilarMarks.showTablePage(tableSelector, pageNumber, SimilarMarks.Pag.getPageSize());
        SimilarMarks.Pag.displayPageNavigation();
    },
    navigateToPreviousPage: function()
    {
        SimilarMarks.Pag.navigateToPage(parseInt(SimilarMarks.Pag.currentPage) - 1);
    },
    navigateToNextPage: function()
    {
        SimilarMarks.Pag.navigateToPage(parseInt(SimilarMarks.Pag.currentPage) + 1);
    },
    getWrappedPageNumber:function (pageNumber)
    {
        var linkClass = "page";
        var itemClass = "";
        if(pageNumber == parseInt(SimilarMarks.Pag.currentPage))
        {
            itemClass += " active";
        }
        var wrap = "<li class='" + itemClass + "'><a class='" + linkClass + "'>"  + pageNumber + "</a></li>";
        return wrap;
    },
    addPageNumber:function (pageNumber)
    {
        $("#similarTrademarksPageContainer").append(SimilarMarks.Pag.getWrappedPageNumber(pageNumber));
    },
    addPageNavigation:function(backwards, disabled)
    {
        var linkClass = "next-page";
        var txt = "Next";
        if(backwards)
        {
            linkClass = "prev-page";
            txt = "Prev";
        }
        if(disabled)
        {
            linkClass += " disabled";
        }
        $("#similarTrademarksPageContainer").append("<li><a class='" + linkClass + "'>" + txt + "</a></li>");
    },
    addSeparator:function ()
    {
        $("#similarTrademarksPageContainer").append("<li><a class='page-separator'>&middot;</a></li>");
    },
    displayPageNavigation:function ()
    {
        $("#similarTrademarksPageContainer").html("");
        var currentPage = parseInt(SimilarMarks.Pag.currentPage);
        var numberOfPages = parseInt(SimilarMarks.Pag.getNumberOfPages());
        if(currentPage == 1)
        {
            SimilarMarks.Pag.addPageNavigation(true, true);
        }
        else
        {
            SimilarMarks.Pag.addPageNavigation(true, false);
        }

        if (numberOfPages <= 7)
        {
            for (var i = 1; i <= numberOfPages; i++)
            {
                SimilarMarks.Pag.addPageNumber(i);
            }
        }
        else
        {
            SimilarMarks.Pag.addPageNumber(1);

            if(currentPage > 4)
            {
                SimilarMarks.Pag.addSeparator();
            }

            if(currentPage - 2 > 1)
            {
                SimilarMarks.Pag.addPageNumber(currentPage - 2);
            }
            if(currentPage - 1 > 1)
            {
                SimilarMarks.Pag.addPageNumber(currentPage - 1);
            }
            if(currentPage > 1 && currentPage < numberOfPages)
            {
                SimilarMarks.Pag.addPageNumber(currentPage);
            }
            if(currentPage + 1 < numberOfPages)
            {
                SimilarMarks.Pag.addPageNumber(currentPage + 1);
            }
            if(currentPage + 2 < numberOfPages)
            {
                SimilarMarks.Pag.addPageNumber(currentPage + 2); }
            if(currentPage < numberOfPages - 3)
            {
                SimilarMarks.Pag.addSeparator();
            }

            SimilarMarks.Pag.addPageNumber(numberOfPages);
        }

        if(currentPage == numberOfPages)
        {
            SimilarMarks.Pag.addPageNavigation(false, true);
        }
        else
        {
            SimilarMarks.Pag.addPageNavigation(false, false);
        }
    }
}


$("#similarTrademarksPageContainer li a.page").live("click", function ()
{
    SimilarMarks.Pag.navigateToPage($(this).html());
})
$("#similarTrademarksPageContainer li a.prev-page").live("click", function ()
{
    if($(this).hasClass('disabled'))
    {
         return;
    }
    SimilarMarks.Pag.navigateToPreviousPage();
})
$("#similarTrademarksPageContainer li a.next-page").live("click", function ()
{
    if($(this).hasClass('disabled'))
    {
        return;
    }
    SimilarMarks.Pag.navigateToNextPage();
})
$("#similarTrademarksTable a.sorter").live("click", function()
{
    var sortDescending = $(this).hasClass("asc");
    // remove all sort classes from other selectors
    $("#similarTrademarksTable a.sorter").each(function()
    {
        $(this).removeClass("desc");
        $(this).removeClass("asc");
    })

    SimilarMarks.sort($(this).attr("data-val"), sortDescending);
    if(sortDescending)
    {
        $(this).addClass("desc");
    }
    else
    {
        $(this).addClass("asc");
    }
})