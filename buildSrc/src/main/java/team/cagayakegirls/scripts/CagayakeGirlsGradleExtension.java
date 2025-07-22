package team.cagayakegirls.scripts;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;

import javax.inject.Inject;

public abstract class CagayakeGirlsGradleExtension {
    @Inject
    protected abstract Project getProject();

    public Dependency yarnMappingsPatch(String mappingsVersion) {
        return getProject().getDependencies().create("dev.architectury:yarn-mappings-patch-neoforge:" + mappingsVersion);
    }
}
