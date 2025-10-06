package com.techzo.cambiazo.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
public class BanStatus {

    private boolean active;
    private LocalDateTime bannedUntil;

    protected BanStatus() {
    }

    private BanStatus(boolean active, LocalDateTime bannedUntil) {
        this.active = active;
        this.bannedUntil = bannedUntil;
    }

    public static BanStatus inactive() {
        return new BanStatus(false, null);
    }

    public static BanStatus activeFor(Duration duration) {
        return new BanStatus(true, LocalDateTime.now().plus(duration));
    }

    public boolean isActive() {
        return active && LocalDateTime.now().isBefore(bannedUntil);
    }

    public BanStatus refresh() {
        if (!active) return inactive();
        if (LocalDateTime.now().isAfter(bannedUntil)) return inactive();
        return this;
    }

    public Duration getRemainingTime() {
        if (!active || bannedUntil == null) return Duration.ZERO;
        if (LocalDateTime.now().isAfter(bannedUntil)) return Duration.ZERO;
        return Duration.between(LocalDateTime.now(), bannedUntil);
    }

    public LocalDateTime bannedUntil() {
        return bannedUntil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BanStatus other)) return false;
        return active == other.active &&
                ((bannedUntil == null && other.bannedUntil == null) ||
                        (bannedUntil != null && bannedUntil.equals(other.bannedUntil)));
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(active, bannedUntil);
    }
}
