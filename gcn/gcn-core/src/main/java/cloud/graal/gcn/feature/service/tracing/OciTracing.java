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
package cloud.graal.gcn.feature.service.tracing;

import cloud.graal.gcn.GcnGeneratorContext;
import cloud.graal.gcn.feature.GcnFeatureContext;
import cloud.graal.gcn.model.GcnCloud;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.feature.opentelemetry.OpenTelemetry;
import io.micronaut.starter.feature.opentelemetry.OpenTelemetryAnnotations;
import io.micronaut.starter.feature.opentelemetry.OpenTelemetryExporterZipkin;
import io.micronaut.starter.feature.opentelemetry.OpenTelemetryHttp;
import io.micronaut.starter.feature.opentelemetry.OpenTelemetryZipkin;
import jakarta.inject.Singleton;

import static cloud.graal.gcn.model.GcnCloud.OCI;

/**
 * OCI tracing service feature.
 *
 * @since 1.0.0
 */
@Singleton
public class OciTracing extends AbstractTracingFeature {

    private final OpenTelemetryZipkin openTelemetryZipkin;
    private final OpenTelemetryExporterZipkin openTelemetryExporterZipkin;

    /**
     * @param openTelemetry               OpenTelemetry feature
     * @param openTelemetryHttp           OpenTelemetryHttp feature
     * @param openTelemetryAnnotations    OpenTelemetryAnnotations feature
     * @param openTelemetryZipkin         OpenTelemetryZipkin feature
     * @param openTelemetryExporterZipkin OpenTelemetryExporterZipkin feature
     */
    public OciTracing(OpenTelemetry openTelemetry,
                      OpenTelemetryHttp openTelemetryHttp,
                      OpenTelemetryAnnotations openTelemetryAnnotations,
                      OpenTelemetryZipkin openTelemetryZipkin,
                      OpenTelemetryExporterZipkin openTelemetryExporterZipkin) {
        super(openTelemetry, openTelemetryHttp, openTelemetryAnnotations);
        this.openTelemetryZipkin = openTelemetryZipkin;
        this.openTelemetryExporterZipkin = openTelemetryExporterZipkin;
    }

    @Override
    public void processSelectedFeatures(GcnFeatureContext featureContext) {
        featureContext.addFeature(openTelemetryZipkin, OpenTelemetryZipkin.class);
        featureContext.addFeature(openTelemetryExporterZipkin, OpenTelemetryExporterZipkin.class);
    }

    @Override
    protected void doApply(GcnGeneratorContext generatorContext) {
        generatorContext.getConfiguration().addNested(
                "otel.exporter.zipkin.endpoint",
                "https://[redacted].apm-agt.us-phoenix-1.oci.oraclecloud.com/20200101/observations/public-span" +
                        "?dataFormat=zipkin&dataFormatVersion=2&dataKey=[public key]");
    }

    @NonNull
    @Override
    public GcnCloud getCloud() {
        return OCI;
    }

    @NonNull
    @Override
    public String getName() {
        return "gcn-oci-tracing";
    }
}
