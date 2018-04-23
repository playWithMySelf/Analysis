package com.founder.bussiness.building.service;

import java.util.Map;

public interface IBuildingService {
    public Map selectDicdataByTypeOrName(Map<String, String> param);

    public Map selectAllBuildingInfo(Map<String, String> param);
}
