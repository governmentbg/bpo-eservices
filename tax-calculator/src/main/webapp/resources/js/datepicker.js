$(document).ready(function () {
    // === Date Picker ===
    $.datepicker.regional['bg'] = {
        closeText: 'Затвори', // set a close button text
        currentText: 'Днес', // set today text
        monthNames: ['Януари', 'Февруари', 'Март', 'Април', 'Май', 'Юни', 'Юли', 'Август', 'Септември', 'Октомври', 'Ноември', 'Декември'], // set month names
        monthNamesShort: ['Яну', 'Фев', 'Март', 'Апр', 'Май', 'Юни', 'Юли', 'Авг', 'Септ', 'Окт', 'Ноем', 'Дек'], // set short month names
        dayNames: ['Неделя', 'Понеделник', 'Вторник', 'Сряда', 'Четвъртък', 'Петък', 'Събота'], // set days names
        dayNamesShort: ['Нд', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'], // set short day names
        dayNamesMin: ['Н', 'П', 'В', 'С', 'Ч', 'Пт', 'Сб'], // set more short days names
        dateFormat: 'dd.mm.yy', // set format date
        firstDay: 1,   // Default value is 0; Sunday 0; Monday 1; Tuesday 2; Wednesday 3; ...
        yearRange: "1900:+50",
        changeYear: true,
        changeMonth: true
    };

    $.datepicker.setDefaults($.datepicker.regional['bg']);
    // $(".datepicker").datepicker();
});