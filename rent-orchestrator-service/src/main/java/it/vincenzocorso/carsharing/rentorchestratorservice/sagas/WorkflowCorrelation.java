package it.vincenzocorso.carsharing.rentorchestratorservice.sagas;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowCorrelation extends PanacheEntityBase {
    @Id
    public String messageId;
    public String workflowId;
}
