package notice.noticespring.web.component;

import org.hibernate.mapping.Any;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.print.DocFlavor;
import javax.swing.*;
import java.lang.module.Configuration;
import java.util.Map;

public class ThymeleafParser
{
    public String parsing(String fileName, Map<String, Object> variableMap){

        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);

        Context context = new Context();
        context.setVariables(variableMap);

        return templateEngine.process(fileName, context);

    }

}
