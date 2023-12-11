package com.company.jmixpm.screen.project;

import com.company.jmixpm.datatype.ProjectLabels;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
    @Autowired
    private TextArea<ProjectLabels> labelsField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        labelsField.setEditable(true);

        event.getEntity().setProjectLabels(new ProjectLabels(List.of("bug", "new", "task")));
    }
    
    
}