package uk.co.appoly.autoclean;

import java.util.ArrayList;
import java.util.List;

public class ServiceDatabase {

    public static List<ServiceModel> getServiceTypes() {
        ArrayList<ServiceModel> serviceTypes = new ArrayList<>();
        serviceTypes.add(new ServiceModel(0,"Winter Protection Service", 140, "", R.drawable.winter_protection, "5 hours and over"));
        serviceTypes.add(new ServiceModel(1,"Enhancement Detail", 220, "", R.drawable.enhancement_detail, "Duration Varies"));
        serviceTypes.add(new ServiceModel(2,"Engine bay cleaned", 20, "", R.drawable.engine_bay_cleaned, "1 hour 30 minuets"));
        serviceTypes.add(new ServiceModel(3,"Headlight restoration", 30, "", R.drawable.headlight_restoration, "2 hours and over"));
        serviceTypes.add(new ServiceModel(4,"Luxury Valet", 150, "", R.drawable.luxury_valet, "8 hours and over"));
        serviceTypes.add(new ServiceModel(5,"Full Valet", 80, "", R.drawable.full_valet, "6 hours and over"));
        serviceTypes.add(new ServiceModel(6,"Mini Valet", 40, "", R.drawable.mini_valet, "2 hours 30 minuets and over"));
        serviceTypes.add(new ServiceModel(7,"Maintenance Wash", 20, "", R.drawable.maintenance_wash, "1 hour and over"));

        return serviceTypes;
    }

}
