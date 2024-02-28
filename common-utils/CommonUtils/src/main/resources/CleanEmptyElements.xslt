<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" />

    <xsl:template match="@*|node()">
        <xsl:if test="normalize-space(.) != '' or ./@* != ''">
            <xsl:copy>
                <xsl:copy-of select = "@*"/>
                <xsl:apply-templates/>
            </xsl:copy>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
