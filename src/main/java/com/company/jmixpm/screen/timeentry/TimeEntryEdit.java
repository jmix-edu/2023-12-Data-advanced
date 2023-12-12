package com.company.jmixpm.screen.timeentry;

import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.TimeEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("TimeEntry.edit")
@UiDescriptor("time-entry-edit.xml")
@EditedEntityContainer("timeEntryDc")
public class TimeEntryEdit extends StandardEditor<TimeEntry> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<TimeEntry> event) {
        TimeEntry entry = event.getEntity();
        User current = ((User) currentAuthentication.getUser());
        entry.setUser(current);
    }
}