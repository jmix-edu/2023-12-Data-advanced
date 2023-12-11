package com.company.jmixpm.screen.projectstats;

import com.company.jmixpm.app.ProjectStatsService;
import com.company.jmixpm.entity.ProjectStats;
import io.jmix.core.LoadContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("ProjectStatsScreen")
@UiDescriptor("project-stats-screen.xml")
public class ProjectStatsScreen extends Screen {

    @Autowired
    private ProjectStatsService projectStatService;

    @Install(to = "projectStatsDl", target = Target.DATA_LOADER)
    private List<ProjectStats> projectStatsesDlLoadDelegate(LoadContext<ProjectStats> loadContext) {
        return projectStatService.fetchProjectStatistics();
    }
}