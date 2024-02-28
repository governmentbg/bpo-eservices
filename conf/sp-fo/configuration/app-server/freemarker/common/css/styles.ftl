<style type="text/css">
    * {
        font-family: "Arial Unicode MS";
        letter-spacing: 1px;
        font-size: 10pt;
    }
    table.receiptTable {
        page-break-after: auto;
        -fs-table-paginate: paginate;
    }
    table.bordered {
        border-collapse: collapse;
        border-spacing: 0;
        border: 0.03em solid black;
        width: 100%;
    }
    table.bordered td, th {
        border: 0.03em solid black;
        padding: 5px;
    }
    table.views-table {
        margin-left:20px;
        border: none;
        width: 100%;
    }
    table.views-table td {
        border: none;
        padding: 5px;
    }
    table.views-table td div {
        text-align: center;
    }
    .titleText {
        font-size: 13pt;
        font-weight: bold;
    }

    .eserviceTitle {
        font-size: 13pt;
        font-weight: bold;
    }

    .eserviceTitle.TM {
        color: #0556ab;
    }
    .eserviceTitle.DS {
        color: #016e01;
    }
    .eserviceTitle.PT {
        color: #6a30a0;
    }
    .eserviceTitle.UM {
        color: #b84904;
    }
    .eserviceTitle.EP {
        color: #1a5f5f;
    }
    h3 {
        font-size: 13pt;
    }
    h1 {
        font-size: 15pt;
    }

    @page {
        font-family:  'Arial Unicode MS';
        @bottom-right {
            font-size: 7pt;
            counter-increment: page;
            content: counter(page) "/" counter(pages);
        }
        @bottom-left {
            font-size: 7pt;
            counter-increment: page;
            content: "${.now?string("dd.MM.yyyy HH:mm:ss")}";
        }
    }
</style>