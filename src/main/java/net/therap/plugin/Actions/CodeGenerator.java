package main.java.net.therap.plugin.Actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import main.java.net.therap.plugin.Dialogs.MainDialog;
import main.java.net.therap.plugin.utils.Util;

/**
 * @author babar
 * @since 8/24/16.
 */
public class CodeGenerator extends AnAction {

    public CodeGenerator() {
        super("Generate Therap Code");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);

        if (project == null) {
            return;
        }

        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);

        StringBuilder sb = new StringBuilder();

        if (psiFile instanceof PsiJavaFile) {
            PsiJavaFile javaFile = (PsiJavaFile) psiFile;
            PsiClass[] classes = javaFile.getClasses();
            PsiClass psiClass = classes[0];

            Util.CLASS_NAME = psiClass.getName();
            Util.PACKAGE_NAME = javaFile.getPackageName();
            Util.psiFile = javaFile;
            Util.mainProject = project;

            Util.GENERATOR_COMMAND.setDomainName(psiClass.getName());
            Util.GENERATOR_COMMAND.setInstanceName(Util.getInstanceName());

            /*sb.append("Domain Class: ").append(className);
            sb.append("Package: ").append(packageName);*/
//            sb.append("\nController Class: ").append(name).append("Controller")
//                    .append("\nHelper Class: ").append(name).append("Helper")
//                    .append("\nService Class: ").append(name).append("Service");
//
//            for (PsiField field : psiClass.getFields()) {
//                sb.append("\nName: ").append(field.getName()).append(" type: ").append(field.getType().getCanonicalText());
//            }
        }

        String message = sb.toString();

        //Messages.showMessageDialog(project, project.getBasePath(), "Alert", Messages.getInformationIcon());

        MainDialog dialog = new MainDialog(project);
        dialog.show();
    }

}

