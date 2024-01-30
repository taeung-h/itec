package notice.noticespring.web.component;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Component

public class PDFGeneratorUtil {

    @Autowired
    private TemplateEngine templateEngine;

    public String createPdf(String templatename, Map map) throws IOException, DocumentException {

        String fileNameUrl = "";

        Context ctx = new Context();

        if (map != null) {

            Iterator itMap = map.entrySet().iterator();

            while (itMap.hasNext()) {

                Map.Entry pair = (Map.Entry) itMap.next();

                ctx.setVariable(pair.getKey().toString(), pair.getValue());



            }

        }

        String processedHtml = templateEngine.process(templatename, ctx);

        FileOutputStream os = null;

        String studentId = map.get("ID").toString();

        try {


            final File outputFile = File.createTempFile("education"+studentId+"_", ".pdf");

            os = new FileOutputStream(outputFile);


            ITextRenderer itr = new ITextRenderer();


            //이미지 PDF 추가를 위한 부분
            ImageReplaceElementFactory imageReplaceElementFactory =
                    new ImageReplaceElementFactory(itr.getSharedContext().getReplacedElementFactory());
            itr.getSharedContext().setReplacedElementFactory(imageReplaceElementFactory);


            //한글 폰트 추가를 위한 부분
            ClassPathResource classPathResource = new ClassPathResource("/static/font/NanumGothic.ttf");
            itr.getFontResolver().addFont(
                    // resources 아래에 있는 폰트 경로를 입력해준다
                    classPathResource.getURI().toString(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );



            itr.setDocumentFromString(processedHtml);

            itr.layout();

            itr.createPDF(os, false);

            itr.finishPDF();

            fileNameUrl = outputFile.getName();

        }

        finally {

            if (os != null) {

                try {

                    os.close();

                } catch (IOException e) { }

            }

        }

        return fileNameUrl;

    }

}
