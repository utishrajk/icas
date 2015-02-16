package com.feisystems.icas.domain.client;


import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry {
	
	private Collection<Link> link;

	public Collection<Link> getLink() {
		return link;
	}

	public void setLink(Collection<Link> link) {
		this.link = link;
	}

}
