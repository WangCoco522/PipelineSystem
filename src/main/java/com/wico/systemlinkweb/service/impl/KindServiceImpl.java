/*
package com.wico.systemlinkweb.service.impl;

import com.wico.systemlinkweb.domain.KindDef;
import com.wico.systemlinkweb.mapper.KindMapper;
import com.wico.systemlinkweb.service.IKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @Author XuCheng
 * @Date 2020/4/27 10:49
 *//*

@Service
public class KindServiceImpl implements IKindService {

    @Autowired
    private KindMapper kindMapper;

    public static Map<String,String> kindDefs = new HashMap<>();

    public static Map<String, String> getKindDefs() {
        return kindDefs;
    }

    public static void setKindDefs(Map<String, String> kindDefs) {
        KindServiceImpl.kindDefs = kindDefs;
    }

    @Override
    @PostConstruct
    public Map<String, String> initKindDdefs(){
        */
/*
        List<KindDef> list = kindMapper.findKind();
        for (KindDef kindDef : list){
            kindDefs.put(kindDef.getKindCode(),kindDef.getKindContent());
        }
        *//*

        return kindDefs;
    }
}
*/
