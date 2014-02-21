<?xml version="1.0" encoding="UTF-8"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"> 
<xsl:output indent="yes" encoding="UTF-8" method="xml"/>

	<xsl:template match="/">
	  <Categories>
	       <xsl:apply-templates select="category">
 				<xsl:with-param name="path" select="current()"/>  
	     	</xsl:apply-templates>
	    </Categories>
	</xsl:template> 
	
	<xsl:template match="category">
	 <xsl:param name="path"/> 
	 <Category>
	 	<xsl:attribute name="id">
	 		<xsl:value-of select="@id" />
	 	</xsl:attribute>
	 	<xsl:attribute name="path">
         <xsl:call-template name="genPath"/>
        </xsl:attribute>
         <xsl:attribute name="alsoFoundIn">
        	 <xsl:apply-templates select="alsoFoundIn">
 				<xsl:with-param name="found" select="current()"/>  
	     	</xsl:apply-templates>
        </xsl:attribute>
	  </Category>
        <xsl:if test="count(category) > 0">
          	<xsl:apply-templates select="category">
          		<xsl:with-param name="path" select="current()"/> 
          	</xsl:apply-templates>
        </xsl:if>
	</xsl:template>
	
	<xsl:template name="genPath">
    <xsl:param name="prevPath"/>
    <xsl:variable name="currPath" select="concat('|',@name,$prevPath)"/>
    <xsl:for-each select="parent::*">
      <xsl:call-template name="genPath">
        <xsl:with-param name="prevPath" select="$currPath"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:if test="not(parent::*)">
      <xsl:value-of disable-output-escaping="yes" select="substring-after($currPath,'|')"/>      
    </xsl:if>
  </xsl:template>
  
    <xsl:template match="alsoFoundIn">
  		<xsl:param name="foundNodes"/>
    	<xsl:value-of disable-output-escaping="yes" select="concat(',',@id,$foundNodes)"/>
  	</xsl:template>
</xsl:stylesheet>