package notice.noticespring.web.service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Image;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import notice.noticespring.web.domain.CompletedMember;
import notice.noticespring.web.domain.Education;
import notice.noticespring.web.domain.Member;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;




@Service
public class PDFService {

    private final TemplateEngine templateEngine;

    public PDFService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generateSignPdf(Education education, List<Member> members, Member instructor,String outputFilePath) throws Exception {
        // Create Thymeleaf context
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("edu", education);

        int count=1;

        for(Member member : members) {
            try {
                thymeleafContext.setVariable("member"+ Integer.toString(count), member);
                count ++;

            } catch (IllegalStateException e) {

            }
        }

        thymeleafContext.setVariable("instructor", instructor.getName());
        thymeleafContext.setVariable("instructorSignPath", instructor.getSignpath());


        thymeleafContext.setVariable("nomembernumber", "-");
        thymeleafContext.setVariable("nomembernumberreason", "-");


        // Render Thymeleaf template to HTML
        String htmlContent = templateEngine.process("complete/printexcel", thymeleafContext);

        // Generate PDF using Flying Saucer
        try (OutputStream os = new FileOutputStream(outputFilePath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
        }
    }

    public void test(String title, String content,String path, String templeteName, String outputFilePath) throws Exception {



        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(fos));
            Document document = new Document(pdfDocument, PageSize.A4);


            // Thymeleaf 템플릿 엔진 생성
            ITemplateEngine templateEngine = new SpringTemplateEngine();
            Context thymeleafContext = new Context();


            thymeleafContext.setVariable("title", title);
            thymeleafContext.setVariable("content", content);
            thymeleafContext.setVariable("path", path);

            // Thymeleaf 템플릿을 HTML로 렌더링
            String processedHtml = templateEngine.process(templeteName, thymeleafContext);

            // Log or debug processed HTML
            System.out.println("Processed HTML: " + processedHtml);

            HtmlConverter.convertToPdf(processedHtml, pdfDocument.getWriter());

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error generating PDF", e);
        }
    }

}

