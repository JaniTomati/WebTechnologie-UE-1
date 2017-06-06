<!-- Web-Technologie 2017 - Jula (115167), Jana (115753), Christopher (114602), Josef (115850) -->
<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/">
<HTML>
<BODY>
<TABLE cellspacing="3" cellpadding="8">
   <TR bgcolor="#AAAAAA">
      <TD class="heading"><B>Vorname</B></TD>
      <TD class="heading"><B>Name</B></TD>
      <TD class="heading"><B>Einführungjahr</B></TD>
   </TR>
   <xsl:for-each select="peanuts/character">
    <xsl:sort select="surname"/>
    <xsl:if test="@intro>1960">
    <TR bgcolor="#DDDDDD">
       <TD width="25%" valign="top">
          <xsl:value-of select="name"/>
       </TD>
       <TD width="20%" valign="top">
          <xsl:value-of select="surname"/>
       </TD>
       <TD width="20%" valign="top">
          <xsl:value-of select="@intro"/>
       </TD>
     </TR>
    </xsl:if>
   </xsl:for-each>
</TABLE>
</BODY>
</HTML>
</xsl:template>
</xsl:stylesheet>