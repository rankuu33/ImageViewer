package software.ulpgc.imageviewer.application;

import software.ulpgc.imageviewer.model.ImageCarousel;
import software.ulpgc.imageviewer.view.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;


public class SwingImageDisplay extends JPanel implements ImageDisplay {

    private record Paint(ImageIcon image, int offset) {}

    private final List<Paint> paints = new ArrayList<>();
    private final ImageCarousel imageCarousel;
    private Shift shift = Shift.Null;
    private Released released = Released.Null;
    private int initShift;

    public SwingImageDisplay(ImageCarousel imageCarousel) {
        this.imageCarousel = imageCarousel;
        initializeListeners();
    }

    private void initializeListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initShift = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released.offset(e.getX() - initShift);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX() - initShift);
            }
        });
    }

    @Override
    public void showCurrent() {
        showImage(0, imageCarousel.id());
    }

    @Override
    public void paintCurrent(int offset) {
        showImage(offset, imageCarousel.id());
    }

    @Override
    public void paintNext(int offset) {
        showImage(offset, imageCarousel.nextId());
    }

    @Override
    public void paintPrevious(int offset) {
        showImage(offset, imageCarousel.previousId());
    }

    @Override
    public void showNext() {
        showImage(0, imageCarousel.next().id());
    }

    @Override
    public void showPrevious() {
        showImage(0, imageCarousel.previous().id());
    }

    private void showImage(int offset, String id) {
        if (!paints.isEmpty() && offset == 0) {
            paints.set(0, createPaint(id, offset));
        } else {
            loadImage(offset, id);
        }
        repaint();
    }

    private Paint createPaint(String id, int offset) {
        File imageFile = new File("./images/" + id);
        if (!imageFile.exists()) {
            System.err.println("File not found: " + imageFile.getAbsolutePath());
            return null;
        }
        return new Paint(new ImageIcon(imageFile.getAbsolutePath()), offset);
    }

    private void loadImage(int offset, String id) {
        Paint paint = createPaint(id, offset);
        if (paint != null) {
            paints.add(paint);
        }
    }

    @Override
    public void clear() {
        paints.clear();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Paint paint : paints) {
            if (paint.image == null) continue;

            int imageWidth = paint.image.getIconWidth();
            int imageHeight = paint.image.getIconHeight();

            double widthRatio = (double) getWidth() / imageWidth;
            double heightRatio = (double) getHeight() / imageHeight;
            double minRatio = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (imageWidth * minRatio);
            int newHeight = (int) (imageHeight * minRatio);

            int x = (getWidth() - newWidth) / 2 + paint.offset;
            int y = (getHeight() - newHeight) / 2;

            g.drawImage(paint.image.getImage(), x, y, newWidth, newHeight, this);
        }
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift != null ? shift : Shift.Null;
    }

    @Override
    public void on(Released released) {
        this.released = released != null ? released : Released.Null;
    }
}

