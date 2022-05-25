package com.cars.cardealership.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelNames {
    public static Map<String, List<String>> makeModels = new HashMap<String, List<String>>() {{
        put("BMW", new ArrayList<String>(){{ add("F30"); add("F31"); add("G20");}});
        put("Tesla", new ArrayList<String>(){{ add("Model S"); add("Model X"); add("Model 3");}});
        put("Volkswagen", new ArrayList<String>(){{ add("Tiguan"); add("Golf"); add("Touareg");}});
        put("Skoda", new ArrayList<String>(){{ add("Kodiaq"); add("Octavia"); add("Fabia");}});
        put("Audi", new ArrayList<String>(){{ add("e-tron"); add("A4"); add("Q4 e-tron");}});
    }};
}
