package com.company.jmixpm.app;

import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.datatype.ProjectLabelsDatatype;
import com.company.jmixpm.entity.Project;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import io.jmix.core.JmixOrder;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.core.metamodel.model.Range;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.ComponentGenerationContext;
import io.jmix.ui.component.ComponentGenerationStrategy;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.sys.ValuePathHelper;
import org.springframework.core.Ordered;

@org.springframework.stereotype.Component
public class ProjectFieldsComponentGenerationStrategy implements ComponentGenerationStrategy, Ordered {

    private UiComponents uiComponents;

    public ProjectFieldsComponentGenerationStrategy(UiComponents uiComponents) {
        this.uiComponents = uiComponents;
    }

    @Nullable
    @Override
    public io.jmix.ui.component.Component createComponent(ComponentGenerationContext context) {
        String checkPropertyName = context.getProperty();
        String[] properties = ValuePathHelper.parse(checkPropertyName);
        if (properties.length > 1) {
            checkPropertyName = properties[properties.length - 1];
        }

        if (!"projectLabels".equals(checkPropertyName)) {
            return null;
        }

        MetaClass metaClass = context.getMetaClass();

        MetaPropertyPath mpp = metaClass.getPropertyPath(context.getProperty());

        if (mpp != null ) {
            metaClass = mpp.getMetaProperty().getDomain();
        }

        if (!metaClass.getJavaClass().equals(Project.class)) {
            return null;
        }
        MetaProperty property = metaClass.getProperty(checkPropertyName);
        Range range = property.getRange();

        if (range.isDatatype()) {
            Datatype datatypeToCheck = range.asDatatype();
            if (datatypeToCheck instanceof ProjectLabelsDatatype) {
                return create(context);
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return JmixOrder.HIGHEST_PRECEDENCE;
    }

    private io.jmix.ui.component.Component create(ComponentGenerationContext context) {
        TextArea<ProjectLabels> component = uiComponents.create(TextArea.NAME);
        component.setRows(2);
        component.setValueSource(context.getValueSource());
        return component;
    }
}