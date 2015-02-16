package com.feisystems.icas.domain.client;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {
	private Collection<Entry> entry;
	
	public void setEntry(Collection<Entry> entry) {
		this.entry = entry;
	}
	
	public Collection<Entry> getEntry() {
		return entry;
	}
}
