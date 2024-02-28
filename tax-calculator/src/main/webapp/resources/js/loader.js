$( function() {
    //console.log(1);
    $("#bpo-loader").dialog({
        autoOpen: false,
        dialogClass: "ui-shadow position-fixed z1001",
        zIndex: 9999,
        resizable: false,
        modal: true,    //  puts a screen behind modal that prevents clicking on page
        closeOnEscape: false    //  esc key will close dlg
    });
    $(".ui-dialog-titlebar").hide();


} );


