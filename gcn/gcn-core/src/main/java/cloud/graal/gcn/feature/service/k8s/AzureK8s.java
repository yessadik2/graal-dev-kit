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
package cloud.graal.gcn.feature.service.k8s;

import cloud.graal.gcn.model.GcnCloud;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.feature.k8s.KubernetesClient;
import jakarta.inject.Singleton;

import static cloud.graal.gcn.model.GcnCloud.AZURE;

/**
 * Azure Kubernetes service feature.
 *
 * @since 1.0.0
 */
@Singleton
public class AzureK8s extends AbstractK8sFeature {

    /**
     * @param kubernetesClient KubernetesClient feature
     */
    public AzureK8s(KubernetesClient kubernetesClient) {
        super(kubernetesClient);
    }

    @NonNull
    @Override
    public GcnCloud getCloud() {
        return AZURE;
    }

    @NonNull
    @Override
    public String getName() {
        return "gcn-azure-k8s";
    }
}
