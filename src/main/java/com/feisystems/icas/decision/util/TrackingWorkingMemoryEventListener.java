package com.feisystems.icas.decision.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.DefaultWorkingMemoryEventListener;
import org.drools.event.rule.WorkingMemoryEvent;
import org.drools.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * When validation rules fire, they should insert a TradeValidationAnnotation
 * into the working memory. This class listens for these events, and adds them
 * to a list so that a client can query all the alerts raised for a request.
 * <p>
 * You should probably avoid using this outside testing, as the contained lists
 * of events will grow and grow.
 * </p>
 */
public class TrackingWorkingMemoryEventListener
            extends DefaultWorkingMemoryEventListener {

    private static Logger log = LoggerFactory.getLogger(TrackingWorkingMemoryEventListener.class);

    private List<WorkingMemoryEvent> allEvents = new ArrayList<WorkingMemoryEvent>();
    private List<ObjectInsertedEvent> insertions = new ArrayList<ObjectInsertedEvent>();
    private List<ObjectRetractedEvent> retractions = new ArrayList<ObjectRetractedEvent>();
    private List<ObjectUpdatedEvent> updates = new ArrayList<ObjectUpdatedEvent>();

    private List<Map<String, Object>> factChanges = new ArrayList<Map<String,Object>>();

    private FactHandle handleFilter;
    private Class<?> classFilter;

    /**
     * Void constructor sets the listener to record all working memory events
     * with no filtering.
     */
    public TrackingWorkingMemoryEventListener() {
        this.handleFilter = null;
    }
    
    /**
     * Constructor which sets up an event filter. The listener will only record
     * events when the event {@link FactHandle} matches the constructor argument.
     * 
     * @param handle
     *            The {@link FactHandle} to filter on.
     */
    public TrackingWorkingMemoryEventListener(FactHandle handle) {
        this.handleFilter = handle;
    }
    
    public TrackingWorkingMemoryEventListener(Class<?> classFilter) {
        this.handleFilter = null;
        this.classFilter = classFilter;
    }

    @Override
    public void objectInserted(final ObjectInsertedEvent event) {
        if ((handleFilter == null  && classFilter == null)
                || event.getFactHandle() == handleFilter
                || event.getObject().getClass().equals(classFilter)) {
            insertions.add(event);
            allEvents.add(event);
            log.trace("Insertion: " + DroolsUtil.objectDetails(event.getObject()));
        }
    }

    @Override
    public void objectRetracted(final ObjectRetractedEvent event) {
        if ((handleFilter == null  && classFilter == null) 
                || event.getFactHandle() == handleFilter
                || event.getOldObject().getClass().equals(classFilter)) {
            retractions.add(event);
            allEvents.add(event);
            log.trace("Retraction: " + DroolsUtil.objectDetails(event.getOldObject()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void objectUpdated(final ObjectUpdatedEvent event) {
        if ((handleFilter == null  && classFilter == null) 
                || event.getFactHandle() == handleFilter
                || event.getObject().getClass().equals(classFilter)) {
            updates.add(event);
            allEvents.add(event);

            Object fact = event.getObject();
            try {
                factChanges.add(BeanUtils.describe(fact));
            } catch (Exception e) {
                log.error("Unable to get object details for tracking: " + DroolsUtil.objectDetails(fact), e);
            }
            log.trace("Update: " + DroolsUtil.objectDetails(event.getObject()));
        }
    }

    public List<WorkingMemoryEvent> getAllEvents() {
        return allEvents;
    }

    public List<ObjectInsertedEvent> getInsertions() {
        return insertions;
    }

    public List<ObjectRetractedEvent> getRetractions() {
        return retractions;
    }

    public List<ObjectUpdatedEvent> getUpdates() {
        return updates;
    }

    public List<Map<String, Object>> getFactChanges() {
        return factChanges;
    }

    public String getPrintableSummary() {
        return "TrackingWorkingMemoryEventListener: " +
                "insertions=[" + insertions.size() + "], " +
                "retractions=[" + retractions.size() + "], " +
                "updates=[" + updates.size() + "]";
    }

    public String getPrintableDetail() {
        StringBuilder report = new StringBuilder(
                "TrackingWorkingMemoryEventListener: " + "insertions=["
                        + insertions.size() + "], " + "retractions=["
                        + retractions.size() + "], " + "updates=["
                        + updates.size() + "]");

        for (ObjectInsertedEvent event : insertions) {
            report.append("\n" + DroolsUtil.objectDetails(event.getObject()));
        }
        for (ObjectRetractedEvent event : retractions) {
            report.append("\n" + DroolsUtil.objectDetails(event.getOldObject()));
        }
        for (ObjectUpdatedEvent event : updates) {
            report.append("\n" + DroolsUtil.objectDetails(event.getObject()));
        }

        return report.toString();
    }

}
