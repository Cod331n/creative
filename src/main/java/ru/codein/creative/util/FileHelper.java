package ru.codein.creative.util;

import com.google.gson.Gson;
import ru.codein.creative.player.CreativePlayerPlotData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings({"StringBufferReplaceableByString"})
public class FileHelper {
    public static void toJson(String fileName, File dir, CreativePlayerPlotData creativePlayerPlotData) {
        try (FileWriter writer = new FileWriter(new File(dir, new StringBuilder(fileName).append(".json").toString()))) {
            Gson gson = new Gson();
            gson.toJson(creativePlayerPlotData, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static CreativePlayerPlotData fromJson(String fileName, File dir) {
        try (FileReader reader = new FileReader(new StringBuilder(dir.toString()).append("/").append(fileName).append(".json").toString())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, CreativePlayerPlotData.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static CreativePlayerPlotData fromJson(File file) {
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, CreativePlayerPlotData.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void updateJson(String fileName, File dir, CreativePlayerPlotData creativePlayerPlotData) {
        FileHelper.toJson(fileName, dir, creativePlayerPlotData);
    }

    public static List<File> getFilesInDir(File dir) {
        try (Stream<Path> paths = Files.walk(Paths.get(dir.getPath()))) {
            List<File> list = new ArrayList<>();
            paths.filter(Files::isRegularFile).forEach(p -> list.add(new File(p.toString())));

            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public static Optional<File> getFileInDir(String fileName, File dir) {
        if (dir.listFiles() != null && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (fileName.equals(file.getName())) {
                    return Optional.of(file);
                }
            }
        }

        return Optional.empty();
    }
}
