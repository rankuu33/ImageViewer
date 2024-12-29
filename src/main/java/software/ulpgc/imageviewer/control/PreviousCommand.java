package software.ulpgc.imageviewer.control;

import software.ulpgc.imageviewer.view.ImageDisplay;

public class PreviousCommand implements Command {
    private final ImageDisplay display;

    public PreviousCommand(ImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.clear();
        display.showPrevious();
    }
}
