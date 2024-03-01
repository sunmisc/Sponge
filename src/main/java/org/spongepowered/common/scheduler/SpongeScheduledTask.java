package org.spongepowered.common.scheduler;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.common.SpongeCommon;
import org.spongepowered.common.event.tracking.PhaseContext;
import org.spongepowered.common.event.tracking.PhaseTracker;
import org.spongepowered.common.event.tracking.phase.plugin.PluginPhase;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class SpongeScheduledTask implements ScheduledTask, DelayedRunnable {
    private final SpongeScheduler scheduler;
    private final String name;
    private final UUID uuid;
    private final SpongeTask src;
    private volatile boolean cancelled = false;

    /*
     * Negative value - time in ticks
     * This is done for atomic access of the getDelay() method
     */
    private volatile long time;
    private final Cyclic cyclic;

    SpongeScheduledTask(final SpongeScheduler scheduler,
                        final String name, final UUID uuid,
                        final SpongeTask src,
                        final long start,
                        final Cyclic cyclic) {
        this.scheduler = scheduler;
        this.name = name;
        this.uuid = uuid;
        this.src = src;
        this.time = start;
        this.cyclic = cyclic;
    }
    @Override
    public void run() {
        if (this.isCancelled())
            return;
        final SpongeTask x = this.src;

        try (final @Nullable PhaseContext<@NonNull ?> context = PluginPhase.State.SCHEDULED_TASK
                .createPhaseContext(PhaseTracker.getInstance())
                .source(this)
                .container(x.plugin())) {
            context.buildAndSwitch();
            try {
                x.executor().accept(this);
            } catch (final Throwable t) {
                SpongeCommon.logger().error("The Scheduler tried to run the task '{}' owned by '{}' but an error occurred.",
                        name, x.plugin().metadata().id(), t);
            }
        }

        if (isPeriodic()) {
            final long q = x.interval;
            this.time = q < 0
                    ? -(this.scheduler.timestamp(true) - q)
                    :  this.scheduler.timestamp(false) + q;
            this.cyclic.enqueue(this);
        } else {
            this.cyclic.finish();
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        final long p = this.time, now = this.scheduler.timestamp(p < 0);
        return unit.convert(Math.abs(p) - now, NANOSECONDS);
    }

    @Override
    public Scheduler scheduler() {
        return this.scheduler;
    }

    @Override
    public Task task() {
        return this.src;
    }

    @Override
    public boolean cancel() {
        final boolean cancelled = !this.cancelled ||
                CANCELLED.compareAndSet(this, false, true);
        if (cancelled)
            this.cyclic.finish();
        return cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) return 0;
        return Long.compare(
                this.getDelay(TimeUnit.NANOSECONDS),
                other.getDelay(TimeUnit.NANOSECONDS));
    }

    @Override
    public boolean isPeriodic() {
        return this.src.interval != 0;
    }

    @Override
    public UUID uniqueId() {
        return this.uuid;
    }

    @Override
    public String name() {
        return this.name;
    }


    private static final VarHandle CANCELLED;
    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            CANCELLED = l.findVarHandle(SpongeScheduledTask.class,
                    "cancelled", boolean.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
