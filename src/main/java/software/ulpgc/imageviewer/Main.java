package software.ulpgc.imageviewer;

import software.ulpgc.imageviewer.application.MainFrame;
import software.ulpgc.imageviewer.application.SwingImageDisplay;
import software.ulpgc.imageviewer.control.NextCommand;
import software.ulpgc.imageviewer.control.PreviousCommand;
import software.ulpgc.imageviewer.io.FileImageLoader;
import software.ulpgc.imageviewer.io.ImagePresenter;
import software.ulpgc.imageviewer.model.ImageCarousel;

import java.io.File;

public class Main {

    private static final String defaultPath = "./images/";

    public static void main(String[] args) {
        String imagePath = args.length > 0 ? args[0] : defaultPath;
        ImageCarousel imageCarousel = loadImages(imagePath);
        SwingImageDisplay swingImageDisplay = new SwingImageDisplay(imageCarousel);

        ImagePresenter presenter = new ImagePresenter(swingImageDisplay);
        presenter.show();

        MainFrame mainFrame = createMainFrame(swingImageDisplay);
        mainFrame.setVisible(true);
    }

    private static ImageCarousel loadImages(String path) {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a valid directory: " + path);
        }

        FileImageLoader loader = new FileImageLoader(folder);
        ImageCarousel imageCarousel = loader.load();
        if (imageCarousel == null) {
            throw new IllegalArgumentException("No images could be loaded from the directory: " + path);
        }
        return imageCarousel;
    }

    private static MainFrame createMainFrame(SwingImageDisplay display) {
        MainFrame mainFrame = new MainFrame(display);
        mainFrame.addCommand("next", new NextCommand(display));
        mainFrame.addCommand("previous", new PreviousCommand(display));
        return mainFrame;
    }
}

