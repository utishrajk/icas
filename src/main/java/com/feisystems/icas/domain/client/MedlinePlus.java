package com.feisystems.icas.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MedlinePlus {
	private Feed feed;
	
	public void setFeed(Feed feed) {
		this.feed = feed;
	}
	
	public Feed getFeed() {
		return feed;
	}
}
