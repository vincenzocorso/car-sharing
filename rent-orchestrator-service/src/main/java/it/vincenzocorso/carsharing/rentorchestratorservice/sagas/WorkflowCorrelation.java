package it.vincenzocorso.carsharing.rentorchestratorservice.sagas;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowCorrelation extends PanacheEntityBase {
    @Id
    public String messageId;
    public String workflowId;
}
