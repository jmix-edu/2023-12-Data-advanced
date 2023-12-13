package com.company.jmixpm.screen.projectsnapshotsbrowse;

import com.company.jmixpm.entity.Project;
import io.jmix.auditui.screen.snapshot.SnapshotDiffViewer;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ProjectSnapshotsBrowse")
@UiDescriptor("project-snapshots-browse.xml")
public class ProjectSnapshotsBrowse extends Screen {

    @Autowired
    private SnapshotDiffViewer snapshotDiff;

    @Subscribe("projectsTable")
    public void onProjectsTableSelection(Table.SelectionEvent<Project> event) {
        if (CollectionUtils.isNotEmpty(event.getSelected())) {
            snapshotDiff.loadVersions(event.getSelected().iterator().next());
        }
    }
}