package main.java.net.therap.plugin.Dialogs;

import com.intellij.ide.projectView.impl.nodes.PsiMethodNode;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.impl.ModuleManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import main.java.net.therap.plugin.model.DomainCommand;
import main.java.net.therap.plugin.utils.Component;
import main.java.net.therap.plugin.utils.Util;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author babar
 * @since 8/24/16.
 */
public class MainDialog extends DialogWrapper {

    private Project project;
    private String className;
    private String packageName;

    private JTextField serviceClassInput;
    private ComboBox serviceModuleInput;
    private JTextField servicePackageInput;
    private JCheckBox serviceCheckBox;

    private JTextField controllerClassInput;
    private ComboBox controllerModuleInput;
    private JTextField controllerPackageInput;
    private JCheckBox controllerCheckBox;

    private JCheckBox helperCheckBox;
    private JCheckBox commandCheckBox;
    private JCheckBox searchCheckBox;
    private JCheckBox uiCheckBox;

    private Configuration cfg;


    public MainDialog(@Nullable Project project) {
        super(project);
        init();
        setTitle("Main");

        this.project = project;
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "templates");

        getDomainWorkflow();
    }

    private String getDomainWorkflow() {
        String workflowName = "";

        workflowName = Util.GENERATOR_COMMAND.getDomainName().concat("Workflow");

        //workflowName = "OrderService.java";

        PsiClass[] classes = PsiShortNamesCache.getInstance(Util.mainProject).
                getClassesByName(workflowName, GlobalSearchScope.allScope(Util.mainProject));

        PsiField[] psiFields = classes[0].getAllFields();

        String fieldNames = "";

        for (PsiField field : psiFields) {
            fieldNames = field.getName() + ":";
        }

        PsiMethod[] psiMethods = classes[0].getAllMethods();

        Messages.showMessageDialog(project, psiMethods[0].getName(), "Alert", Messages.getInformationIcon());

        return workflowName;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {

        JPanel panel = new JPanel();
        //panel.setSize(1000, 500);

        GridLayout mainGrid = new GridLayout(4,2);
        panel.setLayout(mainGrid);

        /*panel.add(getDomainInfoPanel());
        panel.add(getServiceViewPanel());
        panel.add(getControllerViewPanel());
        panel.add(getCheckListPanel());*/

        /* Domain Info Panel */

        final JPanel domainInfoPanel = new JPanel();

        domainInfoPanel.setLayout(new GridLayout(2,2));
        domainInfoPanel.add(new JLabel("Domain"));
        domainInfoPanel.add(new JLabel());

        domainInfoPanel.add(new JLabel("Package"));
        domainInfoPanel.add(new JLabel());

        domainInfoPanel.setBorder(BorderFactory.createTitledBorder("Domain/Entity"));

        panel.add(domainInfoPanel);

        /* WorkFlow Info Panel */

        JPanel workFlowPanel = new JPanel();
        workFlowPanel.setLayout(new GridLayout(2,1));

        workFlowPanel.add(new JLabel("Workflow Name"));
        workFlowPanel.add(new JLabel("workflow name here"));

        workFlowPanel.setBorder(BorderFactory.createTitledBorder("Workflow"));
        panel.add(workFlowPanel);

        /* Service Info Panel */

        JPanel servicePanel = new JPanel();

        GridLayout serviceGrid = new GridLayout(4,2);
        servicePanel.setLayout(serviceGrid);

        servicePanel.add(new JLabel("Class"));
        String defaultServiceClass = Util.GENERATOR_COMMAND.getDomainName() + Component.SERVICE.getValue();
        serviceClassInput = new JTextField(defaultServiceClass);
        servicePanel.add(serviceClassInput);

        servicePanel.add(new JLabel("Module"));
        serviceModuleInput = new ComboBox(new DefaultComboBoxModel(getModuleList()));
        servicePanel.add(serviceModuleInput);

        servicePanel.add(new JLabel("Package"));
        servicePackageInput = new JTextField();
        servicePanel.add(servicePackageInput);

        serviceCheckBox = new JCheckBox("Generate Service Class");
        servicePanel.add(serviceCheckBox);

        servicePanel.setBorder(BorderFactory.createTitledBorder(Component.SERVICE.getValue()));
        panel.add(servicePanel);

        /* Controller info panel */

        JPanel controllerPanel = new JPanel();

        GridLayout controllerGrid = new GridLayout(4,2);
        controllerPanel.setLayout(controllerGrid);

        controllerPanel.add(new JLabel("Class"));
        String defaultControllerClass = Util.GENERATOR_COMMAND.getDomainName() + Component.CONTROLLER.getValue();
        controllerClassInput = new JTextField(defaultControllerClass);
        controllerPanel.add(controllerClassInput);

        controllerPanel.add(new JLabel("Module"));
        controllerModuleInput = new ComboBox(new DefaultComboBoxModel(getModuleList()));
        controllerPanel.add(controllerModuleInput);

        controllerPanel.add(new JLabel("Package"));
        controllerPackageInput = new JTextField();
        controllerPanel.add(controllerPackageInput);

        controllerCheckBox = new JCheckBox("Generate Controller Class");
        controllerPanel.add(controllerCheckBox);

        controllerPanel.setBorder(BorderFactory.createTitledBorder(Component.CONTROLLER.getValue()));
        panel.add(controllerPanel);

        /* CheckBox info panel */

        JPanel checkListPanel = new JPanel();
        checkListPanel.setLayout(new GridLayout(5, 1));

        helperCheckBox = new JCheckBox("Generate Helper");
        checkListPanel.add(helperCheckBox);

        commandCheckBox = new JCheckBox("Generate Command");
        checkListPanel.add(commandCheckBox);

        searchCheckBox = new JCheckBox("Generate Search");
        checkListPanel.add(searchCheckBox);

        uiCheckBox = new JCheckBox("Generate UI");
        checkListPanel.add(uiCheckBox);

        final JButton generateCode = new JButton("Generate Code");
        checkListPanel.add(generateCode);

        generateCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DomainCommand domainCommand = Util.GENERATOR_COMMAND;

                domainCommand.setServiceClassName(serviceClassInput.getText());
                domainCommand.setServiceModuleName(getSelectedModuleName(serviceModuleInput.getSelectedIndex()));
                domainCommand.setServicePackageName(servicePackageInput.getText());
                domainCommand.setGenerateServiceClass(serviceCheckBox.isSelected());

                domainCommand.setControllerClassName(controllerClassInput.getText());
                domainCommand.setControllerModuleName(getSelectedModuleName(controllerModuleInput.getSelectedIndex()));
                domainCommand.setControllerPackageName(controllerPackageInput.getText());
                domainCommand.setGenerateControllerClass(controllerCheckBox.isSelected());

                domainCommand.setGenerateHelperClass(helperCheckBox.isSelected());
                domainCommand.setGenerateCommandClass(commandCheckBox.isSelected());
                domainCommand.setGenerateSearchController(searchCheckBox.isSelected());
                domainCommand.setGenerateView(uiCheckBox.isSelected());

                if (getModuleList().length > 1) {
                    domainCommand.setMultiModuleProject(true);
                }

                try {
                    generateCode();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (TemplateException e1) {
                    e1.printStackTrace();
                }
            }
        });

        panel.add(checkListPanel);

        return panel;
    }

    private String getSelectedModuleName(int selectedIndex) {
        String[] moduleNames = getModuleList();
        return moduleNames[selectedIndex];
    }

    private String[] getModuleList() {
        Module[] allModules = ModuleManagerImpl.getInstanceImpl(Util.mainProject).getModules();
        String[] moduleNames = new String[allModules.length];

        for (int i=0; i< allModules.length; i++) {
            moduleNames[i] = allModules[i].getName();
        }

        return moduleNames;
    }

    private void generateCode() throws IOException, TemplateException {

        if (Util.GENERATOR_COMMAND.isGenerateServiceClass()) {
            generateServiceClass();
        }

        if (Util.GENERATOR_COMMAND.isGenerateControllerClass()) {
            generateControllerClass();
        }

        if (Util.GENERATOR_COMMAND.isGenerateHelperClass()) {
            generateHelperClass();
        }

        if (Util.GENERATOR_COMMAND.isGenerateCommandClass()) {
            generateCommandClass();
        }

        if (Util.GENERATOR_COMMAND.isGenerateSearchController()) {
            generateSearchControllerClass();
        }

        if (Util.GENERATOR_COMMAND.isGenerateView()) {

        }
    }

    private void generateServiceClass() throws IOException, TemplateException {
        String serviceFile = Util.GENERATOR_COMMAND.getServiceFileName();
        FileUtil.createIfNotExists(new File(serviceFile));
        Map<String,Object> serviceModel = new HashMap<>();
        populateModelForServiceClass(serviceModel);

        renderFtl(Component.SERVICE, serviceFile, serviceModel);
    }

    private void populateModelForServiceClass(Map<String, Object> model) {
        model.put("PACKAGE_NAME", Util.GENERATOR_COMMAND.getServicePackageName().concat(".service"));
        model.put("imports",new ArrayList<String>());
        model.put("CLASS_NAME", Util.GENERATOR_COMMAND.getDomainName());
        model.put("CLASS_INSTANCE_NAME", Util.GENERATOR_COMMAND.getInstanceName());
        model.put("FT_FORM_TYPE", "YOUR_FORM_TYPE_HERE");
    }

    private void generateControllerClass() throws IOException, TemplateException {
        String controllerFile = Util.GENERATOR_COMMAND.getControllerFileName();
        FileUtil.createIfNotExists(new File(controllerFile));
        Map<String, Object> controllerModel = new HashMap<>();
        populateModelForControllerClass(controllerModel);

        renderFtl(Component.CONTROLLER, controllerFile, controllerModel);
    }

    private void populateModelForControllerClass(Map<String, Object> model) {
        model.put("PACKAGE_NAME", Util.GENERATOR_COMMAND.getControllerPackageName().concat(".web.controller"));
        model.put("IMPORTS", "");
        model.put("REQUEST_PATH", "/" + Util.GENERATOR_COMMAND.getDomainName().toLowerCase());
        model.put("CLASS_NAME", Util.GENERATOR_COMMAND.getDomainName());
    }

    private void generateHelperClass() throws IOException, TemplateException {
        String helperFile = Util.GENERATOR_COMMAND.getHelperFileName();
        FileUtil.createIfNotExists(new File(helperFile));
        Map<String, Object> helperModel = new HashMap<>();
        populateModelForHelperClass(helperModel);

        renderFtl(Component.HELPER, helperFile, helperModel);
    }

    private void populateModelForHelperClass(Map<String, Object> model) {
        model.put("PACKAGE_NAME", Util.GENERATOR_COMMAND.getControllerPackageName().concat(".web.helper"));
        model.put("CLASS_NAME", Util.GENERATOR_COMMAND.getDomainName());
    }

    private void renderFtl(Component component, String fileName, Map<String, Object> model)
            throws IOException, TemplateException {
        Template template = cfg.getTemplate(component.getTemplateFileName());
        Writer file = new FileWriter(fileName);
        template.process(model, file);
        file.flush();
        file.close();
    }

    private void generateCommandClass() throws IOException, TemplateException {
        String commandFile = Util.GENERATOR_COMMAND.getCommandFileName();
        FileUtil.createIfNotExists(new File(commandFile));
        Map<String, Object> commandModel = new HashMap<>();
        populateModelForCommandClass(commandModel);

        renderFtl(Component.COMMAND, commandFile, commandModel);
    }

    private void populateModelForCommandClass(Map<String, Object> model) {
        model.put("PACKAGE_NAME", Util.GENERATOR_COMMAND.getControllerPackageName().concat(".web.commnad"));
        model.put("CLASS_NAME", Util.GENERATOR_COMMAND.getDomainName());
        model.put("CLASS_INSTANCE_NAME", Util.GENERATOR_COMMAND.getInstanceName());
    }

    private void generateSearchControllerClass() throws IOException, TemplateException {
        String searchControllerFile = Util.GENERATOR_COMMAND.getSearchControllerFileName();
        FileUtil.createIfNotExists(new File(searchControllerFile));
        Map<String, Object> searchControllerModel = new HashMap<>();
        populateModelForSearchControllerClass(searchControllerModel);

        renderFtl(Component.SEARCH_CONTROLLER, searchControllerFile, searchControllerModel);

        generateSearchCommandClass();
    }

    private void populateModelForSearchControllerClass(Map<String, Object> model) {
        model.put("PACKAGE_NAME", Util.GENERATOR_COMMAND.getControllerPackageName().concat(".web.controller"));
        model.put("CLASS_NAME", Util.GENERATOR_COMMAND.getDomainName());
    }

    private void generateSearchCommandClass() throws IOException, TemplateException {
        String searchCommandFile = Util.GENERATOR_COMMAND.getSearchCommandFileName();
        FileUtil.createIfNotExists(new File(searchCommandFile));
        Map<String, Object> searchCommandModel = new HashMap<>();
        populateModelForSearchCommandClass(searchCommandModel);

        renderFtl(Component.SEARCH_COMMAND, searchCommandFile, searchCommandModel);
    }

    private void populateModelForSearchCommandClass(Map<String, Object> model) {
        model.put("PACKAGE_NAME", Util.GENERATOR_COMMAND.getControllerPackageName().concat(".web.command"));
        model.put("CLASS_NAME", Util.GENERATOR_COMMAND.getDomainName());
        model.put("CLASS_INSTANCE_NAME", Util.GENERATOR_COMMAND.getInstanceName());
        model.put("DEFAULT_SORT_COL","YOUR_DEFAULT_SORT_COL");
        model.put("LOADER_PARAM", "YOUR_LOADER_PARAM");
        model.put("FT_FORM_TYPE", "YOUR_FORM_TYPE");
    }
}
