/*
 * Copyright 2017-2022 Oracle and/or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cloud.graal.gcn.template;

import io.micronaut.starter.template.DefaultTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class GcnPropertiesTemplate extends DefaultTemplate {

    private final Properties properties;

    private final Map<String, Object> originalConfig;

    public GcnPropertiesTemplate(String path, Map<String, Object> config) {
        this(DEFAULT_MODULE, path, config);
    }

    public GcnPropertiesTemplate(String module, String path, Map<String, Object> config) {
        super(module, path);
        originalConfig = config;
        this.properties = transform(new Properties(), "", config);
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        OutputStream outputStream1 = new OutputStream() {
            private final StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) {
                this.string.append((char) b);
            }

            public String toString() {
                return this.string.toString();
            }
        };

        properties.store(outputStream1, null);
        String string = outputStream1.toString();
        string = string.substring(string.indexOf('\n') + 1);
        List<String> lines = Stream.of(string.split("\n")).sorted().toList();
        string = String.join("\n", lines);
        outputStream.write(string.getBytes());
    }

    public Map<String, Object> getOriginalConfig() {
        return originalConfig;
    }

    private Properties transform(Properties finalConfig, String prefix, Map<String, Object> config) {
        for (Map.Entry<String, Object> entry : config.entrySet()) {
            transform(finalConfig, prefix + entry.getKey(), entry.getValue());
        }
        return finalConfig;
    }

    @SuppressWarnings("unchecked")
    private void transform(Properties finalConfig, String prefix, Object value) {
        if (value instanceof Map) {
            transform(finalConfig, prefix + ".", (Map<String, Object>) value);
        } else if (value instanceof List<?> list) {
            for (int i = 0; i < list.size(); i++) {
                transform(finalConfig, prefix + "[" + i + "]", list.get(i));
            }
        } else {
            finalConfig.put(prefix, value.toString());
        }
    }
}
