<#ftl encoding="UTF-8" />

<#escape x as x?html>

<html>
<head>
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <style type="text/css">
        @font-face {
            font-family: "Arial Unicode MS";
            src: url('arialuni.ttf');
            -fs-pdf-font-embed: embed;
            -fs-pdf-font-encoding: Identity-H;
        }

        * {
            font-family: "Arial Unicode MS";
            letter-spacing: 1px;
        }
    </style>
</head>
<body>

<div style="margin: 45px;">
    <#assign tmApp=args[0] />
    <#list  tmApp.tradeMark.wordSpecifications as spec>
        WORD ELEMENTS: ${spec.wordElements}
    </#list>
</div>


</body>
</html>
</#escape>
