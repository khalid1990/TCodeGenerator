package ${PACKAGE_NAME};

<#--${IMPORTS}-->

import java.io.Serializable;

public class ${CLASS_NAME}Command implements Serializable {

    private static final long serialVersionUID = 1L;

    private ${CLASS_NAME} ${CLASS_INSTANCE_NAME};

    private ActionView av;

    private String backLink;

    private Integer backType;

    public ${CLASS_NAME}Command() {
    }

    public ${CLASS_NAME}Command(${CLASS_NAME} ${CLASS_INSTANCE_NAME}, ActionView av, String backLink, Integer backType) {
        this.${CLASS_INSTANCE_NAME} = ${CLASS_INSTANCE_NAME};
        this.av = av;
        this.backLink = backLink;
        this.backType = backType;
    }
}