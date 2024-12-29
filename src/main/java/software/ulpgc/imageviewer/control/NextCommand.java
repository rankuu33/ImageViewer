package software.ulpgc.imageviewer.control;

import software.ulpgc.imageviewer.view.ImageDisplay;


public class NextCommand implements Command {

    private final ImageDisplay display;

    public NextCommand(ImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.clear();
        display.showNext();
    }

}
