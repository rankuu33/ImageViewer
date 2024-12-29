package software.ulpgc.imageviewer.model;

public interface ImageCarousel {
    String id();
    String nextId();
    String previousId();
    ImageCarousel next();
    ImageCarousel previous();
}
