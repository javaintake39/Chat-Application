<?xml version="1.0" encoding="UTF-8"?>
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <body style="font-family:Arial;font-size:12pt;background-color:#EEEEEE">
        
        <h2>Chat Messages</h2>
        <xsl:for-each select="MyMsg/Msg">
            <div style="background-color:teal;color:white;padding:4px">
                 <p>
                    <xsl:value-of select="chattype"/>
                </p>
                <span style="font-weight:bold">
                    <xsl:value-of select="from"/> -to </span>
                <xsl:value-of select="to"/>
            </div>
            <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
                <p>
                    <xsl:value-of select="content"/>
                    <span style="font-style:italic"> (<xsl:value-of select="date"/> date of sending)</span>
                </p>
                
            </div>
        </xsl:for-each>
    </body>
</html>