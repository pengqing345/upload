package com.pq.test.bulk;

import com.pq.test.util.ExcelUtils;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Test1 {
    @Test
    public void bulkImport(){
        String urlPath = "E:\\RMS\\test\\src\\main\\resources\\upload\\学生信息.xlsx";
        Long start = System.currentTimeMillis(); //导入开始时间
/*        List<JSONObject> list = new ArrayList<>();
        List<String> buildingUuids = new ArrayList<>();*/
        List<LinkedHashMap<String, String>> linkedHashMaps = ExcelUtils.excel3json(urlPath);
        if(linkedHashMaps != null){
            for (int i = 0; i < linkedHashMaps.size(); i++) {
                LinkedHashMap<String, String> stringLinkedHashMap = linkedHashMaps.get(i);
                Iterator it = stringLinkedHashMap.keySet().iterator();
                Student student = new Student();
                while (it.hasNext()) {

                    String key = it.next().toString();
                    String value = stringLinkedHashMap.get(key);
                    if("学生姓名".equals(key)||"学生所在班级".equals(key)){
                        if ("null".equals(value) || "".equals(value.trim())) {
                           continue;
                        }else{
                            if("学生姓名".equals(key)){
                                student.setSname(value);
                            }else if("学生所在班级".equals(key)){
                                student.setGrade(value);
                            }

                        }
                    }else{
                           if ("学生ID".equals(key)) {
                                if ("null".equals(value) || org.apache.commons.lang.StringUtils.isBlank(value)) {
                                    student.setSid(null);
                                } else {
                                    student.setSid(new Double(value).intValue());
                                }
                            } else if("学生性别".equals(key)){
                                student.setSex(new Double(value).intValue());
                            }else if("学生年龄".equals(key)){
                                student.setAge(new Double(value).intValue());
                            }
                        }
                }
                System.out.println(student.toString());
                System.out.println("导入共花费时间：" + (System.currentTimeMillis() - start) / 1000 + "秒。");
            }
        }

    }
}
