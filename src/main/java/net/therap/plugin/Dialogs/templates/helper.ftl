package ${PACKAGE_NAME};

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<#--${IMPORTS}-->

@Component
public class ${CLASS_NAME}Helper {

private static final Logger log = LoggerFactory.getLogger(${CLASS_NAME}Helper.class);

    <#--${FIELDS}

    ${METHODS}-->
}