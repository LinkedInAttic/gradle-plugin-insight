package org.sfaber.gradle.internal.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by sfaber on 3/8/16.
 */
@Aspect
public class PluginLifecycleAspect {

    public static Listener LISTENER;

    @Around("execution (* org.gradle.api.Plugin.apply*(..))")
    public void apply(ProceedingJoinPoint point) throws Throwable {
        LISTENER.beforeApplied((Plugin<Project>) point.getThis());

        try {
            point.proceed();
        } finally {
            LISTENER.afterApplied((Plugin<Project>) point.getThis());
        }
    }

    public interface Listener {
        void beforeApplied(Plugin<Project> plugin);
        void afterApplied(Plugin<Project> plugin);
    }
}
