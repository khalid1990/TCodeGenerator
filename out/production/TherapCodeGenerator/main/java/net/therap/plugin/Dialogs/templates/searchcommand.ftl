package ${PACKAGE_NAME}

<#--${IMPORTS}-->

import java.io.Serializable;

@FinderParams(
    browserTitle = "${CLASS_NAME} :: ${CLASS_NAME}",
    pageTitle = "label.${CLASS_INSTANCE_NAME}.search",
    defaultSortCol = "${DEFAULT_SORT_COL}",
    loaderParam = "${LOADER_PARAM}",
    moduleName = CommonForm.${FT_FORM_TYPE}
)
public class ${CLASS_NAME}SearchCommand extends AbstractSearchCmd {

    private static final long serialVersionUID = 1L;

   <#-- ${FIELDS}

    ${METHODS}-->

    @Override
    public Role[] getAllRoles() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Map<Role, int[]> getAllowedStatusMap() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Set<Integer> getListStatusSet() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String[] getSearchFields() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String[] getSearchFieldsOrder() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String getSearchSql(ListOutputType listOutputType) {
        return BASE_QUERY;
    }

    @Override
    public String listerWhereFormatter() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String providerAliasFormatter() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean useAFSLister() {
        throw new UnsupportedOperationException("TODO");
    }
}