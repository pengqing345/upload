package com.pq.test.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by rickon on 2017/3/20.
 */
public class JSONUtil {
    public static List<Map<String, String>> analysisJSON(String relationData) {
        JSONArray ja = JSON.parseArray(relationData);
        Iterator<Object> it = ja.iterator();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        while (it.hasNext()) {
            Map<String, String> map = new HashMap<String, String>();
            JSONObject ob = (JSONObject) it.next();
            String key = ob.keySet().toString().replace("[", "").replace("]", "");
            map.put("relationId", key);
            //System.out.print(key);
            String val = ob.get(key).toString();
            //System.out.print(val);
            map.put("relationType", val);
            list.add(map);
        }
        return list;
    }



    //    public static List<DataRelation> analysisJSON(String ownerId, String relationData){
//        JSONArray ja=JSON.parseArray(relationData);
//        Iterator<Object> it=ja.iterator();
//        List<DataRelation> list=new ArrayList<DataRelation>();
//        while (it.hasNext()){
//            DataRelation dr=new DataRelation();
//            dr.setOwnerId(ownerId);
//            JSONObject ob=(JSONObject)it.next();
//            String key=ob.keySet().toString().replace("[","").replace("]","");
//            dr.setRelationId(key);
//            //System.out.print(key);
//            String val=ob.get(key).toString();
//            //System.out.print(val);
//            dr.setRelationType(val);
//            list.add(dr);
//        }
//        return list;
//    }
/*
    public static List<DataRelation> analysisJSON(String ownerId, String relationData) {
        System.out.println("relationData" + relationData);
        JSONArray ja = JSON.parseArray(relationData);
        Iterator<Object> it = ja.iterator();
        List<DataRelation> list = new ArrayList<>();
        while (it.hasNext()) {
            DataRelation dr = new DataRelation();
            JSONObject ob = (JSONObject) it.next();
            dr.setOwnerId(ownerId);
            if (ob.containsKey("relationId")) {
                dr.setRelationId((String) ob.get("relationId"));
            }
            if (ob.containsKey("relationType")) {
                dr.setRelationType((String) ob.get("relationType"));
            }
//            if(ob.containsKey("type")){
//                dr.setType(Integer.parseInt(ob.get("type").toString()));
//            }
            list.add(dr);
        }
        return list;
    }
*/


}
