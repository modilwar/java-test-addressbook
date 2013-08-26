package com.gumtree.io;

import java.io.IOException;
import java.util.List;

public interface FileReader {
    List<String> readLines(String file) throws IOException;
}
