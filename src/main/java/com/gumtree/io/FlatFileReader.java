package com.gumtree.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FlatFileReader implements FileReader {

    @Override
    public List<String> readLines(String fileName) throws IOException {
        try(BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
            return getLines(br);
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private LinkedList<String> getLines(BufferedReader br) throws IOException {
        LinkedList<String> lines = new LinkedList<String>();
        String line = null;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}