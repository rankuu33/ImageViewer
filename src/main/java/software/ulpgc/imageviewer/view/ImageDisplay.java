package software.ulpgc.imageviewer.view;

public interface ImageDisplay {


    int getWidth();
    void clear();
    void on(Shift shift);
    void on(Released released);

    interface Shift {
        Shift Null = offset -> {
        };

        void offset(int offset);
    }

    interface Released {
        Released Null = offset -> {
        };

        void offset(int offset);
    }

    void paintCurrent(int offset);

    void paintNext(int offset);

    void showCurrent();

    void paintPrevious(int offset);

    void showNext();

    void showPrevious();

}
