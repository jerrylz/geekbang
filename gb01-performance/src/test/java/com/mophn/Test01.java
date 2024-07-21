package com.mophn;


import cn.hutool.json.JSONUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Test01 {
    public static void main(String[] args) {
        ArrayList<Crop> maps = new ArrayList<>();

        maps.add(new Crop("玉米", 1234, "GN001"));
        maps.add(new Crop("小麦", 3452, "GN001"));

        maps.add(new Crop("玉米", 3334, "GN002"));
        maps.add(new Crop("小麦", 9452, "GN002"));
        maps.add(new Crop("水稻", 52, "GN002"));

        // {"id":"", "玉米": 1234, "小麦": 3452}
        Map<String, List<Crop>> ids = maps.stream().collect(Collectors.groupingBy(Crop::getId, Collectors.toList()));
        ArrayList<Object> res = new ArrayList<>();
        Set<Object> cropTypeSet = new HashSet<>();
        for (String id : ids.keySet()) {
            HashMap<String, Object> map = new LinkedHashMap<>();
            float sum = 0;
            for (Crop crop : ids.get(id)){
                map.put(crop.getType(), crop.getArea());
                cropTypeSet.add(crop.getType());
                sum += crop.getArea();
            }
            map.put("id", id);
            map.put("sum", sum);
            res.add(map);
        }
        System.out.println(JSONUtil.toJsonStr(res));
        System.out.println(JSONUtil.toJsonStr(cropTypeSet));

        float a = 23.45f;
        float a2 = 23.49f;
        float a3 = 23.67f;
        ArrayList<Float> floats = new ArrayList<>();
        floats.add(a);
        floats.add(a2);
        floats.add(a3);
        floats.stream().reduce(Float::sum).orElse(0f);

        double d = a+a2+a3;
        System.out.println(d);

    }

    static class Crop {
        private String type;
        private float area;
        private String id;

        public Crop() {
        }

        public Crop(String type, float area, String id) {
            this.type = type;
            this.area = area;
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float getArea() {
            return area;
        }

        public void setArea(float area) {
            this.area = area;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
