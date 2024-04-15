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
package cloud.graal.gcn.feature.service.metrics;

import cloud.graal.gcn.GcnGeneratorContext;
import cloud.graal.gcn.feature.GcnFeatureContext;
import cloud.graal.gcn.model.GcnCloud;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.feature.micrometer.CloudWatch;
import io.micronaut.starter.feature.micrometer.Core;
import io.micronaut.starter.feature.micrometer.MicrometerAnnotations;
import io.micronaut.starter.feature.other.Management;
import jakarta.inject.Singleton;

import static cloud.graal.gcn.model.GcnCloud.AWS;

/**
 * AWS metrics service feature.
 *
 * @since 1.0.0
 */
@Singleton
public class AwsMetrics extends AbstractMetricsFeature {

    private final CloudWatch cloudWatch;

    /**
     * @param cloudWatch CloudWatch feature
     * @param core       Core feature
     * @param management Management feature
     */
    public AwsMetrics(CloudWatch cloudWatch,
                      Core core,
                      Management management,
                      MicrometerAnnotations micrometerAnnotations) {
        super(core, management, micrometerAnnotations);
        this.cloudWatch = cloudWatch;
    }

    @Override
    public void processSelectedFeatures(GcnFeatureContext featureContext) {
        super.processSelectedFeatures(featureContext);
        featureContext.addFeature(cloudWatch, CloudWatch.class);
    }

    @NonNull
    @Override
    protected void doApply(GcnGeneratorContext generatorContext) {
        if (generatorContext.generateExampleCode()) {
            generatorContext.getTestConfiguration().addNested("micronaut.metrics.export.cloudwatch.enabled", false);
        }
    }

    @NonNull
    @Override
    public GcnCloud getCloud() {
        return AWS;
    }

    @NonNull
    @Override
    public String getName() {
        return "gcn-aws-metrics";
    }
}
