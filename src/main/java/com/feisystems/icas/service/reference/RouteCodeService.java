package com.feisystems.icas.service.reference;
import com.feisystems.icas.domain.reference.RouteCode;
import java.util.List;

public interface RouteCodeService {

	public abstract long countAllRouteCodes();


	public abstract void deleteRouteCode(RouteCode routeCode);


	public abstract RouteCode findRouteCode(Long id);


	public abstract List<RouteCode> findAllRouteCodes();


	public abstract List<RouteCode> findRouteCodeEntries(int firstResult, int maxResults);


	public abstract void saveRouteCode(RouteCode routeCode);


	public abstract RouteCode updateRouteCode(RouteCode routeCode);

}
