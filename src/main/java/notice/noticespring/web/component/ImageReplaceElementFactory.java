package notice.noticespring.web.component;


import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageReplaceElementFactory implements ReplacedElementFactory {

    private ReplacedElementFactory replacedElementFactory;

    ImageReplaceElementFactory(ReplacedElementFactory replacedElementFactory)
    {
        this.replacedElementFactory = replacedElementFactory;
    }


    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth, int cssHeight) {

        final Element element;
        final String nodeName;
        final String srcPath;

        if (box.getElement() == null) {
            return null;
        } else {
            element = box.getElement();

            nodeName = element.getNodeName();
            srcPath = element.getAttribute("src");
        }

        if (nodeName.equals("img") && srcPath.startsWith("/image")) {
            try {
                ClassPathResource classPathResource = new ClassPathResource("static${element.getAttribute('src')}");

                ITextFSImage iTextFSImage = new ITextFSImage(
                        Image.getInstance(
                                Files.readAllBytes(
                                        Path.of(classPathResource.getURI())
                                )
                        )
                );

                if ((cssWidth != -1) || (cssHeight != -1)) {
                    iTextFSImage.scale(cssWidth, cssHeight);
                }
                ITextImageElement iTextImageElement = new ITextImageElement(iTextFSImage);
                return iTextImageElement;

            } catch (BadElementException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            return replacedElementFactory.createReplacedElement(c, box, uac, cssWidth, cssHeight);

        }


    }

    @Override
    public void remove(Element e){
        replacedElementFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        replacedElementFactory.setFormSubmissionListener(listener);
    }

    @Override
    public void reset(){
        replacedElementFactory.reset();
    }
}


