/*
 * Copyright 2023 Oracle and/or its affiliates
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
package cloud.graal.gcn.feature.service.streaming;

import cloud.graal.gcn.GcnGeneratorContext;
import cloud.graal.gcn.model.GcnCloud;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.feature.messaging.kafka.Kafka;
import jakarta.inject.Singleton;

import java.util.Map;

import static cloud.graal.gcn.model.GcnCloud.GCP;

/**
 * GCP streaming service feature.
 *
 * @since 1.0.0
 */
@Singleton
public class GcpStreaming extends AbstractStreamingFeature {

    /**
     * @param kafka Kafka feature
     */
    public GcpStreaming(Kafka kafka) {
        super(kafka);
    }

    @Override
    protected void doApply(GcnGeneratorContext generatorContext) {

        //kafka:
        //  retries: 3
        //  max:
        //    request:
        //      size: 1048576
        //    partition:
        //      fetch:
        //        bytes: 1048576
        generatorContext.getConfiguration().addNested(Map.of(
                "kafka.max.partition.fetch.bytes", 1048576,
                "kafka.max.request.size", 1048576,
                "kafka.retries", 3)
        );
    }

    @NonNull
    @Override
    public GcnCloud getCloud() {
        return GCP;
    }

    @NonNull
    @Override
    public String getName() {
        return "gcn-gcp-streaming";
    }
}
