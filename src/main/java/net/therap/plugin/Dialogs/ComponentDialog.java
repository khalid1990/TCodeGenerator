package main.java.net.therap.plugin.Dialogs;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.impl.ModuleManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import main.java.net.therap.plugin.utils.Component;
import main.java.net.therap.plugin.utils.Util;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author babar
 * @since 8/25/16.
 */
public class ComponentDialog extends DialogWrapper {

    public ComponentDialog(@Nullable Project project) {
        super(project);
        init();
        setTitle("Generate "+ Util.selectedComponent +" Code");
    }

    @Nullable
    @Override
    public JComponent createCenterPanel() {

        String defaultClassName = Util.CLASS_NAME;

        JPanel jPanel = new JPanel();

        GridLayout gridLayout = new GridLayout(4, 2);
        jPanel.setLayout(gridLayout);

        jPanel.add(new JLabel("Class Name"));
        final JTextField className = new JTextField(defaultClassName);
        jPanel.add(className);

        jPanel.add(new JLabel("Module"));
        final ComboBox comboBox = new ComboBox(new DefaultComboBoxModel(getModuleList()));
        jPanel.add(comboBox);

        jPanel.add(new JLabel("Package"));

        JTextField chosenPackage = new JTextField();
        final JTextField packageNameInput = new JTextField();
        //packageNameInput.set
        jPanel.add(packageNameInput);

        jPanel.add(new JLabel(""));
        JButton jButton = new JButton("Generate Code");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedModule = getSelectedModuleName(comboBox.getSelectedIndex());
                StringBuilder sb = new StringBuilder();
                sb.append(Util.mainProject.getBasePath());

                if (getModuleList().length > 1) {
                    sb.append(File.separator);
                    sb.append(selectedModule);
                }

                sb.append("/src/main/java/");

                String packageName = packageNameInput.getText();
                String[] splits = packageName.split("\\.");
                for(String token : splits) {
                    sb.append(token);
                    sb.append(File.separator);
                }

                sb.append(className.getText() + Util.selectedComponent+".java");

                String fileName = sb.toString();
                //Messages.showMessageDialog(project, fileName, "Alert", Messages.getInformationIcon());

                FileUtil.createIfNotExists(new File(fileName));

                try {
                    Util.GENERATOR_COMMAND.setInstanceName(getInstanceName());

                    createFileFromTemplate(fileName, className.getText(), packageName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (TemplateException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }

            }
        });
        jPanel.add(jButton);

        return jPanel;
    }

    private void createFileFromTemplate(String fileName, String className,
                                        String packageName) throws IOException, TemplateException, URISyntaxException {

        //Messages.showMessageDialog(project, , "Alert", Messages.getInformationIcon());

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "templates");
        Template template = cfg.getTemplate(Util.templateName);

        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        /*data.put("message", "Hello World!");

        //List parsing
        List<String> countries = new ArrayList<String>();
        countries.add("Bangladesh");
        countries.add("United States");
        countries.add("Germany");
        countries.add("France");
        countries.add("Bulgaria");

        data.put("countries", countries);*/
        if (Component.SERVICE.getValue().equals(Util.selectedComponent)) {
            populateModelForServiceClass(data, packageName, className);
        }
        // File output
        Writer file = new FileWriter(fileName);
        template.process(data, file);
        file.flush();
        file.close();

    }

    private void populateModelForServiceClass(Map<String, Object> model, String packageName, String className) {
        model.put("PACKAGE_NAME", packageName);
        model.put("imports",new ArrayList<String>());
        model.put("CLASS_NAME", className);
        model.put("CLASS_INSTANCE_NAME", getInstanceName());
        model.put("FT_FORM_TYPE", "LOC");

    }

    private String getInstanceName() {

        String className = Util.GENERATOR_COMMAND.getDomainName();
        char[] chars = className.toCharArray();
        chars[0] = (char) (chars[0] + 32);

        return new String(chars);
    }

    private String getSelectedModuleName(int selectedIndex) {
        String[] moduleNames = getModuleList();
        return moduleNames[selectedIndex];
    }

    /*private void openPackageChooserDialog(JTextField chosenPackage, int selectedIndex) {



        Module module = null;

        Module[] modules = ModuleManagerImpl.getInstanceImpl(Util.mainProject).getModules();

        for (Module mod : modules) {
            if (mod.getName().equals(selectedModuleName)) {
                module = mod;
                break;
            }
        }

        PackageChooserDialog dialog = new PackageChooserDialog("Select a Java package", module);
        dialog.show();

        PsiPackage psiPackage = dialog.getSelectedPackage();

        String msg = psiPackage != null ? psiPackage.getQualifiedName() :
                (Util.PACKAGE_NAME.isEmpty() ? null : Util.PACKAGE_NAME);
        chosenPackage.setText(msg);

    }*/

    private String[] getModuleList() {
        Module[] allModules = ModuleManagerImpl.getInstanceImpl(Util.mainProject).getModules();
        String[] moduleNames = new String[allModules.length];

        for (int i=0; i< allModules.length; i++) {
            moduleNames[i] = allModules[i].getName();
        }

        return moduleNames;
    }

}
