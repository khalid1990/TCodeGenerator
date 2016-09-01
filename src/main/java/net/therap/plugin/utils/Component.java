package main.java.net.therap.plugin.utils;

/**
 * @author babar
 * @since 8/24/16.
 */
public enum Component {

    SERVICE("Service", 0),
    CONTROLLER("Controller", 1),
    HELPER("Helper", 2),
    VIEW("View", 3),
    VALIDATOR("Validator", 4),
    COMMAND("Command", 5),
    SEARCH_CONTROLLER("SearchController", 6),
    SEARCH_COMMAND("SearchCommand", 7);

    private final String value;
    private final int id;
    private final String templateFileName;

    private Component(String value, int id) {
        this.value = value;
        this.id = id;
        this.templateFileName = this.value.toLowerCase() + ".ftl";
    }

    public String getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    private static final Component[] allComponents = {SERVICE, CONTROLLER, HELPER,
            VIEW, VALIDATOR, COMMAND, SEARCH_CONTROLLER, SEARCH_COMMAND};

    public static String[] getSelectableComponentList() {
        String[] componentList = new String[allComponents.length];

        for (Component component : allComponents) {
            componentList[component.getId()] = component.getValue();
        }

        return componentList;
    }

    public static Component getComponent(int id) {

        for (Component component : allComponents) {
            if (component.getId() == id) {
                return component;
            }
        }

        return null;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }
}
