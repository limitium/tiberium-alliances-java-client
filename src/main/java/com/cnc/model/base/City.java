package com.cnc.model.base;


import com.cnc.model.resources.CityResource;
import com.cnc.model.resources.CityResourceType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class City {
    private final int MAX_X_BUILDING = 9;
    private final int MAX_Y_BUILDING = 8;
    private String name;
    private long id;
    private long x;
    private long y;
    private double level;
    private double defenceLevel;
    private double offenceLevel;
    private HashMap<CityResourceType, CityResource> resources;
    private HashMap<Long, Building> buildings;
    private HashMap<Long, OffenseUnit> offense;
    private HashMap<Long, DefenseUnit> defense;
    private ResourceField[][] resourceFields;

    public City() {
        buildings = new HashMap<Long, Building>();
        offense = new HashMap<Long, OffenseUnit>();
        defense = new HashMap<Long, DefenseUnit>();
        resources = new HashMap<CityResourceType, CityResource>();
        resources.put(CityResourceType.TIBERIUM, new CityResource());
        resources.put(CityResourceType.CRYSTAL, new CityResource());
        resources.put(CityResourceType.POWER, new CityResource());
        resources.put(CityResourceType.CREDITS, new CityResource());
    }

    public void update(JSONObject data) {
        name = (String) data.get("n");
        id = (Long) data.get("i");
        x = (Long) data.get("x");
        y = (Long) data.get("y");
        level = ((Number) data.get("blv")).doubleValue();
        offenceLevel = ((Number) data.get("olv")).doubleValue();
        defenceLevel = ((Number) data.get("dlv")).doubleValue();

        for (Object o : (JSONArray) data.get("r")) {
            JSONObject resourceData = (JSONObject) o;
            CityResourceType cityResourceType = CityResourceType.get((Long) resourceData.get("t"));
            if (cityResourceType != null) {
                resources.get(cityResourceType).update(resourceData);
            }
        }
        buildings.clear();
        for (Object o : (JSONArray) data.get("b")) {
            JSONObject buildingData = (JSONObject) o;
            BuildingType buildingType = BuildingType.get((Long) buildingData.get("t"));
            if (buildingType != null) {
                Building building = new Building();
                building.update(buildingData);
                buildings.put(building.getId(), building);
            }
        }
        offense.clear();
        defense.clear();
        for (Object o : (JSONArray) data.get("u")) {
            JSONObject unitData = (JSONObject) o;
            Long type = (Long) unitData.get("ui");
            if (type < 100) {
                if (OffenseType.get(type) != null) {
                    OffenseUnit offenseUnit = new OffenseUnit();
                    offenseUnit.update(unitData);
                    offense.put(offenseUnit.getId(), offenseUnit);
                }
            } else {
                if (DefenseType.get(type) != null) {
                    DefenseUnit defenseUnit = new DefenseUnit();
                    defenseUnit.update(unitData);
                    defense.put(defenseUnit.getId(), defenseUnit);
                }
            }

        }
        int y = 0;
        resourceFields = new ResourceField[MAX_X_BUILDING][MAX_Y_BUILDING];
        for (Object o : (JSONArray) data.get("l")) {
            if (y > MAX_Y_BUILDING) {
                break;
            }
            long stored = (Long) o;
            if (stored > 0) {
                for (int x = 0; x < MAX_X_BUILDING; x++) {
                    long code = (stored >> (3 * x)) & 0x7;
                    if (code > 0) {
                        ResourceFieldType resourceFieldType = ResourceFieldType.get(code);
                        resourceFields[x][y] = new ResourceField(x, y, resourceFieldType);
                    }
                }
            }
            y++;
        }
    }

    public long getId() {
        return id;
    }

    public int getTier() {
        int tier = 8;
        int[] caps = {40, 35, 30, 25, 21, 16, 10, 1};
        for (int cap : caps) {
            if (this.getLevel() >= cap) {
                return tier;
            }
            tier--;
        }
        return tier;
    }

    public double getLevel() {
        return level;
    }

    public CityResource getResource(CityResourceType cityResourceType) {
        return resources.get(cityResourceType);
    }

    public String getName() {
        return name;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public double getDefenceLevel() {
        return defenceLevel;
    }

    public double getOffenceLevel() {
        return offenceLevel;
    }

    public int getBuildingLimit() {
        for (Map.Entry<Long, Building> entry : buildings.entrySet()) {
            Building building = entry.getValue();
            if (building.getType() == BuildingType.CONSTRUCTION_YARD) {
                return (int) (building.getLevel() + 15);
            }
        }
        return 0;
    }

    public HashMap<Long, Building> getBuildings() {
        return buildings;
    }

    public ResourceField getResourceFiled(int x, int y) {
        return resourceFields[x][y];
    }
}
