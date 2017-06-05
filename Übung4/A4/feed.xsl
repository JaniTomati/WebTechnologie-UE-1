<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<!-- Web-Technologie 2017 - Jula (115167), Jana (115753), Christopher (114602), Josef (115850) -->
   <xsl:output method="html"/>
   <xsl:template match="/">
   <html>
   <head>
     <title><xsl:value-of select="title"/></title>
       <meta charset="utf-8"/>
       <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
       <link rel="stylesheet" type="text/css" href="feed_reader.css"/>
       <link rel="icon" href="./128px-Feed-icon.svg.png"/>
   </head>
   <body>
     <header>
       <img src="feed.svg"/>
       <h1>Feed Reader</h1>
     </header>
     <div class="container">
       <xsl:apply-templates/>
     </div>
     <footer>
       <p><b>Web-Technology Feed Reader:</b> Submission of Jula, Christopher, Josef and Jana </p>
     </footer>
   </body>
   </html>
   </xsl:template>

<xsl:template match="/feed">
  <aside>
    <xsl:variable name="hyperlink"><xsl:value-of select="id"/></xsl:variable>
    <h3><a href="{$hyperlink}"><xsl:value-of select="title"/></a></h3>
    <p><xsl:value-of select="author/name"/></p>
    <p><xsl:value-of select="subtitle"/></p>
    <p>Last updated: <time date="2017-05-06"><xsl:value-of select="updated"/></time></p>
  </aside>
  <main>
  <xsl:for-each select="entry">
  <section>
    <h2><a href="{link/@href}"><xsl:value-of select="title"/></a></h2>
    <p><xsl:value-of select="summary"/></p>
    <p>Last updated: <time date="2017-05-06"><xsl:value-of select="updated"/></time></p>
  </section>
  </xsl:for-each>
  </main>
  </xsl:template>
</xsl:stylesheet>
