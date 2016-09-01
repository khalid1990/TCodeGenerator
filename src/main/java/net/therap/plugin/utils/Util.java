package main.java.net.therap.plugin.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import main.java.net.therap.plugin.model.DomainCommand;

/**
 * @author babar
 * @since 8/25/16.
 */
public class Util {

    public static String CLASS_NAME = "";
    public static String PACKAGE_NAME = "";
    public static PsiFile psiFile;
    public static Project mainProject;
    public static String selectedComponent = "";
    public static String templateName = "test.ftl";

    public static DomainCommand GENERATOR_COMMAND = new DomainCommand();

    public static String getInstanceName() {

        String className = Util.GENERATOR_COMMAND.getDomainName();
        char[] chars = className.toCharArray();
        chars[0] = (char) (chars[0] + 32);

        return new String(chars);
    }

}
