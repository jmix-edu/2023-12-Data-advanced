package com.company.jmixpm.entity;

import com.company.jmixpm.listener.TaskJpaListener;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@JmixEntity(annotatedPropertiesOnly = false)
@Table(name = "TASK_", indexes = {
        @Index(name = "IDX_TASK__PROJECT", columnList = "PROJECT_ID")
})
@Entity(name = "Task_")
@EntityListeners(TaskJpaListener.class)
public class Task {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    //    @JmixProperty
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JoinColumn(name = "ASSIGNEE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User assignee;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "ESTIMATED_EFFORTS")
    private Integer estimatedEfforts;

    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "IS_CLOSED", nullable = false)
    @NotNull
    private Boolean isClosed = false;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @JmixProperty
    @Transient
    private LocalDateTime supposedEndDate;

    public LocalDateTime getSupposedEndDate() {
        return supposedEndDate;
    }

    public void setSupposedEndDate(LocalDateTime supposedEndDate) {
        this.supposedEndDate = supposedEndDate;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getEstimatedEfforts() {
        return estimatedEfforts;
    }

    public void setEstimatedEfforts(Integer estimatedEfforts) {
        this.estimatedEfforts = estimatedEfforts;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @PostLoad
    public void postLoad() {
        if (estimatedEfforts != null) {
            supposedEndDate = startDate != null
                    ? startDate
                    : LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
            supposedEndDate = supposedEndDate.plusHours(estimatedEfforts);
        }

    }
}