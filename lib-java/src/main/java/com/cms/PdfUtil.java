package com.cms;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PdfUtil {

    public static void main(String[] args) {
        toImage();
    }


    public static void toImage() {
        try {
            System.out.println("转换开始:" + System.currentTimeMillis());
            File file = new File("/Work/1.pdf");
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImage(i, 1f);
                ImageIO.write(image, "PNG", new File("/Work/" + i + ".png"));
            }
            System.out.println("转换完成:" + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
