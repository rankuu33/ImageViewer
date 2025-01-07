package software.ulpgc.imageviewer.io;

import software.ulpgc.imageviewer.model.ImageCarousel;

import java.io.File;
import java.io.FileFilter;

public class FileImageLoader implements ImageLoader {

    private static final String[] supportedExtensions = {".png", ".jpg", ".jpeg"};
    private final File[] files;

    public FileImageLoader(File folder) {
        if (folder == null || !folder.isDirectory()) {
            throw new IllegalArgumentException("The provided folder is invalid: " + folder);
        }

        this.files = folder.listFiles(isImage());
        if (files == null || files.length == 0) {
            System.err.println("No images were found in the directory: " + folder.getAbsolutePath());
        }
    }

    private FileFilter isImage() {
        return file -> {
            if (!file.isFile()) return false;
            String fileName = file.getName().toLowerCase();
            for (String ext : supportedExtensions) {
                if (fileName.endsWith(ext)) {
                    return true;
                }
            }
            return false;
        };
    }

    @Override
    public ImageCarousel load() {
        if (files == null || files.length == 0) {
            System.err.println("No images to load.");
            return null;
        }
        return createImageCarousel();
    }

    private ImageCarousel createImageCarousel() {
        return new ImageCarousel() {
            private int index = 0;

            @Override
            public String id() {
                return getFileName(index);
            }

            @Override
            public String nextId() {
                return getFileName((index + 1) % files.length);
            }

            @Override
            public String previousId() {
                return getFileName((index - 1 + files.length) % files.length);
            }

            @Override
            public ImageCarousel next() {
                index = (index + 1) % files.length;
                return this;
            }

            @Override
            public ImageCarousel previous() {
                index = (index - 1 + files.length) % files.length;
                return this;
            }

            private String getFileName(int index) {
                if (index < 0 || index >= files.length) {
                    throw new IllegalArgumentException("Invalid file index: " + index);
                }
                return files[index].getName();
            }
        };
    }
}


