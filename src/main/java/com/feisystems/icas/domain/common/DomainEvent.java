package com.feisystems.icas.domain.common;

import java.util.Date;

public interface DomainEvent {

    public int eventVersion();

    public Date occurredOn();
}
