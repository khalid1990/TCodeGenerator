package main.java.net.therap.plugin.model;

import main.java.net.therap.plugin.utils.Component;
import main.java.net.therap.plugin.utils.Util;

import java.io.File;
import java.io.Serializable;

/**
 * @author babar
 * @since 8/30/16
 */
public class DomainCommand implements Serializable {

    private String domainName;

    private String instanceName;

    private String serviceClassName;

    private String serviceModuleName;

    private String servicePackageName;

    private boolean generateServiceClass;

    private String controllerClassName;

    private String controllerModuleName;

    private String controllerPackageName;

    private boolean generateControllerClass;

    private boolean generateHelperClass;

    private boolean generateCommandClass;

    private boolean generateSearchController;

    private boolean generateView;

    private boolean multiModuleProject;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getServiceModuleName() {
        return serviceModuleName;
    }

    public void setServiceModuleName(String serviceModuleName) {
        this.serviceModuleName = serviceModuleName;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public boolean isGenerateServiceClass() {
        return generateServiceClass;
    }

    public void setGenerateServiceClass(boolean generateServiceClass) {
        this.generateServiceClass = generateServiceClass;
    }

    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public String getControllerModuleName() {
        return controllerModuleName;
    }

    public void setControllerModuleName(String controllerModuleName) {
        this.controllerModuleName = controllerModuleName;
    }

    public String getControllerPackageName() {
        return controllerPackageName;
    }

    public void setControllerPackageName(String controllerPackageName) {
        this.controllerPackageName = controllerPackageName;
    }

    public boolean isGenerateControllerClass() {
        return generateControllerClass;
    }

    public void setGenerateControllerClass(boolean generateControllerClass) {
        this.generateControllerClass = generateControllerClass;
    }

    public boolean isGenerateHelperClass() {
        return generateHelperClass;
    }

    public void setGenerateHelperClass(boolean generateHelperClass) {
        this.generateHelperClass = generateHelperClass;
    }

    public boolean isGenerateCommandClass() {
        return generateCommandClass;
    }

    public void setGenerateCommandClass(boolean generateCommandClass) {
        this.generateCommandClass = generateCommandClass;
    }

    public boolean isGenerateSearchController() {
        return generateSearchController;
    }

    public void setGenerateSearchController(boolean generateSearchController) {
        this.generateSearchController = generateSearchController;
    }

    public boolean isGenerateView() {
        return generateView;
    }

    public void setGenerateView(boolean generateView) {
        this.generateView = generateView;
    }

    public boolean isMultiModuleProject() {
        return multiModuleProject;
    }

    public void setMultiModuleProject(boolean multiModuleProject) {
        this.multiModuleProject = multiModuleProject;
    }

    public String getServiceFileName() {
        return getFileName(serviceModuleName, servicePackageName, serviceClassName, Component.SERVICE);
    }

    public String getControllerFileName() {
        return getFileName(controllerModuleName, controllerPackageName, controllerClassName, Component.CONTROLLER);
    }

    public String getHelperFileName() {
        return getFileName(controllerModuleName, controllerPackageName, domainName.concat(Component.HELPER.getValue()),
                Component.HELPER);
    }

    public String getCommandFileName() {
        return getFileName(controllerModuleName, controllerPackageName, domainName.concat(Component.COMMAND.getValue()),
                Component.COMMAND);
    }

    public String getSearchControllerFileName () {
        return getFileName(controllerModuleName, controllerPackageName,
                domainName.concat(Component.SEARCH_CONTROLLER.getValue()), Component.SEARCH_CONTROLLER);
    }

    public String getSearchCommandFileName() {
        return getFileName(controllerModuleName, controllerPackageName,
                domainName.concat(Component.SEARCH_COMMAND.getValue()), Component.SEARCH_COMMAND);
    }

    public String getFileName(String moduleName, String packageName, String className, Component component) {
        StringBuilder sb = new StringBuilder();

        sb.append(Util.mainProject.getBasePath());

        if (isMultiModuleProject()) {
            sb.append(File.separator);
            sb.append(moduleName);
        }

        sb.append("/src/main/java/");

        String[] splits = packageName.split("\\.");
        for(String token : splits) {
            sb.append(token);
            sb.append(File.separator);
        }

        if (!component.equals(Component.SERVICE)) {
            sb.append("web").append(File.separator);
        }

        if (component.equals(Component.SEARCH_CONTROLLER)) {
            sb.append("controller").append(File.separator);
        } else if (component.equals(Component.SEARCH_COMMAND)) {
            sb.append("command").append(File.separator);
        } else {
            sb.append(component.getValue().toLowerCase()).append(File.separator);
        }

        sb.append(className + Util.selectedComponent + ".java");

        return sb.toString();
    }

}
