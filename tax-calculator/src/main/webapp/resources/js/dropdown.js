//dropdown
function serviceDropdownFunction() {
    document.getElementById("servicesDropdown").classList.toggle("show");
}
function objectDropdownFunction() {
    document.getElementById("objectsDropdown").classList.toggle("show");
}
function objectDropdownFunctionOwedTaxes() {
    document.getElementById("objectsDropdown-owed-taxes").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function (event) {
    if (!event.target.matches('#applicationTypes') && !event.target.matches('#objectTypes') && !event.target.matches('#objectTypes-owed-taxes')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
};