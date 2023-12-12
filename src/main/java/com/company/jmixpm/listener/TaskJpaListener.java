package com.company.jmixpm.listener;

import com.company.jmixpm.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

public class TaskJpaListener {

    private static final Logger log = LoggerFactory.getLogger(TaskJpaListener.class);
    @PreUpdate
    @PreRemove
    @PrePersist
    public void beforeUpdate(Task task) {
        log.info(task.getClass().getSimpleName() + " before update: " + task.getId());
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void afterUpdate(Task task) {
        log.info(task.getClass().getSimpleName() + " after update: " + task.getId());
    }
}
