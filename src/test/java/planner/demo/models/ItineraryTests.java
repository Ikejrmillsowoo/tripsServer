//package planner.demo.models;
//
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ItineraryTests {
//
//    @Test
//    void classExistsAndHasPublicNoArgConstructor() throws Exception {
//        Class<?> cls = Class.forName("planner.demo.models.Itinerary");
//        Constructor<?> ctor = cls.getDeclaredConstructor();
//        assertTrue(Modifier.isPublic(ctor.getModifiers()) || Modifier.isProtected(ctor.getModifiers()),
//                "Itinerary should expose a public or protected no-arg constructor for JPA");
//        Object instance = ctor.newInstance();
//        assertNotNull(instance);
//    }
//
//    @Test
//    void publicSettersGettersRoundTripIfPresent() throws Exception {
//        Class<?> cls = Class.forName("planner.demo.models.Itinerary");
//        Object instance = cls.getDeclaredConstructor().newInstance();
//
//        Method[] methods = cls.getMethods();
//        Map<String, Method> getters = new HashMap<>();
//        Map<String, Method> setters = new HashMap<>();
//
//        for (Method m : methods) {
//            if (Modifier.isPublic(m.getModifiers())) {
//                String name = m.getName();
//                if (name.startsWith("get") && m.getParameterCount() == 0) {
//                    getters.put(name.substring(3), m);
//                } else if (name.startsWith("is") && m.getParameterCount() == 0) {
//                    getters.put(name.substring(2), m);
//                } else if (name.startsWith("set") && m.getParameterCount() == 1) {
//                    setters.put(name.substring(3), m);
//                }
//            }
//        }
//
//        // For each setter that has a matching getter, attempt a round-trip using a sensible sample value.
//        for (Map.Entry<String, Method> entry : setters.entrySet()) {
//            String prop = entry.getKey();
//            Method setter = entry.getValue();
//            Method getter = getters.get(prop);
//            if (getter == null) continue; // no getter, skip
//
//            Class<?> paramType = setter.getParameterTypes()[0];
//            Object sample = sampleValueForType(paramType);
//            if (sample == null) continue; // unknown sample, skip
//
//            // invoke setter then getter and compare
//            setter.invoke(instance, sample);
//            Object got = getter.invoke(instance);
//
//            // For primitive return types, Java reflection returns boxed values.
//            assertEquals(sample, got, "Property " + prop + " did not round-trip via setter/getter");
//        }
//    }
//    private Object sampleValueForType(Class<?> t) {
//        if (t.equals(String.class)) return "sample";
//        if (t.equals(Long.class) || t.equals(long.class)) return 42L;
//        if (t.equals(Integer.class) || t.equals(int.class)) return 7;
//        if (t.equals(Short.class) || t.equals(short.class)) return (short)3;
//        if (t.equals(Byte.class) || t.equals(byte.class)) return (byte)2;
//        if (t.equals(Double.class) || t.equals(double.class)) return 1.5;
//        if (t.equals(Float.class) || t.equals(float.class)) return 1.5f;
//        if (t.equals(Boolean.class) || t.equals(boolean.class)) return true;
//        if (t.equals(Character.class) || t.equals(char.class)) return 'x';
//        if (t.equals(LocalDate.class)) return LocalDate.of(2000,1,1);
//        // add other common types as needed
//        return null;
//    }
//
//}
