package it.vincenzocorso.carsharing.common.quarkus;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import it.vincenzocorso.carsharing.common.quarkus.messaging.outbox.jpa.OutboxJpaMessageProducer;

public class CommonQuarkusProcessor {
    private static final String FEATURE = "common-quarkus";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void build(BuildProducer<AdditionalBeanBuildItem> additionalBean) {
        additionalBean.produce(AdditionalBeanBuildItem.unremovableOf(OutboxJpaMessageProducer.class));
    }
}
