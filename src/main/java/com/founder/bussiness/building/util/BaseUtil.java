package com.founder.bussiness.building.util;

import java.util.Map;

public class BaseUtil {
    public  static Map<String,String> ctrlMap;
    static {
        ctrlMap.put("1","SFWF_1,SFWF_0");//1是危房，0不是
        ctrlMap.put("2","FWYTDM_10,FWYTDM_20,FWYTDM_30,FWYTDM_50,FWYTDM_60,FWYTDM_70,FWYTDM_80,FWYTDM_90");
        ctrlMap.put("3","SFCZFW_1,SFCZFW_0");//1代表出租，0代表没有出租
    }
}
