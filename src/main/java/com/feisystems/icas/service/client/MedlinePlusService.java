package com.feisystems.icas.service.client;

import java.util.List;

import com.feisystems.icas.domain.client.Link;

public interface MedlinePlusService {
	public abstract List<Link> getLinks(String code, String type);
}
