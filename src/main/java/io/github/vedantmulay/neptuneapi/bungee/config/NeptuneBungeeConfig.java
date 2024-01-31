/*
 * Copyright (c) 2024 Lumina Games
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.vedantmulay.neptuneapi.bungee.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.*;

import java.util.HashMap;
import java.util.Map;

public class NeptuneBungeeConfig {

    private final File configFile;
    private Map<String, Object> configData;

    public NeptuneBungeeConfig(String configFileName) {
        this.configFile = new File("plugins/NeptuneBungee/" + configFileName);
        this.configData = new HashMap<>();
        loadConfig();
    }

    public void loadConfig() {
        try {
            if (!configFile.exists()) {
                copyDefaultConfig();
            }

            try (FileReader reader = new FileReader(configFile)) {
                Yaml yaml = new Yaml();
                configData = yaml.load(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml(options);
            yaml.dump(configData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyDefaultConfig() {
        try {
            Path pluginDirectory = Paths.get(configFile.getParent());
            if (!Files.exists(pluginDirectory)) {
                Files.createDirectories(pluginDirectory);
            }

            InputStream defaultConfigStream = getClass().getResourceAsStream("/" + configFile.getName());

            if (defaultConfigStream != null) {
                Files.copy(defaultConfigStream, Paths.get(configFile.toURI()));
            } else {
                throw new FileNotFoundException("Default " + configFile.getName() + " not found in resources.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getConfigValue(String key) {
        return configData.get(key);
    }

    public void setConfigValue(String key, Object value) {
        configData.put(key, value);
        saveConfig();
    }
}
