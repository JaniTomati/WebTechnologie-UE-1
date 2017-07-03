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
       <link rel="stylesheet" type="text/css" href="./feed_reader.css"/>
       <link rel="icon" href="./128px-Feed-icon.svg.png"/>
       <script type="text/javascript" src="./feedreader.js"/>
   </head>
   <body>
     <header>
       <img src="feed.svg"/>
       <h1>Feed Reader</h1>
     </header>
     <input type="button" id="hide" value="Show Form" onClick="Hide()"/>
     <div class="newEntry" id="toggle">
       <h2>Add a New Entry</h2>
       <form id="add-entry" action="Feed" method="post">
           <div>
               <label for="title">Title:</label>
               <input class="input" id="title" name="title" placeholder="The HTML &lt;form&gt; element" required="required"/>
           </div>
           <div>
               <label for="url">URL:</label>
               <input class="input" type="url" id="url" name="url" placeholder="https://developer.mozilla.org/en-US/docs/Web/HTML/Element/form" required="required"/>
           </div>
           <div>
               <label for="author">Author:</label>
               <input class="input" id="author" name="author" placeholder="Mozilla" required="required"/>
           </div>
           <div>
               <label for="summary">Summary:</label>
               <textarea class="input" id="summary" name="summary" rows="3" placeholder="The HTML &lt;form&gt; element represents a document section that contains interactive controls to submit information to a web server."></textarea>
           </div>
           <input class="entryButton" type="submit" id="submit" value="Add new entry"/>
       </form>
     </div>
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
    <p><b>Last updated: </b><time date="2017-05-06"><xsl:value-of select="updated"/></time></p>
  </aside>
  <main>
  <xsl:for-each select="entry">
    <xsl:sort select="updated" order="descending"/>
    <section>
      <h2><a href="{link/@href}"><xsl:value-of select="title"/></a></h2>
      <p><xsl:value-of select="summary"/></p>
      <p><b>Author: </b> <xsl:value-of select="author/name"/></p>
      <p><b>Last updated: </b> <time date="2017-05-06"><xsl:value-of select="updated"/></time></p>
    </section>
  </xsl:for-each>
  </main>
  </xsl:template>
</xsl:stylesheet>
