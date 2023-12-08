package view;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class PDFViewerGui extends JFrame {
    private PDDocument document;
    private PDFRenderer renderer;
    private int numPages;
    private int currentPage = 0;
    private JLabel label;
    private JScrollPane scrollPane;

    public PDFViewerGui(String pdfFilePath) {
        try {
            document = Loader.loadPDF(new File(pdfFilePath));
            renderer = new PDFRenderer(document);
            numPages = document.getNumberOfPages();

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle("Visualizador de PDF - Página " + (currentPage + 1) + " de " + numPages);
            setSize(800, 600);
            setBackground(new Color(5, 5, 5));
            
    		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    		int x = (screenSize.width - getWidth()) / 2;
    		int y = (screenSize.height - getHeight()) / 2;
    		setLocation(x, y);

            label = new JLabel(renderPage(currentPage));
            scrollPane = new JScrollPane(label);
            add(scrollPane, BorderLayout.CENTER);

            JButton previousButton = new JButton("Anterior");
            previousButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentPage > 0) {
                        currentPage--;
                        label.setIcon(renderPage(currentPage));
                        updateTitle();
                    }
                }
            });

            JButton nextButton = new JButton("Próxima");
            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentPage < numPages - 1) {
                        currentPage++;
                        label.setIcon(renderPage(currentPage));
                        updateTitle();
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(previousButton);
            buttonPanel.add(nextButton);
            add(buttonPanel, BorderLayout.SOUTH);

            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageIcon renderPage(int pageIndex) {
        try {
            BufferedImage image = renderer.renderImageWithDPI(pageIndex, 75);
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateTitle() {
        setTitle("Visualizador de PDF - Página " + (currentPage + 1) + " de " + numPages);
    }

}
