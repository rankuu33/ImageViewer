package software.ulpgc.imageviewer.io;

import software.ulpgc.imageviewer.view.ImageDisplay;


public class ImagePresenter {

    private final ImageDisplay display;

    public ImagePresenter(ImageDisplay display) {
        this.display = display;
        this.display.on((ImageDisplay.Shift) this::shift);
        this.display.on((ImageDisplay.Released) this::released);
    }

    private void shift(int offset) {
        display.clear();
        display.paintCurrent(offset);
        if (offset >= 0) {
            display.paintPrevious(offset - display.getWidth());
        } else {
            display.paintNext(display.getWidth() + offset);
        }
    }

    private void released(int offset) {
        display.clear();
        if (Math.abs(offset) < display.getWidth() / 2) {
            display.showCurrent();
            return;
        }
        if (offset < 0) {
            display.showNext();
        } else {
            display.showPrevious();
        }

    }

    public void show() {
        this.display.clear();
        this.display.showCurrent();
    }

}
