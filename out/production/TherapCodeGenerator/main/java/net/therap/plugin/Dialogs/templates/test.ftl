FreeMarker Template example: ${message}

=======================
===  Country List   ====
=======================
<#list countries as country>
${country_index + 1}. ${country}
</#list>