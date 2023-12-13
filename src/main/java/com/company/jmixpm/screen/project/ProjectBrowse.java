package com.company.jmixpm.screen.project;

import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CollectionContainer<Project> projectsDc;
    @Autowired
    private Notifications notifications;

    @Subscribe("dmCreate")
    public void onDmCreateClick(final Button.ClickEvent event) {
        Project project = dataManager.create(Project.class);
        project.setName("Project " + RandomStringUtils.randomAlphabetic(5));
        User user = (User) currentAuthentication.getUser();
        project.setManager(user);

        Project saved = dataManager.unconstrained().save(project);
        projectsDc.getMutableItems().add(saved);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        int newProjectsCount = dataManager.loadValue("select count(p) from Project p " +
                                "where :session_isManager = TRUE " +
                                "and p.status = @enum(com.company.jmixpm.entity.ProjectStatus.NEW) " +
                                "and p.manager.id = :current_user_id"
                        , Integer.class)
                .one();

        if (newProjectsCount != 0) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withPosition(Notifications.Position.TOP_RIGHT)
                    .withCaption("New projects")
                    .withDescription("You have " + newProjectsCount + " project(s) with NEW status: ")
                    .show();
        }

    }


}